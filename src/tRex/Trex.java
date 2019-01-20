package tRex;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import util.Constants;

public class Trex extends GCompound implements Runnable
{
	/**Image of TRex*/
	private GImage rImage;
	/**A HitBox containing hitPoints*/
	private HitBox hitBox;
	/**Initial y coordinate of TRex*/
	private double initY;
	/**Initial x coordinate of TRex*/
	private double initX;
	/**A string indicating the state of TRex*/
	private String state;
	/**A string indicating the previous state of TRex*/
	private String prevState;
	/**Y speed of TRex*/
	private double dy;
	/**Indicates whether the TRex is dead*/
	private static boolean dead;
	/**Indicates whether the TRex will collide with an object*/
	private boolean willCollide;
	/**A sound clip that makes the sound of jumping*/
	private Clip jumpSound;
	/**A sound clip that makes the sound of dying*/
	private Clip deadSound;
	

	public Trex()
	{
		rImage = new GImage("run.gif");
		add(rImage);

		scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.TREX_SCALE_FACTOR);

		loadSounds();
	}

	@Override
	public void run()
	{
		initY = getY();
		initX = getX();

		hitBox = new HitBox(this);
		add(hitBox);

		while (true)
		{
			if (!dead)
			{
				checkState();
				pause(9);
			}
			else
			{
				die();

				while (dead)
				{
					pause(Constants.PAUSE_TIME);
				}
			}
		}
	}

	/**
	 * Checks the current state of TREx and controls TRex's behavior.
	 */
	private void checkState()
	{
		if (state == "j")
		{
			if (Game.isDarkMode())
			{
				setImage("nightNormal.png");
			}
			else
			{
				setImage("normal.png");
			}
			setLocation(initX, initY);
			jump();
		}
		else if (state == "f")
		{
			fall();
		}
		else if (state == "d")
		{
			if (Game.isDarkMode())
			{
				setImage("nightDuckRun.gif");
			}
			else
			{
				setImage("duckRun.gif");
			}
			setLocation(initX, initY * 1.07);
		}
		else if (prevState != state || Game.isDarkMode())
		{
			setState("");
			if (Game.isDarkMode())
			{
				setImage("nightRun.gif");
			}
			else
			{
				setImage("run.gif");
			}
			setLocation(initX, initY);
		}
	}

	/**
	 * Moves TRex up with sound.
	 */
	public void jump()
	{
		jumpSound.stop();
		jumpSound.setFramePosition(0);
		jumpSound.start();

		dy = Constants.J_SPEED;

		while (state != "f" && getY() > initY - Constants.J_HEIGHT)
		{
			move(0, dy);
			dy += Constants.GRAVITY;

			if (dy > 0) state = "f";

			pause(25);
		}

		if (getY() < initY - Constants.J_HEIGHT && state != "f")
		{
			state = "f";
		}
	}

	/**
	 * Moves TRex down with an acceleration.
	 */
	public void fall()
	{
		while (getY() <= initY && !dead)
		{
			if (!willCollide)
			{
				dy += Constants.GRAVITY;
			}

			if (getY() + dy > initY)// If reaching ground
			{
				dy = initY - getY();
				move(0, dy);
				break;
			}

			move(0, dy);
			pause(25);
		}

		dy = 0;
		setState("");
	}

	/**
	 * Kills TRex and plays a sound.
	 */
	public void die()
	{
		setState("dead");
		deadSound.stop();
		deadSound.setFramePosition(0);
		deadSound.start();

		if (Game.isDarkMode())
		{
			setImage("nightDead1.png");
		}
		else
		{
			setImage("dead1.png");
		}
		
		if (prevState == "d")
		{
			setLocation(initX, initY);
		}
	}

	/**
	 * Sets the image of TRex.
	 * @param imagePath - path of image
	 */
	public void setImage(String imagePath)
	{
		rImage.setImage(imagePath);
		rImage.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.TREX_SCALE_FACTOR);
		remove(hitBox);
		hitBox = new HitBox(this);
		add(hitBox);
	}

	/**
	 * Loads all sounds.
	 */
	private void loadSounds()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("./data/jump.wav").getAbsoluteFile());
			jumpSound = AudioSystem.getClip();
			jumpSound.open(audioInputStream);

			audioInputStream = AudioSystem
					.getAudioInputStream(new File("./data/dead.wav").getAbsoluteFile());
			deadSound = AudioSystem.getClip();
			deadSound.open(audioInputStream);

			audioInputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// ---------------GETTERS & SETTERS---------------
	/**
	 * Sets the state of TRex.
	 * @param state - state of TRex
	 */
	public void setState(String state)
	{
		prevState = this.state;
		this.state = state;
	}

	/**
	 * Returns the state of TRex
	 * @return state - state of TRex
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Sets the y speed of TRex.
	 * @param dy - y speed
	 */
	public void setDy(double dy)
	{
		this.dy = dy;
	}

	/**
	 * Returns the y speed of TRex.
	 * @return dy - y speed
	 */
	public double getDy()
	{
		return dy;
	}

	/**
	 * Returns the initial y coordinate of TRex.
	 * @return initY - initial y coordinate of TRex.
	 */
	public double getInitY()
	{
		return initY;
	}

	/**
	 * Returns the initial x coordinate of TRex.
	 * @return initX - initial x coordinate of TRex.
	 */
	public double getInitX()
	{
		return initX;
	}

	/**
	 * Returns true if TRex is dead.
	 * @return dead - a boolean indicating whether the TRex is dead.
	 */
	public static boolean isDead()
	{
		return dead;
	}

	/**
	 * Sets the boolean indicating whether the TRex is dead.
	 * @param dead - a boolean indicating whether the TRex is dead.
	 */
	public static void setDead(boolean dead)
	{
		Trex.dead = dead;
	}

	/**
	 * Returns the HitBox of the TRex.
	 * @return hitBox - HitBox of TRex
	 */
	public HitBox getHitBox()
	{
		return hitBox;
	}

	/**
	 * Sets the boolean indicating whether the TRex will collide with an object.
	 * @param willCollide - a boolean indicating whether the TRex will collide with an object.
	 */
	public void setWillCollide(boolean willCollide)
	{
		this.willCollide = willCollide;
	}
}
