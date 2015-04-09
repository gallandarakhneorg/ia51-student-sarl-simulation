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

/** Abstract implementation of a time.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractTime implements Time {

	/**
	 */
	public AbstractTime() {
		//
	}

	@Override
	public float getCurrentTime(TimeUnit unit) {
		float t = getCurrentTime();
		switch(unit) {
		case DAYS:
			return t / 86400f;
		case HOURS:
			return t / 3600f;
		case MINUTES:
			return t / 60f;
		case SECONDS:
			return t;
		case MILLISECONDS:
			return t * 1000f;
		case MICROSECONDS:
			return t * 1000000f;
		case NANOSECONDS:
			return t * 1000000000f;
		default:
			return t;
		}
	}

	@Override
	public float getLastStepDuration(TimeUnit unit) {
		float step = getLastStepDuration();
		switch(unit) {
		case DAYS:
			return step / 86400f;
		case HOURS:
			return step / 3600f;
		case MINUTES:
			return step / 60f;
		case SECONDS:
			return step;
		case MILLISECONDS:
			return step * 1000f;
		case MICROSECONDS:
			return step * 1000000f;
		case NANOSECONDS:
			return step * 1000000000f;
		default:
			return step;
		}
	}

	@Override
	public float perSecond(float amountPerSecond) {
		return amountPerSecond * getLastStepDuration();
	}

	@Override
	public int compareTo(Time o) {
		float t = (o == null) ? 0f : o.getCurrentTime();
		return Float.compare(getCurrentTime(), t);
	}

}