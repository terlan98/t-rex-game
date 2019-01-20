package util;

/**
 * This interface contains constants that are important for the functionality of
 * the game.
 * 
 * @author Terlan
 *
 */
public interface Constants
{
	// ------------GAME------------
	/** Pause time before checking whether the score should be increased or not. */
	short SCORE_INCREASE_TIME = 120;
	/** After each SPEED_INCREASE_SCORE, the map speed increases. */
	short SPEED_INCREASE_SCORE = 100;
	/** After each NIGHT_SCORE dark mode will be turned on */
	short NIGHT_SCORE = 700;
	/** Dark mode will remain on until score increases by this amount */
	short DARK_TIME = 120;
	/** Scale factor of ground images. */
	double SCALE_FACTOR = 0.000616;
	/**
	 * At this color value, the map will switch all images with bright ones. The
	 * purpose is to prevent images from changing immediately after the dark mode is
	 * on.
	 */
	short BRIGHT_SWITCH_COLOR = 234;
	/**
	 * This value shows how many milliseconds should the game continuously pause
	 * when TRex is dead. If needed, this value can be increased to make a slight
	 * performance improvement.
	 */
	short PAUSE_TIME = 250;

	// ------------MAP------------
	/**Initial scroll speed*/
	double INITIAL_SCROLL = -3.5;
	/** Indicates the amount by which the scrollSpeed will be increased.*/
	double SCROLL_INCREASE = -0.2;
	/**Minimal distance between clouds*/
	short CLOUD_DISTANCE = 7;
	/** Minimal distance between Collidables*/
	short COLLIDABLES_DISTANCE = 4;
	/** Y coordinate of cactus relative to TRex*/
	double CACTUS_Y = 48;
	/**For calculation of the y-coordinate of the plane*/
	double PLANE_Y_FACTOR = 0.65;
	
	// ------------TREX------------
	/**Gravity that will affect TRex*/
	short GRAVITY = 2;
	/**Jump speed of TRex*/
	short J_SPEED = -21;
	/**Jump height of TRex*/
	short J_HEIGHT = 200;
	/**Scale factor of TRex*/
	double TREX_SCALE_FACTOR = 0.0005;

	// ------------CLOUD------------
	/**Scale factor of clouds*/
	double CLOUD_SCALE = 0.0006;
	
	// ------------PLANE------------
	/**Scale factor of the body part of the plane*/
	double PLANE_BODY_SCALE = 0.0009;
	/**Scale factor of the propeller of the plane*/
	double PLANE_PROPELLER_SCALE = 0.26;
	/**Scale factor of the banner type 1*/
	double BANNER_TYPE_1_SCALE = 0.0003;
	/**Scale factor of the banner type 2*/
	double BANNER_TYPE_2_SCALE = 0.0003;
	/**Scale factor of the banner type 3*/
	double BANNER_TYPE_3_SCALE = 0.0002;
}
