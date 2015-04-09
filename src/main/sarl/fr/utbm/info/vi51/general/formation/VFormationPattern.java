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

import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Formation in V.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class VFormationPattern extends FormationPattern {

	/** Space between slots.
	 */
	public static final float INTER_SLOT_SPACE = 50f;
	
	/**
	 */
	public VFormationPattern() {
		scale(1);
	}
	
	/**
	 * @param slotCount is the count of slots in the formation.
	 */
	public VFormationPattern(int slotCount) {
		scale(slotCount);
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public String toString() {
		return LocalizedString.getString(VFormationPattern.class, "NAME", getSlots().size()); //$NON-NLS-1$
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
			FormationSlot parent = (spotIndex<=2) ? getSlotAt(0) : getSlotAt(spotIndex-2);
			
			if (spotIndex%2==0) {
				// left spot
				newSpot = new FormationSlot(-INTER_SLOT_SPACE,-INTER_SLOT_SPACE,0,parent,spotIndex);
			}
			else {
				// right spot
				newSpot = new FormationSlot(-INTER_SLOT_SPACE,INTER_SLOT_SPACE,0,parent,spotIndex);
			}
		}
		
		return newSpot;
	}

}