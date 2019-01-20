package gui;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import tRex.Game;

/**
 * A subclass of GCompound that includes two GImages. One of those images acts
 * as a button whereas the other one is a text saying "GAME OVER".
 * 
 * @author Terlan
 *
 */
public class GameOverGUI extends GCompound
{
	/**An image that has the "GAME OVER" text.*/
	private GImage gameOverImg;
	/**An image that will represent the restart button.*/
	private GImage btn_restart;

	/**
	 * Initializes GameOverGUI data.
	 */
	public GameOverGUI()
	{
		if (Game.isDarkMode())//Uses dark images for dark mode
		{
			gameOverImg = new GImage("darkGameOver.png");
			btn_restart = new GImage("darkReplayButton.png");
		}
		else
		{
			gameOverImg = new GImage("gameOver.png");
			btn_restart = new GImage("replayButton.png");
		}

		gameOverImg.scale((Game.APPLICATION_HEIGHT + Game.APPLICATION_WIDTH) * 0.0006);
		btn_restart.scale((Game.APPLICATION_HEIGHT + Game.APPLICATION_WIDTH) * 0.0005);

		add(gameOverImg);
		add(btn_restart, gameOverImg.getWidth() / 2 - btn_restart.getWidth() / 2, gameOverImg.getHeight() * 2);
	}
	
	
	/**
	 * Initializes GameOverGUI data and sets its location to the given coordinate.
	 * @param x - coordinate
	 * @param y - coordinate
	 */
	public GameOverGUI(double x, double y)
	{
		this();
		setLocation(x, y);
	}
	
	/**
	 * Returns the restart button.
	 * @return - btn_restart - restart button.
	 */
	public GImage getBtn_restart()
	{
		return btn_restart;
	}
}
