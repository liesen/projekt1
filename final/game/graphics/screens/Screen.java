package	game.graphics.screens;

import	java.awt.Dimension;
import 	java.awt.Graphics2D;
import 	java.awt.Image;

import	game.GameController;
import	game.graphics.BufferedCanvas;
import	game.input.KeyboardController;


/**
* A screen is used by the game to draw upon and to interact with the user via
* different "controllers". Each screen can contain a background image, therefor 
* must classes who inherits from <code>Screen</code> call 
* <code>super.render(Graphics2D )</code>.
*
* @author	Johan Liesén
* @author	Jimmy Stridh
* @version	5/11/2004 7:40PM
*/
public abstract class Screen extends BufferedCanvas
{
	private GameController	gc;
	private Image			backgroundImage;
	
	
	
	/**
	* Creates a screen.
	*
	* @param	size		component size
	* @param	gc			the game controller
	* @param	keyb		keyboard input handler
	*/
	public Screen(Dimension size, GameController gc, KeyboardController keyb)
	{
		super(size);
		addKeyListener(keyb);
		
		this.gc	= gc;
	}
	
	
	
	/**
	* Set background image.
	*
	* @param	bgImg		image object
	*/
	public void setBackground(Image bgImg)
	{
		backgroundImage	= bgImg;
	}
	
	
	/**
	* Render screen. Subclasses needs to call this for the background image to
	* be drawn.
	*
	* @param	g			graphics context
	*/
	public void render(Graphics2D g)
	{
		if (backgroundImage != null)
		{
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
} //end Screen