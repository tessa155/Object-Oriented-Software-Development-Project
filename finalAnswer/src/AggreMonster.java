
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class AggreMonster extends Monster{
	public static final double SPEED = 0.25;
	private boolean attacked = false;

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public AggreMonster(double x, double y, Player player) 
	throws SlickException
	{
		super(x,y,player);
	}

    /** chase the player
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     */	
	public void chase(World world, int delta){
		//algorithm 1
		if(World.DIST<= disTo(getPlayer()) && disTo(getPlayer()) <=150){
			//set CalculatedX and Y by the algorithm
			calculateAlgo(delta);
			move(world, delta, getCalculatedX(), getCalculatedY());
		}
	}
	
    /** attack the player
     * @param delta Time passed since last frame (milliseconds).
     * @param COOLDOWN the cooldown value of a specific subclass
     * @param damage the damage value of a specific subclass
     */		
	public void attack(int delta, int damage){
		//if player is within 50, attack till she dies
    	if(disTo(getPlayer())<World.DIST){
    		if(getCooldownTimer() == 0){
    			getPlayer().setHp(getPlayer().getHp()-damage);
    			attacked = true;
    		}
    	}	
	}
    /** return randomDamage
     * @param DAMAGE damage value of a specific subclass
     */
    public int randomDamage(int DAMAGE){
		Random rand = new Random(); 
		int ranDamage= rand.nextInt(DAMAGE+1);
		return ranDamage;
    }


}
