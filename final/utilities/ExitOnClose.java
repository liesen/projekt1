package	utilities;

import	java.awt.event.WindowAdapter;
import 	java.awt.event.WindowEvent;


/**
* Class for automatically exiting a program when a window is closed.
*
*/
public final class ExitOnClose extends WindowAdapter
{
	/**
	* Invoked when a window is being closed.
	*
	*/
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
} //end ExitOnClose