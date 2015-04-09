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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.utbm.info.vi51.framework.FrameworkLauncher;
import fr.utbm.info.vi51.framework.environment.AgentBody;
import fr.utbm.info.vi51.framework.environment.EnvironmentEvent;
import fr.utbm.info.vi51.framework.environment.Frustum;
import fr.utbm.info.vi51.framework.environment.MobileObject;
import fr.utbm.info.vi51.framework.environment.SituatedObject;
import fr.utbm.info.vi51.framework.environment.WorldModelState;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.math.Shape2f;
import fr.utbm.info.vi51.framework.math.Vector2f;
import fr.utbm.info.vi51.framework.time.TimeManager;
import fr.utbm.info.vi51.framework.util.LocalizedString;

/** Abstract implementation of a GUI for the agent framework.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractFrameworkGUI extends JFrame implements FrameworkGUI {

	private static final long serialVersionUID = -2393638827023549444L;

	private static final int WAITING_FACTOR = 2;
	private static final int WAITING_MIN = 0;
	private static final int WAITING_MAX = 20;
	
	private final World world;
	private final float worldWidth;
	private final float worldHeight;
	private final TimeManager timeManager;

	private WorldModelState lastState = null;
	private WorldModelStateProvider environment = null;

	private final JScrollPane scroll;
	private final JLabel messageBox;
	private final JSlider speedSlider;

	private static int time2slider(float delay) {
		return (int)(WAITING_MAX - WAITING_MIN - delay / WAITING_FACTOR);		
	}

	private static float slider2time(int value) {
		return (WAITING_MAX - WAITING_MIN - value) * WAITING_FACTOR;
	}

	/**
	 * @param title
	 * @param worldWidth
	 * @param worldHeight
	 * @param frameIcon
	 * @param timeManager
	 */
	public AbstractFrameworkGUI(String title, float worldWidth, float worldHeight, URL frameIcon, TimeManager timeManager) {
		setTitle(title);
		ImageIcon icon = new ImageIcon(frameIcon);
		setIconImage(icon.getImage());
		Container content = getContentPane();

		content.setLayout(new BorderLayout());

		this.world = new World(isMouseCursorHidden());
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.timeManager = timeManager;

		scroll = new JScrollPane(this.world);
		content.add(BorderLayout.CENTER,scroll);

		JButton closeBt = new JButton(LocalizedString.getString(getClass(), "QUIT")); //$NON-NLS-1$
		closeBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});
		
		this.speedSlider = new JSlider(JSlider.HORIZONTAL);
		this.speedSlider.setToolTipText(LocalizedString.getString(getClass(), "SLIDER_LABEL"));
		this.speedSlider.setMinimum(WAITING_MIN);
		this.speedSlider.setMaximum(WAITING_MAX);
		this.speedSlider.setValue(time2slider(timeManager.getSimulationDelay()));
		this.speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = AbstractFrameworkGUI.this.speedSlider.getValue();
				AbstractFrameworkGUI.this.timeManager.setSimulationDelay(slider2time(value));
			}
		});

		this.messageBox = new JLabel();

		content.add(BorderLayout.SOUTH, createBottomPanel(this.speedSlider, closeBt, this.messageBox));

		this.world.setPreferredSize(new Dimension((int)worldWidth, (int)worldHeight));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});

		this.world.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				setMouseTargetOnScreen(new Point2f(e.getX(), e.getY()));
			}
			public void mouseMoved(MouseEvent e) {
				setMouseTargetOnScreen(new Point2f(e.getX(), e.getY()));
			}			
		});

		this.world.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				//
			}
			public void mouseEntered(MouseEvent e) {
				setMouseTargetOnScreen(new Point2f(e.getX(), e.getY()));
			}
			public void mouseExited(MouseEvent e) {
				setMouseTargetOnScreen(null);
			}
			public void mousePressed(MouseEvent e) {
				//
			}
			public void mouseReleased(MouseEvent e) {
				//
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		pack();

		Dimension d = getSize();
		setLocation(-d.width/2, -d.height/2);
		setLocationRelativeTo(null);
	}
	
	/** Replies if the mouse cursor must be hidden or not.
	 * 
	 * @return <code>true</code> to hide the cursor, <code>false</code> to show.
	 */
	protected abstract boolean isMouseCursorHidden();
	
	/** Change the message in the dedicated box.
	 *
	 * @param message - the message.
	 */
	protected void setMessage(String message) {
		this.messageBox.setText(message);
	}

	/** Replies the message in the dedicated box.
	 *
	 * @return the message (could be <code>null</code>).
	 */
	protected String getMessage() {
		return this.messageBox.getText();
	}

	/** Create the bottom panel.
	 * 
	 * @param speedSlider the slider for changing the simulation speed.
	 * @param closeButton the close button.
	 * @param messageBox the box for messages.
	 * @return the bottom panel.
	 */
	protected JComponent createBottomPanel(JSlider speedSlider, JComponent closeButton, JComponent messageBox) {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(3, 1));
		bottomPanel.add(speedSlider);
		bottomPanel.add(closeButton);
		bottomPanel.add(messageBox);
		return bottomPanel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void environmentChanged(EnvironmentEvent event) {
		synchronized(getTreeLock()) {
			if (this.environment==null) {
				this.environment = event.getStateProvider();
			}
			this.lastState = this.environment.getState();
			repaint();
		}
	}

	/**
	 * @param screenPosition the position of the target on the screen
	 */
	protected void setMouseTargetOnScreen(Point2f screenPosition) {
		synchronized(getTreeLock()) {
			Point2f masPosition = screen2mas(screenPosition);
			if (this.environment!=null)
				this.environment.setMouseTarget(masPosition);
			if (masPosition == null) {
				setMessage(null);
			} else {
				setMessage(
						LocalizedString.getString(getClass(), "TARGET_POSITION", masPosition.getX(), masPosition.getY()));
			}
		}
	}

	/**
	 * @param masPosition the position of the target in the MAS
	 */
	protected void setMouseTargetInMAS(Point2f masPosition) {
		synchronized(getTreeLock()) {
			if (this.environment!=null)
				this.environment.setMouseTarget(masPosition);
			if (masPosition == null) {
				setMessage(null);
			} else {
				setMessage(
						LocalizedString.getString(getClass(), "TARGET_POSITION", masPosition.getX(), masPosition.getY()));
			}
		}
	}

	/** Replies the last environment state.
	 * 
	 * @return the last environment state.
	 */
	protected WorldModelState getLastState() {
		return this.lastState;
	}

	/** Convert the coordinates in the MAS into the equivalent coordinates on the screen.
	 *
	 * @param p the coordinates
	 * @return the coordinates on the screen.
	 */
	protected Point2f mas2screen(Point2f p) {
		if (p == null) {
			return null;
		}
		return new Point2f(p.getX(), this.worldHeight - p.getY());
	}

	/** Convert the vector in the MAS into the equivalent vector on the screen.
	 *
	 * @param v the vector
	 * @return the vector on the screen.
	 */
	protected Vector2f mas2screen(Vector2f p) {
		if (p == null) {
			return null;
		}
		return new Vector2f(p.getX(), -p.getY(), true);
	}

	/** Convert the size in the MAS into the equivalent size on the screen.
	 *
	 * @param size the size
	 * @return the size on the screen.
	 */
	protected float mas2screen(float size) {
		// No scaling/zooming
		return size;
	}

	/** Convert the point from the screen coordinate to the MAS coordinate.
	 *
	 * @param point the point on the screen.
	 * @return the point in the MAS
	 */
	protected Point2f screen2mas(Point2f point) {
		if (point == null) {
			return null;
		}
		return new Point2f(
				point.getX(),
				this.worldHeight - point.getY());
	}

	/** Convert the given MAS shape to the equivalent AWT shape.
	 *
	 * @param shape the MAS shape
	 * @return the AWT shape.
	 */
	protected Shape mas2screen(Shape2f<?> shape) {
		if (shape == null) {
			return null;
		}
		if (shape instanceof Circle2f) {
			Circle2f c = (Circle2f) shape;
			Point2f p = mas2screen(c.getCenter());
			float radius = mas2screen(c.getRadius());
			return new Ellipse2D.Float(
					p.getX() - radius,
					p.getY() - radius,
					2f * c.getRadius(),
					2f * c.getRadius());
		}
		if (shape instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) shape;
			Point2f l = mas2screen(r.getLower());
			Point2f u = mas2screen(r.getUpper());
			return new Rectangle2D.Float(
					(float) Math.min(l.getX(), u.getX()),
					(float) Math.min(l.getY(), u.getY()),
					mas2screen(r.getWidth()),
					mas2screen(r.getHeight()));
		}
		throw new IllegalArgumentException();
	}

	/** Paint something in the world.
	 * 
	 * @param g2d the graphical context in which is must be drawn.
	 */
	protected void paintWorld(Graphics2D g2d) {
		//
	}

	/** Paint an agent body.
	 * 
	 * @param g2d the graphical context in which is must be drawn.
	 * @param positionOnScreen the position of the object on the screen.
	 * @param orientationOnScreen the orientation of the body on the screen.
	 * @param shape the shape of the body on the screen.
	 * @param type the type of the body.
	 * @param name the name of the body (could be <code>null</code>).
	 * @param positionInMas the position of the body in the MAS.
	 * @param frustum the frustum.
	 */
	protected abstract void paintAgentBody(Graphics2D g2d,
			Point2f positionOnScreen,
			Vector2f orientationOnScreen,
			Shape shape,
			Serializable type, String name,
			Point2f positionInMas,
			Shape frustum);

	/** Paint a situated object.
	 * 
	 * @param g2d the graphical context in which is must be drawn.
	 * @param positionOnScreen the position of the object on the screen.
	 * @param orientationOnScreen the orientation of the body on the screen.
	 * @param shape the shape of the body on the screen.
	 * @param type the type of the object.
	 * @param name the name of the body (could be <code>null</code>).
	 * @param positionInMas the position of the body in the MAS.
	 */
	protected abstract void paintSituatedObject(Graphics2D g2d,
			Point2f positionOnScreen,
			Vector2f orientationOnScreen,
			Shape shape,
			Serializable type, String name,
			Point2f positionInMas);

	private void drawAxis(Graphics2D g2d) {
		Dimension dim = this.scroll.getViewport().getViewSize();
		g2d.setColor(g2d.getBackground().darker());
		//
		GeneralPath p = new GeneralPath();
		p.moveTo(19, (float) dim.getHeight() - 5);
		p.lineTo(22, (float) dim.getHeight() - 2);
		p.lineTo(2, (float) dim.getHeight() - 2);
		p.lineTo(2, (float) dim.getHeight() - 22);
		p.lineTo(5, (float) dim.getHeight() - 19);
		//
		g2d.draw(p);
		//
		Font oldFont = g2d.getFont();
		Font f = oldFont.deriveFont(6);
		g2d.setFont(f);
		g2d.drawString("x", 24, (float) dim.getHeight() - 1);
		g2d.drawString("y", 1, (float) dim.getHeight() - 25);
		g2d.setFont(oldFont);
	}

	/**
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private class World extends JPanel {

		private static final long serialVersionUID = 8516008479029079959L;

		/**
		 * @param hideMouseCursor indicates if the mouse cursor must be hidden.
		 */
		public World(boolean hideMouseCursor) {
			if (hideMouseCursor) {
				setCursor( getToolkit().createCustomCursor( new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ), new Point(), null ) );
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;

			Color background = getBackground().darker();
			g2d.setColor(background);
			g2d.draw(new Rectangle2D.Float(
					0, 0,
					mas2screen(AbstractFrameworkGUI.this.worldWidth),
					mas2screen(AbstractFrameworkGUI.this.worldHeight)));

			Dimension currentDim = getPreferredSize();

			paintWorld(g2d);

			drawObjects(g2d, currentDim);

			drawAxis(g2d);
		}

		private void drawObjects(Graphics2D g2d, Dimension currentDim) {
			WorldModelState state = getLastState();
			if (state!=null) {
				for (SituatedObject o : state.getObjects()) {
					Point2f positionOnScreen = mas2screen(o.getPosition());
					Vector2f directionOnScreen;
					if (o instanceof MobileObject) {
						directionOnScreen = mas2screen(((MobileObject) o).getDirection());
					} else {
						directionOnScreen = null;
					}
					Shape awtShape = mas2screen(o.getShape());
					if (o instanceof AgentBody) {
						Frustum frustum = ((AgentBody) o).getFrustum();
						Shape2f<?> frustumShape = (frustum == null) ? null : frustum.toShape(
								o.getPosition(), ((AgentBody) o).getDirection());
						paintAgentBody(
								g2d,
								positionOnScreen,
								directionOnScreen,
								awtShape,
								o.getType(),
								o.getName(),
								o.getPosition(),
								mas2screen(frustumShape));
					} else {
						paintSituatedObject(
								g2d,
								positionOnScreen,
								directionOnScreen,
								awtShape,
								o.getType(),
								o.getName(),
								o.getPosition());
					}
				}
			}
		}
	}

}