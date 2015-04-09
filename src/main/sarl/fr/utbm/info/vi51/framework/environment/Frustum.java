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
import java.util.Iterator;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.math.Vector2f;

/**
 * Define a field-of-view.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface Frustum extends Serializable, Cloneable {

	/** Replies the identifier of the owner of this frustum.
	 *
	 * @return the identifier of the frustum's owner.
	 */
	UUID getOwner();
	
	/** Replies a shape that is representing this frustum.
	 *
	 * @param position the position of the owner of the frustum.
	 * @param orientation the orientation of the owner of the frustum.
	 * @return the shape.
	 */
	Shape2f<?> toShape(Point2f position, Vector2f orientation);
	
	/** Create a filtering iterator.
	 * The replied iterator may filtering the objects of the given iterator.
	 *
	 * @param iterator the original iterator.
	 * @return the filtering iterator, or the given <code>iterator</code> if
	 * there is no filtering.
	 */
	<D extends SituatedObject> Iterator<D> filter(Iterator<D> iterator);
	
}