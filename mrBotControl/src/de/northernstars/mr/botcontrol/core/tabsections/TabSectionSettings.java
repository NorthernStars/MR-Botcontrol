package de.northernstars.mr.botcontrol.core.tabsections;

import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public TabSectionSettings(MRBotControl mrBotControl) {
		super(mrBotControl);
		
		gui = control.getGui();		
		gui.panelSettings.addFocusListener(this);		
		gui.cmbSerialLibraries.setModel(libsModel);
		gui.cmbSerialLibraries.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if( arg0.getStateChange() == ItemEvent.SELECTED ){
					String libName = gui.cmbSerialLibraries.getItemAt( gui.cmbSerialLibraries.getSelectedIndex() );
					control.getFtdi().selectLibByName(libName);
				}
			}
		});
		
		updateLibsModel();
	}
	
	private void updateLibsModel(){
		// update items list
		libsModel.removeAllElements();
		for( String lib : control.getFtdi().getAvailableLibNames() ){
			libsModel.addElement(lib);
		}
		
		// set selected item
		control.getGui().cmbSerialLibraries.setSelectedItem( control.getFtdi().getSelectedLibName() );
	}

	@Override
	public void focusGained(FocusEvent e) {
		updateLibsModel();
	}

	@Override
	public void focusLost(FocusEvent e) {}

}
