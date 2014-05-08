package de.northernstars.mr.botcontrol.network.protocol;

import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommands;

public enum NetBotProtocolCommands {

	BOT_SET_ID(BotProtocolCommands.BOT_SET_ID),
	
	MOTOR_LEFT(BotProtocolCommands.MOTOR_LEFT_FWD),
	MOTOR_RIGHT(BotProtocolCommands.MOTOR_RIGHT_FWD),
		
	LEDS(BotProtocolCommands.LEDS_ON),
	
	LED_ON(BotProtocolCommands.LED_ON),
	LED_OFF(BotProtocolCommands.LED_OFF),
	
	LED_ANIMATION(BotProtocolCommands.LED_ANIMATION),
	LED_STATUS(BotProtocolCommands.LED_STATUS_ON),
	
	LED_1_RED(BotProtocolCommands.LED_1_RED),
	LED_1_GREEN(BotProtocolCommands.LED_1_GREEN),
	LED_1_BLUE(BotProtocolCommands.LED_1_BLUE),
	
	LED_2_RED(BotProtocolCommands.LED_2_RED),
	LED_2_GREEN(BotProtocolCommands.LED_2_GREEN),
	LED_2_BLUE(BotProtocolCommands.LED_2_BLUE),
	
	LED_3_RED(BotProtocolCommands.LED_3_RED),
	LED_3_GREEN(BotProtocolCommands.LED_3_GREEN),
	LED_3_BLUE(BotProtocolCommands.LED_3_BLUE),
	
	LED_4_RED(BotProtocolCommands.LED_4_RED),
	LED_4_GREEN(BotProtocolCommands.LED_4_GREEN),
	LED_4_BLUE(BotProtocolCommands.LED_4_BLUE);
	
	public BotProtocolCommands CMD = BotProtocolCommands.BOT_ID;
	
	private NetBotProtocolCommands(BotProtocolCommands cmd){
		CMD = cmd;
	}
	
}
