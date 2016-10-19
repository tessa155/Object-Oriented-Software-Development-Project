/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract public class Monster extends Unit{

	private Player player;
	private double calculatedX;
	private double calculatedY;
	
	private boolean isDead = false;
	
	public Monster(double x, double y, Player player)
	throws SlickException
	{
		super(x,y);
		this.player = player;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}


	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getCalculatedX() {
		return calculatedX;
	}

	public void setCalculatedX(double calculatedX) {
		this.calculatedX = calculatedX;
	}

	public double getCalculatedY() {
		return calculatedY;
	}

	public void setCalculatedY(double calculatedY) {
		this.calculatedY = calculatedY;
	}
	
    /** update this monster object
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     */	  	
	public abstract void update(World world, int delta);
	

    /** calculate new position using a specific algorithm
     * @param delta Time passed since last frame (milliseconds).
     */	
	public void calculateAlgo(int delta){
		double amount;
		
		if(this instanceof AggreMonster){
			amount = AggreMonster.SPEED *(1/Math.sqrt(2))* delta;
		}else{
			amount = PassiveMonster.SPEED * delta;
		}
		
		double distx = Math.abs(getX()-player.getX());
		double disty = Math.abs(getY()-player.getY());
		
		double disTotal = Math.sqrt(distx*distx + disty*disty);
		
		if(this instanceof AggreMonster){
			
			if(getX()-player.getX()>0){
				calculatedX = getX()-distx/disTotal*amount;
			}else{
				calculatedX = getX()+distx/disTotal*amount;	
			}
			
			if(getY()-player.getY()>0){
				calculatedY = getY()-disty/disTotal*amount;
			}else{
				calculatedY = getY()+disty/disTotal*amount;	
			}
			
		}
		
		if(this instanceof PassiveMonster){
			if(getX()-player.getX()>0){
				calculatedX = getX()+distx/disTotal*amount;
			}else{
				calculatedX = getX()-distx/disTotal*amount;	
			}
			
			if(getY()-player.getY()>0){
				calculatedY = getY()+disty/disTotal*amount;
			}else{
				calculatedY = getY()-disty/disTotal*amount;	
			}			
			
		}
		
	}
	
    /** move this monster object in the world
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     * @param new_x new value x
     * @param new_y new value y
     */		
	public void move(World world, int delta, double new_x, double new_y) {
        // Move the player by dir_x, dir_y, as a multiple of delta * speed
        if(!world.terrainBlocks(new_x,getY())){
            setX(new_x);
        }if(!world.terrainBlocks(getX(),new_y)){
            setY(new_y);
        }
	
	}

}
