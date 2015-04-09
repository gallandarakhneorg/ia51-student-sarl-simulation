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
package fr.utbm.info.vi51.general.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

/**
 * Definition of a spatial tree node.
 * 
 * @param <N> - type of the root node.
 * @param <D> - type of the data inside the tree.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface SpatialTreeNode<N extends SpatialTreeNode<N, D>, D extends ShapedObject> extends Iterable<N>, Serializable {

	/** Replies the data associated to the node.
	 *
	 * @return the data.
	 */
	Collection<D> getData();
	
	/** Replies the number of data associated to the node.
	 *
	 * @return the number of data.
	 */
	int getDataCount();

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

	/** Replies the bounds covered by the node.
	 *
	 * @return the bounds of the area covered by the node.
	 */
	Rectangle2f getBounds();

	/** Return the parent node.
	 *
	 * @return the parent node, or <code>null</code> if this node is the root.
	 */
	N getParent();
	
	/** Returns the children.
	 *
	 * @return the children.
	 */
	List<N> getChildren();
	
	/** Replies if this node is a leaf in the tree.
	 *
	 * @return <code>true</code> if this node has no child.
	 */
	boolean isLeaf();

	/** Replies if this node is the root in the tree.
	 *
	 * @return <code>true</code> if this node has no parent.
	 */
	boolean isRoot();
	
	/** Create the child nodes if they are not existing.
	 */
	void createChildren();

	/** Remove the child nodes if they are existing.
	 */
	void removeChildren();
	
	/** Replies if the node has no data nor child.
	 * 
	 * @return <code>true</code> if there is no data nor child.
	 */
	boolean isEmpty();

}