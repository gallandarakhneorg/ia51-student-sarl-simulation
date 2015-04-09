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
import java.util.Iterator;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.general.frustum.FrustumCullerTreeIterator;
import fr.utbm.info.vi51.general.tree.SpatialTree;
import fr.utbm.info.vi51.general.tree.SpatialTreeNode;
import fr.utbm.info.vi51.general.tree.SpatialTreeNodeFactory;
import fr.utbm.info.vi51.general.tree.iterator.BroadFirstTreeIterator;
import fr.utbm.info.vi51.general.tree.iterator.DepthFirstObjectTreeIterator;


/**
 * Abstract definition of a spatial tree.
 * 
 * @param <N> - type of the root node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractSpatialTree<N extends SpatialTreeNode<N, D>, D extends ShapedObject> implements SpatialTree<N, D> {

	private static final long serialVersionUID = -8519697258779018514L;
	
	private SpatialTreeNodeFactory<N> factory;
	private N root;
	
	/**
	 * @param factory the node factory to use.
	 */
	public AbstractSpatialTree(SpatialTreeNodeFactory<N> factory) {
		assert (factory != null);
		this.factory = factory;
	}
	
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		for (N node : this) {
			((AbstractSpatialTreeNode<N, D>) node).setTree((SpatialTree<N, D>) this);
		}
    }
	
	@Override
	public boolean addData(D data) {
		if (data == null) {
			return false;
		}
		N root = getRoot();
		if (root != null) {
			return root.addData(data);
		}
		return false;
	}
	
	@Override
	public boolean removeData(D data) {
		if (data == null) {
			return false;
		}
		N root = getRoot();
		if (root != null) {
			return root.removeData(data);
		}
		return false;
	}
	
	@Override
	public Rectangle2f getBounds() {
		N root = getRoot();
		if (root == null) {
			return new Rectangle2f();
		}
		return root.getBounds();
	}
	
	@Override
	public N getRoot() {
		return this.root;
	}

	@Override
	public void setRoot(N root) {
		if (this.root instanceof AbstractSpatialTreeNode<?, ?>) {
			((AbstractSpatialTreeNode<N, D>) this.root).setTree(null);
		}
		this.root = root;
		if (this.root instanceof AbstractSpatialTreeNode<?, ?>) {
			((AbstractSpatialTreeNode<N, D>) this.root).setTree((SpatialTree<N, D>) this);
		}
	}

	@Override
	public Iterator<N> iterator() {
		return new BroadFirstTreeIterator<>(getRoot());
	}

	@Override
	public SpatialTreeNodeFactory<N> getNodeFactory() {
		return this.factory;
	}
	
	@Override
	public void setNodeFactory(SpatialTreeNodeFactory<N> factory) {
		this.factory = factory;
	}

	@Override
	public void initialize(Rectangle2f worldSize) {
		setRoot(getNodeFactory().newInstance(worldSize, null));
	}

	@Override
	public Iterator<D> dataIterator() {
		return new DepthFirstObjectTreeIterator<N, D>(getRoot());
	}
	
	@Override
	public Iterator<D> dataIterator(Shape2f<?> bounds) {
		return new FrustumCullerTreeIterator<>(getRoot(), bounds);
	}

	@Override
	public Iterable<D> getData() {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return dataIterator();
			}
		};
	}

}