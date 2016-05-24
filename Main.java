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
	
	public static KeyboardInput keyboard;
	
	public static void main(String args[]) {
		
		GUI gui = new GUI(600, 740);
		Map map = new Map(3);
		int[] controls1 = new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SHIFT};
		Player player = new Player(map, new Color(255, 0, 200), controls1);
		player.setPoint(new Point2D.Double(20, -180));
		player.setLookingDirection(new Direction(54));
		DataToGUI base = new DataToGUI(gui, map, player);
		
		GUI gui2 = new GUI(600, 740);
		gui2.frame.setLocation(700, 0);
		Map map2 = new Map(3);
		int[] controls2 = new int[]{KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD0};
		Player player2 = new Player(map2, new Color(255, 215, 0), controls2);
//		player2.setPoint(new Point2D.Double(195, -35));
		player2.setPoint(new Point2D.Double(25, -165));
		player2.setLookingDirection(new Direction(0));
		DataToGUI base2 = new DataToGUI(gui2, map2, player2);
		
		GUI keyboardGUI = gui2;
		keyboard = new KeyboardInput();
		keyboardGUI.frame.addKeyListener(keyboard);
		keyboardGUI.frame.setFocusable(true);
		keyboardGUI.frame.requestFocusInWindow();
		
		
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
