package de.northernstars.mr.botcontrol.network;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public class MRBotControlCommandPackage {

	private int botID = 0;;
	private int protocolRevision = 2;;
	private List<BotProtocolSection> sections = new ArrayList<BotProtocolSection>();
	
	/**
	 * Default constructor
	 */
	public MRBotControlCommandPackage() {}
	
	/**
	 * Constructor
	 * @param aBotID				{@link Integer} bot id
	 * @param aProtocolRevision		{@link Integer} protocol revision
	 */
	public MRBotControlCommandPackage(int aBotID, int aProtocolRevision){
		botID = aBotID;
		protocolRevision = aProtocolRevision;
	}
	
	/**
	 * Adds a section
	 * @param section	{@link BotProtocolSection} to add
	 */
	public boolean addSection(BotProtocolSection section){
		return sections.add(section);
	}
	
	/**
	 * Removes a {@link BotProtocolSection} from list.
	 * @param index		{@link Integer} index to remove
	 */
	public BotProtocolSection removeSections(int index){
		return sections.remove(index);
	}
	
	/**
	 * @return the botID
	 */
	public int getBotID() {
		return botID;
	}
	/**
	 * @param botID the botID to set
	 */
	public void setBotID(int botID) {
		this.botID = botID;
	}
	/**
	 * @return the sections
	 */
	public List<BotProtocolSection> getSections() {
		return sections;
	}
	/**
	 * @param sections the sections to set
	 */
	public void setSections(List<BotProtocolSection> sections) {
		this.sections = sections;
	}

	/**
	 * @return the protocolRevision
	 */
	public int getProtocolRevision() {
		return protocolRevision;
	}

	/**
	 * @param protocolRevision the protocolRevision to set
	 */
	public void setProtocolRevision(int protocolRevision) {
		this.protocolRevision = protocolRevision;
	}
	
	public String toXml(){
		String xml = null;
		JAXB.marshal(this, xml);
		return xml;
	}
	
}
