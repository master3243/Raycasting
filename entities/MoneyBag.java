/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

import raycasting.Main;

public class MoneyBag extends Entity {
	
	private int amount;
	
	public MoneyBag(Point2D loc, int size, Color col, int amount) {
		super(loc, size, col);
		this.amount = amount;
	}

	@Override
	public void playerTouch(Player p) {
		p.increaseMoneyInBP(amount);
		Main.map.removeEntity(this);
	}

}
