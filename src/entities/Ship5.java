package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ship5 extends BattleShip{

	public Ship5(float x, float y) throws SlickException {
		super(x, y);
		shipLength = 2;
		Image[] image = { new Image("data/ship5.png") };
		int[] duration = { 100 };
		selectedAni = new Animation( new Image[] {new Image("data/ship5selected.png")}, new int[] {100}, false);
		horizontal = new Animation(image, duration, false);
		Image i = new Image("data/ship5flipped.png");
		vertical = new Animation( new Image[] {i}, new int[] {100}, false);
		sprite = horizontal;
		shipLife = new int[shipLength];
		shipHealth = shipLength;
		
	}
	
	
	
	

}
