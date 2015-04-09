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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 * This is the QLearning core algorithm.
 * 
 * @param <S> is the type of the states
 * @param <A> is the type of the actions
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class QLearning<S extends QState, A extends QAction> {

	private final Random random = new Random();
	private final QProblem<S,A> problem;
	private final Map<S,Map<A,Float>> qValues = new TreeMap<S,Map<A,Float>>(new QComparator()); 

	/**
	 * @param problem is the problem to learn on.
	 */
	public QLearning(QProblem<S,A> problem) {
		this.problem = problem;
		for(S s : problem.getAvailableStates()) {
			Map<A,Float> m = new TreeMap<A, Float>(new QComparator());
			this.qValues.put(s, m);
			for(A a : problem.getAvailableActionsFor(s)) {
				m.put(a, 0f);
			}
		}
	}

	/**
	 * @param numberOfIterations are the number of iterations to execute for this call.
	 */
	public void learn(int numberOfIterations) {
		List<A> actions;
		S state;
		A action;
		QFeedback<S> result;
				
		state = this.problem.getCurrentState();
		
		for(int i=0; i<numberOfIterations; ++i) {
			if (this.random.nextFloat()<this.problem.getNu()) {
				state = this.problem.getRandomState();
			}
			
			if (this.random.nextFloat()<this.problem.getRho()) {
				actions = this.problem.getAvailableActionsFor(state);
				action = actions.get(this.random.nextInt(actions.size()));
			}
			else {
				action = getBestAction(state);
			}
			
			result = forceFeedBack(state, action, Float.NaN);
			
			state = result.getNewState();
		}
	}
	
	/** Force the update of the QLearning graph with the given feedback.
	 * 
	 * @param state is the state from which the feedback is given.
	 * @param action is the state from which the feedback is given.
	 * @param feedbackValue is the feedback value.
	 * @return the feedback used.
	 */
	public QFeedback<S> forceFeedBack(S state, A action, float feedbackValue) {
		QFeedback<S> result = this.problem.takeAction(state,action);
		assert(result!=null);
		if (!Float.isNaN(feedbackValue))
			result.setScore(feedbackValue);
		
		float q = getQ(state, action);
		
		A futureAction = getBestAction(result.getNewState());
		float maxQ = getQ(result.getNewState(), futureAction);
		
		q = (1f - this.problem.getAlpha()) * q
			+ this.problem.getAlpha() *
				(result.getScore() +
					this.problem.getGamma() * maxQ);
		
		putQ(state, action, q);
		
		return result;
	}
	
	/** Replies the best action from the QState or a random action
	 * if more than 1 best action.
	 * 
	 * @param state
	 * @return the best action
	 */
	public A getBestAction(S state) {
		Map<A,Float> m = this.qValues.get(state);
		assert(m!=null);
		List<A> bestActions = new ArrayList<A>();
		float bestScore = Float.NEGATIVE_INFINITY;
		for(Entry<A,Float> entry : m.entrySet()) {
			if (entry.getValue()>bestScore) {
				bestActions.clear();
				bestActions.add(entry.getKey());
				bestScore = entry.getValue();
			}
			else if (entry.getValue()==bestScore) {
				bestActions.add(entry.getKey());
			}
		}
		assert(!bestActions.isEmpty());
		return bestActions.get(this.random.nextInt(bestActions.size()));
	}
	
	private float getQ(S state, A action) {
		Map<A,Float> m = this.qValues.get(state);
		assert(m!=null);
		Float q = m.get(action);
		if (q==null) return 0f;
		return q.floatValue();
	}

	private void putQ(S state, A action, float q) {
		Map<A,Float> m = this.qValues.get(state);
		assert(m!=null);
		m.put(action, q);
	}

}