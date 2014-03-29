package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jftdiserial.core.Baudrates;
import de.hanneseilers.jftdiserial.core.DataBits;
import de.hanneseilers.jftdiserial.core.Parity;
import de.hanneseilers.jftdiserial.core.SerialDevice;
import de.hanneseilers.jftdiserial.core.StopBits;
import de.northernstars.mr.botcontrol.core.MRBotControl;

/**
 * Section Settings
 * @author Hannes Eilers
 *
 */
public class TabSectionSettings extends TabSection {

	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger();
	
	private DefaultComboBoxModel<String> libsModel = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> devsModel = new DefaultComboBoxModel<String>();
	
	public TabSectionSettings(MRBotControl mrBotControl) {
		super(mrBotControl);
		
		gui = control.getGui();		
		gui.panelSettings.addFocusListener(this);		
		gui.cmbSerialLibraries.setModel(libsModel);
		gui.cmbDevices.setModel(devsModel);
		gui.cmbSerialLibraries.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					String libName = gui.cmbSerialLibraries.getItemAt( gui.cmbSerialLibraries.getSelectedIndex() );
					control.getFtdi().selectLibByName(libName);
					updateDevicesModel();
				}
			}
		});
		gui.cmbDevices.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					gui.txtDevice.setText( gui.cmbDevices.getItemAt(gui.cmbDevices.getSelectedIndex()) );
				}
			}
		});
		
		gui.btnConnect.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( control.getFtdi().isConnected() ){
					
					control.getFtdi().disconnect();
					
				} else{
					
					// get device by name
					String devName = gui.txtDevice.getText();
					Baudrates baudrate = gui.cmbBaudrate.getItemAt(gui.cmbBaudrate.getSelectedIndex());
					DataBits dataBits = gui.cmbDataBits.getItemAt(gui.cmbDataBits.getSelectedIndex());
					Parity parity = gui.cmbParity.getItemAt(gui.cmbParity.getSelectedIndex());
					StopBits stopBits = gui.cmbStopBits.getItemAt(gui.cmbStopBits.getSelectedIndex());
					
					for( SerialDevice dev : control.getFtdi().getAvailableDevices() ){
						if( dev.toString().equals(devName) ){
							control.getFtdi().setConnectionSettings(baudrate, dataBits, stopBits, parity, 100);
							if( control.getFtdi().connect(dev) ){
								break;
							}
						}
					}					
					
				}
				
				updateGuiComponentsEnabled();
			}
		});
		
		// update models
		updateLibsModel();
	}
	
	private void updateGuiComponentsEnabled(){
		boolean en = true;
		String status = "Disconnected";
		String cmdConnect = "Connect";
		if( control.getFtdi().isConnected() ){
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
		gui.txtDevice.setEnabled(en);
		
		// set texts
		gui.lblStatus.setText(status);
		gui.btnConnect.setText(cmdConnect);
	}
	
	private void updateLibsModel(){
		// update items list
		libsModel.removeAllElements();
		for( String lib : control.getFtdi().getAvailableLibNames() ){
			libsModel.addElement(lib);
		}
		
		// set selected item
		control.getGui().cmbSerialLibraries.setSelectedItem( control.getFtdi().getSelectedLibName() );
		
		// update devices list
		updateDevicesModel();
		updateGuiComponentsEnabled();
	}
	
	private void updateDevicesModel(){
		// update items list
		devsModel.removeAllElements();
		for( SerialDevice dev : control.getFtdi().getAvailableDevices() ){
			devsModel.addElement(dev.toString());
		}
		
		// set manual device edit
		gui.txtDevice.setText( gui.cmbDevices.getItemAt(gui.cmbDevices.getSelectedIndex()) );
	}

	@Override
	public void focusGained(FocusEvent e) {
		updateLibsModel();
		updateGuiComponentsEnabled();
	}

	@Override
	public void focusLost(FocusEvent e) {}

}
