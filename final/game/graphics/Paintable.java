package	game.graphics;

import	java.awt.Graphics2D;


/**
* Used to make sure that objects that implements this interface can be painted.
* 
* @author	Karl Olofsson
* @version	2004-05-05 18:59
*/
public interface Paintable
{
    /**
    * Used for painting.
    * 
    * @param  	g			graphics context
    */
    public void paint(Graphics2D g);
} //end Paintable