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
		java.lang.reflect.Method[] methods = MapData.class.getMethods();
		for(java.lang.reflect.Method m : methods)
			if(m.getName().equals("generateMap" + mapNumber))
				try {
					m.invoke(null, this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
		
	}
	
	public final ArrayList<Point2D> entitySpawnLocations = new ArrayList<>();
	
	public final ArrayList<Wall> physicalWalls = new ArrayList<>();
	public final HashMap<Integer, Wall[]> playerNumberToPlayerWalls = new HashMap<>();
	public final ArrayList<Entity> entities = new ArrayList<>();
	public final HashMap<Entity, Wall[]> entitiesToEntityWalls = new HashMap<>();
	
	
	public void updatePlayerLocations() {
		playerNumberToPlayerWalls.clear();

		for (int i = 0; i < Player.players.size(); i++) {
			Wall[] playerWalls = Util.generatePlayerWalls(i);
			addPlayerWallArray(i, playerWalls);
		}
	}
	
	public void updateEntityLocations() {
		entitiesToEntityWalls.clear();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			
			Wall[] entityWalls = Util.generateEntityWalls(entity);
			addEntityWallArray(entity, entityWalls);
		}
	}
 
	public WallProperties[] generateWallPropertiesArray(int playerNumber) {
		Player player = Player.players.get(playerNumber);
		
		WallProperties[] result = new WallProperties[World.width_resolution];
		double degreeBetweenRays = player.getDegreeBetweenRays();
		double playerDirection = player.getLookingDirection().getValue();
		Point2D playerPosition = player.getPoint();
		double rightMostPixel = playerDirection - (player.getFOV() / 2);

		for (int i = 0; i < World.width_resolution; i++) {
			Direction rayDirection = new Direction(rightMostPixel + (degreeBetweenRays * i));
			Line2D rayLine = Util.getRayLine(playerPosition, rayDirection);

			Wall closestWall = getNearestWall(rayLine, playerNumber);
			if (closestWall == null)
				continue;
			double wallDistance = Util.getDistance(rayLine, closestWall);
			Color wallColor = closestWall.getColor();
			result[i] = new WallProperties(wallDistance, wallColor);
		}
		return result;
	}

	public Wall getNearestWall(Line2D rayLine, int playerNumberToIgnore) {
		ArrayList<Wall> collidingWalls = getCollidingWalls(rayLine, playerNumberToIgnore);
		double min = Integer.MAX_VALUE;
		double distance;
		Wall result = null;
		for (Wall wall : collidingWalls) {
			if(wallBelongsToPlayer(wall, playerNumberToIgnore))
				continue;
			
			distance = Util.getDistance(rayLine, wall);
			if (distance < min) {
				min = distance;
				result = wall;
			}
		}
		return min >= World.draw_distance ? null : result;
	}

	public ArrayList<Wall> getCollidingWalls(Line2D rayLine, int playerNumberToIgnore) {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : getWalls()) {
			if (wall.intersectsLine(rayLine) && !wallBelongsToPlayer(wall, playerNumberToIgnore))
				result.add(wall);
		}
		return result;
	}
	
	public ArrayList<Wall> getWalls() {
		ArrayList<Wall> result = new ArrayList<Wall>();
		for (Wall wall : physicalWalls)
			result.add(wall);
		for (Wall[] wallArr : playerNumberToPlayerWalls.values())
			for (Wall wall : wallArr)
				result.add(wall);
		for (Wall[] wallArr : entitiesToEntityWalls.values())
			for (Wall wall : wallArr)
				result.add(wall);
		
		return result;
	}
	
	private boolean wallBelongsToPlayer(Wall wall, int playerNumberToIgnore){
		for(Wall w : playerNumberToPlayerWalls.get(playerNumberToIgnore)){
			if (wall == w)
				return true;
		}
		return false;
	}
	
	public Player getNearestInSightPlayer(Line2D rayLine, int playerNumberToIgnore) {
		Wall nearestWall = getNearestWall(rayLine, playerNumberToIgnore);
		for (Wall x : physicalWalls)
			if (x == nearestWall)
				return null;
		for (int i = 0; i < Player.players.size(); i++) {
			for (Wall x : playerNumberToPlayerWalls.get(i))
				if (x == nearestWall)
					return Player.players.get(i);
		}
		return null;
	}

	public boolean collidesWithWall(Line2D line, int playerNumberToIgnore) {
		for (Wall wall : getWalls()) {
			if (wall.intersectsLine(line) && !wallBelongsToPlayer(wall, playerNumberToIgnore))
				if (!wall.isWalkable)
					return true;
		}
		return false;
	}

	public boolean canMove(Point2D from, Point2D to, int playerNumberToIgnore) {
		Line2D changeInPosition = new Line2D.Double(from, to);
		boolean doesntCollidesWithWall = !collidesWithWall(changeInPosition, playerNumberToIgnore);
		return doesntCollidesWithWall;
	}

	public void addWallArray(Wall[] arr) {
		for (Wall wall : arr)
			physicalWalls.add(wall);
	}

	public void addPlayerWallArray(int playerNumber, Wall[] wallArr) {
		playerNumberToPlayerWalls.put(playerNumber, wallArr);
	}

	public void addEntityWallArray(Entity entity, Wall[] wallArr) {
		entitiesToEntityWalls.put(entity, wallArr);
	}

	public Base getBase(int playerNumber){
		for(Entity e : entities)
			if(e.getClass().getSimpleName().equals("Base"))
				if(((Base) e).OwnerNumber == playerNumber)
					return ((Base) e);
		return null;
	}
	
	
}

