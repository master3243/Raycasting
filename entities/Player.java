/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.entities;

import java.awt.Color;
import java.awt.geom.Point2D;

import raycasting.Direction;
import raycasting.World;
import raycasting.keyboard.KeyboardInput;
import raycasting.map.Map;

public class Player {

	private double x = 0;
	private double y = 0;
	private Direction lookingDirection = new Direction(0);
	private double lookingDirectionZAxis = 0;
	private Map map;
	private int[] controls;
	private double staminaPercentage = 100;
	private boolean isRunning = false;
	public int pov = originalPOV;
	
	
	private double movementInOneSecond = 20;
	private double rotationInOneSecond = 180;
	private double movementInOneTick = movementInOneSecond / World.FPS;
	private double rotationInOneTick = rotationInOneSecond / World.FPS;
	
	private double MIN_DISTANCE_FROM_WALL = 2;
	
	private double staminaDurationInSeconds = 5;
	private double staminaDurationInTicks = staminaDurationInSeconds * World.FPS;
	private double runningDistanceMultiplier = 2;
	private double runningDistanceInOneSecond = runningDistanceMultiplier * movementInOneSecond;
	private double runningDistanceInOneTick = runningDistanceInOneSecond / World.FPS;
//	private double cooldownDurationInSeconds = 1;
	
	
	public static final int originalPOV = 120;
	public static final int millisecondsBetweenTicks = World.millisecondsBetweenTicks;
	public int playerNumber;
	public Color playerColor;
	
	public Player(Map map, int num, Color playerColor, int[] controls) {
		this.map = map;
		playerNumber = num;
		this.controls = controls;
		this.playerColor = playerColor;
	}

	public void setPoint(Point2D point) {
		x = point.getX();
		y = point.getY();
	}

	public void setLookingDirection(Direction lookingDirection) {
		this.lookingDirection = lookingDirection;
	}

	public Direction getLookingDirection() {
		return lookingDirection;
	}

	public void setLookingDirectionZAxis(double newValue) {
		this.lookingDirectionZAxis = newValue;
	}

	public double getLookingDirectionZAxis() {
		return lookingDirectionZAxis;
	}

	public Point2D getPoint() {
		return new Point2D.Double(x, y);
	}
	
	public double getMovementInOneTick(){
		if(isRunning)
			return runningDistanceInOneTick;
		else
			return movementInOneTick;
	}
	
	public double getDegreeBetweenRays(){
		return pov * 1.0 / World.width_resolution;
	}

	public void moveOneFrame(Direction direction) {
		double changeInX = getMovementInOneTick() * Math.cos(Math.toRadians(direction.getDirectionNumber()));
		int signOfChangeInX = (int) Math.signum(changeInX);
		double minDistanceFromWallInX = MIN_DISTANCE_FROM_WALL * signOfChangeInX;
		
		double changeInY = getMovementInOneTick() * Math.sin(Math.toRadians(direction.getDirectionNumber()));
		int signOfChangeInY = (int) Math.signum(changeInY);
		double minDistanceFromWallInY = MIN_DISTANCE_FROM_WALL * signOfChangeInY;

		Point2D newXPos = new Point2D.Double(x + changeInX + minDistanceFromWallInX, y);
		Point2D newYPos = new Point2D.Double(x, y + changeInY + minDistanceFromWallInY);

		if (!map.canMove(getPoint(), newXPos))
			changeInX = 0;
		if (!map.canMove(getPoint(), newYPos))
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
		getLookingDirection().addDirection(rotationInOneTick);
	}

	public void rotateOneFrameRight() {
		getLookingDirection().addDirection(-1 * rotationInOneTick);
	}
	
	public void lookUpOneFrame(){
		if (lookingDirectionZAxis > -350)
			lookingDirectionZAxis += -10;
	}
	
	public void lookDownOneFrame(){
		if (lookingDirectionZAxis < 400)
			lookingDirectionZAxis += 10;
	}
	
	private void staminaUpdate(boolean buttonActive){
		pov = originalPOV;
		if(buttonActive && staminaPercentage > 0){
			isRunning = true;
			decreaseStamina();
			pov += 20;
		} else {
			increaseStamina();
			isRunning = false;
		}
	}
	
	private void increaseStamina(){
		staminaPercentage += 100 / staminaDurationInTicks;
		if(staminaPercentage > 100)
			staminaPercentage = 100;
	}
	
	private void decreaseStamina(){
		staminaPercentage -= 100 / staminaDurationInTicks;
		if(staminaPercentage < 0)
			staminaPercentage = 0;
	}

	public void updatePlayer(KeyboardInput KB) {
		if(KB.keyDown(controls[8]))
			staminaUpdate(true);
		else
			staminaUpdate(false);
		
		if (KB.keyDown(controls[0]))
			moveOneFrameForward();
		if (KB.keyDown(controls[1]))
			moveOneFrameLeft();
		if (KB.keyDown(controls[2]))
			moveOneFrameBackward();
		if (KB.keyDown(controls[3]))
			moveOneFrameRight();
		if (KB.keyDown(controls[4]))
			rotateOneFrameRight();
		if (KB.keyDown(controls[5]))
			rotateOneFrameLeft();
		if (KB.keyDown(controls[6]))
			lookDownOneFrame();
		if (KB.keyDown(controls[7]))
			lookUpOneFrame();
	}

}
