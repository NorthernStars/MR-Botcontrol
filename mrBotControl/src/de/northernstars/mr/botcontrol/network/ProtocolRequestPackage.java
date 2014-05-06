package de.northernstars.mr.botcontrol.network;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

public class ProtocolRequestPackage {

	private int protocolRevision = -1;
	private boolean accepted = false;
	
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
	/**
	 * @return the connect
	 */
	public boolean isAccepted() {
		return accepted;
	}
	/**
	 * @param connect the connect to set
	 */
	public void setAccepted(boolean connect) {
		this.accepted = connect;
	}
	
	/**
	 * Converts {@link ProtocolRequestPackage} to xml data{@link String}
	 * @return	xml data {@link String}
	 */
	public String toXML(){
		StringWriter writer = new StringWriter();
		JAXB.marshal(this, writer);
		return writer.toString();
	}
	
	/**
	 * Converts xml data {@link String} to {@link ProtocolRequestPackage}
	 * @param xml	xml data {@link String}
	 * @return		{@link ProtocolRequestPackage}
	 */
	public static ProtocolRequestPackage fromXml(String xml){
		StringReader reader = new StringReader(xml);
		return JAXB.unmarshal(reader, ProtocolRequestPackage.class);
	}
	
}
