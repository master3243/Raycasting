/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.util.ArrayList;

import raycasting.Main;
import raycasting.WallProperties;
import raycasting.entities.Base;
import raycasting.entities.Player;
import raycasting.map.Map;

public class DataToGUI {

	private GUI gui;
	private Map map;
	private int playerNumber;
	
	private static final Color skyColor = new Color(0, 255, 255);
	private static final Color groundColor = new Color(130, 90, 44);
	private static final int rayHeightMultiplicationConstant = 5000; // trial-and-error
	private static final int damageToRedScreenMultiplicationConstant = 1; // trial-and-error

	public DataToGUI(GUI gui, Map map, int playerNumber) {
		this.gui = gui;
		this.map = map;
		this.playerNumber = playerNumber;
	}

	public void update() {
		updateGUI();
		keyboard_check();
	}

	private void keyboard_check() {
		Main.keyboard.poll();
		Player p = Player.players.get(playerNumber);
		p.updatePlayer(Main.keyboard);
	}

	private void updateGUI() {
		gui.clearRectangles();

		map.updatePlayerLocations();
		map.updateEntityLocations();
		updateUI();
		
		Rectangle[] background = getBackground();
		gui.addRectangles(background);

		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, playerNumber);
		gui.addRectangles(walls);

		gui.repaint();
	}
	
	private void updateUI(){
		Player p = Player.players.get(playerNumber);
		gui.moneyInBP = p.getMoenyInBP();
		Base temp = map.getBase(playerNumber);
		if(temp != null)
			gui.moneyInBase = temp.getMoney();
	}
	
	private ArrayList<Rectangle> wallPropertiesToRectangles(Map map, int playerNumber) {
		WallProperties[] wallProperties = map.generateWallPropertiesArray(playerNumber);
		ArrayList<Rectangle> result = new ArrayList<Rectangle>();

		int numOfRetangles = wallProperties.length; // World.width_resolution
		double widthOfRectangle = (double) gui.widthOfWindow / numOfRetangles;
		Player p = Player.players.get(playerNumber);
		double heightOfEyeLevel = p.getLookingDirectionZAxis();

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
		
		double playerLookingDirectionZAxis = Player.players.get(playerNumber).getLookingDirectionZAxis();
		double middleOfLookingDirectionZAxis = gui.heightOfWindow / 2 + playerLookingDirectionZAxis;
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
		double maxHealth = Player.players.get(playerNumber).maxHealth;
		double playerHealth = Player.players.get(playerNumber).getHealth();
		int changeInRed = (int) (maxHealth - playerHealth) * damageToRedScreenMultiplicationConstant;

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
