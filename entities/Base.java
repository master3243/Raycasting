/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Base extends Entity {
	
	private int money = 0;
	public final Player owner;
	
	public Base(Point2D loc, int size, Color col, Player player) {
		super(loc, size, col);
		owner = player;
	}

	@Override
	public void playerTouch(Player p) {
		if(p == owner){
			money += p.getMoenyInBP();
			p.setMoneyInBP(0);
		}
	}
	
	public void increaseMoney(int amount){
		money += amount;
	}
	
	public int getMoney(){
		return money;
	}

}
