package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class BattleShip extends Entity {

	protected int xCo = 5, yCo = 5;
	protected int shipNumber;
	protected int shipLength;
	protected int shipHealth;
	protected boolean alive = true;
	protected int[] shipLife;
	protected float boardX = 1 * 64, boardY = 0 * 64;
	protected float dragBoxX = 13 * 64, dragBoxY = 1 * 64;
	boolean selected;
	boolean dropped;
	boolean flipped;
	boolean coincident ;
	boolean placed;
	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}
	Animation horizontal, vertical, selectedAni, unselectedAni;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDropped() {
		return dropped;
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

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public void isCoincident(BattleShip ship) {
		
	}
	
	public void setCoincident(boolean coincident) {
		this.coincident = coincident;
	}

	public BattleShip(float x, float y) throws SlickException {
		super(x, y);
		selected = false;
		
	}
	
//	public BattleShip(float x, float y, int length) throws SlickException {
//		super(x, y);
//		selected = false;
//		this.shipHealth = length;
//	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

	}

	@Override
	public void render() {
		if (!placed) {
			if (selected) {
				selectedAni.draw(INIT_X, INIT_Y);
			} else {
				horizontal.draw(INIT_X, INIT_Y);
			}
		}
		
		if (flipped) {
			sprite = vertical;
		} else {
			sprite = horizontal;
		}
		sprite.draw(x, y);
	}

	public int getShipLength() {
		return shipLength;
	}
	
	public void setShipLength(int shipLength) {
		this.shipLength = shipLength;
	}
	public int getShipHealth() {
		return shipHealth;
	}
	public void setShipHealth(int shipHealth){
		this.shipHealth = shipHealth;
	}
	public boolean getAlive(){
		return alive;
	}
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	public boolean isAlive(){
		return alive;
	}
	public boolean hit() { //takes 1 away from health, returns boolean whether the ship has been sunk
		shipHealth -= 1;
		if (shipHealth <=0) {
			alive = false;
		}
		return alive;
	}
}
