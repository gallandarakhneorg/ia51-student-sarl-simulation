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
public class Circle2f extends Shape2f<Circle2f> {

	private static final long serialVersionUID = -2716047233536640322L;

	private final Point2f center = new Point2f();
	private float radius;
	
	/**
	 */
	public Circle2f() {
		//
	}

	/**
	 * @param center
	 * @param radius
	 */
	public Circle2f(Point2f center, float radius) {
		this.center.set(center);
		this.radius = radius;
	}

	/**
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Circle2f(float x, float y, float radius) {
		this.center.set(x, y);
		this.radius = radius;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle2f) {
			Circle2f c = (Circle2f) obj;
			return Objects.equal(this.center, c.center) && this.radius == c.radius;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.center, this.radius);
	}

	@Override
	public Circle2f clone() {
		return (Circle2f) super.clone();
	}
	
	/** Replies a copy of the center point of this circle.
	 * 
	 * @return the center point.
	 */
	public Point2f getCenter() {
		return this.center.clone();
	}

	/** Replies the radius of the circle.
	 * 
	 * @return the radius.
	 */
	public float getRadius() {
		return this.radius;
	}
	
	@Override
	public String toString() {
		return "[" + this.center.toString() + "-" + this.radius + "]";
	}

	/** Replies if an intersection exists between this rectangle and the given shape.
	 * 
	 * @param s - the shape.
	 * @return <code>true</code> if an intersection exists.
	 */
	public boolean intersects(Shape2f<?> s) {
		if (s instanceof Circle2f) {
			Circle2f r = (Circle2f) s;
			float x = r.getCenter().getX() - this.center.getX();
			float y = r.getCenter().getY() - this.center.getY();
			return ((x*x) + (y*y)) < ((this.radius + r.radius) * (this.radius + r.radius));
		}
		if (s instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) s;
			return r.intersects(this);
		}
		if (s instanceof MotionHull2f) {
			return ((MotionHull2f) s).intersects(this);
		}
		throw new IllegalArgumentException();
	}

	@Override
	public Circle2f translate(Tuple2f<?> vector) {
		return new Circle2f(
				this.center.getX() + vector.getX(),
				this.center.getY() + vector.getY(),
				this.radius);
	}
	
	@Override
	public Rectangle2f getBounds() {
		return new Rectangle2f(
				this.center.getX() - this.radius,
				this.center.getY() - this.radius,
				this.center.getX() + this.radius,
				this.center.getY() + this.radius);
	}
	
	@Override
	public float getMaxDemiSize() {
		return this.radius;
	}
	
}