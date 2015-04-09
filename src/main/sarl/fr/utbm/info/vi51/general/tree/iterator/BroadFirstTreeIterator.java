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
package fr.utbm.info.vi51.general.tree.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import fr.utbm.info.vi51.general.tree.SpatialTreeNode;


/**
 * Broad-first tree iterator.
 * 
 * @param <N> - type of the root node.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class BroadFirstTreeIterator<N extends SpatialTreeNode<N, ?>> implements Iterator<N> {

	private final LinkedList<N> queue = new LinkedList<>();
	
	/**
	 * @param root - the root node.
	 */
	public BroadFirstTreeIterator(N root) {
		if (root != null) {
			this.queue.add(root);
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.queue.isEmpty();
	}

	@Override
	public N next() {
		if (this.queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		N n = this.queue.remove(0);
		this.queue.addAll(n.getChildren());
		return n;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}