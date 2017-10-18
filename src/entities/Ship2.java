package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ship2 extends BattleShip{

	public Ship2(float x, float y) throws SlickException {
		super(x, y);
		shipLength = 4;
		Image[] image = { new Image("data/ship2.png") };
		int[] duration = { 100 };
		selectedAni = new Animation( new Image[] {new Image("data/ship2selected.png")}, new int[] {100}, false);
		horizontal = new Animation(image, duration, false);
		Image i = new Image("data/ship2flipped.png");
		vertical = new Animation( new Image[] {i}, new int[] {100}, false);
		sprite = horizontal;
		shipHealth = shipLength;
		
		shipLife = new int[shipLength];
		
	}
	
	
	
	

}
