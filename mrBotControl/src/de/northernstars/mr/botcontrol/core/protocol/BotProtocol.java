package de.northernstars.mr.botcontrol.core.protocol;

import java.util.LinkedList;
import java.util.List;

/**
 * Class with static function to generate {@link BotProtocolCommand}
 * @author Hannes Eilers
 *
 */
public class BotProtocol {
	
	private static final byte protocolVersion = (byte) 0x02;
	
	/**
	 * Left wheel speed
	 * @param speed	{@link Integer} < 0 backward, foreward otherwise.
	 * @return	{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand leftWheelSpeed(int speed){
		if( speed < 0 ){
			return new BotProtocolCommand(BotProtocolCommands.MOTOR_LEFT_BWD, adjustSpeed(speed));
		}
		return new BotProtocolCommand(BotProtocolCommands.MOTOR_LEFT_FWD, adjustSpeed(speed));
	}
	
	/**
	 * Right wheel speed
	 * @param speed	{@link Integer} < 0 backward, foreward otherwise.
	 * @return	{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand rightWheelSpeed(int speed){
		if( speed < 0 ){
			return new BotProtocolCommand(BotProtocolCommands.MOTOR_RIGHT_BWD, adjustSpeed(speed));
		}
		return new BotProtocolCommand(BotProtocolCommands.MOTOR_RIGHT_FWD, adjustSpeed(speed));
	}
	
	/**
	 * Stopping both wheels
	 * @return {@link BotProtocolCommand}
	 */
	public static BotProtocolCommand stopWheels(){
		return new BotProtocolCommand(BotProtocolCommands.MOTOR_STOP);
	}
	
	/**
	 * Switching status led on and off
	 * @param on	{@code true} to switch led on, {@code false} otherwise.
	 * @return		{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand ledStatus(boolean on){
		if( on ){
			return new BotProtocolCommand(BotProtocolCommands.LED_STATUS_ON);
		}
		return new BotProtocolCommand(BotProtocolCommands.LED_STATUS_OFF);
	}
	
	/**
	 * Switching all rgb leds on and off
	 * @param on	{@code true} to switch leds on, {@code false} otherwise.
	 * @return		{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand ledRGBAll(boolean on){
		if( on ){
			return new BotProtocolCommand(BotProtocolCommands.LEDS_ON);
		}
		return new BotProtocolCommand(BotProtocolCommands.LEDS_OFF);
	}
	

	/**
	 * Switching a led on or off
	 * @param led	{@link LEDS} to set
	 * @param on	{@code true} if to switch led on, {@code false} otherwise
	 * @return		{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand ledSwitchOnOff(LEDS led, boolean on){
		BotProtocolCommands cmd = BotProtocolCommands.LED_OFF;
		if( on ){
			cmd = BotProtocolCommands.LED_ON;
		}
		
		switch(led){
		case LED1:
			return new BotProtocolCommand(cmd, 0x01);
			
		case LED2:
			return new BotProtocolCommand(cmd, 0x02);
			
		case LED3:
			return new BotProtocolCommand(cmd, 0x03);
			
		default:
			return new BotProtocolCommand(cmd, 0x04);	
		}
	}
	
	/**
	 * Setting red led color
	 * @param led		{@link LEDS} to set color of
	 * @param color		{@link Integer} color between 0 and 255
	 * @return			{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand setLEDColorRed(LEDS led, int color){
		byte bColor = adjustColor(color);
		
		switch(led){
		case LED1:
			return new BotProtocolCommand(BotProtocolCommands.LED_1_RED, bColor);
			
		case LED2:
			return new BotProtocolCommand(BotProtocolCommands.LED_2_RED, bColor);
			
		case LED3:
			return new BotProtocolCommand(BotProtocolCommands.LED_3_RED, bColor);
			
		default:
			return new BotProtocolCommand(BotProtocolCommands.LED_4_RED, bColor);
		}		
	}
	
	/**
	 * Setting red green color
	 * @param led		{@link LEDS} to set color of
	 * @param color		{@link Integer} color between 0 and 255
	 * @return			{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand setLEDColorGreen(LEDS led, int color){
		byte bColor = adjustColor(color);
		
		switch(led){
		case LED1:
			return new BotProtocolCommand(BotProtocolCommands.LED_1_GREEN, bColor);
			
		case LED2:
			return new BotProtocolCommand(BotProtocolCommands.LED_2_GREEN, bColor);
			
		case LED3:
			return new BotProtocolCommand(BotProtocolCommands.LED_3_GREEN, bColor);
			
		default:
			return new BotProtocolCommand(BotProtocolCommands.LED_4_GREEN, bColor);
		}		
	}
	
	/**
	 * Setting red blue color
	 * @param led		{@link LEDS} to set color of
	 * @param color		{@link Integer} color between 0 and 255
	 * @return			{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand setLEDColorBlue(LEDS led, int color){
		byte bColor = adjustColor(color);
		
		switch(led){
		case LED1:
			return new BotProtocolCommand(BotProtocolCommands.LED_1_BLUE, bColor);
			
		case LED2:
			return new BotProtocolCommand(BotProtocolCommands.LED_2_BLUE, bColor);
			
		case LED3:
			return new BotProtocolCommand(BotProtocolCommands.LED_3_BLUE, bColor);
			
		default:
			return new BotProtocolCommand(BotProtocolCommands.LED_4_BLUE, bColor);
		}		
	}
	
	/**
	 * Setting a led animation
	 * @param animation		{@link LEDAnimations} to set
	 * @return				{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand ledAnimation(LEDAnimations animation){
		return new BotProtocolCommand(BotProtocolCommands.LED_ANIMATION, animation.CMD);
	}
	
	/**
	 * Sets new bot id.<br>
	 * ATTENTION:<br>
	 * Be carful with this option!
	 * Take care not to set two bots with the same id!
	 * @param newBotID	{@link Integer} bot id to set
	 * @return			{@link BotProtocolCommand}
	 */
	public static BotProtocolCommand changeBotID(int newBotID){
		return new BotProtocolCommand(BotProtocolCommands.BOT_SET_ID, newBotID);
	}
	
	/**
	 * Adjusting color value to valid data byte
	 * @param color		{@link Integer} color value 0-255
	 * @return			Valid data {@link Byte}
	 */
	public static byte adjustColor(int color){
		if( color < 0 ){
			color = color * -1;
		}
		if( color > 255 ){
			color = 255;
		}
		
		return (byte) color;
	}
	
	/**
	 * Adjusting speed value to valid data byte.
	 * @param speed		{@link Integer} speed value 0-100
	 * @return			Valid data {@link Byte}
	 */
	public static byte adjustSpeed(int speed){
		// Limit min and max value
		if( speed < 0 ){
			speed = speed * -1;
		}
		if( speed > 100 ){
			speed = 100;
		}
		
		// calculate value between 0 and 255
		float fSpeed = speed/100f;
		speed = (int)(fSpeed * 255f);
		
		return (byte) speed;
	}
	
	/**
	 * Generates {@link Byte} data to send from {@link List} of {@link BotProtocolSection}.
	 * @param sections	{@link List} of {@link BotProtocolSection}
	 * @return			{@link Byte} array with data to send
	 */
	public static byte[] generateDataFromSections(List<BotProtocolSection> sections){
		BotProtocolSection[] bSections = new BotProtocolSection[sections.size()];
		for( int i=0; i<sections.size();i++ ){
			bSections[i] = sections.get(i);
		}
		
		return generateDataFromSections(bSections);
	}
	
	/**
	 * Generates {@link Byte} data to send from array of {@link BotProtocolSection}.
	 * @param sections	{@link List} of {@link BotProtocolSection}
	 * @return			{@link Byte} array with data to send
	 */
	public static byte[] generateDataFromSections(BotProtocolSection[] sections){
		List<Byte> dataList = new LinkedList<Byte>();
		
		// Add protocol start
		dataList.add( BotProtocolCommands.PROTOCOL_VERSION.CMD );
		dataList.add( protocolVersion );
		
		// Add sections
		for( BotProtocolSection section : sections ){
			if( section.getCommandList().size() > 0 ){
				// Add bot id
				dataList.add( BotProtocolCommands.SEPERATOR_SECTION.CMD );
				dataList.add( BotProtocolCommands.BOT_ID.CMD );
				dataList.add( (byte) section.getBotID() );
				
				// Add commands
				for( BotProtocolCommand cmd : section.getCommandList() ){
					dataList.add( BotProtocolCommands.SEPERATOR_COMMAND.CMD );
					dataList.add( cmd.getCommand().CMD );
					dataList.add( cmd.getValue() );
				}
			}
		}
		
		// Add protocol end
		dataList.add( BotProtocolCommands.SEPERATOR_SECTION.CMD );
		dataList.add( BotProtocolCommands.PROTOCOL_TRANSMISSION_END.CMD );
		dataList.add( (byte) 0x00 ); 
		
		// Convert data list to byte array
		byte[] data = new byte[dataList.size()];		
		for( int i=0; i<dataList.size(); i++ ){
			data[i] = dataList.get(i);
		}
		
		return data;		
	}

}
