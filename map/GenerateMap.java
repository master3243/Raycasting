/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.map;

import java.util.ArrayList;

import raycasting.Util;
import raycasting.old.Direction;
import raycasting.old.Player;
import raycasting.old.Wall;
import raycasting.old.WallProperties;
import raycasting.old.World;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GenerateMap {
	
	public static void generateMap(GenerateMap map, int mapNumber){
		switch(mapNumber){
		case 1:
			generateMap1(map);
			break;
		case 2:
			generateMap2(map);
			break;
		case 3:
			generateMap3(map);
			break;
		}
	}
	
	public static void generateMap1(GenerateMap map) {
		map.generateSquare(0, 0, 1000, -1000, new Color(50, 50, 0));
		map.walls.add(new Wall(5, -30, 20, -30));
	}
	
	public static void generateMap2(GenerateMap map){
		map.generateSquare(0, 0, 1000, -1000);
		map.generateSquare(10, -10, 20, -20, new Color(100, 0, 0));
		map.generateSquare(10, -30, 20, -40, new Color(0, 100, 0));
		map.generateSquare(20, -10, 30, -20, new Color(0, 100, 0));
		map.generateSquare(10, -50, 20, -60, new Color(0, 0, 100));
		map.generateSquare(10, -70, 20, -80, new Color(100, 100, 0));		
	}
	
	public static void generateMap3(GenerateMap map){
		map.generateSquare(0, 0, 1000, -1000);
		map.generateSquare(10, -10, 20, -20, new Color(100, 0, 0));
		map.generateSquare(10, -30, 20, -40, new Color(0, 100, 0));
		map.generateSquare(20, -10, 30, -20, new Color(0, 100, 0));
		map.generateSquare(10, -50, 20, -60, new Color(0, 0, 100));
		map.generateSquare(10, -70, 20, -80, new Color(100, 100, 0));		
		map.generateSquare(50, -10, 80, -40, new Color(0, 100, 100));
		map.generateSquare(50, -10, 80, -40, new Color(0, 100, 100));
		
		Color color = new Color(30, 70, 60);
		map.walls.add(new Wall(30, -45, 31, -50, color));
		map.walls.add(new Wall(30, -45, 31, -40, color.darker()));
		map.walls.add(new Wall(31, -40, 45, -45, color));
		map.walls.add(new Wall(31, -50, 45, -45, color.darker()));
	}
	
	public ArrayList<Wall> walls = new ArrayList<>();

	public void generateSquare(int x1, int y1, int x2, int y2){
		generateSquare(x1, y1, x2, y2, new Color(0, 0, 0));
	}
	
	public void generateSquare(int x1, int y1, int x2, int y2, Color color){
		walls.add(new Wall(x1, y1, x2, y1, color));
		walls.add(new Wall(x2, y1, x2, y2, color.darker()));
		walls.add(new Wall(x2, y2, x1, y2, color));
		walls.add(new Wall(x1, y2, x1, y1, color.darker()));
	}

	public double[] generateDistanceArray(Player player) {

		double[] result = new double[World.width_resolution];
		double fraction = World.pov * 1.0 / World.width_resolution;
		double playerDirection = player.getLookingDirection().getDirectionNumber();
		Point2D playerPosition = player.getPoint();
		double rightMostPixel = playerDirection - (World.pov / 2);

		for (int i = 0; i < World.width_resolution; i++) {
			Direction rayDirection = new Direction(rightMostPixel + (fraction * i));
			Line2D rayLine = new Line2D.Double(playerPosition.getX(), playerPosition.getY(),
					playerPosition.getX() + World.draw_distance * Math.cos(Math.toRadians(rayDirection.getDirectionNumber())),
					playerPosition.getY() + World.draw_distance * Math.sin(Math.toRadians(rayDirection.getDirectionNumber())));
			Wall closestWall = nearestWall(rayLine);
			if(closestWall == null)
				continue;
			result[i] = getDistance(rayLine, closestWall);	
		}
		return result;
	}
	
	public WallProperties[] generateWallPropertiesArray(Player player) {

		WallProperties[] result = new WallProperties[World.width_resolution];
		double fraction = World.pov * 1.0 / World.width_resolution;
		double playerDirection = player.getLookingDirection().getDirectionNumber();
		Point2D playerPosition = player.getPoint();
		double rightMostPixel = playerDirection - (World.pov / 2);

		for (int i = 0; i < World.width_resolution; i++) {
			Direction rayDirection = new Direction(rightMostPixel + (fraction * i));
			Line2D rayLine = new Line2D.Double(playerPosition.getX(), playerPosition.getY(),
					playerPosition.getX() + World.draw_distance * Math.cos(Math.toRadians(rayDirection.getDirectionNumber())),
					playerPosition.getY() + World.draw_distance * Math.sin(Math.toRadians(rayDirection.getDirectionNumber())));
			Wall closestWall = nearestWall(rayLine);
			if(closestWall == null)	
				continue;
			
			result[i] = new WallProperties(getDistance(rayLine, closestWall), closestWall.getColor());	
		}
		return result;
	}

	public Wall nearestWall(Line2D rayLine) {
		ArrayList<Wall> collidingWalls = getCollidingWalls(rayLine);
		double min = Integer.MAX_VALUE;
		double distance;
		Wall result = null;
		for(Wall wall : collidingWalls){
			distance = getDistance(rayLine, wall);
			if(distance < min){
				min = distance;
				result = wall;
			}
		}
		return min >= World.draw_distance ? null : result;
	}
	
	public double getDistance(Line2D rayLine, Wall wall){
		Point2D intersection = Util.getLineLineIntersection(wall.getLine(), rayLine);
		
		if(intersection == null)
			return World.draw_distance; // distance is set to be draw_distance units far -> wall not drawn
		
		double distance = rayLine.getP1().distance(intersection);
		return distance;
	}

	public ArrayList<Wall> getCollidingWalls(Line2D rayLine) {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : walls) {
			if (wall.intersectsLine(rayLine))
				result.add(wall);
		}
		return result;
	}

	

}
