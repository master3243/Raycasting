/*
 * Abdulrahman Alabdulkareem 782435
 * May 15, 2016
 */
package raycasting;

public class Direction {
	
	private double direction;
	
	public Direction(double direction){
		setDirection(direction);
	}
	
	public Direction(Direction direction){
		this(direction.getDirectionNumber());
	}
	
	public double getDirectionNumber() {
		return direction;
	}

	public void setDirection(double direction) {
		if(direction >= 0)
			this.direction = direction % 360;
		else
			setDirection(360 + direction);
	}
	
	public void addDirection(Direction added){
		setDirection(getDirectionNumber() + added.direction);
	}
	
	public void addDirection(double added){
		setDirection(getDirectionNumber() + added);
	}
	
	public void subtractDirection(Direction subtracted){
		setDirection(getDirectionNumber() - subtracted.getDirectionNumber());
	}
	
	public boolean facingDown(){
		return direction > 180;
	}
	
	public boolean facingRight(){
		return direction < 90 || direction > 270;
	}
	
	public Direction getDirectionAddedToThis(Direction added){
		return new Direction(getDirectionNumber() + added.getDirectionNumber());
	}
	
	public Direction getDirectionAddedToThis(double added){
		return new Direction(getDirectionNumber() + added);
	}
	
}
