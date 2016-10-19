/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Elvira extends Villager{
	
	public static final int HP_MAX = 1;
	public static final String IMG_PATH = RPG.ASSETS_PATH+"/units/shaman.png";
	public static final String NAME = "Elvira";
	

	public Elvira(double x, double y)
	throws SlickException
	{
		super(x,y);
		setHp(HP_MAX);
		setImg(new Image(IMG_PATH));
	}

	
    /** render call renderHelathBar method
     * @param g The current Graphics context.
     */
	public void callRenderHealthBar(Graphics g) {
		super.renderHealthBar(g, NAME, HP_MAX);		
	}


    /** interact with player.
     * @param player the Player object being interacted
     * @param delta Time passed since last frame (milliseconds).
     * @param t check if T is pressed
     */
	public void interact(Player player, int delta, boolean t) {
		if (t){
			if(disTo(player)<World.DIST){
				if(isStarted() == false){
					if(player.getHp() < player.getHp_max()){
						player.setHp(player.getHp_max());
						setLine("You're looking much healthier now");
					}else{
						setLine("Return to me if you ever need healing");
					}
					setStarted(true);
				}

			}
		}
		if (isStarted()){
			if(getCooldownTimer()<Villager.COOLDOWN){
				setCooldownTimer(getCooldownTimer()+delta);
			}else{
				setCooldownTimer(0);
				setStarted(false);

			}
		}
		
	}



}
