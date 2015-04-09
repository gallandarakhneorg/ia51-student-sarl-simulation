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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.time.TimeManager;

/**
 * Abstract implementation of a situated environment.  
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractEnvironment implements Environment {

	private final Map<UUID,AgentBody> agentBodies = new TreeMap<UUID,AgentBody>();
	private final TimeManager timeManager;
	private final float width;
	private final float height;
	private final Collection<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();
	private final AtomicBoolean stateChanged = new AtomicBoolean();
	private final AtomicBoolean init = new AtomicBoolean(true);

	/**
	 * @param width is the width of the environment.
	 * @param height is the height of the environment.
	 * @param timeManager is the time manager to use.
	 */
	public AbstractEnvironment(float width, float height, TimeManager timeManager) {
		this.width = width;
		this.height = height;
		this.timeManager = timeManager;
	}
	
	/** Mark the state of the environment as changed.
	 */
	protected void stateChanged() {
		this.stateChanged.set(true);
	}

	/** {@inheritDoc}
	 */
	public void addEnvironmentListener(EnvironmentListener listener) {
		synchronized(this.listeners) {
			this.listeners.add(listener);
		}
	}

	/** {@inheritDoc}
	 */
	public void removeEnvironmentListener(EnvironmentListener listener) {
		synchronized(this.listeners) {
			this.listeners.remove(listener);
		}
	}

	/** Invoked to create an environment event.
	 * 
	 * @return an environment event.
	 */
	protected EnvironmentEvent createEnvironmentEvent() {
		return new EnvironmentEvent(this);
	}

	/** Notifies listeners about changes in environment.
	 */
	protected void fireEnvironmentChange() {
		EnvironmentListener[] list;
		synchronized(this.listeners) {
			list = new EnvironmentListener[this.listeners.size()];
			this.listeners.toArray(list);
		}
		EnvironmentEvent event = createEnvironmentEvent();
		for(EnvironmentListener listener : list) {
			listener.environmentChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public TimeManager getTimeManager() {
		return this.timeManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getWidth() {
		return this.width;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getHeight() {
		return this.height;
	}

	/** Add an agent body.
	 * This function could be call before the simulation is started.
	 * 
	 * @param body - the body.
	 * @param position - the position of the body.
	 * @param direction - the direction of the body.
	 */
	protected synchronized void addAgentBody(AgentBody body, Point2f position, float direction) {
		if (this.init.get()) {
			this.agentBodies.put(body.getID(), body);
			body.setPosition(position.getX(), position.getY());
			body.setAngle(direction);
			onAgentBodyCreated(body);
		} else {
			throw new IllegalStateException("You cannot call this function after the start of the simulation");
		}
	}

	/** Invoked when an agent body is created.
	 * 
	 * @param body the body.
	 */
	protected abstract void onAgentBodyCreated(AgentBody body);

	/** Invoked when an agent body is destroyed.
	 * 
	 * @param body the body.
	 */
	protected abstract void onAgentBodyDestroyed(AgentBody body);

	/**
	 * {@inheritDoc}
	 */
	public WorldModelState getState() {
		return new WorldModelState(this);
	}

	/** Replies the bodies in the environment.
	 *
	 * The replied collection is unmodifiable.
	 * 
	 * @return the bodies in the environment.
	 */
	public Iterable<AgentBody> getAgentBodies() {
		return Collections.unmodifiableCollection(this.agentBodies.values());
	}

	@Override
	public int getAgentBodyNumber() {
		return this.agentBodies.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public AgentBody getAgentBodyFor(UUID agentId) {
		return this.agentBodies.get(agentId);
	}

	/**
	 * {@inheritDoc}
	 */
	public void runBehaviour() {
		if (this.init.getAndSet(false)) {
			fireEnvironmentChange();
		}

		this.stateChanged.set(false);

		Collection<MotionInfluence> motionInfluences = new ArrayList<>();
		Collection<Influence> otherInfluences = new ArrayList<>();
		for(AgentBody body : this.agentBodies.values()) {
			MotionInfluence mi = body.consumeMotionInfluence();
			if (mi != null) {
				motionInfluences.add(mi);
			}
			for (Influence i : body.consumeOtherInfluences()) {
				if (i instanceof KillInfluence) {
					stateChanged();
					AgentBody rbody = this.agentBodies.remove(i.getEmitter());
					if (rbody != null) {
						onAgentBodyDestroyed(rbody);
					}
				} else {
					otherInfluences.add(i);
				}
			}
		}

		for(Influence i : computeEndogenousBehaviorInfluences()) {
			if (i instanceof MotionInfluence) {
				motionInfluences.add((MotionInfluence) i);
			} else if (!(i instanceof KillInfluence)) {
				otherInfluences.add(i);
			}
		}

		if (!motionInfluences.isEmpty() || !otherInfluences.isEmpty()) {
			applyInfluences(
					motionInfluences,
					otherInfluences,
					this.timeManager);
		}

		if (this.stateChanged.get()) {
			fireEnvironmentChange();
		}

		this.timeManager.increment();

		List<Percept> list;
		for(AgentBody body : this.agentBodies.values()) {
			list = computePerceptionsFor(body);
			if (list==null) list = Collections.emptyList();
			body.setPerceptions(list);
		}
	}

	/** Compute the influences for the endogenous behavior of the environment.
	 * 
	 * @return the list of the environment influences.
	 */
	protected abstract List<Influence> computeEndogenousBehaviorInfluences();

	/** Compute the perceptions for an agent body.
	 * 
	 * @param agent is the body of the perceiver.
	 * @return the list of the perceived object, never <code>null</code>
	 */
	protected abstract List<Percept> computePerceptionsFor(AgentBody agent);

	/** Detects conflicts between influences and applied resulting actions.
	 * 
	 * @param motionInfluences are the motion influences to apply.
	 * @param otherInfluences are the other influences to apply.
	 * @param timeManager is the time manager of the environment.
	 */
	protected abstract void applyInfluences(Collection<MotionInfluence> motionInfluences,
			Collection<Influence> otherInfluences, TimeManager timeManager);

	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector2f computeSteeringTranslation(MobileObject obj, Vector2f move, TimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeSteeringTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector2f computeKinematicTranslation(MobileObject obj, Vector2f move, TimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeKinematicTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final float computeKinematicRotation(MobileObject obj, float move, TimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeKinematicRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final float computeSteeringRotation(MobileObject obj, float move, TimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeSteeringRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Move the given object.
	 * 
	 * @param obj is the object to move.
	 * @param instantTranslation is the linear motion in m
	 * @param instantRotation is the angular motion in r
	 */
	protected final void move(MobileObject obj, Vector2f instantTranslation, float instantRotation) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			float duration = this.timeManager.getLastStepDuration();
			o.move(instantTranslation.getX(), instantTranslation.getY(), duration, getWidth(), getHeight());
			o.rotate(instantRotation, duration);
			stateChanged();
		}
		else {
			throw new IllegalArgumentException("obj"); //$NON-NLS-1$
		}
	}

}