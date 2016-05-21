/*
 * Abdulrahman Alabdulkareem 782435
 * May 21, 2016
 */
package raycasting;

import java.awt.geom.Point2D;

import raycasting.gui.DataToGUI;
import raycasting.gui.GUI;
import raycasting.map.Map;
import raycasting.Direction;
import raycasting.entities.Player;

public class Main {

	public static void main(String args[]) {
		GUI gui = new GUI(1200, 740);
		Map map = new Map(3);
		Player player = new Player(map);
		player.setPoint(new Point2D.Double(40, -42));
		player.setLookingDirection(new Direction(150));
		
		DataToGUI base = new DataToGUI(gui, map, player);
		base.startUpdatingGUI();
	}
}
