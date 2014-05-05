package de.northernstars.mr.botcontrol.core.protocol;

/**
 * Enum for led animations
 * @author hannes
 *
 */
public enum LEDAnimations {

	NONE(0),
	FADE(1),
	RED_PULSED(2),
	STROBE(3),
	LADY(4);
	
	public byte CMD;
	
	private LEDAnimations(int aCmd){
		CMD = (byte) aCmd;
	}
	
}
