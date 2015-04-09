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

import java.util.List;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.Vector2f;

/**
 * Provides the services of a body.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface Body {

	/** Replies the id of this body.
	 * 
	 * @return the id of this body.
	 */
	UUID getID();

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	void influenceKinematic(Vector2f linearInfluence, float angularInfluence);
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	void influenceSteering(Vector2f linearInfluence, float angularInfluence);

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	void influenceKinematic(Vector2f linearInfluence);
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	void influenceSteering(Vector2f linearInfluence);
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	void influenceKinematic(float angularInfluence);
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	void influenceSteering(float angularInfluence);
	
	/** Replies all the perceived objects.
	 * 
	 * @return the perceived objects.
	 */
	List<Percept> getPerceivedObjects();

}