package de.northernstars.mr.botcontrol.core.protocol;

/**
 * Enum for led animations
 * @author hannes
 *
 */
public enum LEDAnimations {

	NONE(0),
	FADE(1),
	RED_PULSED(3),
	STROBE(2),
	LADY(4),
	RAINBOW(5);
	
	public byte CMD;
	
	private LEDAnimations(int aCmd){
		CMD = (byte) aCmd;
	}
	
}
