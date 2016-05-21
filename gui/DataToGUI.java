/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import raycasting.WallProperties;
import raycasting.World;
import raycasting.entities.Player;
import raycasting.map.Map;

public class DataToGUI {
	
	private GUI gui;
	private Map map;
	private Player player;
	private Timer guiUpdater;
	
	public DataToGUI(GUI gui, Map map, Player player){
		this.gui = gui;
		this.map = map;
		this.player = player;
		
		guiUpdater = new Timer(World.millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateGUI();
			}
		});
		guiUpdater.setInitialDelay(0);
	}
	
	public void startUpdatingGUI(){
		guiUpdater.start();
	}
	
	public void stopUpdatingGUI(){
		guiUpdater.stop();
	}
	
	private void updateGUI(){
		gui.clearRectangles();
		ArrayList<Rectangle> walls = wallPropertiesToRectangles(map, player);
		gui.addRectangles(walls);
		gui.repaint();
	}
	
	private ArrayList<Rectangle> wallPropertiesToRectangles(Map map, Player player){
		WallProperties[] wallProp = map.generateWallPropertiesArray(player);
		ArrayList<Rectangle> result = new ArrayList<Rectangle>();
		
		int numOfRetangles = wallProp.length; //World.width_resolution
		double widthOfRectangle = (double) gui.widthOfWindow / numOfRetangles;
		double heightOfEyeLevel = player.getLookingDirectionZAxis().getDirectionNumber();
		
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
	
}
