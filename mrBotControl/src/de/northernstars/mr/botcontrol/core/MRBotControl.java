package de.northernstars.mr.botcontrol.core;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.interfaces.GuiFrameListener;
import de.northernstars.mr.botcontrol.gui.MainFrame;

/**
 * Main class
 * @author Hannes Eilers
 *
 */
public class MRBotControl implements GuiFrameListener {
	
	private static final Logger log = LogManager.getLogger();
	private MainFrame gui = null;

	/**
	 * Constructor
	 */
	public MRBotControl() {
		// TODO Auto-generated constructor stub
		MainFrame.showMainFrame(this);
		log.debug("test");
	}
	
	@Override
	public void frameLoaded(JFrame frame) {
		try{
			gui = (MainFrame) frame;
		} catch(ClassCastException e){}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.debug("Started MRBotControl");
		new MRBotControl();
	}

	

}
