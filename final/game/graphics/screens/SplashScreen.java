package game.graphics.screens;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;

import utilities.DataPreLoader;
import game.graphics.ProgressBar;
import game.graphics.BufferedCanvas;
import utilities.DisplayModeManager;
import game.audio.AudioPlayer;

/**
 *	Displays a splash screen and starts the data caching in the background
 *	@author Jimmy Stridh
 *
 */
public class SplashScreen extends Frame implements Observer
{
	private SplashScreenCanvas canvas;
	private int height = 480;
	private int width = 640;
	private DataPreLoader dpl;
	
	/**
	 * Creates the splash screen
	 */	
	public SplashScreen() {
		//Set the background color
		setBackground(Color.BLACK);
		
		//Create the canvas which will be displayed
		canvas = new SplashScreenCanvas(width,height);
		
		//Remove window decoration
		setUndecorated(true);
		
		//Add the canvas to the frame
		add(canvas);
		
		setSize(width,height);

		//Center the frame on the screen
		DisplayModeManager.setCentered(this);
		
		//Display it
		setVisible(true);
		
		//perform the initializations: data caching, init audio player etc.
		init();
	}
	
	/**
	 * Perform some initializations for the game eg. initiate audioplayer,
	 * data cache.
	 */
	public void init()
	{
		//Create the audioplayer
		canvas.setMessage("Initiating AudioPlayer");
		AudioPlayer.getInstance();
	
		//Create the datacache
		canvas.setMessage("Initiating Data Cache");
		dpl = DataPreLoader.getInstance();
		dpl.addObserver(this);
		//Begin caching		
		dpl.initiateLoad();
		
		//when all done, remove the splash screen
		dispose();
	}
	
	/**
	*	Called when a file has been loaded in the data cache.
	*	Updates the text and progressbar on the canvas.
	*/
   public void update(Observable obs, Object obj)
   {
		if (obs == dpl)
		{
			if(obj instanceof Integer)
			{
				Integer nrOfFiles = (Integer) obj;
				canvas.getProgressBar().setMax(nrOfFiles.intValue());
			} 
			else
			{			
				String[] obj2	= (String[]) obj;
				String type		= (String) obj2[0];
				String name		= (String) obj2[1];
				
				canvas.getProgressBar().incBar(1);
				canvas.setMessage("Loading " + type + ": " + name);
			}
		}
 		repaint();
   }
}