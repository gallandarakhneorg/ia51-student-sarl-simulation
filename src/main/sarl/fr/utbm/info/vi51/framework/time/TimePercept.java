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

import java.io.Serializable;

import com.google.common.base.Objects;

/** A perception of the time.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class TimePercept extends AbstractTime implements Serializable, Cloneable {

	private static final long serialVersionUID = 6751639163169606473L;

	private final float currentTime;
	private final float stepDuration;
	
	/**
	 * @param currentTime
	 * @param stepDuration
	 */
	public TimePercept(float currentTime, float stepDuration) {
		this.currentTime = currentTime;
		this.stepDuration = stepDuration;
	}

	@Override
	public float getCurrentTime() {
		return this.currentTime;
	}

	@Override
	public float getLastStepDuration() {
		return this.stepDuration;
	}
	
	@Override
	public TimePercept clone() {
		try {
			return (TimePercept) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Time) {
			Time t = (Time) obj;
			return this.currentTime == t.getCurrentTime();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.currentTime);
	}
	
	@Override
	public String toString() {
		return Float.toString(this.currentTime);
	}

}