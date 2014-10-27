package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.MRBotControl;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public class TabSectionTest extends TabSection {
	
	private static final Logger log = LogManager.getLogger();

	public TabSectionTest(MRBotControl mrBotControl) {
		super(mrBotControl);
		
		// connect gui
		gui.btnQuickTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				BotProtocolSection section = new BotProtocolSection(getBotID());
				send( new BotProtocolSection[]{section} );
			}
		});
		
		gui.btnNormalTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		gui.btnCompetitionTestTest.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 * Sends array of {@link BotProtocolSection} to remote device
	 * @param aSections	Array of {@link BotProtocolSection}
	 */
	private synchronized void send(BotProtocolSection[] aSections){
		if( control.getWriter() != null ){
			control.getWriter().putDataInQue(aSections);
		}
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
		
		parent.setEnabled(enable);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		log.debug("Selected component {}", gui.tabbedPane.getSelectedComponent());
		if( gui.tabbedPane.getSelectedComponent() == gui.panelBotControl ){
			
			// check if still connected
			if( control.getFtdi().isConnected() ){
				setChildsEnabled(true, null);
			} else {
				setChildsEnabled(false, null);
			}
			
		}
	}

}
