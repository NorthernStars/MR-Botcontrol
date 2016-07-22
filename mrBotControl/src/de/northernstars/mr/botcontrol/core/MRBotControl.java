package de.northernstars.mr.botcontrol.core;

import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jserial.core.Baudrates;
import de.hanneseilers.jserial.core.DataBits;
import de.hanneseilers.jserial.core.JSerial;
import de.hanneseilers.jserial.core.Parity;
import de.hanneseilers.jserial.core.SerialDevice;
import de.hanneseilers.jserial.core.StopBits;
import de.northernstars.mr.botcontrol.core.interfaces.CommandPackageRecievedListener;
import de.northernstars.mr.botcontrol.core.interfaces.GuiFrameListener;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;
import de.northernstars.mr.botcontrol.core.tabsections.TabSection;
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionBotControl;
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
	private JSerial serial = new JSerial();
	private DataWriter writer;
	private MRBotControlServer server;
	private boolean mQuietMode = false;
	private boolean mAutoConnect = false;

	/**
	 * Constructor
	 * @param quiet
	 * @param autoConnect
	 */
	public MRBotControl(boolean quiet, boolean autoConnect){
		this(quiet, autoConnect, null);
	}

	/**
	 * Constructor
	 * @param quiet
	 * @param autoConnect
	 * @param serialDevice
	 */
	public MRBotControl(boolean quiet, boolean autoConnect, String serialDevice) {
		sInstance = this;
		mQuietMode = quiet;
		mAutoConnect = autoConnect;
		
		log.info("Starting with quiet mode = {}, auto connect = {}, device = {}", mQuietMode, mAutoConnect, serialDevice);
		
		// check if to show gui
		if( !quiet ){
			MainFrame.showMainFrame(this);
		} else {
			autoConnect = true;
		}
		
		// check if to auto connect
		if( autoConnect ){
			serial.selectFirstAvailableLib();
			List<SerialDevice> vDevices = serial.getAvailableDevices();
			log.debug("# of serial devices = {}", vDevices.size());
			
			// search for device to connect to
			boolean vFound = false;
			for( SerialDevice vDevice : vDevices ){
				if( (serialDevice != null && vDevice.toString().equals(serialDevice))
						|| serialDevice == null ){
					log.debug("Auto connecting to {}", vDevice);
					getSerial().setConnectionSettings(Baudrates.BAUD_115200, DataBits.DATABITS_8, StopBits.STOPBIT_1, Parity.NONE, 100);
					getSerial().connect(vDevice);
					vFound = true;
					break;
				}
			}	
			
			// check if connected
			if( !vFound ){
				log.error("Device {} not found in list of {} devices!", serialDevice, vDevices.size());
			}
			
		}
		
		// create server and data writer
		server = new MRBotControlServer();
		server.addCommandPackageRecievedListener(this);
		
		writer = new DataWriter(serial);
		
		// start server and data writer
		new Thread(server).start();
		new Thread(writer).start();
		
	}
	
	/**
	 * @return	{@link MRBotControl} instance
	 */
	public static MRBotControl getInstance(){
		if( sInstance == null ){
			sInstance = new MRBotControl(false, false);
		}
		
		return sInstance;
	}
	
	/**
	 * Loads {@link TabSection} instances
	 */
	private void loadTabSections(){
		new TabSectionSettings();
		new TabSectionBotControl();
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
			if( mAutoConnect )
				gui.tabbedPane.setSelectedIndex(1);
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
		
		try {
			
			// parsing command line options
			Options vOptions = new Options();
			vOptions.addOption("q", false, "Quiet start (without GUI and auto connect on, see -a).");
			vOptions.addOption("d", true, "Device to connect to (if autoconnect used, see -a), 'ttyUSB0' for example.");
			vOptions.addOption("a", false, "Auto connect: Uses first available lib fo connection. Tries to connect to first device or to device given (see -d).");
			vOptions.addOption("p", true, "Socket port number to use");
			
			CommandLineParser vParser = new DefaultParser();
			CommandLine vArgs;
		
			vArgs = vParser.parse(vOptions, args);
			
			// checking connection settings
			if( vArgs.hasOption('p') )
				MRBotControlServer.socketPort = Integer.parseInt(vArgs.getOptionValue('p'));
			
			// starting main class
			if( vArgs.hasOption('d') )
				new MRBotControl(vArgs.hasOption('q'), vArgs.hasOption('a'), vArgs.getOptionValue('d'));
			else
				new MRBotControl(vArgs.hasOption('q'), vArgs.hasOption('a'));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
	}

	/**
	 * @return the jSerial object
	 */
	public JSerial getSerial() {
		return serial;
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

	public boolean isQuietMode() {
		return mQuietMode;
	}

	public boolean isAutoConnect() {
		return mAutoConnect;
	}

}
