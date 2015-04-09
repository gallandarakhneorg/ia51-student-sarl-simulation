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
package fr.utbm.info.vi51.general.tree.tree2d;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

/**
 * Defines the type of a separation line: vertical or horizontal.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public enum SeparationLine {

	/** The zone is vertically divided into two sub-zones.
	 */
	VERTICAL {

		@Override
		public SeparationLine turn() {
			return SeparationLine.HORIZONTAL;
		}

		@Override
		public Rectangle2f getLowerOrLeftBounds(Rectangle2f bounds) {
			Point2f lower = bounds.getLower();
			Point2f center = bounds.getCenter();
			Point2f upper = bounds.getUpper();
			return new Rectangle2f(
					lower.getX(), lower.getY(),
					center.getX(), upper.getY());
		}

		@Override
		public Rectangle2f getUpperOrRightBounds(Rectangle2f bounds) {
			Point2f lower = bounds.getLower();
			Point2f center = bounds.getCenter();
			Point2f upper = bounds.getUpper();
			return new Rectangle2f(
					center.getX(), lower.getY(),
					upper.getX(), upper.getY());
		}

	},
	
	/** The zone is horizontally divided into two sub-zones.
	 */
	HORIZONTAL {

		@Override
		public SeparationLine turn() {
			return SeparationLine.VERTICAL;
		}

		@Override
		public Rectangle2f getLowerOrLeftBounds(Rectangle2f bounds) {
			Point2f lower = bounds.getLower();
			Point2f center = bounds.getCenter();
			Point2f upper = bounds.getUpper();
			return new Rectangle2f(
					lower.getX(), lower.getY(),
					upper.getX(), center.getY());
		}

		@Override
		public Rectangle2f getUpperOrRightBounds(Rectangle2f bounds) {
			Point2f lower = bounds.getLower();
			Point2f center = bounds.getCenter();
			Point2f upper = bounds.getUpper();
			return new Rectangle2f(
					lower.getX(), center.getY(),
					upper.getX(), upper.getY());
		}

	};
	
	/** Replies the separation line after turning the current separation line.
	 *
	 * @return the separation line resulting of the turn.
	 */
	public abstract SeparationLine turn();

	/** Replies the lower or left part of the given bounds.
	 * 
	 * @param bounds the zone bounds.
	 * @return the sub-zone bounds.
	 */
	public abstract Rectangle2f getLowerOrLeftBounds(Rectangle2f bounds);
	
	/** Replies the upper or right part of the given bounds.
	 * 
	 * @param bounds the zone bounds.
	 * @return the sub-zone bounds.
	 */
	public abstract Rectangle2f getUpperOrRightBounds(Rectangle2f bounds);

}
