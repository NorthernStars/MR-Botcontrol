package core;

/**
 * Class for sending bot commands
 * @author northernstars
 *
 */
public class BotCommand {
	
	public static final int MAX_SPEED = 31;

	private int botID = 0;
	private int leftSpeed = 0;
	private int rightSpeed = 0;
	
	public BotCommand(int botID){
		this.botID = botID;
	}
	
	public BotCommand(int botID, int leftSpeed, int rightSpeed){
		this(botID);
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}
	
	
	/**
	 * Converts bot command to string
	 * @return
	 */
	public String toString(){
		StringBuilder command = new StringBuilder();
		
		// check speeds
		if( leftSpeed < MAX_SPEED*-1 )
			leftSpeed = MAX_SPEED*-1;
		else if( leftSpeed > MAX_SPEED )
			leftSpeed = MAX_SPEED;
		
		if( rightSpeed < MAX_SPEED*-1 )
			rightSpeed = MAX_SPEED*-1;
		else if( rightSpeed > MAX_SPEED )
			rightSpeed = MAX_SPEED;
		
		// create command
		command.append("(fid,");
		
		// add bot ID
  		if(botID < 16){
  			command.append('0');
  		}
  		command.append(Integer.toHexString(botID));
  		command.append(",(");
  		
  		// add speed
  		if(leftSpeed == 0 && rightSpeed == 0){  			
  			
  			// STOP
  			command.append( "stop" );
  			
  		}
  		else{
  			
  			command.append( "lst,(" );
  			
  			// Set left speed
  			if( leftSpeed > 0 )
  				command.append( "fl," );
  			else
  				command.append( "bl," );
  			
  			if( Math.abs(leftSpeed) < 16 )
  				command.append("0");
  			command.append( Integer.toHexString(Math.abs(leftSpeed)) );
  			
  			command.append( "),(" );
  			
  			// Set right speed
  			if( rightSpeed > 0 )
  				command.append( "fr," );
  			else
  				command.append( "br," );
  			
  			if( Math.abs(rightSpeed) < 16 )
  				command.append("0");
  			command.append( Integer.toHexString(Math.abs(rightSpeed)) );
  			
  			command.append( ")" );  			
  			
  		}
  				

  		command.append("))");
		
		// Return command string
		return command.toString();
	}

	public int getBotID() {
		return botID;
	}

	public void setBotID(int botID) {
		this.botID = botID;
	}

	public int getLeftSpeed() {
		return leftSpeed;
	}

	public void setLeftSpeed(int leftSpeed) {
		this.leftSpeed = leftSpeed;
	}

	public int getRightSpeed() {
		return rightSpeed;
	}

	public void setRightSpeed(int rightSpeed) {
		this.rightSpeed = rightSpeed;
	}

}
