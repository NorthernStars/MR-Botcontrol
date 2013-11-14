package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import gui.MainFrame;

public class BotControl implements ActionListener {

	public static final String version = "0.1a";
	private static final int MAX_COMMANDS_PER_PACKET = 6;
	private final static char WIDTH = 8;	
	private final static char CRCTABLE[] = {
		0x00,0xd8,0x68,0xb0,0xd0,0x08,0xb8,0x60,0x78,0xa0,0x10,0xc8,0xa8,0x70,0xc0,0x18,      /* 000-015 */
		0xf0,0x28,0x98,0x40,0x20,0xf8,0x48,0x90,0x88,0x50,0xe0,0x38,0x58,0x80,0x30,0xe8,      /* 016-031 */
		0x38,0xe0,0x50,0x88,0xe8,0x30,0x80,0x58,0x40,0x98,0x28,0xf0,0x90,0x48,0xf8,0x20,      /* 032-047 */
		0xc8,0x10,0xa0,0x78,0x18,0xc0,0x70,0xa8,0xb0,0x68,0xd8,0x00,0x60,0xb8,0x08,0xd0,      /* 048-063 */
		0x70,0xa8,0x18,0xc0,0xa0,0x78,0xc8,0x10,0x08,0xd0,0x60,0xb8,0xd8,0x00,0xb0,0x68,      /* 064-079 */
		0x80,0x58,0xe8,0x30,0x50,0x88,0x38,0xe0,0xf8,0x20,0x90,0x48,0x28,0xf0,0x40,0x98,      /* 080-095 */
		0x48,0x90,0x20,0xf8,0x98,0x40,0xf0,0x28,0x30,0xe8,0x58,0x80,0xe0,0x38,0x88,0x50,      /* 096-111 */
		0xb8,0x60,0xd0,0x08,0x68,0xb0,0x00,0xd8,0xc0,0x18,0xa8,0x70,0x10,0xc8,0x78,0xa0,      /* 112-127 */
		0xe0,0x38,0x88,0x50,0x30,0xe8,0x58,0x80,0x98,0x40,0xf0,0x28,0x48,0x90,0x20,0xf8,      /* 128-143 */
		0x10,0xc8,0x78,0xa0,0xc0,0x18,0xa8,0x70,0x68,0xb0,0x00,0xd8,0xb8,0x60,0xd0,0x08,      /* 144-159 */
		0xd8,0x00,0xb0,0x68,0x08,0xd0,0x60,0xb8,0xa0,0x78,0xc8,0x10,0x70,0xa8,0x18,0xc0,      /* 160-175 */
		0x28,0xf0,0x40,0x98,0xf8,0x20,0x90,0x48,0x50,0x88,0x38,0xe0,0x80,0x58,0xe8,0x30,      /* 176-191 */
		0x90,0x48,0xf8,0x20,0x40,0x98,0x28,0xf0,0xe8,0x30,0x80,0x58,0x38,0xe0,0x50,0x88,      /* 192-207 */
		0x60,0xb8,0x08,0xd0,0xb0,0x68,0xd8,0x00,0x18,0xc0,0x70,0xa8,0xc8,0x10,0xa0,0x78,      /* 208-223 */
		0xa8,0x70,0xc0,0x18,0x78,0xa0,0x10,0xc8,0xd0,0x08,0xb8,0x60,0x00,0xd8,0x68,0xb0,      /* 224-239 */
		0x58,0x80,0x30,0xe8,0x88,0x50,0xe0,0x38,0x20,0xf8,0x48,0x90,0xf0,0x28,0x98,0x40       /* 240-255 */
	};
	
	private IRCom ircom = null;
	private MainFrame frame = null;
	private String comport = "COM1";
	private int baudrate = 115200;
	
	/**
	 * Constructor
	 */
	public BotControl(String comport, int baudrate){
		ircom = new IRCom();
		this.comport = comport;
		this.baudrate = baudrate;
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
	}
	
	/**
	 * Connects to device
	 * @param comport
	 * @param baudrate
	 * @param databits
	 * @param stopbits
	 * @param parity
	 * @param flowcontrol
	 * @throws Exception
	 */
	public void connect(int databits, int stopbits, int parity, int flowcontrol) throws Exception{
		// try to open serial com port
		try {
			// print connection data
			System.out.println( "> Connecting to "+comport);
			System.out.println( "\tbaudrate:"+baudrate );
			System.out.println( "\tdatabits:"+databits );
			System.out.println( "\tstopbits:"+stopbits );
			System.out.println( "\tparity:"+parity );
			System.out.println( "\tflowcontrol:"+flowcontrol );
			
			this.ircom.connect(comport, baudrate, databits, stopbits, parity, flowcontrol);
			
		} catch (NoSuchPortException | PortInUseException
				| UnsupportedCommOperationException | IOException e) {
			e.printStackTrace();
			throw new Exception( "COULD NOT CONNECT TO COM PORT" );
		}
	}
	
	/**
	 * Generates crc value
	 * @param message
	 * @return
	 */
	private int crc(String message) {
		int data;
		int remainder = 0;
		int byt;

		for (byt = 0; byt < message.length(); ++byt) {
			data = (message.charAt(byt) ^ (remainder >> (WIDTH - 8)));
			data = data & 0xFF;
			remainder = CRCTABLE[data] ^ (remainder << 8);
			remainder = remainder & 0xFF;
		}

		return remainder;
	}
	
	/**
	 * Sends command string
	 * @param cmd
	 */
	public void sendCommand(String cmd) {
		StringBuffer command = new StringBuffer("S");
		
		// add data length
		if( cmd.length() < 16 )
			command.append('0');		
		command.append( Integer.toString(cmd.length(), 16) );
		
		// add command
		command.append( cmd );
		
		// add crc
		int crc = crc(cmd);		
		if( crc < 16 ) 
			command.append('0');		
		command.append(Integer.toString(crc, 16));
		
		// transmit data
		System.out.println("[IRComm] Sending command: '" + command + "'.");
		if( ircom != null ){
			
			try {
				
				ircom.write( command.toString() );
				if( frame.panelStatus.getBackground().getRed() > 0 )
					frame.panelStatus.setBackground( Color.GREEN );
				else
					frame.panelStatus.setBackground( Color.RED );
				frame.panelStatus.repaint();
				
			} catch (IOException e) {
				System.err.println("Could not write to serial device");
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Sends multiple commands
	 * @param commands
	 * @param repetitions
	 */
	public void sendCommands(List<BotCommand> commands, int repetitions){
		for( int r = 0; r < repetitions; r++ ){
			
	  		if( commands.size() > MAX_COMMANDS_PER_PACKET ){
	  			
	  			for( int i = 0; i < commands.size(); i += MAX_COMMANDS_PER_PACKET ){
	  				if (i + MAX_COMMANDS_PER_PACKET > commands.size() )
	  					sendCommand( commands.subList(i, commands.size()) );
	  				else
	  					sendCommand( commands.subList(i, i + MAX_COMMANDS_PER_PACKET) );
	  			}
	  			
	  		}
	  		else{
	  			
	  			sendCommand( commands );
	  			
	  		}
	  		
		}
	}
	
	/**
	 * Sends list BotCommands
	 * @param cmd
	 */
	private void sendCommand(List<BotCommand> commands){
		StringBuilder cmd = new StringBuilder();
		
		// Check list size
		if( commands.size() == 0 ){
			return;
		}
		
		// Build command list
		cmd.append("lst");		
		for( BotCommand b : commands ){
			cmd.append( "," +  b );
		}
		
		// Send command
		sendCommand( cmd.toString() );		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		// check for button
		if( btn == frame.btnConnect  ){
			
			// connect/disconnect to device
			if( btn.getText().equals( "Connect" ) ){
				
				String comport = frame.txtComport.getText();
				this.comport = comport;
				try {
					connect( SerialPort.DATABITS_8,  SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE );
					frame.lblStatus.setText( "Connected to "+comport );
					btn.setText( "Disconnect" );
				} catch (Exception e1) {
					frame.lblStatus.setText( "Could not connect to device!" );
				}
				
			}
			else{
				ircom.disconnect();
				
				frame.lblStatus.setText( "Disconnected" );
				frame.btnConnect.setText( "Connect" );
				
			}
			
		}
		else if( btn == frame.btnSend ){
			
			// Get data
			int botID = frame.cmbBotID.getSelectedIndex();
			int leftSpeed = Integer.valueOf( frame.spLeftSpeed.getValue().toString() );
			int rightSpeed = Integer.valueOf( frame.spRightSpeed.getValue().toString() );
			
			// Create command
			BotCommand command = new BotCommand(botID, leftSpeed, rightSpeed);
			List<BotCommand> commands = new ArrayList<BotCommand>();
			commands.add( command );
			
			// Send command
			sendCommands( commands, 1 );			
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
		BotControl control = new BotControl(comport, baudrate);
		control.initGui();

	}

}
