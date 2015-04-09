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
package fr.utbm.info.vi51.general.formation;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * A slot in a formation body.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class FormationSlot {

	private final int spotIndex;
	private final Vector2f globalOrientation;
	private final Point2f globalPosition;
	private final Vector2f relativePosition;
	private final Vector2f relativeOrientation;
	private final FormationSlot parent;
	
	/**
	 * @param dx is the relative vector from the parent slot to this slot.
	 * @param dy is the relative vector from the parent slot to this slot.
	 * @param rotation is the relative rotation from the parent slot. 
	 * @param parent is the parent slot.
	 * @param spotIndex is the index of the slot in the formation body.
	 */
	public FormationSlot(float dx, float dy, float rotation, FormationSlot parent, int spotIndex) {
		this.spotIndex = spotIndex;
		this.globalOrientation = null;
		this.globalPosition = null;
		this.relativePosition = new Vector2f(dx, dy);
		this.relativeOrientation = Vector2f.toOrientationVector(rotation);
		this.parent = parent;
	}
	
	/**
	 */
	public FormationSlot() {
		this.spotIndex = 0;
		this.globalOrientation = Vector2f.toOrientationVector(0);
		this.globalPosition = new Point2f();
		this.relativePosition = new Vector2f();
		this.relativeOrientation = null;
		this.parent = null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return LocalizedString.getString(FormationSlot.class, "SLOT", this.spotIndex); //$NON-NLS-1$
	}
	
	/** Replies the global position of this slot.
	 * 
	 * @return the global position of this slot.
	 */
	public Point2f getGlobalPosition() {
		Point2f pos = new Point2f();
		computeGlobalPositionOrientation(pos);
		return pos;
	}
	
	private float computeGlobalPositionOrientation(Point2f position) {
		if (this.parent != null) {
			float angle = this.parent.computeGlobalPositionOrientation(position);
			
			Vector2f myPosition = this.relativePosition.clone();
			myPosition.turn(angle);
			
			position.add(myPosition);

			return angle;
		}
		position.set(this.globalPosition);
		return this.globalOrientation.getOrientationAngle();
	}

	/** Set the global position of this slot.
	 * <p>
	 * This function does nothing if this slot has a parent slot.
	 * 
	 * @param x
	 * @param y
	 */
	public void setGlobalPosition(float x, float y) {
		if (this.parent == null) {
			assert (this.globalPosition != null);
			this.globalPosition.set(x,y);
		}
	}

	/** Replies the global orientation which may be targeted by an entity
	 * on this slot.
	 * 
	 * @return the global orientation targeted by the entity on the slot.
	 */
	public Vector2f getGlobalOrientation() {
		if (this.parent != null) {
			Vector2f v = this.parent.getGlobalOrientation();
			v.turn(this.relativeOrientation.getOrientationAngle());
			return v;
		}
		return this.globalOrientation.clone();
	}

	/** Set the global orientation of this slot.
	 * <p>
	 * This function does nothing is this slot has a parent slot.
	 * 
	 * @param x
	 * @param y
	 */
	void setGlobalOrientation(float x, float y) {
		if (this.parent == null) {
			assert (this.globalOrientation != null);
			this.globalOrientation.set(x,y);
			if (this.globalOrientation.length() > 0f) {
				this.globalOrientation.normalize();
			}
		}
	}

	/** Replies the relative position from the parent slot to this slot.
	 * 
	 * @return the relative position.
	 */
	public Vector2f getRelativePosition() {
		return this.relativePosition.clone();
	}
	
	/** Replies the relative orientation from the parent slot to this slot.
	 * 
	 * @return the relative orientation
	 */
	public float getRelativeOrientation() {
		return this.relativeOrientation.getOrientationAngle();
	}

	/** Replies the parent slot.
	 *
	 * @return the parent slot, or <code>null</code> if none.
	 */
	public FormationSlot getSpotParent() {
		return this.parent;
	}
	
	/** Replies the index of this slot in the formation body.
	 * 
	 * @return the index of this slot in the formation body.
	 */
	public int getSpotIndex() {
		return this.spotIndex;
	}

}