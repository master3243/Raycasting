/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.map;

import java.util.ArrayList;

import raycasting.Util;
import raycasting.Direction;
import raycasting.WallProperties;
import raycasting.World;
import raycasting.entities.Player;
import raycasting.entities.Wall;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Map {
	
	public Map(int mapNumber){
		switch(mapNumber){
		case 1:
			MapData.generateMap1(this);
			break;
		case 2:
			MapData.generateMap2(this);
			break;
		case 3:
			MapData.generateMap3(this);
			break;
		}
	}
	
	public ArrayList<Wall> walls = new ArrayList<>();
	
	public WallProperties[] generateWallPropertiesArray(Player player) {

		WallProperties[] result = new WallProperties[World.width_resolution];
		double degreeBetweenRays = player.degreeBetweenRays;
		double playerDirection = player.getLookingDirection().getDirectionNumber();
		Point2D playerPosition = player.getPoint();
		double rightMostPixel = playerDirection - (World.pov / 2);

		for (int i = 0; i < World.width_resolution; i++) {
			Direction rayDirection = new Direction(rightMostPixel + (degreeBetweenRays * i));
			
			double endXAxisOfRay = playerPosition.getX() + 
					World.draw_distance * Math.cos(Math.toRadians(rayDirection.getDirectionNumber()));
			double endYAxisOfRay = playerPosition.getY() + 
					World.draw_distance * Math.sin(Math.toRadians(rayDirection.getDirectionNumber()));
			Point2D endPointOfRay = new Point2D.Double(endXAxisOfRay, endYAxisOfRay);
			Line2D rayLine = new Line2D.Double(playerPosition, endPointOfRay);
			
			Wall closestWall = nearestWall(rayLine);
			if(closestWall == null)	
				continue;
			double wallDistance = Util.getDistance(rayLine, closestWall);
			Color wallColor = closestWall.getColor();
			result[i] = new WallProperties(wallDistance, wallColor);	
		}
		return result;
	}

	public Wall nearestWall(Line2D rayLine) {
		ArrayList<Wall> collidingWalls = getCollidingWalls(rayLine);
		double min = Integer.MAX_VALUE;
		double distance;
		Wall result = null;
		for(Wall wall : collidingWalls){
			distance = Util.getDistance(rayLine, wall);
			if(distance < min){
				min = distance;
				result = wall;
			}
		}
		return min >= World.draw_distance ? null : result;
	}

	public ArrayList<Wall> getCollidingWalls(Line2D rayLine) {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : walls) {
			if (wall.intersectsLine(rayLine))
				result.add(wall);
		}
		return result;
	}
	
	public boolean collidesWithWall (Line2D line){
		for (Wall wall : walls) {
			if (wall.intersectsLine(line))
				return true;
		}
		return false;
	}

	public boolean canMove(Point2D from, Point2D to) {
		Line2D changeInPosition = new Line2D.Double(from, to);
		boolean collidesWithWall = collidesWithWall(changeInPosition);
		return !collidesWithWall;
	}

	

}
