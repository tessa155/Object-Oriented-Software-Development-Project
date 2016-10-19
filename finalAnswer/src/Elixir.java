/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Elixir extends Item{
	
	public final String IMG_PATH = RPG.ASSETS_PATH+"/items/elixir.png";
	
	public Elixir(double x, double y)
	throws SlickException
	{
		super(x,y);
		setImg(new Image(IMG_PATH));
	}

	@Override
    /** do nothing
     * @param player Player object being affected
     */	
	public void affect(Player player) {
		return;
	}

}
