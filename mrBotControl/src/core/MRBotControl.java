package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import gnu.io.SerialPort;
import gui.BotNumbers;
import gui.MainFrame;

public class MRBotControl extends BotControl implements ActionListener {

	public static final String version = "0.1a";
	private static final int testDelay = 200;
	
	
	private MainFrame frame = null;
	
	
	/**
	 * Constructor
	 */
	public MRBotControl(String comport, int baudrate){
		super(comport, baudrate);
	}
	
	/**
	 * Initiates GUI
	 */
	public void initGui(){
		// Create gui
		frame = new MainFrame();
		frame.setVisible(true);
		
		if( comport != null ){
			frame.txtComport.setText(comport);
		}
		
		// Add listener
		frame.btnConnect.addActionListener(this);
		frame.btnSend.addActionListener(this);
		frame.btnTestBots.addActionListener(this);
		frame.btnForeward.addActionListener(this);
		frame.btnBackward.addActionListener(this);
		frame.btnSpinCW.addActionListener(this);
		frame.btnSpinCCW.addActionListener(this);
	}
	
	@Override
	public void status(String msg) {
		super.status(msg);
		frame.lblStatus.setText(msg);
	}
	
	/**
	 * Sends a list of bot commands
	 * @param commands
	 */
	public void send(List<BotCommand> commands){
		try {
			
			// Send command
			sendCommands(commands, 1);
			
			// Toggle status panel
			if( frame.panelStatus.getBackground().getRed() > 0 )
				frame.panelStatus.setBackground( Color.GREEN );
			else
				frame.panelStatus.setBackground( Color.RED );
			frame.panelStatus.repaint();
			
		} catch (IOException e) {
			System.err.println("Could not send data");
		}
	}
	
	/**
	 * Sends one command
	 * @param command
	 */
	public void send(BotCommand command){
		try {
			
			// Send command
			sendCommand(command);
			
			// Toggle status panel
			if( frame.panelStatus.getBackground().getRed() > 0 )
				frame.panelStatus.setBackground( Color.GREEN );
			else
				frame.panelStatus.setBackground( Color.RED );
			frame.panelStatus.repaint();
			
		} catch (IOException e) {
			System.err.println("Could not send data");
		}
	}
	
	/**
	 * Test all bots
	 */
	public void testBots(){
		int leftSpeed = 31;
		int rightSpeed = -31;
		
		for( int i=0; i <= BotNumbers.values().length; i++ ){

			// Create and send command
			send( new BotCommand(i, leftSpeed, rightSpeed) );
			
			// Show number of bot
			status( "Testing bot "+i );
			
			// Wait
			try {
				Thread.sleep(testDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		// Get data from gui
		int botID = frame.cmbBotID.getSelectedIndex();
		int leftSpeed = Integer.valueOf( frame.spLeftSpeed.getValue().toString() );
		int rightSpeed = Integer.valueOf( frame.spRightSpeed.getValue().toString() );
		
		// CONNECT / DISCONNECT
		if( btn == frame.btnConnect  ){			
			// connect/disconnect to device
			if( btn.getText().equals( "Connect" ) ){				
				String comport = frame.txtComport.getText();
				this.comport = comport;
				try {
					connect( SerialPort.DATABITS_8,  SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE );
					status( "Connected to "+comport );
					btn.setText( "Disconnect" );
				} catch (Exception e1) {
					frame.lblStatus.setText( "Could not connect to device!" );
				}				
			}
			else{
				ircom.disconnect();				
				status( "Disconnected" );
				frame.btnConnect.setText( "Connect" );				
			}			
		}
		
		// SEND SPEED
		else if( btn == frame.btnSend ){
			// Create and send command
			send( new BotCommand(botID, leftSpeed, rightSpeed) );			
		}
		
		// TEST BOTS
		else if( btn == frame.btnTestBots ){			
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					testBots();
				}
			} ).start();			
		}
		
		// FOREWARD
		else if( btn == frame.btnForeward ){
			leftSpeed = rightSpeed = BotCommand.MAX_SPEED/2;
			send( new BotCommand(botID, leftSpeed, rightSpeed) );			
		}
		
		// BACKWARD
		else if( btn == frame.btnBackward ){
			leftSpeed = rightSpeed = (BotCommand.MAX_SPEED/2)*-1;
			send( new BotCommand(botID, leftSpeed, rightSpeed) );			
		}
		
		// SPIN CLOCKWISE
		else if( btn == frame.btnSpinCW ){
			leftSpeed = BotCommand.MAX_SPEED/2;
			rightSpeed = (BotCommand.MAX_SPEED/2)*-1;
			send( new BotCommand(botID, leftSpeed, rightSpeed) );			
		}
		
		// SPIN COUNTERCLOCKWISE
		else if( btn == frame.btnSpinCCW ){
			leftSpeed = (BotCommand.MAX_SPEED/2)*-1;
			rightSpeed = BotCommand.MAX_SPEED/2;
			send( new BotCommand(botID, leftSpeed, rightSpeed) );			
		}
		
		
	}
	
	
	
	/**
	 * MAIN FUNCTION
	 * @param args
	 */
	public static void main(String[] args) {
		
		String comport = "/dev/ttyUSB0";
		int baudrate = 115200;
		
		// Generate command line options
		Options lvOptions = new Options();
		lvOptions.addOption("h", "help", false, "Shows help");
		lvOptions.addOption("p", "port", true, "Device port for serial connection");
		lvOptions.addOption("b", "baud", true, "Baudrate for connection");
		
		// Generate command line parser
		CommandLineParser lvParser = new BasicParser();
		
		// Parse command lines
		CommandLine lvCmd = null;
		try {
			lvCmd = lvParser.parse(lvOptions, args);
		} catch (ParseException e1) {
			System.err.println("Could not parse command line options. Use defaults.");
		}
		
		// get command line arguments
		if( lvCmd != null){
			
			if( lvCmd.hasOption('h') ){
				// show help
				HelpFormatter lvFormater = new HelpFormatter();
				lvFormater.printHelp("mrBotControl", lvOptions);
			}	
			
			if( lvCmd.hasOption('p') ){
				// update comport
				comport = lvCmd.getOptionValue('p');
			}
			
			if( lvCmd.hasOption('b') ){
				// update baudrate
				comport = lvCmd.getOptionValue('b');
			}
			
			
		}
		
		// Create main object and gui
		final MRBotControl control = new MRBotControl(comport, baudrate);
		control.initGui();
		
		// quick and dirty receiver
		new Thread( new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {

				DatagramSocket vSocket = null;
				try {
					vSocket = new DatagramSocket( 5555 );
					vSocket.setSoTimeout( 0 );
				} catch ( SocketException e ) {
					e.printStackTrace();
				}
				
				String vData = null;
				String[] vPart;
				
				DatagramPacket vDatagrammPacketFromServer = new DatagramPacket( new byte[16384], 16384 );
				
				long vLastTime = System.nanoTime();
				List<BotCommand> vListOfCommands = new ArrayList<BotCommand>();
				
				while(true){
					
					try {
						
						vSocket.receive( vDatagrammPacketFromServer );
						
						vData = new String( vDatagrammPacketFromServer.getData(), 0, vDatagrammPacketFromServer.getLength() );
						vPart = vData.split("\\|");
						vListOfCommands.add( new BotCommand( Integer.parseInt( vPart[0] ), 31 * Integer.parseInt( vPart[1] )/100, 31 * Integer.parseInt( vPart[2] )/100 ) );
						
					} catch ( Exception e ) {
						e.printStackTrace();
					}
					
					if( System.nanoTime() - vLastTime > 25000000 ){
						
						System.out.println(vListOfCommands);
						control.send( vListOfCommands );
						vListOfCommands.clear();
						vLastTime = System.nanoTime();
						
					}
					
				}
			}
		} ).start();

	}

}
