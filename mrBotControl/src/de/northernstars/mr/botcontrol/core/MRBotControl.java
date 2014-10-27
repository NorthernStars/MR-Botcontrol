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
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionTest;
import de.northernstars.mr.botcontrol.gui.MainFrame;
import de.northernstars.mr.botcontrol.network.MRBotControlServer;

/**
 * Main class
 * @author Hannes Eilers
 *
 */
public class MRBotControl implements GuiFrameListener, CommandPackageRecievedListener {
	
	private static final Logger log = LogManager.getLogger();
	private static MRBotControl sInstance;
	
	private MainFrame gui = null;
	private FTDISerial ftdi = new FTDISerial();
	private DataWriter writer;
	private MRBotControlServer server;
	

	/**
	 * Constructor
	 */
	public MRBotControl() {
		MainFrame.showMainFrame(this);
		
		// create server and data writer
		server = new MRBotControlServer();
		server.addCommandPackageRecievedListener(this);
		
		writer = new DataWriter(ftdi);
		
		// start server and data writer
		new Thread(server).start();
		new Thread(writer).start();
		
		sInstance = this;
	}
	
	/**
	 * @return	{@link MRBotControl} instance
	 */
	public static MRBotControl getInstance(){
		if( sInstance == null ){
			sInstance = new MRBotControl();
		}
		
		return sInstance;
	}
	
	/**
	 * Loads {@link TabSection} instances
	 */
	private void loadTabSections(){
		new TabSectionSettings();
		new TabSectionBotControl();
		new TabSectionDebugInterface();
		new TabSectionTest();
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
	
	@Override
	public void frameCloseing(JFrame frame) {
		if( writer != null ){
			writer.setActive(false);
			server.setActive(false);
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
		if( writer != null && writer.isActive() ){
			log.debug("Sending {}", (Object[]) sections);
			writer.putDataInQue(sections);
		}
	}

	public DataWriter getWriter() {
		return writer;
	}

}
