package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/**
 * A MouseMotionListener that detects mouse movement and moves the custom cursor around.
 * @author Terlan
 *
 */
public class MenuMouseMotionListener implements MouseMotionListener
{
	/**
	 * Moves the cursor image to the location of mouse when detects mouse movement.
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		MenuCanvas.getCursorImg().setLocation(e.getX(),e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		
	}
}
