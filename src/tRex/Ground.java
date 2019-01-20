package tRex;

import acm.graphics.GImage;
import util.Constants;

/**
 * A subclass of GImage.
 * @author Terlan
 *
 */
public class Ground extends GImage
{
	/**
	 * Initializes Ground data and scales it.
	 */
	public Ground()
	{
		super("ground.png");
		scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.SCALE_FACTOR);
	}
}
