/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import raycasting.WallProperties;
import raycasting.World;
import raycasting.entities.Player;
import raycasting.map.Map;

public class DataToGUI {
	
	private GUI gui;
	private Map map;
	private Player player;
	
	private static final Color skyColor = new Color(0, 255, 255);
	private static final Color groundColor = new Color(130, 90, 44);
	
	public DataToGUI(GUI gui, Map map, Player player){
		this.gui = gui;
		this.map = map;
		this.player = player;
	}
	
	public void startUpdatingGUI(){
		while(true){
			try {
				Thread.sleep(World.millisecondsBetweenTicks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			keyboard_check();
			updateGUI();
		}
	}
	
	void keyboard_check(){
		gui.keyboard.poll();
		player.updatePlayer(gui.keyboard);
	}
	
	private void updateGUI(){
		gui.clearRectangles();
		
		Rectangle[] background = getBackground();
		gui.addRectangles(background);
		
		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, player);
		gui.addRectangles(walls);
		
		gui.repaint();
	}
	
	private ArrayList<Rectangle> wallPropertiesToRectangles(Map map, Player player){
		WallProperties[] wallProp = map.generateWallPropertiesArray(player);
		ArrayList<Rectangle> result = new ArrayList<Rectangle>();
		
		int numOfRetangles = wallProp.length; //World.width_resolution
		double widthOfRectangle = (double) gui.widthOfWindow / numOfRetangles;
		double heightOfEyeLevel = player.getLookingDirectionZAxis();
		
		for(int i = 0; i < wallProp.length; i++){
			if (wallProp[i] == null){
				continue;
			}
			double x = gui.widthOfWindow - widthOfRectangle * (i + 1);
			double height = (5000/wallProp[i].distance);
			double y = gui.heightOfWindow / 2 + heightOfEyeLevel  - height / 2;
			Color color = wallProp[i].color;
			
			result.add(new Rectangle(x, y, widthOfRectangle, height, color));
		}
		return result;
	}
	
	private Rectangle[] getBackground(){
		Rectangle[] result = new Rectangle[2];
		double middleOfLookingDirectionZAxis = gui.heightOfWindow / 2 + player.getLookingDirectionZAxis();
		
		Rectangle sky = new Rectangle(0, 0, gui.widthOfWindow, middleOfLookingDirectionZAxis, skyColor);
		Rectangle ground = new Rectangle(0, middleOfLookingDirectionZAxis
				, gui.widthOfWindow, gui.heightOfWindow, groundColor);

		result[0] = sky;
		result[1] = ground;
		
		return result;
	}
	
}
