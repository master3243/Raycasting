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
		map.physicalWalls.add(new Wall(5, -30, 20, -30));
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
		map.physicalWalls.add(new Wall(30, -45, 31, -50, color));
		map.physicalWalls.add(new Wall(30, -45, 31, -40, color.darker()));
		map.physicalWalls.add(new Wall(31, -40, 45, -45, color));
		map.physicalWalls.add(new Wall(31, -50, 45, -45, color.darker()));
	}
	
	public static void generateMap4(Map map){
		Color color = new Color(30, 70, 60);
		int[][] arr = new int[][]{
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
		generateMapFromArray(map, arr, color);
	}
	
	public static void generateSquare(Map map,double x1, double y1, double x2, double y2, Color color){
		map.physicalWalls.add(new Wall(x1, y1, x2, y1, color));
		map.physicalWalls.add(new Wall(x2, y1, x2, y2, color.darker()));
		map.physicalWalls.add(new Wall(x2, y2, x1, y2, color));
		map.physicalWalls.add(new Wall(x1, y2, x1, y1, color.darker()));
	}
	
	public static void generateMapFromArray(Map map, int[][] arr, Color color){
		for(int r = 0; r < arr.length; r++)
			for(int c = 0; c < arr[0].length; c++)
				if(arr[r][c] == 1){
					int x1 = c*10;
					int y1 = r*10;
					y1 = -y1;
					int x2 = c*10 + 10;
					int y2 = r*10 + 10;
					y2 = -y2;
					
					boolean generateTop = r == 0 || arr[r-1][c] == 0;
					boolean generateLeft = c == 0 || arr[r][c-1] == 0;
					
					if(generateTop)
						map.physicalWalls.add(new Wall(x1, y1, x2, y1, color));
					map.physicalWalls.add(new Wall(x2, y1, x2, y2, color.darker()));
					map.physicalWalls.add(new Wall(x2, y2, x1, y2, color));
					if(generateLeft)
						map.physicalWalls.add(new Wall(x1, y2, x1, y1, color.darker()));
					
				}
	}
	
}
