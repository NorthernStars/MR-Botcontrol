package de.northernstars.mr.botcontrol.network;

import javax.xml.bind.JAXB;

public class MRBotControlConnectPackage {

	private int protocolRevision = 2;
	private boolean connect = true;
	
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
	public boolean isConnect() {
		return connect;
	}
	/**
	 * @param connect the connect to set
	 */
	public void setConnect(boolean connect) {
		this.connect = connect;
	}
	
	public String toXML(){
		String xml = null;
		JAXB.marshal(this, xml);
		return xml;
	}
	
}
