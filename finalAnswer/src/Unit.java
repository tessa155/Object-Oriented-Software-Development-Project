/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

abstract public class Unit extends GameObject{

	private int hp;
	private int cooldownTimer = 0;
	
	
	public int getCooldownTimer() {
		return cooldownTimer;
	}

	public void setCooldownTimer(int cooldownTimer) {
		this.cooldownTimer = cooldownTimer;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	
	 
	public Unit(double x, double y)
    throws SlickException
    {
		super(x,y);
	}

    /** render call renderHelathBar method
     * @param g The current Graphics context.
     */
	public abstract void callRenderHealthBar(Graphics g);
	
	
    /** render health bar
     * @param g The current Graphics context.
     * @param NAME the name used
     * @param HP_MAX hp_max of the child object
     */
	public void renderHealthBar(Graphics g, String NAME, int HP_MAX) {
		//renderPanel or renderBar(common for villagers and monsters)
		//so only player overrides this.
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp  
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White

        //coordinate of the health bar
        int bar_x = (int)getX()-getImg().getHeight()/2;
        int bar_y = (int)getY()-50;
        
        //coordinate of the text(name)
        int text_x;
        int text_y = bar_y;
        
        //width and height of the health bar
        int bar_width;
        if(g.getFont().getWidth(NAME)+6<=70){
        	bar_width = 70;
        }else{
        	bar_width = g.getFont().getWidth(NAME)+6;
        }

        int bar_height = 20;
        
        double health_percent = (float)getHp()/HP_MAX;                   
        int hp_bar_width = (int) (bar_width * health_percent);
        
        text_x = bar_x + (bar_width - g.getFont().getWidth(NAME)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(NAME, text_x, text_y);
			
	}
	
	

}
