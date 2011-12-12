package	game.graphics.screens;

import 	java.awt.Dimension;
import 	java.awt.Graphics2D;
import	java.io.File;

import 	game.GameController;
import	game.graphics.MenuButton;
import	game.graphics.screens.Screen;
import 	game.input.KeyboardController;
import 	utilities.DataPreLoader;


/**
*	Display a menu screen with four menu choices
*
*	@author	Johan Liesén
*	@author Jimmy Stridh
*	@author Karl Olofsson
*/
public class MenuScreen extends Screen
{
	private MenuButton[]	buttons;
	private int				selected;
	
	
	
	/**
	* Create MenuScreen.
	*
	*/
	public MenuScreen(Dimension size, GameController gc, KeyboardController keyb)
	{
		super(size, gc, keyb);
		
		// Get preloaded data
		DataPreLoader	data	= DataPreLoader.getInstance();

		setBackground(data.getImage("menu_background"));
	
		selected	= 0;
		buttons		= new MenuButton[] {
							new MenuButton(data.getImage("menu_play_on"),
										 data.getImage("menu_play_off"),
										 selected++),
							new MenuButton(data.getImage("menu_instructions_on"),
										 data.getImage("menu_instructions_off"),
										 selected++),
							new MenuButton(data.getImage("menu_credits_on"),
										 data.getImage("menu_credits_off"),
										 selected++),										 
							new MenuButton(data.getImage("menu_exit_on"),
										 data.getImage("menu_exit_off"),
										 selected)										 										 
					  };
		
		// Select the first button
		selectNext();
	}
	
	
	
	/**
	* Set the next button as <em>selected</em>.
	*
	*/
	public void selectNext()
	{
		buttons[selected].setSelected(false);
		step(1);
		buttons[selected].setSelected(true);
	}
	
	
	/**
	* Set the previous button as <em>selected</em>.
	*
	*/
	public void selectPrevious()
	{
		buttons[selected].setSelected(false);
		step(-1);
		buttons[selected].setSelected(true);
	}
	
	
	/**
	* Alter where the selection pointer is.
	*
	* @param	steps		number of steps to jump (negative when jumping 
	*						backwards)
	*/
	private void step(int steps)
	{
		selected	= (selected + steps + buttons.length) % buttons.length;
	}
	
	
	/**
	* Get the select <code>MenuButton</code>.
	*
	* @return				currently selected button
	*/
	public MenuButton getSelectedButton()
	{
		return buttons[selected];
	}
	
	
	/**
	* Get ID of the button currently selected. Same as 
	* <code>getSelectedButtonID().getID()</code>.
	*
	* @return				ID of selected button
	* @see					MenuScreen#getSelectedButton
	*/
	public int getSelectedButtonID()
	{
		return getSelectedButton().getID();
	}
	
	
	/**
	* Paint the menu screen.
	*
	*/
	public void render(Graphics2D g)
	{
		super.render(g);	// Paint background image
		
		int	x	= 77;
		int	y	= 264;
   	
        // Paint all the buttons
		for (int i = 0; i < buttons.length; y += buttons[i].getHeight(), i++)
		{
			// paints button in position: x is constant
			buttons[i].paint(g, x, y);
		}
	}
} //end MenuScreen