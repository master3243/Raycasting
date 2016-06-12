/*
 * Abdulrahman Alabdulkareem 782435
 * Jun 10, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

import raycasting.Main;

public class MoneyBag extends Entity {

	private static final int size = 3;
	private static final Color col = new Color(12, 100, 12);

	private int amount;

	public MoneyBag(Point2D loc, int amount) {
		super(loc, size, col);
		this.amount = amount;
	}

	@Override
	public void playerTouch(int playerNumber) {
		Player p = Player.players.get(playerNumber);
		if (p.canRecieveMoreMoneyInBP()) {
			p.increaseMoneyInBP(amount);
			Main.gameEngine.removeEntity(this);
		}
	}

	@Override
	public double getWaitTime() {
		return 3;
	}

	@Override
	public String getProperties() {
		return "MoneyBag$" + amount;
	}

	public static boolean belongsToThis(String properties) {
		return properties.startsWith("MoneyBag");
	}

	public static MoneyBag returnObjectWithProperties(String properties) {
		int amount = Integer.parseInt(properties.substring(9));
		return new MoneyBag(new Point2D.Double(), amount);
	}
}
