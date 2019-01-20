package tRex;

import acm.graphics.GImage;
import acm.util.RandomGenerator;

/**
 * A Cactus that is Collidable. Has an image, order (the type of cactus),
 * and a boolean indicating whether the cactus should be big.
 * 
 * @author Terlan
 *
 */
public class Cactus extends Collidable
{
	/**Cactus image*/
	private GImage img;
	/**Order(type) of cactus*/
	private short order;
	/**Indicates whether the cactus is big or not*/
	private boolean isBig;

	/**
	 * Creates an instance of Cactus class and initializes some data.
	 * @param isBig - a boolean indicating whether the cactus should be big.
	 */
	public Cactus(boolean isBig)
	{
		order = (short) (RandomGenerator.getInstance().nextInt(1,6));
		this.isBig = isBig;
		
		createCactusImg();

		add(img);
		hitBox = new HitBox(this);
		add(hitBox);
	}

	/**
	 * Creates a cactus image by taking into account dark mode and size of cactus.
	 */
	private void createCactusImg()
	{
		if (Game.isDarkMode())//Creates dark cactus if the game is in dark mode.
		{
			if (isBig)
			{
				img = new GImage("/collidables/darkBCactus" + order + ".png");
				img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.00051);
			}
			else
			{
				img = new GImage("/collidables/darkCactus" + order + ".png");
				img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.0005);
			}
		}
		else//Creates normal cactus if the game is in bright mode.
		{
			if (isBig)
			{
				img = new GImage("/collidables/bCactus" + order + ".png");
				img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.00051);
			}
			else
			{
				img = new GImage("/collidables/cactus" + order + ".png");
				img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.0005);
			}
		}
	}
	
	/**
	 * Sets the image of cactus using the given image path.
	 * @param imgPath - path of image
	 */
	public void setImage(String imgPath)
	{
		img.setImage(imgPath);
		if(imgPath.contains("b") || imgPath.contains("B"))//If image path contains 'B' or 'b', big cactus should be created.
		{
			img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.00051);
		}
		else//Small cactus
		{
			img.scale((Game.APPLICATION_WIDTH + Game.APPLICATION_HEIGHT) * 0.0005);
		}
	}

	/**
	 * Returns the order of this cactus.
	 * @return order
	 */
	public int getOrder()
	{
		return order;
	}

	/**
	 * Returns true if cactus is big.
	 * @return - isBig
	 */
	public boolean isBig()
	{
		return isBig;
	}
}
