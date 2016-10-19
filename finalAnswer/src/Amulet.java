
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Amulet extends Item{
	public final String IMG_PATH = RPG.ASSETS_PATH+"/items/amulet.png";
	
	public Amulet(double x, double y) 
	throws SlickException
	{
		super(x,y);
		setImg(new Image(IMG_PATH));
	}

	@Override
    /** increase hp_max of player
     * @param player Player object being affected
     */	
	public void affect(Player player) {
		player.setHp_max(player.getHp_max()+80);
		player.setHp(player.getHp()+80);
		
	}

}
