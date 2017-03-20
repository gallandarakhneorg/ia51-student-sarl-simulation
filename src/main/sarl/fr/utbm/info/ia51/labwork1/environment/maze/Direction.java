/* 
 * $Id$
 * 
 * Copyright (c) 2015-17 Stephane GALLAND.
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
package fr.utbm.info.ia51.labwork1.environment.maze;

import fr.utbm.info.ia51.framework.math.Vector2i;


/**
 * Direction in a maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum Direction {

	NORTH {

		@Override
		public Direction opposite() {
			return SOUTH;
		}

		@Override
		public Direction left() {
			return WEST;
		}

		@Override
		public Direction right() {
			return EAST;
		}
			
		@Override
		public Vector2i toVector() {
			return NORTH_VECTOR;
		}		

	},
	
	EAST {

		@Override
		public Direction opposite() {
			return WEST;
		}

		@Override
		public Direction left() {
			return NORTH;
		}

		@Override
		public Direction right() {
			return SOUTH;
		}
			
		@Override
		public Vector2i toVector() {
			return EAST_VECTOR;
		}		

	},
	
	SOUTH {

		@Override
		public Direction opposite() {
			return NORTH;
		}

		@Override
		public Direction left() {
			return EAST;
		}

		@Override
		public Direction right() {
			return WEST;
		}
		
		@Override
		public Vector2i toVector() {
			return SOUTH_VECTOR;
		}		
		
	},
	
	WEST {
		
		@Override
		public Direction opposite() {
			return EAST;
		}

		@Override
		public Direction left() {
			return SOUTH;
		}

		@Override
		public Direction right() {
			return NORTH;
		}
		
		@Override
		public Vector2i toVector() {
			return WEST_VECTOR;
		}		
		
	};
	
	public static final Vector2i NORTH_VECTOR = new Vector2i(0, -1);
	public static final Vector2i EAST_VECTOR = new Vector2i(1, 0);
	public static final Vector2i SOUTH_VECTOR = new Vector2i(0, 1);
	public static final Vector2i WEST_VECTOR = new Vector2i(-1, 0);
	
	public abstract Direction opposite();
	
	public abstract Direction left();

	public abstract Direction right();
	
	public abstract Vector2i toVector();

	public static Direction random() {
		return values()[(int) (Math.random() * 4)];
	}
	
	public static Direction fromVector(Vector2i v) {
		if (v.getX() > 0) {
			return EAST;
		}
		if (v.getX() < 0) {
			return WEST;
		}
		if (v.getY() > 0) {
			return SOUTH;
		}
		return NORTH;
	}

}
