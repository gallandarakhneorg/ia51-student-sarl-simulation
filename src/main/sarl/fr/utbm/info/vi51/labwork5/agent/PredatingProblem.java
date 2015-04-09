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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.arakhne.afc.vmutil.locale.Locale;

import com.google.common.base.Objects;

import fr.utbm.info.vi51.framework.environment.Percept;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.general.qlearning.DefaultQState;
import fr.utbm.info.vi51.general.qlearning.QFeedback;
import fr.utbm.info.vi51.general.qlearning.QProblem;

/** Description of the predating problem for
 * the QLearning algorithm.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
class PredatingProblem implements QProblem<DefaultQState,PredatorAction> {

	private static final long serialVersionUID = -9176626183288110292L;

	private static final float VERY_GOOD_SCORE = 2f;
	private static final float GOOD_SCORE = 1f;
	private static final float IDDLE_SCORE = 0f;
	private static final float BAD_SCORE = -1f;
	private static final float VERY_BAD_SCORE = -2f;
	
	private final Random random = new Random();
	
	/** Current state in the learning algorithm.
	 */
	private DefaultQState currentState = null;
	
	/** Last target encountered by the predator.
	 */
	private Percept lastTargetEncountered = null;
	
	private final DefaultQState[] states = new DefaultQState[16];
	
	/**
	 */
	public PredatingProblem() {
		for(int i=0; i<this.states.length; ++i) {
			this.states[i] = new DefaultQState(
					i,
					Locale.getString(PredatingProblem.class, "STATE_"+i)); //$NON-NLS-1$
		}
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public PredatingProblem clone() {
		try {
			return (PredatingProblem)super.clone();
		}
		catch(Throwable e) {
			throw new Error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAlpha() {
		return .5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getGamma() {
		return .5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getNu() {
		return .5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getRho() {
		return .5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PredatorAction> getAvailableActionsFor(DefaultQState state) {
		List<PredatorAction> actions = new ArrayList<PredatorAction>();
		for(PredatorActionType type : PredatorActionType.values()) {
			actions.add(new PredatorAction(type));
		}
		return actions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DefaultQState> getAvailableStates() {
		return Arrays.asList(this.states);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefaultQState getCurrentState() {
		return this.currentState;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefaultQState getRandomState() {
		int idx = this.random.nextInt(this.states.length);
		return this.states[idx];
	}

	private Percept detectPrey(List<Percept> percepts) {
		for(Percept p : percepts) {
			if (Objects.equal(p.getType(), "TARGET")) {
				return p;
			}
		}
		return null;
	}
	
	/** Translate a list of perceptions to the current QState.
	 * 
	 * @param position
	 * @param percepts
	 */
	public void translateCurrentState(Point2f position, List<Percept> percepts) {
		this.lastTargetEncountered = detectPrey(percepts);
		if (this.lastTargetEncountered==null) {
			this.currentState = this.states[0];
			return;
		}

		Vector2f vectorToPrey = this.lastTargetEncountered.getPosition().operator_minus(position);
		vectorToPrey.normalize();
		
		byte zones = 0;
		byte cell;
		
		for(Percept p : percepts) {
			if (!this.lastTargetEncountered.equals(p)) {
				Vector2f vectorToPredator = p.getPosition().operator_minus(this.lastTargetEncountered.getPosition());
				vectorToPredator.normalize();
				
				double angle = vectorToPrey.signedAngle(vectorToPredator);
				
				if (-Math.PI/4. <=angle&&angle<= Math.PI/4.) {
					cell = 1; // Back
				}
				else if (Math.PI/4. <=angle&&angle<= 3.*Math.PI/4.) {
					cell = 2; // Left
				}
				else if (-3.*Math.PI/4. <=angle&&angle<= -Math.PI/4.) {
					cell = 4; // Right
				}
				else {
					cell = 8; // On my side
				}
				
				zones = (byte)(zones | cell);
				if (zones==15) break; // All zones contains a predator
			}
		}
		
		this.currentState = this.states[zones + 1];
	}
	
	/** Replies the last encountered target.
	 * 
	 * @return the last encountered target.
	 */
	public Percept getLastEncounteredTarget() {
		return this.lastTargetEncountered;
	}
	
	private QFeedback<DefaultQState> verygood(int state) {
		return verygood(this.states[state]);
	}

	private static QFeedback<DefaultQState> verygood(DefaultQState state) {
		return new QFeedback<DefaultQState>(state, VERY_GOOD_SCORE);
	}

	private QFeedback<DefaultQState> good(int state) {
		return good(this.states[state]);
	}

	private static QFeedback<DefaultQState> good(DefaultQState state) {
		return new QFeedback<DefaultQState>(state, GOOD_SCORE);
	}

	private static QFeedback<DefaultQState> noimpact(DefaultQState state) {
		return new QFeedback<DefaultQState>(state, IDDLE_SCORE);
	}

	private QFeedback<DefaultQState> bad(int state) {
		return bad(this.states[state]);
	}

	private static QFeedback<DefaultQState> bad(DefaultQState state) {
		return new QFeedback<DefaultQState>(state, BAD_SCORE);
	}

	private QFeedback<DefaultQState> verybad(int state) {
		return verybad(this.states[state]);
	}

	private static QFeedback<DefaultQState> verybad(DefaultQState state) {
		return new QFeedback<DefaultQState>(state, VERY_BAD_SCORE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QFeedback<DefaultQState> takeAction(DefaultQState state, PredatorAction action) {
		// Give a feedback score for the action.
		switch(state.toInt()) {
		case 0: // No carrot
			switch(action.getType()) {
			case RANDOM_MOVE:
			case WAIT:
				return good(state);
			case MOVE_LEFT:
			case MOVE_RIGHT:
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			default:
			}
			break;
		case 1: // Alone
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(state);
			case MOVE_RIGHT:
			case WAIT:
				return good(state);
			case RANDOM_MOVE:
				return bad(state);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			default:
			}
			break;
		case 2: // All back
			switch(action.getType()) {
			case MOVE_LEFT:
				// Next state should be all are on the left
				return verygood(3);
			case MOVE_RIGHT:
				return good(5);
			case WAIT:
				return noimpact(state);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case RANDOM_MOVE:
				return bad(state);
			default:
			}
			break;
		case 3: // All left
			switch(action.getType()) {
			case MOVE_RIGHT:
				return good(2);
			case MOVE_LEFT:
				return bad(9);
			case RANDOM_MOVE:
				return bad(state);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 4: // Left, and back (none on my side) 
			switch(action.getType()) {
			case MOVE_RIGHT:
				return verygood(this.states[6]);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_LEFT:
				return bad(11);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 5: // All right
			switch(action.getType()) {
			case MOVE_LEFT:
				return good(2);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_RIGHT:
				return verybad(9);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 6: // Right, and back (none on my side)
			switch(action.getType()) {
			case MOVE_LEFT:
				return good(4);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_RIGHT:
				return verybad(13);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 7: // Left, and right (none on my side)
			switch(action.getType()) {
			case MOVE_LEFT:
			case MOVE_RIGHT:
				return bad(10);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return good(state);
			default:
			}
			break;
		case 8: // Left, right, and back (none on my side)
			switch(action.getType()) {
			case MOVE_TO_KILL_THE_PREY:
				return verygood(state);
			case WAIT:
			case RANDOM_MOVE:
				return verybad(state);
			case MOVE_LEFT:
				return verybad(12);
			case MOVE_RIGHT:
				return verybad(14);
			default:
			}
			break;
		case 9: // All on my side
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(5);
			case MOVE_RIGHT:
				return good(3);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case WAIT:
			case RANDOM_MOVE:
				return bad(state);
			default:
			}
			break;
		case 10: // On my side, and back
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(7);
			case MOVE_RIGHT:
				return good(7);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 11: // On my side, and left
			switch(action.getType()) {
			case MOVE_RIGHT:
				return verygood(4);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_LEFT:
				return verybad(13);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 12: // On my side, left, and back
			switch(action.getType()) {
			case MOVE_RIGHT:
				return verygood(8);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_LEFT:
				return verybad(15);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 13: // On my side, and right
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(6);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_RIGHT:
				return verybad(11);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 14: // On my side, right, and back
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(8);
			case MOVE_TO_KILL_THE_PREY:
				return verybad(state);
			case MOVE_RIGHT:
				return verybad(15);
			case RANDOM_MOVE:
				return bad(state);
			case WAIT:
				return noimpact(state);
			default:
			}
			break;
		case 15: // On my side, right, and left
			switch(action.getType()) {
			case MOVE_LEFT:
				return verygood(14);
			case MOVE_RIGHT:
				return good(12);
			case MOVE_TO_KILL_THE_PREY:
			case WAIT:
				return verybad(state);
			case RANDOM_MOVE:
				return bad(state);
			default:
			}
			break;
		case 16: // On my side, left, right, and back
			switch(action.getType()) {
			case MOVE_TO_KILL_THE_PREY:
				return verygood(state);
			case MOVE_LEFT:
			case MOVE_RIGHT:
			case WAIT:
			case RANDOM_MOVE:
				return noimpact(state);
			default:
			}
			break;
		default:
		}
		
		// Default: no score change nor state change.
		return new QFeedback<DefaultQState>(state, 0f);
	}
	
}
