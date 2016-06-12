/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

import raycasting.Main;
import raycasting.World;

public class Base extends Entity {

	public static final int size = 5;
	public static final Color col = new Color(120, 100, 122);
	public static final int destructInSeconds = 2;
	
	private static final int destructInTicks = destructInSeconds * World.FPS;
	
	
	private int money = 0;
	public final int OwnerNumber;
	
	public Base(Point2D loc, int OwnerNumber) {
		super(loc, size, col);
		this.OwnerNumber = OwnerNumber;
	}

	public void increaseMoney(int amount){
		money += amount;
	}
	
	public int getMoney(){
		return money;
	}

	@Override
	public void playerTouch(int playerNumber) {
		if(playerNumber == OwnerNumber){
			Player p = Player.players.get(playerNumber);
			money += p.getMoenyInBP();
			p.setMoneyInBP(0);
		}
		else
			updateDestructTimer();
	}
	
	private int touchStartTick = 0;
	private int touchLastTick = 0;
	private void updateDestructTimer(){
		int currentTick = Main.gameEngine.getTicksSinceStart();
		if(Main.gameEngine.getTicksSinceStart() - 1 != touchLastTick){
			touchStartTick = currentTick;
			touchLastTick = currentTick;
		}
		else
			touchLastTick++;
		
		if(touchLastTick - touchStartTick == destructInTicks)
			Main.gameEngine.removeEntity(this);
		
	}

	@Override
	public double getWaitTime() {
		return 15;
	}

	@Override
	public String getProperties() {
		return "Base$" + OwnerNumber;
	}

	public static boolean belongsToThis(String properties) {
		return properties.startsWith("Base");
	}

	public static Base returnObjectWithProperties(String properties){
		int ownerNumber = Integer.parseInt(properties.substring(5));
		return new Base(new Point2D.Double(), ownerNumber);
	}

}
