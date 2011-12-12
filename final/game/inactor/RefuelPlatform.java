package	game.inactor;

import	java.awt.Color;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Point;

import	game.Player;
import	game.actor.Ship;
import	game.inactor.Platform;
import	game.inactor.Arrow;
import	utilities.DataPreLoader;


/**
* Platform where the players can refuel their ship.
*
* @author	Johan Liesén
*/
public class RefuelPlatform extends Platform
{
	/** The amount of energy which the ship that refuels get at each update */
	private final	int	FUEL_INCREMENT	= 1;
	
	/** Cost of one fuel cell */
	private final	int	FUEL_COST		= 1;
	
	
	
	/**
	* Create refuel platform.
	*
	* @param	x			x position
	* @param	y			y position
	*/
	public RefuelPlatform(int x, int y)
	{
		this(new Point(x, y));
	}
	
	
	/**
	* Create refuel platform.
	*
	* @param	p			platform position
	*/
	public RefuelPlatform(Point p)
	{
		super(p, DataPreLoader.getInstance().getImage("refuel_platform"));
		setBaseImage(DataPreLoader.getInstance().getImage("refuel_base"));
	}
	
	
	
	/**
	* Refuels the ship an charges its player.
	*
	* @param	ship		ship to refuel
	* @return				wether the refuel process was successful or not
	*/
	public boolean refuel(Ship ship)
	{
		if (ship.hasLanded())
		{
			Player	player	= ship.getOwner();
			
			if (player.getCredits() > FUEL_COST)
			{
				if (ship.getEnergy() < (Ship.MAX_ENERGY - FUEL_INCREMENT))
				{
					// Charge
					player.addCredits(-FUEL_COST * FUEL_INCREMENT);
					
					// Energize
					ship.addEnergy(FUEL_INCREMENT);
				}
				else
				{
					// Charge
					player.addCredits(-FUEL_COST * (Ship.MAX_ENERGY - ship.getEnergy()));
					
					// Set ship energy to max
					ship.setEnergy(Ship.MAX_ENERGY);
				}
				
				return ship.getEnergy() != Ship.MAX_ENERGY;
			}
				
			// The player has no credits. Call Baltic Inkasso at once!
			return false;
		}
		
		return false;
	}
	
	
	
	/**
	* Overriden from <code>Platform</code>. Method is empty.
	*
	*/
	public void showArrow(Arrow a, boolean show)
	{
		
	}
	
	
	/**
	* Overriden from <code>Platform</code>. Method is empty.
	*
	*/
	public void showArrow(Arrow a)
	{
		
	}
	
	
	/**
	* Overriden from <code>Platform</code>. Method is empty.
	*
	*/
	public void hideArrow()
	{
		
	}
	
	
	/**
	* Paints the refuel platform.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		super.paint(g);
	}
} //end RefuelPlatform