/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GiantBat extends PassiveMonster{


	private static final String IMG_PATH = RPG.ASSETS_PATH+"/units/dreadbat.png";
	private static final int HP_MAX = 40;
	private static final String NAME = "Giant Bat";
	private static final int COOLDOWN = 3000;
	private static final int RUNCOOLDOWN = 5000;
	
	
	public GiantBat(double x, double y, Player player) throws SlickException{
		super(x,y, player);
		setImg(new Image(IMG_PATH));
		setHp(HP_MAX);
		
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
		roam(world, delta, COOLDOWN);
		runAway(world, delta, RUNCOOLDOWN);

		if(getHp()<=0){
			setDead(true);
		}
		
	}


}
