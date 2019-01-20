package gui;

import java.awt.Color;
import acm.graphics.GLabel;
import tRex.Game;
import util.Constants;

/**
 * A child class of GLabel that is used in Game to show the current score and/or
 * the highest score.
 * 
 * @author Terlan
 *
 */
public class ScoreLabel extends GLabel implements Runnable
{
	/** Indicates whether the label should blink.*/
	private boolean blink;
	/**The number of blinks.*/
	private byte blinkCount;

	/**
	 * Initializes ScoreLabel data with the given score.
	 * 
	 * @param score
	 *            - current score of the player
	 */
	public ScoreLabel(int score)
	{
		super(String.valueOf(score));
		setFont(ResumeGUI.getMyFont());
	}

	/**
	 * Initializes ScoreLabel data with the score of 0.
	 */
	public ScoreLabel()
	{
		this(0);
	}

	/**
	 * Continuously updates the ScoreLabel after pausing a few milliseconds.
	 */
	@Override
	public void run()
	{
		while (true)
		{
			updateLabel();

			pause(Constants.SCORE_INCREASE_TIME);
		}
	}

	/**
	 * Updates the ScoreLabel with the latest score info and blinks after each 100 score.
	 */
	private void updateLabel()
	{
		if (Game.getScore() != 0 && Game.getScore() % 100 == 0)// After each 100 score, the label should blink.
		{
			setLabel(String.valueOf(Game.getScore()));
			blink = true;
		}
		else if (!blink)
		{
			setLabel(String.valueOf(Game.getScore()));
		}

		if (blink)// If label should blink
		{
			setVisible(!isVisible());// If label is visible, makes it invisible and vice versa.
			blinkCount++;
			pause(35);
		}
		if (blinkCount > 5)// Stops blinking after 5 blinks.
		{
			blinkCount = 0;
			blink = false;
		}
	}
	
	/**
	 * Sets the ScoreLabel using the given string.
	 * @param str - string containing score info.
	 */
	@Override
	public void setLabel(String str)
	{
		if (!Character.isAlphabetic(str.charAt(0)))// If ScoreLabel will contain the current score (it will not have the
													// word "HI:")
		{
			setColor(new Color(120, 120, 120));
			int score = Integer.parseInt(str);

			String result = "";

			for (int i = 0; i < 5 - str.length(); i++)// Adds zeros to the beginning of result string.
			{
				result += '0';
			}
			result += score;// Adds score to the result string.
			super.setLabel(result);
		}
		else// If ScoreLabel will contain high score
		{
			setColor(new Color(160, 160, 160));

			String number = "";
			StringBuilder builder = new StringBuilder(str);

			for (int i = 0; i < str.length(); i++)// Finds the number part of the string by excluding all non-digit
													// characters.
			{
				if (Character.isDigit(str.charAt(i)))
				{
					number += str.charAt(i);
				}
			}

			for (int i = 0; i < 5 - number.length(); i++)// Inserts zeros to str.
			{
				str = builder.insert(3, "0").toString();
			}

			super.setLabel(str);
		}
	}
}
