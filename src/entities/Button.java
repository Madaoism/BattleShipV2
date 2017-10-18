package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Button extends Entity{
	
	protected int w, h;
	protected boolean selected;

	public Button(float x, float y) throws SlickException {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void update(float mouseX, float mouseY, Input input) throws SlickException{
		if (mouseX >= x && mouseX <= x+w && mouseY >=y && mouseY <= y+h) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				selected = true;
			}
		}
	}

}
