/*
 * Abdulrahman Alabdulkareem 782435
 * May 19, 2016
 */
package raycasting.map;

import java.awt.Color;

import raycasting.entities.Wall;

public class MapData {
	
	public static void generateMap1(Map map) {
		generateSquare(map, 0, 0, 1000, -1000, new Color(50, 50, 0));
		map.walls.add(new Wall(5, -30, 20, -30));
	}
	
	public static void generateMap2(Map map){
		generateSquare(map, 0, 0, 1000, -1000, new Color(0, 0, 0));
		generateSquare(map, 10, -10, 20, -20, new Color(100, 0, 0));
		generateSquare(map, 10, -30, 20, -40, new Color(0, 100, 0));
		generateSquare(map, 20, -10, 30, -20, new Color(0, 100, 0));
		generateSquare(map, 10, -50, 20, -60, new Color(0, 0, 100));
		generateSquare(map, 10, -70, 20, -80, new Color(100, 100, 0));		
	}
	
	public static void generateMap3(Map map){
		generateSquare(map, 0, 0, 1000, -1000, new Color(0, 0, 0));
		generateSquare(map, 10, -10, 20, -20, new Color(100, 0, 0));
		generateSquare(map, 10, -30, 20, -40, new Color(0, 100, 0));
		generateSquare(map, 20, -10, 30, -20, new Color(0, 100, 0));
		generateSquare(map, 10, -50, 20, -60, new Color(0, 0, 100));
		generateSquare(map, 10, -70, 20, -80, new Color(100, 100, 0));		
		generateSquare(map, 50, -10, 80, -40, new Color(0, 100, 100));
		generateSquare(map, 50, -10, 80, -40, new Color(0, 100, 100));
		
		Color color = new Color(30, 70, 60);
		map.walls.add(new Wall(30, -45, 31, -50, color));
		map.walls.add(new Wall(30, -45, 31, -40, color.darker()));
		map.walls.add(new Wall(31, -40, 45, -45, color));
		map.walls.add(new Wall(31, -50, 45, -45, color.darker()));
	}
	
	public static void generateSquare(Map map,double x1, double y1, double x2, double y2, Color color){
		map.walls.add(new Wall(x1, y1, x2, y1, color));
		map.walls.add(new Wall(x2, y1, x2, y2, color.darker()));
		map.walls.add(new Wall(x2, y2, x1, y2, color));
		map.walls.add(new Wall(x1, y2, x1, y1, color.darker()));
	}
}
