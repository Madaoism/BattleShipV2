package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity{

	public Player(float x, float y) throws SlickException{
		super(x, y);
				
		// Complete relative path to create frames for animation of the entity
		Image[] movementUp = {new Image("data/"),
				new Image("data/")};
		
		Image[] movementDown = {new Image("data/"),
				new Image("data/")};
		
		Image[] movementLeft = {new Image("data/"),
				new Image("data/")};
		
		Image[] movementRight = {new Image("data/"),
				new Image("data/")};
		
		Image[] noMovement = {new Image("data/")};
		
		// Must have the same array length as the image arrays above
		// Represent the during of each frame in milliseconds
		int [] duration = {100, 100};

		int [] durationIdle = {100};
		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);
		idle = new Animation(noMovement, durationIdle, false);

        sprite = right;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		Input input = container.getInput();
		float fdelta=delta*speed;
		
		if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))) {
			sprite = up;
			if (true) {
				sprite.update(delta);
				// The lower the delta the slowest the sprite will animate.
				y -= fdelta;
			}
		}
	
		else if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))) {
			sprite = down;
			if (true) {
				sprite.update(delta);
				y += fdelta;
			}
		}
	
		else if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))) {
			sprite = left;
			if (true) {
				sprite.update(delta);
				x -= fdelta;
			}	
		}
	
		else if ( (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))) {
			sprite = right;
			if (true) {
				sprite.update(delta);
				x += fdelta;
			}
		}
		
	}

}
