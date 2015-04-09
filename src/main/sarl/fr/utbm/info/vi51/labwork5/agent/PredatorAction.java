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
package fr.utbm.info.vi51.labwork5.agent;

import fr.utbm.info.vi51.general.qlearning.QAction;
import fr.utbm.info.vi51.general.qlearning.QComparable;

/** Action of a predator in the QLearning process.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class PredatorAction implements QAction {

	private static final long serialVersionUID = -8369241275865124561L;
	
	private final PredatorActionType type;
	
	/**
	 * @param type
	 */
	public PredatorAction(PredatorActionType type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PredatorAction clone() {
		try {
			return (PredatorAction)super.clone();
		}
		catch(Throwable e) {
			throw new Error(e);
		}
	}
	
	/** Replies the type of the action.
	 * 
	 * @return the type of the action.
	 */
	public PredatorActionType getType() {
		return this.type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int toInt() {
		return this.type.ordinal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(QComparable o) {
		return toInt() - o.toInt();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.type.name();
	}
	
}
