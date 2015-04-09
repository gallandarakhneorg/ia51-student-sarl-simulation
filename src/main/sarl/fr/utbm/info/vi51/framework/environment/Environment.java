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

import java.util.UUID;

import fr.utbm.info.vi51.framework.time.TimeManager;

/**
 * Situated environment.  
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface Environment {

	/** Replies the time manager of this environment.
	 * 
	 * @return the time manager of this environment.
	 */
	public TimeManager getTimeManager();
	
	/** Replies the width of the environment.
	 * 
	 * @return the width of the environment.
	 */
	public float getWidth();
	
	/** Replies the height of the environment.
	 * 
	 * @return the height of the environment.
	 */
	public float getHeight();
	
	/** Replies number of bodies in the environment.
	 * 
	 * @return the number of bodies in the environment.
	 */
	public int getAgentBodyNumber();

	/** Replies the agent body associated to the given agent.
	 * 
	 * @param agentId
	 * @return the agent body or <code>null</code>.
	 */
	public AgentBody getAgentBodyFor(UUID agentId);
	
	/** Replies the objects in the environment.
	 *
	 * The replied collection is unmodifiable.
	 *
	 * @return the objects in the environment.
	 */
	public Iterable<? extends SituatedObject> getAllObjects();

	/** Replies the bodies in the environment.
	 *
	 * The replied collection is unmodifiable.
	 *
	 * @return the bodies in the environment.
	 */
	public Iterable<? extends AgentBody> getAgentBodies();

	/** Run the environment behaviour: apply influences, compute perceptions.
	 */
	public void runBehaviour();
	
	/** Add listener on environment events.
	 * 
	 * @param listener
	 */
	public void addEnvironmentListener(EnvironmentListener listener);

	/** Remove listener on environment events.
	 * 
	 * @param listener
	 */
	public void removeEnvironmentListener(EnvironmentListener listener);
	
}