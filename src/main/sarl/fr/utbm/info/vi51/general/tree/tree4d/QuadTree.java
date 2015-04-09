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
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.general.tree.SpatialTreeNodeFactory;
import fr.utbm.info.vi51.general.tree.abstracts.AbstractSpatialTree;


/**
 * Definition of a quadtree.
 * 
 * @param <D> - the type of data in the tree.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class QuadTree<D extends ShapedObject> extends AbstractSpatialTree<QuadTreeNode<D>, D> {

	private static final long serialVersionUID = 4139508947751858094L;

	/**
	 */
	public QuadTree() {
		super(new QuadTreeNodeFactory<D>());
	}

	/**
	 * @param <D> - the type of data in the tree.
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private static class QuadTreeNodeFactory<D extends ShapedObject> implements SpatialTreeNodeFactory<QuadTreeNode<D>> {

		private static final long serialVersionUID = -8782831711364377550L;

		@Override
		public QuadTreeNode<D> newInstance(Rectangle2f bounds, QuadTreeNode<D> parent) {
			return new QuadTreeNode<D>(bounds);
		}
		
		@Override
		public int getMaxDataCountPerNode() {
			return 1;
		}
		
	}

}
