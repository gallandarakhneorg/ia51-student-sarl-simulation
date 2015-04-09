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
package fr.utbm.info.vi51.general.tree.tree2d;

import fr.utbm.info.vi51.framework.environment.ShapedObject;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.general.tree.abstracts.AbstractArraySpatialTreeNode;


/**
 * Definition of a 2D-tree node.
 * 
 * @param <D> - type of the data in the node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class BinaryTreeNode<D extends ShapedObject> extends AbstractArraySpatialTreeNode<BinaryTreeNode<D>, D> {

	private static final long serialVersionUID = 2064484622807842374L;
	
	private final SeparationLine separationLine;
	
	/** 
	 * @param bounds the bounds covered by the node.
	 * @param separationLine defines the separation line for creating the children.
	 */
	public BinaryTreeNode(Rectangle2f bounds, SeparationLine separationLine) {
		super(bounds);
		this.separationLine = separationLine;
	}

	/** Replies the separation line to use for creating the children.
	 *
	 * @return the separation line.
	 */
	public SeparationLine getSeparationLine() {
		return this.separationLine;
	}
	
	/** Replies the lower or left child node.
	 * 
	 * @return the node.
	 */
	public BinaryTreeNode<D> getLowerOrLeftChild() {
		return this.children != null ? this.children[0] : null;
	}

	/** Replies the upper or right child node.
	 * 
	 * @return the node.
	 */
	public BinaryTreeNode<D> getUpperOrRightChild() {
		return this.children != null ? this.children[1] : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createChildren() {
		if (this.children == null) {
			this.children = new BinaryTreeNode[2];
		}
		if (this.children[0] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Rectangle2f childBounds = getSeparationLine().getLowerOrLeftBounds(bounds);
			this.children[0] = newNodeInstance(childBounds);
			this.children[0].setParent(this);
		}
		if (this.children[1] == null) {
			Rectangle2f bounds = getBounds();
			assert (bounds != null);
			Rectangle2f childBounds = getSeparationLine().getUpperOrRightBounds(bounds);
			this.children[1] = newNodeInstance(childBounds);
			this.children[1].setParent(this);
		}
	}

}