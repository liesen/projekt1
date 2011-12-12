import game.GameController;
import game.graphics.screens.SplashScreen;


/**
* Starts the game by showing the splash screen and then leaving over control
* to the game controller.
* Command line <c>-nofullscreen</c> will start the game windowed.
*
* @author Jimmy Stridh
* @version 1.1
*/
public class Main
{
	public static void main(String[] args)
	{
		boolean fullscreen	=	true;
		
		//If fullscreen for some reason w
		if(args.length > 0)
		{
			if(args[0].equals("-nofullscreen"))
			{
				fullscreen = false;
			}
		}
		
		//Show the splash screen, which will start the sound
		//subsystem and do data caching. All this, while being pretty...
		SplashScreen splash = new SplashScreen();
		
		//Create game controller, which will run the game.
		GameController gc = new GameController(fullscreen);
	}
}
