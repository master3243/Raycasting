/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.map;

import java.util.ArrayList;
import java.util.HashMap;

import raycasting.Util;
import raycasting.Direction;
import raycasting.WallProperties;
import raycasting.World;
import raycasting.entities.Base;
import raycasting.entities.Entity;
import raycasting.entities.Player;
import raycasting.entities.Wall;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;

public class Map {

	public Map(int mapNumber) {
		switch (mapNumber) {
		case 1:
			MapData.generateMap1(this);
			break;
		case 2:
			MapData.generateMap2(this);
			break;
		case 3:
			MapData.generateMap3(this);
			break;
		case 4:
			MapData.generateMap4(this);
			break;
		case 5:
			MapData.generateMap5(this);
			break;
		}
		java.lang.reflect.Method[] methods = MapData.class.getMethods();
		for(java.lang.reflect.Method m : methods)
			if(m.getName().equals("generateMap" + mapNumber))
				try {
					m.invoke(null, this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
		
	}

	public final ArrayList<Wall> physicalWalls = new ArrayList<>();
	public final HashMap<Player, Wall[]> playersToPlayerWalls = new HashMap<>();
	public final ArrayList<Entity> entities = new ArrayList<>();
	public final HashMap<Entity, Wall[]> entitiesToEntityWalls = new HashMap<>();
	
	
	public ArrayList<Wall> getWalls() {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : physicalWalls)
			result.add(wall);
		for (Wall[] wallArr : playersToPlayerWalls.values())
			for (Wall wall : wallArr)
				result.add(wall);
		for (Wall[] wallArr : entitiesToEntityWalls.values())
			for (Wall wall : wallArr)
				result.add(wall);
		
		return result;
	}

	public WallProperties[] generateWallPropertiesArray(Player player) {

		WallProperties[] result = new WallProperties[World.width_resolution];
		double degreeBetweenRays = player.getDegreeBetweenRays();
		double playerDirection = player.getLookingDirection().getValue();
		Point2D playerPosition = player.getPoint();
		double rightMostPixel = playerDirection - (player.getFOV() / 2);

		for (int i = 0; i < World.width_resolution; i++) {
			Direction rayDirection = new Direction(rightMostPixel + (degreeBetweenRays * i));
			Line2D rayLine = Util.getRayLine(playerPosition, rayDirection);

			Wall closestWall = getNearestWall(rayLine, player);
			if (closestWall == null)
				continue;
			double wallDistance = Util.getDistance(rayLine, closestWall);
			Color wallColor = closestWall.getColor();
			result[i] = new WallProperties(wallDistance, wallColor);
		}
		return result;
	}

	public Wall getNearestWall(Line2D rayLine, Player playerWallToAvoid) {
		ArrayList<Wall> collidingWalls = getCollidingWalls(rayLine, playerWallToAvoid);
		double min = Integer.MAX_VALUE;
		double distance;
		Wall result = null;
		for (Wall wall : collidingWalls) {
			if(wallBelongsToPlayer(wall, playerWallToAvoid))
				continue;
			
			distance = Util.getDistance(rayLine, wall);
			if (distance < min) {
				min = distance;
				result = wall;
			}
		}
		return min >= World.draw_distance ? null : result;
	}
	
	private boolean wallBelongsToPlayer(Wall wall, Player player){
		for(Wall w : playersToPlayerWalls.get(player)){
			if (wall == w)
				return true;
		}
		return false;
	}
	
	public Player getNearestInSightPlayer(Line2D rayLine, Player playerWallToAvoid) {
		Wall nearestWall = getNearestWall(rayLine, playerWallToAvoid);
		for (Wall x : physicalWalls)
			if (x == nearestWall)
				return null;
		for (Player p : Player.players) {
			for (Wall x : playersToPlayerWalls.get(p))
				if (x == nearestWall)
					return p;
		}
		return null;
	}

	public ArrayList<Wall> getCollidingWalls(Line2D rayLine, Player playerWallToAvoid) {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : getWalls()) {
			if (wall.intersectsLine(rayLine) && !wallBelongsToPlayer(wall, playerWallToAvoid))
				result.add(wall);
		}
		return result;
	}

	public boolean collidesWithWall(Line2D line, Player playerWallToAvoid) {
		for (Wall wall : getWalls()) {
			if (wall.intersectsLine(line) && !wallBelongsToPlayer(wall, playerWallToAvoid))
				if (!wall.isWalkable)
					return true;
		}
		return false;
	}

	public boolean canMove(Point2D from, Point2D to, Player playerWallToAvoid) {
		Line2D changeInPosition = new Line2D.Double(from, to);
		boolean doesntCollidesWithWall = !collidesWithWall(changeInPosition, playerWallToAvoid);
		return doesntCollidesWithWall;
	}

	public void addWallArray(Wall[] arr) {
		for (Wall wall : arr)
			physicalWalls.add(wall);
	}

	public void addPlayerWallArray(Player player, Wall[] wallArr) {
		playersToPlayerWalls.put(player, wallArr);
	}

	public void updateEntities(){

		for(Player p : Player.players){
			for(Entity e : entitiesToEntityWalls.keySet()){
				if(e.isPlayerTouching(p)){
					e.playerTouch(p);
				}
			}
		}
	}

	public void removeEntity(Entity e){
		entities.remove(e);
	}

	public void addEntityWallArray(Entity entity, Wall[] wallArr) {
		entitiesToEntityWalls.put(entity, wallArr);
	}

	public Base getBase(Player p){
		for(Entity e : entities)
			if(e.getClass().getSimpleName().equals("Base"))
				if(((Base) e).owner == p)
					return ((Base) e);
		return null;
	}
}

