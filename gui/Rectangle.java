/*
 * Abdulrahman Alabdulkareem 782435
 * May 16, 2016
 */
package raycasting.gui;

import java.awt.Color;

public class Rectangle {
	
	public final double x;
	public final double y;
	public final double width;
	public final double height;
	public final Color color;

	public Rectangle(double x1, double y1, double x2, double y2, Color color) {
		this.x = x1;
		this.y = y1;
		this.width = x2;
		this.height = y2;
		this.color = color;
	}
}
