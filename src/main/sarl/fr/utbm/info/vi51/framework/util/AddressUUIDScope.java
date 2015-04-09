/* 
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package fr.utbm.info.vi51.framework.util;

import io.sarl.lang.core.Address;
import io.sarl.lang.core.Scope;

import java.util.UUID;

/** This scope is accepting the addresses that has the agent with the given identifier.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class AddressUUIDScope implements Scope<Address> {
	
	private static final long serialVersionUID = -854440717352244448L;

	private static final String SCOPE_ID = "uuid://"; //$NON-NLS-1$

	private final UUID id;

	/**
	 * @param id - identifier to put in the scope.
	 */
	public AddressUUIDScope(UUID id) {
	    this.id = id;
	}

	@Override
	public String toString() {
		return SCOPE_ID + this.id.toString();
	}

	@Override
	public boolean matches(Address address) {
		assert (address != null);
		return this.id.equals(address.getUUID());
	}
	
}
