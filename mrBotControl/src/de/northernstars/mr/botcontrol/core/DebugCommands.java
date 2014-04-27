package de.northernstars.mr.botcontrol.core;

/**
 * Enummeration of debugging commands
 * @author Hannes Eilers
 *
 */
public enum DebugCommands {

	MOTORS_FWD_HALF_SPEED('f'),
	MOTORS_BWD_HALF_SPEED('b'),
	MOTORS_STOP('z'),
	
	LED_RGB_ALL_ON('g'),
	LED_RGB_ALL_OFF('h'),
	LED_STATUS_ON('s'),
	LED_STATUS_OFF('r'),
	
	BOT_ID('i'),
	BOT_PUBLISHER('j'),
	BOT_VERSION('j'),
	BOT_PRODUCT('l'),
	
	HELP('?');
	
	
	public byte CMD;
	
	private DebugCommands(char aCmd){
		CMD = (byte) aCmd;
	}
	
}
