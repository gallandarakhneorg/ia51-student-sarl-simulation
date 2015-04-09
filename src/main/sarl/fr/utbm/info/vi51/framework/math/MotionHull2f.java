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

import java.lang.ref.SoftReference;

import com.google.common.base.Objects;



/** 2D Path with floating-point values.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class MotionHull2f extends Shape2f<MotionHull2f> {

	private static final long serialVersionUID = -2716047233536640322L;

	private final Point2f start = new Point2f();
	private final Vector2f direction = new Vector2f();
	private final float size;
	
	private SoftReference<Rectangle2f> bounds = null;
	
	/**
	 * @param point
	 * @param vector
	 * @param lateralSize
	 */
	public MotionHull2f(Point2f point, Vector2f vector, float lateralSize) {
		this.start.set(point);
		this.direction.set(vector);
		this.size = lateralSize;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MotionHull2f) {
			MotionHull2f r = (MotionHull2f) obj;
			return Objects.equal(this.start, r.start) && Objects.equal(this.direction, r.direction);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.start, this.direction);
	}

	@Override
	public MotionHull2f clone() {
		return (MotionHull2f) super.clone();
	}
	
	/** Replies a copy of the start point of this path.
	 * 
	 * @return the start point.
	 */
	public Point2f getStart() {
		return this.start.clone();
	}

	/** Replies a copy of the direction of this path.
	 * 
	 * @return the upper point.
	 */
	public Vector2f getDirection() {
		return this.direction.clone();
	}
	
	@Override
	public String toString() {
		return "[" + this.start.toString() + "-" + this.direction.toString() + "]";
	}

	/** Replies if an intersection exists between this rectangle and the given shape.
	 * 
	 * @param s - the shape.
	 * @return <code>true</code> if an intersection exists.
	 */
	public boolean intersects(Shape2f<?> s) {
		if (s instanceof MotionHull2f) {
			MotionHull2f p = (MotionHull2f) s;
			Point2f end = this.start.clone();
			end.add(this.direction);
			Point2f end2 = p.start.clone();
			end2.add(p.direction);
			float d = MathUtil.distanceSegmentToSegment(
					this.start, end, p.start, end2);
			return d < (this.size + p.size);
		}
		if (s instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) s;
			Point2f end = this.start.clone();
			end.add(this.direction);
			Point2f pa = new Point2f(r.getLower().getX(), r.getUpper().getY());
			Point2f pb = new Point2f(r.getUpper().getX(), r.getLower().getY());
			float d1 = MathUtil.distanceSegmentToSegment(
					r.getLower(), pa, this.start, end);
			float d2 = MathUtil.distanceSegmentToSegment(
					pa, r.getUpper(), this.start, end);
			float d3 = MathUtil.distanceSegmentToSegment(
					r.getUpper(), pb, this.start, end);
			float d4 = MathUtil.distanceSegmentToSegment(
					pb, r.getLower(), this.start, end);
			float d = MathUtil.min(d1, d2, d3, d4);
			return d < this.size;
		}
		if (s instanceof Circle2f) {
			Circle2f c = (Circle2f) s;
			Point2f center = c.getCenter();
			Point2f end = this.start.clone();
			end.add(this.direction);
			return MathUtil.distancePointToSegment(center, this.start, end) < (this.size + c.getRadius());
		}
		throw new IllegalArgumentException();
	}
	
	/** Replies the center point of the rectangle.
	 * 
	 * @return the center point.
	 */
	public Point2f getCenter() {
		return new Point2f(
				this.start.getX() + this.direction.getX() / 2f,
				this.start.getY() + this.direction.getY() / 2f);
	}

	@Override
	public MotionHull2f translate(Tuple2f<?> vector) {
		return new MotionHull2f(new Point2f(
				this.start.getX() + vector.getX(),
				this.start.getY() + vector.getY()),
				this.direction.clone(),
				this.size);
	}

	@Override
	public Rectangle2f getBounds() {
		Rectangle2f bb = this.bounds == null ? null : this.bounds.get();
		if (bb == null) {
			float x = this.start.getX() + this.direction.getX();
			float y = this.start.getY() + this.direction.getY();
			Vector2f d = this.direction.clone();
			d.turn(MathUtil.PI / 2f);
			
			float x1 = this.start.getX() + d.getX();
			float y1 = this.start.getY() + d.getY();
			float x2 = this.start.getX() - d.getX();
			float y2 = this.start.getY() - d.getY();
			float x3 = x + d.getX();
			float y3 = y + d.getY();
			float x4 = x - d.getX();
			float y4 = y - d.getY();
			
			bb = new Rectangle2f(
					MathUtil.min(x1, x2, x3, x4),
					MathUtil.min(y1, y2, y3, y4),
					MathUtil.max(x1, x2, x3, x4),
					MathUtil.max(y1, y2, y3, y4));
			this.bounds = new SoftReference<Rectangle2f>(bb);
		}
		return bb;
	}
	
	@Override
	public float getMaxDemiSize() {
		return this.size;
	}

}