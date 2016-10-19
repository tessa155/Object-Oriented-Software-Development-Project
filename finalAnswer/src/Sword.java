/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sword extends Item{
	public final String IMG_PATH = RPG.ASSETS_PATH+"/items/sword.png";
	
	public Sword(double x, double y)
	throws SlickException
	{
		super(x,y);
		setImg(new Image(IMG_PATH));
	}

	@Override
    /** increase damage of player
     * @param player Player object being affected
     */	
	public void affect(Player player) {
		player.setDamage(player.getDamage()+30);
		
	}

}
