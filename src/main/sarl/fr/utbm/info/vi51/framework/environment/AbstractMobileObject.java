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
package fr.utbm.info.vi51.framework.environment;

import java.util.UUID;

import fr.utbm.info.vi51.framework.math.MathUtil;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.time.TimeManager;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Abstract implementation of an object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractMobileObject extends AbstractSituatedObject implements MobileObject {

	private static final long serialVersionUID = -2670464828720893140L;
	
	private final float maxLinearSpeed;
	private final float maxLinearAcceleration;
	private final float maxAngularSpeed;
	private final float maxAngularAcceleration;
	
	private float angle = 0;
	private float currentAngularSpeed = 0;
	private Vector2f linearMove = new Vector2f();

	/**
	 * @param id the identifier of the object.
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 * @param maxLinearSpeed is the maximal linear speed.
	 * @param maxLinearAcceleration is the maximal linear acceleration.
	 * @param maxAngularSpeed is the maximal angular speed.
	 * @param maxAngularAcceleration is the maximal angular acceleration.
	 */
	public AbstractMobileObject(UUID id, Shape2f<?> shape, float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed, float maxAngularAcceleration) {
		super(id, shape);
		this.maxLinearSpeed = Math.abs(maxLinearSpeed);
		this.maxLinearAcceleration = Math.abs(maxLinearAcceleration);
		this.maxAngularAcceleration = Math.abs(maxAngularAcceleration);
		this.maxAngularSpeed = Math.abs(maxAngularSpeed);
	}
	
	@Override
	public AbstractMobileObject clone() {
		AbstractMobileObject clone = (AbstractMobileObject) super.clone();
		clone.linearMove = this.linearMove.clone();
		return clone;
	}

	/** {@inheritDoc}
	 */
	public float getAngle() {
		return this.angle;
	}
	
	/** {@inheritDoc}
	 */
	public Vector2f getDirection() {
		return Vector2f.toOrientationVector(this.angle);
	}

	/** Set the orientation of the object.
	 * 
	 * @param angle
	 */
	protected void setAngle(float angle) {
		this.angle = angle;
		this.currentAngularSpeed = 0;
	}

	/** Set the direction of the object.
	 * 
	 * @param dx
	 * @param dy
	 */
	protected void setDirection(float dx, float dy) {
		this.angle = new Vector2f(dx, dy).getOrientationAngle();
		this.currentAngularSpeed = 0;
	}

	/** {@inheritDoc}
	 */
	public float getMaxLinearSpeed() {
		return this.maxLinearSpeed;
	}

	/** {@inheritDoc}
	 */
	public float getMaxAngularSpeed() {
		return this.maxAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	public float getMaxLinearAcceleration() {
		return this.maxLinearAcceleration;
	}

	/** {@inheritDoc}
	 */
	public float getMaxAngularAcceleration() {
		return this.maxAngularAcceleration;
	}
	
	/** {@inheritDoc}
	 */
	public float getCurrentAngularSpeed() {
		return this.currentAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	public float getCurrentLinearSpeed() {
		return this.linearMove.length();
	}

	/** {@inheritDoc}
	 */
	public Vector2f getCurrentLinearMotion() {
		return new Vector2f(this.linearMove);
	}

	/** Rotate the object.
	 * 
	 * @param rotation is the real instant motion. 
	 * @param simulationDuration is the time during which the motion is applied.
	 */
	protected void rotate(float rotation, float simulationDuration) {
		assert (!Double.isNaN(rotation)) : LocalizedString.getString(getClass(), "INVALID_ROTATION", getName());
		assert (!Double.isNaN(simulationDuration)) : LocalizedString.getString(getClass(), "INVALID_DURATION", getName());
		this.angle += rotation;
		this.currentAngularSpeed = rotation / simulationDuration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setPosition(float x, float y) {
		super.setPosition(x, y);
		this.linearMove.set(0,0);
	}

	/** Move the situated object.
	 * 
	 * @param dx is the real instant motion. 
	 * @param dy is the real instant motion.
	 * @param simulationDuration is the time during which the motion is applied.
	 * @param worldWidth is the width of the world.
	 * @param worldHeight is the height of the world.
	 * @return the real motion.
	 */
	protected Vector2f move(float dx, float dy, float simulationDuration, float worldWidth, float worldHeight) {
		// Ensure that the motion in inside the bounds of the world.
		Vector2f r = new Vector2f(dx, dy);
		Shape2f<?> currentShape = getShape();
		Shape2f<?> targetShape = currentShape.translate(r);
		Rectangle2f targetBounds = targetShape.getBounds();

		if (targetBounds.getLower().getX() < 0) {
			float exceedingAmount = - targetBounds.getLower().getX();
			r.addX(exceedingAmount);
		} else if (targetBounds.getUpper().getX() > worldWidth) {
			float exceedingAmount = targetBounds.getUpper().getX() - worldWidth;
			r.subX(exceedingAmount);
		}
		
		if (targetBounds.getLower().getY() < 0) {
			float exceedingAmount = - targetBounds.getLower().getY();
			r.addY(exceedingAmount);
		} else if (targetBounds.getUpper().getY() > worldHeight) {
			float exceedingAmount = targetBounds.getUpper().getY() - worldHeight;
			r.subY(exceedingAmount);
		}

		// Update the position
		addPosition(r.getX(), r.getY());
		
		// Update dynamic properties
		if (simulationDuration>0) {
			this.linearMove.set(r.getX(), r.getY());
			float distance = this.linearMove.length();
			if (distance>0) {
				this.linearMove.normalize();
				this.linearMove.scale(distance/simulationDuration);
			}
		}
		else {
			this.linearMove.set(0,0);
		}

		return r;
	}

	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion, expressed with acceleration.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected Vector2f computeSteeringTranslation(Vector2f move, TimeManager clock) {
		float length = move.length();

		Vector2f v;
		
		if (length != 0f) {
			// Clamp acceleration
			float acceleration = MathUtil.clamp(
					(move.dot(this.linearMove) < 0f) ? -length : length, 
					-getMaxLinearAcceleration(), 
					getMaxLinearAcceleration());
			
			// Apply Newton law, first part (from acceleration to speed)
			acceleration = Math.abs(acceleration) / length;
			v = move.operator_multiply(acceleration);
			
			v.scale(.5f * clock.getLastStepDuration());
			v = this.linearMove.operator_plus(v);
		}
		else {
			v = this.linearMove.clone();
		}
		
		// v is a speed - unit: [m/s]
		
		length = (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY());
		if (length != 0f) {
			// Clamp the speed
			float speed = MathUtil.clamp(
					(v.dot(this.linearMove) < 0f) ? -length : length, 
					0f, 
					getMaxLinearSpeed());

			// Compute the Newton law, part 2 (from speed to distance)
			float factor = clock.getLastStepDuration() * Math.abs(speed) / length;
		
			return v.operator_multiply(factor);
		}
		
		return new Vector2f();
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion, expressed with speed.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected Vector2f computeKinematicTranslation(Vector2f move, TimeManager clock) {
		float speed = move.length();
		if (speed != 0f) {
			// Apply Newton-Euler-1 law
			float factor = clock.getLastStepDuration() * MathUtil.clamp(speed, 0, getMaxLinearSpeed()) / speed;
			return move.operator_multiply(factor);
		}
		return new Vector2f();
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion with speed.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected float computeKinematicRotation(float move, TimeManager clock) {
		float speed = Math.abs(move);
		if (speed != 0f) {
			// Apply Newton-Euler-1 law
			float factor = clock.getLastStepDuration() * MathUtil.clamp(speed, 0, getMaxAngularSpeed()) / speed;
			return move * factor;
		}
		return 0f;
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected float computeSteeringRotation(float move, TimeManager clock) {
		float v;
		
		if (move != 0f) {
			// Clamp acceleration
			float acceleration = MathUtil.clamp(
					move, 
					-getMaxAngularAcceleration(), 
					getMaxAngularAcceleration());
			
			// Apply Newton law, first part (from acceleration to speed)
			acceleration = Math.abs(acceleration) / Math.abs(move);
			v = move * acceleration;
			v *= .5f * clock.getLastStepDuration();
			v += this.currentAngularSpeed;
		}
		else {
			v = this.currentAngularSpeed;
		}
		
		// v is a speed - unit: [m/s]
		
		if (v != 0f) {
			// Clamp the speed
			float speed = MathUtil.clamp(
					v, 
					-getMaxAngularSpeed(), 
					getMaxAngularSpeed());

			// Compute the Newton law, part 2 (from speed to distance)
			float factor = clock.getLastStepDuration() * Math.abs(speed) / Math.abs(v);
		
			return v * factor;
		}
		
		return 0f;
	}

}