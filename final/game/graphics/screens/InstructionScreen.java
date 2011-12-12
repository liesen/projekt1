package	game.graphics.screens;

import	game.GameController;
import	game.graphics.box.MessageBox;
import	game.graphics.box.TextBox;
import	game.input.KeyboardController;
import	utilities.DataPreLoader;

import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Font;
import	java.awt.Graphics2D;
import	java.awt.RenderingHints;
import	java.awt.font.TextAttribute;
import	java.util.HashMap;
import	java.util.Map;


/**
* Displays an instructions screen with info about how to play the game.
*
* @author	Johan Liesén
* @author	Karl Olofsson
* @author	Jimmy Stridh
*/
public class InstructionScreen extends Screen
{
	/** Transparent box (container for textBox) */
	private MessageBox	messageBox;
	
	/** Box with text */
	private	TextBox		winningTextBox;
    private TextBox		controllingTextBox;
    private	TextBox		scoringTextBox;
	private	TextBox		goBackTextBox;
	
	
	
	/**
	*	Constructs an instructions screen with the specified dimensions
	*	@param	size	Dimensions of the screen
	*	@param	gc		The GameController
	*	@param	keyb	The keyboard controller
	*/
	public InstructionScreen(Dimension size, GameController gc, KeyboardController keyb)
	{
		super(size, gc, keyb);
		
		HashMap	attr;
		String	text;
		
		// MessageBox
		messageBox		= new MessageBox(0, 0, size.width, size.height,
										new Color(25, 25, 25), 0.50f, true);
		messageBox.setPadding(140, 60, 40, 40);
		
		// Go back!
		attr	= new HashMap();
		attr.put(TextAttribute.FONT, new Font("Arial", Font.ITALIC, 13));
		
		text	= "Press ESC to return to main menu";
		
		goBackTextBox	= new TextBox(size.width - 250, size.height - 30, 
									  size.width, size.height,
									  text, Color.WHITE, attr);
		
		// Format text
		attr	= new HashMap();
		attr.put(TextAttribute.FONT, new Font("Arial", Font.BOLD, 15));
							
		text = "A player will strive to makes as many transports as "+
			   "possible. The game ends when a player has made completed a " +
			   "certain amount of assignments or has made a certain amount " +
			   "of credits. The game also ends if a player travels out of " +
			   "bounds and does not have sufficient credits to re-enter the " +
			   "game.";


		// Create text box
		winningTextBox	= new TextBox(messageBox.getBounds(), text, 
									  Color.WHITE, attr);
		winningTextBox.setPadding(25, 10, 10, 10);
		
		
		text = "Player one uses the keyboard arrows.\n Player number two " +
			   "uses W (up), S (down), A (left) and D (right).";
				  
		controllingTextBox = new TextBox(messageBox.getBounds(), text, 
										 Color.WHITE, attr);
		controllingTextBox.setPadding(120, 10, 10, 10);
		
		text = "Rewards for completing an assignment (transporting a "+
			   "passenger from its start to it's destination) are given " +
			   "in form of simple points, one for each assignment, but " +
			   "also in form of space money. The credits are used by the " +
			   "player to buy energy for his/her ship. Energy is fuel and " +
			   "without fuel, the ship won't fly.";
				  
		scoringTextBox = new TextBox(messageBox.getBounds(), text, 
									 Color.WHITE, attr);
		scoringTextBox.setPadding(180, 10, 10, 10);
		
		setBackground(DataPreLoader.getInstance().getImage("menu_background"));
	}
	
	
	
	/**
	*	Render the Instructions screen on the specified graphics context.
	*	@param	g	The Graphics2D object the paint on.
	*/
	public void render(Graphics2D g)
	{
		// Paint background
		super.render(g);
		
		// Hints to look the text look really niez!
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
		winningTextBox.render(g);
		controllingTextBox.render(g);
		scoringTextBox.render(g);
	}
} //end CreditsScreen