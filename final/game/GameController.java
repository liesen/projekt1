package	game;

import	java.awt.Dimension;
import	java.awt.Graphics2D;
import	java.awt.event.KeyEvent;
      	
import	game.Engine;
import	game.audio.AudioPlayer;
import	game.graphics.GameFrame;
import	game.graphics.BufferedCanvas;
import	game.graphics.screens.CreditsScreen;
import game.graphics.screens.InstructionScreen;
import	game.graphics.screens.MenuScreen;
import	game.graphics.screens.PlayScreen;
import	game.graphics.screens.ScreenManager;
import	game.input.KeyboardController;
import	utilities.DataPreLoader;
import	utilities.DisplayModeManager;
import	utilities.ExitOnClose;
import	utilities.HideCursor;


/**
*	Is the main controller of the entire game. Is always active, controlling
*	what will happen on keypresses, starting and stopping the game engine etc.
*
*	@author Jimmy Stridh
*	@author	Karl Olofsson
*	@author Johan Liesén
*
*/
public class GameController
{
	/** Screen size */
	private final Dimension		SCREEN_SIZE = new Dimension(640, 480);
	
	/** The ghettoblaster */
	private AudioPlayer			audio;
	
	/** The data repository */
	private DataPreLoader		data;
	
	/** Frame which holds the screens */
	private GameFrame			gameFrame;
	
	/** Keyboard input handler */
	private KeyboardController	keyboard;
	
	/** Manager with "ez-switchin'-between-components"-functionality */
	private ScreenManager		screens;
	
	/** The core engine */
	private Engine				engine;

	
	
	/**
	*	The constructor of the game engine.
	*	
	*	@param 	fullscreen	Determines if the game should be run fullscreen or not.
	*/
	public GameController(boolean fullscreen)
	{
		System.out.println(fullscreen);
		
		// Get data repository (data caching should already have been performed)
		data		= DataPreLoader.getInstance();
		
		// Get audio player
		audio		= AudioPlayer.getInstance();
		
		// Create input handler
		keyboard	= new KeyboardController(this);
		
		// Initiate engine
		engine		= new Engine(this);
		
		// Build screens and their 'manager'
		gameFrame 	= new GameFrame(SCREEN_SIZE, "Interstellar Taxi");
		screens		= new ScreenManager(gameFrame);
		
		//Use the screen manager as the layout of the game frame/window.
		gameFrame.setLayout(screens);
		
		//Assign an icon to it instead of the standard Java one.
		gameFrame.setIconImage(data.getImage("shipIcon"));
		
		
		gameFrame.add(new InstructionScreen(SCREEN_SIZE, this, keyboard), "instruction");
		gameFrame.add(new CreditsScreen(SCREEN_SIZE, this, keyboard), "credits");
		gameFrame.add(new PlayScreen(SCREEN_SIZE, this, keyboard, engine), "play");
		gameFrame.add(new MenuScreen(SCREEN_SIZE, this, keyboard), "menu");
		
		//Hide the mouse cursor when over the game frame		
		HideCursor.hide(gameFrame);
		
		//Go fullscreen if it wasn't explicitly disabled
		if(fullscreen)
			DisplayModeManager.setFullscreen(gameFrame);
		else
			//Pack the frame to get correct dimensions if running windowed.
			gameFrame.pack();
		
		gameFrame.setVisible(true);
		screens.show("menu");
		audio.playMusic("music_menu");
	}
	
	
	
	/**
	*	Is called from the KeyboardController when a key is pressed or depressed.
	*	@param	keycode	The constant of the key activated.
	*	@param	keyDown	<c>True</c> if the key was pressed, <c>false</c> if 
	*			depressed.
	*/
	public void handleKeyboardInput(int keyCode, boolean keyDown)
	{
		//Get the current screen name
		String	currentScreenName	= screens.getCurrentName();
		
		// NOTE: The menu screen reacts only when the key is pressed.
		if (currentScreenName.equals("menu") && keyDown)
		{
			MenuScreen	screen	= (MenuScreen) screens.getCurrent();
			
			switch (keyCode)
			{
				case KeyEvent.VK_DOWN:
					screen.selectNext();
					screen.repaint();
					audio.playEffect("menuButton", true);
					break;
					
				case KeyEvent.VK_UP:
					screen.selectPrevious();
					screen.repaint();					
					audio.playEffect("menuButton", true);
					break;
					
				case KeyEvent.VK_ENTER:					
					audio.playEffect("menuAccept", false);					
					switch (screen.getSelectedButtonID())
					{
						case 0:	// Play
							engine.init();
							screens.show("play");
							engine.start();
							audio.playMusic("music_game1");							
							break;
							
						case 1: // Instruction
							screens.show("instruction");
							break;
							
						case 2: // Credits
							screens.show("credits");
							break;
							
						case 3: // Exit
							System.exit(0);
							break;
					}
					break;
				
				//Operation not supported, do nothing.
				default:
					break;
			}
		}
		else if ((currentScreenName.equals("instruction") || 
				  currentScreenName.equals("credits")) && keyDown)
		{
			
			//For both the instructions screen and the credits screen, ESCAPE
			//and ENTER will take you back to the menu.
			switch (keyCode)
			{
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_ESCAPE:
					screens.show("menu");
					break;
					
				default:
					break;
			}
		}
		else if (currentScreenName.equals("play"))
		{			
			switch (keyCode)
			{
				// Player 1: Go left
				case KeyEvent.VK_LEFT:
					engine.p1Left(keyDown);
					break;
					
				// Player 1: Go right
				case KeyEvent.VK_RIGHT:
					engine.p1Right(keyDown);				
					break;
					
				// Player 1: Thrust
				case KeyEvent.VK_UP:
					engine.p1Up(keyDown);				
					break;
					
				// Player 1: Thrust
				case KeyEvent.VK_DOWN:
					engine.p1Down(keyDown);
					break;
					
				// Player 2: Go left
				case KeyEvent.VK_A:
					engine.p2Left(keyDown);				
					break;
					
				// Player 2: Go right
				case KeyEvent.VK_D:
					engine.p2Right(keyDown);					
					break;
					
				// Player 2: Thrust
				case KeyEvent.VK_W:
					engine.p2Up(keyDown);				
					break;
					
				// Player 2: Thrust
				case KeyEvent.VK_S:
					engine.p2Down(keyDown);				
					break;
					
				case KeyEvent.VK_ESCAPE:
					engine.stop();
					screens.show("menu");
					audio.playMusic("music_menu");					
					break;
			}
		}
	}
} //end class GameController