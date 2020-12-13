/******************************************************************************************************
 * Nicholas Mel
 * The main driver program that creates the GUI, the program will allow user to take in two user input
 * then store them either as a dictList or wordList in the displayPanel.
 * User can then choose to add a word from the wordList to the dictList or ignore it.
 * User can also replace an existing word from wordList with a new word the user specifies.
 * User can open up the statistic tab to show the current stats of the program.
 * There is also a help panel created for the user to read through to understand
 * how to use this program
 ***************************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Dictionary extends JApplet {
	//Initializing Applet and setting dimensions.
	private int APPLET_WIDTH = 800, APPLET_HEIGHT = 500;
	private JTabbedPane tPane;

	// separate files needed for all of these
	private InputPanel inputPanel;
	private DisplayPanel displayPanel;
	private HelpPanel helpPanel;
	//private SavePanel savePanel;
	private Vector<String> wordList = new Vector<String>();
	private Vector<String> dictList = new Vector<String>();
	private String dict;
	public String tmp;

	public void init() {
		// list of flights to be used in every panel

		inputPanel = new InputPanel(wordList, dictList);
		helpPanel = new HelpPanel();
		displayPanel = new DisplayPanel(wordList, dictList);

		// create a tabbed pane with two tabs
		tPane = new JTabbedPane();
		tPane.addTab("Upload", inputPanel);
		tPane.addTab("Display", displayPanel);
		tPane.addTab("Help", helpPanel);
		//tPane.addTab("Statistics", statsPanel);

		getContentPane().add(tPane);
		setSize(APPLET_WIDTH, APPLET_HEIGHT); // set Applet size
	}
}

//multiple in/out files
//add/ignore word