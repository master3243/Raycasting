/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import raycasting.entities.Base;
import raycasting.entities.Entity;
import raycasting.entities.MoneyBag;
import raycasting.entities.Player;
import raycasting.gui.DataToGUI;
import raycasting.map.Map;

public class GameEngine {
	
	private final Map map;
	private final DataToGUI[] dataToGUIs;
	public boolean kbPaused = false;
	public final ArrayList<String> spawnQueue = new ArrayList<String>();
	
	private static int ticksSinceStart = 0;
	
	public GameEngine(Map map, DataToGUI[] dataToGUIs){
		this.map = map;
		this.dataToGUIs = dataToGUIs;
		
	}
	
	public void startGame(){
		updateWholeGameOneFrame();
		startCountdown();
		while (true) {
			try {
				Thread.sleep(World.millisecondsBetweenTicks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			updateWholeGameOneFrame();
		}
	}
	
	private void startCountdown(){
		kbPaused = true;
		for(DataToGUI o : dataToGUIs)
			o.startCountdown(3);
	}
	
	private void updateWholeGameOneFrame(){
		Main.keyboard.poll();
		map.updateMap();
		updateEntities();
		updateSpawnQueue();
		for(DataToGUI o : dataToGUIs)
			o.update();
		ticksSinceStart++;
		
		if(ticksSinceStart == 6){
			Player.players.get(0).setPoint(map.getFirstBase(0).location);
			Player.players.get(1).setPoint(map.getFirstBase(1).location);
		}
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
	
	public void updateSpawnQueue(){
		for(int i = spawnQueue.size()-1; i >= 0; i--){
			String spawn = spawnQueue.get(i);
			int tick = Integer.parseInt(spawn.substring(0, spawn.indexOf(':')));
			if(tick >= getTicksSinceStart())
				continue;
			
			String properties = spawn.substring(spawn.indexOf(":")+1);
			boolean addedSuccesfully = addEntity(properties);
			if(addedSuccesfully)
				spawnQueue.remove(i);
		}
	}

	public int getTicksSinceStart(){
		return ticksSinceStart;
	}
	
	public void removeEntity(Entity e){
		map.freeEntitySpawnLocations.add(e.location);
		respawnEntity(e);
		map.entities.remove(e);
	}
	
	private boolean addEntity(String properties){
		Point2D spawnLocation = map.getFreeEntitySpawnLocation();
		if(spawnLocation == null)
			return false;
		map.reserveEntitySpawnLocation(spawnLocation);
		
		Entity newEntity = null;
		if(Base.belongsToThis(properties)){
			newEntity = Base.returnObjectWithProperties(properties);
		
		} else if(MoneyBag.belongsToThis(properties)){
			newEntity = MoneyBag.returnObjectWithProperties(properties);
			
		} else
			return false;
		
		newEntity.setLocation(spawnLocation);
		map.entities.add(newEntity);
		return true;
	}
	
	public void respawnEntity(Entity e){
		int waitTimeInTicks = (int) (World.FPS * e.getWaitTime());
		int respawnTick = ticksSinceStart + waitTimeInTicks;
		
		String entityProperties = e.getProperties();
		
		String result = respawnTick + ":" + entityProperties;
		spawnQueue.add(result);
	}

}
