/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting;

import raycasting.gui.DataToGUI;
import raycasting.map.Map;

public class GameEngine {
	
	private final Map map;
	private final DataToGUI[] dataToGUIs;
	
	public GameEngine(Map map, DataToGUI[] dataToGUIs){
		this.map = map;
		this.dataToGUIs = dataToGUIs;
	}
	
	public void startGame(){
		while (true) {
			try {
				Thread.sleep(World.millisecondsBetweenTicks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(DataToGUI o : dataToGUIs)
				o.update();
			map.updateEntities();
			World.increaseTicks();
		}
	}
	
}
