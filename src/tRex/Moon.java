package tRex;

import acm.graphics.GImage;
import acm.util.RandomGenerator;
/**
 * A subclass of GImage with a random moon image. 
 * @author Terlan
 *
 */
public class Moon extends GImage
{
	/**
	 * Creates a Moon object with a scaled random moon image.
	 */
	public Moon()
	{
		this("/background/moon" + RandomGenerator.getInstance().nextInt(1, 7) + ".png");
		scale(0.6);
	}
	
	/**
	 * Creates a Moon object with given image.
	 * @param imgPath - path of image
	 */
	private Moon(String imgPath)
	{
		super(imgPath);
	}
}
