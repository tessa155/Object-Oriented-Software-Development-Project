/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Tessa(Hyeri) Song <songt>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class GameObject {
	private double x;
	private double y;
	private Image img = null;
	
	public GameObject(double x, double y)
	throws SlickException{
		this.x = x;
		this.y = y;

	}
	
	public Image getImg() {
		return img;
	}


	public void setImg(Image img) {
		this.img = img;
	}


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

    /** render this object
     */
	public void render(){
		//only player overrides this.
		img.drawCentered((int)x, (int)y);
	}
	
    /** calculate the distance between this and object of GameObject
     * @param o the object of GameObject 
     */
	public double disTo(GameObject o){
		double dis = Math.sqrt((o.x-x)*(o.x-x) + (o.y-y)*(o.y-y));
		return dis;
	}
	
	
}
