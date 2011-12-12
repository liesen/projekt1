package	game.graphics;

import	java.awt.Dimension;
import	java.awt.Frame;
import	java.awt.LayoutManager;

import	utilities.DisplayModeManager;
import 	utilities.ExitOnClose;


/**
*
*
*/
public class GameFrame extends Frame
{
	/**
	*
	*
	*/
	public GameFrame(Dimension size, String caption)
	{
		super(caption);

		// Set a fixed size
		setResizable(false);
		setSize(size);

		// Make sure that the program is closed when the Frame is
		addWindowListener(new ExitOnClose());
		
		// Center this container on the screen
		DisplayModeManager.setCentered(this);
	}
} //end GameFrame