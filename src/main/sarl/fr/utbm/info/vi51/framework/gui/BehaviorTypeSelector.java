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
package fr.utbm.info.vi51.framework.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.arakhne.afc.vmutil.Resources;

import fr.utbm.info.vi51.framework.environment.DynamicType;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/**
 * Dialog box for selecting the type of a behavior.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class BehaviorTypeSelector extends JDialog {

	private static final long serialVersionUID = -1718324646190007840L;
	
	private DynamicType behaviorType = null;

	/** Open the dialog box for selecting a type of behavior.
	 * 
	 * @return the selected type or <code>null</code> if the dialog box was manually closed.
	 */
	public static DynamicType open() {
		BehaviorTypeSelector selector = new BehaviorTypeSelector();
		selector.setVisible(true);
		DynamicType type = selector.getSelectedBehaviorType();
		selector.dispose();
		return type;
	}
	
	protected BehaviorTypeSelector() {
		URL imageUrl;
		Icon icon;
		String label;
		
		setTitle(LocalizedString.getString(BehaviorTypeSelector.class, "TITLE"));
		setModal(true);
		
		setLayout(new GridLayout(2, 1));

		imageUrl = Resources.getResource(BehaviorTypeSelector.class, "kinematic.png");
		icon = new ImageIcon(imageUrl);
		label = LocalizedString.getString(BehaviorTypeSelector.class, "KINEMATIC_BUTTON");
		JButton kinematicButton = new JButton(label, icon);
		kinematicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(BehaviorTypeSelector.this) {
					BehaviorTypeSelector.this.behaviorType = DynamicType.KINEMATIC;
				}
				BehaviorTypeSelector.this.setVisible(false);
			}
		});
		add(kinematicButton);

		imageUrl = Resources.getResource(BehaviorTypeSelector.class, "steering.png");
		icon = new ImageIcon(imageUrl);
		label = LocalizedString.getString(BehaviorTypeSelector.class, "STEERING_BUTTON");
		JButton steeringButton = new JButton(label, icon);
		steeringButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(BehaviorTypeSelector.this) {
					BehaviorTypeSelector.this.behaviorType = DynamicType.STEERING;
				}
				BehaviorTypeSelector.this.setVisible(false);
			}
		});
		add(steeringButton);
		
		pack();
		Dimension d = getSize();
		setLocation(-d.width/2, -d.height/2);
		setLocationRelativeTo(null);
	}

	/** Replies the selected behavior type.
	 *
	 * @return the selected behavior type. It could be <code>null</code> if the user
	 * has close the dialog box without selecting a type.
	 */
	public synchronized DynamicType getSelectedBehaviorType()  {
		return this.behaviorType;
	}

}