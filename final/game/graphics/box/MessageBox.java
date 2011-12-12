package	game.graphics.box;

import	java.awt.AlphaComposite;
import	java.awt.BasicStroke;
import	java.awt.Composite;
import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Graphics2D;
import	java.awt.Paint;
import	java.awt.Point;
import	java.awt.Rectangle;


/**
* The MessageBox is a (rounded) square with a color which can be semi 
* transparent.
*
* @author	Johan Liesén
* @author	Jimmy Stridh
*/
public class MessageBox
{
	/** Box position */
	private Point				position;
	
	/** Box size */
	private Dimension			size;
	
	/** Rounded or not? */
	private boolean				rounded;
	
	/** Transparency ranging from 0 to 1 */
	private	float				transparency;
	
	/** Background color */
	private	Color				color;
	
	
	
	/**
	* Create box.
	*
	* @param	bounds		bounding box
	* @param	color		color
	* @param	transparency
	*						transparency value (0 to 1)
	*/
	public MessageBox(Rectangle bounds, Color color, float transparency)
	{
		this(bounds, color, transparency, false);
	}
	
	
	/**
	* Create box.
	*
	* @param	bounds		bounding box
	* @param	color		color
	* @param	transparency
	*						transparency value (0 to 1)
	* @param	rounded		
	*/
	public MessageBox(Rectangle bounds, Color color, float transparency, 
																boolean rounded)
	{
		this(bounds.x, 
			 bounds.y, 
			 bounds.width, 
			 bounds.height, 
			 color, 
			 transparency, 
			 rounded);
	}
	
	
	/**
	* Create box.
	*
	* @param	x			x position
	* @param	y			y position
	* @param	width		width of box
	* @param	height		box height
	* @param	color		color
	* @param	transparency
	*						transparency value (0 to 1)
	*/
	public MessageBox(int x, int y, int width, int height, Color color, 
															 float transparency)
	{
		this(x, y, width, height, color, transparency, false);
	}
	
	
	/**
	* Create box.
	*
	* @param	x			x position
	* @param	y			y position
	* @param	width		width of box
	* @param	height		box height
	* @param	color		color
	* @param	transparency
	*						transparency value (0 to 1)
	* @param	rounded
	*/
	public MessageBox(int x, int y, int width, int height, Color color, 
											float transparency, boolean rounded)
	{
		// Set box values
		position	= new Point(x, y);
		size		= new Dimension(width, height);
		
		// Padding must be called after the bounding box has been created!
		setPadding(0, 0, 0, 0);
		
		setColor(color);
		setTransparency(transparency);
		setRounded(rounded);
	}
	
	
	
	/**
	* Returns the bounds of the box.
	*
	* @return				the box' bounds
	*/
	public Rectangle getBounds()
	{
		return new Rectangle(position.x, position.y, size.width, size.height);
	}
	
	
	/**
	* Sets the position of the box.
	*
	* @param	x			x coordinate
	* @param	y			y coordinate
	*/
	public void setPosition(int x, int y)
	{
		position.setLocation(x, y);
	}
	
	
	/**
	* Returns the position of the box.
	*
	* @return				box position
	*/
	public Point getPosition()
	{
		return position;
	}
	
	
	/**
	* Sets the size of the box.
	*
	* @param	width		width
	* @param	height		height
	*/
	public void setSize(int width, int height)
	{
		size.setSize(width, height);
	}
	
	
	/**
	* Returns the size of the box.
	*
	* @return				size of box
	*/
	public Dimension getSize()
	{
		return size;
	}
	
	
	/**
	* Sets the color of the box.
	*
	* @param	color		box color
	*/
	public void setColor(Color color)
	{
		this.color	= color;
	}
	
	
	/**
	* Sets the box to be rounded or not.
	*
	* @param	rounded		if <code>true</code>, the box´ corners becomes 
	*						rounded
	*/
	public void setRounded(boolean rounded)
	{
		this.rounded	= rounded;
	}
	
	
	/**
	* Sets the transparency level. The value ranges from 0 (invisible) to 1
	* (opaque).
	*
	* @param	transparency
	*						transparency level
	*/
	public void setTransparency(float transparency)
	{
		if (transparency > 1f)
		{
			throw new IllegalArgumentException("Transparency ranges from 0.0 to 1.0");
		}
		
		this.transparency	= transparency;
	}
	
	
	/**
	* Sets padding.
	*
	* @param	top			padding top
	* @param	bottom		padding bottom
	* @param	left		padding left
	* @param	right		padding right
	*/
	public void setPadding(int top, int bottom, int left, int right)
	{
		// Translate the position with (x + left, y + top)
		position.translate(left, top);
		
		// Decrease the size
		setSize(size.width - left - right, size.height - top - bottom);
	}
	
	
	/**
	* Renders (paints) the box.
	*
	* @param	g			graphics context
	*/
	public void render(Graphics2D g)
	{
		Paint			prevPaint		= g.getPaint();
		Composite		prevComposite	= g.getComposite();
		AlphaComposite	alphaComposite	= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
		
		g.setComposite(alphaComposite);
		g.setPaint(color);
		
		if (rounded)
		{
			// Bra värde, typ. storlek / 2 * 10
			int		edges	= (int) ((size.width - position.x) / 20);
			
			g.fillRoundRect(position.x, position.y, size.width, size.height,
																  edges, edges);
		}
		else
		{
			g.fillRect(position.x, position.y, size.width, size.height);
		}

		// Release transparent Paint and Component
		g.setPaint(prevPaint);
		g.setComposite(prevComposite);
	}
} //end MessageBox