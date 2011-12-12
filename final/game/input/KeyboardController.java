package	game.input;

import	java.awt.event.KeyEvent;
import	java.awt.event.KeyListener;

import	game.GameController;


/**
* KeyboardController maps input keycodes into action codes and sends them to a 
* game controller.
*
* @author	Jimmy Stridh
*/
public class KeyboardController implements KeyListener
{
	private GameController	gc;		// Reference to a GameController
	
	
	
	/**
	* Create a KeyboardController.
	*
	* @param	gc		GameController reference
	*/
	public KeyboardController(GameController gc)	//, Map keys)
	{
		this.gc		= gc;
		// this.keys	= keys;
	}
	
	
	
	/**
	* Called when a key is pressed. Sends update to the game controller about 
	* what button that was pressed.
	*
	* @param	ke			keyboard event info
	*/
	public void keyPressed(KeyEvent ke)
	{
		gc.handleKeyboardInput(ke.getKeyCode(), true);
	}
	
	
	/**
	* Called when a key is released. Sends update to the game controller about 
	* what button that was released.
	*
	* @param	ke			keyboard event info
	*/
	public void keyReleased(KeyEvent ke)
	{ 
		gc.handleKeyboardInput(ke.getKeyCode(), false);
	}
			
	
	// Has to be implemented as of interface KeyListener
	public void keyTyped(KeyEvent ke)
	{
		
	}
} //end KeyboardController