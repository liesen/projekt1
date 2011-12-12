package game.graphics;

import	java.awt.Graphics;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;
import	java.awt.Rectangle;
import	java.awt.Shape;
import	java.awt.geom.AffineTransform;
import	java.awt.image.AffineTransformOp;
import	java.awt.image.BufferedImage;
import	java.util.Collection;
import	java.util.HashMap;
import	java.util.Map;

import	utilities.ImageHelper;
import	utilities.Vector2D;


/**
 * Represents a sprite with the abilites to paint itself,
 * rotate, move around.
 *
 * TODO: Add possibility to have different boundaries for each state.
 *	 Add error checking for frames of unequal size.
 *	 goatposition?
 * BUGS: When rotating a certain angle, the picture will get bigger, and will continue to increase in size.
 *
 * @author	Jimmy Stridh
 * @author	Johan Liesén
 * @version 5/11/2004 1:09PM
 */ 
public class Sprite implements Paintable
{
	/** The current position */
	protected Point	position;	
	
	/** The frame(s) for the sprite */
	protected BufferedImage[] frames;
	
	protected BufferedImage[] unrotatedFrames;
	
	/** The index of the current frame in the animation */
	protected int currentFrameIndex;
	
	/** The currently selected state */
	protected String currentStateKey;
	
	/** The list of states for the sprite */
	protected Map states;
	
	/** The priority the sprites has in terms of being drawn over another
	*   sprite. Lower priority means further back. */
	protected int priority;
	
	/** The current rotation of the sprite i degrees */
	protected double rotation = 0;
	
	/** The outer boundaries of the the sprite */
	protected Rectangle bounds;
	
	/** The vector representing the current speed and direction of the sprite */
	protected Vector2D motionVector;
	
	/** Defining if the sprite should be painted or not */
	protected boolean visible;
	
	
	
	/**
	*
	*
	*/
	public Sprite(String initialStateKey,Image[] spriteFrames,int priority){
		states = new HashMap();
		addState(initialStateKey,spriteFrames);
		
		try
		{
			setState(initialStateKey);
		} 
		catch(NoSuchStateException e)
		{
			e.printStackTrace();
		}
		
		// Get bounds from image size
		bounds			= new Rectangle(frames[0].getTileWidth(),
							  			frames[0].getTileHeight());
		
		// Start dead
		motionVector	= new Vector2D(0.0, 0.0);
		
		setPriority(priority);
		
/*		unrotatedFrames = new BufferedImage[frames.length];
		
		for(int i=0;i<frames.length;i++)
		{
			unrotatedFrames[i] = (BufferedImage) (frames[i].clone());
		}*/
	}
	
	
	
	/**
	*
	*
	*/
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	
	/**
	*
	*
	*/
	public boolean isVisible()
	{
		return visible;
	}
	
	
	/**
	*
	*
	*/
	public double getAngle()
	{
		return rotation;
	}
	

	/** Rotate the sprite realtively to it's current rotation.
	 * @param degrees The nr of degrees which to rotate the ship by.
	 */
	public BufferedImage getRotatedFrame(double degrees, int currentFrameIndex)
	{
		//Create the transformation/rotation
		AffineTransform tx = new AffineTransform();

		tx.rotate((double)degrees*((2.0*Math.PI)/360.0), frames[0].getTileWidth()/2,
						   frames[0].getTileHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx,
					       AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter(frames[currentFrameIndex], null);
	}
	

	public void rotate(double degrees)
	{
		rotation = rotation + degrees;
	}
	
	/** Set the direction of the sprite.
	 * @param degrees The degree the sprite should point towards.
	 */
	public void rotateTo(double degrees)
	{
		rotation = degrees;
	} 
	
	/** Relocate the sprite to a new position
	 * @param x The x position
	 * @param y The y position
	 */
	public void setPosition (int x,int y)
	{
		setPosition(new Point(x,y));
	}
	
	
	/** 
	* Relocate the sprite to a new position
	*
	* @param	position	new position
	*/
	public void setPosition (Point position) 
	{
		this.position = new Point(position);
		bounds.setLocation(position);
	}
	
	
	/**
	*
	*
	*/
	public Point getPosition()
	{
		return new Point(position);
	}
	
	
	/**
	*
	*
	*/
	public void flip()
	{
		// Change the motion
		motionVector	= motionVector.multiply(new Vector2D(1, -1));
		
    	// Mirror the image along the y-axis
    	AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
    	tx.translate(-frames[0].getWidth(null), 0);
    	AffineTransformOp	op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
   		for(int i=0;i<frames.length;i++)
   		{
   			frames[i] = op.filter(frames[i], null);
		}   		 
	}
	
	
	/**
	*
	*
	*/
	public void setMotionVector(Vector2D motionVector)
	{
		this.motionVector = motionVector;
	}
	
	
	/**
	*
	*
	*/
	public Vector2D getMotionVector()
	{
		return new Vector2D(motionVector);
	}


	/**
	* Change the current drawing state of the sprite to the one specified. If no
	*  such state exists a <code>NoSuchStateException</code> will be thrown.
	*
	* @throws NoSuchStateException	If the specified state hasn't been 
	*								previously added.
	*/
	public void setState(String stateKey) throws NoSuchStateException
	{
		//reset the frame index
		currentFrameIndex = 0;
		
		//retreive the frames matching the key
		Object framesTemp = states.get(stateKey);
		
		//If the result is null, there was no corresponding state.
		if(framesTemp == null)
			throw new NoSuchStateException(stateKey);
		else
		{
			frames = (BufferedImage[])framesTemp;
			currentStateKey = new String(stateKey);
		}
	}
	
	/** 
	* Give the sprite another state that can be changed to. If only one
	* frame is supplied, the sprite will not be animated. If a state with
	* the same key previously existed, it will be overwritten.
	* All frames must be of equal dimensions.
	*
	* @param stateKey A unique identifier for this state
	* @param spriteFrames One or more images.
	*/
	public void addState(String stateKey,Image[] spriteFrames)
	{
		//Convert images to bufferedImages
		BufferedImage[] bufferedSpriteFrames = 
					   new BufferedImage[spriteFrames.length];
		for(int i=0;i<spriteFrames.length;i++)
		{
			bufferedSpriteFrames[i] = 
					    ImageHelper.
					       toBufferedImage(spriteFrames[i]);
		}
		
		//insert into the list of sprite states
		states.put(stateKey,bufferedSpriteFrames);
	}
	
	
	/**
   	* Retreive a list of the sprites states.
   	*
   	* @return 				unique keys for the different states
   	*/
	public Collection getStates()
	{
		return states.keySet();
	}
	
	
	/**
	* Retreive the key for the current selected state.
	*
	* @return 				unique key of the current state
	*/
	public String getCurrentState()
	{
		return new String(currentStateKey);
	}
	
	
	/**
	* Returns the sprites priority.
	*
	* @return				priority
	*/
	public int getPriority()
	{
		return priority;
	}
	
	
	/**
	* Set the sprites priority.
	*
	* @param	priority	priority
	*/
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	

	/**
	* Change the boundaries of the sprite. By default, this is the outer
	* dimensions of the first frame. The Rectangle will be centered on the
	* sprite.
	*
	* @param	r		the sprites bounds
	*/
	public void setBounds(Rectangle r)
	{
		bounds.setBounds(r);
	}
	
	
	/**
	* Get the outer boundaries of the sprite.
	*
	* @return			the sprites bounds
	*/
	public Rectangle getBounds()
	{
		return new Rectangle(bounds);
	}
	
	
	/**
	* Returns the centerposition of the sprite.
	*
	* @return			sprite center
	*/
	public Point getCenterPoint()
	{
		// Image top left
		Point	center	= getPosition();
		
		// Image center
		center.translate(bounds.width / 2, bounds.height / 2);
		
		return center;
	}
	
	
	/**
	* Sets the sprite's position centered at the given point.
	*
	* @param	p			new center for sprite
	*/
	public void setCenterAt(Point p)
	{
		p.translate(-bounds.width / 2, -bounds.height / 2);
		
		setPosition(p);
	}
	
	
	/**
	* Test if the sprite collides with the specified area.
	*
	* @param	r			Rectangle to test collision with
	* @return				<code>true</code> if the sprite collides with the 
	*						section, else <code>false</code>
	*/
	public boolean detectCollision(Rectangle r)
	{
		return bounds.intersects(r);
	}
		
	
	/**
	* Perform the render of the sprite and paint it.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		if (visible)
		{
			if(rotation == 0)
			 g.drawImage(frames[currentFrameIndex], null, position.x, position.y);
			else
			{
				g.drawImage(getRotatedFrame(rotation, currentFrameIndex), null,
												position.x, position.y);
			}
			// wrap around
			if (currentFrameIndex == frames.length - 1)
				currentFrameIndex = 0;
			else
				currentFrameIndex++;
		}
	}
} //end Sprite class
