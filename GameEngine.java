/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting;

import java.util.ArrayList;

import raycasting.entities.Entity;
import raycasting.entities.Player;
import raycasting.gui.DataToGUI;
import raycasting.map.Map;

public class GameEngine {
	
	private final Map map;
	private final DataToGUI[] dataToGUIs;
	private boolean kbPaused = false;
	private final ArrayList<String> spawnQueue = new ArrayList<String>();
	
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
			updateEntities();
			World.increaseTicks();
		}
	}
	
	public boolean getkbPauseStatus(){
		return kbPaused;
	}

	public void updateEntities(){

		for(int p = 0; p < Player.players.size(); p++)
			//iterate from top to bottom so if an entity is removed the iteration is not disturbed
			for(int i = map.entities.size()-1; i >= 0; i--){
				Entity e = map.entities.get(i);
				if(e.isPlayerTouching(p))
					e.playerTouch(p);
			}
	}

	public void removeEntity(Entity e){
		respawnEntity(e);
		map.entities.remove(e);
	}

	private void respawnEntity(Entity e){
		
	}

}
