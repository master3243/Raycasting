/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting.old;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.Timer;

public class Player {

	private double x = 0;
	private double y = 0;
	private Direction lookingDirection = new Direction(0);
	private Map map;
	private double MIN_DISTANCE_FROM_WALL = 2;
	
	public int millisecondsBetweenTicks = World.millisecondsBetweenTicks;

	private double movementInOneSecond = 20;
	private double movementInOneTick = movementInOneSecond / World.FPS;
	private double rotationInOneSecond = 180;
	private double rotationInOneTick = rotationInOneSecond / World.FPS;
	
	public Player(){
		this(null);
	}
	
	public Player(Map map){
		initTimers();
		this.map = map;
	}
	
	public void setPoint(Point2D point) {
		x = point.getX();
		y = point.getY();
	}

	public void setLookingDirection(Direction lookingDirection) {
		this.lookingDirection = lookingDirection;
	}

	public void addToLookingDirection(double degrees) {
		Direction newDirection = getLookingDirection();
		newDirection.addDirection(new Direction(degrees));
		setLookingDirection(newDirection);
	}

	public Point2D getPoint() {
		return new Point2D.Double(x, y);
	}

	public Direction getLookingDirection() {
		return lookingDirection;
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
		
		if(!map.canMove(getPoint(), newXPos))
			changeInX = 0;
		if(!map.canMove(getPoint(), newYPos))
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

	private Timer repeatMovingForward;
	private Timer repeatMovingBackward;
	private Timer repeatMovingLeft;
	private Timer repeatMovingRight;
	private Timer repeatRotatingLeft;
	private Timer repeatRotatingRight;

	private void initTimers() {
		repeatMovingForward = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				moveOneFrameForward();
			}
		});
		repeatMovingForward.setInitialDelay(0);
		
		repeatMovingBackward = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				moveOneFrameBackward();
			}
		});
		repeatMovingBackward.setInitialDelay(0);
		
		repeatMovingLeft = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				moveOneFrameLeft();
			}
		});
		repeatMovingLeft.setInitialDelay(0);
		
		repeatMovingRight = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				moveOneFrameRight();
			}
		});
		repeatMovingRight.setInitialDelay(0);
		
		repeatRotatingLeft = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rotateOneFrameLeft();
			}
		});
		repeatRotatingLeft.setInitialDelay(0);
		
		repeatRotatingRight = new Timer(millisecondsBetweenTicks, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rotateOneFrameRight();
			}
		});
		repeatRotatingRight.setInitialDelay(0);
		
	}

	public void startMovingForward() {
		repeatMovingForward.start();
	}

	public void startMovingBackward() {
		repeatMovingBackward.start();
	}

	public void startMovingRight() {
		repeatMovingRight.start();
	}

	public void startMovingLeft() {
		repeatMovingLeft.start();
	}

	public void startRotatingLeft() {
		repeatRotatingLeft.start();
	}

	public void startRotatingRight() {
		repeatRotatingRight.start();
	}

	public void stopMovingForward() {
		repeatMovingForward.stop();
	}

	public void stopMovingBackward() {
		repeatMovingBackward.stop();
	}

	public void stopMovingRight() {
		repeatMovingRight.stop();
	}

	public void stopMovingLeft() {
		repeatMovingLeft.stop();
	}

	public void stopRotatinggLeft() {
		repeatRotatingLeft.stop();
	}

	public void stopRotatingRight() {
		repeatMovingRight.stop();
	}
}
