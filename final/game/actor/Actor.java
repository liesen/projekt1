package	game.actor;

import	java.awt.Image;
import	java.awt.Point;
import	java.awt.Graphics2D;

import	game.GameObject;


/**
* An actor is a <code>{@link GameObject}</code> which can act, i.e. ships and
* passengers.
* 
* @author	Karl Olofsson
* @author	Jimmy Stridh
* @version	2004-05-09
*/
public class Actor extends GameObject
{
	/**
	* Create a new Actor.
	*
	* @param	initialStateKey
	*						initial state
	* @param	spriteFrames
	*						frames
	* @param	priority	priority
	* @param	position	initial position
	*/
	public Actor(String initialStateKey, Image[] spriteFrames, int priority,
    															 Point position)
	{
		super(initialStateKey, spriteFrames, priority, position);
	}
	
	
	
	/**
	* Paint the actor.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		super.paint(g);
	}
} //end Actor