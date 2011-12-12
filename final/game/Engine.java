package	game;

import 	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Graphics2D;
import	java.awt.Point;
import	java.awt.Rectangle;
import	java.util.ArrayList;
import	java.util.Iterator;
import 	java.util.List;

import	game.actor.Actor;
import	game.actor.Ship;
import	game.actor.Passenger;
import	game.audio.AudioPlayer;
import 	game.graphics.screens.PlayScreen;
import 	game.graphics.Sprite;
import 	game.graphics.NoSuchStateException;
import 	game.inactor.Arrow;
import 	game.inactor.Inactor;
import	game.inactor.Platform;
import	game.inactor.RefuelPlatform;
import 	utilities.Vector2D;


/**
* Engine handles all the game objects and respond to situations in the world 
* such as collisions, ship movement, painting, and so on...
* 
* @author		Karl Olofsson
* @author		Jimmy Stridh
* @author		Johan "Rocco" Liesén
* @version		2004-05-14 01:18
*/
public class Engine implements Runnable
{
	/** Array of players */
	private Player[] players;

	/** List of passengers */
	private List	passengers;

	/**	List of inactors */
	private List	inactors;

	/** Play screen */
	private PlayScreen		playScreen;

	private boolean[] movesLeft;

	private boolean[] movesRight;

	private boolean[] movesUp;

	private boolean[] movesDown;

	/** decides wheter the engine is running or not */
	private boolean isRunning;

	private long	engineStartTime;

	/** The time in millis since the engine was started */
	private long	dTime;

	private int		dSecsSincePassengerSpawn;

	private long 	lastPassengerSpawnTime;

	private Vector2D	gravity	= new Vector2D(0, 0.075);

	private volatile Thread gameLoop;
	
	/** Audio system */
	private AudioPlayer audio = AudioPlayer.getInstance();
	
	

	/**
	* Constructor for the engine.
	*
	* @param	gc			the game controller
	*/
	public	Engine(GameController gc)
	{

	}
	
	
	
	/**
	* Initializes data and all the game objects.
	*
	*/
	public void init()
	{
		players			= new Player[2];
		players[0]		= new Player("Player1", Color.BLUE);
		players[1]		= new Player("Player2", Color.RED);

		Ship[] ships	= new Ship[2];
		ships[0]		= new Ship(players[0], new Point(200, 100));
		ships[1]		= new Ship(players[1], new Point(100, 300));

		players[0].setShip(ships[0]);
		players[1].setShip(ships[1]);
		
		// Movement shit
		movesLeft 		= new boolean[2];
		movesRight		= new boolean[2];
		movesUp 		= new boolean[2];
		movesDown 		= new boolean[2];
                    	
		passengers		= new ArrayList();

		// Different platforms need different positions
		inactors		= new ArrayList();
		inactors.add(new Platform(new Point(100, 100)));
		inactors.add(new Platform(new Point(500, 400)));
		inactors.add(new Platform(new Point(300, 300)));
		inactors.add(new Platform(new Point(100, 450)));
		inactors.add(new Platform(new Point(50, 320)));
		inactors.add(new RefuelPlatform(new Point(200, 200)));
	}
	
	
	/**
	* Runs the game.
	*
	*/
	public void run()
	{
		long roundStartTime, roundTime;
		
		while (isRunning)
		{
			// Start timer
			roundStartTime = System.currentTimeMillis();
			
			// Update playscreen
			playScreen.repaint();

			// Update the game world
			update();

			// Give other threads some space (also slows the game down)
			Thread.currentThread().yield();
			
			// Uphold a good FPS value
			roundTime = System.currentTimeMillis() - roundStartTime;
			
			if(roundTime < 17)
			{
				try
				{
					Thread.sleep(17 - roundTime); //~60 FPS
				}
				catch(InterruptedException e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	
	/**
	* Starts the engine (game).
	*
	*/
	public void start()
	{
		engineStartTime	= System.currentTimeMillis();
		dTime			= System.currentTimeMillis() - engineStartTime;
		isRunning		= true;
		lastPassengerSpawnTime = dTime;

		// Initialize
		if(passengers == null)
		{
			init();
		}

		Thread gameLoop = new Thread(this);
		
		if (gameLoop != null)
		{
			gameLoop.start();
		}
	}
	
	
	/**
	* Stops the engine.
	*
	*/
	public void stop()
	{
		isRunning	= false;
		gameLoop 	= null;
	}
	

	/**
	* Render paints the objects.
	*
	* @param	g			graphics context
	*/
	public void render(Graphics2D g)
	{	
		// Paint all inactors		
		for(int i = 0; i < inactors.size(); i++)
		{
			((Inactor) inactors.get(i)).paint(g);
		}
		
		// Paint all passengers
		for (int i = 0; i < passengers.size(); i++)
		{
			((Passenger) passengers.get(i)).paint(g);
		}
		
		// Paint all ships
		for (int i = 0; i < players.length; i++)
		{
			players[i].getShip().paint(g);
		}
	}

	/**
	*	Updates
	*/
	private void update()
	{
		dTime = System.currentTimeMillis() - engineStartTime;
		dSecsSincePassengerSpawn = (int)Math.round(
									 (dTime - lastPassengerSpawnTime) / 1000.0);

		updateShipPositions();
		//collisionDetect();

		resolveCollisions();
		//newPassengerPlatformSelect();

		spawnPassenger();
		// kolla efter "osynliga" object och ta bort dem?
		
		removeDeadActors();
	}
	
	
	/**
	* Returns an array of players.
	*
	* @return				the players
	*/
	public Player[] getPlayers()
	{
		return players;
	}
	
	
	/**
	* Handles the removal of dead/inactive actors.
	*
	*/
	public void removeDeadActors()
	{
		for (int i = 0; i < players.length; i++)
		{
			Ship	ship	= players[i].getShip();
			
			if (ship.getEnergy() <= 0)
			{
				// ship.implode();
			}
		}
	}

	/**
	* Updates ship positions and motion vectors.
	*
	*/
	private void updateShipPositions()
	{
		for (int i = 0; i < players.length; i++)
		{
			Ship		ship 	= players[i].getShip();
			Vector2D 	motion	= ship.getMotionVector();
		
			// Affect ship movement vector
			if (movesLeft[i] && !ship.hasLanded())	motion	= motion.add(ShipMovement.LEFT);
			if (movesRight[i] && !ship.hasLanded())	motion	= motion.add(ShipMovement.RIGHT);
			if (movesDown[i] && !ship.hasLanded())	motion	= motion.add(ShipMovement.DOWN);
			
			// When the ship throttles then the image should be updated
			try
			{
				if (movesUp[i])
				{
					motion	= motion.add(ShipMovement.UP);
					
					if (ship.hasLanded())
						ship.liftOff();
					
					ship.setState("thrust");
				}
				else
				{
					ship.setState("default");
				}
			}
			catch (NoSuchStateException e)
			{
				System.out.println(e);
			}
			
			
			// Apply gravity
			motion	= motion.add(gravity);
			
			// Speed limit
			if (motion.getLength() > 10)
			{
				motion	= motion.toLength(10);
			}

			// Alter movement
			ship.setMotionVector(motion);
				
			// Move ship
			Point shipPosition	= ship.getPosition();

			shipPosition.translate((int) Math.round(motion.getX()),
								   (int) Math.round(motion.getY()));

			// Wrap around screen
			Dimension	screen	= playScreen.getSize();

			if(shipPosition.x > screen.width)	shipPosition.x = 0;
			if(shipPosition.x < 0)				shipPosition.x = screen.width;
			if(shipPosition.y > screen.height)	shipPosition.y = 0;
			if(shipPosition.y < 0)				shipPosition.y = screen.height;

			// Set new position
			ship.setPosition(shipPosition);
		}
	}
	
	
	/**
	* Spawns a passenger 
	*
	*/
	private void spawnPassenger()
	{
		if ((dSecsSincePassengerSpawn >= 5) && (passengers.size() < players.length))
		{			
			Platform	source		= getEmptyPlatform();
			Platform	destination	= null;
			
			// Make sure that the destination isn't the same as the source
			do
			{
				destination	= getEmptyPlatform();
			}
			while (source == destination);
			
			
			// Notify destination platform that it's a destination
			destination.setDestination(true);
        	
			// Create passenger					
			Passenger	passenger	= new Passenger(source, destination);
        	
			//Tell the source platform which it's passenger.
			source.setPassenger(passenger);
			
			// Add passenger to list of passengers
			passengers.add(passenger);
			
			// Show passenger
			passenger.setVisible(true);
			
			lastPassengerSpawnTime = dTime;
			
			audio.playEffect("Taxi" + ((int) (Math.random() * 3) + 1), false);
		}
	}
	

	/**
	* Returns a platform which is not inhabited, a destination and is not of
	* type <code>{@link RefuelPlatform}</code>.
	*
	* @return				empty platform
	*/
	private Platform getEmptyPlatform()
	{
		Platform	pf	= null;
		int			pfs	= inactors.size();	// Number of platforms
		
		do
		{
			pf	= (Platform) inactors.get((int) (Math.random() * pfs));
		}
		while (pf.isInhabited() || (pf instanceof RefuelPlatform) || pf.isDestination());
		
		return pf;
	}


	/**
	* Sets a reference to which screen to update.
	*
	* @param	playScreen	the play screen
	*/
	public void setPlayScreen(PlayScreen playScreen)
	{
		this.playScreen = playScreen;
	}


	/**
	* Lands a ship on a specific platform.
	*
	* @param	s			ship to land
	* @param	pf			the platform
	*/
	public void land(Ship s, Platform pf)
	{
		s.land();
		Player player = s.getOwner();
		
		if (pf instanceof RefuelPlatform)
		{
			// Fill the player with the special sauce
			((RefuelPlatform) pf).refuel(s);
		}
		else 
		{
			if (pf.isInhabited() && !s.hasPassenger())
			{
				Passenger	p	= pf.getPassenger();
				
				// Add passenger to ship
				s.pickUpPassenger(p);
				//pf.setInhabited(false);
				pf.setPassenger(null);
				p.setVisible(false);
				
				// Show arrow @ the passenger's destination platform
				p.getDestination().showArrow(player.getArrow());
			}
			
			// Check for a passenger dropoff
			if (s.hasPassenger() && s.getPassenger().getDestination() == pf)
			{
				pf.setDestination(false);
				Passenger	p	= s.getPassenger();

				// Remove it from the list of passengers
				passengers.remove(s.getPassenger());
				
				// Let the passenger out of the ship
				s.dropOffPassenger();
				
				lastPassengerSpawnTime = dTime;				
				
				pf.setDestination(false);
				
				// Hide arrow above platform
				pf.hideArrow();
				
				// Reward the player
				player.addScore(1);
				player.addCredits(100);
			}
		}
	}


	public void p1Left(boolean activation)
	{
		movesLeft[0] = activation;
	}

	public void p1Right(boolean activation)
	{
		movesRight[0] = activation;
	}

	public void p1Up(boolean activation)
	{
		movesUp[0] = activation;
	}

	public void p1Down(boolean activation)
	{
		movesDown[0] = activation;
	}

	public void p2Left(boolean activation)
	{
		movesLeft[1] = activation;
	}

	public void p2Right(boolean activation)
	{
		movesRight[1] = activation;
	}

	public void p2Up(boolean activation)
	{
		movesUp[1] = activation;
	}

	public void p2Down(boolean activation)
	{
		movesDown[1] = activation;
	}

	
	/**
	* Detects and resolves collisions in the world.
	*
	*/
	
	// http://www.gamasutra.com/features/20020118/vandenhuevel_01.htm
	// http://www.gamedev.net/reference/articles/article1026.asp
	
	private void resolveCollisions()
	{
		// Go through each player
		for (int i = 0; i < players.length; i++)
		{
			Ship	ship	= players[i].getShip();
			
			Point	center	= ship.getCenterPoint();
			
			// Check collision between current and other ships
			for (int j = i + 1; j < players.length; j++)
			{
				Ship		ship2		= players[j].getShip();
				
				// Skip collisions when one of the ship has landed
				if (ship.hasLanded() || ship2.hasLanded())	continue;
				
				double		distance	= center.distance(ship2.getCenterPoint());
				
				if (distance <= (ship.getRadius() + ship2.getRadius()))
				{					
					// Collision between two ships
					
					/*	Lagen om rörelsemängdens bevarande:
						
						m och M är massa
						u, u', v och v' är rörelse(vektorer)
						
							mv + Mu = mv' + Mu'
							
						Massorna, m och M, är samma för båda skeppen, dvs
						
							v + u = v' + u'									  */
					// Get motion vectors for both ships
					Vector2D	v1		= ship.getMotionVector();
					Vector2D	v2		= ship2.getMotionVector();
					
					// The line of contact runs from one centerpoint to the 
					// other
					Vector2D	n		= new Vector2D(center,
											   		   ship2.getCenterPoint());
										
					// Normalize vector
					n	= n.toLength(1);
					
					// Get length of the ship's motion vectors projected on the
					// line of contact
					double		a1		= v1.dot(n);
					double		a2		= v2.dot(n);
						
					/*	P är "längden" på kraften längs kontaktlinjen,					
							P		= 2 * m * M * (a1 - a2) / (m + M)
									= 2 * m^2 * (a1 - a2) / 2m
									= m * (a1 - a2),
						och
							v'		= v ± (P / m) * n,						
						dvs						
							v'		= v ± (a1 - a2) * n.
													
						force	= (a1 - a2) * n.							  */					
					Vector2D	force	= n.multiply(a1 - a2);
					
					// Set a minimum value to the force vector
					if (force.getLength() < 1)
					{
						force	= force.toLength(1);
					}
					
					// Add or remove the force at contact
					ship.setMotionVector(v1.subtract(force));
					ship2.setMotionVector(v2.add(force));
					
					// Inflict damage
					ship.addEnergy((int) -force.getLength());
					ship2.addEnergy((int) -force.getLength());
					
					// Activate both shields
					ship.setShielded(true);
					ship2.setShielded(true);
					
					audio.playEffect("shieldBump", false);					
				}
			} // !for each (other) ship
			
			
			// Ship - Inactor collision
			Iterator	it	= inactors.iterator();
			
			while (it.hasNext())
			{
				Inactor		inactor	= (Inactor) it.next();
				Vector2D	normal	= null;
				
				if (ship.getBounds().intersects(inactor.getBounds()))
				{
					// We probably have an intersection
					
					// Get the edges of the inactor
					Point[]	vertices	= inactor.getVertices();
					
					// Initialize points
					Point	closest		= vertices[0],
							test		= null;
				
					// Loop through all the vertices
					for (int k = 0; k < vertices.length; k++)
					{
						// q is the next vertex
						Point		p				= vertices[k],
									q				= vertices[(k + 1) % vertices.length];
						
						// Vector from the first vertex to the next
						Vector2D	pq				= new Vector2D(q, p);
						
						double		pqLength		= pq.getLength();
						
						// Normalized vector along the line from p to q
						Vector2D	n				= pq.toLength(1);
									
						// Vector from p to the center of the ship
						Vector2D	c				= new Vector2D(center, p);
						
						// The ...
						double		factor			= n.dot(c);
						
						// Get the closest point
						if (factor < 0)
						{
							// Closest point lies outside the start of the line
							test	= p;
						}
						else if (factor > pqLength)
						{
							// Closest point lies outside the end of the line
							test	= q;
						}
						else // Closest point lies on the line between p and q
						{
							// px travels from p to the intersection point, x
							Vector2D	px	= n.multiply(factor);
							
							// Translate point so that it lies on the line, pq
							test			= new Point(p.x + (int) px.getX(),
														p.y + (int) px.getY());
						}
						
						// Test if the closest point on the line is the closest 
						// of each line
						if (test.distance(center) <= closest.distance(center))
						{
							closest	= test;
							normal	= (new Vector2D(n.getY(), n.getX())).negate();
							
							// Test special case: if ship collides with a vertex
							if ((factor < 0) || (factor > pqLength))
							{
								normal	= new Vector2D(center, closest);
								normal	= normal.toLength(1);
							}
						}
					} // !for each vertex
				
					// Collision between circle and plane
					if (closest.distance(center) <= ship.getRadius())
					{
						Vector2D	motion		= ship.getMotionVector();
						Point		inactorP	= inactor.getPosition();
						Rectangle	inactorB	= inactor.getBounds();
						
						// Land the ship if the ship's velocity is lower than 2
						// _and_ the ship is above the platform
						if ((motion.getLength() < 2) && 
							(center.y < closest.y) && 
							(center.x > inactorP.x) && 
							(center.x < inactorB.width + inactorP.x))
						{
							land(ship, (Platform) inactor);
						}
						
						// Bounce if the ship hasn't landed
						if (!ship.hasLanded())
						{
							audio.playEffect("shieldBump", false);						
						
							// Inflict damage upon ship
							ship.addEnergy((int) -motion.getLength());
							
							// Change the motion vector
							motion	= motion.mirror(normal);					
							ship.setMotionVector(motion);
							ship.setShielded(true);
						}
					}
				} // !while each inactor
			} // !if bounds intersect
		} // !for each player
	}
}//end Engine