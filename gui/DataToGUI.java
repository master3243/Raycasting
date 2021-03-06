/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.util.ArrayList;

import raycasting.Main;
import raycasting.World;
import raycasting.entities.Base;
import raycasting.entities.Player;
import raycasting.helperClasses.WallProperties;
import raycasting.map.Map;

public class DataToGUI {

	private GUI gui;
	private Map map;
	private int playerNumber;

	private static final Color skyColor = new Color(0, 255, 255);
	private static final Color groundColor = new Color(130, 90, 44);
	private static final int rayHeightMultiplicationConstant = 3000; // trial-and-error
	private static final int damageToRedScreenMultiplicationConstant = 1; // trial-and-error

	public DataToGUI(GUI gui, Map map, int playerNumber) {
		this.gui = gui;
		this.map = map;
		this.playerNumber = playerNumber;
	}

	private boolean countingDown = false;
	private int startTick = 0;
	private int numberOnDisplay = 0;

	public void startCountdown(int number) {
		countingDown = true;
		startTick = Main.gameEngine.getTicksSinceStart();
		numberOnDisplay = number;
		gui.onScreenText = String.valueOf(numberOnDisplay);
	}

	public void updateCountdown() {
		int oneSecond = World.FPS;
		if (startTick + oneSecond <= Main.gameEngine.getTicksSinceStart()) {
			startTick = Main.gameEngine.getTicksSinceStart();

			if (numberOnDisplay == 1) {
				gui.onScreenText = "GO!";
				Main.gameEngine.kbPaused = false;
				numberOnDisplay--;
			} else if (numberOnDisplay == 0) {
				gui.onScreenText = "";
				countingDown = false;
			} else {
				numberOnDisplay--;
				gui.onScreenText = String.valueOf(numberOnDisplay);
			}
		}

	}

	public void update() {
		updateGUI();
		if(!Main.gameEngine.kbPaused)
			playerKeyBoardUpdate();
	}

	private void playerKeyBoardUpdate() {
		Player p = Player.players.get(playerNumber);
		p.updatePlayer(Main.keyboard);
	}

	private void updateGUI() {
		gui.clearRectangles();

		updateUI();

		Rectangle[] background = getBackground();
		gui.addRectangles(background);

		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, playerNumber);
		gui.addRectangles(walls);

		gui.repaint();
	}

	private void updateUI() {
		if (countingDown)
			updateCountdown();
		
		//Backpack money
		Player p = Player.players.get(playerNumber);
		gui.moneyInBP = p.getMoenyInBP();
		
		//Base money
		Base temp = map.getFirstBase(playerNumber);
		if (temp != null)
			gui.moneyInBase = temp.getMoney();
		
		//add on screen text
		int playerScreenBufferSize = p.getOnScreenTextBufferSize();
		if(playerScreenBufferSize > 0){
			for(String text : p.getOnScreenTextBuffer()){
				int durationInSeconds = Integer.valueOf(text.substring(0, text.indexOf(":")));
				String displayText = text.substring(text.indexOf(":") + 1);
				addOnScreenText(displayText, durationInSeconds);		
			}
			//clear player buffer because already added to this classes buffer
			p.getOnScreenTextBuffer().clear();
		}
		
		//any text in middle of screen
		updateOnScreenText();
	}
	
	private ArrayList<String> onScreenTextBuffer = new ArrayList<>();
	
	private void addOnScreenText(String text, double secondsOnScreen){
		int ticksPerSecond = World.FPS;
		int ticksOnScreen = (int) (ticksPerSecond * secondsOnScreen);
		onScreenTextBuffer.add(ticksOnScreen + ":" + text);
	}
	
	private void updateOnScreenText(){
		if(onScreenTextBuffer.size() == 0)
			return;
		String current = onScreenTextBuffer.get(0);
		int ticksRemaining = Integer.parseInt(current.substring(0, current.indexOf(":")));
		String text = current.substring(current.indexOf(":") + 1);
		
		gui.onScreenText = text;	
		
		if(ticksRemaining > 0){
			ticksRemaining--;
			onScreenTextBuffer.remove(0);
			onScreenTextBuffer.add(0, ticksRemaining + ":" + text);
		}else{
			gui.onScreenText = "";
			onScreenTextBuffer.remove(0);
		}
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

	private double convertRayHeight(double distance) {
		// return rayHeightMultiplicationConstant / distance;
		// return Math.tan(1/distance)*1000;
		return rayHeightMultiplicationConstant / (distance + 2);
	}

}
