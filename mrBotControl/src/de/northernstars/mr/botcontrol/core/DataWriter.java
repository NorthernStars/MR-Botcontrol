package de.northernstars.mr.botcontrol.core;

import de.hanneseilers.jftdiserial.core.FTDISerial;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocol;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

/**
 * Class for writing data to ftdi device using async. thread.
 * @author hannes
 *
 */
public class DataWriter implements Runnable{
	private FTDISerial ftdi;
	private BotProtocolSection[] sections;
	
	public DataWriter(FTDISerial aFtdi, BotProtocolSection[] aSections) {
		ftdi = aFtdi;
		sections = aSections;
	}
	
	@Override
	public void run() {
		ftdi.write(
				BotProtocol.generateDataFromSections(sections) );
	}		
}
