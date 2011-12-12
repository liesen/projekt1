package utilities;

import 	java.awt.Graphics;
import 	java.awt.GraphicsConfiguration;
import 	java.awt.GraphicsDevice;
import 	java.awt.GraphicsEnvironment;
import 	java.awt.HeadlessException;
import 	java.awt.Image;
import 	java.awt.Transparency;
import 	java.awt.image.BufferedImage;
import 	java.awt.image.ColorModel;
import 	java.awt.image.PixelGrabber;
import 	javax.swing.ImageIcon;

/**
*	This class contains helper methods for loading and using images.
*	Since there is no way to convert an <code>Image</code> to a <code>
*	BufferedImage </code> using the API, this code is needed. Most of it comes
*	from http://javaalmanac.com/egs/java.awt.image.
*/
public class ImageHelper{

// below code is from http://javaalmanac.com/egs/java.awt.image/HasAlpha.htm	
	/**
	 *	Checks if a specified image has transparent pixels
	 *	@param image	The image to be tested
	 *	@return		true if the image has transparent pixels
	 */   
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
	        if (image instanceof BufferedImage) {
	        	BufferedImage bimage = (BufferedImage)image;
	        	return bimage.getColorModel().hasAlpha();
	        }
	    
	        // Use a pixel grabber to retrieve the image's color model;
	        // grabbing a single pixel is usually sufficient
	         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
	        try {
	            pg.grabPixels();
	        } catch (InterruptedException e) {
	        }
	    
	        // Get the image's color model
	        ColorModel cm = pg.getColorModel();
	        //return cm.hasAlpha();
	        
	        // Fix (?) by Liesén @ 2004-05-14 00:36
	        return (cm == null) ? false : cm.hasAlpha();
	    }
	    
//below code is from http://javaalmanac.com/egs/java.awt.image/Image2Buf.html   
	/**
	*	Converts an <code>Image</code> object to a <code>BufferedImage</code>
	*	object
	*	@param	image	The <code>Image</code> object to be converted
	*	@return		a <code>BufferedImage</code> conversion from the input
	*/
	// This method returns a buffered image with the contents of an image
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage)image;
	        }
	    
	        // This code ensures that all the pixels in the image are loaded
	        image = new ImageIcon(image).getImage();
	    
	        // Determine if the image has transparent pixels; 
	        // for this method's
	        // implementation, see Determining If an Image Has
	        // Transparent Pixels
	        boolean hasAlpha = hasAlpha(image);
	    
	        // Create a buffered image with a format that's compatible
	        // with the screen
	        BufferedImage bimage = null;
	        GraphicsEnvironment ge = GraphicsEnvironment.
	        				  getLocalGraphicsEnvironment();
	        try {
			// Determine the type of transparency of the new
			//				 buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}
			
			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(
			image.getWidth(null),image.getHeight(null),transparency);
	        } catch (HeadlessException e) {
	            // The system does not have a screen
	        }
	    
	        if (bimage == null) {
	            // Create a buffered image using the default color model
	            int type = BufferedImage.TYPE_INT_RGB;
	            if (hasAlpha) {
	                type = BufferedImage.TYPE_INT_ARGB;
	            }
	            bimage = new BufferedImage(image.getWidth(null),
	            				   image.getHeight(null), type);
	        }
	    
	        // Copy image to buffered image
	        Graphics g = bimage.createGraphics();
	    
	        // Paint the image onto the buffered image
	        g.drawImage(image, 0, 0, null);
	        g.dispose();
	    
	        return bimage;
	    }
}//end class ImageHelper