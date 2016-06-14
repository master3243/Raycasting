/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.map;

import java.util.ArrayList;
import java.util.HashMap;

import raycasting.Util;
import raycasting.World;
import raycasting.entities.Base;
import raycasting.entities.Entity;
import raycasting.entities.Player;
import raycasting.entities.Wall;
import raycasting.helperClasses.Direction;
import raycasting.helperClasses.WallProperties;

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
	
	public final ArrayList<Point2D> freeEntitySpawnLocations = new ArrayList<>();
	
	public final ArrayList<Wall> physicalWalls = new ArrayList<>();
	public final HashMap<Integer, Wall[]> playerNumberToPlayerWalls = new HashMap<>();
	public final ArrayList<Entity> entities = new ArrayList<>();
	public final HashMap<Entity, Wall[]> entitiesToEntityWalls = new HashMap<>();
	
	public void updateMap(){
		updatePlayerLocations();
		updateEntityLocations();
	}
	
	private void updatePlayerLocations() {
		playerNumberToPlayerWalls.clear();

		for (int i = 0; i < Player.players.size(); i++) {
			Wall[] playerWalls = Util.generatePlayerWalls(i);
			addPlayerWallArray(i, playerWalls);
		}
	}
	
	private void updateEntityLocations() {
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
			//TODO remove below line, testing to remove fisheye
			double fecdirection = Math.abs(playerDirection - rayDirection.getValue());
			wallDistance = wallDistance * Math.cos(Math.toRadians(fecdirection));
			
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
			if(playerNotSupposedToSeeWall(wall, playerNumberToIgnore))
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
	
	private boolean playerNotSupposedToSeeWall(Wall wall, int playerNumber){
		if(wallBelongsToPlayer(wall, playerNumber))
			return true;
		//get beses belonging to other players
		for(int i = 0; i < Player.players.size(); i++){
			if(i == playerNumber)
				continue;
			Base baseToCheck = getFirstBase(i);
			Wall[] baseWalls = entitiesToEntityWalls.get(baseToCheck);
			if(baseWalls == null)
				continue;
			for(Wall w : baseWalls)
				if(w == wall)
					return true;
		}
		return false;
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
			if (wall.intersectsLine(line) && !wallBelongsToPlayer(wall, playerNumberToIgnore) && !wall.isWalkable)
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
	
	//Might Cause BUG: Only gets first base even if there are others
	public Base getFirstBase(int playerNumber){
		for(Entity e : entities)
			if(e.getClass().getSimpleName().equals("Base"))
				if(((Base) e).OwnerNumber == playerNumber)
					return ((Base) e);
		return null;
	}
	
	public Point2D getFreeEntitySpawnLocation(){
		int size = freeEntitySpawnLocations.size();
		if(size == 0)
			return null;
		
		int randInt = (int) (Math.random() * size);
		return freeEntitySpawnLocations.get(randInt);
	}
	
	public void reserveEntitySpawnLocation(Point2D p){
		for(int i = 0; i < freeEntitySpawnLocations.size(); i++)
			if(freeEntitySpawnLocations.get(i).equals(p)){
				freeEntitySpawnLocations.remove(i);
				return;
			}
		throw new IllegalArgumentException("Can't find specified location in freeEntitySpawnLocations ArrayList");
	}
	
}

