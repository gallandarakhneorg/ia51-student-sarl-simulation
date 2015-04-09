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

import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.math.Shape2f;

/**
 * Definition of a spatial data-structure.
 * 
 * @param <N> - type of the root node.
 * @param <D> - the type of the objects inside the tree.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface SpatialDataStructure<D extends ShapedObject> extends Serializable {

	/** Initialize the data-structure that is covering the given area.
	 *
	 * @param worldSize the size of the world.
	 */
	void initialize(Rectangle2f worldSize);
	
	/** Replies the bounds covered by the tree nodes.
	 *
	 * @return the bounds covered by the tree.
	 */
	Rectangle2f getBounds();
	
	/** Change the data associated to the node.
	 *
	 * @param data - the data.
	 * @return <code>true</code> if the data was added.
	 */
	boolean addData(D data);

	/** Change the data associated to the node.
	 *
	 * @param data - the data.
	 * @return <code>true</code> if the data was removed.
	 */
	boolean removeData(D data);
	
	/** Replies an iterator on the data.
	 *
	 * @return the iterator on the data.
	 */
	Iterator<D> dataIterator();

	/** Replies an iterable on the data.
	 *
	 * @return the iterable on the data.
	 */
	Iterable<D> getData();

	/** Replies an iterator on the data elements that are intersecting the given bounds.
	 *
	 * @param bounds - the selection bounds.
	 * @return the iterator.
	 */
	Iterator<D> dataIterator(Shape2f<?> bounds);

}