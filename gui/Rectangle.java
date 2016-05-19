/*
 * Abdulrahman Alabdulkareem 782435
 * May 16, 2016
 */
package raycasting.gui;

import java.awt.Color;

public class Rectangle {
	
	public final double x;
//	public final int x;
	public final int y;
	public final int width;
	public final int height;
	public final Color color;

	public Rectangle(int x1, int y1, int x2, int y2, Color color) {
		this.x = x1;
		this.y = y1;
		this.width = x2;
		this.height = y2;
		this.color = color;
	}
}
