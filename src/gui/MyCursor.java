package gui;

import acm.graphics.GImage;

/**
 * A subclass of GImage that serves as a cursor in the MenuCanvas class.
 * @author Terlan
 *
 */
public class MyCursor extends GImage
{
	/**
	 * Initializes MyCursor data.
	 */
	public MyCursor()
	{
		super("coinMouse.png");
		scale(0.07);
	}
	
	/**
	 * Activates the cursor by removing the coin from the cursor.
	 */
	public void activate()
	{
		setImage("Mouse.png");
		scale(0.07);
	}
}
