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
package fr.utbm.info.vi51.general.behavior.steering;

import fr.utbm.info.vi51.framework.agent.BehaviourOutput;
import fr.utbm.info.vi51.framework.environment.DynamicType;
import fr.utbm.info.vi51.framework.math.MathUtil;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.general.behavior.AlignBehaviour;

/**
 * Steering Align Behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SteeringAlignBehaviour implements AlignBehaviour {

	private static final float TIME_TO_REACH_DIRECTION = 2f;
	
	private final float stopRadius; 
	private final float decelerateRadius; 

	/**
	 * @param stopRadius is the angle between the current direction and the target direction
	 * under which the rotation for alignment is ignored.
	 * @param decelerateRadius is the angle between the current direction and the target direction
	 * under which the rotation is going slower.
	 */
	public SteeringAlignBehaviour(float stopRadius, float decelerateRadius) {
		assert (stopRadius < decelerateRadius);
		this.stopRadius = stopRadius;
		this.decelerateRadius = decelerateRadius;
	}

	/**
	 * {@inheritDoc}
	 */
	public BehaviourOutput runAlign(Vector2f orientation, float angularSpeed, float maxAngularAcc, Vector2f target) {
		assert (maxAngularAcc >= 0f);
		float rotation = orientation.signedAngle(target);
		float rotationSize = Math.abs(rotation);

		float acceleration = 0f;

		if (rotationSize >= this.stopRadius) {
			float speed = Math.abs(angularSpeed);
			
			float directionToTarget = Math.signum(rotation);

			if (rotationSize > this.decelerateRadius || speed == 0f) {
				// Unit is: [r/s^2] = [r/s^2] * ID 
				acceleration = maxAngularAcc * directionToTarget;
			} else {
				// Compute the acceleration - Units: [r/s] = ID * [r] / [s^2] 
				acceleration = rotation / (TIME_TO_REACH_DIRECTION * TIME_TO_REACH_DIRECTION);
			}
		} else {
			acceleration = -angularSpeed / (TIME_TO_REACH_DIRECTION * TIME_TO_REACH_DIRECTION);
		}

		BehaviourOutput output = new BehaviourOutput(DynamicType.STEERING);
		output.setAngular(MathUtil.clamp(acceleration, -maxAngularAcc, maxAngularAcc));
		return output;
	}

}