package tRex;

import acm.graphics.GImage;
import util.Constants;
/**
 * A subclass of GImage.
 * @author Terlan
 *
 */
public class Cloud extends GImage
{
	/**
	 * Creates a new cloud with either dark or bright image depending on the game mode.
	 */
	public Cloud()
	{
		super("/background/cloud.png");
		scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.CLOUD_SCALE);
		if (Game.isDarkMode())// If the game is in dark mode, change cloud image to dark cloud
		{
			setImage("/background/darkCloud.png");
		}
	}
	
	/**
	 * A constructor that uses image path to set the image and scales it after setting.
	 * @param imgPath
	 */
	private Cloud(String imgPath)
	{
		super(imgPath);
		scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.CLOUD_SCALE);
	}
	
	/**
	 * An overridden version of setImage that scales the image after setting the image.
	 */
	@Override
	public void setImage(String imgPath)
	{
		super.setImage(imgPath);
		scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.CLOUD_SCALE);
	}
}
