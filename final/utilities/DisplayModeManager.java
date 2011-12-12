package utilities;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.DisplayMode;
import java.awt.Point;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;


/**
* Contains methods for altering the way a window is displayed on the screen.
*
*
*/
public abstract class DisplayModeManager
{
	/** The local graphics environment */
	private static GraphicsEnvironment 	gEnv = GraphicsEnvironment.
												  getLocalGraphicsEnvironment();
	/** The local graphics device */											  
	private static GraphicsDevice 		gDevice = GraphicsEnvironment.
										      getLocalGraphicsEnvironment().
										       getDefaultScreenDevice();
	
	/**
	*	Make a window be displayed full screen in a 640x480 resolution
	*
	*	@param	fsFrame	the frame to be displayed fullscreen
	*/
	public static void setFullscreen(Frame fsFrame)
	{
		fsFrame.setUndecorated(true);
		gDevice.setFullScreenWindow(fsFrame);
		selectFullScreenDisplayMode();
	}
	
	/**
	*	Make a frame windowed
	*	@param	windowedFrame	the frame to set to windowed
	*/
	public static void setWindowed(Frame windowedFrame)
	{
		windowedFrame.setUndecorated(false);
		gDevice.setFullScreenWindow(windowedFrame);
		setCentered(windowedFrame);
	}
	
    /**
    *	Retrieve the best display mode out of a list. The list contains the modes:
    *	 640x480 32bit color, 640x480 16bit color and 640x480 8bit color.
    */    
    private static DisplayMode getBestDisplayMode() {
    	DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
        	new DisplayMode(640, 480, 32, 0),
        	new DisplayMode(640, 480, 16, 0),
        	new DisplayMode(640, 480, 8, 0)
    	};
        
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = gDevice.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                   && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                   && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()
                   ) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }
    
    /**
    *	Change to the best available display mode
    */    
    private static void selectFullScreenDisplayMode() {
        DisplayMode best = getBestDisplayMode();
        if (best != null) {
            gDevice.setDisplayMode(best);
        }
    }	
	
	/**
	* Sets the location of a <code>Container</code> to be centered on the 
	* screen.
	* 
	* @param	c			container to center
	*/
	public static void setCentered(Container c)
	{
		Point center			= gEnv.getCenterPoint();
		Point relativeCenter	= new Point(center.x - c.getWidth() / 2,
											center.y - c.getHeight() / 2);
		
		c.setLocation(relativeCenter);
	}
} //end DisplayModeManager
