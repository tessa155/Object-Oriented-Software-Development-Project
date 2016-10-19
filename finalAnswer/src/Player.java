/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */


import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** The character which the user plays as.
 */
public class Player extends Unit
{
	public static String IMG_PATH = RPG.ASSETS_PATH+"/units/player.png";
    private static final double SPEED = 0.55;

	private Image panel = null;
    private Image img_flipped = null;
    private Item[] inventory = null;
    
    private int cooldown = 600;
	private int damage = 26;
    private int hp_max = 10000;
    
    private boolean face_left = false;
    private boolean attacked = false;
	
    /** Creates a new Player.
     * @param image_path Path of player's image file.
     * @param x The Player's starting x location in pixels.
     * @param y The Player's starting y location in pixels.
     */
    public Player(double x, double y)
        throws SlickException
    {
    	super(x,y);
    	setHp(hp_max);
    	setImg(new Image(IMG_PATH));
   
        img_flipped = getImg().getFlippedCopy(true, false);
        inventory = new Item[4];
        panel = new Image("assets/panel.png");
        
    }
	
	public Item[] getInventory() {
		return inventory;
	}



	public int getHp_max() {
		return hp_max;
	}


	public void setHp_max(int hp_max) {
		this.hp_max = hp_max;
	}


	public int getDamage() {
		return damage;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}


	public int getCooldown() {
		return cooldown;
	}


	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}




    /** update this Player.
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param t check if T is pressed
	 * @param a check if A is pressed
     * @param delta Time passed since last frame (milliseconds).
     */
	public void update(World world, double dir_x, double dir_y, boolean t, boolean a, int delta){
		
		move(world, dir_x, dir_y, delta);
		
		attacked = false;
		for(Monster mon : world.getArrMons()){
			if(mon != null && a && disTo(mon)<World.DIST)
				attack(mon, delta, a);
		}
		
		if(attacked){
			setCooldownTimer(cooldown);
		}

		if(0 < getCooldownTimer()){
			setCooldownTimer(getCooldownTimer()-delta);
		}else{
			setCooldownTimer(0);
		}
			
		
		for (Item item : world.getArrItems()){
			if(item != null && disTo(item)<World.DIST){
				pickUp(item);
			}
		}
		
		for (Villager v : world.getArrVills()){
			v.interact(this, delta, t);
		}
		
		if(getHp()<=0){
			setX(738);
			setY(549);
			setHp(getHp_max());
		}
	}
	
    /** attack a monster.
     * @param mon Monster object being attacked
	 * @param delta
	 * @param a check if A is pressed
     */
    public void attack(Monster mon, int delta, boolean a){
    	if(getCooldownTimer() == 0){
    		mon.setHp(mon.getHp()-randomDamage());
    		if(mon instanceof PassiveMonster){
    			PassiveMonster pmon = (PassiveMonster)mon;
    			pmon.setIsAttacked(true);
    			pmon.setRunCooldownTimer(0);
    		}
    		attacked = true;
    	}

    }
    
    /** pick up an item.
	 * @param item item being picked up
     */
    public void pickUp(Item item){
    	//put the item in the inventory
    	boolean isPut = false;
    	for(int i = 0;i<4; i++){
    		if(inventory[i] == null && isPut == false){
    			inventory[i] = item;
    			item.setTaken(true);
    			item.affect(this);
    			isPut = true;
    		}
    	}
    }
	


    /** Move the player in a given direction.
     * Prevents the player from moving outside the map space, and also updates
     * the direction the player is facing.
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void move(World world, double dir_x, double dir_y, double delta)
    {
        if (dir_x > 0)
            this.face_left = false;
        else if (dir_x < 0)
            this.face_left = true;

        // Move the player by dir_x, dir_y, as a multiple of delta * speed
        double new_x = getX() + dir_x * delta * SPEED;
        double new_y = getY() + dir_y * delta * SPEED;

        if(!world.terrainBlocks(new_x,getY())){
            setX(new_x);
        }if(!world.terrainBlocks(getX(),new_y)){
            setY(new_y);
        }
   
    }
    
    @Override
    /** render this player.
     */
    public void render()
    {
        Image which_img;
        which_img = this.face_left ? this.img_flipped : getImg();
        which_img.drawCentered((int) getX(), (int) getY());
    }
    
    /** return random damage
     */
    public int randomDamage(){
		Random rand = new Random(); 
		int ranDamage= rand.nextInt(damage+1);
		return ranDamage;
    }

    /** Renders the player's status panel.
     * @param g The current Slick graphics context.
     */
    public void renderPanel(Graphics g)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.SCREEN_HEIGHT - RPG.PANELHEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREEN_HEIGHT - RPG.PANELHEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = getHp()+"/"+hp_max;                                 

        bar_x = 90;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANELHEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (float)getHp()/hp_max;                   
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = ""+damage+"";                                   
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = ""+cooldown+"";                                   
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANELHEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.SCREEN_HEIGHT - RPG.PANELHEIGHT
            + ((RPG.PANELHEIGHT - 72) / 2);
        
        inv_x-=33;
        inv_y+=32;
        
        for (Item player_item : inventory)               
        {
             //Render the item to (inv_x, inv_y)
        	 if(player_item != null){
        		 inv_x += 70;
        		 player_item.setX(inv_x);
        		 player_item.setY(inv_y);
        		 player_item.render();
        	 }		   
        }
    }

	@Override
    /** render health bar
     * @param g The current Graphics context.
     */
	public void callRenderHealthBar(Graphics g) {
		super.renderHealthBar(g, "Player", hp_max);
		
	}
    
    
}
