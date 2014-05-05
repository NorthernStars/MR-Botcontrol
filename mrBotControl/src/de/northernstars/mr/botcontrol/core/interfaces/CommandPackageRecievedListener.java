package de.northernstars.mr.botcontrol.core.interfaces;

import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public interface CommandPackageRecievedListener {

	/**
	 * Called if new data recieved.
	 * @param sections	Array of {@link BotProtocolSection}
	 */
	public void commandPackageRecieved(BotProtocolSection[] sections);
	
}
