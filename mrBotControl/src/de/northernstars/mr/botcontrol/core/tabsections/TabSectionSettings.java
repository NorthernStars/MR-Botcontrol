package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jserial.core.Baudrates;
import de.hanneseilers.jserial.core.DataBits;
import de.hanneseilers.jserial.core.Parity;
import de.hanneseilers.jserial.core.SerialDevice;
import de.hanneseilers.jserial.core.StopBits;

/**
 * Section Settings
 * @author Hannes Eilers
 *
 */
public class TabSectionSettings extends TabSection {

	private static final Logger log = LogManager.getLogger();
	
	private DefaultComboBoxModel<String> libsModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> devsModel = new DefaultComboBoxModel<String>();
	
	public TabSectionSettings() {
		super();
			
		gui.cmbSerialLibraries.setModel(libsModel);
		gui.cmbDevices.setModel(devsModel);
		
		// add listener
		if( !control.isAutoConnect() ){
			
			gui.cmbSerialLibraries.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if( arg0.getStateChange() == ItemEvent.SELECTED ){
						String libName = gui.cmbSerialLibraries.getItemAt( gui.cmbSerialLibraries.getSelectedIndex() );
						control.getSerial().selectLibByName(libName);
						updateDevicesModel();
					}
				}
			});
			
			gui.btnConnect.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if( control.getSerial().isConnected() ){
						
						control.getSerial().disconnect();
						gui.lblStatus.setText("Disconnected");
						log.debug("Disconnected from device.");
						
					} else{
						
						// get device by connectorName
						String devName = gui.txtDevice.getText();
						Baudrates baudrate = gui.cmbBaudrate.getItemAt(gui.cmbBaudrate.getSelectedIndex());
						DataBits dataBits = gui.cmbDataBits.getItemAt(gui.cmbDataBits.getSelectedIndex());
						Parity parity = gui.cmbParity.getItemAt(gui.cmbParity.getSelectedIndex());
						StopBits stopBits = gui.cmbStopBits.getItemAt(gui.cmbStopBits.getSelectedIndex());
						
						for( SerialDevice dev : control.getSerial().getAvailableDevices() ){
							if( dev.toString().equals(devName) ){
								control.getSerial().setConnectionSettings(baudrate, dataBits, stopBits, parity, 100);
								if( control.getSerial().connect(dev) ){
									gui.lblStatus.setText("Connected to " + dev.toString());
									log.debug("Connected to {}", dev);
									break;
								}
							}
						}					
						
					}
					
					updateGuiComponentsEnabled();
				}
			});
			
			gui.btnRefresh.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if( control.getSerial().isConnected() ){
						gui.btnConnect.doClick();
					}
					
					updateDevicesModel();
				}
			});
			
		}
		
		gui.cmbDevices.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					gui.txtDevice.setText( gui.cmbDevices.getItemAt(gui.cmbDevices.getSelectedIndex()) );
				}
			}
		});
		
		// update models
		updateLibsModel();
	}
	
	private void updateGuiComponentsEnabled(){
		boolean en = true;
		String status = "Disconnected";
		String cmdConnect = "Connect";
		if( control.getSerial().isConnected() || control.isAutoConnect() ){
			en = false;
			status = "Connected";
			cmdConnect = "Disconnect";
		}
		
		// enable/disable gui components
		gui.cmbBaudrate.setEnabled(en);
		gui.cmbDataBits.setEnabled(en);
		gui.cmbDevices.setEnabled(en);
		gui.cmbParity.setEnabled(en);
		gui.cmbStopBits.setEnabled(en);
		gui.cmbSerialLibraries.setEnabled(en);
		gui.txtDevice.setEnabled(en);
		
		if( control.isAutoConnect() ){
			gui.btnConnect.setEnabled(en);
			gui.btnRefresh.setEnabled(en);
		}
		
		// set texts
		gui.lblStatus.setText(status);
		gui.btnConnect.setText(cmdConnect);
	}
	
	private void updateLibsModel(){
		if( !control.getSerial().isConnected() ){
			// update items list
			libsModel.removeAllElements();
			for( String lib : control.getSerial().getAvailableLibNames() ){
				libsModel.addElement(lib);
			}
			
			// set selected item
			gui.cmbSerialLibraries.setSelectedItem( control.getSerial().getSelectedLibName() );
			
			// update devices list
			updateDevicesModel();
		}
		
		updateGuiComponentsEnabled();
	}
	
	synchronized private void updateDevicesModel(){
		// update items list
		devsModel.removeAllElements();
		for( SerialDevice dev : control.getSerial().getAvailableDevices() ){
			devsModel.addElement(dev.toString());
		}
		
		// set manual device edit
		gui.txtDevice.setText( gui.cmbDevices.getItemAt(gui.cmbDevices.getSelectedIndex()) );		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if( gui.tabbedPane.getSelectedComponent() == gui.panelSettings ){
			updateLibsModel();
			updateGuiComponentsEnabled();
		}
	}

}
