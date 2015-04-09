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

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.general.tree.SpatialTree;
import fr.utbm.info.vi51.general.tree.SpatialTreeNode;


/**
 * Abstract definition of a spatial tree node.
 * 
 * @param <N> - type of the root node.
 * @param <D> - type of the data inside the node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractSpatialTreeNode<N extends SpatialTreeNode<N, D>, D extends ShapedObject> implements SpatialTreeNode<N, D> {

	private static final long serialVersionUID = 2749817253571456287L;

	private Rectangle2f bounds;	
	private Collection<D> data;
	private transient WeakReference<N> parentNode;
	private transient WeakReference<SpatialTree<N, D>> tree;

	/** 
	 */
	public AbstractSpatialTreeNode() {
		//
	}

	/** 
	 * @param bounds
	 */
	public AbstractSpatialTreeNode(Rectangle2f bounds) {
		assert (bounds != null);
		this.bounds = bounds;
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		for (N child : getChildren()) {
			((AbstractSpatialTreeNode<N,D>) child).setParent((N) this);
		}
	}

	/** Replies the tree in which this node is located..
	 *
	 * @return the tree.
	 */
	protected SpatialTree<N, D> getTree() {
		return this.tree == null ? null : this.tree.get();
	}

	/** Create an instance of a tree node for the tree.
	 *
	 * @param bounds - the bounds covered by the node.
	 * @return the node.
	 */
	@SuppressWarnings("unchecked")
	protected N newNodeInstance(Rectangle2f bounds) {
		return getTree().getNodeFactory().newInstance(bounds, (N) this);
	}

	/** Clear the list of data.
	 */
	protected void clearData() {
		if (this.data != null) {
			this.data.clear();
		}
	}

	@Override
	public Collection<D> getData() {
		if (this.data == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableCollection(this.data);
	}

	@Override
	public int getDataCount() {
		return this.data == null ? 0 : this.data.size();
	}

	/** Replies the internal data structure that is available for storing data.
	 * 
	 * @return the data structure
	 */
	protected final Collection<D> getInternalDataStructure() {
		if (this.data == null) {
			this.data = new TreeSet<>();
		}
		return this.data;
	}

	/** Change the internal data structure that is available for storing data.
	 * The data are moved from the previous data structure to the new data structure.
	 * 
	 * @param type the type of the internal data structure to use.
	 */
	protected final void setInternalDataStructure(Class<? extends Collection<D>> type) {
		Collection<D> newData;
		try {
			newData = type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (this.data != null) {
			newData.addAll(this.data);
		}
		this.data = newData;
	}

	/** Remove the data.
	 * This function cannot be overridden.
	 * 
	 * @param data
	 */
	protected final void removeDataNotOverriddable(D data) {
		if (this.data != null) {
			this.data.remove(data);
			if (this.data.isEmpty()) {
				this.data = null;
			}
		}
	}

	@Override
	public N getParent() {
		return this.parentNode == null ? null : this.parentNode.get();
	}

	/** Change the parent node.
	 *
	 * @param parent the new parent node.
	 */
	protected void setParent(N parent) {
		SpatialTree<N, D> t;
		if (parent == null) {
			this.parentNode = null;
			t = null;
		} else {
			this.parentNode = new WeakReference<>(parent);
			if (parent instanceof AbstractSpatialTreeNode<?, ?>) {
				t = ((AbstractSpatialTreeNode<N, D>) parent).getTree();
			} else {
				t = null;
			}
		}
		setTree(t);
	}

	/** Change the tree link.
	 *
	 * @param tree the container.
	 */
	protected void setTree(SpatialTree<N, D> tree) {
		if (tree == null) {
			this.tree = null;
		} else {
			this.tree = new WeakReference<>(tree);
		}
	}

	@Override
	public Rectangle2f getBounds() {
		return this.bounds;
	}

	@Override
	public boolean isRoot() {
		return this.parentNode == null;
	}
	
	@Override
	public String toString() {
		return getInternalDataStructure().toString();
	}

}