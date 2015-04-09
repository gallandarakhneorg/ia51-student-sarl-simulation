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
package fr.utbm.info.vi51.framework.time;


/** Time manager for the Jaak environment.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface TimeManager extends Time {

	/** Increment the time.
	 */
	void increment();
	
	/** Replies the delay to apply at each simulation step.
	 *
	 * This delay permits to control partially the speed of the simulation process.
	 *
	 * @return the delay of waiting between two simulation steps.
	 */
	float getSimulationDelay();

	/** Set the delay to apply at each simulation step.
	 *
	 * This delay permits to control partially the speed of the simulation process.
	 *
	 * @param delay the delay of waiting between two simulation steps.
	 */
	void setSimulationDelay(float delay);

}