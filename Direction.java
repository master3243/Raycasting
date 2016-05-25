/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting;

public class Direction {
	
	
	public static final int FULL_CIRCLE = 360;
	public static final int HALF_CIRCLE = 180;
	public static final int QUARTER_CIRCLE = 90;
	
	public static final int HALF_LEFT = QUARTER_CIRCLE / 2;
	public static final int LEFT = QUARTER_CIRCLE;
	public static final int HALF_RIGHT = FULL_CIRCLE - HALF_LEFT;
	public static final int RIGHT = FULL_CIRCLE - LEFT;
	
	
	private double direction;
	
	public Direction(double direction){
		setDirection(direction);
	}
	
	public Direction(Direction direction){
		this(direction.getValue());
	}
	
	public double getValue() {
		return direction;
	}

	public double getRadValue() {
		return Math.toRadians(direction);
	}

	public void setDirection(double direction) {
		if(direction >= 0)
			this.direction = direction % 360;
		else
			setDirection(360 + direction);
	}
	
	public void addDirection(Direction added){
		setDirection(getValue() + added.direction);
	}
	
	public void addDirection(double added){
		setDirection(getValue() + added);
	}
	
	public void subtractDirection(Direction subtracted){
		setDirection(getValue() - subtracted.getValue());
	}
	
	public boolean facingDown(){
		return direction > 180;
	}
	
	public boolean facingRight(){
		return direction < 90 || direction > 270;
	}
	
	public Direction getDirectionAddedToThis(Direction added){
		return new Direction(getValue() + added.getValue());
	}
	
	public Direction getDirectionAddedToThis(double added){
		return new Direction(getValue() + added);
	}
	
}
