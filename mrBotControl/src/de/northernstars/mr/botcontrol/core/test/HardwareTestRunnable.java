package de.northernstars.mr.botcontrol.core.test;

public class HardwareTestRunnable implements Runnable {

	private boolean stop = false; 
	
	@Override
	public void run() {
		while( !stop ){
			
		}
	}
	
	/**
	 * Stopps test
	 */
	public void stopTest(){
		stop = true;
	}

}
