package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jftdiserial.core.FTDISerial;
import de.northernstars.mr.botcontrol.core.MRBotControl;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocol;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public class TabSectionBotControl extends TabSection {
	
	private static final Logger log = LogManager.getLogger();

	public TabSectionBotControl(MRBotControl mrBotControl) {
		super(mrBotControl);
		gui.tabbedPane.addChangeListener(this);
		
		gui.btnMotorsForeward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					int rightSpeed = Integer.parseInt( gui.txtMotorRight.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.leftWheelSpeed(leftSpeed) );
					section.add( BotProtocol.rightWheelSpeed(rightSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorsBackward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					int rightSpeed = Integer.parseInt( gui.txtMotorRight.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.leftWheelSpeed(-leftSpeed) );
					section.add( BotProtocol.rightWheelSpeed(-rightSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorsStop.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int botID = Integer.parseInt( gui.txtBotID.getText() );					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.stopWheels() );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorLeftForeward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.leftWheelSpeed(leftSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorLeftBackward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.leftWheelSpeed(-leftSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorRightForeward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int rightSpeed = Integer.parseInt( gui.txtMotorRight.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.rightWheelSpeed(rightSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnMotorRightBackward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int rightSpeed = Integer.parseInt( gui.txtMotorRight.getText() );
					int botID = Integer.parseInt( gui.txtBotID.getText() );
					
					BotProtocolSection section = new BotProtocolSection(botID);
					section.add( BotProtocol.rightWheelSpeed(-rightSpeed) );
					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
	}
	
	/**
	 * Sends array of {@link BotProtocolSection} to remote device
	 * @param aSections	Array of {@link BotProtocolSection}
	 */
	private synchronized void send(BotProtocolSection[] aSections){
		log.debug("Sending {}", (Object[]) aSections);
		new Thread( new DataWriter(control.getFtdi(), aSections) ).start();
	}
	
	/**
	 * Private class for writing data to ftdi device using async. thread.
	 * @author Hannes Eilers
	 *
	 */
	private class DataWriter implements Runnable{
		private FTDISerial ftdi;
		private BotProtocolSection[] sections;
		
		public DataWriter(FTDISerial aFtdi, BotProtocolSection[] aSections) {
			ftdi = aFtdi;
			sections = aSections;
		}
		
		@Override
		public void run() {
			ftdi.write(
					BotProtocol.generateDataFromSections(sections) );
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
				setChildsEnabled(true, gui.panelBotControl);
			} else {
				setChildsEnabled(false, gui.panelBotControl);
			}
			
		}
	}

	
	
}
