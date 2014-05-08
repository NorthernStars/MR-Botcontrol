package de.northernstars.mr.botcontrol.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.network.CommandPackage;
import de.northernstars.mr.botcontrol.network.MRBotControlServer;
import de.northernstars.mr.botcontrol.network.ProtocolRequestPackage;
import de.northernstars.mr.botcontrol.network.protocol.NetBotProtocolCommand;
import de.northernstars.mr.botcontrol.network.protocol.NetBotProtocolCommands;
import de.northernstars.mr.botcontrol.network.protocol.NetBotProtocolSection;

public class NetworkTest {
	
	private static final Logger log = LogManager.getLogger();
	private DatagramSocket socket;
	
	public NetworkTest() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void test(){
		try{
			
			log.debug("Starting Network Test");
			InetAddress address = InetAddress.getLocalHost();
			
			// send protocol request
			ProtocolRequestPackage req = new ProtocolRequestPackage();
			req.setProtocolRevision(2);
			send(req.toXML(), address, MRBotControlServer.socketPort);
			
			// check recieved data
			req = ProtocolRequestPackage.fromXml( recieve() );
			if( req.isAccepted() ){				
				log.debug("Protocol revision {} accepted.", req.getProtocolRevision());
			} else {
				log.error("Protocl revision not accepted, use revision {} instead!", req.getProtocolRevision());
			}
			
			// sending data
			CommandPackage cmd = new CommandPackage( req.getProtocolRevision() );
			NetBotProtocolSection section = new NetBotProtocolSection(0);
			section.add( new NetBotProtocolCommand(NetBotProtocolCommands.MOTOR_LEFT, 100) );
			cmd.addSection(section);
			
			send( cmd.toXml(), address, MRBotControlServer.socketPort );
			
		} catch (UnknownHostException err){
			err.printStackTrace();
		}
	}
	
	private void send(String data, InetAddress address, int port){
		try{
			log.debug("SEND {}:{}\n{}", address, port, data);
			byte[] bData = data.getBytes();
			DatagramPacket packet = new DatagramPacket(bData, bData.length, address, port);
			socket.send(packet);
		} catch (IOException err){
			err.printStackTrace();
		}
		
	}
	
	private String recieve(){
		byte[] buf = new byte[2048];
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		
		try {
			socket.receive(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.debug("RECIEVE {}:{}\n{}", p.getAddress(), p.getPort(), new String(p.getData(), 0, p.getLength()));
		return new String(p.getData(), 0, p.getLength());
	}

	public static void main(String[] args) {
		NetworkTest test = new NetworkTest();
		test.test();
	}
	
}
