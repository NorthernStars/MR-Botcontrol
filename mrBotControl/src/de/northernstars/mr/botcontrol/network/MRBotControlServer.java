package de.northernstars.mr.botcontrol.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.interfaces.CommandPackageRecievedListener;
import de.northernstars.mr.botcontrol.network.protocol.NetBotProtocol;
import de.northernstars.mr.botcontrol.network.protocol.NetBotProtocolSection;


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
	 * @param commandPackage	{@link CommandPackage}
	 */
	private void notifyCommandPackageRecievedListener(CommandPackage commandPackage){
		// generate array if sections
		NetBotProtocolSection[] sections = new NetBotProtocolSection[ commandPackage.getSections().size() ];
		int i = 0;
		for( NetBotProtocolSection section : commandPackage.getSections() ){
			sections[i] = section;
			i++;
		}
		
		// notify listener
		for( CommandPackageRecievedListener listener : commandRecievedListener ){
			listener.commandPackageRecieved( NetBotProtocol.toBotProtocolSections(sections) );
		}
	}
	
	@Override
	public void run() {
		
		log.debug("Datagram socket server running.");
		
		while( active ){			
			try {
				
				// recieve data
				DatagramPacket packet = new DatagramPacket( new byte[1024], 1024 );
				socket.receive( packet );
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				String data = new String(packet.getData(), 0, packet.getLength());
				
				// check for connection request
				ProtocolRequestPackage connectDataPackage = ProtocolRequestPackage.fromXml(data);
				if( connectDataPackage != null && connectDataPackage.getProtocolRevision() >= 0 ){
					log.debug("{}:{} connecting with protocol revision {}.", address, port, connectDataPackage.getProtocolRevision());
					if( connectDataPackage.getProtocolRevision() != 2 ){
						connectDataPackage.setProtocolRevision(2);
						connectDataPackage.setAccepted(false);
					}
					
					// send connectDataPackage back
					connectDataPackage.setAccepted(true);
					byte[] buf = connectDataPackage.toXML().getBytes();
					socket.send( new DatagramPacket(buf, buf.length, address, port) );
				}
				else{
					
					// convert data to 
					CommandPackage commandPackage = CommandPackage.fromXML(data);
					if( commandPackage != null && commandPackage.getCommandProtocolRevision() == 2  ){
						log.debug("Recieved data from {}:{} - protocol revision {}.", address, port, commandPackage.getCommandProtocolRevision());
						notifyCommandPackageRecievedListener(commandPackage);
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				active = false;
			}
			
		}
		
		log.debug("Datagram socket server stopped.");
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
