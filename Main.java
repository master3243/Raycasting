/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import raycasting.gui.DataToGUI;
import raycasting.gui.GUI;
import raycasting.keyboard.KeyboardInput;
import raycasting.map.Map;
import raycasting.Direction;
import raycasting.entities.Player;

public class Main {
	
	public static Player[] players = new Player[2];
	public static KeyboardInput keyboard;
	
	public static Player[] getPlayers(){
		return players;
	}
	
	public static void main(String args[]) {
		
		
		GUI gui = new GUI(600, 740);
		Map map = new Map(3);
		int[] controls1 = new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT};
		Player player = new Player(map, 1, new Color(255, 0, 200), controls1);
		player.setPoint(new Point2D.Double(40, -42));
		player.setLookingDirection(new Direction(150));
		players[0] = player;
		DataToGUI base = new DataToGUI(gui, map, player);
		
		GUI gui2 = new GUI(600, 740);
		gui2.frame.setLocation(700, 0);
		Map map2 = new Map(3);
		int[] controls2 = new int[]{KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD0};
		Player player2 = new Player(map2, 2, new Color(255, 215, 0), controls2);
		player2.setPoint(new Point2D.Double(45, -42));
		player2.setLookingDirection(new Direction(150));
		players[1] = player2;
		DataToGUI base2 = new DataToGUI(gui2, map2, player2);
		
		keyboard = new KeyboardInput();
		gui2.frame.addKeyListener(keyboard);
		gui2.frame.setFocusable(true);
		gui2.frame.requestFocusInWindow();
		
		
		while (true) {
			try {
				Thread.sleep(World.millisecondsBetweenTicks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			base.update();
			base2.update();
			
		}
	}
}
