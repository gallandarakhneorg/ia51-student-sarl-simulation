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
package fr.utbm.info.vi51.general.frustum;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.AbstractFrustum;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.math.Vector2f;

/**
 * A circular frustum.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class CircleFrustum extends AbstractFrustum {

	private static final long serialVersionUID = -2803027579045308299L;
	
	private final float radius;
	
	/**
	 * @param owner the identifier of the owner of this frustum.
	 * @param radius the radius of the field-of-view.
	 */
	public CircleFrustum(UUID owner, float radius) {
		super(owner);	
		this.radius = radius;
	}
	
	/** Replies the radius of the frustum.
	 *
	 * @return the radius.
	 */
	public float getRadius() {
		return radius;
	}

	@Override
	public Shape2f<?> toShape(Point2f position, Vector2f orientation) {
		return new Circle2f(position, getRadius());
	}

}