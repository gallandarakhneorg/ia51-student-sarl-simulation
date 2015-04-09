/* 
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.vi51.labwork4.environment;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.AbstractMobileObject;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.MathUtil;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;

/**
 * Situated object representing the mouse position.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public final class MouseTarget extends AbstractMobileObject {

	private static final long serialVersionUID = 4893719355660004451L;

	/**
	 * @param x
	 * @param y
	 */
	public MouseTarget(float x, float y) {
		super(	UUID.randomUUID(),
				new Circle2f(0, 0, 5f),
				Float.MAX_VALUE,
				Float.MAX_VALUE,
				x, y);
		setName("Mouse Target");
		setType("TARGET");
	}
	
	/** Change the position of the mouse target.
	 * 
	 * @param newPosition
	 * @param simulationDuration is the time during which the motion is applied.
	 * @param worldWidth is the width of the world.
	 * @param worldHeight is the height of the world.
	 */
	void setMousePosition(Point2f newPosition, float simulationDuration, float worldWidth, float worldHeight) {
		Point2f oldPosition = getPosition();
		Vector2f oldDirection = getDirection();
		Vector2f motion = newPosition.operator_minus(oldPosition);
		float rotation;
		if (motion.epsilonNul(MathUtil.EPSILON)) {
			rotation = 0f;
		} else {
			rotation = oldDirection.signedAngle(motion);
		}
		move(motion.getX(), motion.getY(), simulationDuration, worldWidth, worldHeight);
		rotate(rotation, simulationDuration);
	}

	@Override
	public String toString() {
		return "TARGET @ " + getPosition();
	}
	
}