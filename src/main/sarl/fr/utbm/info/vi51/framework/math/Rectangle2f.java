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

import com.google.common.base.Objects;


/** 2D Rectangle with floating-point values.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Rectangle2f extends Shape2f<Rectangle2f> {

	private static final long serialVersionUID = -2716047233536640322L;

	private final Point2f lower = new Point2f();
	private final Point2f upper = new Point2f();
	
	/**
	 */
	public Rectangle2f() {
		//
	}

	/**
	 * @param p1
	 * @param p2
	 */
	public Rectangle2f(Point2f p1, Point2f p2) {
		this.lower.set(
				Math.min(p1.getX(), p2.getX()),
				Math.min(p1.getY(), p2.getY()));
		this.upper.set(
				Math.max(p1.getX(), p2.getX()),
				Math.max(p1.getY(), p2.getY()));
	}

	/**
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Rectangle2f(float x1, float y1, float x2, float y2) {
		this.lower.set(
				Math.min(x1, x2),
				Math.min(y1, y2));
		this.upper.set(
				Math.max(x1, x2),
				Math.max(y1, y2));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) obj;
			return Objects.equal(this.lower, r.lower) && Objects.equal(this.upper, r.upper);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.lower, this.upper);
	}

	@Override
	public Rectangle2f clone() {
		return (Rectangle2f) super.clone();
	}
	
	/** Replies a copy of the lower point of this rectangle.
	 * 
	 * @return the lower point.
	 */
	public Point2f getLower() {
		return this.lower.clone();
	}

	/** Replies a copy of the upper point of this rectangle.
	 * 
	 * @return the upper point.
	 */
	public Point2f getUpper() {
		return this.upper.clone();
	}
	
	@Override
	public String toString() {
		return "[" + this.lower.toString() + "-" + this.upper.toString() + "]";
	}

	/** Replies if an intersection exists between this rectangle and the given shape.
	 * 
	 * @param s - the shape.
	 * @return <code>true</code> if an intersection exists.
	 */
	public boolean intersects(Shape2f<?> s) {
		if (s instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) s;
			return intersects(this.lower.getX(), this.upper.getX(), r.lower.getX(), r.upper.getX())
					&& intersects(this.lower.getY(), this.upper.getY(), r.lower.getY(), r.upper.getY());
		}
		if (s instanceof Circle2f) {
			Circle2f c = (Circle2f) s;
			Point2f center = c.getCenter();
			float x = MathUtil.clamp(center.getX(), this.lower.getX(), this.upper.getX());
			float y = MathUtil.clamp(center.getY(), this.lower.getY(), this.upper.getY());
			x -= center.getX();
			y -= center.getY();
			float radius = c.getRadius();
			return (x*x+y*y) < (radius * radius);
		}
		if (s instanceof MotionHull2f) {
			return ((MotionHull2f) s).intersects(this);
		}
		throw new IllegalArgumentException();
	}
	
	private boolean intersects(float a1, float a2, float b1, float b2) {
		assert (a1 <= a2);
		assert (b1 <= b2);
		return (a2 > b1) && (b2 > a1);
	}

	/** Replies the center point of the rectangle.
	 * 
	 * @return the center point.
	 */
	public Point2f getCenter() {
		return new Point2f(
				(this.lower.getX() + this.upper.getX()) / 2f,
				(this.lower.getY() + this.upper.getY()) / 2f);
	}

	@Override
	public Rectangle2f translate(Tuple2f<?> vector) {
		return new Rectangle2f(
				this.lower.getX() + vector.getX(),
				this.lower.getY() + vector.getY(),
				this.upper.getX() + vector.getX(),
				this.upper.getY() + vector.getY());
	}

	@Override
	public Rectangle2f getBounds() {
		return clone();
	}

	/** Replies the width of the rectangle.
	 *
	 * @return the width.
	 */
	public float getWidth() {
		return this.upper.getX() - this.lower.getX();
	}

	/** Replies the height of the rectangle.
	 *
	 * @return the height.
	 */
	public float getHeight() {
		return this.upper.getY() - this.lower.getY();
	}
	
	@Override
	public float getMaxDemiSize() {
		return Math.max(getWidth(), getHeight()) / 2f;
	}

}