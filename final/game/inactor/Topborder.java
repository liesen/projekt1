package	game.inactor;

import	java.awt.Color;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;

import	game.inactor.Arrow;
import	game.inactor.Inactor;
import	game.actor.Passenger;
import	utilities.DataPreLoader;



/**
* Topborder is an inactor painted under the statusbar. 
*
* @author	Johan Liesén
* @author	Karl Olofsson
*/
public class Topborder extends Inactor
{
	
	/**
	* Create Topborder.
	*
	* @param	Point			topborder position
	*/
	public Topborder(Point p)
	{
                this(p, DataPreLoader.getInstance().getImage("topborder"));
        }
	
	/**
        *
        *	Creates a Topborder with a specified image
        *
        *	@param	Point		topborder position
        *	@param	Image		image of topborder
        */
        public Topborder(Point p, Image image)
        {
                super("default",
			  new Image[] { image }, 
			  0, 
			  p);
                setVisible(true);
        }
	/**
	* Paints the topborder. 
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		// Draw topborder
		super.paint(g);
	}
} //end Platform