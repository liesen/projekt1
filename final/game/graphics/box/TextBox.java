package	game.graphics.box;

import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Graphics2D;
import	java.awt.Point;
import	java.awt.Rectangle;
import	java.awt.font.LineBreakMeasurer;
import	java.awt.font.TextLayout;
import	java.text.AttributedString;
import	java.text.AttributedCharacterIterator;
import	java.util.Map;
import	java.util.HashMap;


/**
* The <code>TextBox</code> is a box which contains text. The text inside the box
* is formatted in a way which takes linebreaks into consideration, which can be
* nice when having small areas to work with.
*
* @author	Johan Liesén
* @author	Jimmy Stridh
*/
public class TextBox
{
	/** Top left corner position */
	private	Point				position;
	
	/** Box size */
	private Dimension			size;
	
	/** The text in the box */
	private	AttributedString	text;
	
	/** Text color */
	private	Color				color;
	
	/** Fotn attributes */
	private	Map					attributes;
	
	
	
	/**
	* Creates a text box with default (none) font attributs.
	*
	* @param	bounds		bounding box
	* @param	text		the text
	* @param	color		text color
	*/
	public TextBox(Rectangle bounds, String text, Color color)
	{
		this(bounds, text, color, null);
	}
	
	
	/**
	* Creates a text box.
	*
	* @param	bounds		bounding box
	* @param	text		the text
	* @param	color		text color
	* @param	attributes	font attributes
	*/
	public TextBox(Rectangle bounds, String text, Color color, Map attributes)
	{
		this(bounds.x, bounds.y, bounds.width, bounds.height, text, color, attributes);
	}
	
	
	/** 
	* Creates a text box with default (none) font attributs.
	*
	* @param	x			x position
	* @param	y			y position
	* @param	width		box width
	* @param	height		box height
	* @param	text		the text
	* @param	color		text color
	*/
	public TextBox(int x, int y, int width, int height, String text, 
																	Color color)
	{
		this(x, y, width, height, text, color, null);
	}
	
	
	/**
	* Creates a text box.
	*
	* @param	x			x position
	* @param	y			y position
	* @param	width		box width
	* @param	height		box height
	* @param	text		the text
	* @param	color		text color
	* @param	attributes	font attributes
	*/
	public TextBox(int x, int y, int width, int height, String text, 
													Color color, Map attributes)
	{
		position	= new Point(x, y);
		size		= new Dimension(width, height);
		setText(text, attributes);
		setPadding(0, 0, 0, 0);
		setColor(color);
	}
	
	
	
	/**
	* Returns the bounding box.
	*
	* @return				box boundaries
	*/
	public Rectangle getBounds()
	{
		return new Rectangle(position.x, position.y, size.width, size.height);
	}
	
	
	/**
	* Sets the text which is displayed.
	*
	* @param	text		text
	*/
	public void setText(String text)
	{
		this.text	= new AttributedString(text);
	}
	
	
	/**
	* Sets formatted text to be displayed.
	*
	* @param	text		text
	* @param	attributes	font attributes
	*/
	public void setText(String text, Map attributes)
	{
		if (attributes == null)
		{
			setText(text);
			return;
		}
		
		this.text	= new AttributedString(text, attributes);
	}
	
	
	/**
	* Sets width and height of the box.
	*
	* @param	width		width
	* @param	height		height
	*/
	public void setSize(int width, int height)
	{
		size.setSize(width, height);
	}
	
	
	/**
	* Sets the text color.
	*
	* @param	c			text color
	*/
	public void setColor(Color c)
	{
		color	= c;
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
		position.translate(left, top);
		
		setSize(size.width - left - right, size.height - top - bottom);
	}
	
	
	/**
	* Renders (paints) the box.
	*
	* @param	g			graphics context
	*/
	public void render(Graphics2D g)
	{
		AttributedCharacterIterator	aci		= text.getIterator();
		LineBreakMeasurer			lbm		= new LineBreakMeasurer(aci, g.getFontRenderContext());
		float						wrap	= size.width,
									posX	= position.x,
									posY	= position.y;
		
		g.setColor(color);
		
		while (lbm.getPosition() < aci.getEndIndex())
		{
			// New layout for this line (what abound the 2nd param?!)
			TextLayout	layout	= lbm.nextLayout(wrap, (int) wrap, false);
			
			// Calc. new X coordinate
			posX	= layout.isLeftToRight() ? position.x : wrap - layout.getAdvance();
			
			// Paint the text
			layout.draw(g, posX, posY);
			
			// Calc. new Y position (1.5 is used to give more vertical spacing)
			posY	+= layout.getAscent() * 1.5f + layout.getLeading();
		}
	}
} //end TextBox