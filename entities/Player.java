/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.entities;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import raycasting.Direction;
import raycasting.keyboard.KeyboardInput;
import raycasting.map.Map;
import raycasting.old.World;

public class Player {

	private double x = 0;
	private double y = 0;
	private Direction lookingDirection = new Direction(0);
	private double lookingDirectionZAxis = 0;
	private Map map;
	private double MIN_DISTANCE_FROM_WALL = 2;

	private double movementInOneSecond = 20;
	private double rotationInOneSecond = 180;
	private double movementInOneTick = movementInOneSecond / World.FPS;
	private double rotationInOneTick = rotationInOneSecond / World.FPS;

	public static int millisecondsBetweenTicks = World.millisecondsBetweenTicks;
	public double degreeBetweenRays = World.pov * 1.0 / World.width_resolution;

	public Player(Map map) {
		this.map = map;
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

		if (KB.keyDown(KeyEvent.VK_W))
			moveOneFrameForward();
		if (KB.keyDown(KeyEvent.VK_A))
			moveOneFrameLeft();
		if (KB.keyDown(KeyEvent.VK_S))
			moveOneFrameBackward();
		if (KB.keyDown(KeyEvent.VK_D))
			moveOneFrameRight();
		if (KB.keyDown(KeyEvent.VK_RIGHT))
			rotateOneFrameRight();
		if (KB.keyDown(KeyEvent.VK_LEFT))
			rotateOneFrameLeft();
		if (KB.keyDown(KeyEvent.VK_UP))
			if (lookingDirectionZAxis < 300)
				lookingDirectionZAxis += 10;
		if (KB.keyDown(KeyEvent.VK_DOWN))
			if (lookingDirectionZAxis > -300)
				lookingDirectionZAxis += -10;
	}

}
