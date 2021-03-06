package de.northernstars.mr.botcontrol.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hanneseilers.jserial.core.JSerial;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocol;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommand;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommands;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;
import de.northernstars.mr.botcontrol.core.tabsections.TabSectionBotControl;

/**
 * Class for writing data to ftdi device using async. thread.
 * @author hannes
 *
 */
public class DataWriter implements Runnable{
	
	private static final Logger log = LogManager.getLogger();
	
	private BlockingQueue<BotProtocolSection> mSectionQue = new ArrayBlockingQueue<>(100, true);
	private Map<Integer, Long> mLastMessage = new HashMap<Integer, Long>();
	public static final long timeout = 75;
	
	private JSerial ftdi;
	private boolean active = true;
	
	/**
	 * Constructor
	 * @param aFtdi	{@link JSerial} device
	 */
	public DataWriter(JSerial aFtdi) {
		ftdi = aFtdi;
	}
	
	/**
	 * Put new data into que
	 * @param sections	Array of {@link BotProtocolSection}
	 * @return			{@code true} if adding data to que was successfull, {@code false} othwersie.
	 */
	public boolean putDataInQue(BotProtocolSection[] sections){
		for( BotProtocolSection s : sections ){
			synchronized (mSectionQue) {
				if( !mSectionQue.offer(s) ){
					return false;
				}			
			}
		}
		return true;
	}
	
	@Override
	public void run() {
		
		List<BotProtocolSection> vSectionsList = new ArrayList<BotProtocolSection>();
		BotProtocolSection vSection;
		
		log.debug("Data writer started");
		
		while(active){
			
			// adding new messages to list
			synchronized (mSectionQue) {
				while( !mSectionQue.isEmpty() ){					
					vSection = mSectionQue.poll();
					if( vSection != null ){
						vSectionsList.add(vSection);
						synchronized (mLastMessage) {
							mLastMessage.put( vSection.getBotID(), System.currentTimeMillis() );
						}
						log.debug("Added message for {} {}", vSection.getBotID(), vSection);
					}
				}
			}
			
			// checking all messages in list
			synchronized (mLastMessage) {
				for( int vID : mLastMessage.keySet() ){				
					// check if message is outdated
					if( TabSectionBotControl.isAutostop()
							&& mLastMessage.get(vID)+timeout < System.currentTimeMillis()){
						
						// message outdated > sending stop
						log.debug("Outdated message for bot {}", vID);
						vSection = new BotProtocolSection(vID);
						vSection.add( new BotProtocolCommand(BotProtocolCommands.MOTOR_STOP, 2) );
						vSectionsList.add( vSection );
					}				
				}
				
				// check if to remove messageS from history
				Integer vIdSet[] = mLastMessage.keySet().toArray(new Integer[0]);
				for( int vID : vIdSet ){					
					if(mLastMessage.get(vID)+3*timeout < System.currentTimeMillis()){
						mLastMessage.remove(vID);
					}
				}
			}
			
			// sending all messages
			if( !vSectionsList.isEmpty() ){
				for(BotProtocolSection s : vSectionsList){
					log.debug("SEND {}:{}", s.getBotID(), s);
					
					// adjust speed
					float viMaxSpeed = Float.parseFloat(
							MRBotControl.getInstance().getGui().txtMaxSpeed.getText() );
					for( BotProtocolCommand cmd : s.getCommandList() ){
						if( cmd.getCommand() == BotProtocolCommands.MOTOR_LEFT_BWD
								|| cmd.getCommand() == BotProtocolCommands.MOTOR_LEFT_FWD
								|| cmd.getCommand() == BotProtocolCommands.MOTOR_RIGHT_BWD
								|| cmd.getCommand() == BotProtocolCommands.MOTOR_RIGHT_FWD){
							byte vValue = (byte) ((0xff & cmd.getValue()) * viMaxSpeed/100f);
							System.out.println("value " + ( 0xff & vValue ));
							cmd.setValue(vValue);
							
						}
					}
				}
				
				System.out.println( "connected: " + ftdi.isConnected() );
				ftdi.write(
						BotProtocol.generateDataFromSections( vSectionsList ));
				vSectionsList.clear();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		log.debug("Data writer stopped");
		
	}

	/**
	 * @return	{@code true} if thread is running, {@code} false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Set if thread is active or not
	 * @param active	{@link Boolean}
	 */
	public void setActive(boolean active) {
		this.active = active;
	}		
}
