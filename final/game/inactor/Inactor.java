package	game.inactor;

import	java.awt.Graphics2D;
import 	java.awt.Image;
import 	java.awt.Point;
import 	java.awt.Rectangle;

import	game.GameObject;


/**
* An Inactor is a GameObject which cannot act.
* 
* @author	Karl Olofsson
* @author	Johan Liesén
*/
public class Inactor extends GameObject
{

    /**
    * Creates an Inactor.
	*
	* @param	initialStateKey
	*						initial state
	* @param	spriteFrames
	*						frames
	* @param	priority	priority
	* @param	position	initial position
    */   
    public Inactor(String initialStateKey,
    			   Image[] spriteFrames,
    			   int priority,
    			   Point position)
    {
    	super(initialStateKey,spriteFrames,priority,position);
    }
    
	
	
	/**
	* Returns the vertices (position of edges) of the Inactor bounds.
	*
	* @return				vertices
	*/
	public Point[] getVertices()
	{
		Point		position	= getPosition();
		Rectangle	bounds		= getBounds();
		
		return new Point[] { new Point(position.x, position.y),
							 new Point(position.x + bounds.width - 1, position.y),
							 new Point(position.x + bounds.width - 1, position.y + bounds.height - 1),
							 new Point(position.x, position.y + bounds.height - 1) };
	}
	
	
	/**
	* Paints the Inactor.
	*
	* @param	g		graphics context
	*/
	public void paint(Graphics2D g)
	{
		super.paint(g);
	}
} //end Inactor