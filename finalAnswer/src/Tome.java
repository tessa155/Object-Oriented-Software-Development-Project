/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tome extends Item{
	public final String IMG_PATH = RPG.ASSETS_PATH+"/items/book.png";
	
	public Tome(double x, double y)
	throws SlickException
	{
		super(x,y);
		setImg(new Image(IMG_PATH));
	}

	@Override
    /** decrease cooldown of player
     * @param player Player object being affected
     */	
	public void affect(Player player) {
		player.setCooldown(player.getCooldown()-300);
	}

}
