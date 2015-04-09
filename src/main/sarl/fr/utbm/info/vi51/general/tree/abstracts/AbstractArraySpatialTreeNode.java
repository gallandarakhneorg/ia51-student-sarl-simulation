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
package fr.utbm.info.vi51.general.tree.abstracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.math.Shape2f;


/**
 * Definition of a tree node that uses an array for storing the children.
 * 
 * @param <D> - type of the data in the node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractArraySpatialTreeNode<N extends AbstractArraySpatialTreeNode<N, D>, D extends ShapedObject> extends AbstractSpatialTreeNode<N, D> {

	private static final long serialVersionUID = -5545432812243292228L;

	/** Array of the child nodes.
	 */
	protected N[] children;

	/** 
	 * @param bounds the bounds covered by the node.
	 */
	public AbstractArraySpatialTreeNode(Rectangle2f bounds) {
		super(bounds);
	}

	@Override
	public boolean addData(D data) {
		if (isLeaf()) {
			int maxObjectsPerNode = getTree().getNodeFactory().getMaxDataCountPerNode();
			if (getDataCount() >= maxObjectsPerNode) {
				// Split the node and move all the objects into the children
				createChildren();
				Iterator<D> iterator = getInternalDataStructure().iterator();
				while (iterator.hasNext()) {
					D existingData = iterator.next();
					if (addInChildWhenPossible(existingData)) {
						iterator.remove();
					}
				}
				if (!addInChildWhenPossible(data)) {
					return getInternalDataStructure().add(data);
				}
			} else {
				// Enough space in this node.
				return getInternalDataStructure().add(data);
			}
		} else if (!addInChildWhenPossible(data)) {
			return getInternalDataStructure().add(data);
		}
		return true;
	}

	private boolean addInChildWhenPossible(D data) {
		assert (this.children != null);
		Shape2f<?> dataShape = data.getShape();
		N selectedChild = null;
		for (N child : this.children) {
			if (child.getBounds().intersects(dataShape)) {
				if (selectedChild != null) {
					// The object is intersecting two or more children
					return false;
				}
				selectedChild = child;
			}
		}
		if (selectedChild != null) {
			return selectedChild.addData(data);
		}
		// No intersection between the children and the data
		return false;
	}

	@Override
	public boolean removeData(D data) {
		boolean removed = true;
		if (isLeaf()) {
			removed = getInternalDataStructure().remove(data);
		} else {
			if (!removeFromChildWhenPossible(data)) {
				removed = getInternalDataStructure().remove(data);
			}
		}
		if (removed) {
			removeChildrenIfEmpty();
		}
		return removed;
	}

	private boolean removeChildrenIfEmpty() {
		if (this.children != null) {
			for (int i = 0; i < this.children.length; ++i) {
				if (this.children[i] != null
						&& !this.children[i].isEmpty()) {
					return false;
				}
			}
			for (int i = 0; i < this.children.length; ++i) {
				if (this.children[i] != null) {
					assert (this.children[i].getDataCount() == 0);
					this.children[i].setParent(null);
					this.children[i] = null;
				}
			}
			this.children = null;
			return true;
		}
		return false;
	}

	private boolean removeFromChildWhenPossible(D data) {
		assert (this.children != null);
		Shape2f<?> dataShape = data.getShape();
		N selectedChild = null;
		for (N child : this.children) {
			if (child.getBounds().intersects(dataShape)) {
				if (selectedChild != null) {
					// The object is intersecting two or more children
					return false;
				}
				selectedChild = child;
			}
		}
		if (selectedChild != null) {
			return selectedChild.removeData(data);
		}
		// No intersection between the children and the data
		return false;
	}

	@Override
	public boolean isLeaf() {
		return this.children == null;
	}

	@Override
	public boolean isEmpty() {
		return isLeaf() && getDataCount() == 0;
	}

	@Override
	public void removeChildren() {
		if (this.children != null) {
			for (int i = 0; i < this.children.length; ++i) {
				if (this.children[i] != null) {
					this.children[i].setParent(null);
					this.children[i] = null;
				}
			}
			this.children = null;
		}
	}

	@Override
	public List<N> getChildren() {
		List<N> nodes = new ArrayList<>(4);
		if (this.children != null) {
			for (int i  = 0; i < this.children.length; ++i) {
				if (this.children[i] != null) {
					nodes.add(this.children[i]);
				}
			}
		}
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public String toString() {
		return getBounds().toString() + "=>" + super.toString();
	}

	@Override
	public Iterator<N> iterator() {
		return new ChildIterator();
	}

	/**
	 * @param <D> - the type of the objects in the node.
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private class ChildIterator implements Iterator<N> {

		private int n = 0;
		private N next;

		/**
		 */
		public ChildIterator() {
			searchNext();
		}

		private void searchNext() {
			N c;
			this.next = null;
			while (this.n < AbstractArraySpatialTreeNode.this.children.length && this.next == null) {
				c = AbstractArraySpatialTreeNode.this.children[this.n];
				if (c != null) {
					this.next = c;
				}
				++this.n;
			}
		}

		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public N next() {
			N n = this.next;
			searchNext();
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}