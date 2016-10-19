/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PrinceAldric extends Villager{
	private Elixir elixir = null;
	
	public static final int HP_MAX = 1;
	public static final String IMG_PATH = RPG.ASSETS_PATH+"/units/prince.png";
	public static final String NAME = "Aldric";
	
	public PrinceAldric(double x, double y)
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

    /** try to take elixir from player's inventory
     * @param player the player object affected
     */	    	
	public void takeElixir(Player player){
		for(int i = 0;i<4;i++){
			if(player.getInventory()[i] != null){
				if(player.getInventory()[i] instanceof Elixir){
					elixir = (Elixir)player.getInventory()[i];
					player.getInventory()[i] = null;
				}
			}
		}	
	}


    /** interact with player.
     * @param player the Player object being interacted
     * @param delta Time passed since last frame (milliseconds).
     * @param t check if T is pressed
     */
	public void interact(Player player, int delta, boolean t) {
		if (t){
			if(disTo(player)<50){
				if(isStarted() == false){
					takeElixir(player);
					if(elixir != null){
						setLine("The elixir! My father is cured! Thank you");
					}else{
						setLine("Please seek out the Elixir of Life to cure the king.");
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
