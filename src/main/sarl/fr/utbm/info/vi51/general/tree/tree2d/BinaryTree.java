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
import fr.utbm.info.vi51.general.tree.SpatialTreeNodeFactory;
import fr.utbm.info.vi51.general.tree.abstracts.AbstractSpatialTree;


/**
 * Definition of a 2D-tree.
 * 
 * @param <D> - the type of data in the tree.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class BinaryTree<D extends ShapedObject> extends AbstractSpatialTree<BinaryTreeNode<D>, D> {

	private static final long serialVersionUID = -1253764888721438194L;

	/**
	 */
	public BinaryTree() {
		super(new BinaryTreeNodeFactory<D>());
	}

	/**
	 * @param <D> - the type of data in the tree.
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private static class BinaryTreeNodeFactory<D extends ShapedObject> implements SpatialTreeNodeFactory<BinaryTreeNode<D>> {

		private static final long serialVersionUID = 3012750598513370201L;

		@Override
		public BinaryTreeNode<D> newInstance(Rectangle2f bounds, BinaryTreeNode<D> parent) {
			SeparationLine separationLine;
			if (parent == null) {
				separationLine = SeparationLine.VERTICAL;
			} else {
				separationLine = parent.getSeparationLine().turn();
			}
			return new BinaryTreeNode<D>(bounds, separationLine);
		}
		
		@Override
		public int getMaxDataCountPerNode() {
			return 1;
		}
		
	}

}
