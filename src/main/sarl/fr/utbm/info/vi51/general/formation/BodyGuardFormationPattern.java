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

import fr.utbm.info.vi51.framework.math.MathUtil;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Formation with body guards.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class BodyGuardFormationPattern extends FormationPattern {

	/** Default radius of a spot.
	 */
	public static final float SPOT_RADIUS = 20f;
	
	/** Default space between two spots.
	 */
	public static final float INTER_SPACE = 9f;

	private final float spotRadius;
	private final float interSpace;
	
	/**
	 */
	public BodyGuardFormationPattern() {
		this(1, SPOT_RADIUS, INTER_SPACE);
	}
	
	/**
	 * @param slotCount is the count of slots in the formation.
	 */
	public BodyGuardFormationPattern(int slotCount) {
		this(slotCount, SPOT_RADIUS, INTER_SPACE);
	}
	
	/**
	 * @param slotCount is the count of slots in the formation.
	 * @param spotRadius radius of the spots.
	 * @param interSpace space between two adjacent spots.
	 */
	public BodyGuardFormationPattern(int slotCount, float spotRadius, float interSpace) {
		this.spotRadius = spotRadius;
		this.interSpace = interSpace;
		scale(slotCount);
	}

	/** {@inheritDoc}
	 */
	@Override
	public String toString() {
		return LocalizedString.getString(BodyGuardFormationPattern.class, "NAME", getSlots().size()); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected synchronized FormationSlot createSpot(int spotIndex, int spotCount) {
		FormationSlot newSpot;
		
		if (spotIndex==0) {
			newSpot = new FormationSlot(); // leader
		}
		else {
			float angle = MathUtil.TWO_PI / (spotCount - 1);
			float radius = (this.spotRadius * 2f + this.interSpace) * (spotCount - 1) / MathUtil.TWO_PI;
			FormationSlot parent = getSlotAt(0);
			angle = (spotIndex - 1 + .5f) * angle;
			Vector2f v = Vector2f.toOrientationVector(angle);
			v.scale(radius);
			newSpot = new FormationSlot(v.getX(), v.getY(), angle, parent, spotIndex);
		}
		
		return newSpot;
	}

}