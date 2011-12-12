package	game.actor;

import	java.awt.Color;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;
import	java.awt.Rectangle;

import	game.inactor.Platform;
import 	utilities.DataPreLoader;


/**
* A passenger is an actor which can be transported between platforms by ships.
*
* @author 	Karl Olofsson
* @author	Johan Liesén
* @author	Jimmy Stridh
* @version	5/11/2004 2:57PM
*/
public class Passenger extends Actor
{
	/** The destination platform */
	private		Platform	destination;
	
	/** Source platform */
	private		Platform	source;
	
	private		int			rightX,leftX;
	
	private		boolean		walkingRight = true;
	
	/**
	* Creates a passenger with a "source" platform aswell as a destination.
	*
	* @param	source		passenger's home
	* @param	destination	passenger's target
	*/
	public Passenger(Platform source, Platform destination)
	{
		// Start position is outside of the screen since the actual position is 
		// being calculated outside of the super constructor
		super("DEFAULT",
			  new Image[] { DataPreLoader.getInstance().getImage("manWalk1"),
							DataPreLoader.getInstance().getImage("manWalk2"),
							DataPreLoader.getInstance().getImage("manWalk3"),
							DataPreLoader.getInstance().getImage("manWalk4"),
							DataPreLoader.getInstance().getImage("manWalk5"),
							DataPreLoader.getInstance().getImage("manWalk6") },
			  1,
			  new Point(-10, -10));
		
		// Set source and target
		this.source			= source;
		this.destination	= destination;	
		
		// Set the position
		Rectangle	pfBounds	= source.getBounds();
		Rectangle	bounds		= getBounds();
		
		//Get the right boundary of the platform
		rightX = pfBounds.width + pfBounds.x - getBounds().width;
		
		//Get the right boundary of the platform
		leftX = pfBounds.x;
		
		//Randomize a start position between the two corners
		int x = pfBounds.x + (int) (Math.random()*(rightX - leftX));
		
		// Position the passenger on the platform
		int y	= pfBounds.y + bounds.y;
		
		// Update position
		setPosition(x, y);
	}
	
	
	
	/**
	* Returns the source platform.
	*
	* @return				source platform
	*/
	public Platform getSource()
	{
		return source;
	}
	
	
	/**
	* Returns the destionation platform.
	*
	* @return				destination platform
	*/
	public Platform getDestination()
	{
		return destination;
	}
	
	
	/**
	* Paint the passenger.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		super.paint(g);
		update();
	}
	
	
	/**
	* Update the passenger's movement (position, direction, etc.).
	*
	*/
	public void update()
	{
		Point passengerPos = getPosition();
		
		//change the direction of the passenger if it's at the edge
		if(passengerPos.x == rightX && walkingRight)
		{
			walkingRight = false;
			flip();
		} 
		else if(passengerPos.x == leftX && !walkingRight)
		{
			walkingRight = true;
			flip();
		}
		
		//Move it accordingly
		if(walkingRight)
		{
			passengerPos.translate(1,0);
		}
		else
		{
			passengerPos.translate(-1,0);
		}
		
		//Reposition the sprite
		setPosition(passengerPos);
	}
} //end Passenger