package core;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.List;

/**
 * Class for controlling bots
 * @author northernstars
 *
 */
public class BotControl {

	protected static final int MAX_COMMANDS_PER_PACKET = 6;
	protected final static char WIDTH = 8;	
	protected final static char CRCTABLE[] = {
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
	
	protected IRCom ircom = null;
	protected String comport = "COM1";
	protected int baudrate = 115200;
	
	/**
	 * Constructor
	 * @param comport
	 * @param baudrate
	 */
	public BotControl(String comport, int baudrate){
		ircom = new IRCom();
		this.comport = comport;
		this.baudrate = baudrate;
	}
	
	/**
	 * Function to show status message
	 * @param msg
	 */
	public void status(String msg) {
		System.out.println("> "+msg);
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
			status( "Connecting to "+comport);
			status( "\tbaudrate:"+baudrate );
			status( "\tdatabits:"+databits );
			status( "\tstopbits:"+stopbits );
			status( "\tparity:"+parity );
			status( "\tflowcontrol:"+flowcontrol );
			
			ircom.connect(comport, baudrate, databits, stopbits, parity, flowcontrol);
			
		} catch (NoSuchPortException | PortInUseException
				| UnsupportedCommOperationException | IOException e) {
			e.printStackTrace();
			throw new Exception( "COULD NOT CONNECT TO COM PORT" );
		}
	}
	
	/**
	 * Sends command string
	 * @param cmd
	 * @throws IOException 
	 */
	public void sendCommand(String cmd) throws IOException {
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
		if( ircom != null ){			
			ircom.write( command.toString() );
			status("[IRComm] Sending command: '" + command + "'.");
		}
	}
	
	/**
	 * Sends one command
	 * @param cmd
	 * @throws IOException
	 */
	public void sendCommand(BotCommand cmd) throws IOException{
		sendCommand( cmd.toString() );
	}
	
	/**
	 * Sends multiple commands
	 * @param commands
	 * @param repetitions
	 * @throws IOException 
	 */
	public void sendCommands(List<BotCommand> commands, int repetitions) throws IOException{
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
	 * ATTENTION: Did not split data packages
	 * @param cmd
	 * @throws IOException 
	 */
	protected void sendCommand(List<BotCommand> commands) throws IOException{
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
	
	/**
	 * Generates crc value
	 * @param message
	 * @return
	 */
	protected int crc(String message) {
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
	
}
