package tRex;

import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import util.Constants;

public class Map extends GCompound implements Runnable
{
	/**Scroll speed of map*/
	private double scrollSpeed;
	/**Indicates whether the map should stop moving*/
	private boolean stop;
	/**Distance traveled after putting the last collidable object.*/
	private int collidablesDistance;
	/**Distance traveled after putting the last cloud.*/
	private int cloudDistance;
	/**An instance of TRex that will be passed to this class by Game class*/
	private Trex rex;
	/**An ArrayList containing ground images*/
	private ArrayList<GImage> groundImages = new ArrayList<GImage>(2);
	/**An ArrayList containing clouds*/
	private ArrayList<GImage> clouds = new ArrayList<GImage>();
	/**An ArrayList containing celestials (moon and stars)*/
	private ArrayList<GImage> celestials = new ArrayList<GImage>();// Will contain moon and stars
	/**An ArrayList containing Collidable objects*/
	private ArrayList<GObject> collidableObjs = new ArrayList<GObject>();
	/**A Plane that will fly when certain amount of points is reached*/
	private Plane plane;
	/**A Moon that will appear in the dark mode*/
	private Moon moon;

	/**
	 * Initializes Map data.
	 * 
	 * @param rex
	 *            - an instance of TRex
	 */
	public Map(Trex rex)
	{
		this.rex = rex;

		addGround();

		plane = new Plane();
		add(plane, Game.APPLICATION_WIDTH, -Game.APPLICATION_HEIGHT * Constants.PLANE_Y_FACTOR);
	}

	@Override
	public void run()
	{
		resetScrollSpeed();

		while (true)
		{
			if (!stop)// Map should continue
			{
				scroll();

				createGround();

				pause(10);
			}
			else// Map should stopped
			{
				pause(Constants.PAUSE_TIME);
			}
		}
	}

	/**
	 * Adds 2 ground images to the screen and to the groundImages ArrayList.
	 */
	private void addGround()
	{
		groundImages.add(new Ground());
		groundImages.add(new Ground());

		groundImages.get(1).setLocation(groundImages.get(0).getWidth(), 0);

		add(groundImages.get(0));
		add(groundImages.get(1));
	}

	/**
	 * Removes all Collidable objects (cactuses and birds) from the map and the
	 * collidableObjs ArrayList.
	 */
	public void removeCollidables()
	{
		for (int i = 0; i < collidableObjs.size(); i++)
		{
			remove(collidableObjs.get(i));
		}

		collidableObjs.clear();
	}

	/**
	 * Adds airplane to the map.
	 */
	public void addPlane()
	{
		if (Game.getScore() % (300) == 0 && plane.getX() < -plane.getWidth())
		{
			remove(plane);
			plane = new Plane();
			add(plane);
			plane.setLocation(Game.APPLICATION_WIDTH, -Game.APPLICATION_HEIGHT * 0.65);
		}
	}

	/**
	 * Adds airplane to the given coordinate in map.
	 * 
	 * @param x
	 *            - coordinate
	 * @param y
	 *            - coordinate
	 */
	public void addPlane(double x, double y)
	{
		plane.setLocation(x, y);
	}

	/**
	 * Adds a cloud to the map and to the clouds ArrayList.
	 */
	public void addCloud()
	{
		if (RandomGenerator.getInstance().nextDouble() > 0.8
				&& cloudDistance > Constants.CLOUD_DISTANCE)// Creates clouds
		{
			Cloud cloud = new Cloud();

			add(cloud, Game.APPLICATION_WIDTH, -Game.APPLICATION_HEIGHT * 0.5
					- RandomGenerator.getInstance().nextDouble() * Game.APPLICATION_HEIGHT * 0.3);
			cloud.sendToBack();
			if (moon != null) moon.sendToBack();
			clouds.add(cloud);
			cloudDistance = 0;
		}
		else
		{
			cloudDistance++;
		}
	}

	/**
	 * Adds a cloud to the given location in map and to the clouds ArrayList.
	 * 
	 * @param x
	 *            - coordinate
	 * @param y
	 *            - coordinate
	 */
	public void addCloud(double x, double y)
	{
		Cloud cloud = new Cloud();

		add(cloud, x, y);
		cloud.sendToBack();
		if (moon != null) moon.sendToBack();
		clouds.add(cloud);
	}

	/**
	 * Adds a moon and three stars with random locations to the map and to the
	 * celestials ArrayList.
	 */
	public void addCelestials()
	{
		Star star;
		moon = new Moon();
		add(moon,
				Game.APPLICATION_WIDTH * 0.3
						+ Game.APPLICATION_WIDTH * 0.7 * RandomGenerator.getInstance().nextDouble(),
				-Game.APPLICATION_HEIGHT * 0.5 - RandomGenerator.getInstance().nextDouble()
						* Game.APPLICATION_HEIGHT * 0.3);

		for (int i = 1; i < 4; i++)// Stars
		{
			star = new Star(i);
			add(star,
					Game.APPLICATION_WIDTH * 0.2 + Game.APPLICATION_WIDTH * 0.8
							* RandomGenerator.getInstance().nextDouble(),
					-Game.APPLICATION_HEIGHT * 0.5 - RandomGenerator.getInstance().nextDouble()
							* Game.APPLICATION_HEIGHT * 0.3);
			star.sendToBack();
			celestials.add(star);
		}
		moon.sendToBack();
		celestials.add(moon);
	}

	/**
	 * Removes all celestials from map and the celestials ArrayList.
	 */
	public void removeCelestials()
	{
		for (GImage celestial : celestials)
		{
			remove(celestial);
		}
		celestials.clear();
	}

	/**
	 * Adds a random amount of Collidables to the map and to the ArrayList.
	 */
	public void addCollidables()
	{
		if (RandomGenerator.getInstance().nextDouble() > 0.8
				&& collidablesDistance > Constants.COLLIDABLES_DISTANCE)
		{
			if (RandomGenerator.getInstance().nextBoolean() || Game.getScore() < 500)// Cactus
			{
				if (RandomGenerator.getInstance().nextBoolean())// Small cactus
				{
					for (int i = 0; i < RandomGenerator.getInstance().nextInt(1, 3); i++)
					{
						addCactus(false, i);
					}
				}
				else// Big cactus
				{
					for (int i = 0; i < RandomGenerator.getInstance().nextInt(1, 2); i++)
					{
						addCactus(true, i);
					}
				}
			}
			else// Bird
			{
				addBird();
			}

			rex.sendToFront();
			collidablesDistance = 0;
		}
		else
		{
			collidablesDistance++;
		}
	}

	/**
	 * Creates a single cactus with given info and adds it to the map and
	 * collidableObjs ArrayList.
	 * 
	 * @param isBig
	 *            - boolean that determines whether the cactus should be big
	 * @param i
	 *            - a number that will be used for positioning the new cactus
	 */
	private void addCactus(boolean isBig, int i)
	{
		Cactus cactus = null;
		if (isBig)
		{
			cactus = new Cactus(true);

			add(cactus, Game.APPLICATION_WIDTH + i * (cactus.getWidth()),
					getLocalPoint(0, rex.getInitY()).getY()
							+ (Constants.CACTUS_Y - cactus.getHeight()));
		}
		else
		{
			cactus = new Cactus(false);

			add(cactus, Game.APPLICATION_WIDTH + i * (cactus.getWidth()),
					getLocalPoint(0, rex.getInitY()).getY()
							+ (Constants.CACTUS_Y - cactus.getHeight()));
		}
		collidableObjs.add(cactus);
	}

	/**
	 * An overloaded version of addCactus method that adds a cactus to the given
	 * location and to the collidableObjs ArrayList.
	 * 
	 * @param isBig
	 *            - boolean that determines whether the cactus should be big
	 * @param x
	 *            - coordinate
	 * @param y
	 *            - coordinate
	 */
	public void addCactus(boolean isBig, double x, double y)
	{
		Cactus cactus = null;
		if (isBig)
		{
			cactus = new Cactus(true);

			add(cactus, x, y);
		}
		else
		{
			cactus = new Cactus(false);

			add(cactus, x, y);
		}
		collidableObjs.add(cactus);
	}

	/**
	 * Switches the existing cactus and cloud objects' images with their
	 * darker/brighter versions.
	 */
	public void switchImages()
	{
		if (Game.isDarkMode())// Switching from bright to dark mode
		{
			for (int i = 0; i < collidableObjs.size(); i++)// Switches collidables
			{
				if (collidableObjs.get(i) instanceof Cactus)
				{
					if (((Cactus) collidableObjs.get(i)).isBig())
					{
						((Cactus) collidableObjs.get(i)).setImage("/collidables/darkBCactus"
								+ ((Cactus) collidableObjs.get(i)).getOrder() + ".png");
					}
					else
					{
						((Cactus) collidableObjs.get(i)).setImage("/collidables/darkCactus"
								+ ((Cactus) collidableObjs.get(i)).getOrder() + ".png");
					}
				}
			}

			for (int i = 0; i < clouds.size(); i++)// Switches clouds
			{
				clouds.get(i).setImage("/background/darkCloud.png");
			}

			plane.setBody("/background/darkPLANE.png");// Switches airplane
		}
		else// Switching from bright to dark mode
		{
			for (int i = 0; i < collidableObjs.size(); i++)
			{
				if (collidableObjs.get(i) instanceof Cactus)
				{
					if (((Cactus) collidableObjs.get(i)).isBig())
					{
						((Cactus) collidableObjs.get(i)).setImage("/collidables/bCactus"
								+ ((Cactus) collidableObjs.get(i)).getOrder() + ".png");
					}
					else
					{
						((Cactus) collidableObjs.get(i)).setImage("/collidables/cactus"
								+ ((Cactus) collidableObjs.get(i)).getOrder() + ".png");
					}
				}
			}
			for (int i = 0; i < clouds.size(); i++)// Switches clouds
			{
				clouds.get(i).setImage("/background/cloud.png");
			}

			plane.setBody("/background/PLANE.png");// Switches airplane
		}
	}

	/**
	 * Adds a bird to the map and collidableObjs ArrayList.
	 */
	private void addBird()
	{
		Bird bird = new Bird();
		Thread birdThread = new Thread(bird);
		add(bird, Game.APPLICATION_WIDTH, -Game.APPLICATION_HEIGHT * 0.08
				- (Game.APPLICATION_HEIGHT * 0.2) * RandomGenerator.getInstance().nextDouble());
		birdThread.start();
		collidableObjs.add(bird);
	}

	/**
	 * Adds a bird to the given location in the map and collidableObjs ArrayList.
	 * 
	 * @param x
	 *            - coordinate
	 * @param y
	 *            - coordinate
	 */
	public void addBird(double x, double y)
	{
		Bird bird = new Bird();
		Thread birdThread = new Thread(bird);
		add(bird, x, y);
		birdThread.start();
		collidableObjs.add(bird);
	}

	/**
	 * Makes the illusion of endless ground by changing the location of the ground
	 * image that is out of the screen.
	 */
	private void createGround()
	{
		if (groundImages.get(0).getX() + groundImages.get(0).getWidth() < 0)
		{
			groundImages.get(0).setLocation(
					groundImages.get(1).getX() + groundImages.get(1).getWidth() + scrollSpeed, 0);
		}
		else if (groundImages.get(1).getX() + groundImages.get(1).getWidth() < 0)
		{
			groundImages.get(1).setLocation(
					groundImages.get(0).getX() + groundImages.get(0).getWidth() + scrollSpeed, 0);
		}
	}

	/**
	 * Scrolls the map to the left. Checks collision before scrolling. Also removes
	 * the objects that are outside screen to save memory.
	 */
	private void scroll()
	{
		checkCollision();

		for (GImage img : groundImages)
		{
			img.move(scrollSpeed, 0);// Moves ground images
		}

		for (int i = 0; i < clouds.size(); i++)
		{
			clouds.get(i).move(Constants.INITIAL_SCROLL * 0.5, 0);

			if (clouds.get(i).getX() + clouds.get(i).getWidth() < 0)// Removes invisible clouds to
																	// save memory
			{
				remove(clouds.get(i));
				clouds.remove(clouds.get(i));
			}
		}

		for (int i = 0; i < collidableObjs.size(); i++)
		{
			if (collidableObjs.get(i).getX() + collidableObjs.get(i).getWidth() < 0)// Removes
																					// invisible
																					// cactuses to
																					// save memory
			{
				if (collidableObjs.get(i) instanceof Bird)
					((Bird) collidableObjs.get(i)).setOutside(true);
				remove(collidableObjs.get(i));
				collidableObjs.remove(collidableObjs.get(i));
			}
			if (!collidableObjs.isEmpty())// Moves cactuses
				collidableObjs.get(i).move(scrollSpeed, 0);
		}

		if (plane != null) plane.move(Constants.INITIAL_SCROLL * 0.7, 0);// Moves the plane

		for (int i = 0; i < celestials.size(); i++)// Moves celestials
		{
			if (celestials.get(i) instanceof Moon)// Moon
			{
				celestials.get(i).move(Constants.INITIAL_SCROLL * 0.08, 0);
			}
			else// Star
			{
				celestials.get(i).move(Constants.INITIAL_SCROLL * 0.1, 0);
			}
		}
	}

	/**
	 * Checks the collision of TRex with objects in the collidableObjs ArrayList.
	 */
	private void checkCollision()
	{
		ArrayList<GRect> objPoints = new ArrayList<GRect>();
		ArrayList<GRect> rexPoints = new ArrayList<GRect>();
		rex.setWillCollide(false);

		for (int i = 0; i < collidableObjs.size(); i++)// For each object
		{
			if (!stop && collidableObjs.get(i).getX() < rex.getX() + rex.getWidth()
					&& collidableObjs.get(i).getX() + collidableObjs.get(i).getWidth() > rex.getX())// If
																									// an
																									// object
																									// is
																									// close
																									// enough
																									// to
																									// Trex
			{
				objPoints = ((Collidable) collidableObjs.get(i)).getHitBox().getHitPoints();// Hitpoints
																							// of
																							// object
				rexPoints = rex.getHitBox().getHitPoints();// Hitpoints of Trex

				for (int j = 0; j < objPoints.size(); j++)// For each hitPoint of object
				{
					GPoint oTopLeft = ((Collidable) collidableObjs.get(i))
							.getCanvasPoint(objPoints.get(j).getX(), objPoints.get(j).getY());// Top-left
																								// corner
																								// of
																								// hitPoint

					GRect oRect = new GRect(oTopLeft.getX(), oTopLeft.getY(),
							objPoints.get(j).getWidth(), objPoints.get(j).getHeight());// A GRect
																						// representing
																						// hitPoint
																						// in Map.

					for (int k = 0; k < rexPoints.size(); k++)// For each hitpoint of Trex
					{
						GPoint rTopLeft = rex.getCanvasPoint(rexPoints.get(k).getX(),
								rexPoints.get(k).getY());// Top-left corner of TRex

						GRect rRect = new GRect(rTopLeft.getX() - scrollSpeed, rTopLeft.getY(),
								rexPoints.get(k).getWidth(),
								(rexPoints.get(k).getHeight() + rex.getDy() * 0.15));// A GRect
																						// representing
																						// hitPoint
																						// in Map.

						if (oRect.getBounds().intersects(rRect.getBounds()))// If hitPoints
																			// intersect
						{
							rex.setDy(0);
							stop = true;
							Trex.setDead(true);
							rex.setWillCollide(false);
							return;
						}

						if (rex.getDy() > 0
								&& rRect.getY() + rRect.getHeight() + rex.getDy() > oRect.getY())// If
																									// there
																									// is
																									// a
																									// chance
																									// that
																									// there
																									// will
																									// be
																									// a
																									// collision
						{
							rex.setWillCollide(true);

							if (rex.getDy() < 0)
							{
								rex.setDy(0);
							}
						}
						else
						{
							rex.setWillCollide(false);
						}
					}
				}
			}
		}
	}

	/**
	 * Equalizes scroll speed to the default value.
	 */
	public void resetScrollSpeed()
	{
		scrollSpeed = Constants.INITIAL_SCROLL;
	}

	// ------------------GETTERS & SETTERS------------------
	/**
	 * Sets the boolean <code>stop</code> to the given value.
	 * @param stop - a boolean indicating whether the Map should stop scrolling
	 */
	public void setStop(boolean stop)
	{
		this.stop = stop;
	}

	/**
	 * Returns true if the map is stopped.
	 * @return stop - a boolean indicating whether the Map should stop scrolling
	 */
	public boolean isStopped()
	{
		return stop;
	}

	/**
	 * Returns current scroll speed of Map.
	 * @return scrollSpeed - scroll speed
	 */
	public double getScrollSpeed()
	{
		return scrollSpeed;
	}

	/**
	 * Sets the scroll speed of Map to the given value.
	 * @param s - scroll speed
	 */
	public void setScrollSpeed(double s)
	{
		this.scrollSpeed = s;
	}

	/**
	 * Returns the ArrayList containing Collidable objects.
	 * @return collidableObjs - an ArrayList containing Collidable objects.
	 */
	public ArrayList<GObject> getCollidableObjs()
	{
		return collidableObjs;
	}

	/**
	 * Returns the ArrayList containing clouds.
	 * @return clouds - an ArrayList containing clouds.
	 */
	public ArrayList<GImage> getClouds()
	{
		return clouds;
	}

	/**
	 * Returns the plane.
	 * @return plane
	 */
	public Plane getPlane()
	{
		return plane;
	}
}
