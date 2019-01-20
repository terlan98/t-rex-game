package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;

/**
 * A subclass of GCompound that contains a label and two buttons for validating
 * whether the user wants to resume the game from save file or not.
 * 
 * @author Terlan
 *
 */
public class ResumeGUI extends GCompound
{
	/**A label containing a question*/
	private GLabel label;
	/**Yes button*/
	private GImage btn_yes;
	/**No button*/
	private GImage btn_no;
	/**A custom font*/
	private static Font myFont;

	/**
	 * Initializes ResumeGUI data.
	 */
	public ResumeGUI()
	{
		label = new GLabel("Resume game?");
		btn_yes = new GImage("yesButton.png");
		btn_no = new GImage("noButton.png");

		try
		{
			myFont = Font
					.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("./data/PressStart2P-Regular.ttf")))
					.deriveFont(Font.PLAIN, 15);///Creates a new Font from file.
		}
		catch (FontFormatException | IOException e)
		{
			System.err.println("There was a problem with font");
		}

		addLabel();
		addButtons();
	}

	/**
	 * Sets font and color of label and adds it to ResumeGUI.
	 */
	private void addLabel()
	{
		label.setFont(myFont);
		label.setColor(new Color(120, 120, 120));
		add(label);
	}

	/**
	 * Scales and adds buttons to ResumeGUI.
	 */
	private void addButtons()
	{
		btn_yes.scale(0.5);
		btn_no.scale(0.5);

		add(btn_yes, label.getWidth() * 0.15, label.getHeight() * 2);
		add(btn_no, label.getWidth() * 0.55, label.getHeight() * 2);
	}

	//------------GETTERS & SETTERS------------
	/**
	 * Returns the "Yes" button.
	 * @return - btn_yes
	 */
	public GImage getBtn_yes()
	{
		return btn_yes;
	}
	
	/**
	 * Returns the "No" button.
	 * @return - btn_no
	 */
	public GImage getBtn_no()
	{
		return btn_no;
	}

	/**
	 * Returns the font that is used in the label of ResumeGUI.
	 * @return - myFont
	 */
	public static Font getMyFont()
	{
		return myFont;
	}
}
