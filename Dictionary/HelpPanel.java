/******************************************************************************************
 * Nicholas Mel
 * The HelpPanel shows the text that guides the user in how to use this program.
 ******************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class HelpPanel extends JPanel {
	private JLabel title;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l3a;
	private JLabel l3b;
	private JLabel l3c;
	private JLabel l3d;
	private JLabel l4;
	private JLabel l5;
	private JPanel p1;

	// Constructor initializes components and organize them using certain
	// layouts
	public HelpPanel() {	//The help panel has text that tells the user how to use this program.
		title = new JLabel("WORD AND DICTIONARY PROGRAM HELP");
		l1 = new JLabel("In the upload tab, insert a valid input and dictionary .txt file name into the provided field. The ");
		l2 = new JLabel("display tab will display words not already in the dictionary, as well as new words from the input ");
		l3 = new JLabel(".txt file. The user can choose to add new words or save the new added words in the dictionary.");
		l3a= new JLabel("display tab will also have ignore, replace, store, add, statistic functions.");
		l3b= new JLabel("ignore function will allow user to ignore a word selected from word list.");
		l3c= new JLabel("replace function will allow user to replace an existing word in word List with new user input word.");
		l3d= new JLabel("statistics function will show statistics of wordsAddedCount, linesCount, wordsReplacedCount, wordsIgnoredCount, etc.");
		l4 = new JLabel("\n\n\n");
		l5 = new JLabel("Program created by Team IDK");
		
		// organize components here
		p1 = new JPanel();
		p1.setLayout(new GridLayout(10,1));
		p1.add(title);
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.add(l3a);
		p1.add(l3b);
		p1.add(l3c);
		p1.add(l3d);
		p1.add(l4);
		p1.add(l5);
		add(p1);

	}
}
