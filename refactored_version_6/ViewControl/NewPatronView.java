package ViewControl;
/* AddPartyView.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a patron
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.text.*;

public class NewPatronView {

	private int maxSize;

	private JFrame win;
	private JButton abort, finished;
	private JLabel nickLabel, fullLabel, emailLabel;
	private JTextField nickField, fullField, emailField;
	private String nick, full, email;
	NewPatronView new_patron_view = this;

	private boolean done;

	private AddPartyView addParty;

	public NewPatronView(AddPartyView v) {

		addParty=v;	
		done = false;

		win = new JFrame("Add Patron");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Patron Panel
		JPanel patronPanel = new JPanel();
		patronPanel.setLayout(new GridLayout(3, 1));
		patronPanel.setBorder(new TitledBorder("Your Info"));

		String s="Nick Name";
		JTextField nickField = HelperView.makeNewPatronPanel(s,patronPanel);

		s="Full Name";
		JTextField fullField = HelperView.makeNewPatronPanel(s,patronPanel);

		s="E-Mail";
		JTextField emailField = HelperView.makeNewPatronPanel(s,patronPanel);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		s="Add Patron";
		finished = HelperView.makNewTab(s, buttonPanel);
//		finished.addActionListener(this);
		finished.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nick = nickField.getText();
				full = fullField.getText();
				email = emailField.getText();
				done = true;
				addParty.updateNewPatron( new_patron_view );
				win.hide();
			}
			
		});

		s="Abort";
		abort = HelperView.makNewTab(s, buttonPanel);
//		abort.addActionListener(this);
		abort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				done = true;
				win.hide();
			}
			
		});


		// Clean up main panel
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		HelperView.setWindowAlignment(win);

	}

//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource().equals(abort)) {
//			done = true;
//			win.hide();
//		}
//
//		if (e.getSource().equals(finished)) {
//			nick = nickField.getText();
//			full = fullField.getText();
//			email = emailField.getText();
//			done = true;
//			addParty.updateNewPatron( this );
//			win.hide();
//		}
//
//	}

	public boolean done() {
		return done;
	}

	public String getNick() {
		return nick;
	}

	public String getFull() {
		return full;
	}

	public String getEmail() {
		return email;
	}

}
