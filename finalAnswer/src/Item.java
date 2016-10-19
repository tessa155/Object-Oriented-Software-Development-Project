/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract public class Item extends GameObject{

	private boolean taken = false; // modified by player object
	
	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public Item(double x, double y)
	throws SlickException
	{
		super(x,y);
	}
	
    /** do affect player object
     * @param player Player object being affected
     */	
	abstract public void affect(Player player);
}
