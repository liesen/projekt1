package game.graphics;

import	java.awt.Canvas;
import	java.awt.Dimension;
import	java.awt.Graphics;
import	java.awt.Graphics2D;
import	java.awt.Image;


/**
* A <code>{@link Canvas}</code> using double-buffering.
*
*/
public abstract class BufferedCanvas extends Canvas 
{
	/** Offscreen buffer */
	private Image		offscreenImage;
	
	/** Graphics context assigned to the offscreen image */
	private Graphics	offscreenGraphics;
	
	/** Size of the offscreen image */
	private Dimension	offscreenSize;
	
	
	
	/**
	*
	*
	*/
	public BufferedCanvas(Dimension d)
	{
		setSize(d);
	}
	


	/**
	* Off-screen version that overrides default update(Graphics) method. All 
	* drawings are done off-screen before posted.
	*
	* @param	g		graphics context
	*/
	public final synchronized void update(Graphics g)
	{
		int width	= getSize().width;
		int height	= getSize().height;


		if ((offscreenImage == null) ||	
			(width != offscreenSize.width) ||
			(height != offscreenSize.height))
		{
			// Create new offscreen images
			offscreenImage		= createImage(width,height );
			offscreenSize		= getSize();
			offscreenGraphics	= offscreenImage.getGraphics();
			
			// What's this good for?
			offscreenGraphics.setFont(getFont());
		}

		// Paint to the buffer image
		paint(offscreenGraphics);
		
		// Publish the buffer
		g.drawImage(offscreenImage, 0, 0, this);
	}
	
	
	/**
	* Paints the Canvas.
	*
	* @param	g		graphics context
	*/
	public void paint(Graphics g)
	{
		render((Graphics2D) g);
	}
	
	
	/**
	* Overridden by subclasses to perform drawing.
	*
	* @param	g		graphics context
	*/
	public abstract void render(Graphics2D g);
} //end BufferedCanvas