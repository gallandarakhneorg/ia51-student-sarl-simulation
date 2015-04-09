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

import java.util.concurrent.TimeUnit;

/** Time manager for the Jaak environment.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface Time extends Comparable<Time> {

	/** Replies the current time in seconds.
	 *
	 * @return the current time in seconds.
	 */
	float getCurrentTime();

	/** Replies the current time in the given time unit.
	 *
	 * @param unit is the time unit to use for replied value.
	 * @return the current time.
	 */
	float getCurrentTime(TimeUnit unit);

	/** Replies the duration of the last simulation step in seconds.
	 *
	 * @return the duration of the last simulation step in seconds.
	 */
	float getLastStepDuration();

	/** Replies the duration of the last simulation step in the given time unit.
	 *
	 * @param unit is the time unit used to format the replied value.
	 * @return the duration of the last simulation step.
	 */
	float getLastStepDuration(TimeUnit unit);

	/** Replies the instant amount which is corresponds to the
	 * given amount, given per second.
	 * 
	 * @param amountPerSecond
	 * @return amountPerSecond * getTimeStepDuration()
	 */
	float perSecond(float amountPerSecond);

}