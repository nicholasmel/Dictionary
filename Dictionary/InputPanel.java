/******************************************************************************************
 * Nicholas Mel
 * The InputPanel sets up the interface of the input tab of the GUI.
 * This area allows the user to import in .txt files to their corresponding section (wordList or dictList)
 ******************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.util.*;

public class InputPanel extends JPanel {
	//The input and output label files.
	private JLabel inLabel;
	private JLabel outLabel;
	
	//Text fields to enter the .txt file names.
	private JTextField f1;
	private JTextField f2;
	//Submit button after inputting the file names.
	private JButton submit;
	private Vector<String> wordList;
	private Vector<String> dictList;
	private String in;
	//Initializing the original user inputs.
	private String input1 = "";
	private String input2 = "";
	private String dict;
	private JLabel message;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	//Initializing the BufferedReader as null for both the files.
	private BufferedReader file1 = null;
	private BufferedReader file2 = null;
	
	public static String tmp;
	
	//Initializing statistics variables that will be used in DisplayPanel.java
	private static int inFileWordsCount = 0;
	private int wordsReplacedCount = 0;
	private int wordsAddedCount = 0;
	private int wordsRemovedCount = 0;
	private static int linesReadCount=0;
	private int wordsRepeatedBetweenFiles=0;
	
	
	public InputPanel(Vector<String> wordList, Vector<String> dictList) {
		this.wordList = wordList;
		this.dictList = dictList;

		
		//Main area where user will put in user input.
		inLabel = new JLabel("Enter input text file");
		f1 = new JTextField(25);;
		outLabel = new JLabel("Enter dictionary text file");
		f2 = new JTextField(25);
		message = new JLabel();

		submit = new JButton("SUBMIT");
		submit.addActionListener(new ButtonListener());

		// organize components here
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 1));
		p1.add(inLabel);
		p1.add(f1);

		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1));
		p2.add(outLabel);
		p2.add(f2);

		p3 = new JPanel();
		p3.setLayout(new GridLayout(2, 1));
		p3.add(submit);
		p3.add(message);

		p4 = new JPanel(new BorderLayout());
		p4.add(p1, BorderLayout.NORTH);
		p4.add(p2, BorderLayout.CENTER);
		p4.add(p3, BorderLayout.SOUTH);
		add(p4);
	}
	
	//ButtonListener that implements ActionListener to allow the user to press the submit button
	/**
	 * Nicholas Mel
	 * Main logic of this section where program will take in two user inputs
	 * which will correspond to the input file or the dictionary file then
	 * the program will add the words to the displayPanel (words and dictionary) respectively
	 * if a .txt file doesn't exist or user doesn't input anything an error will display.
	 * If a word exists in both files it will get removed.
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			in = f1.getText();
			dict = f2.getText();
			tmp = f2.getText();
			wordList.clear();
			dictList.clear();

			file1 = null;
			file2 = null;

			try {	//Reading the two files user has defined, one as input file the other is the dictionary file.
				file1 = new BufferedReader(new FileReader(new File(in)));
				file2 = new BufferedReader(new FileReader(new File(dict)));
			} catch (FileNotFoundException e) {

			}
			//If any of these conditions meet, then message will display an error.
			if (file1 == null || file2 == null || in == "" || dict == "") {
				message.setText("Error: input and dictionary files not found");
			} else {															//Else success and it will add the words from the files into their respective lists and incrementing 
				message.setText("Success! Input and dictionary file added");	//the statistic variables.
				try {
					input1 = file1.readLine();
					while (input1 != null && input1.length() > 0) {
						wordList.add(input1.toLowerCase().replaceAll("[^A-Za-z]+", ""));
						input1 = file1.readLine();
						inFileWordsCount+=1;
						linesReadCount+=1;
					}
					file1.close();
					input2 = file2.readLine();
					while (input2 != null && input2.length() > 0) {
						dictList.add(input2.toLowerCase().replaceAll("[^A-Za-z]+", ""));
						input2 = file2.readLine();
						linesReadCount+=1;
					}
					file2.close();
				} catch (IOException ex) {

				}//If there are duplicate words then it will get removed.
				for (int i = 0; i < wordList.size(); i++) {
					for (int j = 0; j < dictList.size(); j++) {
						if (wordList.get(i).equals(dictList.get(j))) {
							wordList.remove(i);
						}
					}
				}
			}
			
			f1.setText("");
			f2.setText("");

		} // end of actionPerformed method
	} // end of ButtonListener class
	
	public static int getWordsCount(){
		return inFileWordsCount;
	}
	public static int getLinesCount(){
		return linesReadCount;
	}
}