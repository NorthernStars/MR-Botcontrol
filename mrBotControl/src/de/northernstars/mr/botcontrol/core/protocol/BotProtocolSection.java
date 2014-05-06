package de.northernstars.mr.botcontrol.core.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing list of {@link BotProtocolCommand} for bot
 * @author Hannes Eilers
 *
 */
public class BotProtocolSection {

	private int botID = 0;
	private List<BotProtocolCommand> commandList = new ArrayList<BotProtocolCommand>();
	
	public BotProtocolSection(){}
	
	/**
	 * Constructor
	 * @param aBotID	{@link Integer} bot id
	 */
	public BotProtocolSection(int aBotID) {
		botID = aBotID;
	}
	
	/**
	 * Adds a new command to section
	 * @param aCommand	{@link BotProtocolCommand} to add
	 * @return			This {@link BotProtocolSection}
	 */
	public BotProtocolSection add( BotProtocolCommand aCommand ){
		commandList.add(aCommand);
		return this;
	}

	/**
	 * @return the botID
	 */
	public int getBotID() {
		return botID;
	}

	/**
	 * @param botID 	{@link Integer} bot id
	 * @return			This {@link BotProtocolSection}
	 */
	public BotProtocolSection setBotID(int botID) {
		this.botID = botID;
		return this;
	}

	/**
	 * @return the commandList
	 */
	public List<BotProtocolCommand> getCommandList() {
		return commandList;
	}

	/**
	 * @param commandList 	{@link List} of {@link BotProtocolCommand}
	 * @return				This {@link BotProtocolSection}
	 */
	public BotProtocolSection setCommandList(List<BotProtocolCommand> commandList) {
		this.commandList = commandList;
		return this;
	}
	
	
	public String toString(){
		String ret = "";
		
		for( BotProtocolCommand cmd : commandList ){
			ret += "(" + cmd.getCommand() + "," + cmd.getValue() + ") ";
		}
		
		return ret;
	}
	
}
