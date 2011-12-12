package	game.inactor;

import	java.awt.Color;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;
import	java.awt.image.BufferedImage;

import	game.inactor.Arrow;
import	game.inactor.Inactor;
import	game.actor.Passenger;
import	utilities.DataPreLoader;



/**
* Platform.
*
* @author	Johan Liesén
*/
public class Platform extends Inactor
{
	private Arrow		arrow;		// The arrow
	private boolean		showArrow;
	private	boolean		isInhabited;
	private Passenger	passenger;
	private boolean		isDestination;
	private Image 		baseImage;
	

	/**
	* Create platform.
	*
	* @param	x			x position
	* @param	y			y position
	*/
	public Platform(int x, int y)
	{
		this(new Point(x, y));
	}
	
	
	/**
	* Create platform.
	*
	* @param	p			platform position
	*/
	public Platform(Point p)
	{
		this(p, DataPreLoader.getInstance().getImage("platform"));
	}
	
	
	/**
	* Create platform with a special image.
	*
	* @param	p			platform position
	* @param	image		image
	*/
	public Platform(Point p, Image image)
	{
		super("default",
			  new Image[] { image }, 
			  0, 
			  p);
		
		// Just in case
		arrow		= null;
		showArrow	= false;
		
		setBaseImage(DataPreLoader.getInstance().getImage("platform_base"));
		setVisible(true);	// DEBUG
	}
	
	
	public void setBaseImage(Image img)
	{
		baseImage = img;
	}

	
	/**
	* Show arrow.
	*
	* @param	a			the arrow
	* @param	show		<code>true</code> to show, <code>false</code> to 
	*						hide
	*/
	public void showArrow(Arrow a, boolean show)
	{
		arrow		= a;
		showArrow	= show;
		
		Point	newArrowPosition	= getPosition();
				newArrowPosition.translate(getBounds().width / 2, -5);
		
		arrow.setPosition(newArrowPosition.x, newArrowPosition.y);
	}
	
	
	/**
	* Show arrow.
	*
	* @param	a			the arrow
	*/
	public void showArrow(Arrow a)
	{
		showArrow(a, true);
	}
	
	
	/**
	* Hide arrow.
	*
	*/
	public void hideArrow()
	{
		arrow		= null;
		showArrow	= false;
	}
	
	
	/**
	* Set to indicate wether or not the platform is a destination for a 
	* passenger.
	*
	* @param	dest		<code>true</code> if the platform is a destination,
	*						<code>false</code> otherwise
	*/
	public void setDestination(boolean dest)
	{
		isDestination = dest;
	}
	
	
	/**
	* Returns wether the platform is a destination.
	*
	* @return				<code>true</code> if the platform is a destination,
	*						<code>false</code> otherwise
	*/
	public boolean isDestination()
	{
		return isDestination;
	}
	
	
	/**
	* Returns wether the platform is inhabited (by a passenger) or not.
	*
	* @return				<code>true</code> if the platform is inhabited,
	*						<code>false</code> otherwise
	*/
	public boolean isInhabited()
	{
		return (passenger != null);
	}
	
	
	public void setInhabited(boolean b)
	{
		isInhabited 	= b;
	}		


	/**
	* Sets the passenger which has the platform as its home.
	*
	* @param	pass		a passenger
	*/
	public void setPassenger(Passenger pass)
	{
		passenger 		= pass;
	}
	
	
	/**
	* Returns the passenger located on the platform.
	*
	* @return				the passenger which live on the platform
	*/
	public Passenger getPassenger()
	{
		return passenger;
	}
	
	
	/**
	* Paint arrow (if one is set) and the platform itself.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		// Draw platform
		super.paint(g);
	
		//Draw the base on the platform
		int baseX = getBounds().x + getBounds().width/2 - baseImage.getWidth(null)/2;
		int baseY = getBounds().y - baseImage.getHeight(null);
		g.drawImage(baseImage, baseX, baseY, null);
													
		
		// Draw arrow
		if (showArrow && (arrow != null))
		{
			arrow.paint(g);
		}
		
	}
} //end Platform