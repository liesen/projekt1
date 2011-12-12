package game.audio;

import javax.sound.sampled.Clip;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.util.Map;
import java.util.HashMap;

import utilities.DataPreLoader;


/**
* 
* AudioPlayer plays audio clips and midi sequences. It uses a datapreloader as a
* container for pre-loaded files.
*
* @author	Jimmy Stridh
* @author	Johan Liesén
* @version	1.1
*/
public class AudioPlayer
{
	/** Singleton instance of this class */
	private static AudioPlayer	singleton	= new AudioPlayer();
	
	/** The data repository */
	private DataPreLoader dpl;
	
	/** The midi sequencer of the system */
	private Sequencer sequencer;
	
	/** Currently playing sequence	*/
	private String currentSequence = "";
	
	
	
	/**
	* Internal constructor used to initialize instance fields
	*
	*/
	private AudioPlayer()
	{
		dpl = DataPreLoader.getInstance();
		
		try
		{
			// Create a sequencer for the sequence
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

	    } 
	    catch (MidiUnavailableException e)
	    {
			System.err.println(e);
		}
	}


	/**
	* Get the singleton instance of the audio player.
	*
	* @return	Instance of the AudioLoader
	*/
	public static AudioPlayer getInstance()
	{
		return singleton;
	}
		
	
	/**
	*	Play sound effect
	*
	*	@param	key	The name of the file from the data repository.
	*
	*/
	public void playEffect(String key, boolean interruptable)
	{
		Clip	clip	= dpl.getClip(key);
		
		if (clip != null)
		{
			playClip(clip, interruptable);
		}
	}
	
	
	/**
	*	Play music track
	*
	*	@param	key	The name of the file from the data repository
	*
	*/
	public void playMusic(String key)
	{
		Sequence	seq	= dpl.getSequence(key);

		
		if ((seq != null) && (currentSequence != key))
		{
			//Change the indicator of currently playing sequence.
			currentSequence	= key;
			playSequence(seq);
		}
		
	}
	
	
	/**
	* Instantly play a clip.
	*
	* @param	clip		audio clip to play
	*/	
	public void playClip(Clip clip, boolean interruptable){
		if(interruptable)
		{
			if (clip != null)
			{
				clip.setFramePosition(0);
				clip.start();
			}
		}
		else
		{
			if (clip != null && !clip.isRunning())
			{
				clip.setFramePosition(0);
				clip.start();
			}
		}
	}
	
	
	/**
	* Play (MIDI) sequence.
	*
	* @param	seq			sequence to play
	*/
	public void playSequence(Sequence seq){
		try
		{
			if (!sequencer.isRunning()) 
			{
				sequencer.stop();
			}

			// Initiate
		    sequencer.setSequence(seq);
		    
		    // Start playing (loop)
		    sequencer.start();
		} 
		catch (InvalidMidiDataException e) 
		{
			System.err.println(e);
	    }
	}
} //end class AudioPlayer
