package de.northernstars.mr.botcontrol.network.protocol;

/**
 * Class storing one command with value
 * @author Hannes Eilers
 *
 */
public class NetBotProtocolCommand {

	private NetBotProtocolCommands command;
	private int value = 0;
	
	public NetBotProtocolCommand(){}
	
	public NetBotProtocolCommand(NetBotProtocolCommands aCommand) {
		command = aCommand;
	}
	
	public NetBotProtocolCommand(NetBotProtocolCommands aCommand, int aValue) {
		this(aCommand);
		value = aValue;
	}

	/**
	 * @return the command
	 */
	public NetBotProtocolCommands getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(NetBotProtocolCommands command) {
		this.command = command;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
