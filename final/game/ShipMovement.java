package	game;

import	utilities.Vector2D;


/**
* Constants for the movement of a ship.
*
* @author	Johan Liesén
*/
public abstract class ShipMovement
{
	// Just an incredibly excellent number
	private static double			speed	= 0.2;
	
	/** Move upwards */
	public final static Vector2D	UP		= new Vector2D(0, -speed);
	
	/** Move down */
	public final static Vector2D	DOWN	= new Vector2D(0,  speed);
	
	/** Move left */
	public final static Vector2D	LEFT	= new Vector2D(-speed, 0);
	
	/** Move right */
	public final static Vector2D	RIGHT	= new Vector2D( speed, 0);
} //end ShipMovement