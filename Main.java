/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting;

import java.awt.geom.Point2D;

import javax.swing.KeyStroke;

import raycasting.gui.DataToGUI;
import raycasting.gui.GUI;
import raycasting.keyboard.ComponentKeyboardMapEditor;
import raycasting.map.Map;
import raycasting.Direction;
import raycasting.entities.Player;

public class Main {

	public static void main(String args[]) {
		GUI gui = new GUI(1200, 740);
		Map map = new Map(3);
		Player player = new Player();
		player.setPoint(new Point2D.Double(40, -42));
		player.setLookingDirection(new Direction(150));
		
		ComponentKeyboardMapEditor keyboard = gui.getComponentMapEditor();
		keyboard.bindToMoveInDirection(player, KeyStroke.getKeyStroke("W"), new Direction(0));
		keyboard.bindToMoveInDirection(player, KeyStroke.getKeyStroke("A"), new Direction(90));
		keyboard.bindToMoveInDirection(player, KeyStroke.getKeyStroke("S"), new Direction(180));
		keyboard.bindToMoveInDirection(player, KeyStroke.getKeyStroke("D"), new Direction(270));
		keyboard.bindToRotateInDirection(player, KeyStroke.getKeyStroke("LEFT"), true);
		keyboard.bindToRotateInDirection(player, KeyStroke.getKeyStroke("RIGHT"), false);
		
		
		DataToGUI base = new DataToGUI(gui, map, player);
		base.startUpdatingGUI();
	}
}
