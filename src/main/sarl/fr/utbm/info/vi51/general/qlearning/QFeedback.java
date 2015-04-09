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
package fr.utbm.info.vi51.general.qlearning;

import java.io.Serializable;

/**
 * Feedback for the QLearning.
 * 
 * @param <S> is the type of the states
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class QFeedback<S extends QState> implements Cloneable, Serializable {

	private static final long serialVersionUID = 3570187670012709771L;

	/** Score given to the action by the feedback algorithm.
	 */
	private float score;
	
	/** Arriving state after the execution of the action.
	 */
	private final S newState;
	
	/**
	 * @param newState
	 * @param score
	 */
	public QFeedback(S newState, float score) {
		this.newState = newState;
		this.score = score;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QFeedback<S> clone() {
		try {
			return (QFeedback<S>)super.clone();
		}
		catch(Throwable e) {
			throw new Error(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return Float.toString(this.score) + " >> " + this.newState.toString(); //$NON-NLS-1$
	}
	
	/** Replies the new state after the action commitment.
	 * 
	 * @return the new state.
	 */
	public S getNewState() {
		return this.newState;
	}
	
	/** Replies the feedback score.
	 * 
	 * @return the feedback score.
	 */
	public float getScore() {
		return this.score;
	}

	/** Set the feedback score.
	 * 
	 * @param score is the feedback score.
	 */
	public void setScore(float score) {
		this.score = score;
	}

}