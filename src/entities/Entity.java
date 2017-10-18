package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public abstract class Entity {
	
	protected float x, y;
	protected float INIT_X, INIT_Y;
	protected int width, height;
	protected Animation up, down, left, right, idle, sprite;
	protected float speed;

	public Entity(float x, float y) throws SlickException{
		setInitPosition(x, y);
		setPositionToInit();
	}
	
	public abstract void update(GameContainer container, int delta) throws SlickException;
	
	public void render() {
		sprite.draw(x, y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getINIT_X() {
		return INIT_X;
	}

	public float getINIT_Y() {
		return INIT_Y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setInitPosition(float x, float y) {
		INIT_X = x;
		INIT_Y = y;
	}
	
	public void setPositionToInit() {
		x = INIT_X;
		y = INIT_Y;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
