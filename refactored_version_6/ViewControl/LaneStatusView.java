package ViewControl;
/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import Model.*;
public class LaneStatusView  implements Observer {

	private JPanel jp;

	private JLabel curBowler, pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.addObserver(psv);

		lv = new LaneView( lane, laneNum );
		lane.addObserver(lv);

		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
//		foul = new JLabel( " " );
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		String s="View Lane";
		viewLane = HelperView.makNewTab(s,buttonPanel);	
//		viewLane.addActionListener(this);
		viewLane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ( lane.isPartyAssigned() ) { 
					if ( laneShowing == false ) {
						lv.show();
						laneShowing=true;
					} else if ( laneShowing == true ) {
						lv.hide();
						laneShowing=false;
					}
				}
			}
			
		});
		
		s="Pinsetter";
		viewPinSetter = HelperView.makNewTab(s,buttonPanel);	
//		viewPinSetter.addActionListener(this);
		viewPinSetter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ( lane.isPartyAssigned() ) {
					if ( psShowing == false ) {
						psv.show();
						psShowing=true;
					} else if ( psShowing == true ) {
						psv.hide();
						psShowing=false;
					}
				}
			}
		});

		s="     ";
		maintenance = HelperView.makNewTab(s,buttonPanel);	
		maintenance.setBackground( Color.GREEN );
//		maintenance.addActionListener(this);
		maintenance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ( lane.isPartyAssigned() ) {
					lane.unPauseGame();
					maintenance.setBackground( Color.GREEN );
				}
			}
			
		});

		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );



		jp.add( cLabel );
		jp.add( curBowler );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if( o instanceof Lane) {
			Lane le = (Lane)o;
			if(lane.isGameFinished()){ // Start end of game routine
				EndGamePrompt egp = new EndGamePrompt( ((Bowler) le.getParty().getMembers().get(0)).getNick() + "'s Party" );
				int result = egp.getResult();
				egp.distroy();
				egp = null;	
				System.out.println("result was: " + result);
				if (result == 1) {					// yes, want to play again
					lane.resetScores();
					lane.resetBowlerIterator();
				}
				else if (result == 2) {// no, dont want to play another game
					Vector printVector;	
					EndGameReport egr = new EndGameReport( ((Bowler)le.getParty().getMembers().get(0)).getNick() + "'s Party", le.getParty());
					printVector = egr.getResult();
					Iterator scoreIt = le.getParty().getMembers().iterator();
					le.party = null;
					le.partyAssigned = false;
					
					int myIndex = 0;
					while (scoreIt.hasNext()){
						Bowler thisBowler = (Bowler)scoreIt.next();
						ScoreReport sr = new ScoreReport( thisBowler, lane.getCumulScore()[myIndex++], le.gameNumber );
						sr.sendEmail(thisBowler.getEmail());
						Iterator printIt = printVector.iterator();
						while (printIt.hasNext()){
							if (thisBowler.getNick() == (String)printIt.next()){
								System.out.println("Printing " + thisBowler.getNick());
								sr.sendPrintout();
							}
						}
					}
				}
			}	
			curBowler.setText(((Bowler) le.getBowler()).getNick());
			if (le.isMechanicalProblem()) {
				maintenance.setBackground(Color.RED);
			}
			if (lane.isPartyAssigned() == false) {
				viewLane.setEnabled(false);
				viewPinSetter.setEnabled(false);
			} else {
				viewLane.setEnabled(true);
				viewPinSetter.setEnabled(true);
			}
		}
		
		else if(o instanceof Pinsetter) {
			Pinsetter pe = (Pinsetter)arg;
			pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
		}
	}


}
