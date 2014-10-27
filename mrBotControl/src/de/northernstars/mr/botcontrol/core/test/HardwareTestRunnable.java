package de.northernstars.mr.botcontrol.core.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.northernstars.mr.botcontrol.core.MRBotControl;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommand;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommands;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

public class HardwareTestRunnable implements Runnable {

	private static final Logger log = LogManager.getLogger();
	
	/**
	 * Timings in seconds.
	 */
	private static long QUICK_TEST_TIME = 30;
	private static long[] INTERVAL_TEST_TIMES = new long[]{ 10, 20, 30, 60, 120, 180, 300, 600, 1200, 1800, 2700, 3600 };
	private static long INTERVAL_TEST_BREAK_MAX = 300;
	private static long COMPETITION_TEST_HALFTIME = 1800;
	private static long COMPETITION_TEST_BREAK = 600;
	private static long COMPETITION_TEST_OVERALL_TIME = 28800;
		
	private boolean mStop = false; 
	private MRBotControl control;
	private TestTypes mType = TestTypes.QUICK;
	private long mStartTime = 0;
	private int mIteration = 0;
	private long mNextInterval = 0;
	private boolean mToggle = false;
	
	public HardwareTestRunnable(  TestTypes aType ){
		super();
		mType = aType;
		control = MRBotControl.getInstance();
	}
	
	@Override
	public void run() {
		mStartTime = System.currentTimeMillis();
		
		log.debug( "Start {} hardware test: {}", mType.name() );
		
		boolean ret = true;
		while( !mStop && ret ){
			
			switch( mType ){
			case COMPETITION:
				ret = competitionTest();
				break;
				
			case INTERVAL:
				ret = intervalTest();
				break;
				
			default:
				ret = quickTest();
				break;
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
			
		}
		
		log.debug( "Stopped {} hardware test.", mType.name() );
		setStatus( "Stopped " + mType.name() + " test." );
	}
	
	/**
	 * Stops test
	 */
	public void stopTest(){
		mStop = true;
	}
	
	/**
	 * Does a quick test
	 */
	private boolean quickTest(){
		if( mStartTime + QUICK_TEST_TIME*1000 > System.currentTimeMillis() ){
			send();
			setStatus( "Quick Test. Remaining time: "
						+ (((mStartTime + QUICK_TEST_TIME*1000) - System.currentTimeMillis())/1000 + 1) + "s." );
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Does an interval test
	 */
	private boolean intervalTest(){		
		// check if to toggle
		if( System.currentTimeMillis() >= mStartTime + mNextInterval ){
			
			// check for break
			if( mToggle ){
				
				// break
				if( INTERVAL_TEST_TIMES[ mIteration ] < INTERVAL_TEST_BREAK_MAX ){
					mNextInterval += INTERVAL_TEST_TIMES[ mIteration ]*1000;
				} else {
					mNextInterval += INTERVAL_TEST_BREAK_MAX*1000;
				}				
				mIteration++;			
				
				// check if to exit
				if( mIteration > INTERVAL_TEST_TIMES.length-1 ){
					return false;
				}
				
			} else {
				
				// send				
				mNextInterval += INTERVAL_TEST_TIMES[ mIteration ]*1000;				
				
			}
			
			// toggle flag
			mToggle = !mToggle;
			
		}
		
		// check if to send or to do break;
		if( mToggle ){
			send();
			setStatus( "Interval Test sending. Remaining interval time: "
					+ ((mStartTime + mNextInterval - System.currentTimeMillis())/1000 + 1) + "s." );
		} else {
			setStatus( "Interval Test break. Remaining interval time: "
					+ ((mStartTime + mNextInterval - System.currentTimeMillis())/1000 + 1) + "s." );
		}
		
		return true;
	}
	
	/**
	 * Does a competition test
	 */
	private boolean competitionTest(){
		// check overall time
		if( mStartTime + COMPETITION_TEST_OVERALL_TIME*1000 > System.currentTimeMillis() ){
			
			// check if to toggle
			if( System.currentTimeMillis() >= mStartTime + mNextInterval ){
				
				if( mToggle ){
					
					// break;
					mNextInterval += COMPETITION_TEST_BREAK*1000;
					
				} else {
					
					// send
					mNextInterval += COMPETITION_TEST_HALFTIME*1000;
					
				}
				
				// toggle flag
				mToggle = !mToggle;
				
			}
			
			// check if to send or to do break;
			if( mToggle ){
				send();
				setStatus( "Competition Test sending. Remaining time: "
						+ ((mStartTime + mNextInterval - System.currentTimeMillis())/1000 + 1) + "s." );
			} else {
				setStatus( "Competition Test break. Remaining time: "
						+ ((mStartTime + mNextInterval - System.currentTimeMillis())/1000 + 1) + "s." );
			}
			
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sends array of {@link BotProtocolSection} to remote device
	 * @param aSections	Array of {@link BotProtocolSection}
	 */
	private synchronized void send(){		
		BotProtocolSection vSection = new BotProtocolSection(0);
		vSection.add( new BotProtocolCommand(BotProtocolCommands.LED_ANIMATION, 0xff) );
		
		if( control.getWriter() != null ){
			control.getWriter().putDataInQue( new BotProtocolSection[]{vSection} );
		}
	}
	
	private void setStatus(String msg){
		control.getGui().lblTestStatus.setText(msg);
	}
	

}
