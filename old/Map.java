/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.old;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Map {
	
	public static void generateMap(Map map, int mapNumber){
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
	
	public static void generateMap1(Map map) {
		map.generateSquare(0, 0, 1000, -1000, new Color(50, 50, 0));
		map.walls.add(new Wall(5, -30, 20, -30));
	}
	
	public static void generateMap2(Map map){
		map.generateSquare(0, 0, 1000, -1000);
		map.generateSquare(10, -10, 20, -20, new Color(100, 0, 0));
		map.generateSquare(10, -30, 20, -40, new Color(0, 100, 0));
		map.generateSquare(20, -10, 30, -20, new Color(0, 100, 0));
		map.generateSquare(10, -50, 20, -60, new Color(0, 0, 100));
		map.generateSquare(10, -70, 20, -80, new Color(100, 100, 0));		
	}
	
	public static void generateMap3(Map map){
		map.generateSquare(0, 0, 1000, -1000);
		map.generateSquare(10, -10, 20, -20, new Color(100, 0, 0));
		map.generateSquare(10, -30, 20, -40, new Color(0, 100, 0));
		map.generateSquare(20, -10, 30, -20, new Color(33, 100, 60));
		map.generateSquare(10, -50, 20, -60, new Color(0, 0, 100));
		map.generateSquare(10, -70, 20, -80, new Color(100, 100, 0));		
		map.generateSquare(50, -10, 80, -40, new Color(0, 100, 100));
		map.generateSquare(50, -10, 80, -40, new Color(0, 100, 100));
		map.generateSquare(100, -1, 120, -10, new Color(45, 134, 100));
		
		Color color = new Color(30, 70, 60);
		map.walls.add(new Wall(30, -45, 31, -50, color));
		map.walls.add(new Wall(30, -45, 31, -40, color.darker()));
		map.walls.add(new Wall(31, -40, 45, -45, color));
		map.walls.add(new Wall(31, -50, 45, -45, color.darker()));
	}
	
	public ArrayList<Wall> walls = new ArrayList<>();

	public void generateSquare(double x1, double y1, double x2, double y2){
		Color black = new Color(0, 0, 0);
		walls.add(new Wall(x1, y1, x2, y1, black));
		walls.add(new Wall(x2, y1, x2, y2, black));
		walls.add(new Wall(x2, y2, x1, y2, black));
		walls.add(new Wall(x1, y2, x1, y1, black));
	}
	
	public void generateSquare(double x1, double y1, double x2, double y2, Color color){
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
		Point2D intersection = getLineLineIntersection(wall.getLine(), rayLine);
		if(intersection == null)
			return World.draw_distance;
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

	// from http://www.java-gaming.org/index.php?topic=22590.0
	public static Point2D getLineLineIntersection(Line2D a, Line2D b) {
		
		double x1 = a.getX1();
		double x2 = a.getX2();
		double x3 = b.getX1();
		double x4 = b.getX2();
		double y1 = a.getY1();
		double y2 = a.getY2();
		double y3 = b.getY1();
		double y4 = b.getY2();
		
		
		double det1And2 = det(x1, y1, x2, y2);
		double det3And4 = det(x3, y3, x4, y4);
		double x1LessX2 = x1 - x2;
		double y1LessY2 = y1 - y2;
		double x3LessX4 = x3 - x4;
		double y3LessY4 = y3 - y4;
		double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
		if (det1Less2And3Less4 == 0) {
			// the denominator is zero so the lines are parallel and there's
			// either no solution (or multiple solutions if the lines overlap)
			// so return null.
			return null;
		}
		double x = (det(det1And2, x1LessX2, det3And4, x3LessX4) / det1Less2And3Less4);
		double y = (det(det1And2, y1LessY2, det3And4, y3LessY4) / det1Less2And3Less4);
		return new Point2D.Double(x, y);
	}

	public static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}

}
