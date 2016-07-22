package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.MRBotControl;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocol;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;
import de.northernstars.mr.botcontrol.core.protocol.LEDAnimations;
import de.northernstars.mr.botcontrol.core.protocol.LEDS;

public class TabSectionBotControl extends TabSection {
	
	private static final Logger log = LogManager.getLogger();

	public TabSectionBotControl() {
		super();
		gui.tabbedPane.addChangeListener(this);
		
		gui.btnMotorsForeward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					int rightSpeed = Integer.parseInt( gui.txtMotorRight.getText() );
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
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
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
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
				BotProtocolSection section = new BotProtocolSection(getBotID());
				section.add( BotProtocol.stopWheels() );					
				send( new BotProtocolSection[]{section} );
			}
		});
		
		gui.btnMotorLeftForeward.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int leftSpeed = Integer.parseInt( gui.txtMotorLeft.getText() );
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
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
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
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
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
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
					
					BotProtocolSection section = new BotProtocolSection(getBotID());
					section.add( BotProtocol.rightWheelSpeed(-rightSpeed) );					
					send( new BotProtocolSection[]{section} );
				} catch (NumberFormatException err)
				{
					log.warn("Number format Exception {}", err.getLocalizedMessage());
				}
			}
		});
		
		gui.btnLEDStatus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean on = false;
				if( gui.btnLEDStatus.getText().contains("on") ){
					on = true;
					gui.btnLEDStatus.setText("LED Status off");
				} else {
					gui.btnLEDStatus.setText("LED Status on");
				}
				
				BotProtocolSection section = new BotProtocolSection(getBotID());
				section.add( BotProtocol.ledStatus(on) );
				send( new BotProtocolSection[]{section} );
			}
		});
		
		gui.btnLEDAllOn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean on = false;
				if( gui.btnLEDAllOn.getText().contains("on") ){
					on = true;
					gui.btnLEDAllOn.setText("All RGB LEDs off");
				} else{
					gui.btnLEDAllOn.setText("All RGB LEDs on");
				}
				
				BotProtocolSection section = new BotProtocolSection(getBotID());
				section.add( BotProtocol.ledRGBAll(on) );
				send( new BotProtocolSection[]{section} );				
			}
		});
		
		gui.cmbLEDAnimation.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				LEDAnimations animation = gui.cmbLEDAnimation.getItemAt( gui.cmbLEDAnimation.getSelectedIndex() );
				BotProtocolSection section = new BotProtocolSection(getBotID());
				section.add( BotProtocol.ledAnimation(animation) );
				send( new BotProtocolSection[]{section} );				
			}
		});
		
		gui.btnLED1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLEDColor(LEDS.LED1);
			}
		});
		
		gui.btnLED2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLEDColor(LEDS.LED2);
			}
		});
		
		gui.btnLED3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLEDColor(LEDS.LED3);
			}
		});
		
		gui.btnLED4.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLEDColor(LEDS.LED4);
			}
		});
		
		gui.btnChangeBotID.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int newBotID = Integer.parseInt(gui.txtBotIDChangeTo.getText());
					BotProtocolSection section = new BotProtocolSection(getBotID());
					section.add( BotProtocol.changeBotID(newBotID) );
					section.add( BotProtocol.changeBotID(newBotID) );
					section.add( BotProtocol.changeBotID(newBotID) );
					send( new BotProtocolSection[]{section} );
					gui.txtBotID.setText( Integer.toString(newBotID) );
				} catch (NumberFormatException err){
					log.warn("Can not convert new bot ID to number.");
				}
			}
		});
		
		gui.btnTestBots.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {					
					@Override
					public void run() {
						for( int id=0; id<30; id++ ){
							BotProtocolSection section = new BotProtocolSection(id);
							section.add( BotProtocol.ledAnimation(LEDAnimations.STROBE) );
							send( new BotProtocolSection[]{section} );
							gui.lblTestBotsStatus.setText( Integer.toString(id) );
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						gui.lblTestBotsStatus.setText( "" );
					}
				}).start();
			}
		});
		
	}
	
	/**
	 * Sends led color command
	 * @param led	{@link LEDS} to set color of
	 */
	private void setLEDColor(LEDS led){
		int[] color = getLEDColor(led);
		BotProtocolSection section = new BotProtocolSection(getBotID());
		section.add( BotProtocol.setLEDColorRed(led, color[0]) );
		section.add( BotProtocol.setLEDColorGreen(led, color[1]) );
		section.add( BotProtocol.setLEDColorBlue(led, color[2]) );
		send( new BotProtocolSection[]{section} );
	}
	
	/**
	 * Gets {@link Integer} values of rgb field
	 * @param led	{@link LEDS} to get rgb value from
	 * @return		{@link Integer} array with {r, g, b}
	 */
	private int[] getLEDColor(LEDS led){
		
		JTextField txtR, txtG, txtB;
		
		switch(led){
		case LED1:
			txtR = gui.txtLED1Red;
			txtG = gui.txtLED1Green;
			txtB = gui.txtLED1Blue;
			break;
			
		case LED2:
			txtR = gui.txtLED2Red;
			txtG = gui.txtLED2Green;
			txtB = gui.txtLED2Blue;
			break;
			
		case LED3:
			txtR = gui.txtLED3Red;
			txtG = gui.txtLED3Green;
			txtB = gui.txtLED3Blue;
			break;
			
		default:
			txtR = gui.txtLED4Red;
			txtG = gui.txtLED4Green;
			txtB = gui.txtLED4Blue;
			break;
		
		}
		
		try{
			int r = Integer.parseInt(txtR.getText());
			int g = Integer.parseInt(txtG.getText());
			int b = Integer.parseInt(txtB.getText());
			
			return new int[]{r, g, b};
		} catch (NumberFormatException e){
			log.warn("Can not convert rgb value to number.");
		}
		
		return new int[]{0,0,0};
	}
	
	/**
	 * @return {@link Integer} bot id from gui
	 */
	private int getBotID(){
		try{
			return Integer.parseInt( gui.txtBotID.getText() );
		} catch (NumberFormatException e){
			log.warn("Can not convert bot id to number.");
			return 0;
		}
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
			if( control.getSerial().isConnected() ){
				setChildsEnabled(true, gui.panelBotControl);
			} else {
				setChildsEnabled(false, gui.panelBotControl);
			}
			
		}
	}

	public static boolean isAutostop() {
		return MRBotControl.getInstance().getGui().chkAutoStop.isSelected();
	}

	
	
}
