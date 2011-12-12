package	game.graphics.screens;

import 	java.awt.Color;
import 	java.awt.Dimension;
import 	java.awt.Graphics2D;
import	java.awt.Rectangle;

import	game.Engine;
import	game.GameController;
import	game.graphics.ScoreBoard;
import	game.graphics.Statusbar;
import 	game.input.KeyboardController;
import	utilities.DataPreLoader;


/**
*
*	This is the screen on which the game itself will be showed
*
*	@author		Karl Olofsson
*	@author		Jimmy Stridh
*	@author		Johan Liesén
*	@version	1.1-TREKRONOR
*
*/
public class PlayScreen extends Screen
{
	private Engine				engine;
	private	GameController		controller;
	private boolean				showScoreBoard;
	private Statusbar			statusbar;
	
	
	
	/**
	* The constructor for PlayScreen
	*
	*/
	public PlayScreen(Dimension size, GameController gc,  
									KeyboardController keyb, Engine engine)
	{	
		super(size, gc, keyb);
		
		this.engine = engine;
		engine.setPlayScreen(this);
		
		statusbar	= new Statusbar(size, engine);
		
		setBackground(DataPreLoader.getInstance().getImage("play_background"));
	}
	
	/**
	* Paints the game
	*
	*/
	public void render(Graphics2D g)
	{
		// Render background
		super.render(g);
		
		// Render statusbar
		statusbar.render(g);
		
		// Render the game!
		engine.render(g);
	}
} //end PlayScreen
