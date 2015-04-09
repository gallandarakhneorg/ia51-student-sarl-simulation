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

import java.io.Serializable;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.Point2f;

/**
 * Object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface SituatedObject extends ShapedObject, Cloneable, Comparable<SituatedObject> {

	/** Clone the object.
	 *
	 * @return the clone.
	 */
	SituatedObject clone();
	
	/** Replies the type of the object.
	 * 
	 * @return the type of the object.
	 */
	Serializable getType();
	
	/** Replies the identifier of the object.
	 * 
	 * @return the identifier of the object.
	 */
	UUID getID();

	/** Replies the name of the object.
	 *
	 * The name is defined only for displaying purpose.
	 * 
	 * @return the name of the object.
	 */
	String getName();

	/** Replies the position of the object.
	 * 
	 * @return the x-coordinate of the position of this object.
	 */
	float getX();
	
	/** Replies the position of the object.
	 * 
	 * @return the y-coordinate of the position of this object.
	 */
	float getY();

	/** Replies the position of the object.
	 * 
	 * @return the position of the object.
	 */
	Point2f getPosition();
	
}