package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class FiringTarget extends Entity {

	protected int xCo, yCo;
	protected int size;
	protected float boardX = 1 * 64, boardY = 0 * 64;
	protected float dragBoxX = 13 * 64, dragBoxY = 1 * 64;
	boolean selected;
	boolean dropped;
//	Animation unselectedAni, selectedAni;
	Image img, selectedImg;
	public int result;
	
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	public FiringTarget(float x, float y) throws SlickException {
		super(x, y);
		selected = false;
		img = new Image("data/target.png");
		selectedImg = new Image("data/targetSelected.png");
	}
	
	

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
	}

	public void render(Graphics g) {
		if (!selected) { 
			g.drawImage(img, x, y);
		} else {
			g.drawImage(selectedImg, x, y);
		}
	}

	public int getxCo() {
		return xCo;
	}

	public void setxCo(int xCo) {
		this.xCo = xCo;
	}

	public int getyCo() {
		return yCo;
	}

	public void setyCo(int yCo) {
		this.yCo = yCo;
	}

	public void update(float mouseX, float mouseY, Input input) throws SlickException{
		
	}
	
	public void setTarget(float x, float y) {
		this.x = (int) (x/64) *64;
		this.y = (int) (y/64) *64;
	}

}
