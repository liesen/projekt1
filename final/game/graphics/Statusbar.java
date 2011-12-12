package	game.graphics;

import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Font;
import	java.awt.FontMetrics;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.util.HashMap;
import	java.util.Map;

import	game.Engine;
import	game.Player;
import	utilities.DataPreLoader;


/**
* The Statusbar provides the <code>{@link Player}</code> with information about
* his Ship.
*
* @author	Johan Liesén
*/
public class Statusbar
{
	/** Size of the bar */
	private Dimension		size;

	/** Game engine */
	private Engine			engine;
	
	/** Font to draw the text in */
	private Font			font;
	
	
	
	/**
	* Creates a status bar placed at (<em>0</em>, <em>0</em>).
	*
	* @param	size		bar size
	* @param	e			game engine
	*/
	public Statusbar(Dimension size, Engine e)
	{
		this.size				= size;
		this.engine				= e;
		
		// Font
		this.font				= new Font("Arial", Font.PLAIN, 11);						  
	}
	
	
	/**
	* Renders/paints the status bar.
	*
	* @param			graphics context
	*/
	public void render(Graphics2D g)
	{
		g.setFont(font);
		
		FontMetrics	metrics	= g.getFontMetrics();
		
		// Get players
		Player[]	players		= engine.getPlayers();
		int			boxLength	= size.width / players.length;
		int			boxHeight	= metrics.getHeight();
		
		for (int i = 0; i < players.length; i++)
		{
			g.setColor(players[i].getColor());
			
			g.drawString(players[i].getName() + ": " + players[i].getScore() + " (" + players[i].getShip().getEnergy() + ")", 
						 5 + i * boxLength,
						 boxHeight);
		}
	}
} //end class Statusbar