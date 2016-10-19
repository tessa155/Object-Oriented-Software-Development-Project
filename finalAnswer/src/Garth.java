/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Garth extends Villager{

	public static final int HP_MAX = 1;
	public static final String IMG_PATH = RPG.ASSETS_PATH+"/units/peasant.png";
	public static final String NAME = "Garth";
	
	public Garth(double x, double y)
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

	    	
    /** check if player has amulet
     * @param player the player object being affected
     * @return boolean value
     */	    	
	public boolean hasPlayerAmulet(Player player){
	    for(Item item : player.getInventory()){
	    	if(item instanceof Amulet)
	    		return true;
	    }
	    return false;
	}
	
    /** check if player has amulet
     * @param player the player object being affected
     * @return boolean value
     */	  
	public boolean hasPlayerSword(Player player){
	    for(Item item : player.getInventory()){
	    	if(item instanceof Sword)
	    		return true;
	    }
	    return false;
		
	}
	
    /** check if player has amulet
     * @param player the player object being affected
     * @return boolean value
     */	  
	public boolean hasPlayerTome(Player player){
	    for(Item item : player.getInventory()){
	    	if(item instanceof Tome)
	    		return true;
	    }
	    return false;
		
	}
	

    /** interact with player.
     * @param player the Player object being interacted
     * @param delta Time passed since last frame (milliseconds).
     * @param t check if T is pressed
     */
	public void interact(Player player, int delta, boolean t) {
		if(t){
			if(disTo(player)<World.DIST){
				if(isStarted() == false){
					if(hasPlayerAmulet(player) == false){
						setLine("Find the Amulet of Vitality, across the river to the west.");
					}else if(hasPlayerSword(player) == false){
						setLine("Find the Sword of Strength - cross the river and back, on the east side.");
					}else if(hasPlayerTome(player) == false){
						setLine("Find the Tome of Agility, in the Land of Shadows.");
					}else{
						setLine("You have found all the treasure I know of.");
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
