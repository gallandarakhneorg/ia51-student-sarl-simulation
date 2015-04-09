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
package fr.utbm.info.vi51.labwork5.environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.utbm.info.vi51.framework.environment.SituatedObject;
import fr.utbm.info.vi51.framework.environment.SpatialDataStructure;
import fr.utbm.info.vi51.framework.environment.WorldModelState;

/**
 * State of the world model.  
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class ExtendedWorldModelState extends WorldModelState {

	private final SpatialDataStructure<SituatedObject> dataStructure;
	
	/**
	 * @param environment
	 */
	@SuppressWarnings("unchecked")
	public ExtendedWorldModelState(WorldModel environment) {
		super(environment);
		try {
			// Clone through serialization for using native cloning features
			byte[] array;
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(environment.getSpatialDataStructure());
				baos.flush();
				array = baos.toByteArray();
			}
			try (ByteArrayInputStream bais = new ByteArrayInputStream(array)) {
				ObjectInputStream ois = new ObjectInputStream(bais); 
				this.dataStructure = (SpatialDataStructure<SituatedObject>) ois.readObject();
			}
		} catch (Exception e) {
			throw new IOError(e);
		}
	}
	
	/** Replies the spatial data-structure.
	 * 
	 * @return the spatial data-structure.
	 */
	public SpatialDataStructure<SituatedObject> getSpatialDataStructure() {
		return this.dataStructure;
	}

}