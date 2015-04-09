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
package fr.utbm.info.vi51.labwork2.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;

import fr.utbm.info.vi51.framework.gui.AbstractFrameworkGUI;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.time.TimeManager;
import fr.utbm.info.vi51.framework.util.LocalizedString;
import fr.utbm.info.vi51.framework.util.Resources;
import fr.utbm.info.vi51.labwork2.MainProgram;

/** UI for the rabbits.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class GUI extends AbstractFrameworkGUI {

	private static final long serialVersionUID = 7958401897774129069L;

	/** Size of the direction vector (in pixels).
	 */
	private static final float DIRECTION_RADIUS = 30f;

	private static final Icon RABBIT_ICON;
	private static final Icon CARROT_ICON;
	private static final int ICON_WIDTH;
	private static final int ICON_HEIGHT;
	
	static {
		URL url = Resources.getResource(GUI.class, "rabbit.png"); //$NON-NLS-1$
		assert(url!=null);
		RABBIT_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "carrot.png"); //$NON-NLS-1$
		assert(url!=null);
		CARROT_ICON = new ImageIcon(url);
		ICON_WIDTH = RABBIT_ICON.getIconWidth();
		ICON_HEIGHT = RABBIT_ICON.getIconHeight();
	}
	
	private JCheckBox showIcons;
	private JCheckBox showPositions;
	
	/**
	 * @param worldWidth
	 * @param worldHeight
	 * @param timeManager
	 */
	public GUI(float worldWidth, float worldHeight, TimeManager timeManager) {
		super(
				LocalizedString.getString(MainProgram.class, "PROGRAM_NAME"), //$NON-NLS-1$
				worldWidth,
				worldHeight,
				Resources.getResource(GUI.class, "icon.png"), //$NON-NLS-1$
				timeManager);
	}
	
	@Override
	protected boolean isMouseCursorHidden() {
		// Hide the mouse cursor since a carrot icon is displayed at the same location.
		return true;
	}

	@Override
	protected JComponent createBottomPanel(JSlider speedSlider, JComponent closeButton, JComponent messageBox) {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(5, 1));
		this.showIcons = new JCheckBox(LocalizedString.getString(getClass(), "SHOW_ICONS")); //$NON-NLS-1$
		this.showIcons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		this.showIcons.setSelected(true);
		this.showPositions = new JCheckBox(LocalizedString.getString(getClass(), "SHOW_POSITIONS")); //$NON-NLS-1$
		this.showPositions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		bottomPanel.add(speedSlider);
		bottomPanel.add(this.showIcons);
		bottomPanel.add(this.showPositions);
		bottomPanel.add(closeButton);
		bottomPanel.add(messageBox);
		return bottomPanel;
	}
	
	/** Paint an icon and the orientation.
	 * 
	 * @param g2d the graphical context.
	 * @param icon the icon to draw.
	 * @param position the center position of the icon.
	 * @param orientation the orientation.
	 */
	protected void paintIcon(Graphics2D g2d, Icon icon, Point2f position, Vector2f orientation) {
		if (orientation != null) {
			g2d.draw(new Line2D.Float(
					position.getX(),
					position.getY(),
					position.getX() + orientation.getX() * DIRECTION_RADIUS * 1.5f,
					position.getY() + orientation.getY() * DIRECTION_RADIUS * 1.5f));
		}
		icon.paintIcon(this, g2d,
				(int) position.getX() - ICON_WIDTH / 2,
				(int) position.getY() - ICON_HEIGHT / 2);
	}
	
	@Override
	protected void paintAgentBody(Graphics2D g2d,
			Point2f positionOnScreen,
			Vector2f orientationOnScreen,
			Shape shape, Serializable type, String name,
			Point2f positionInMAS,
			Shape frustum) {
		Rectangle bounds = shape.getBounds();
		g2d.setColor(Color.BLUE);
		if (this.showIcons.isSelected()) {
			paintIcon(g2d, RABBIT_ICON, positionOnScreen, orientationOnScreen);
		}
		else {
			orientationOnScreen.scale(DIRECTION_RADIUS);
			g2d.draw(new Line2D.Float(
					positionOnScreen.getX(),
					positionOnScreen.getY(),
					positionOnScreen.getX() + orientationOnScreen.getX(),
					positionOnScreen.getY() + orientationOnScreen.getY()));
			g2d.fill(shape);
		}
		if (this.showPositions.isSelected()) {
			Font oldFont = g2d.getFont();
			Font f = oldFont.deriveFont(6);
			g2d.setColor(g2d.getBackground().darker());
			g2d.setFont(f);
			NumberFormat fmt = new DecimalFormat("#.#");
			g2d.drawString(
					"(" + fmt.format(positionInMAS.getX()) + ";" + fmt.format(positionInMAS.getY()) + ")",
					(float) bounds.getMaxX() + 2,
					(float) bounds.getMaxY() + 2);
			g2d.setFont(oldFont);
		}
	}

	@Override
	protected void paintSituatedObject(Graphics2D g2d,
			Point2f positionOnScreen,
			Vector2f orientationOnScreen,
			Shape shape, Serializable type, String name,
			Point2f positionInMAS) {
		Rectangle bounds = shape.getBounds();
		g2d.setColor(Color.GREEN);
		
		Icon icon = null;
		if ("TARGET".equals(type)) {
			icon = CARROT_ICON;
		}
			
		if (this.showIcons.isSelected() && icon != null) {
			paintIcon(g2d, icon, positionOnScreen, orientationOnScreen);
		} else {
			g2d.fill(shape);
			if (orientationOnScreen != null) {
				g2d.setColor(g2d.getBackground());
				Vector2f v = orientationOnScreen.operator_multiply(
						(float) Math.max(bounds.getWidth(), bounds.getHeight()));
				g2d.draw(new Line2D.Float(
						positionOnScreen.getX(),
						positionOnScreen.getY(),
						positionOnScreen.getX() + v.getX(),
						positionOnScreen.getY() + v.getY()));
			}
		}
		if (this.showPositions.isSelected()) {
			Font oldFont = g2d.getFont();
			Font f = oldFont.deriveFont(6);
			g2d.setColor(g2d.getBackground().darker());
			g2d.setFont(f);
			NumberFormat fmt = new DecimalFormat("#.#");
			g2d.drawString(
					"(" + fmt.format(positionInMAS.getX()) + ";" + fmt.format(positionInMAS.getY()) + ")",
					(float) bounds.getMaxX() + 2,
					(float) bounds.getMaxY() + 2);
			g2d.setFont(oldFont);
		}
	}

}