package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ShotImage extends Image{
	
	int x, y;
	
	public ShotImage(String msg, int x, int y) throws SlickException {
		super(msg);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
