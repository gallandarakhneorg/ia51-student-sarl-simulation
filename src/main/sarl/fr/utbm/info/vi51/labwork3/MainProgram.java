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
package fr.utbm.info.vi51.labwork3;

import io.sarl.lang.core.Agent;
import fr.utbm.info.vi51.framework.FrameworkLauncher;
import fr.utbm.info.vi51.framework.environment.AgentBody;
import fr.utbm.info.vi51.framework.environment.DynamicType;
import fr.utbm.info.vi51.framework.gui.BehaviorTypeSelector;
import fr.utbm.info.vi51.framework.gui.FrameworkGUI;
import fr.utbm.info.vi51.framework.util.LocalizedString;
import fr.utbm.info.vi51.framework.util.SpawnMapping;
import fr.utbm.info.vi51.general.formation.BodyGuardFormationPattern;
import fr.utbm.info.vi51.general.formation.FormationAssignment;
import fr.utbm.info.vi51.general.formation.FormationPattern;
import fr.utbm.info.vi51.labwork3.environment.WorldModel;
import fr.utbm.info.vi51.labwork3.gui.GUI;
import fr.utbm.info.vi51.labwork3.agent.Follower;
import fr.utbm.info.vi51.labwork3.agent.Leader;

/**
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class MainProgram {

	private static float WORLD_SIZE_X = 700;
	private static float WORLD_SIZE_Y = 700;
	private static int SLOT_COUNT = 15;
	private static int FOLLOWER_COUNT = 10;

	/** Main program.
	 * 
	 * @param argv are the command line arguments.
	 * @throws Exception 
	 */	
	public static void main(String[] argv) throws Exception {

		System.out.println(LocalizedString.getString(MainProgram.class, "INTRO_MESSAGE")); //$NON-NLS-1$

		DynamicType type = BehaviorTypeSelector.open();
		if (type == null) {
			System.exit(0);
		}

		FormationAssignment formationAssignment = new FormationAssignment();
		FormationPattern formation = new BodyGuardFormationPattern(SLOT_COUNT);

		WorldModel environment = new WorldModel(WORLD_SIZE_X, WORLD_SIZE_Y);

		environment.createLeader();
		for (int i = 0; i < FOLLOWER_COUNT; ++i) {
			environment.createFollower();
		}

		FrameworkGUI gui = new GUI(WORLD_SIZE_X, WORLD_SIZE_Y, environment.getTimeManager(), formation);

		FrameworkLauncher.launchSimulation(
				environment,
				new ApplicationMapping(),
				type,
				gui,
				formation,
				formationAssignment);
	}

	/**
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private static class ApplicationMapping extends SpawnMapping {

		@Override
		public Class<? extends Agent> getAgentTypeForBody(AgentBody body) {
			Object type = body.getType();
			if ("LEADER".equals(type)) {
				return Leader.class;
			}
			if ("FOLLOWER".equals(type)) {
				return Follower.class;
			}
			throw new IllegalArgumentException();
		}

	}

}