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
import raycasting.map.Map;
import raycasting.Direction;
import raycasting.entities.Base;
import raycasting.entities.MoneyBag;
import raycasting.entities.Player;

public class Main {

	public static KeyboardInput keyboard;
	public static final Map map = new Map(6);
	
	public static void main(String args[]) {

		GUI gui = new GUI(600, 740);
		int[] controls1 = new int[] { KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_I,
				KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE };
		Player player = new Player(map, new Color(255, 0, 200), controls1);
		player.setPoint(new Point2D.Double(20, -180));
		player.setLookingDirection(new Direction(54));
		DataToGUI base = new DataToGUI(gui, map, player);

		GUI gui2 = new GUI(600, 740);
		gui2.frame.setLocation(700, 0);
		int[] controls2 = new int[] { KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT,
				KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD0,
				KeyEvent.VK_PERIOD };
		Player player2 = new Player(map, new Color(255, 215, 0), controls2);
		player2.setPoint(new Point2D.Double(195, -35));
		player2.setPoint(new Point2D.Double(25, -165));
		player2.setLookingDirection(new Direction(0));
		DataToGUI base2 = new DataToGUI(gui2, map, player2);
		
		
		map.entities.add(new MoneyBag(new Point2D.Double(40, -180), 3, new Color(12, 100, 12), 1000));
		map.entities.add(new MoneyBag(new Point2D.Double(40, -170), 3, new Color(12, 100, 12), 1000));
		map.entities.add(new Base(new Point2D.Double(50, -180), 5, new Color(120, 100, 122), player));
		
		
		GUI keyboardGUI = gui2;
		keyboard = new KeyboardInput();
		keyboardGUI.frame.addKeyListener(keyboard);
		keyboardGUI.frame.setFocusable(true);
		keyboardGUI.frame.requestFocusInWindow();

		GameEngine gameEngine = new GameEngine(map, new DataToGUI[]{base, base2});
		gameEngine.startGame();
	}
}
