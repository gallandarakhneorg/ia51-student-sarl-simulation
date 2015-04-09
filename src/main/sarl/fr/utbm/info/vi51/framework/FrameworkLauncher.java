/* 
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package fr.utbm.info.vi51.framework;

import io.janusproject.Boot;
import io.janusproject.JanusConfig;
import io.janusproject.kernel.Kernel;
import io.janusproject.services.contextspace.ContextSpaceService;
import io.janusproject.util.LoggerCreator;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.EventSpace;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import fr.utbm.info.vi51.framework.environment.DynamicType;
import fr.utbm.info.vi51.framework.environment.Environment;
import fr.utbm.info.vi51.framework.environment.StopSimulation;
import fr.utbm.info.vi51.framework.gui.FrameworkGUI;
import fr.utbm.info.vi51.framework.util.SpawnMapping;

/**
 * Launcher of the simulation framework.
 *
 * This launcher needs the {@link http://www.janusproject.io Janus platform}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class FrameworkLauncher {
	
	private static UUID environmentInteractionSpace = null;
	private static Environment environmentSingleton = null;
	private static FrameworkGUI uiSingleton = null;
	private static Kernel kernel = null;
	private static final AtomicBoolean canStop = new AtomicBoolean(false);

	/** Launch the simulation and its associated UI.
	 * 
	 * @param environment is the environment to use.
	 * @param spawnMapping the mapping from body to agent type.
	 * @param behaviorType the type of the agent behaviors.
	 * @param gui is the GUI to launch.
	 * @param parameters the parameters to give to every agent.
	 * @throws Exception if the Janus platform cannot be started.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void launchSimulation(Environment environment, SpawnMapping spawnMapping,
			DynamicType behaviorType, FrameworkGUI gui, Object... parameters) throws Exception {
		Boot.setOffline(true);
		Boot.setVerboseLevel(LoggerCreator.toInt(Level.INFO));
		Boot.showJanusLogo();
		
		environmentInteractionSpace = UUID.randomUUID();
		
		uiSingleton = gui;
		environmentSingleton = environment;
		
		Object[] params = new Object[4 + parameters.length];
		params[0] = environmentSingleton;
		params[1] = environmentInteractionSpace;
		params[2] = spawnMapping;
		params[3] = behaviorType;
		System.arraycopy(parameters, 0, params, 4, parameters.length);
		
		kernel = Boot.startJanus(
				(Class) null,
				SimulatorAgent.class,
				params);
		if (uiSingleton != null) {
			if (environmentSingleton != null) {
				environmentSingleton.addEnvironmentListener(uiSingleton);
			}
			uiSingleton.setVisible(true);
		}
		canStop.set(true);
	}
	
	/** Stop the simulation right now.
	 */
	public static void stopSimulation() {
		if (!canStop.get()) {
			throw new IllegalStateException("You must call startSimulation() before stopSimulation().");
		}
		AgentContext context = kernel.getService(ContextSpaceService.class).getContext(
				UUID.fromString(JanusConfig.DEFAULT_CONTEXT_ID_VALUE));
		EventSpace space = context.getDefaultSpace();
		Address adr = new Address(space.getID(), UUID.randomUUID());
		StopSimulation sSimulation = new StopSimulation(adr);
		context.getDefaultSpace().emit(sSimulation);
		if (uiSingleton!=null) {
			uiSingleton.setVisible(false);
			if (environmentSingleton != null) {
				environmentSingleton.removeEnvironmentListener(uiSingleton);
			}
			uiSingleton.dispose();
		}
		environmentSingleton = null;
		environmentInteractionSpace = null;
		uiSingleton = null;
		kernel = null;
	}
	
}