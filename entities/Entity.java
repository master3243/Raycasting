/*
 * Abdulrahman Alabdulkareem 782435
 * May 23, 2016
 */
package raycasting.entities;

import java.awt.geom.Point2D;

public abstract class Entity {
	
	public final Point2D location;
	public final double wallLength;
	
	public Entity(Point2D loc, int size){
		location = loc;
		wallLength = size;
	}
	
}
