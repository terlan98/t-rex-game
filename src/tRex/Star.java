package tRex;

import acm.graphics.GImage;

/**
 * A subclass of GImage that serves a decorative purpose in the dark mode of the
 * game.
 * 
 * @author Terlan
 *
 */
public class Star extends GImage
{
	/**
	 * Initializes Star data and scales it. Takes an integer as an argument
	 * indicating the type of the star.
	 * 
	 * @param num
	 *            - the number (type) of star
	 */
	public Star(int num)
	{
		this("/background/star" + num + ".png");
		scale(0.7);
	}

	/**
	 * Initializes the Star using super constructor.
	 * @param imgPath
	 */
	private Star(String imgPath)
	{
		super(imgPath);
	}
}
