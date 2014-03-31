package de.northernstars.mr.botcontrol.core.tabsections;

import javax.swing.event.ChangeListener;

import de.northernstars.mr.botcontrol.core.MRBotControl;
import de.northernstars.mr.botcontrol.gui.MainFrame;

public abstract class TabSection implements ChangeListener {

	protected MRBotControl control;
	protected MainFrame gui;
	
	/**
	 * Consctructor
	 * @param mrBotControl	{@link MRBotControl} instance
	 */
	public TabSection(MRBotControl mrBotControl) {
		control = mrBotControl;
	}
	
}
