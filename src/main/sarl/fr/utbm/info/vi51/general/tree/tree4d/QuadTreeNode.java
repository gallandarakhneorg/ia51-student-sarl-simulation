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
package fr.utbm.info.vi51.general.tree.tree4d;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.general.tree.abstracts.AbstractArraySpatialTreeNode;


/**
 * Definition of a quadtree node.
 * 
 * @param <D> - type of the data in the node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class QuadTreeNode<D extends ShapedObject> extends AbstractArraySpatialTreeNode<QuadTreeNode<D>, D> {

	private static final long serialVersionUID = -2119136081212633513L;

	/** 
	 * @param bounds the bounds covered by the node.
	 */
	public QuadTreeNode(Rectangle2f bounds) {
		super(bounds);
	}

	/** Replies the lower-left child node.
	 * 
	 * @return the node.
	 */
	public QuadTreeNode<D> getLowerLeftChild() {
		return this.children != null ? this.children[0] : null;
	}

	/** Replies the upper-left child node.
	 * 
	 * @return the node.
	 */
	public QuadTreeNode<D> getUpperLeftChild() {
		return this.children != null ? this.children[1] : null;
	}

	/** Replies the lower-right child node.
	 * 
	 * @return the node.
	 */
	public QuadTreeNode<D> getLowerRightChild() {
		return this.children != null ? this.children[2] : null;
	}

	/** Replies the upper-right child node.
	 * 
	 * @return the node.
	 */
	public QuadTreeNode<D> getUpperRightChild() {
		return this.children != null ? this.children[3] : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createChildren() {
		if (this.children == null) {
			this.children = new QuadTreeNode[4];
		}
		if (this.children[0] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Point2f lower = bounds.getLower();
			Point2f center = bounds.getCenter();
			this.children[0] = newNodeInstance(new Rectangle2f(lower, center));
			this.children[0].setParent(this);
		}
		if (this.children[1] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Point2f lower = bounds.getLower();
			Point2f upper = bounds.getUpper();
			Point2f center = bounds.getCenter();
			this.children[1] = newNodeInstance(
					new Rectangle2f(
							lower.getX(), center.getY(),
							center.getX(), upper.getY()));
			this.children[1].setParent(this);
		}
		if (this.children[2] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Point2f lower = bounds.getLower();
			Point2f upper = bounds.getUpper();
			Point2f center = bounds.getCenter();
			this.children[2] = newNodeInstance(
					new Rectangle2f(
							center.getX(), lower.getY(),
							upper.getX(), center.getY()));
			this.children[2].setParent(this);
		}
		if (this.children[3] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Point2f upper = bounds.getUpper();
			Point2f center = bounds.getCenter();
			this.children[3] = newNodeInstance(new Rectangle2f(center, upper));
			this.children[3].setParent(this);
		}
	}

}