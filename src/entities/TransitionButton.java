package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TransitionButton extends Button{

	Image img;
	
	public TransitionButton(float x, float y) throws SlickException {
		super(x, y);
		img = new Image("data/transition.png");
		w = img.getWidth();
		h = img.getHeight();
	}
	
	public void render(Graphics g) {
		g.drawImage(img, x, y);
	}

}
