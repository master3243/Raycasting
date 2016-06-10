/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.util.ArrayList;

import raycasting.Main;
import raycasting.Util;
import raycasting.WallProperties;
import raycasting.entities.Entity;
import raycasting.entities.Player;
import raycasting.entities.Wall;
import raycasting.map.Map;

public class DataToGUI {

	private GUI gui;
	private Map map;
	private Player player;

	private static final Color skyColor = new Color(0, 255, 255);
	private static final Color groundColor = new Color(130, 90, 44);
	private static final int rayHeightMultiplicationConstant = 5000; // trial-and-error
	private static final int damageToRedScreenMultiplicationConstant = 1; // trial-and-error

	public DataToGUI(GUI gui, Map map, Player player) {
		this.gui = gui;
		this.map = map;
		this.player = player;
	}

	public void update() {
		updateGUI();
		keyboard_check();
	}

	private void keyboard_check() {
		Main.keyboard.poll();
		player.updatePlayer(Main.keyboard);
	}

	private void updateGUI() {
		gui.clearRectangles();

		updatePlayerLocations();
		updateEntityLocations();
		
		Rectangle[] background = getBackground();
		gui.addRectangles(background);

		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, player);
		gui.addRectangles(walls);

		gui.repaint();
	}

	private void updatePlayerLocations() {
		map.playersToPlayerWalls.clear();

		for (int i = 0; i < Player.players.size(); i++) {
			Player player = Player.players.get(i);
			Wall[] playerWalls = Util.generatePlayerWalls(player);
			map.addPlayerWallArray(player, playerWalls);
		}
	}
	
	private void updateEntityLocations() {
		map.entitiesToEntityWalls.clear();
		
		for (int i = 0; i < map.entities.size(); i++) {
			Entity entity = map.entities.get(i);
			Wall[] entityWalls = Util.generateEntityWalls(entity);
			map.addEntityWallArray(entity, entityWalls);
		}
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
			double height = convertRayHeight(wallProperties[i].distance);
			// double height = (500 - wallProp[i].distance*10); trying out
			// different methods,, not good

			double y = gui.heightOfWindow / 2 + heightOfEyeLevel - height / 2;
			Color color = wallProperties[i].color;
			color = editColorBasedOnHealth(color);

			result.add(new Rectangle(x, y, widthOfRectangle, height, color));
		}
		return result;
	}

	private Rectangle[] getBackground() {
		Rectangle[] result = new Rectangle[2];

		double middleOfLookingDirectionZAxis = gui.heightOfWindow / 2 + player.getLookingDirectionZAxis();
		Color newSkyColor = new Color(skyColor.getRGB());
		newSkyColor = editColorBasedOnHealth(newSkyColor);
		Color newGroundColor = new Color(groundColor.getRGB());
		newGroundColor = editColorBasedOnHealth(newGroundColor);

		Rectangle sky = new Rectangle(0, 0, gui.widthOfWindow, middleOfLookingDirectionZAxis, newSkyColor);
		Rectangle ground = new Rectangle(0, middleOfLookingDirectionZAxis, gui.widthOfWindow,
				gui.heightOfWindow - middleOfLookingDirectionZAxis, newGroundColor);

		result[0] = sky;
		result[1] = ground;

		return result;
	}

	private Color editColorBasedOnHealth(Color oldColor) {
		Color newColor = null;
		int changeInRed = (int) (player.maxHealth - player.getHealth()) * damageToRedScreenMultiplicationConstant;

		int red = oldColor.getRed() + changeInRed;
		if (red > 255)
			red = 255;

		int green = oldColor.getGreen() - changeInRed;
		if (green < 0)
			green = 0;

		int blue = oldColor.getBlue() - changeInRed;
		if (blue < 0)
			blue = 0;

		newColor = new Color(red, green, blue);
		return newColor;
	}
	
	private double convertRayHeight(double distance){
//		return rayHeightMultiplicationConstant / distance;
//		return Math.tan(1/distance)*1000;
		return rayHeightMultiplicationConstant / (distance+2);
	}

}
