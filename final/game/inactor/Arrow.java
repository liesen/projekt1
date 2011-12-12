package	game.inactor;

import 	java.awt.Color;
import 	java.awt.Dimension;
import 	java.awt.Graphics2D;
import 	java.awt.Point;

import 	game.graphics.Paintable;


/**
* <p>
*   An <code>Arrow</code> is an arrow. For questions, please call 
*   555-WHAT-IS-AN-ARROW.
* </p>
*
* <p>
*   Note that the position of the arrow is where its sharp edge points.
* </p>
*
* @author	Johan Liesén
*/
public class Arrow implements Paintable
{
	private Color		color;
	private Point		position;
	private	Dimension	size		= new Dimension(50, 50);
	
	
	
	/**
	* Creates an Arrow.
	*
	* @param	c			color of arrow
	*/
	public Arrow(Color c)
	{
		color		= c;
		position	= new Point(0, 0);
	}
	
	
	
	/**
	* Update the position of where to draw the arrow.
	*
	* @param	x			<em>x</em> coordinate of new location
	* @param	y			<em>y</em> coordinate of new location
	*/
	public void setPosition(int x, int y)
	{
		position.setLocation(x, y);
	}
	
	
	/**
	* Returns arrow position.
	*
	* @return				arrow position
	*/
	public Point getPosition()
	{
		return position;
	}
	
	
	/**
	* Draw arrow.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		Color	c	= g.getColor();
		
		g.setColor(color);
		
		// Paint a filled arc with the sharp edge pointing on the given location
		// and the arc ranges clockwise from 110 to 70 degrees (90 being 12 
		// o'clock)
		g.fillArc(position.x - (size.width / 2),
				  position.y - size.height,
				  size.width,
				  size.height,
				  110,
				  -40);
				  
		g.setColor(c);
	}
	
	
	/**
	* Paints the arrow on (<em>x</em>, <em>y</em>) by first calling 
	* <code>setLocation(<em>x</em>, <em>y</em>)</code> and then use
	* <code>paint(g)</code>
	*
	* @param	g			graphics context
	* @param	x			<em>x</em> coordinate
	* @param	y			<em>y</em> coordinate
	* @see		#setPosition(int, int)
	* @see		#paint(Graphics2D)
	*/
	public void paint(Graphics2D g, int x, int y)
	{
		setPosition(x, y);
		paint(g);
	}
} //end Arrow