package de.northernstars.mr.botcontrol.core.protocol;

public enum BotProtocolCommands {

	SEPERATOR_SECTION(0x2b),
	SEPERATOR_COMMAND(0x2e),
	
	PROTOCOL_VERSION(0x50),
	PROTOCOL_TRANSMISSION_END(0x59),
	BOT_ID(0x5a),
	BOT_SET_ID(0x4b),
	
	MOTOR_LEFT_FWD(0x41),
	MOTOR_LEFT_BWD(0x42),
	MOTOR_RIGHT_FWD(0x44),
	MOTOR_RIGHT_BWD(0x47),
	MOTOR_STOP(0x48),
		
	LEDS_ON(0x4d),
	LEDS_OFF(0x4e),
	
	LED_ON(0x7b),
	LED_OFF(0x7e),
	
	LED_ANIMATION(0x53)
	,
	LED_STATUS_ON(0x55),
	LED_STATUS_OFF(0x56),
	
	LED_1_RED(0x63),
	LED_1_GREEN(0x65),
	LED_1_BLUE(0x66),
	
	LED_2_RED(0x69),
	LED_2_GREEN(0x6a),
	LED_2_BLUE(0x6c),
	
	LED_3_RED(0x6f),
	LED_3_GREEN(0x71),
	LED_3_BLUE(0x72),
	
	LED_4_RED(0x74),
	LED_4_GREEN(0x77),
	LED_4_BLUE(0x78);
	
	
	public byte CMD;
	
	private BotProtocolCommands(int cmd){
		CMD = (byte) cmd;
	}
	
}
