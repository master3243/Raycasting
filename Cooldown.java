/*
 * Abdulrahman Alabdulkareem 782435
 * May 26, 2016
 */
package raycasting;

public class Cooldown {
	
	public final double maxDuration;
	public final double lowerLimit;
	
	private final double maxDurationInTicks;
	private double percentage = 100;
	private boolean inCooldown = false;
	
	
	public Cooldown(double durationInSeconds, double lowerLimit){
		maxDuration = durationInSeconds;
		maxDurationInTicks = maxDuration * World.FPS;
		this.lowerLimit = lowerLimit;
	}
	
	public double getDuration(){
		return maxDuration;
	}
	
	public double getPercentage(){
		return percentage;
	}

	public void increase(){
		if(percentage == 100)
			return;
		
		inCooldown = true;
		percentage += 100 / maxDurationInTicks;
		
		if(percentage > 100)
			percentage = 100;
	}
	
	public void decrease(){
		inCooldown = false;
		percentage -= 100 / maxDurationInTicks;
		
		if(percentage < 0)
			percentage = 0;
	}
	
	public void empty(){
		percentage = 0;
	}
	
	public boolean canUse(){
		if(percentage >= lowerLimit)
			return true;
		if(percentage > 0 && !inCooldown)
			return true;
		
		return false;
	}
	
}
