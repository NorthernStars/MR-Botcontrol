package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.test.HardwareTestRunnable;
import de.northernstars.mr.botcontrol.core.test.TestTypes;

public class TabSectionTest extends TabSection {
	
	private static final Logger log = LogManager.getLogger();
	private HardwareTestRunnable mTestRunnable;

	public TabSectionTest() {
		super();
		
		// connect gui
		gui.btnQuickTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( mTestRunnable != null){
					mTestRunnable.stopTest();
				}
				
				mTestRunnable = new HardwareTestRunnable(TestTypes.QUICK);
				(new Thread(mTestRunnable)).start();
			}
		});
		
		gui.btnNormalTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( mTestRunnable != null){
					mTestRunnable.stopTest();
				}
				
				mTestRunnable = new HardwareTestRunnable(TestTypes.INTERVAL);
				(new Thread(mTestRunnable)).start();
			}
		});
		
		gui.btnCompetitionTestTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( mTestRunnable != null){
					mTestRunnable.stopTest();
				}
				
				mTestRunnable = new HardwareTestRunnable(TestTypes.COMPETITION);
				(new Thread(mTestRunnable)).start();
			}
		});
		
		gui.btnStopTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( mTestRunnable != null){
					mTestRunnable.stopTest();
				}
			}
		});
		
	}
	
	
	
	/**
	 * Sets enabled status of all children of a parent {@link Component}
	 * @param enable	{@code true} to enable all components, {@code false} otherwise
	 * @param parent	Parent {@link Component}
	 */
	private void setChildsEnabled(boolean enable, Component parent){
		if( parent instanceof Container ){
			Container container = (Container) parent;
			for( Component c : container.getComponents() ){
				setChildsEnabled(enable, c);
			}
		}
		
		if( parent != null ){
			parent.setEnabled(enable);
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		log.debug("Selected component {}", gui.tabbedPane.getSelectedComponent());
		if( gui.tabbedPane.getSelectedComponent() == gui.panelBotControl ){
			
			// check if still connected
			if( control.getSerial().isConnected() ){
				setChildsEnabled(true, null);
			} else {
				setChildsEnabled(false, null);
			}
			
		}
	}

}
