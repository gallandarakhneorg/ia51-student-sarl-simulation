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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;

/** A formation composed of connected slots.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class FormationPattern {

	private FormationSlot leaderSpot;
	private final List<FormationSlot> spots;

	/**
	 */
	public FormationPattern() {
		this.leaderSpot = null;
		this.spots = new ArrayList<>();
	}
		
	/** Clear the formation body.
	 */
	protected void clear() {
		this.leaderSpot = null;
		this.spots.clear();
	}

	private void addSpot(FormationSlot spot) {
		this.spots.add(spot);
		if (this.spots.size() == 1) {
			this.leaderSpot = spot;
		}
	}
	
	/** Replies the slots in the formation body.
	 * 
	 * @return the slots in the formation body.
	 */
	public List<FormationSlot> getSlots() {
		return Collections.unmodifiableList(this.spots);
	}
	
	/** Replies the slot at the given index.
	 * 
	 * @param index
	 * @return the slot at the given index.
	 */
	public FormationSlot getSlotAt(int index) {
		return this.spots.get(index);
	}

	/** Force this formation body to contains the given number of slots.
	 * 
	 * @param spotCount
	 */
	public void scale(int spotCount) {
		clear();
		FormationSlot spot;
		for(int i=0; i<spotCount; i++) {
			spot = createSpot(i,spotCount);
			if (spot!=null) {
				addSpot(spot);
			}
		}
	}
	
	/** Create a slot for the given position in the formation body.
	 * 
	 * @param spotIndex is the position of the new slot.
	 * @param spotCount is the total number of slots in the formation.
	 * @return the created slot.
	 */
	protected abstract FormationSlot createSpot(int spotIndex, int spotCount);
	
	/** Change the global position of the formation, ie of the leader.
	 * 
	 * @param x
	 * @param y
	 */
	public void setGlobalPosition(float x, float y) {
		if (this.leaderSpot!=null) {
			this.leaderSpot.setGlobalPosition(x,y);
		}
	}
	
	/** Change the global position of the formation, ie of the leader.
	 * 
	 * @param position
	 */
	public void setGlobalPosition(Point2f position) {
		setGlobalPosition(position.getX(), position.getY());
	}

	/** Change the global orientation of the formation, ie of the leader.
	 * 
	 * @param x
	 * @param y
	 */
	public void setGlobalOrientation(float x, float y) {
		if (this.leaderSpot!=null) {
			this.leaderSpot.setGlobalOrientation(x,y);
		}
	}

	/** Change the global orientation of the formation, ie of the leader.
	 * 
	 * @param direction
	 */
	public void setGlobalOrientation(Vector2f direction) {
		setGlobalOrientation(direction.getX(), direction.getY());
	}

	/** Replies the global position of the formation, ie of the leader.
	 * 
	 * @return the global position of the leader.
	 */
	public Point2f getGlobalPosition() {
		if (this.leaderSpot!=null) {
			return this.leaderSpot.getGlobalPosition();
		}
		return new Point2f();
	}

}