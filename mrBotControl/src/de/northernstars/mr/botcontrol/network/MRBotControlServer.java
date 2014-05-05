package de.northernstars.mr.botcontrol.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.interfaces.CommandPackageRecievedListener;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;


public class MRBotControlServer implements Runnable {
	
	public static int socketPort = 9080;	
	private static final Logger log = LogManager.getLogger();
	
	private DatagramSocket socket;
	private boolean active = false;
	
	private List<CommandPackageRecievedListener> commandRecievedListener = new ArrayList<CommandPackageRecievedListener>();
	
	/**
	 * Default constructor
	 */
	public MRBotControlServer() {
		try {
			socket = new DatagramSocket(socketPort, InetAddress.getLocalHost());
			active = true;
			log.debug("Started datagram socket at {}:{}", InetAddress.getLocalHost(), socketPort);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adding {@link CommandPackageRecievedListener}
	 * @param listener	{@link CommandPackageRecievedListener} to add
	 * @return			{code true} if successfulle, {@code false} otherwise
	 */
	public boolean addCommandPackageRecievedListener(CommandPackageRecievedListener listener){
		return commandRecievedListener.add(listener);
	}
	
	/**
	 * Removes a {@link CommandPackageRecievedListener}
	 * @param listener	{@link CommandPackageRecievedListener} to remove
	 * @return			{code true} if successfulle, {@code false} otherwise
	 */
	public boolean removeCommandPackageRecievedListener(CommandPackageRecievedListener listener){
		return commandRecievedListener.remove(listener);
	}
	
	/**
	 * Removes all {@link CommandPackageRecievedListener}.
	 */
	public void removeAllCommandPackageRecievedListener(){
		commandRecievedListener.clear();
	}
	
	/**
	 * Notifies all listener about a new command set.
	 * @param commandPackage	{@link MRBotControlCommandPackage}
	 */
	private void notifyCommandPackaeRecievedListener(MRBotControlCommandPackage commandPackage){
		// generate array if sections
		BotProtocolSection[] sections = new BotProtocolSection[ commandPackage.getSections().size() ];
		int i = 0;
		for( BotProtocolSection section : commandPackage.getSections() ){
			sections[i] = section;
			i++;
		}
		
		// notify listener
		for( CommandPackageRecievedListener listener : commandRecievedListener ){
			listener.commandPackageRecieved(sections);
		}
	}
	
	@Override
	public void run() {
		
		while( active ){			
			try {
				
				// recieve data
				DatagramPacket packet = new DatagramPacket( new byte[1024], 1024 );
				socket.receive( packet );
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				String data = new String( packet.getData(), 0, packet.getLength() );
				
				// check for connection request
				MRBotControlConnectPackage connectDataPackage = JAXB.unmarshal(data, MRBotControlConnectPackage.class);
				if( connectDataPackage != null ){
					log.debug("{}:{} connecting with protocol revision {}.", address, port, connectDataPackage.getProtocolRevision());
					if( connectDataPackage.getProtocolRevision() != 2 ){
						connectDataPackage.setProtocolRevision(2);
						connectDataPackage.setConnect(false);
					}
					
					// send connectDataPackage back
					byte[] buf = connectDataPackage.toXML().getBytes();
					socket.send( new DatagramPacket(buf, buf.length, address, port) );
				}
				else{
					
					// convert data to 
					MRBotControlCommandPackage commandPackage = JAXB.unmarshal(data, MRBotControlCommandPackage.class);
					if( commandPackage != null && commandPackage.getProtocolRevision() == 2  ){
						log.debug("Recieved data from {}:{} - protocol revision {}.", address, port, commandPackage.getProtocolRevision());
						notifyCommandPackaeRecievedListener(commandPackage);
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				active = false;
			}
			
		}
	}
	
	/**
	 * Disconnect server socket
	 */
	public void disconnect(){
		setActive(false);
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
