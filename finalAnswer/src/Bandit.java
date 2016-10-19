
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bandit extends AggreMonster{

	private static final String IMG_PATH = RPG.ASSETS_PATH+"/units/bandit.png";
	private static final int HP_MAX = 40;
	private static final int DAMAGE = 80;
	private static final String NAME = "Bandit";
	private static final int COOLDOWN = 200;
	
	public Bandit(double x, double y, Player player) throws SlickException{
		super(x, y, player);
		setHp(HP_MAX);
		setImg(new Image(IMG_PATH));
		setCooldownTimer(COOLDOWN);
		
	}

    /** render call renderHelathBar method
     * @param g The current Graphics context.
     */
	public void callRenderHealthBar(Graphics g) {
		super.renderHealthBar(g, NAME, HP_MAX);		
	}
	

    /** update this monster object
     * @param world The world the monster is on (to check blocking).
     * @param delta Time passed since last frame (milliseconds).
     */	
	public void update(World world, int delta) {
		chase(world, delta);
		setAttacked(false);
		attack(delta, randomDamage(DAMAGE));
		
		if(isAttacked()){
			setCooldownTimer(COOLDOWN);
		}

		if(0 < getCooldownTimer()){
			setCooldownTimer(getCooldownTimer()-delta);
		}else{
			setCooldownTimer(0);
		}
		
		if(getHp()<=0){
			setDead(true);
		}
	}
	

}
