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
package fr.utbm.info.vi51.labwork2.environment;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.AbstractSituatedObject;
import fr.utbm.info.vi51.framework.math.Circle2f;

/**
 * Situated object representing the mouse position.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public final class MouseTarget extends AbstractSituatedObject {

	private static final long serialVersionUID = -7372943049564638811L;

	/**
	 * @param x
	 * @param y
	 */
	public MouseTarget(float x, float y) {
		super(UUID.randomUUID(), new Circle2f(0, 0, 5f), x, y);
		setType("TARGET");
	}
	
	/** Set the position of the mouse target.
	 * 
	 * @param x
	 * @param y
	 */
	public void setMousePosition(float x, float y) {
		// *** CAUTION ***
		// Changing directly the position of the target without going through the influence
		// solver may cause collision between the target and the agents.
		setPosition(x, y);
	}
	
}