package core;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Class for connecting to serial port
 * @author northernstars
 *
 */
public class IRCom {

	private SerialPort serialPort;
	private InputStream in = null;
	private OutputStream out = null;
	@SuppressWarnings("unused")
	private String comport = "COM1";
	
	/**
	 * Connect to device
	 * @param comport
	 * @param baudrate
	 * @param databits
	 * @param stopbits
	 * @param parity
	 * @param flowcontrol
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws IOException
	 */
	public void connect(String comport, int baudrate, int databits, int stopbits, int parity, int flowcontrol)
			throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException{
		
		this.comport = comport;
		
		// get comport identifier
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");
		
		// check if port is free
		if( portIdentifier.isCurrentlyOwned() ){
			throw new PortInUseException();
		}
		
		// Open comport
		CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
        
        if ( commPort instanceof SerialPort){
            serialPort = (SerialPort) commPort;
            
            // set parameter
            serialPort.setSerialPortParams( baudrate, databits, stopbits, parity );
            serialPort.setFlowControlMode(flowcontrol);            
            
            try {
				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
			} catch (IOException e) {
				throw new IOException("Could not create streams.");
			}   
        }
	}
	
	/**
	 * Disconnects from serial device
	 */
	public void disconnect(){
		if( serialPort != null ){			
			in = null;
			out = null;
			serialPort.close();
		}
	}	
	
	
	/**
	 * Reads string from serial port
	 * @return
	 * @throws IOException
	 */
	public String read() throws IOException{
		byte[] buffer = new byte[1024];
		int len = -1;
		
		// read from stream
		if( in != null ){
			len = in.read(buffer);
		}
		else{
			throw new IOException("No input stream opened");
		}
		
		return new String(buffer, 0, len);
	}
	
	/**
	 * Writes data to serial port
	 * @param data
	 * @throws IOException
	 */
	public void write(String data) throws IOException{	
		if( out != null ){
			out.write( data.getBytes() );
		}
		else{
			throw new IOException("No output stream opened");
		}
	}
	
	@SuppressWarnings("unchecked")
	static List<String> listPorts()
    {
		List<String> portList = new ArrayList<String>();
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            portList.add( portIdentifier.getName() );
        }  
        return portList;
    }

}
