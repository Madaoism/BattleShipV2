package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DoneButton extends Button{

	Image img;
	
	public DoneButton(float x, float y) throws SlickException {
		super(x, y);
		img = new Image("data/doneButton.png");
		w = img.getWidth();
		h = img.getHeight();
		
	}
	
	public void render(Graphics g) {
		g.drawImage(img, INIT_X, INIT_Y);
	}

}
