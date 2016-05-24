/*
 * Abdulrahman Alabdulkareem 782435
 * May 19, 2016
 */
package raycasting;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import raycasting.entities.Player;
import raycasting.entities.Wall;

public class Util {

	public static double getDistance(Line2D rayLine, Wall wall){
		Point2D intersection = getLineLineIntersection(wall.getLine(), rayLine);
		
		//TODO might not be useful, remove if so
		if(intersection == null)
			return World.draw_distance; // distance is set to be draw_distance units far -> wall not drawn
		
		double distance = rayLine.getP1().distance(intersection);
		return distance;
	}
	
	private static final double INVRS_SQRT_2 = 0.71;
	
	public static Wall[] generatePlayerWalls(Player player){
		Wall[] result = new Wall[4];
		
		Color color = player.playerColor;
		double playerX = player.getPoint().getX();
		double playerY = player.getPoint().getY();
		double lookingDirection = player.getLookingDirection().getDirectionAddedToThis(Direction.HALF_LEFT)
				.getDirectionNumber();
		System.out.println(lookingDirection);
		double wallLength = player.lengthOfPlayerWall;
		
		double wallLengthFromMiddle = wallLength * INVRS_SQRT_2;
		double xExtra = wallLengthFromMiddle * Math.cos(lookingDirection);
		double yExtra = wallLengthFromMiddle * Math.sin(lookingDirection);
		
		Point2D topRight = new Point2D.Double(playerX + xExtra, playerY + yExtra);
		Point2D topLeft = new Point2D.Double(playerX - yExtra, playerY + xExtra);
		Point2D bottomLeft = new Point2D.Double(playerX - xExtra, playerY - yExtra);
		Point2D bottomRight = new Point2D.Double(playerX + yExtra, playerY - xExtra);
		
		result[0] = new Wall(topRight, topLeft, color);
		result[1] = new Wall(topLeft, bottomLeft, color);
		result[2] = new Wall(bottomLeft, bottomRight, color);
		result[3] = new Wall(bottomRight, topRight, color.darker());
		
		
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
	
	// from http://www.java-gaming.org/index.php?topic=22590.0
	private static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}

}
