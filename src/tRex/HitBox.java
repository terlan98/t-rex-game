package tRex;

import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class HitBox extends GCompound
{
	/**An ArrayList containing hitPoints*/
	private ArrayList<GRect> hitPoints = new ArrayList<GRect>();

	/**
	 * Initializes HitBox data according to the type of the object.
	 * @param object - GObject for which a new HitBox should be created
	 */
	public HitBox(GObject object)
	{
		if (object instanceof Trex)// Trex
		{
			if (((Trex) object).getState() != "d")// Normal
			{
				GRect point = new GRect(object.getWidth() * 0.53, object.getHeight() * 0.08, object.getWidth() * 0.4,
						object.getHeight() * 0.18);
				hitPoints.add(point);// Head

				point = new GRect(object.getWidth() * 0.07, object.getHeight() * 0.325, object.getWidth() * 0.75,
						object.getHeight() * 0.18);
				hitPoints.add(point);// From tail to mouth

				point = new GRect(object.getWidth() * 0.519, object.getHeight() * 0.75, object.getWidth() * 0.07,
						object.getHeight() * 0.15);
				hitPoints.add(point);// Right leg

				point = new GRect(object.getWidth() * 0.319, object.getHeight() * 0.75, object.getWidth() * 0.07,
						object.getHeight() * 0.15);
				hitPoints.add(point);// Left leg

				point = new GRect(object.getWidth() * 0.188, object.getHeight() * 0.65, object.getWidth() * 0.05,
						object.getHeight() * 0.12);
				hitPoints.add(point);// Tail

				point = new GRect(object.getWidth() * 0.07, object.getHeight() * 0.55, object.getWidth() * 0.05,
						object.getHeight() * 0.12);
				hitPoints.add(point);// Tail
			}
			else// Duck
			{
				GRect point = new GRect(object.getWidth() * 0.897, object.getHeight() * 0.15, object.getWidth() * 0.05,
						object.getHeight() * 0.2);
				hitPoints.add(point);// Nose

				point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.13, object.getWidth() * 0.7,
						object.getHeight() * 0.1);
				hitPoints.add(point);// Spine
			}
		}
		else if (object instanceof Cactus)//Cactus
		{
			if (!((Cactus) object).isBig())// Small cactus
			{
				switch (((Cactus) object).getOrder())//Switch for different types of cactuses
				{
				case 1:
					GRect point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.2, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				case 2:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.2, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				case 3:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.12, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				case 4:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.25, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				case 5:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.37);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.25, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				case 6:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.12, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.5);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.8, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Right arm

					break;
				}
			}
			else// Big cactus
			{
				switch (((Cactus) object).getOrder())//Switch for different types of big cactuses
				{
				case 1:
					GRect point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.3, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.85, object.getHeight() * 0.25, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Right arm
					break;
				case 2:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.3, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.85, object.getHeight() * 0.25, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Right arm
					break;
				case 3:
					point = new GRect(object.getWidth() * 0.09, object.getHeight() * 0.14, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.7);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.85, object.getHeight() * 0.25, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Right arm
					break;
				case 4:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.28, object.getWidth() * 0.05,
							object.getHeight() * 0.23);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.42, object.getHeight() * 0.03, object.getWidth() * 0.18,
							object.getHeight() * 0.7);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.85, object.getHeight() * 0.27, object.getWidth() * 0.05,
							object.getHeight() * 0.25);
					hitPoints.add(point);// Right arm
					break;
				case 5:
					point = new GRect(object.getWidth() * 0.08, object.getHeight() * 0.36, object.getWidth() * 0.04,
							object.getHeight() * 0.23);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.28, object.getHeight() * 0.085, object.getWidth() * 0.1,
							object.getHeight() * 0.3);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.52, object.getHeight() * 0.18, object.getWidth() * 0.04,
							object.getHeight() * 0.17);
					hitPoints.add(point);// Right arm

					point = new GRect(object.getWidth() * 0.71, object.getHeight() * 0.43, object.getWidth() * 0.04,
							object.getHeight() * 0.17);
					hitPoints.add(point);// Small arm 1

					point = new GRect(object.getWidth() * 0.87, object.getHeight() * 0.54, object.getWidth() * 0.043,
							object.getHeight() * 0.13);
					hitPoints.add(point);// Small arm 2
					break;
				case 6:
					point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.15, object.getWidth() * 0.05,
							object.getHeight() * 0.27);
					hitPoints.add(point);// Left arm

					point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.03, object.getWidth() * 0.2,
							object.getHeight() * 0.7);
					hitPoints.add(point);// Middle arm

					point = new GRect(object.getWidth() * 0.85, object.getHeight() * 0.27, object.getWidth() * 0.05,
							object.getHeight() * 0.25);
					hitPoints.add(point);// Right arm
					break;
				}
			}
		}
		else if (object instanceof Bird)//Bird
		{

			switch (((Bird) object).getState())//Switch for 2 different states of Bird.
			{
			case 1:
				GRect point = new GRect(object.getWidth() * 0.12, object.getHeight() * 0.26, object.getWidth() * 0.2,
						object.getHeight() * 0.15);
				hitPoints.add(point);// Head

				point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.45, object.getWidth() * 0.53,
						object.getHeight() * 0.19);
				hitPoints.add(point);// Body

				point = new GRect(object.getWidth() * 0.4, object.getHeight() * 0.65, object.getWidth() * 0.1,
						object.getHeight() * 0.2);
				hitPoints.add(point);// Wing
				break;
			case 2:

				point = new GRect(object.getWidth() * 0.17, object.getHeight() * 0.24, object.getWidth() * 0.35,
						object.getHeight() * 0.13);
				hitPoints.add(point);// Head 1

				point = new GRect(object.getWidth() * 0.1, object.getHeight() * 0.34, object.getWidth() * 0.52,
						object.getHeight() * 0.1);
				hitPoints.add(point);// Head 2

				point = new GRect(object.getWidth() * 0.42, object.getHeight() * 0.52, object.getWidth() * 0.52,
						object.getHeight() * 0.12);
				hitPoints.add(point);// Body

				point = new GRect(object.getWidth() * 0.38, object.getHeight() * 0.1, object.getWidth() * 0.07,
						object.getHeight() * 0.22);
				hitPoints.add(point);// Wing
				break;
			}
		}
	}

	/**
	 * Returns hitPoints of the HitBox.
	 * @return hitPoints - hitPoints of the HitBox.
	 */
	public ArrayList<GRect> getHitPoints()
	{
		return hitPoints;
	}
}
