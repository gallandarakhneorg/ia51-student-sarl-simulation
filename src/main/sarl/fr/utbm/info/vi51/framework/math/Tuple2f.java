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

import java.io.Serializable;

import com.google.common.base.Objects;

/** 2D tuple with 2 floating-point numbers.
 *
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @param <T> is the implementation type of the tuple.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {

	private static final long serialVersionUID = 6447733811545555778L;
	
	/** x coordinate.
	 */
	protected float x;

	/** y coordinate.
	 */
	protected float y;

	/**
	 */
	public Tuple2f() {
		this.x = this.y = 0;
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Tuple2f(Tuple2f<?> tuple) {
		this.x = tuple.getX();
		this.y = tuple.getY();
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Tuple2f(int[] tuple) {
		this.x = tuple[0];
		this.y = tuple[1];
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Tuple2f(float[] tuple) {
		this.x = tuple[0];
		this.y = tuple[1];
	}

	/**
	 * @param x
	 * @param y
	 */
	public Tuple2f(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Tuple2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Clone this point.
	 * 
	 * @return the clone.
	 */
	@SuppressWarnings("unchecked")
	public T clone() {
		try {
			return (T) super.clone();
		}
		catch(CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	/**
	 *  Sets each component of this tuple to its absolute value.
	 */
	public void absolute() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}

	/**
	 *  Sets each component of the tuple parameter to its absolute
	 *  value and places the modified values into this tuple.
	 *  @param t   the source tuple, which will not be modified
	 */
	public void absolute(T t) {
		t.set(Math.abs(this.x), Math.abs(this.y));
	}

	/**
	 * Sets the value of this tuple to the sum of itself and x and y.
	 * @param x
	 * @param y
	 */
	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Sets the value of this tuple to the sum of itself and x and y.
	 * @param x
	 * @param y
	 */
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Sets the x value of this tuple to the sum of itself and x.
	 * @param x
	 */
	public void addX(int x) {
		this.x += x;
	}

	/**
	 * Sets the x value of this tuple to the sum of itself and x.
	 * @param x
	 */
	public void addX(float x) {
		this.x += x;
	}

	/**
	 * Sets the y value of this tuple to the sum of itself and y.
	 * @param y
	 */
	public void addY(int y) {
		this.y += y;
	}

	/**
	 * Sets the y value of this tuple to the sum of itself and y.
	 * @param y
	 */
	public void addY(float y) {
		this.y += y;
	}

	/**
	 *  Clamps this tuple to the range [low, high].
	 *  @param min  the lowest value in this tuple after clamping
	 *  @param max  the highest value in this tuple after clamping
	 */
	public void clamp(int min, int max) {
		if (this.x < min) this.x = min;
		else if (this.x > max) this.x = max;
		if (this.y < min) this.y = min;
		else if (this.y > max) this.y = max;
	}

	/**
	 *  Clamps this tuple to the range [low, high].
	 *  @param min  the lowest value in this tuple after clamping
	 *  @param max  the highest value in this tuple after clamping
	 */
	public void clamp(float min, float max) {
		if (this.x < min) this.x = min;
		else if (this.x > max) this.x = max;
		if (this.y < min) this.y = min;
		else if (this.y > max) this.y = max;
	}

	/**
	 *  Clamps the minimum value of this tuple to the min parameter.
	 *  @param min   the lowest value in this tuple after clamping
	 */
	public void clampMin(int min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}

	/**
	 *  Clamps the minimum value of this tuple to the min parameter.
	 *  @param min   the lowest value in this tuple after clamping
	 */
	public void clampMin(float min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}

	/**
	 *  Clamps the maximum value of this tuple to the max parameter.
	 *  @param max   the highest value in the tuple after clamping
	 */
	public void clampMax(int max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}

	/**
	 *  Clamps the maximum value of this tuple to the max parameter.
	 *  @param max   the highest value in the tuple after clamping
	 */
	public void clampMax(float max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}

	/**
	 * Copies the values of this tuple into the tuple t.
	 * @param t is the target tuple
	 */
	public void get(T t) {
		t.set(this.x, this.y);
	}

	/**
	 *  Copies the value of the elements of this tuple into the array t.
	 *  @param t the array that will contain the values of the vector
	 */
	public void get(int[] t) {
		t[0] = (int)this.x;
		t[1] = (int)this.y;
	}

	/**
	 *  Copies the value of the elements of this tuple into the array t.
	 *  @param t the array that will contain the values of the vector
	 */
	public void get(float[] t) {
		t[0] = this.x;
		t[1] = this.y;
	}

	/**
	 * Sets the value of this tuple to the negation of tuple t1.
	 * @param t1 the source tuple
	 */
	public void negate(T t1) {
		this.x = -t1.getX();
		this.y = -t1.getY();
	}

	/**
	 * Negates the value of this tuple in place.
	 */
	public void negate() {
		this.x = -this.x;
		this.y = -this.y;
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1.
	 * @param s the scalar value
	 * @param t1 the source tuple
	 */
	public void scale(int s, T t1) {
		this.x = s * t1.getX();
		this.y = s * t1.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1.
	 * @param s the scalar value
	 * @param t1 the source tuple
	 */
	public void scale(float s, T t1) {
		this.x = (s * t1.getX());
		this.y = (s * t1.getY());
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of the scale factor with this.
	 * @param s the scalar value
	 */
	public void scale(int s) {
		this.x = s * this.x;
		this.y = s * this.y;
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of the scale factor with this.
	 * @param s the scalar value
	 */
	public void scale(float s) {
		this.x = (s * this.x);
		this.y = (s * this.y);
	}

	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * @param t1 the tuple to be copied
	 */
	public void set(Tuple2f<?> t1) {
		this.x = t1.getX();
		this.y = t1.getY();
	}

	/**
	 * Sets the value of this tuple to the specified x and y
	 * coordinates.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the value of this tuple to the specified x and y
	 * coordinates.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the value of this tuple from the 2 values specified in 
	 * the array.
	 * @param t the array of length 2 containing xy in order
	 */
	public void set(int[] t) {
		this.x = t[0];
		this.y = t[1];
	}

	/**
	 * Sets the value of this tuple from the 2 values specified in 
	 * the array.
	 * @param t the array of length 2 containing xy in order
	 */
	public void set(float[] t) {
		this.x = t[0];
		this.y = t[1];
	}

	/**
	 * Get the <i>x</i> coordinate.
	 * 
	 * @return the x coordinate.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Set the <i>x</i> coordinate.
	 * 
	 * @param x  value to <i>x</i> coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the <i>x</i> coordinate.
	 * 
	 * @param x  value to <i>x</i> coordinate.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get the <i>y</i> coordinate.
	 * 
	 * @return  the <i>y</i> coordinate.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Set the <i>y</i> coordinate.
	 * 
	 * @param y value to <i>y</i> coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Set the <i>y</i> coordinate.
	 * 
	 * @param y value to <i>y</i> coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Sets the value of this tuple to the difference of itself and x and y.
	 * @param x
	 * @param y
	 */
	public void sub(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	/**
	 * Sets the value of this tuple to the difference of itself and x and y.
	 * @param x
	 * @param y
	 */
	public void sub(float x, float y) {
		this.x -= x;
		this.y -= y;
	}

	/**
	 * Sets the x value of this tuple to the difference of itself and x.
	 * @param x
	 */
	public void subX(int x) {
		this.x -= x;
	}

	/**
	 * Sets the x value of this tuple to the difference of itself and x.
	 * @param x
	 */
	public void subX(float x) {
		this.x -= x;
	}

	/**
	 * Sets the y value of this tuple to the difference of itself and y.
	 * @param y
	 */
	public void subY(int y) {
		this.y -= y;
	}

	/**
	 * Sets the y value of this tuple to the difference of itself and y.
	 * @param y
	 */
	public void subY(float y) {
		this.y -= y;
	}

	/** 
	 *  Linearly interpolates between tuples t1 and t2 and places the 
	 *  result into this tuple:  this = (1-alpha)*t1 + alpha*t2.
	 *  @param t1  the first tuple
	 *  @param t2  the second tuple
	 *  @param alpha  the alpha interpolation parameter
	 */
	public void interpolate(T t1, T t2, float alpha) {
		this.x = ((1f-alpha)*t1.getX() + alpha*t2.getX());
		this.y = ((1f-alpha)*t1.getY() + alpha*t2.getY());
	}

	/**  
	 *  Linearly interpolates between this tuple and tuple t1 and 
	 *  places the result into this tuple:  this = (1-alpha)*this + alpha*t1.
	 *  @param t1  the first tuple
	 *  @param alpha  the alpha interpolation parameter  
	 */   
	public void interpolate(T t1, float alpha) {
		this.x = ((1f-alpha)*this.x + alpha*t1.getX());
		this.y = ((1f-alpha)*this.y + alpha*t1.getY());
	}

	/**   
	 * Returns true if all of the data members of Tuple2f t1 are
	 * equal to the corresponding data members in this Tuple2f.
	 * @param t1  the vector with which the comparison is made
	 * @return  true or false
	 */  
	public boolean equals(Tuple2f<?> t1) {
		try {
			return(this.x == t1.getX() && this.y == t1.getY());
		}
		catch (NullPointerException e2) {
			return false;
		}
	}

	/**   
	 * Returns true if the Object t1 is of type Tuple2f and all of the
	 * data members of t1 are equal to the corresponding data members in
	 * this Tuple2f.
	 * @param t1  the object with which the comparison is made
	 * @return  true or false
	 */  
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object t1) {
		try {
			T t2 = (T) t1;
			return(this.x == t2.getX() && this.y == t2.getY());
		}
		catch(AssertionError e) {
			throw e;
		}
		catch (Throwable e2) {
			return false;
		}
	}

	/**
	 * Returns true if the L-infinite distance between this tuple
	 * and tuple t1 is less than or equal to the epsilon parameter, 
	 * otherwise returns false.  The L-infinite
	 * distance is equal to MAX[abs(x1-x2), abs(y1-y2)]. 
	 * @param t1  the tuple to be compared to this tuple
	 * @param epsilon  the threshold value  
	 * @return  true or false
	 */
	public boolean epsilonEquals(T t1, float epsilon) {
		float diff;

		diff = this.x - t1.getX();
		if(Float.isNaN(diff)) return false;
		if((diff<0f?-diff:diff) > epsilon) return false;

		diff = this.y - t1.getY();
		if(Float.isNaN(diff)) return false;
		if((diff<0f?-diff:diff) > epsilon) return false;

		return true;
	}

	/**
	 * Returns true if the L-infinite distance between this tuple
	 * and the zero tuple is less than or equal to the epsilon parameter, 
	 * otherwise returns false.  The L-infinite
	 * distance is equal to MAX[abs(x1), abs(y1)]. 
	 * @param epsilon  the threshold value  
	 * @return  true or false
	 */
	public boolean epsilonNul(float epsilon) {
		if((this.x<0f?-this.x:this.x) > epsilon) return false;
		if((this.y<0f?-this.y:this.y) > epsilon) return false;
		return true;
	}

	/**
	 * Returns a hash code value based on the data values in this
	 * object.  Two different Tuple2f objects with identical data values
	 * (i.e., Tuple2f.equals returns true) will return the same hash
	 * code value.  Two objects with different data members may return the
	 * same hash value, although this is not likely.
	 * @return the integer hash code value
	 */  
	@Override
	public int hashCode() {
		return Objects.hashCode(this.x, this.y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" //$NON-NLS-1$
				+this.x
				+";" //$NON-NLS-1$
				+this.y
				+")"; //$NON-NLS-1$
	}

	@Override
	public int compareTo(T o) {
		if (o == null) {
			return Integer.MAX_VALUE;
		}
		int c = Float.compare(this.x, o.x);
		if (c != 0) {
			return c;
		}
		return Float.compare(this.y, o.y);
	}
	
	/** Replies if the tuple has zero coordinates.
	 *
	 * @return <code>true</code> if the x and y coordinates are equal to zero.
	 */
	public boolean isEmpty() {
		return this.x == 0f && this.y == 0f;
	}
	
}
