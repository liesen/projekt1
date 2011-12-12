package	utilities;

import	java.awt.Image;
import	java.awt.Toolkit;
import	java.io.File;
import	java.io.FileNotFoundException;
import	java.io.IOException;
import	java.util.HashMap;
import	java.util.Map;
import	java.util.Observable;
import	javax.sound.midi.Sequence;
import	javax.sound.midi.InvalidMidiDataException;
import	javax.sound.midi.MidiSystem;
import	javax.sound.sampled.AudioFileFormat;
import	javax.sound.sampled.AudioFormat;
import	javax.sound.sampled.AudioInputStream;
import	javax.sound.sampled.AudioSystem;
import	javax.sound.sampled.Clip;
import	javax.sound.sampled.DataLine;
import	javax.sound.sampled.LineUnavailableException;
import	javax.sound.sampled.UnsupportedAudioFileException;
import 	javax.swing.ImageIcon;


/**
* DataPreLoader
* Caches files that are to be used later on.
*
* @author	Jimmy Stridh
* @author	Johan Liesén
* @author	Karl Olofsson
* @version	5/16/2004
*/
public class DataPreLoader extends Observable
{
	private Map						imageCache;
	private Map 					sequenceCache;
	private Map 					clipCache;	
	private String 					lastLoadedFileType;
	private String 					lastLoadedFileName;
	private int						totalNrofFiles;
	private String					currentDir;
	private static DataPreLoader	singleton = new DataPreLoader();
	
	
	
	/**
	*
	*
	*/
	private DataPreLoader()
	{
		imageCache		= new HashMap();
		sequenceCache	= new HashMap();
		clipCache		= new HashMap();
		
		setCurrentDir("");
	}
	
	

	/**
	* Get the singleton instance of the cache.
	*
	* @return				instance of the DataPreLoader
	*/
	public static DataPreLoader getInstance()
	{
		return singleton;
	}
	
	/**
	* Returns the image specified as an Image object.
	*
	* @param	name 		name of the image resource
	* @return		The cached image, or null if it does not exist
	*/
	public Image getImage(String name)
	{
		Object	image	= imageCache.get(name);
		
		return (image != null) ? (Image) image : null;
	}
	
	
	/**
	* Change the current working directory
	* @param	path	The absolute path to the working dir
	*/
	public void setCurrentDir(String path)
	{
		// Check for a trailing slash or backslash
		if(path.length() != 0)
			if(path.charAt(path.length() - 1) != '\\' && 
										path.charAt(path.length() - 1) != '/')
				// Not present, add one
				path += "/";
				
		currentDir = new String(path);
	}
	
	
	/**
	* Returns the midi sequence specified as a Sequence object.
	*
	* @param name The name of the Sequence resource
	* @return		The cached sequence, or null if it does not exist
	*/	
	public Sequence getSequence(String name){
		return (Sequence)sequenceCache.get(name);
	}

	/**
	 * Returns the audio clip specified as a Clip object
	 * @param name The name of the Clip resource
	* @return		The cached Clip, or null if it does not exist
	 */	
	public Clip getClip(String name){
		return (Clip)clipCache.get(name);
	}
	
	/**
	* Checks whether a file exists or not
	*
	* @param	path		path to file
	*/
	private boolean fileExists(String path)
	{
		File f = new File(path);
		return f.exists();
	}
		
	
	/**
	 * Load an image file into the cache.
	 * @param path The path to the image.
	 * @param name The name to put the image under in the cache
	 */
	public void loadImage(String path, String name) throws FileNotFoundException
	{
		setChanged();
		notifyObservers(new String[] { "Image", name });		
		
		path	= currentDir + path;
		
		//check if file exists
		if(!fileExists(path))
			throw new FileNotFoundException(path);
		
		Image image = Toolkit.getDefaultToolkit().getImage(path);

		//Make sure the whole image is loaded.
		image = new ImageIcon(image).getImage();
		
		imageCache.put(name, image);
	}
	
	
	/**
	* Load a midi sequence into the cache.
	*
	* @param	path		path to the midi sequence.
	* @param	name		name to put the sequence under in the cache
	*/
	private void loadSequence(String path, String name) 
													throws FileNotFoundException
	{
		setChanged();
		notifyObservers(new String[] { "Music", name });
		
		path = currentDir + path;
		
		// check if file exists
		if(!fileExists(path))
			throw new FileNotFoundException(path);
		
		// Create sequence									   
		Sequence	sequence	= null;
		
		try
		{
			sequence	= MidiSystem.getSequence(new File(path));
			
			sequenceCache.put(name, sequence);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	* Load an audio clip into the cache.
	*
	* @param path The path to the audio clip.
	* @param name The name to put the clip under in the cache
	*/
	private void loadClip(String path, String name) throws FileNotFoundException
	{
		setChanged();
		notifyObservers(new String[] { "Sound", name });
		
		path = currentDir + path;
	
		//check if file exists
		if(!fileExists(path))
			throw new FileNotFoundException(path);

		// Create clip
		Clip	soundclip	= null;
		File	f			= new File(path);
		
		try
		{
			// Gather information about the (soon to be) clip
			AudioFileFormat		format	= AudioSystem.getAudioFileFormat(f);
			AudioInputStream	stream	= AudioSystem.getAudioInputStream(f);
			DataLine.Info		info	= new DataLine.Info(Clip.class, 
															stream.getFormat(),
															(int) (stream.getFrameLength() * format.getFormat().getFrameSize()));
			
			// Get line from the Line.Info object and open it
			soundclip	= (Clip) AudioSystem.getLine(info);
			soundclip.open(stream);
			
			// Add to the cache
			clipCache.put(name, soundclip);
		}
		// Thrown if the file doesn't point to a valid audio file data
		catch (UnsupportedAudioFileException e)
		{
			System.err.println(e);
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		// Thrown if a matching line is not available
		catch (LineUnavailableException e)		
		{
			System.err.println(e);
		}
	}


	/**
	* Load files.
	*
	*/
	public void initiateLoad()
	{
		totalNrofFiles = 33;
		
		setChanged();
		notifyObservers(new Integer(totalNrofFiles));
		
		try
		{
        	// Load images
			setCurrentDir("resources/images/");
			loadImage("splash_background.jpg", "splash_background");
			loadImage("passenger_walk_1.gif", "manWalk1");
			loadImage("passenger_walk_2.gif", "manWalk2");
			loadImage("passenger_walk_3.gif", "manWalk3");
			loadImage("passenger_walk_4.gif", "manWalk4");
			loadImage("passenger_walk_5.gif", "manWalk5");
			loadImage("passenger_walk_6.gif", "manWalk6");
			loadImage("menu_background.jpg", "menu_background");
			loadImage("ship.gif", "ship");
			loadImage("ship_thrust.gif", "ship_thrust");
			loadImage("platform.gif", "platform");
			loadImage("platform_base.gif", "platform_base");
			loadImage("ship_player_one.gif", "shipPlayer1");
			loadImage("ship_thrust_player_one.gif", "ship_thrustPlayer1");
			loadImage("ship_player_two.gif", "shipPlayer2");
			loadImage("ship_thrust_player_two.gif", "ship_thrustPlayer2");
                        
                        loadImage("platform.gif", "platform");
                        loadImage("topborder.gif", "topborder");

			loadImage("refuel_platform.gif", "refuel_platform");
			loadImage("refuel_base.gif", "refuel_base");			
			loadImage("menu_play_off.jpg", "menu_play_off");
			loadImage("menu_play_on.jpg", "menu_play_on");
			loadImage("menu_instructions_off.jpg", "menu_instructions_off");
			loadImage("menu_instructions_on.jpg", "menu_instructions_on");				
			loadImage("menu_credits_off.jpg", "menu_credits_off");		
			loadImage("menu_credits_on.jpg", "menu_credits_on");
			loadImage("menu_exit_on.jpg", "menu_exit_on");
			loadImage("menu_exit_off.jpg", "menu_exit_off");
			loadImage("play_background.jpg", "play_background");
			loadImage("shipicon.gif", "shipIcon");
			
			// Load sounds
			setCurrentDir("resources/sound/");
			loadClip("menuaccept.wav", "menuAccept");
			loadClip("menubutton.wav", "menuButton");
			loadClip("shieldbump.wav", "shieldBump");
			loadClip("taxi1.wav", "Taxi1");
			loadClip("taxi2.wav", "Taxi2");			
			loadClip("taxi3.wav", "Taxi3");			
        	
        	// Load Music
			setCurrentDir("resources/music/");
			loadSequence("menu.mid", "music_menu");
			loadSequence("in-game.mid", "music_game1");
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
} //end DataPreLoader
