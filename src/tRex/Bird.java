package tRex;

import acm.graphics.GImage;
import acm.util.RandomGenerator;

/**
 * A Bird that is Collidable. Any instance of this class should have its own
 * thread (for flying animation).
 * 
 * @author Terlan
 *
 */
public class Bird extends Collidable implements Runnable
{
	/**Bird image*/
	private GImage img;
	/**Indicates whether the bird is outside the screen*/
	private boolean isOutside;
	/**State of the bird*/
	private byte state = (byte) RandomGenerator.getInstance().nextInt(1, 2);// State should be random in the beginning

	/**
	 * Creates a new instance of Bird class and initializes some data.
	 */
	public Bird()
	{
		img = new GImage("/collidables/bird" + state + ".png");
		add(img);

		hitBox = new HitBox(this);
		add(hitBox);
	}

	/**
	 * This is where the bird performs the flying animation by switching between its
	 * states.
	 */
	@Override
	public void run()
	{
		while (!Trex.isDead() && !isOutside)
		{
			setState(3 - state);// Switch the state (since there are 2 states, (3-state) will give us the other
								// state each time
			pause(130);
		}
	}

	/**
	 * Returns the current state of the bird.
	 * 
	 * @return state
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * Sets the state of the bird by taking into account dark mode.
	 * 
	 * @param state
	 */
	public void setState(int state)
	{
		this.state = (byte) state;

		if (Game.isDarkMode())// If the game is in dark mode, create dark birds
		{
			img.setImage("/collidables/darkBird" + state + ".png");
		}
		else
		{
			img.setImage("/collidables/bird" + state + ".png");
		}

		updateHitBox();
	}
	
	/**
	 * Sets the boolean <code>outside</code> true which leads to the end of the
	 * bird's thread.
	 * 
	 * @param isOutside
	 */
	public void setOutside(boolean isOutside)
	{
		this.isOutside = isOutside;
	}

	/**
	 * Removes the current HitBox and adds a new one.
	 */
	private void updateHitBox()
	{
		remove(hitBox);
		hitBox = new HitBox(this);
		add(hitBox);
	}
}
