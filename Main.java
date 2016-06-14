/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting;

import java.awt.Color;
import java.awt.event.KeyEvent;
import raycasting.gui.DataToGUI;
import raycasting.gui.GUI;
import raycasting.helperClasses.KeyboardInput;
import raycasting.map.Map;
import raycasting.entities.Player;

public class Main {

	public static KeyboardInput keyboard;
	public static final Map map = new Map(5);
	public static GameEngine gameEngine;
	private static final int[] controlsPlayer1 = new int[]{
			KeyEvent.VK_W    //move up
			, KeyEvent.VK_A  //move left
			, KeyEvent.VK_S  //move down
			, KeyEvent.VK_D  //move right
			, KeyEvent.VK_I  //look up
			, KeyEvent.VK_J  //look left
			, KeyEvent.VK_K  //look down
			, KeyEvent.VK_L  //look right
			, KeyEvent.VK_SHIFT  //sprint
			, KeyEvent.VK_SPACE  //shoot tranqulizer
	};
	private static final int[] controlsPlayer2 = new int[]{
			KeyEvent.VK_UP         //move up
			, KeyEvent.VK_LEFT     //move left
			, KeyEvent.VK_DOWN     //move down
			, KeyEvent.VK_RIGHT    //move right
			, KeyEvent.VK_NUMPAD5  //look up
			, KeyEvent.VK_NUMPAD1  //look left
			, KeyEvent.VK_NUMPAD2  //look down
			, KeyEvent.VK_NUMPAD3  //look right
			, KeyEvent.VK_NUMPAD0  //sprint
			, KeyEvent.VK_PERIOD   //shoot tranqulizer
	};
	
	
	public static void main(String args[]) {

		GUI gui = new GUI(600, 740);
		Player player = new Player(new Color(255, 0, 200), controlsPlayer1);
		DataToGUI base = new DataToGUI(gui, map, player.playerNumber);
		
		GUI gui2 = new GUI(600, 740);
		gui2.frame.setLocation(700, 0);
		Player player2 = new Player(new Color(255, 215, 0), controlsPlayer2);
		DataToGUI base2 = new DataToGUI(gui2, map, player2.playerNumber);
		
		GUI keyboardGUI = gui2;
		keyboard = new KeyboardInput();
		keyboardGUI.frame.addKeyListener(keyboard);
		keyboardGUI.frame.setFocusable(true);
		keyboardGUI.frame.requestFocusInWindow();

		gameEngine = new GameEngine(map, new DataToGUI[]{base, base2});
		gameEngine.startGame();
		
	}
}
