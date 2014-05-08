package de.northernstars.mr.botcontrol.network.protocol;

import de.northernstars.mr.botcontrol.core.protocol.BotProtocol;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommand;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolCommands;
import de.northernstars.mr.botcontrol.core.protocol.BotProtocolSection;

/**
 * Class with static function to generate {@link NetBotProtocolCommand}
 * @author Hannes Eilers
 *
 */
public class NetBotProtocol {
	
	public static BotProtocolSection[] toBotProtocolSections(NetBotProtocolSection[] aSections){
		BotProtocolSection[] sections = new BotProtocolSection[aSections.length];
		
		// convert every section
		for( int i=0; i<aSections.length; i++ ){
			NetBotProtocolSection s = aSections[i];
			sections[i] = new BotProtocolSection( s.getBotID() );
			
			// convert commands
			for( NetBotProtocolCommand cmd : s.getCommandList() ){
				sections[i].add( toBotProtocolCommand(cmd) );
			}
			
		}
		
		return sections;
	}
	
	public static BotProtocolCommand toBotProtocolCommand(NetBotProtocolCommand aCommand){
		
		switch( aCommand.getCommand() ){
		case LEDS:
			break;
			
		case LED_STATUS:
			break;
			
		case MOTOR_LEFT:
			if( aCommand.getValue() < 0 ){
				return new BotProtocolCommand( BotProtocolCommands.MOTOR_LEFT_BWD,
						BotProtocol.adjustSpeed(aCommand.getValue()) );
			}
			
			return new BotProtocolCommand( BotProtocolCommands.MOTOR_LEFT_FWD, 
						BotProtocol.adjustSpeed(aCommand.getValue()));
			
		case MOTOR_RIGHT:
			if( aCommand.getValue() < 0 ){
				return new BotProtocolCommand( BotProtocolCommands.MOTOR_RIGHT_BWD,
						BotProtocol.adjustSpeed(aCommand.getValue()) );
			}
			
			return new BotProtocolCommand( BotProtocolCommands.MOTOR_RIGHT_FWD, 
						BotProtocol.adjustSpeed(aCommand.getValue()));
			
		default:
			break;
		}
		
		return new BotProtocolCommand( aCommand.getCommand().CMD, aCommand.getValue() );
	}

}
