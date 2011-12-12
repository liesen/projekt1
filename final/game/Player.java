package	game;

import	java.awt.Color;

import	game.actor.Ship;
import	game.inactor.Arrow;


/**
* This class represents a player in the game. It holds the player's ship, 
* credits, name, etc.
*
* @author	Johan Liesén
* @author 	Jimmy Stridh
*/
public class Player implements Comparable
{
	private	int		credits,	// Score
					score;		// Number of completed transportations
	private	Arrow	arrow;
	
	/** The player's ship */
	private	Ship	vessel;
	
	/** (Nick)Name of the player */
	private	String	name;
	
	/** Color which represents the player */
	private Color	color;

	
	
	/**
	* Create a player named "UnnamedPlayer".
	*
	* @param	ship		the ship that belongs to the player
	*/
	public Player()
	{
		this("UnnamedPlayer", Color.RED);
	}
	
	
	/**
	* Create a player.
	*
	* @param	name		(nick)name of the player
	* @param	c			color of the player
	*/
	public Player(String name, Color c)
	{
		this.name		= name;
		this.credits	= 0;
		this.score		= 0;
		this.color		= c;
		this.arrow		= new Arrow(color);
	}
	
	
	
	/**
	* Set the the ship beloning to the player.
	*
	* @param	s			the player's ship
	*/
	public void setShip(Ship s)
	{
		vessel = s;
	}
	
	
	/**
	* Set the amount of credits for this player.
	*
	* @param	credits		the player's credits
	*/
	public void setCredits(int credits)
	{
		this.credits	= credits;
	}
	
	
	/**
	* Get the amount of credits this player has.
	*
	* @return				the player's credits
	*/
	public int getCredits()
	{
		return credits;
	}
	
	
	/**
	* Increase the credits for this player.
	*
	* Does the same thing as <code>player.setCredits(player.getCredits() 
	* + credits)</code>. The added credits could be negative, which will reduce
	* the player's credits.
	*
	* @param	credits		credits to add to the current amount of credits
	*/
	public void addCredits(int credits)
	{
		this.credits	+= credits;
	}
	
	
	/**
	* Get the ship for the player.
	*
	* @return				ship belonging to the player
	*/
	public Ship getShip()
	{
		return vessel;
	}
	
	
	/**
	* Get the arrow that belongs to this player.
	*
	* @return				arrow belonging to the player
	*/
	public Arrow getArrow()
	{
		return arrow;
	}
	
	
	/**
	* Returns the number of completed transports that the player has done.
	*
	* @return				number of completed transports
	*/
	public int getScore()
	{
		return score;
	}
	
	
	/**
	* Sets the number of completed Score that the player has done.
	*
	* @param	Score	number of Score
	*/
	public void setScore(int score)
	{
		this.score	= score;
	}
	
	
	/**
	* Adds a number of Score to the existing count. Can be negative to
	* decrease the number.
	*
	* @param	Score	number of Score to add
	*/
	public void addScore(int score)
	{
		this.score	+= score;
	}
	
	
	/**
	* Returns player name.
	*
	* @return				name of the player
	*/
	public String getName()
	{
		return name;
	}
	
	
	/** 
	* Returns the color of the player.
	*
	* @return				the player's color
	*/
	public Color getColor()
	{
		return color;
	}
	
	
	/**
	* Compares this player to another player.
	*
	* Ranks a players in respect to another player by first comparing their
	* score and finally, if the scores are the same, by their respective names.
	*
	* @param	o			the object to be compared
	* @return				...
	*/
	public int compareTo(Object o)
	{
		// The other objects must be a Player; throws a ClassCastException 
		// if it's not
		Player	player	= (Player) o;
		
		// First, compare the scores (credits)
		if (getCredits() > player.getCredits())
		{
			return -1;
		}
		else if (getCredits() < player.getCredits())
		{
			return 1;
		}
		
		// Finally, if the credits are equal, compare the nicknames
		return name.compareTo(player.getName());
	}
} //end Player
