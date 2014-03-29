package de.northernstars.mr.botcontrol.core.interfaces;

import javax.swing.JFrame;

/**
 * Callback interface for listeners waiting for {@link JFrame} to load.
 * @author Hannes Eilers
 *
 */
public interface GuiFrameListener {

	/**
	 * Called if {@link JFrame} loaded
	 * @param frame
	 */
	public void frameLoaded(JFrame frame);
	
}
