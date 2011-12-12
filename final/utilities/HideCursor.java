package utilities;

import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.awt.Point;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

/**
 *	This class will "hide" the cursor by setting a transparent image
 *	as the current cursor for the specified component.
 *	@author Jimmy Stridh
 *	@version 1.0
 */

public class HideCursor {
	
	/**
	 * Sets the cursor for the given Component to a transparent image, thus
	 * giving the illusion of it being hidden.
	 * @see Cursor
	 * @param c The component over which the cursor will be hidden.
	 */
	public static void hide(Component c){
		int[] pixels = new int[16 * 16];
		Image image = Toolkit.getDefaultToolkit().createImage(
		        new MemoryImageSource(16, 16, pixels, 0, 16));
		Cursor transparentCursor =
		        Toolkit.getDefaultToolkit().createCustomCursor
		            (image, new Point(0, 0), "invisiblecursor");
		c.setCursor(transparentCursor);
	}
}