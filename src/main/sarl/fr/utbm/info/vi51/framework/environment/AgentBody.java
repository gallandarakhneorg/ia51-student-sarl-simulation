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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.MathUtil;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class AgentBody extends AbstractMobileObject implements Body {

	private static final long serialVersionUID = -4636419559142339321L;
	
	private final Frustum frustum;
	
	private transient MotionInfluence motionInfluence = null;
	private transient List<Influence> otherInfluences = new ArrayList<>();
	private transient List<Percept> perceptions = new ArrayList<>();

	/**
	 * @param id
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 * @param maxLinearSpeed is the maximal linear speed.
	 * @param maxLinearAcceleration is the maximal linear acceleration.
	 * @param maxAngularSpeed is the maximal angular speed.
	 * @param maxAngularAcceleration is the maximal angular acceleration.
	 * @param frustum the field-of-view associated to the body.
	 */
	public AgentBody(UUID id, Shape2f<?> shape, float maxLinearSpeed, float maxLinearAcceleration,
			float maxAngularSpeed, float maxAngularAcceleration, Frustum frustum) {
		super(id, shape, maxLinearSpeed, maxLinearAcceleration, maxAngularSpeed, maxAngularAcceleration);
		assert (frustum == null || Objects.equals(id, frustum.getOwner()));
		this.frustum = frustum;
		setType("BODY");
	}
	
	@Override
	public AgentBody clone() {
		AgentBody clone = (AgentBody) super.clone();
		clone.motionInfluence = null;
		clone.otherInfluences = new ArrayList<>();
		clone.perceptions = new ArrayList<>();
		return clone;
	}

	/** {@inheritDoc}
	 */
	@Override
	public String toString() {
		String label = LocalizedString.getString(getClass(), "BODY_OF", getID()); //$NON-NLS-1$;
		String name = getName();
		if (name != null && !name.isEmpty()) {
			return name + "(" + label + ")";
		}
		return label;
	}
	
	/** Replies the frustum associated to this body.
	 *
	 * @return the frustum.
	 */
	public Frustum getFrustum() {
		return this.frustum;
	}
	
	/** Invoked to send the given influence to the environment.
	 *
	 * @param influence the influence.
	 */
	public void influence(Influence influence) {
		if (influence != null) {
			if (influence instanceof MotionInfluence) {
				MotionInfluence mi = (MotionInfluence) influence;
				if (mi.getInfluencedObject() == null || mi.getInfluencedObject().equals(getID())) {
					switch(mi.getType()) {
					case KINEMATIC:
						influenceKinematic(mi.getLinearInfluence(), mi.getAngularInfluence());
						break;
					case STEERING:
						influenceSteering(mi.getLinearInfluence(), mi.getAngularInfluence());
						break;
					}
				} else {
					this.otherInfluences.add(mi);
				}
			} else {
				this.otherInfluences.add(influence);
			}
		}
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceKinematic(Vector2f linearInfluence, float angularInfluence) {
		Vector2f li;
		if (linearInfluence!=null) {
			li = new Vector2f(linearInfluence);
			float nSpeed = li.length();
			if (nSpeed>getMaxLinearSpeed()) {
				li.normalize();
				li.scale(getMaxLinearSpeed());
			}
		}
		else {
			li = new Vector2f();
		}
		float ai = MathUtil.clamp(angularInfluence, -getMaxAngularSpeed(), getMaxAngularSpeed());
		this.motionInfluence = new MotionInfluence(DynamicType.KINEMATIC, getID(), li, ai);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceSteering(Vector2f linearInfluence, float angularInfluence) {
		Vector2f li;
		if (linearInfluence!=null) {
			li = new Vector2f(linearInfluence);
			float nSpeed = li.length();
			if (nSpeed>getMaxLinearAcceleration()) {
				li.normalize();
				li.scale(getMaxLinearAcceleration());
			}
		}
		else {
			li = new Vector2f();
		}
		float ai = MathUtil.clamp(angularInfluence, -getMaxAngularAcceleration(), getMaxAngularAcceleration());
		this.motionInfluence = new MotionInfluence(DynamicType.STEERING, getID(), li, ai);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public void influenceKinematic(Vector2f linearInfluence) {
		influenceKinematic(linearInfluence, 0f);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public void influenceSteering(Vector2f linearInfluence) {
		influenceSteering(linearInfluence, 0f);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceKinematic(float angularInfluence) {
		influenceKinematic(null, angularInfluence);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceSteering(float angularInfluence) {
		influenceSteering(null, angularInfluence);
	}
	
	/** Replies all the perceived objects.
	 * 
	 * @return the perceived objects.
	 */
	public List<Percept> getPerceivedObjects() {
		return this.perceptions;
	}

	/** Replies the influence.
	 * 
	 * @return the influence.
	 */
	List<Influence> consumeOtherInfluences() {
		List<Influence> otherInfluences = this.otherInfluences;
		this.otherInfluences = new ArrayList<>();
		for(Influence i : otherInfluences) {
			if (i!=null) i.setEmitter(getID());
		}
		return otherInfluences;
	}
	
	/** Replies the influence.
	 * 
	 * @return the influence.
	 */
	MotionInfluence consumeMotionInfluence() {
		MotionInfluence mi = this.motionInfluence;
		this.motionInfluence = null;
		if (mi!=null) mi.setEmitter(getID());
		return mi;
	}

	/** Set the perceptions.
	 * 
	 * @param perceptions
	 */
	void setPerceptions(List<Percept> perceptions) {
		assert(perceptions!=null);
		this.perceptions = perceptions;
	}

}