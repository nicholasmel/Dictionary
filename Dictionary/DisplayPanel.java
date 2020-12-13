/******************************************************************************************
 * Nicholas Mel
 * DisplayPanel shows the wordList and dictList and the many options the user can take.
 * The user can ignore, replace, add, or store a word, as well as
 * showing the statistics function.
 ******************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.util.*;

public class DisplayPanel extends JPanel {
	//These two lists take the wordList and dictList and show them in the display panel
	private JList<String> words;
	private JList<String> dictionary;
	//Initializing buttons options to append,store,replace,ignore and show statistics.
	private JButton append;
	private JButton store;
	private JButton statistics;
	private JButton replace;
	private JButton ignore;
	//Label to show a message for errors or success.
	private JLabel message;
	//the Vector where the .txt files store their words in.
	private Vector<String> wordList;
	private Vector<String> dictList;
	
	//Panels for the applet to show the word lists and the buttons.
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JScrollPane pane1;
	private JScrollPane pane2;
	private String save = "";
	
	private BufferedWriter file1 = null;
	private FileWriter fw = null;
	private String dict;
	
	//Pop up windows when using the replace function as well as statistic function.
	private JDialog replaceGUI;
	private JDialog statisticGUI;
	private JTextField replacement;
	private JButton ok;
	private JList<String> displayInput;
	private DefaultListModel<String> modelDict;
	private JList<String>displayDict;
	private JTextField input3;
	
	//Initializing variables that would be used to show statistics of the program.
	private int inFileWordsCount = 0;
	private int wordsReplacedCount = 0;
	private int wordsAddedCount = 0;
	private int wordsIgnoredCount = 0;
	private int linesReadCount = 0;
	private int wordsRepeated = 0;
	
	
	//Main display panel.
	public DisplayPanel(Vector<String> wordList, Vector<String> dictList) {
		this.wordList = wordList;
		this.dictList = dictList;
		//setting the size and dimensions of the words and dictionary display.
		words = new JList<String>(wordList);
		words.setPreferredSize(new Dimension(250, 300));
		dictionary = new JList<String>(dictList);
		dictionary.setPreferredSize(new Dimension(250, 300));
		

		//White boxes to hold the words.
		pane1 = new JScrollPane(words);
		pane2 = new JScrollPane(dictionary);
		
		//Calls the ButtonListener and ActionListener methods so when a button is pushed it will take action.
		append = new JButton("ADD WORD TO DICTIONARY");
		append.addActionListener(new ButtonListener());
		store = new JButton("STORE WORDS INTO DICTIONARY FILE");
		store.addActionListener(new ButtonListener());
		replace = new JButton("REPLACE");
		input3 = new JTextField(15);
		JScrollPane scroll = new JScrollPane();
		modelDict = new DefaultListModel();
		displayInput = new JList<String>(modelDict);
		replace.addActionListener(new ButtonListener());
		statistics = new JButton("STATISTICS");
		statistics.addActionListener(new ButtonListener());
		ignore = new JButton("IGNORE");
		ignore.addActionListener(new ButtonListener());
		
		message = new JLabel();
		// organize components here
		p1 = new JPanel();
		p1.add(words);
		p1.add(dictionary);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(3, 3));
		p2.add(append);
		p2.add(store);
		p2.add(replace);
		p2.add(statistics);
		p2.add(ignore);

		p3 = new JPanel(new BorderLayout());
		p3.add(p1, BorderLayout.NORTH);
		p3.add(p2, BorderLayout.SOUTH);
		add(p3);
		add(message);
	}
	//ButtonListener that implements an ActionListener with a variable event that takes action corresponding to user choice.
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==append){
				for (int i = 0; i < dictList.size(); i++) {
					if ((dictList.get(i)).equals(words.getSelectedValue())) {
						message.setText("error: word already added");	//If there is a word that is already added.
						return;
					}
				}
				dictList.add(words.getSelectedValue());//If not the add word to dictionary and increase wordsAddedCount by 1.
				dictionary.updateUI();
				message.setText("word successfully added to dictionary");
				wordsAddedCount+=1;
			} else if(event.getSource()==store){	//Store words in dictList
				try {
					file1 = new BufferedWriter(new FileWriter(new File(InputPanel.tmp), true));
					for (int i = 0; i < dictList.size(); i++) {
						file1.write(dictList.get(i));
						file1.newLine();
					}
					file1.close();

				} catch (IOException e) {

				}
				message.setText("dictionary has been stored");
			}else if(event.getSource()==ignore){	//ignoring a word, word will get removed from either wordList or dictList.
				wordList.remove(words.getSelectedValue());
				dictList.remove(dictionary.getSelectedValue());
				words.updateUI();
				message.setText("word successfully ignored");
				wordsIgnoredCount+=1;				//Increment wordsIgnoredCount by 1 if successful
			} else if(event.getSource()==replace){	//Replace an existing word with a new word from the user.
				replaceGUI = new JDialog();			//Create a pop up window so user can interact with.
				replaceGUI.getSize(new Dimension(200,200));			
				replaceGUI.setResizable(true);
				replaceGUI.setTitle("Replace");
				replaceGUI.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				JPanel replaceWindow = new JPanel();
				replaceWindow.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
				JLabel p1 = new JLabel("Enter new word:");
				replacement = new JTextField();		//Text field for user input.
				replacement.setPreferredSize(new Dimension(200,24));
				ok = new JButton("OK");				//This OK button will finalize user's decision.
				
				replaceWindow.add(p1);
				replaceWindow.add(replacement);
				replaceWindow.add(ok);
				
				ok.addActionListener(new ButtonListener());
				
				replaceGUI.add(replaceWindow);
				replaceGUI.setVisible(true);
			} else if(event.getSource()==ok){		//The OK button during replace menu, will replace oldWord with newWord.
				int inputSelection = words.getSelectedIndex();
				String oldWord = words.getSelectedValue();
				wordList.remove(words.getSelectedValue());
				String newWord = replacement.getText();
				wordList.add(replacement.getText());
				wordsReplacedCount+=1;
				//wordList.remove(words.getSelectedValue());
				words.updateUI();
				
			} else if(event.getSource()==statistics){	//Statistic option that will execute statisticsGUI method.
				statisticsGUI();
			}

		} // end of actionPerformed method
	} // end of ButtonListener class
	
	private void statisticsGUI(){
		//Pop up box for statistics, setting it up with sizing and labels
		statisticGUI = new JDialog();
		statisticGUI.getSize(new Dimension(400,400));
		statisticGUI.setResizable(true);
		statisticGUI.setTitle("Statistic");
		statisticGUI.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);	
		JPanel statisticPanel = new JPanel();
		final int NUM_STATS=6;
		statisticPanel.setLayout(new GridLayout(NUM_STATS,1));
		JScrollPane scrollBar = new JScrollPane(statisticPanel);
		scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//The statistics of words count, lines count, words added count, words replaced count, and words ignored count.
		inFileWordsCount = InputPanel.getWordsCount();
		JLabel inFileWordsDisplay = new JLabel("The # of words from all input files was: " + inFileWordsCount);
		statisticPanel.add(inFileWordsDisplay); 
		linesReadCount = InputPanel.getLinesCount();
		JLabel linesReadDisplay = new JLabel("The # of lines read from all files was: " + linesReadCount);
		statisticPanel.add(linesReadDisplay);
		JLabel wordsAddedDisplay = new JLabel("The # of words added from input file was: " + wordsAddedCount);
		statisticPanel.add(wordsAddedDisplay);
		JLabel wordsReplacedDisplay = new JLabel("The # of words replaced in the input file is: " +wordsReplacedCount);
		statisticPanel.add(wordsReplacedDisplay);
		JLabel wordsIgnoredDisplay = new JLabel("The # of words ignored in the input file is: " + wordsIgnoredCount);
		statisticPanel.add(wordsIgnoredDisplay);

		//Finally we add the Panel to the GUI pop up window.
		statisticGUI.add(statisticPanel);
		statisticGUI.setVisible(true);
	}
	
	
}