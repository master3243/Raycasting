/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import raycasting.Cooldown;
import raycasting.Direction;
import raycasting.KeyboardInput;
import raycasting.Util;
import raycasting.World;
import raycasting.map.Map;

public class Player {

	public static final int millisecondsBetweenTicks = World.millisecondsBetweenTicks;
	public static final ArrayList<Player> players = new ArrayList<>();
	private static int playerCount = 1;

	public final Map map;
	public final int[] controls;
	public final int playerNumber;
	public final Color playerColor;
	public final double movementPerSecond = 20;
	public final double rotationPerSecond = 180;
	public final double zAxisMaxRotation = 400;
	public final double zAxisrotationPerSecond = 600;
	public final double minDistanceFromWall = 2;
	public final double lengthOfPlayerWall = 3;
	public final int originalPOV = 120;
	public final double maxHealth = 100;
	public final double healthRegenPerSecond = 20;
	public final double healthRegenCooldownInSeconds = 3;
	public final double damagePerBullet = 50;
	public final double staminaDurationInSeconds = 2;
	public final double staminaMinUse = 50;
	public final double runningDistanceMultiplier = 2;
	public final double runningDistancePerSecond = runningDistanceMultiplier * movementPerSecond;
	public final double shotsPerSecond = 1;// / 2.0;

	private final Cooldown healthCooldown = new Cooldown(healthRegenCooldownInSeconds, 100);
	private final Cooldown staminaCooldown = new Cooldown(staminaDurationInSeconds, staminaMinUse);
	private final Cooldown gunCooldown = new Cooldown(shotsPerSecond, 100);
	private final double movementPerTick = movementPerSecond / World.FPS;
	private final double rotationPerTick = rotationPerSecond / World.FPS;
	private final double zAxisRotationPerTick = zAxisrotationPerSecond / World.FPS;
	private final double healthRegenPerTick = healthRegenPerSecond / World.FPS;
	private final double runningDistancePerTick = runningDistancePerSecond / World.FPS;

	private int pov = originalPOV;
	private double x = 0;
	private double y = 0;
	private Direction lookingDirection = new Direction(0);
	private double lookingDirectionZAxis = 0;
	private double health = maxHealth;
	private boolean isRunning = false;
	private int moneyInBackPack = 0;
	
	public Player(Map map, Color playerColor, int[] controls) {
		this.map = map;
		this.controls = controls;
		this.playerColor = playerColor;
		players.add(this);
		playerNumber = playerCount;
		playerCount++;
	}

	public Direction getLookingDirection() {
		return new Direction(lookingDirection);
	}

	public double getLookingDirectionZAxis() {
		return lookingDirectionZAxis;
	}

	public Point2D getPoint() {
		return new Point2D.Double(x, y);
	}

	
	public double getMovementInOneTick() {
		if (isRunning)
			return runningDistancePerTick;
		else
			return movementPerTick;
	}

	public double getDegreeBetweenRays() {
		return (double) pov / World.width_resolution;
	}

	public int getFOV() {
		return pov;
	}

	public double getHealth() {
		return health;
	}

	public void setPoint(Point2D point) {
		x = point.getX();
		y = point.getY();
	}

	public void setLookingDirection(Direction lookingDirection) {
		this.lookingDirection = lookingDirection;
	}

	public void setLookingDirectionZAxis(double newValue) {
		this.lookingDirectionZAxis = newValue;
	}

	public void moveOneFrame(Direction direction) {
		double changeInX = getMovementInOneTick() * Math.cos(direction.getRadValue());
		int signOfChangeInX = (int) Math.signum(changeInX);
		double minDistanceFromWallInX = minDistanceFromWall * signOfChangeInX;

		double changeInY = getMovementInOneTick() * Math.sin(direction.getRadValue());
		int signOfChangeInY = (int) Math.signum(changeInY);
		double minDistanceFromWallInY = minDistanceFromWall * signOfChangeInY;

		Point2D newXPos = new Point2D.Double(x + changeInX + minDistanceFromWallInX, y);
		Point2D newYPos = new Point2D.Double(x, y + changeInY + minDistanceFromWallInY);

		if (!map.canMove(getPoint(), newXPos, this))
			changeInX = 0;
		if (!map.canMove(getPoint(), newYPos, this))
			changeInY = 0;

		x += changeInX;
		y += changeInY;
	}

	public void moveOneFrameForward() {
		Direction movementDirection = getLookingDirection().getDirectionAddedToThis(0);
		moveOneFrame(movementDirection);
	}

	public void moveOneFrameBackward() {
		Direction movementDirection = getLookingDirection().getDirectionAddedToThis(180);
		moveOneFrame(movementDirection);
	}

	public void moveOneFrameRight() {
		Direction movementDirection = getLookingDirection().getDirectionAddedToThis(270);
		moveOneFrame(movementDirection);
	}

	public void moveOneFrameLeft() {
		Direction movementDirection = getLookingDirection().getDirectionAddedToThis(90);
		moveOneFrame(movementDirection);
	}

	public void rotateOneFrameLeft() {
		Direction lookingDirection = getLookingDirection().getDirectionAddedToThis(rotationPerTick);
		setLookingDirection(lookingDirection);
	}

	public void rotateOneFrameRight() {
		Direction lookingDirection = getLookingDirection().getDirectionAddedToThis(-1 * rotationPerTick);
		setLookingDirection(lookingDirection);
	}

	public void lookUpOneFrame() {
		if (lookingDirectionZAxis < zAxisMaxRotation)
			lookingDirectionZAxis += zAxisRotationPerTick;
	}

	public void lookDownOneFrame() {
		if (lookingDirectionZAxis > -zAxisMaxRotation)
			lookingDirectionZAxis -= zAxisRotationPerTick;
	}

	private void healthUpdate() {
		healthCooldown.increase();
		if (healthCooldown.canUse())
			healthIncrease();
	}

	private void healthIncrease() {
		health += healthRegenPerTick;
		if (health > maxHealth)
			health = maxHealth;
	}

	private void staminaUpdate(boolean buttonActive) {
		pov = originalPOV;
		if (buttonActive && staminaCooldown.canUse()) {
			staminaCooldown.decrease();
			isRunning = true;
			pov += 20;
		} else {
			staminaCooldown.increase();
			isRunning = false;
		}
	}

	private void shoot(boolean buttonActive) {
		if (buttonActive && gunCooldown.canUse()) {
			gunCooldown.empty();
			Line2D inFront = Util.getRayLine(getPoint(), getLookingDirection());
			Player nearestPlayer = map.getNearestInSightPlayer(inFront, this);
			if (nearestPlayer != null)
				nearestPlayer.haveBeenShot();
		} else {
			gunCooldown.increase();
		}
	}

	public void haveBeenShot() {
		healthCooldown.empty();
		health -= damagePerBullet;
	}
	
	public int getMoenyInBP(){
		return moneyInBackPack;
	}
	
	public void setMoneyInBP(int money){
		moneyInBackPack = money;
	}
	
	public void increaseMoneyInBP(int amount){
		moneyInBackPack += amount;
	}
	
	public void updatePlayer(KeyboardInput KB) {

		healthUpdate();
		staminaUpdate(KB.keyDown(controls[8]));
		shoot(KB.keyDown(controls[9]));

		if (KB.keyDown(controls[0]))
			moveOneFrameForward();
		if (KB.keyDown(controls[1]))
			moveOneFrameLeft();
		if (KB.keyDown(controls[2]))
			moveOneFrameBackward();
		if (KB.keyDown(controls[3]))
			moveOneFrameRight();
		if (KB.keyDown(controls[4]))
			lookUpOneFrame();
		if (KB.keyDown(controls[5]))
			rotateOneFrameLeft();
		if (KB.keyDown(controls[6]))
			lookDownOneFrame();
		if (KB.keyDown(controls[7]))
			rotateOneFrameRight();

	}

}
