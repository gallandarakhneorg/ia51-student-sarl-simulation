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
package fr.utbm.info.vi51.labwork5.environment;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

/**
 * Influence for adding an object.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class TargetAdditionInfluence extends Influence {

	private static final long serialVersionUID = 176348512638922738L;

	private final Point2f position;
	
	/**
	 * @param influencedObject the addable object.
	 * @param
	 */
	public TargetAdditionInfluence(UUID influencedObject, Point2f position) {
		super(influencedObject);
		this.position = position;
	}
	
	/** Replies the position.
	 *
	 * @return the position.
	 */
	public Point2f getPosition() {
		return this.position;
	}
	
	@Override
	public String toString() {
		return "ADD TARGET @ " + getPosition();
	}

}