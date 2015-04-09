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
package fr.utbm.info.vi51.framework.math;

/** 2D Vector with 2 floating-point values.
 *
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Vector2f extends Tuple2f<Vector2f> {

	private static final long serialVersionUID = -2062941509400877679L;

	/**
	 */
	public Vector2f() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2f(Tuple2f<?> tuple) {
		this(tuple, false);
	}

	/**
	 * @param tuple is the tuple to copy.
	 * @param normalize
	 */
	public Vector2f(Tuple2f<?> tuple, boolean normalize) {
		super(tuple);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2f(int[] tuple) {
		this(tuple, false);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2f(float[] tuple) {
		this(tuple, false);
	}

	/**
	 * @param tuple is the tuple to copy.
	 * @param normalize
	 */
	public Vector2f(int[] tuple, boolean normalize) {
		super(tuple);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param tuple is the tuple to copy.
	 * @param normalize
	 */
	public Vector2f(float[] tuple, boolean normalize) {
		super(tuple);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2f(int x, int y) {
		this(x,y,false);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2f(float x, float y) {
		this(x,y,false);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2f(double x, double y) {
		this((float)x,(float)y,false);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2f(long x, long y) {
		this(x,y,false);
	}

	/**
	 * @param x
	 * @param y
	 * @param normalize
	 */
	public Vector2f(int x, int y, boolean normalize) {
		super(x,y);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param normalize
	 */
	public Vector2f(float x, float y, boolean normalize) {
		super(x,y);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param normalize
	 */
	public Vector2f(double x, double y, boolean normalize) {
		super((float)x,(float)y);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param normalize
	 */
	public Vector2f(long x, long y, boolean normalize) {
		super(x,y);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable _) {
				//
			}
		}
	}

	/**
	 * Sets the value of this tuple to the sum of tuples t1 and t2.
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void add(Vector2f t1, Vector2f t2) {
		this.x = t1.getX() + t2.getX();
		this.y = t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the sum of itself and t1.
	 * @param t1 the other tuple
	 */
	public void add(Vector2f t1) {
		this.x += t1.getX();
		this.y += t1.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(int s, Vector2f t1, Vector2f t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(float s, Vector2f t1, Vector2f t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of itself and then adds tuple t1 (this = s*this + t1).
	 * @param s the scalar value
	 * @param t1 the tuple to be added
	 */
	public void scaleAdd(int s, Vector2f t1) {
		this.x = s * this.x + t1.getX();
		this.y = s * this.y + t1.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of itself and then adds tuple t1 (this = s*this + t1).
	 * @param s the scalar value
	 * @param t1 the tuple to be added
	 */
	public void scaleAdd(float s, Vector2f t1) {
		this.x = s * this.x + t1.getX();
		this.y = s * this.y + t1.getY();
	}


	/**
	 * Sets the value of this tuple to the difference
	 * of tuples t1 and t2 (this = t1 - t2).
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void sub(Vector2f t1, Vector2f t2) {
		this.x = t1.getX() - t2.getX();
		this.y = t1.getY() - t2.getY();
	}

	/**
	 * Sets the value of this tuple to the difference
	 * of tuples t1 and t2 (this = t1 - t2).
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void sub(Point2f t1, Point2f t2) {
		this.x = t1.getX() - t2.getX();
		this.y = t1.getY() - t2.getY();
	}

	/**
	 * Sets the value of this tuple to the difference
	 * of itself and t1 (this = this - t1).
	 * @param t1 the other tuple
	 */
	public void sub(Vector2f t1) {
		this.x -= t1.getX();
		this.y -= t1.getY();
	}

	/**
	 * Computes the dot product of the this vector and vector v1.
	 * @param v1 the other vector
	 * @return the dot product.
	 */
	public float dot(Vector2f v1) {
		return (this.x*v1.getX() + this.y*v1.getY());
	}

	/** Change the coordinates of this vector to make it a perpendicular
	 * vector to the original coordinates.
	 */
	public void perpendicularize() {
		// Based on the cross product in 3D of (vx,vy,0)x(0,0,1), right-handed
		//set(getY(), -getX());
		// Based on the cross product in 3D of (vx,vy,0)x(0,0,1), left-handed
		set(-getY(), getX());
	}

	/**  
	 * Returns the length of this vector.
	 * @return the length of this vector
	 */  
	public float length() {
		return (float) Math.sqrt(this.x*this.x + this.y*this.y);
	}

	/**  
	 * Returns the squared length of this vector.
	 * @return the squared length of this vector
	 */  
	public float lengthSquared() {
		return (this.x*this.x + this.y*this.y);
	}
	
	/**  
	 * Set the length of this vector.
	 * @param length - the length of this vector
	 */  
	public void setLength(float length) {
		if (length >= 0f) {
			float norm = this.x*this.x + this.y*this.y;
			if (norm != 0f) {
				norm = (float)Math.sqrt(norm);
				norm = length / norm;
				this.x *= norm;
				this.y *= norm;
			} else {
				this.x = length;
				this.y = 0f;
			}
		} else {
			this.x = this.y = 0f;
		}
	}

	/** Create a colinear vector with the given length.
	 *
	 * @param length - the length.
	 * @return the colinear vector.
	 */
	public Vector2f toColinearVector(float length) {
		float x, y;
		if (length >= 0f) {
			float norm = this.x*this.x + this.y*this.y;
			if (norm != 0f) {
				norm = (float)Math.sqrt(norm);
				norm = length / norm;
				x = this.x * norm;
				y = this.y * norm;
			} else {
				x = length;
				y = 0f;
			}
		} else {
			x = y = 0f;
		}
		return new Vector2f(x, y);
	}

	/**
	 * Sets the value of this vector to the normalization of vector v1.
	 * @param v1 the un-normalized vector
	 */  
	public void normalize(Vector2f v1) {
		float norm = 1f / v1.length();
		this.x = (int)(v1.getX()*norm);
		this.y = (int)(v1.getY()*norm);
	}

	/**
	 * Normalizes this vector in place.
	 */  
	public void normalize() {
		float norm;
		norm = (float)(1./Math.sqrt(this.x*this.x + this.y*this.y));
		this.x *= norm;
		this.y *= norm;
	}


	/**
	 *   Returns the angle in radians between this vector and the vector
	 *   parameter; the return value is constrained to the range [0,PI].
	 *   @param v1    the other vector
	 *   @return   the angle in radians in the range [0,PI]
	 */
	public float angle(Vector2f v1) {
		double vDot = dot(v1) / ( length()*v1.length() );
		if( vDot < -1.) vDot = -1.;
		if( vDot >  1.) vDot =  1.;
		return((float) (Math.acos( vDot )));
	}

	/** Compute a signed angle between this vector and the given vector.
	 * <p>
	 * The signed angle between this vector and {@code v}
	 * is the rotation angle to apply to this vector
	 * to be colinear to {@code v} and pointing the
	 * same demi-plane. It means that the angle replied
	 * by this function is be negative if the rotation
	 * to apply is clockwise, and positive if
	 * the rotation is counterclockwise.
	 * <p>
	 * The value replied by {@link #angle(Vector2D)}
	 * is the absolute value of the vlaue replied by this
	 * function. 
	 * 
	 * @param v is the vector to reach.
	 * @return the rotation angle to turn this vector to reach
	 * {@code v}.
	 */
	public float signedAngle(Vector2f v) {
		return MathUtil.signedAngle(getX(), getY(), v.getX(), v.getY());
	}

	/** Turn this vector about the given rotation angle.
	 * 
	 * @param angle is the rotation angle in radians.
	 */
	public void turn(float angle) {
		float sin = (float)Math.sin(angle);
		float cos = (float)Math.cos(angle);
		float x =  cos * getX() + -sin * getY(); // -sin is on this line according to the coordinate system
		float y =  sin * getX() + cos * getY();
		set(x,y);
	}

	/** Replies the orientation angle on a trigonometric circle
	 * that is corresponding to the given direction of this vector.
	 * 
	 * @return the angle on a trigonometric circle that is corresponding
	 * to the given orientation vector.
	 */
	public float getOrientationAngle() {
		float angle = (float)Math.acos(getX());
		if (getY()<0f) angle = -angle;
		return MathUtil.clampRadian(angle);
	}

	/** Set this vector with the direction of the orientation angle on a trigonometric circle.
	 * 
	 * @param angle the angle on a trigonometric circle.
	 */
	public void setOrientationAngle(float angle) {
		set(
				(float) Math.cos(angle),
				(float) Math.sin(angle));
	}

	@Override
	public Vector2f clone() {
		return (Vector2f) super.clone();
	}
	
	/** Replies the orientation vector, which is corresponding
	 * to the given angle on a trigonometric circle.
	 * 
	 * @param angle is the angle in radians to translate.
	 * @return the orientation vector which is corresponding to the given angle.
	 */
	public static Vector2f toOrientationVector(float angle) {
		return new Vector2f(
				(float)Math.cos(angle),
				(float)Math.sin(angle));
	}

	/** Sum of vectors: r = this + v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Vector2f, Vector2f)
	 */
	public Vector2f operator_plus(Vector2f v) {
		Vector2f r = new Vector2f();
		r.add(this, v);
		return r;
	}

	/** Add vector to a point: r = this + p.
	 * 
	 * @param p the point
	 * @return the result.
	 * @see Point2f#add(Point2f, Vector2f)
	 */
	public Point2f operator_plus(Point2f p) {
		Point2f r = new Point2f();
		r.add(p, this);
		return r;
	}

	/** Sum of vectors: this += v.
	 * It is equivalent to {@code this.add(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Vector2f)
	 */
	public void operator_add(Vector2f v) {
		add(v);
	}

	/** Subtract of vectors: r = this - v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Vector2f, Vector2f)
	 */
	public Vector2f operator_minus(Vector2f v) {
		Vector2f r = new Vector2f();
		r.sub(this, v);
		return r;
	}

	/** Subtract of vectors: this -= v.
	 * It is equivalent to {@code this.sub(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Vector2f)
	 */
	public void operator_remove(Vector2f v) {
		sub(v);
	}

	/** Negation: r = -this.
	 * It is equivalent to {@code this.negate(r)}
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #negate(Vector2f)
	 */
	public Vector2f operator_minus() {
		Vector2f r = new Vector2f();
		negate(r);
		return r;
	}

	/** Dot product: r = this.v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #dot(Vector2f)
	 */
	public float operator_multiply(Vector2f v) {
		return dot(v);
	}

	/** Scale a vector: r = this * f.
	 * 
	 * @param v the vector
	 * @return the scaled vector.
	 */
	public Vector2f operator_multiply(float f) {
		Vector2f r = new Vector2f(this);
		r.scale(f);
		return r;
	}

	/** Scale a vector: r = this / f.
	 * 
	 * @param v the vector
	 * @return the scaled vector.
	 */
	public Vector2f operator_divide(float f) {
		Vector2f r = new Vector2f(this);
		r.scale(1f/f);
		return r;
	}

	/** Replies if the vectors are equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	public boolean operator_equals(Vector2f v) {
		return equals(v);
	}

	/** Replies if the vectors are not equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	public boolean operator_notEquals(Vector2f v) {
		return !equals(v);
	}

	/** Replies if the vectors are equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	public boolean operator_tripleEquals(Vector2f v) {
		return equals(v);
	}

	/** Replies if the vectors are not equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	public boolean operator_tripleNotEquals(Vector2f v) {
		return !equals(v);
	}

	/** Replies if the absolute angle between this and v
	 * 
	 * @param v the vector.
	 * @return the signed angle.
	 * @see #angle(Vector2f)
	 */
	public float operator_upTo(Vector2f v) {
		return angle(v);
	}

	/** Replies if the signed angle from this to v
	 * 
	 * @param v the vector.
	 * @return the signed angle.
	 * @see #signedAngle(Vector2f)
	 */
	public float operator_greaterThanDoubleDot(Vector2f v) {
		return signedAngle(v);
	}

	/** Replies if the signed angle from v to this
	 * 
	 * @param v the vector.
	 * @return the signed angle.
	 * @see #signedAngle(Vector2f)
	 */
	public float operator_doubleDotLessThan(Vector2f v) {
		return - signedAngle(v);
	}

	/** If the tuple is nul then b else a.
	 * 
	 * @param v the vector.
	 * @return the vector.
	 * @see #epsilonNul(float)
	 * @see MathUtil#EPSILON
	 */
	public Vector2f operator_elvis(Vector2f v) {
		if (epsilonNul(MathUtil.EPSILON)) {
			return v;
		}
		return this;
	}
	
}