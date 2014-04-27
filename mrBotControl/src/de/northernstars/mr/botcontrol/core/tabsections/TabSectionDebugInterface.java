package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jftdiserial.core.Baudrates;
import de.hanneseilers.jftdiserial.core.DataBits;
import de.hanneseilers.jftdiserial.core.FTDISerial;
import de.hanneseilers.jftdiserial.core.Parity;
import de.hanneseilers.jftdiserial.core.SerialDevice;
import de.hanneseilers.jftdiserial.core.StopBits;
import de.hanneseilers.jftdiserial.core.interfaces.SerialDataRecievedListener;
import de.northernstars.mr.botcontrol.core.DebugCommands;
import de.northernstars.mr.botcontrol.core.MRBotControl;

public class TabSectionDebugInterface extends TabSection implements SerialDataRecievedListener {
	
	private static final Logger log = LogManager.getLogger();
	
	private DefaultComboBoxModel<String> libsModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> devsModel = new DefaultComboBoxModel<String>();
	
	private FTDISerial ftdi = new FTDISerial();	
	private static final byte transmissionEnd = (byte)('\n');
	private List<DebugCommands> commandQue = new ArrayList<DebugCommands>();
	private List<Byte> recievedData = new ArrayList<Byte>();

	public TabSectionDebugInterface(MRBotControl mrBotControl) {
		super(mrBotControl);
		
		ftdi.addSerialDataRecievedListener(this);
		
		gui.cmbDebugSerialLibraries.setModel(libsModel);
		gui.cmbDebugDevices.setModel(devsModel);
		gui.cmbDebugSerialLibraries.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					String libName = gui.cmbDebugSerialLibraries.getItemAt( gui.cmbDebugSerialLibraries.getSelectedIndex() );
					ftdi.selectLibByName(libName);
					updateDevicesModel();
				}
			}
		});
		gui.cmbDebugDevices.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					gui.txtDebugDevice.setText( gui.cmbDebugDevices.getItemAt(gui.cmbDebugDevices.getSelectedIndex()) );
				}
			}
		});
		
		gui.btnDebugConnect.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( ftdi.isConnected() ){

					// disconnect
					ftdi.disconnect();
					gui.btnDebugConnect.setText("Connect");
					
				} else {
					
					// get device by connectorName
					String devName = gui.txtDebugDevice.getText();
					Baudrates baudrate = gui.cmbDebugBaudrate.getItemAt(gui.cmbDebugBaudrate.getSelectedIndex());
					DataBits dataBits = DataBits.DATABITS_8;
					Parity parity = Parity.NONE;
					StopBits stopBits = StopBits.STOPBIT_1;
					
					for( SerialDevice dev : ftdi.getAvailableDevices() ){
						if( dev.toString().equals(devName) ){
							ftdi.setConnectionSettings(baudrate, dataBits, stopBits, parity, 100);
							if( ftdi.connect(dev) ){
								log.debug("Debug interface connected to {}", dev);
								gui.btnDebugConnect.setText("Disconnect");
								updateDeviceInfo();
								break;
							}
						}
					}			
				}
			}
		});

		gui.btnDebugLedStatus.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if( ftdi.isConnected() ){
					
					if( gui.btnDebugLedStatus.getText().contains("on") ){
						ftdi.write( DebugCommands.LED_STATUS_ON.CMD );
						gui.btnDebugLedStatus.setText("Status LED off");
					} else {
						ftdi.write( DebugCommands.LED_STATUS_OFF.CMD );
						gui.btnDebugLedStatus.setText("Status LED on");
					}
				}
			}
		});
	}
	
	/**
	 * Sends a {@link DebugCommands} or adds it to command que.
	 * @param command	{@link DebugCommands} to send
	 * 					or {@code null} to send next command in que.
	 */
	private void sendCommand(DebugCommands command){
		synchronized (commandQue) {
			
			if( command != null ){
				commandQue.add(command);
				log.debug("Added command {} to que", command);
			}
			else{
				if( commandQue.size() > 0 ){
					log.debug("Sending {}", commandQue.get(0));
					ftdi.write( commandQue.get(0).CMD );
				}
			}
			
		}
		
		if( command != null ){
			sendCommand(null);
		}
	}
	
	private void updateDeviceInfo(){
		if( ftdi.isConnected() ){			
			synchronized (commandQue) {
				
//				sendCommand(DebugCommands.BOT_ID);
				sendCommand(DebugCommands.BOT_PUBLISHER);
//				sendCommand(DebugCommands.BOT_PRODUCT);
//				sendCommand(DebugCommands.BOT_VERSION);
				
			}			
		}
	}
	
	private void updateLibsModel(){
		// update items list
		libsModel.removeAllElements();
		for( String lib : ftdi.getAvailableLibNames() ){
			libsModel.addElement(lib);
		}
		
		// set selected item
		gui.cmbDebugSerialLibraries.setSelectedItem( ftdi.getSelectedLibName() );
		
		// update devices list
		updateDevicesModel();
	}
	
	private void updateDevicesModel(){
		// update items list
		devsModel.removeAllElements();
		for( SerialDevice dev : ftdi.getAvailableDevices() ){
			devsModel.addElement(dev.toString());
		}
		
		// set manual device edit
		gui.txtDevice.setText( gui.cmbDevices.getItemAt(gui.cmbDevices.getSelectedIndex()) );
	}
	
	private void processCommand(DebugCommands command, List<Byte> data){
		
		log.debug("Command {} and data {}", command, data.toArray());
		
		String s = "";
		for( Byte b : data ){
			s += (char)((byte) b);
		}
		log.debug("string: {}", s);
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if( gui.tabbedPane.getSelectedComponent() == gui.panelDebugInterface ){
			updateLibsModel();
		}
	}

	@Override
	public void serialDataRecieved(byte data) {
		
		synchronized (commandQue) {			
			
			// check if transmission is complete
			if( data == transmissionEnd ){				
				// transmission complete > process command
				processCommand(commandQue.get(0), recievedData);
				
				// remove command from que
				commandQue.remove(0);
				recievedData.clear();				
			}
			else{				
				// add data to recieved data
				recievedData.add( data );				
			}
			
		}
	}

}
