package	game.actor;

import	java.awt.Point;
import	java.awt.Graphics2D;
import 	java.awt.Image;
import 	java.awt.Color;

import	game.inactor.Arrow;
import 	game.GameObject;
import 	game.Player;
import 	utilities.DataPreLoader;
import	utilities.Vector2D;


/**
* Ship is the <code>{@link Player}</code>'s tool in the game which he controls.
* 
* @author	Karl Olofsson
* @author	Jimmy Stridh
* @author	Johan Liesén
* @version	5/11/2004 7:21PM
*/
public class Ship extends Actor
{	
	/** The maximum value of the ship's energy level */
	public static final int	MAX_ENERGY	= 100;
	
	/** Energy level */
	private int				energy;
	
	/** Credits */
	private	int				credits;
	
	/** Indicator of the ship's shield */
	private boolean			shielded;
	
	/** Radius of the shield in pixels */
	private int				shieldRadius;
	
	// Ah...
	private int				shieldDropoffCounter;
	
	/** Passenger in the ship */
	private Passenger		passenger;
	
	/** Set if the ship has landed*/
	private boolean			landed;	
	
	/** Ship owner */
	private Player			owner;
	
	
	
	/**
	* Create ship at (<em>0</em>, <em>0</em>).
	*
	* @param	player		ship owner
	*/
	public Ship(Player player)
	{
		this(player, new Point(0, 0));
	}
	
	
	/**
	* Create ship at a given position.
	*
	* @param	player		ship owner
	* @param	position	starting position
	*/
	public Ship(Player player, Point position)
	{
		super("default",
			  new Image[] { DataPreLoader.getInstance().getImage("ship"+player.getName()) },
			  1,
			  position);
		
		// Add thrust state
		addState("thrust", 
				new Image[] { DataPreLoader.getInstance().getImage("ship_thrust"+player.getName()) });		
		
		// Initialize variables
		energy			= Ship.MAX_ENERGY;
		credits			= 0;
		landed			= false;
		owner			= player;
		
		shieldDropoffCounter	= 0;
		
		// Shield radius is set to the longer of the image axes / 2
		shieldRadius 	= Math.max(frames[0].getWidth(), frames[0].getHeight()) / 2;
				
		setVisible(true);  // DEBUG
	}
	
	
	
	/**
	* Makes the ship implode. Used when the ship runs out of energy.
	* 
	*/
	public void implode()
	{
		
	}
	
	
	/**
	* Set a new energy level for the ship.
	*
	* @param				new energy level
	*/
	public void setEnergy(int energy)
	{
		this.energy	= energy;
	}
	
	
	/**
	* Add energy to the ship. The added number can be negative to reduce the
	* ship's energy.
	*
	* @param				energy to add
	*/
	public void addEnergy(int energy)
	{
		this.energy	+= energy;
	}
	
	
	/**
	* Returns the ship's energy level.
	*
	* @param				ship's energy
	*/
	public int getEnergy()
	{
		return energy;
	}
	
	
	/**
	* Activates or deactivates the ship's shield.
	*
	* @param	shielded	activate shield?
	*/
	public void setShielded(boolean shielded)
	{
		this.shielded			= shielded;
		shieldDropoffCounter	= 50;
	}
	
	
	/**
	* Returns the ship's shield status.
	*
	* @return				<code>true</code> if the ship's shield are up,
	*						<code>false</code> otherwise
	*/
	public boolean isShielded()
	{
		return shielded;
	}
	
	
	/**
	* Lands the ship.
	*
	*/
	public void land()
	{
		setMotionVector(new Vector2D(0, 0));
		landed = true;
	}
	
	
	/**
	* Helps the ship at lift off.
	*
	*/
	public void liftOff()
	{
		// Lift off help
		setMotionVector(new Vector2D(0, -2));
		
		// Move ship a bit
		Point	position = getPosition();
		position.translate(0, -3);
		setPosition(position);
		
		// We're off
		landed = false;
	}
	
	
	/**
	* Returns whether the eagle has landed or not.
	*
	* @return				<code>true</code> if the ship has landed, 
	*						<code>false</code> otherwise
	*/
	public boolean hasLanded()
	{
		return landed;
	}
	
	
	/**
	*
	*
	*/
	public void pickUpPassenger(Passenger p)
	{
		passenger	= p;
	}
	
	
	/**
	* Leaves a passenger.
	*
	*/
	public void dropOffPassenger()
	{
		passenger	= null;
	}
	
	
	/**
	* Returns wether the ship has a passenger or not.
	*
	* @return				<code>true</code> if the ship has a passenger, 
	*						<code>false</code> otherwise
	*/
	public boolean hasPassenger()
	{
		return (passenger != null);
	}
	
	
	/**
	* Returns the passenger.
	*
	* @return				the ship's passenger, or <em>null</em> if the ship
	*						doesn't carry one
	*/
	public Passenger getPassenger()
	{
		return passenger;
	}
	
	
	/**
	* Returns a reference to the player responsible for whatever this ship has
	* done.
	*
	* @return				the player
	*/
	public Player getOwner()
	{
		return owner;
	}
	
	
	/**
	* Returns ship's shield radius.
	*
	* @return				shield radius
	*/
	public int getRadius()
	{
		return shieldRadius;
	}
	
	
	/**
	* Paints the ship and its shield.
	*
	* @param	g			graphics context
	*/
	public void paint(Graphics2D g)
	{
		Vector2D shipV = getMotionVector();
		rotateTo(shipV.getX() * 8);
		
		super.paint(g);
		
		// Paint shield?
		if (shielded)
		{
			if (shieldDropoffCounter > 0)
			{
				// Paint shield
				Point	center	= getCenterPoint();
				center.translate(-shieldRadius, -shieldRadius);
				
				g.setColor(owner.getColor());
				g.drawOval(center.x - 2,
						   center.y - 2,
						   shieldRadius * 2 + 4,
						   shieldRadius * 2 + 4);
				
				shieldDropoffCounter--;
			}
			else
			{
				setShielded(false);
			}
		}
	}
} //end Ship