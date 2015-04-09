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

import java.util.Random;

import fr.utbm.info.vi51.framework.agent.BehaviourOutput;
import fr.utbm.info.vi51.framework.environment.DynamicType;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.general.behavior.WanderBehaviour;

/**
 * Steering Wander Behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SteeringWanderBehaviour implements WanderBehaviour {

	private final Random random = new Random();
	private final SteeringFaceBehaviour faceBehaviour;
	private final float circleDistance;
	private final float circleRadius;
	private final float maxRotation;
	private float rotation;

	/**
	 * @param circleDistance is the distance between the entity and the circle center.
	 * @param circleRadius is the radius of the circle.
	 * @param maxRotation is the maximal rotation of the entity.
	 * @param stopRadius is the angle between the current direction and the target direction
	 * under which the rotation for alignment is ignored.
	 * @param decelerateRadius is the angle between the current direction and the target direction
	 * under which the rotation is going slower.
	 */
	public SteeringWanderBehaviour(float circleDistance, float circleRadius, float maxRotation, float stopRadius, float decelerateRadius) {
		this.circleDistance = circleDistance;
		this.circleRadius = circleRadius;
		this.maxRotation = maxRotation;
		this.rotation = 0f;
		this.faceBehaviour = new SteeringFaceBehaviour(stopRadius, decelerateRadius);
	}

	/**
	 * {@inheritDoc}
	 */
	public BehaviourOutput runWander(Point2f position, Vector2f orientation, float linearSpeed, float maxLinearAcc, float angularSpeed, float maxAngularAcc) {
		// Calculate the circle center
		Vector2f circleCenter = orientation.toColinearVector(this.circleDistance);
		//
		// Calculate the displacement force
		Vector2f displacement = circleCenter.toColinearVector(this.circleRadius);
		displacement.turn(this.rotation);
		//
		// Change angle just a bit, so it
		// won't have the same value in the
		// next frame.
		//this.rotation += (Math.random() * 2f - 1f) * this.maxRotation;
		this.rotation += (float) this.random.nextGaussian() * this.maxRotation;
		//
		// Finally calculate the wander force
		Vector2f wanderForce = circleCenter.operator_plus(displacement);
		Point2f target = position.operator_plus(wanderForce);

		BehaviourOutput output = this.faceBehaviour.runFace(position, orientation, angularSpeed, maxAngularAcc, target);
		
		if (output == null || output.getType() != DynamicType.STEERING) {
			output = new BehaviourOutput(DynamicType.STEERING);
		}

		Vector2f linearMotion = orientation.toColinearVector(maxLinearAcc);
		output.setLinear(linearMotion);

		return output;
	}

}