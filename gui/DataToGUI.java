/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import raycasting.Main;
import raycasting.WallProperties;
import raycasting.entities.Player;
import raycasting.entities.Wall;
import raycasting.map.Map;

public class DataToGUI {

	private GUI gui;
	private Map map;
	private Player player;

	private static final Color skyColor = new Color(0, 255, 255);
	private static final Color groundColor = new Color(130, 90, 44);
	private static final double lengthOfOtherPlayerWalls = 3;
	
	
	public DataToGUI(GUI gui, Map map, Player player) {
		this.gui = gui;
		this.map = map;
		this.player = player;
	}

	public void update() {
		keyboard_check();
		updateGUI();
	}

	private void keyboard_check() {
		Main.keyboard.poll();
		player.updatePlayer(Main.keyboard);
	}

	private void updateGUI() {
		gui.clearRectangles();
		updateOtherPlayerLocation((player.playerNumber % 2));
		
		Rectangle[] background = getBackground();
		gui.addRectangles(background);

		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, player);
		gui.addRectangles(walls);
		
		gui.repaint();
	}
	
	private void updateOtherPlayerLocation(int playrNum){
		map.playerWalls.clear();
		Player otherPlayer = Main.getPlayers()[playrNum];
		Point2D loc = otherPlayer.getPoint();
		Color otherColor = otherPlayer.playerColor;
		
		double xStart = loc.getX() - lengthOfOtherPlayerWalls;
		double xEnd = loc.getX() + lengthOfOtherPlayerWalls;
		double yStart = loc.getY() - lengthOfOtherPlayerWalls;
		double yEnd = loc.getY() + lengthOfOtherPlayerWalls;
		
		map.playerWalls.add(new Wall(xStart, yStart, xEnd, yStart, otherColor ));
		map.playerWalls.add(new Wall(xEnd, yStart, xEnd, yEnd, otherColor .darker()));
		map.playerWalls.add(new Wall(xEnd, yEnd, xStart, yEnd, otherColor ));
		map.playerWalls.add(new Wall(xStart, yEnd, xStart, yStart, otherColor .darker()));
	}

	private ArrayList<Rectangle> wallPropertiesToRectangles(Map map, Player player) {
		WallProperties[] wallProperties = map.generateWallPropertiesArray(player);
		ArrayList<Rectangle> result = new ArrayList<Rectangle>();

		int numOfRetangles = wallProperties.length; // World.width_resolution
		double widthOfRectangle = (double) gui.widthOfWindow / numOfRetangles;
		double heightOfEyeLevel = player.getLookingDirectionZAxis();

		for (int i = 0; i < wallProperties.length; i++) {
			if (wallProperties[i] == null) {
				continue;
			}
			double x = gui.widthOfWindow - widthOfRectangle * (i + 1);
			double height = (5000 / wallProperties[i].distance);
//			double height = (500 - wallProp[i].distance*10); trying out different methods,, not good
			
			double y = gui.heightOfWindow / 2 + heightOfEyeLevel - height / 2;
			Color color = wallProperties[i].color;

			result.add(new Rectangle(x, y, widthOfRectangle, height, color));
		}
		return result;
	}

	private Rectangle[] getBackground() {
		Rectangle[] result = new Rectangle[2];
		double middleOfLookingDirectionZAxis = gui.heightOfWindow / 2 + player.getLookingDirectionZAxis();
		
		Rectangle sky = new Rectangle(0, 0, gui.widthOfWindow, middleOfLookingDirectionZAxis, skyColor);
		Rectangle ground = new Rectangle(0, middleOfLookingDirectionZAxis, gui.widthOfWindow
				, gui.heightOfWindow - middleOfLookingDirectionZAxis
				, groundColor);

		result[0] = sky;
		result[1] = ground;

		return result;
	}

}
