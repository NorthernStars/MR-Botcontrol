package de.northernstars.mr.botcontrol.network;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public class CommandPackage {

	private int commandProtocolRevision = 2;;
	private List<BotProtocolSection> sections = new ArrayList<BotProtocolSection>();
	
	/**
	 * Default constructor
	 */
	public CommandPackage() {}
	
	/**
	 * Constructor
	 * @param aProtocolRevision		{@link Integer} protocol revision
	 */
	public CommandPackage(int aProtocolRevision){
		commandProtocolRevision = aProtocolRevision;
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
	public int getCommandProtocolRevision() {
		return commandProtocolRevision;
	}

	/**
	 * @param protocolRevision the protocolRevision to set
	 */
	public void setCommandProtocolRevision(int protocolRevision) {
		this.commandProtocolRevision = protocolRevision;
	}
	
	/**
	 * Converts {@link CommandPackage} to xml data {@link String}
	 * @return
	 */
	public String toXml(){
		StringWriter writer = new StringWriter();
		JAXB.marshal(this, writer);
		return writer.toString();
	}
	
	/**
	 * Converts xml data {@link String} to {@link CommandPackage}
	 * @param xml	xml data {@link String}
	 * @return		{@link CommandPackage}
	 */
	public static CommandPackage fromXML(String xml){
		StringReader reader = new StringReader(xml);
		return JAXB.unmarshal(reader, CommandPackage.class);
	}
	
}
