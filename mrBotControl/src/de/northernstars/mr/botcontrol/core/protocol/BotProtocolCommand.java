package de.northernstars.mr.botcontrol.core.protocol;

/**
 * Class storing one command with value
 * @author Hannes Eilers
 *
 */
public class BotProtocolCommand {

	private BotProtocolCommands command;
	private byte value = 0x00;
	
	public BotProtocolCommand(BotProtocolCommands aCommand) {
		command = aCommand;
	}
	
	public BotProtocolCommand(BotProtocolCommands aCommand, byte aValue) {
		this(aCommand);
		value = aValue;
	}
	
	public BotProtocolCommand(BotProtocolCommands aCommand, int aValue) {
		this( aCommand, (byte) aValue );
	}

	/**
	 * @return the command
	 */
	public BotProtocolCommands getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(BotProtocolCommands command) {
		this.command = command;
	}

	/**
	 * @return the value
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(byte value) {
		this.value = value;
	}
	
}
