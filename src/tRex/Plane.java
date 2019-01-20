package tRex;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.util.RandomGenerator;
import util.Constants;

/**
 * A plane that is going to randomly appear on the screen and fly from right to
 * left
 * 
 * @author terlanismayilsoy
 *
 */
public class Plane extends GCompound
{
	/**A propeller image that rotates*/
	private GImage propeller = new GImage("/background/propeller.gif");
	/**The body part of the plane*/
	private GImage body = new GImage("/background/PLANE.png");
	/**Banner message that the plane will carry*/
	private GImage banner;

	/**
	 * A constructor that gives this Plane a random banner message to carry and sets
	 * the size and location of the components of this compound.
	 */
	public Plane()
	{
		if (Game.isDarkMode())// If the game is in dark mode, change the image of plane with the
								// dark one.
		{
			setBody("/background/darkPLANE.png");
		}

		int type = RandomGenerator.getInstance().nextInt(1, 3);

		switch (type)//Initializes banner according to the type.
		{
		case 1:
			banner = new GImage("/background/goodtimes.png");
			banner.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.BANNER_TYPE_1_SCALE);
			banner.setLocation(body.getWidth() * 0.42, body.getHeight() * 0.34);
			break;
		case 2:
			banner = new GImage("/background/easycheesy.png");
			banner.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.BANNER_TYPE_2_SCALE);
			banner.setLocation(body.getWidth() * 0.4, body.getHeight() * 0.34);
			break;
		case 3:
			banner = new GImage("/background/as999.png");
			banner.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.BANNER_TYPE_3_SCALE);
			banner.setLocation(body.getWidth() * 0.38, body.getHeight() * 0.39);
			break;
		}

		body.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.PLANE_BODY_SCALE);

		add(body);
		add(banner);

		propeller.scale(Constants.PLANE_PROPELLER_SCALE);
		add(propeller, 0, body.getHeight() * 0.328);
	}

	/**
	 * Sets the body image of the Plane.
	 * @param imgPath - path of the image
	 */
	public void setBody(String imgPath)
	{
		body.setImage(imgPath);
		body.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * Constants.PLANE_BODY_SCALE);
	}
}
