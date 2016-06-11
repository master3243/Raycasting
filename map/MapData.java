/*
 * Abdulrahman Alabdulkareem 782435
 * May 19, 2016
 */
package raycasting.map;

import java.awt.Color;
import java.awt.geom.Point2D;

import raycasting.entities.Wall;

public class MapData {

	public static void generateMap1(Map map) {
		generateSquare(0, 0, 1000, -1000, new Color(50, 50, 0));
		map.physicalWalls.add(new Wall(5, -30, 20, -30, new Color(0, 0, 0)));
	}

	public static void generateMap2(Map map) {
		map.addWallArray(generateSquare(0, 0, 1000, -1000, new Color(0, 0, 0)));
		map.addWallArray(generateSquare(10, -10, 20, -20, new Color(100, 0, 0)));
		map.addWallArray(generateSquare(10, -30, 20, -40, new Color(0, 100, 0)));
		map.addWallArray(generateSquare(20, -10, 30, -20, new Color(0, 100, 0)));
		map.addWallArray(generateSquare(10, -50, 20, -60, new Color(0, 0, 100)));
		map.addWallArray(generateSquare(10, -70, 20, -80, new Color(100, 100, 0)));
	}

	public static void generateMap3(Map map) {
		map.addWallArray(generateSquare(0, 0, 1000, -1000, new Color(0, 0, 0)));
		map.addWallArray(generateSquare(10, -10, 20, -20, new Color(100, 0, 0)));
		map.addWallArray(generateSquare(10, -30, 20, -40, new Color(0, 100, 0)));
		map.addWallArray(generateSquare(20, -10, 30, -20, new Color(0, 100, 0)));
		map.addWallArray(generateSquare(10, -50, 20, -60, new Color(0, 0, 100)));
		map.addWallArray(generateSquare(10, -70, 20, -80, new Color(100, 100, 0)));
		map.addWallArray(generateSquare(50, -10, 80, -40, new Color(0, 100, 100)));
		map.addWallArray(generateSquare(50, -10, 80, -40, new Color(0, 100, 100)));

		Color color = new Color(30, 70, 60);
		map.physicalWalls.add(new Wall(30, -45, 31, -50, color));
		map.physicalWalls.add(new Wall(30, -45, 31, -40, color.darker()));
		map.physicalWalls.add(new Wall(31, -40, 45, -45, color));
		map.physicalWalls.add(new Wall(31, -50, 45, -45, color.darker()));
	}

	public static void generateMap4(Map map) {
		Color color = new Color(30, 70, 60);
		int[][] arr = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
		generateMapFromArray(map, arr, 10, color);
	}

	public static void generateMap5(Map map) {
		Color color = new Color(30, 70, 60);
		int[][] arr = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1 },
				{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 2, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 2, 1, 0, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 0, 0, 1, 0, 1, 2, 1 },
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 0, 0, 2, 1, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
		generateMapFromArray(map, arr, 10, color);
	}

	public static Wall[] generateSquare(double x1, double y1, double x2, double y2, Color color) {
		Wall[] result = new Wall[4];
		result[0] = new Wall(x1, y1, x2, y1, color);
		result[1] = new Wall(x2, y1, x2, y2, color.darker());
		result[2] = new Wall(x2, y2, x1, y2, color);
		result[3] = new Wall(x1, y2, x1, y1, color.darker());
		return result;
	}

	public static void generateMapFromArray(Map map, int[][] arr, double wallLength, Color color) {
		for (int r = 0; r < arr.length; r++)
			for (int c = 0; c < arr[0].length; c++) {
				if (arr[r][c] == 1) {
					double x1 = c * wallLength;
					double y1 = -1 * r * wallLength;
					double x2 = c * wallLength + wallLength;
					double y2 = -1 * r * wallLength - wallLength;

					boolean generateTop = r != 0 && arr[r - 1][c] != 1;
					boolean generateLeft = c != 0 && arr[r][c - 1] != 1;
					boolean generateDown = r != arr.length - 1 && arr[r + 1][c] != 1;
					boolean generateRight = c != arr[0].length - 1 && arr[r][c + 1] != 1;

					if (generateTop)
						map.physicalWalls.add(new Wall(x1, y1, x2, y1, color));
					if (generateLeft)
						map.physicalWalls.add(new Wall(x1, y2, x1, y1, color.darker()));
					if (generateRight)
						map.physicalWalls.add(new Wall(x2, y1, x2, y2, color.darker()));
					if (generateDown)
						map.physicalWalls.add(new Wall(x2, y2, x1, y2, color));
				}
				if (arr[r][c] == 2) {
					map.entitySpawnLocations.add(
							new Point2D.Double((c + 0.5) * wallLength, (-1 * (r - 0.5) * wallLength)) );
				}
			}
	}

}
