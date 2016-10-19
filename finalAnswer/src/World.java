/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    private static final int PLAYER_START_X = 756, PLAYER_START_Y = 684;
    public static final int DIST = 50;
    
    private Player player = null;
    private TiledMap map = null;
    private Camera camera = null;
    
	private Villager[] arrVills = null;
    private Monster[] arrMons = null;
    private Item[] arrItems = null;
    
    
    public Villager[] getArrVills() {
		return arrVills;
	}


	public Monster[] getArrMons() {
		return arrMons;
	}


	public Item[] getArrItems() {
		return arrItems;
	}


    /** Map width, in pixels. */
    private int getMapWidth()
    {
        return map.getWidth() * getTileWidth();
    }

    /** Map height, in pixels. */
    private int getMapHeight()
    {
        return map.getHeight() * getTileHeight();
    }

    /** Tile width, in pixels. */
    private int getTileWidth()
    {
        return map.getTileWidth();
    }

    /** Tile height, in pixels. */
    private int getTileHeight()
    {
        return map.getTileHeight();
    }

    /** Create a new World object. 
     * @throws IOException */
    public World()
    throws SlickException, IOException
    {
        map = new TiledMap(RPG.ASSETS_PATH + "/map.tmx", RPG.ASSETS_PATH);
        player = new Player(PLAYER_START_X, PLAYER_START_Y);
        camera = new Camera(player, RPG.SCREEN_WIDTH, RPG.SCREEN_HEIGHT);
        
        //create all the villagers
        arrVills = new Villager[3];
        arrVills[0] = new PrinceAldric(467,679);
        arrVills[1] = new Elvira(738,549);
        arrVills[2] = new Garth(756,870); 
  
        //create all the items
        arrItems = new Item[4];
        arrItems[0] = new Amulet(965,3563);
        arrItems[1] = new Sword(4791,1253);
        arrItems[2] = new Tome(546,6707);
        arrItems[3] = new Elixir(1976,402);
        
        //create all the monsters
        arrMons = new Monster[127];
        FileReader fin = new FileReader("assets/location.txt");;
        BufferedReader in = new BufferedReader(fin);
        StringTokenizer st;
      
        for(int i = 0;i<127;i++){
        	st = new StringTokenizer(in.readLine());
        	if(i<30){
        	    arrMons[i] = new GiantBat(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),player);	
        	}else if(i<68){
      	        arrMons[i] = new Zombie(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), player);
            }else if(i<102){
      	  	    arrMons[i] = new Bandit(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),player);
      	    }else if(i<126){
      		    arrMons[i] = new Skeleton(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), player);
      	    }else{
      		    arrMons[i] = new Draelic(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),player);
      	   }  
        }
        
        fin.close();

    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(int dir_x, int dir_y, boolean t, boolean a, int delta)
    throws SlickException
    {
        player.update(this, dir_x, dir_y, t, a, delta);
        camera.update();
        
        for (Monster mon : arrMons){
        	if(mon != null)
        		mon.update(this, delta);
        }
        
        
    }
    
    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        // Render the relevant section of the map
        int x = -(camera.getMinX() % getTileWidth());
        int y = -(camera.getMinY() % getTileHeight());
        int sx = camera.getMinX() / getTileWidth();
        int sy = camera.getMinY()/ getTileHeight();
        int w = (camera.getMaxX() / getTileWidth()) - (camera.getMinX() / getTileWidth()) + 1;
        int h = (camera.getMaxY() / getTileHeight()) - (camera.getMinY() / getTileHeight()) + 1;
        map.render(x, y, sx, sy, w, h);
        
        player.renderPanel(g);
        
        // Translate the Graphics object
        g.translate(-camera.getMinX(), -camera.getMinY());
        player.render();

        
        //render villagers
        for(int i = 0;i<3;i++){
        	arrVills[i].render();
        	arrVills[i].callRenderHealthBar(g);
        	arrVills[i].renderDialogue(g);
        }
        
        //render monsters
        for(int j = 0;j<127;j++){
        	if(arrMons[j] == null || arrMons[j].isDead()){
        		arrMons[j] = null;
        	}else{
        		arrMons[j].callRenderHealthBar(g);
        		arrMons[j].render();
        	}
        }

        //render items
        for(int l = 0;l<4 ;l++){
        	if(arrItems[l] == null || arrItems[l].isTaken()){
        		arrItems[l]= null;
        	}else{
        		arrItems[l].render();
        	}
        }
    }

    /** Determines whether a particular map coordinate blocks movement.
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @return true if the coordinate blocks movement.
     */
    public boolean terrainBlocks(double x, double y)
    {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > getMapWidth() || y > getMapHeight()) {
            return true;
        }
        
        // Check the tile properties
        int tile_x = (int) x / getTileWidth();
        int tile_y = (int) y / getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return !block.equals("0");
    }

    
}
