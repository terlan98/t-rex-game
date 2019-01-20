package tRex;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import gui.GameOverGUI;
import gui.MenuCanvas;
import gui.ResumeGUI;
import gui.ScoreLabel;
import util.Constants;

public class Game extends GraphicsProgram
{
	private static final long serialVersionUID = 1L;

	/** An instance of TRex */
	private Trex rex;
	/** An instance of map */
	private Map map;
	/** A scoreLabel that will show the current score */
	private ScoreLabel scoreLabel;
	/** A scoreLabel that will show the highest score */
	private ScoreLabel highScoreLabel;
	/** A GameOverGUI that will appear when the TRex is dead */
	private GameOverGUI gOver;
	/**
	 * A ResumeGUI that will appear when user should decide between resuming a game
	 * and starting a new one
	 */
	private ResumeGUI resume;
	/**Indicates whether the user clicked a button in ResumeGUI.*/
	private boolean resumeInteracted;
	/**Indicates whether the user wants to resume saved game.*/
	private boolean shouldResume;
	/**A sound clip that makes the sound of inserting a coin*/
	private Clip coinSound;
	/**A sound clip that makes the sound of successful coin insertion*/
	private Clip successSound;
	/**A sound clip that makes the sound of earning 100 score*/
	private Clip scoreUpSound;
	/**A MenuCanvas that welcomes the user in the beginning of the game*/
	private MenuCanvas menu;
	/**Current score*/
	private static int score;
	/**Highest score*/
	private static int hiScore;
	/**Indicates whether the dark mode is on or not*/
	private static boolean darkMode;
	/** Indicates how long the game is in dark mode. */
	private short darkCount;
	/** Indicates how long the TRex is dead. */
	private byte deadCount;
	/**Width of the application*/
	public static int APPLICATION_WIDTH = 650;
	/**Height of the application*/
	public static int APPLICATION_HEIGHT = 350;

	/**
	 * Initializes data for the game.
	 */
	@Override
	public void init()
	{
		menu = new MenuCanvas();

		addMenu();

		loadSounds();

		addKeyListeners();
		addMouseListeners();

		while (true)
		{
			if (menu.isCoinInserted())// When the coin is inserted a short animation will happen
			{
				animateMenu();
				break;
			}
			pause(Constants.PAUSE_TIME);
		}
		menu = null;// For cleaning up the RAM
		resume = new ResumeGUI();

		askForResume();
		initializeLabels();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()// Saves data before closing
		{
			public void run()
			{
				save();
			}
		}));
	}

	/**
	 * Adds TRex and map, loads saved data and controls the whole game.
	 */
	@Override
	public void run()
	{
		addTRex();
		addMap();

		if (shouldResume)// If user decided to resume previous game
		{
			load();
		}

		while (true)
		{
			if (!Trex.isDead())// Trex is alive
			{
				control();

				pause(Constants.SCORE_INCREASE_TIME);
			}
			else// T-Rex died
			{
				endGame();
				while (Trex.isDead())// While trex is dead, deadCount should increase.
				{
					deadCount++;

					if (deadCount % 5 == 0)// After each 5, reset the deadCounter. 5 has no specific
											// meaning. The purpose is to prevent deadCounter from
											// exceeding its maximum value limit.
					{
						deadCount = 1;
					}
					// NOTE: The purpose of deadCounter is to prevent the user from restarting the
					// game immediately after dying. It ensures that a few seconds have passed after
					// the death.
					pause(250);
				}
				setBackground(Color.WHITE);// Resets background color back to white.
			}
		}

	}

	/**
	 * Controls the game by increasing score, speed, turning dark mode on/off and
	 * adding objects to the map. This method should be called in a while loop
	 * continuously.
	 */
	private void control()
	{
		if (score % Constants.SPEED_INCREASE_SCORE == 0 && score != 0)// Increase scroll speed after
																		// each 100 score
		{
			increaseSpeed();
		}
		score++;

		if (score != 0 && score % Constants.NIGHT_SCORE == 0)// If score is evenly divisible by
																// NIGHT_SCORE, turn dark mode on
		{
			darkMode = true;
			map.switchImages();
		}

		if (darkCount > Constants.DARK_TIME)// Turn the dark mode off
		{
			darkMode = false;
			map.removeCelestials();

			darkCount = 0;
		}
		else if (darkCount == 4)// Celestials should be added after a while
		{
			map.addCelestials();
		}

		if (darkMode)
		{
			if (getBackground().getRed() != 0)// If red component of color is zero, then it is means
												// the map dark enough
			{
				setBackground(getBackground().darker());
				setBackground(getBackground().darker());
			}
			darkCount++;
		}
		else if (getBackground().getRed() != 255)// Switch back to bright mode
		{
			setBackground(getBackground().brighter());
			setBackground(getBackground().brighter());

			if (getBackground().getRed() == Constants.BRIGHT_SWITCH_COLOR)// When color reaches a
																			// certain value, switch
																			// images with brighter
																			// ones.
			{
				map.switchImages();
			}
		}

		map.addCollidables();
		map.addCloud();
		map.addPlane();
	}

	/**
	 * Does all the necessary things for ending the game properly.
	 */
	private void endGame()
	{
		saveHighScore();

		map.resetScrollSpeed();
		map.removeCelestials();
		map.switchImages();

		gOver = new GameOverGUI();
		add(gOver, (getWidth() - gOver.getWidth()) / 2, -(gOver.getHeight() - getHeight()) / 2);
	}

	/**
	 * Adds map with a new Thread to the game.
	 */
	private void addMap()
	{
		map = new Map(rex);
		Thread mapThread = new Thread(map);
		add(map, 0, getHeight() * 0.9);
		mapThread.start();
	}

	/**
	 * Adds TRex with a new Thread to the game.
	 */
	private void addTRex()
	{
		rex = new Trex();
		Thread rexThread = new Thread(rex);
		add(rex, getWidth() * 0.05, getHeight() * 0.81);
		rexThread.start();
	}

	/**
	 * Increases the speed of map and plays a sound.
	 */
	private void increaseSpeed()
	{
		map.setScrollSpeed(map.getScrollSpeed() + Constants.SCROLL_INCREASE);

		scoreUpSound.stop();
		scoreUpSound.setFramePosition(0);
		scoreUpSound.start();
	}

	/**
	 * Adds menuCanvas to the game and removes the default one.
	 */
	private void addMenu()
	{
		remove(getGCanvas());
		add(menu);
		revalidate();// Refreshes the screen to make the new canvas visible
	}

	/**
	 * Removes the MenuCanvas and restores default canvas.
	 */
	private void removeMenu()
	{
		add(getGCanvas());
		getGCanvas().requestFocus();
		remove(menu);
	}

	/**
	 * Animates the menu. This animation acts as a transition to the game.
	 */
	private void animateMenu()
	{
		coinSound.start();
		pause(1400);
		coinSound.stop();
		successSound.start();
		menu.addCoinInserter("coinDone.gif");

		menu.getMenuImg().setImage("tv.gif");// A short TV screen effect
		menu.getMenuImg().scale(2);

		while (menu.getMenuBottom().getY() <= getHeight())// Move menu down
		{
			menu.menuDown();
			pause(20);
		}
		menu.remove(menu.getMenuBottom());
		menu.remove(menu.getCoinInserter());
		pause(100);
		menu.remove(menu.getMenuImg());

		GImage trexImg = new GImage("normal.png");
		Ground groundImg = new Ground();

		trexImg.scale((APPLICATION_WIDTH + APPLICATION_HEIGHT) * Constants.TREX_SCALE_FACTOR);

		menu.add(trexImg, getWidth() * 0.05, -trexImg.getHeight());
		menu.add(groundImg, 0, getHeight());

		while (groundImg.getY() > getHeight() * 0.82)// Brings the ground to the screen
		{
			groundImg.move(0, -3);
			pause(20);
		}

		while (trexImg.getY() < groundImg.getY() - trexImg.getHeight() * 0.717)// Brings T-Rex to
																				// the screen
		{
			trexImg.move(0, 7);
			pause(12);
		}

		removeMenu();
		getMenuBar().removeAll();// removes all items from the menu bar such as 'File', 'Edit', etc.

		menu = null;// Assigning null is for saving RAM.
		trexImg = null;
		groundImg = null;
		coinSound = null;
		successSound = null;
	}

	/**
	 * Initializes scoreLabel and highScoreLabel. Also loads the high score.
	 */
	private void initializeLabels()
	{
		scoreLabel = new ScoreLabel();
		add(scoreLabel, getWidth() * 0.84, getHeight() * 0.11);
		Thread ScoreThread = new Thread(scoreLabel);
		ScoreThread.setPriority(3);
		ScoreThread.start();

		highScoreLabel = new ScoreLabel();
		loadHighScore();
	}

	/**
	 * Writes high score to a text file and updates the highScoreLabel.
	 */
	private void saveHighScore()
	{
		if (score <= hiScore)// If the current score is less than high score, no need to update the
								// high score
		{
			return;
		}

		try
		{
			// ---------Write score to a txt file---------
			PrintWriter pw = new PrintWriter(new FileWriter("scoreSave.txt"), true);
			pw.println(score);
			pw.close();
			// -------------------------------------------

			hiScore = score;
			highScoreLabel.setLabel(String.valueOf("HI:" + hiScore));// Update the high score label

			add(highScoreLabel, scoreLabel.getX() - highScoreLabel.getWidth() * 1.1,
					scoreLabel.getY());
		}
		catch (IOException e)
		{
			System.err.println("There was a problem with input/output when saving high score");
		}
	}

	/**
	 * Loads high score from a text file and updates the highScoreLabel.
	 */
	private void loadHighScore()
	{
		if (isFileEmpty("scoreSave.txt"))
		{
			return;
		}
		BufferedReader rd;
		try
		{
			rd = new BufferedReader(new FileReader("scoreSave.txt"));
			String line = rd.readLine();
			if (line != null)
			{
				hiScore = Integer.parseInt(line);
				highScoreLabel.setLabel(String.valueOf("HI:" + hiScore));// Update the high score
																			// label

				add(highScoreLabel, scoreLabel.getX() - highScoreLabel.getWidth() * 1.1,
						scoreLabel.getY());
			}
		}
		catch (FileNotFoundException e1)
		{
			System.out.println("No high score saved until now");
		}
		catch (IOException e)
		{
			System.err.println("There was a problem in saving process.");
		}
	}

	/**
	 * Saves the current state of the game to a text file. <br>
	 * The order of writing is the following:
	 * <ol>
	 * <li>isDarkMode</li>
	 * <li>Current score</li>
	 * <li>Current scroll speed</li>
	 * <li>Y coordinate of TRex</li>
	 * <li>Collidable objects (cactuses and birds)</li>
	 * <li>Clouds</li>
	 * <li>Plane</li>
	 * </ol>
	 * <span style="color:red; font-weight:bold">Important note:</span> x
	 * coordinates are followed by y coordinates
	 */
	private void save()
	{
		PrintWriter pw;
		if (Trex.isDead())// If TRex is dead, erase the save file and return.
		{
			try
			{
				pw = new PrintWriter(new FileWriter("save.txt"), true);
				pw.close();
			}
			catch (IOException e)
			{
				System.err.println("There was a problem in the saving process.");
			}
			return;
		}

		try
		{
			pw = new PrintWriter(new FileWriter("save.txt"), true);

			pw.println(isDarkMode());// Dark mode
			pw.println(score);// Current score
			pw.println(map.getScrollSpeed());// Scroll speed
			pw.println(rex.getY());// Y-coordinate of TRex

			pw.println("Cactus:");
			for (int i = 0; i < map.getCollidableObjs().size(); i++)// Collidables
			{
				if (map.getCollidableObjs().get(i) instanceof Cactus)
				{
					pw.println(((Cactus) map.getCollidableObjs().get(i)).isBig());
				}
				else
				{
					pw.println("Bird");
				}
				pw.println(map.getCollidableObjs().get(i).getX());
				pw.println(map.getCollidableObjs().get(i).getY());
			}

			pw.println("Clouds:");
			for (int i = 0; i < map.getClouds().size(); i++)// Clouds
			{
				pw.println(map.getClouds().get(i).getX());
				pw.println(map.getClouds().get(i).getY());
			}

			pw.println("Plane:");// Plane
			pw.println(map.getPlane().getX());
			pw.println(map.getPlane().getY());

			pw.close();
		}
		catch (IOException e)
		{
			System.out.println("There was a problem in the saving process.");
		}
	}

	/**
	 * Checks whether a text file is empty or not.
	 * 
	 * @param name
	 *            - name of the file
	 * @return <span style = "font-weight:bold"> true</span> - if the file is empty
	 */
	private boolean isFileEmpty(String name)
	{
		BufferedReader rd = null;

		try
		{
			rd = new BufferedReader(new FileReader(name));
			String line = rd.readLine();
			if (line == null)// If the line is null, the file is empty.
			{
				return true;
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println(name +" was not found. A new save file will be created...");
			return true;
		}
		catch (IOException e)
		{
			System.err.println(
					"An error with input/output occured when checking whether a file is empty.");
			return true;
		}
		finally
		{
			try
			{
				if (rd != null)
				{
					rd.close();
				}
			}
			catch (IOException e)
			{
				System.err.println("Couldn't close the reader.");
			}
		}
		return false;
	}

	/**
	 * Asks the user if he/she wants to resume the game by adding a resumeGUI to the
	 * canvas. This method keeps waiting until the user responds.
	 */
	private void askForResume()
	{
		if (isFileEmpty("save.txt")) return;

		add(resume, (APPLICATION_WIDTH - resume.getWidth()) / 2,
				(APPLICATION_HEIGHT - resume.getHeight()) / 2);// Adds resumeGUI to the center.

		while (!resumeInteracted)
		{
			pause(250);
		}
		remove(resume);
		resume = null;// Resume is not needed after this point. For saving RAM, equalize it to
						// null.
	}

	/**
	 * Loads previous game state from save file.
	 */
	private void load()
	{
		BufferedReader rd;
		try
		{
			if (isFileEmpty("save.txt"))
			{
				return;
			}

			rd = new BufferedReader(new FileReader("save.txt"));

			String line = rd.readLine();

			darkMode = Boolean.parseBoolean(line);// Loads dark mode
			score = Integer.parseInt(rd.readLine());// Loads score
			map.setScrollSpeed(Double.parseDouble(rd.readLine()));
			rex.setLocation(rex.getX(), Double.parseDouble(rd.readLine()));// Loads y coordinate of
																			// T-rex
			if (rex.getY() != rex.getInitY())// If rex's y coordinate is different from the default
												// one, it should fall
			{
				rex.setState("f");
			}
			line = rd.readLine();

			double x = 0, y = 0;

			// -----------------COLLIDABLES-----------------
			while (true)
			{
				line = rd.readLine();
				if (line.equals("Clouds:")) break;

				boolean isBigCactus = false;
				boolean isBird = false;

				switch (line)// This line determines whether a big/small cactus or a Bird should be
								// added.
				{
				case "true":
					isBigCactus = true;
					break;
				case "Bird":
					isBird = true;
				}
				x = Double.parseDouble(rd.readLine());
				y = Double.parseDouble(rd.readLine());

				if (isBird)
				{
					map.addBird(x, y);
				}
				else
				{
					map.addCactus(isBigCactus, x, y);
				}
			}

			// -----------------CLOUDS-----------------
			while (true)
			{
				line = rd.readLine();
				if (line.equals("Plane:") || line == null)
				{
					break;
				}

				x = Double.parseDouble(line);
				y = Double.parseDouble(rd.readLine());
				map.addCloud(x, y);
			}

			// -----------------PLANE-----------------
			x = Double.parseDouble(rd.readLine());
			y = Double.parseDouble(rd.readLine());
			map.addPlane(x, y);

		}
		catch (FileNotFoundException e1)
		{
			System.out.println("Save file was not found.");
		}
		catch (IOException e)
		{
			System.err.println("There was a problem in saving process.");
		}
	}

	/**
	 * Restarts the game by resetting some variables.
	 */
	private void restartGame()
	{
		if (gOver == null) return;

		score = 0;
		deadCount = 0;
		darkCount = 0;
		darkMode = false;
		map.switchImages();
		Trex.setDead(false);
		map.setStop(false);
		rex.setLocation(rex.getInitX(), rex.getInitY());
		rex.setImage("run.gif");
		remove(gOver);
		map.removeCollidables();
	}

	/**
	 * Initializes all sound files.
	 */
	private void loadSounds()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("./data/coinSound.wav").getAbsoluteFile());
			coinSound = AudioSystem.getClip();
			coinSound.open(audioInputStream);

			audioInputStream = AudioSystem
					.getAudioInputStream(new File("./data/SuccessSound.wav").getAbsoluteFile());
			successSound = AudioSystem.getClip();
			successSound.open(audioInputStream);

			audioInputStream = AudioSystem
					.getAudioInputStream(new File("./data/scoreup.wav").getAbsoluteFile());
			scoreUpSound = AudioSystem.getClip();
			scoreUpSound.open(audioInputStream);

			audioInputStream.close();
		}
		catch (Exception e)
		{
			System.out.println("Couldn't load sound files");
		}
	}

	// ------------------MOUSE & KEY EVENTS------------------
	/**
	 * Detects mouse clicks and enables the user to interact with GameOverGUI and
	 * ResumeGUI.
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// -------------ResumeGUI-------------
		if (resume != null && getElementAt(e.getX(), e.getY()) != null)
		{
			GPoint point = resume.getLocalPoint(new GPoint(e.getX(), e.getY()));
			resumeInteracted = resume.getBtn_yes().contains(point)
					|| resume.getBtn_no().contains(point);

			if (resume.getBtn_yes().contains(point))
			{
				shouldResume = true;
			}
		}

		// -------------GameOverGUI-------------
		if (gOver == null || getElementAt(e.getX(), e.getY()) == null) return;

		GPoint point = gOver.getLocalPoint(new GPoint(e.getX(), e.getY()));
		if (gOver.getBtn_restart().contains(point))
		{
			restartGame();
		}
	}

	/**
	 * Detects key press to change TRex state.
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (rex == null || map == null)
		{
			return;
		}

		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
		case KeyEvent.VK_SPACE:
			if (rex.getState() != "f" && !map.isStopped())
			{
				rex.setState("j");
			}
			break;
		case KeyEvent.VK_DOWN:
			rex.setState("d");
		default:
			break;
		}
	}

	/**
	 * Detects key release to change TRex state or restart the game.
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (rex == null || map == null)
		{
			return;
		}

		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
		case KeyEvent.VK_SPACE:
			if (deadCount > 1)
			{
				restartGame();
			}
			else if (!Trex.isDead())
			{
				rex.setState("f");
			}
			break;
		case KeyEvent.VK_DOWN:
			rex.setState("");
		default:
			break;
		}
	}
	// -----------------------------------------------------

	// ------------------GETTERS & SETTERS------------------
	/**
	 * Returns the current score.
	 * 
	 * @return score
	 */
	public static int getScore()
	{
		return score;
	}

	/**
	 * Returns true if the dark mode is on.
	 * 
	 * @return darkMode
	 */
	public static boolean isDarkMode()
	{
		return darkMode;
	}

	// -----------------------------------------------------

	public static void main(String[] args)
	{
		new Game().start();
	}
}
