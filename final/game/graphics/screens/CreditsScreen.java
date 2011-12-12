package	game.graphics.screens;

import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Font;
import	java.awt.Graphics2D;
import	java.awt.RenderingHints;
import	java.awt.font.TextAttribute;
import	java.util.HashMap;
import	java.util.Map;

import	game.GameController;
import	game.graphics.box.MessageBox;
import	game.graphics.box.TextBox;
import	game.input.KeyboardController;
import	utilities.DataPreLoader;


/**
*	Displays a credits screen with info about the programmers and such.
*	@author Johan Liesén
*	@author	Karl Olofsson
*	@author Jimmy Stridh
*
*/
public class CreditsScreen extends Screen
{
	/** Transparent box (container for textextBox) */
	private MessageBox	messageBox;
	
	/** Box with text */
	private	TextBox		textBox;
        private TextBox		greetingsBox;
	private	TextBox		goBackTextBox;
	
	
	
	/**
	*	Constructs a Credits screen with the specified dimensions
	*	@param	size	Dimensions of the screen
	*	@param	gc		The GameController
	*	@param	keyb	The keyboard controller
	*/
	public CreditsScreen(Dimension size, GameController gc, KeyboardController keyb)
	{
		super(size, gc, keyb);
		
		// MessageBox with padding, black background 
		// (transp.) and rounded
		messageBox		= new MessageBox(0, 0, size.width, size.height,
										new Color(25, 25, 25), 0.50f, true);
		messageBox.setPadding(140, 60, 40, 40);
		
		// Go back!
		HashMap				goBackTextAttr	= new HashMap();
		goBackTextAttr.put(TextAttribute.FONT, new Font("Arial", Font.ITALIC, 13));
		
		String				goBackText	= "Press ESC to return to main menu";
		
		goBackTextBox	= new TextBox(size.width - 250, size.height - 30, 
									  size.width, size.height,
									  goBackText, Color.WHITE, goBackTextAttr);
		
		// Format text
		HashMap				attr	= new HashMap();
		attr.put(TextAttribute.FONT, new Font("Arial", Font.BOLD, 14));
		
		// Text
		String				text	= "Developed by Johan Liesén, Karl Olofsson and Jimmy Stridh in Project " +
									  "IT1 at Information Engineering, " + 
									  "Chalmers Univerity of Technology, 2004.";
		
		// Create text box
		textBox	= new TextBox(messageBox.getBounds(), text, Color.WHITE, attr);
		textBox.setPadding(25, 10, 10, 10);
                
                text		= "Greetings goes out to Lena Ph for helping us in the hard, cold and windy moments. OH IT HURTS";  
                greetingsBox = new TextBox(messageBox.getBounds(), text, Color.WHITE, attr);
                greetingsBox.setPadding(80, 10, 10, 10);
		
		//
		setBackground(DataPreLoader.getInstance().getImage("menu_background"));
	}
	
	
	
	/**
	*	Render the Credits screen on the specified graphics context.
	*	@param	g	The Graphics2D object the paint on.
	*/
	public void render(Graphics2D g)
	{
		// Paint background
		super.render(g);
		
		// Hints to make the text look really nice!
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						   RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						   RenderingHints.VALUE_ANTIALIAS_ON);
		
		// White text
		g.setColor(Color.WHITE);
		
		// Draw transparent bounding box
		messageBox.render(g);
		
		// Draw text
		goBackTextBox.render(g);
		textBox.render(g);
                greetingsBox.render(g);
	}
} //end class CreditsScreen