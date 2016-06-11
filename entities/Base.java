/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Base extends Entity {
	
	private int money = 0;
	public final int OwnerNumber;
	
	public Base(Point2D loc, int size, Color col, int OwnerNumber) {
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
	}

	@Override
	public double getWaitTime() {
		return 5;
	}

	@Override
	public String getProperties() {
		return "Base" + OwnerNumber;
	}

}
