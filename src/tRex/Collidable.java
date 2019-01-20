package tRex;

import acm.graphics.GCompound;

/**
 * This abstract class is for uniting Cactuses and Birds. It contains an instance of
 * HitBox and a getter which are necessary for collision detection.
 * 
 * @author Terlan
 *
 */
public abstract class Collidable extends GCompound
{
	/**A hitbox that should be instantiated by the subclass.*/
	HitBox hitBox;

	/**
	 * Returns HitBox of the Collidable.
	 * 
	 * @return hitBox
	 */
	public HitBox getHitBox()
	{
		return hitBox;
	}
}
