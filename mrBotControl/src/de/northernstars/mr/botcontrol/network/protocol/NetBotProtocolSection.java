package de.northernstars.mr.botcontrol.network.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing list of {@link NetBotProtocolCommand} for bot
 * @author Hannes Eilers
 *
 */
public class NetBotProtocolSection {

	private int botID = 0;
	private List<NetBotProtocolCommand> commandList = new ArrayList<NetBotProtocolCommand>();
	
	public NetBotProtocolSection(){}
	
	/**
	 * Constructor
	 * @param aBotID	{@link Integer} bot id
	 */
	public NetBotProtocolSection(int aBotID) {
		botID = aBotID;
	}
	
	/**
	 * Adds a new command to section
	 * @param aCommand	{@link NetBotProtocolCommand} to add
	 * @return			This {@link NetBotProtocolSection}
	 */
	public NetBotProtocolSection add( NetBotProtocolCommand aCommand ){
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
	 * @return			This {@link NetBotProtocolSection}
	 */
	public NetBotProtocolSection setBotID(int botID) {
		this.botID = botID;
		return this;
	}

	/**
	 * @return the commandList
	 */
	public List<NetBotProtocolCommand> getCommandList() {
		return commandList;
	}

	/**
	 * @param commandList 	{@link List} of {@link NetBotProtocolCommand}
	 * @return				This {@link NetBotProtocolSection}
	 */
	public NetBotProtocolSection setCommandList(List<NetBotProtocolCommand> commandList) {
		this.commandList = commandList;
		return this;
	}
	
	
	public String toString(){
		String ret = "";
		
		for( NetBotProtocolCommand cmd : commandList ){
			ret += "(" + cmd.getCommand() + "," + cmd.getValue() + ") ";
		}
		
		return ret;
	}
	
}
