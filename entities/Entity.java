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
	
	public void setLocation(Point2D newLocation){
		location.setLocation(newLocation);
	}
	
	public boolean isPlayerTouching(int playerNumber){
		//multiply by sqrt 2 so that the detection box is just large enough so-
		//that if it is 45 degrees tilted the player still wont be able to clip inside
		double SQRT_2 = 1.42;
		double largerWallLength = SQRT_2 * wallLength;
		Rectangle2D entityLoc = new Rectangle2D.Double(location.getX()-largerWallLength/2,
													location.getY()-largerWallLength/2,
													largerWallLength,
													largerWallLength);
		Player p = Player.players.get(playerNumber);
		Point2D playerLoc = p.getPoint();
		return entityLoc.contains(playerLoc);
	}
	
	public abstract void playerTouch(int playerNumber);
	
	public abstract double getWaitTime();
	
	public abstract String getProperties();

}
