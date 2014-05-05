package de.northernstars.mr.botcontrol.core;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jftdiserial.core.FTDISerial;
import de.northernstars.mr.botcontrol.core.interfaces.CommandPackageRecievedListener;
import de.northernstars.mr.botcontrol.core.interfaces.GuiFrameListener;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;
import de.northernstars.mr.botcontrol.core.tabsections.TabSection;
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionBotControl;
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionDebugInterface;
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionSettings;
import de.northernstars.mr.botcontrol.gui.MainFrame;
import de.northernstars.mr.botcontrol.network.MRBotControlServer;

/**
 * Main class
 * @author Hannes Eilers
 *
 */
public class MRBotControl implements GuiFrameListener, CommandPackageRecievedListener {
	
	private static final Logger log = LogManager.getLogger();
	private MainFrame gui = null;
	private FTDISerial ftdi = new FTDISerial();
	private MRBotControlServer server;

	/**
	 * Constructor
	 */
	public MRBotControl() {
		MainFrame.showMainFrame(this);
		server = new MRBotControlServer();
		server.addCommandPackageRecievedListener(this);
		
		// start server
		new Thread(server).start();
	}
	
	/**
	 * Loads {@link TabSection} instances
	 */
	private void loadTabSections(){
		new TabSectionSettings(this);
		new TabSectionBotControl(this);
		new TabSectionDebugInterface(this);
	}
	
	/**
	 * @return the gui
	 */
	public MainFrame getGui() {
		return gui;
	}
	
	
	@Override
	public void frameLoaded(JFrame frame) {
		try{
			gui = (MainFrame) frame;
			loadTabSections();
		} catch(ClassCastException e){
			log.error("Could not cast " + frame + " to MainFrame.");
		}
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.debug("Started MRBotControl");
		new MRBotControl();
	}

	/**
	 * @return the ftdi
	 */
	public FTDISerial getFtdi() {
		return ftdi;
	}

	@Override
	public void commandPackageRecieved(BotProtocolSection[] sections) {
		log.debug("Sending {}", (Object[]) sections);
		new Thread( new DataWriter(getFtdi(), sections) ).start();
	}	

}
