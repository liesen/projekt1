package	game;

import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;

import	game.graphics.Sprite;


/**
* A GameObject is an object (actor or inactor) in the game space.
* 
* @author	Karl Olofsson
* @author 	Jimmy Stridh
*/
public abstract class GameObject extends Sprite
{
	// Counter for the number of instances of GameObject
    private static 	int		instances	= 0;
    
    /** ID */
    protected 		int		id;
    
    
    
    /**
    * Creates a game object.
    *
	* @param	initialStateKey
	*						initial state
	* @param	spriteFrames
	*						frames
	* @param	priority	priority
	* @param	position	initial position
    */   
    public GameObject(String initialStateKey, 
    				  Image[] spriteFrames,
    				  int priority,
    				  Point position)
    {
    	super(initialStateKey, spriteFrames, priority);
    	
    	setPosition(position);
    	
    	// Set ID to instance and increment it
    	this.id			= instances++;
    }
    
    
    
    /**
    * Decrease instance count the object is destroyed.
    *
    */
    public final void finalize()
    {
    	instances--;
    }
    
    
    /**
    * Returns the number of instances of type <code>GameObject</code>.
    *
    * @return				instance count
    */
    public static int getInstanceCount()
    {
    	return instances;
    }
    
    
    /**
    * Paints the game object.
    *
    * @param	g			graphics context
    */
	public void paint(Graphics2D g)
	{
		super.paint(g);
	}    
} //end GameObject