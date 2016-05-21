/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.entities;

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
	private double MIN_DISTANCE_FROM_WALL = 2;
	private int[] controls;
	
	private double movementInOneSecond = 20;
	private double rotationInOneSecond = 180;
	private double movementInOneTick = movementInOneSecond / World.FPS;
	private double rotationInOneTick = rotationInOneSecond / World.FPS;

	public static int millisecondsBetweenTicks = World.millisecondsBetweenTicks;
	public double degreeBetweenRays = World.pov * 1.0 / World.width_resolution;
	public int playerNumber;
	
	public Player(Map map, int num, int[] controls) {
		this.map = map;
		playerNumber = num;
		this.controls = controls;
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

	public void moveOneFrame(Direction direction) {
		double changeInX = movementInOneTick * Math.cos(Math.toRadians(direction.getDirectionNumber()));
		int signOfChangeInX = (int) Math.signum(changeInX);
		double minDistanceFromWallInX = MIN_DISTANCE_FROM_WALL * signOfChangeInX;
		double changeInY = movementInOneTick * Math.sin(Math.toRadians(direction.getDirectionNumber()));
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

	public void updatePlayer(KeyboardInput KB) {

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
			if (lookingDirectionZAxis < 300)
				lookingDirectionZAxis += 10;
		if (KB.keyDown(controls[7]))
			if (lookingDirectionZAxis > -300)
				lookingDirectionZAxis += -10;
	}

}
