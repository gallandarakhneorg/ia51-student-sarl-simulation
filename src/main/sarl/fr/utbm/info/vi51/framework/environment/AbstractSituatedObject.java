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

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Abstract implementation of an object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractSituatedObject implements SituatedObject, Serializable {

	private static final long serialVersionUID = -3513920280954202791L;
	
	private final UUID id;
	private Point2f position = new Point2f();
	private Shape2f<?> shape;
	private String name;
	private Serializable type;
	
	/**
	 * @param id the identifier of the object.
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 */
	public AbstractSituatedObject(UUID id, Shape2f<?> shape) {
		assert (id != null);
		this.id = id;
		this.shape = shape;
	}

	/**
	 * @param id the identifier of the object.
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 * @param position is the position of the object.
	 */
	public AbstractSituatedObject(UUID id, Shape2f<?> shape, Point2f position) {
		assert (position!=null);
		assert (id != null);
		this.id = id;
		this.shape = shape;
		this.position.set(position);
	}

	/**
	 * @param id the identifier of the object.
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 * @param x is the position of the object.
	 * @param y is the position of the object.
	 */
	public AbstractSituatedObject(UUID id, Shape2f<?> shape, float x, float y) {
		assert (id != null);
		this.id = id;
		this.shape = shape;
		this.position.set(x, y);
	}
	
	@Override
	public AbstractSituatedObject clone() {
		try {
			AbstractSituatedObject clone = (AbstractSituatedObject) super.clone();
			clone.position = this.position.clone();
			clone.shape = this.shape.clone();
			return clone;
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof SituatedObject) {
			SituatedObject sobj = (SituatedObject) obj;
			return Objects.equal(sobj.getID(), getID());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getID());
	}
	
	@Override
	public int compareTo(SituatedObject o) {
		if (o == null) {
			return Integer.MAX_VALUE;
		}
		return getID().compareTo(o.getID());
	}
	
	@Override
	public Serializable getType() {
		return this.type;
	}
	
	@Override
	public final UUID getID() {
		return this.id;
	}
	
	/** Set the type of the object.
	 * 
	 * @param type
	 */
	public void setType(Serializable type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	/** Change the name of the object.
	 *
	 * The name is defined only for displaying purpose.
	 * 
	 * @return the name of the object.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Shape2f<?> getShape() {
		return this.shape.translate(getPosition());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point2f getPosition() {
		return new Point2f(this.position);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return this.position.getX();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return this.position.getY();
	}

	/**
	 * Set the position of the object.
	 * 
	 * @param x
	 * @param y
	 */
	protected void setPosition(float x, float y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			System.err.println(LocalizedString.getString(getClass(), "INVALID_POSITION", getName()));
		} else {
			this.position.set(x, y);
		}
	}
	
	/**
	 * Move the position of the object.
	 * 
	 * @param x
	 * @param y
	 */
	protected void addPosition(float x, float y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			System.err.println(LocalizedString.getString(getClass(), "INVALID_POSITION", getName()));
		} else {
			this.position.add(x, y);
		}
	}

	@Override
	public String toString() {
		String name = getName();
		if (name != null && !name.isEmpty()) {
			return name;
		}
		return super.toString();
	}

}