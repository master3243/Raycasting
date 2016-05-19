/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.old;

import java.awt.Color;
import java.awt.geom.Line2D;

public class Wall {
	
	private Line2D line; 
	private Color color;
	
	public Wall(double x1, double y1, double x2, double y2){
		this(x1, y1, x2, y2, new Color(0, 0, 0));
	}
	
	public Wall(double x1, double y1, double x2, double y2, Color color){
		line = new Line2D.Double(x1, y1, x2, y2);
		this.color = color;
	}
	
	public Line2D getLine(){
		return line;
	}
	
	public Color getColor(){
		return new Color(color.getRGB());
	}
	
	public boolean intersectsLine(Line2D other){
		return line.intersectsLine(other);
	}
	
	public boolean intersectsWall(Wall other){
		return line.intersectsLine(other.line);
	}
	
	
	
}
