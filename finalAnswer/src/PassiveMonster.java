/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.Random;

public abstract class PassiveMonster extends Monster{
	
	public static final int STAY = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int UP_RIGHT = 5;
	public static final int DOWN_RIGHT = 6;
	public static final int DOWN_LEFT = 7;
	public static final int UP_LEFT = 8;
	
	private boolean isAttacked = false;
	public static final double SPEED = 0.2;
	private int direction;
	private int runCooldownTimer = 0;
	
	public PassiveMonster(double x, double y, Player player) 
	throws SlickException
	{
		super(x,y,player);
	}
	
	public int getRunCooldownTimer() {
		return runCooldownTimer;
	}

	public void setRunCooldownTimer(int runCooldownTimer) {
		this.runCooldownTimer = runCooldownTimer;
	}

	public boolean getIsAttacked() {
		return isAttacked;
	}

	public void setIsAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}
	
    /** runaway from the player if attacked
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     * @param RUNCOOLDOWN runcooldown value of a specific subclass
     */	
	public void runAway(World world, int delta, int RUNCOOLDOWN){
		if(isAttacked && runCooldownTimer < RUNCOOLDOWN){
			calculateAlgo(delta);
			move(world, delta, getCalculatedX(), getCalculatedY());
			runCooldownTimer+=delta;
		}else if(isAttacked && runCooldownTimer>=RUNCOOLDOWN){
			runCooldownTimer = 0;
			isAttacked = false;
		}
	}
	
    /** do roam on the world map by checking the picked direction
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     * @param direction direction constant picked
     */		
	public void doRoam(World world, int delta, int direction){
		double new_x = getX(), new_y = getY();
		
		//figure out new_x, first
		if(direction == UP_RIGHT || direction == RIGHT || direction == DOWN_RIGHT){
			new_x = getX()+delta*SPEED;
		}else if(direction == UP_LEFT || direction == LEFT || direction == DOWN_LEFT){
			new_x = getX()-delta*SPEED;
		}
		
		//new_y
		if(direction == DOWN_LEFT || direction == DOWN || direction == DOWN_RIGHT){
			new_y = getY()+delta*SPEED;
		}else if(direction == UP_LEFT || direction == UP || direction == UP_RIGHT){
			new_y = getY()-delta*SPEED;
		}
		
		move(world, delta, new_x, new_y);
		
	}
	
    /** pick direction
     * @return return the picked direction
     */		
	public int pickDirection(){
		Random rand = new Random(); 
		int direction = rand.nextInt(9);
		return direction;
	}
	
    /** roam on the world map
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     * @param COOLDOWN cooldown value of a specific subclass
     */		
	public void roam(World world, int delta, int COOLDOWN){
		if (isAttacked == false){
			if(getCooldownTimer() == 0){
				direction = pickDirection();
				doRoam(world, delta, direction);
				setCooldownTimer(delta);
			}else if(getCooldownTimer() < COOLDOWN){
				doRoam(world, delta, direction);
				setCooldownTimer(getCooldownTimer()+delta);
			}else{
				setCooldownTimer(0);
			}
		}
	}


}
