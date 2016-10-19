/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract public class Villager extends Unit{
	
	public static final int COOLDOWN = 4000;
	private boolean isStarted = false;
	private String line = null;
	
	public Villager(double x, double y)
	throws SlickException
	{
		super(x,y);
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

    /** interact with player.
     * @param player the Player object being interacted
     * @param delta Time passed since last frame (milliseconds).
     * @param t check if T is pressed
     */
	abstract public void interact(Player player, int delta, boolean t);
	
	
    /** render the dialogue
     * @param g The Slick graphics object, used for drawing.
     */
	public void renderDialogue(Graphics g){
		if(isStarted){
			//renderPanel or renderBar(common for villagers and monsters)
			//so only player overrides this.
			Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp 
			Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White

			//coordinate of the health bar
			int bar_x = (int)getX()-(g.getFont().getWidth(line)+6)/2;
			int bar_y = (int)getY()-70;
        
			//coordinate of the test(name)
			int text_x;
			int text_y = bar_y;
        
			//width and height of the health bar
			int bar_width;
			if(g.getFont().getWidth(line)+6<=70){
				bar_width = 70;
			}else{
				bar_width = g.getFont().getWidth(line)+6;
			}

			int bar_height = 20;
        
			text_x = bar_x + (bar_width - g.getFont().getWidth(line)) / 2;
			g.setColor(BAR_BG);
			g.fillRect(bar_x, bar_y, bar_width, bar_height);

			g.setColor(VALUE);
			g.drawString(line, text_x, text_y);
		}
	}

		
}
