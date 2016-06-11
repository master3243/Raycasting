/*
 * Abdulrahman Alabdulkareem 782435
 * May 23, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	
	public final Point2D location;
	public final double wallLength;
	public final Color color;
	
	public Entity(Point2D loc, int size, Color col){
		location = loc;
		wallLength = size;
		color = col;
	}
	
	public boolean isPlayerTouching(int playerNumber){
		Rectangle2D entityLoc = new Rectangle2D.Double(location.getX()-wallLength/2,
													location.getY()-wallLength/2,
													wallLength,
													wallLength);
		Player p = Player.players.get(playerNumber);
		Point2D playerLoc = p.getPoint();
		return entityLoc.contains(playerLoc);
	}
	
	public abstract void playerTouch(int playerNumber);
	
	public abstract double getWaitTime();
	
	public abstract String getProperties();
	
}
