package gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import tRex.Game;

/**
 * A subclass of GCanvas that works as a main menu of the game.
 * 
 * @author Terlan
 *
 */
public class MenuCanvas extends GCanvas
{
	private static final long serialVersionUID = 1L;
	/** Bottom part of the menu. */
	private GImage menuBottom;
	/** An image of TRex with cactuses that serves a decorative purpose. */
	private GImage menuImg;
	/** A coin inserter image. */
	private GImage coinInserter;
	/** A cursor that has a coin on it. */
	private static MyCursor cursor;
	/** Indicates whether the user has inserted the coin or not. */
	private boolean coinInserted;

	/**
	 * Initializes MenuCanvas data.
	 */
	public MenuCanvas()
	{
		setCursor(getToolkit().createCustomCursor(// Removes the default mouse cursor
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));

		initializeMenuImg();

		initializeMenuBottom();

		cursor = new MyCursor();
		add(cursor);

		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{

			}

			/**
			 * Detects mouse press and starts an animation if the user has clicked on
			 * coinInserter.
			 */
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (!isCoinInserted() && coinInserter.contains(e.getX(), e.getY()))
				{
					addCoinInserter("coinLoad.gif");
					cursor.sendToFront();
					setCoinInserted(true);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{

			}
		});

		addMouseMotionListener(new MenuMouseMotionListener());
	}

	/**
	 * Creates menuImg and adds it to the proper location.
	 */
	private void initializeMenuImg()
	{
		setMenuImg(new GImage("menu.png"));// adds a background image
		getMenuImg().setSize(Game.APPLICATION_WIDTH, Game.APPLICATION_HEIGHT * 0.7);
		add(getMenuImg());
	}

	/**
	 * Creates menuBottom and adds it to the proper location. Also adds a coin
	 * inserter.
	 */
	private void initializeMenuBottom()
	{
		setMenuBottom(new GImage("menuBottom.png"));// Adds the bottom part of the menu
		getMenuBottom().setSize(Game.APPLICATION_WIDTH, Game.APPLICATION_HEIGHT * 0.3);
		add(getMenuBottom(), 0, Game.APPLICATION_HEIGHT - menuBottom.getHeight());

		addCoinInserter("coin.png");// Adds the coin inserter
	}

	/**
	 * Adds a coin inserter using the image path.
	 * 
	 * @param imgPath
	 */
	public void addCoinInserter(String imgPath)
	{
		if (getCoinInserter() != null)// Remove the existing coin inserter
		{
			remove(getCoinInserter());
		}
		setCoinInserter(new GImage(imgPath));
		getCoinInserter().setSize(getMenuBottom().getWidth() * 0.103,
				getMenuBottom().getHeight() * 0.65);
		add(getCoinInserter(), getMenuBottom().getWidth() * 0.735, getMenuBottom().getY() * 1.14);

		if (cursor != null)// Change cursor
		{
			cursor.activate();
		}
	}

	/**
	 * Moves the bottom part of the menu and coin inserter down.
	 */
	public void menuDown()
	{
		getMenuBottom().move(0, 6);
		getCoinInserter().move(0, 6);
	}

	/**
	 * Returns the menuImg.
	 * 
	 * @return - menuImg
	 */
	public GImage getMenuImg()
	{
		return menuImg;
	}

	/**
	 * Sets the menuImg.
	 * 
	 * @param menuImg
	 */
	public void setMenuImg(GImage menuImg)
	{
		this.menuImg = menuImg;
	}

	/**
	 * Returns the bottom part of menu.
	 * 
	 * @return - menuBottom - bottom part of menu.
	 */
	public GImage getMenuBottom()
	{
		return menuBottom;
	}

	/**
	 * Sets the image of the bottom part of the menu.
	 * 
	 * @param menuBottom
	 */
	public void setMenuBottom(GImage menuBottom)
	{
		this.menuBottom = menuBottom;
	}

	/**
	 * Returns the coin inserter.
	 * 
	 * @return - coin inserter image.
	 */
	public GImage getCoinInserter()
	{
		return coinInserter;
	}

	/**
	 * Sets the coin inserter.
	 * 
	 * @param coinInserter
	 */
	public void setCoinInserter(GImage coinInserter)
	{
		this.coinInserter = coinInserter;
	}

	/**
	 * Returns true if the user has inserted the coin to coin inserter.
	 * 
	 * @return - coinInserted - a boolean indicating whether the user has inserted
	 *         the coin to coin inserter.
	 */
	public boolean isCoinInserted()
	{
		return coinInserted;
	}

	/**
	 * Sets coinInserted.
	 * 
	 * @param coinInserted
	 *            - a boolean indicating whether the user has inserted the coin to
	 *            coin inserter.
	 */
	public void setCoinInserted(boolean coinInserted)
	{
		this.coinInserted = coinInserted;
	}

	/**
	 * Returns the cursor image.
	 * 
	 * @return - cursor image
	 */
	public static GImage getCursorImg()
	{
		return cursor;
	}
}
