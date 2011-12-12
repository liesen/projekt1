package	game.graphics;

import	java.awt.Image;
import	java.awt.Graphics2D;

import	game.graphics.Paintable;


//
//  MenuButton.java
//  Button in menu system
//
//  Created by Karl on Sun May 02 2004.
//  Copyright (c) 2004 Group 17. All rights reserved.
//

/**
* Button in menu system.
*
* @author	Karl Olofsson
*/
public class MenuButton implements Paintable
{
	// Says wether the button is selected or not
	private boolean		selected;
	
	// Position variables
	private int			x,
						y;
	
	// Button ID (to make it unique)
	private int			id;
	
	// Images
	private Image		selectedImage, 
						unselectedImage;
						
	
	
	/**
	* Creates a menu button. 
	*
	* @param	selectedImage
	*						image displayed when the button is selected
	* @param	unselectedImage
	*						image displayed when the button is not selected
	* @param	id			button ID
	*/
	public MenuButton(Image selectedImage, Image unselectedImage, int id)
	{
	    this(selectedImage, unselectedImage, id, 0, 0);
	}
	
	
	/**
	* Creates a menu button at a given position.
	*
	* @param	selectedImage
	*						image displayed when the button is selected
	* @param	unselectedImage
	*						image displayed when the button is not selected
	* @param	id			button ID
	* @param	x			x coordinate
	* @param	y			y coordinate
	*/
	public MenuButton(Image selectedImage, Image unselectedImage, int id, int x, int y)
	{
	    this.id 				= id;
	    this.unselectedImage	= unselectedImage;
	    this.selectedImage		= selectedImage;
	    
	    setPosition(x, y);
	}
	
	
	
	/**
	* Paints the button.
	*	
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		if (selected)
	    {
	        g.drawImage(selectedImage, x, y, null);
	    }
	    else
	    {
		    g.drawImage(unselectedImage, x, y, null);
	    }
	}
	
	
	/**
	* Positions the button at (<em>x</em>, <em>y</em>) and paints it.
	*
	* @param	g			graphics context
	* @param	x			x position
	* @param	y			y position
	* @see		#setPosition(int , int )
	* @see		#paint(Graphics2D )
	*/
	public void paint(Graphics2D g, int x, int y)
	{
		setPosition(x, y);
		paint(g);
	}
	
	
	/**
	* Set position for the button
	*
	* @param	x			x coordinate
	* @param	y			y coordinate
	*/
	public void setPosition(int x, int y)
	{
		this.x	= x;
		this.y	= y;
	}
	
	
	/**
	* Set button state as selected or unselected.
	*
	* @param	selected	is the button selected?
	*/
	public void setSelected(boolean selected)
	{
	    this.selected = selected;
	}
	
	
	/**
	* Checks if the button is selected or not.
	*
	* @return				<code>true</code> if button is selected, 
	*						<code>false</code> otherwise
	*/
	public boolean isSelected()
	{
	    return selected;
	}
	
	
	/**
	* Returns height of current image.
	*
	* @return				height of current image
	*/
	public int getHeight()
	{
	    if (selected)
	    {
	        return selectedImage.getHeight(null);
	    }
	    else
	    {
	        return unselectedImage.getHeight(null);
	    }
	}
	
	/**
	* Returns width of current image.
	*
	* @return				width of current image
	*/
	public int getWidth()
	{
	    if (selected)
	    {
	        return selectedImage.getWidth(null);
	    }
	    else
	    {
	        return unselectedImage.getWidth(null);
	    }
	    
	}
	
	
	/**
	* Get button ID.
	*
	* @return				ID of button
	*/
	public int getID()
	{
		return id;
	}
	
	
	/**
	* Indicates whether two MenuButton's ID's are equal.
	*
	* @param	o			object to compare
	* @return				<code>true</code> if the object's ID's are the same,
	*						<code> false</code> otherwise
	*/
	public boolean equals(Object o)
	{
		if (o instanceof MenuButton)
		{
			MenuButton	mb	= (MenuButton) o;
			
			return id == mb.getID();
		}
		
		return false;
	}
} //end MenuButton
