package	game.graphics;

import	java.awt.Color;
import	java.awt.Font;
import	java.awt.Graphics2D;
import	java.awt.Rectangle;

import	game.Engine;
import	game.Player;
import	game.graphics.box.MessageBox;


/**
*
*
*/
public class ScoreBoard extends MessageBox
{
	private Engine		engine;
	private	int[]		columnStops;
	private Font		font;
	
	

	public ScoreBoard(Rectangle bounds, Engine engine)
	{
		super(bounds, Color.BLACK, 0.25f);
		
		font		= new Font("Arial", Font.PLAIN, 18);
		
		this.engine	= engine;
		
		columnStops	= new int[] { 10, 100, 400 }; 
	}
	
	
	
	public void render(Graphics2D g)
	{
		// Paint the box
		super.render(g);
		
		Player[]	players			= engine.getPlayers();
		int			drawPositionY	= 20;	// Start value 20 px from top
	
		for (int i = 0, column = 0; i < players.length; i++, column = 0)
		{
			g.setFont(font);
			
			g.drawString(players[i].getName(), columnStops[column++], drawPositionY);
			g.drawString(players[i].getScore() + "", columnStops[column++], drawPositionY);
			g.drawString(players[i].getCredits() + "", columnStops[column++], drawPositionY);
			
			drawPositionY	+= 50;
		}
	}
} //end ScoreBoard
