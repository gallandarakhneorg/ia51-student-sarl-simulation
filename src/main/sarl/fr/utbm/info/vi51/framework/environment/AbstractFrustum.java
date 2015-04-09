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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract implementation of a field-of-view.
 * This implementation provides the default filtering behavior: remove the perceiver's body
 * from the perception.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractFrustum implements Frustum {
	
	private static final long serialVersionUID = -6917303628628755437L;
	
	private final UUID owner;
	
	/**
	 * @param owner the identifier of the owner of this frustum.
	 */
	public AbstractFrustum(UUID owner) {
		this.owner = owner;
	}
	
	@Override
	public UUID getOwner() {
		return this.owner;
	}

	@Override
	public <D extends SituatedObject> Iterator<D> filter(Iterator<D> iterator) {
		return new PerceiverBodyFilter<>(iterator);
	}
	
	/**
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private class PerceiverBodyFilter<D extends SituatedObject> implements Iterator<D> {
		
		private final Iterator<D> original;
		private D next;
		
		public PerceiverBodyFilter(Iterator<D> original) {
			this.original = original;
			searchNext();
		}
		
		private void searchNext() {
			this.next = null;
			while (this.next == null && this.original.hasNext()) {
				D candidate = this.original.next();
				if (!Objects.equals(candidate.getID(), getOwner())) {
					this.next = candidate;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public D next() {
			D n = this.next;
			if (n == null) {
				throw new NoSuchElementException();
			}
			searchNext();
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}