package gameStages;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import entities.Entity;

public class Menu extends Stage {

	private Cursor cursor;
	private boolean optionPane;
	private boolean play;
	private boolean bgm;

	public boolean isDone() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean isBgm() {
		return bgm;
	}

	public void setBgm(boolean bgm) {
		this.bgm = bgm;
	}

	// coordinates for menu options
	private float y1, y2, y3;
	private float x1, x2, x3;

	public Menu(GameContainer gc) {
		super(gc);

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		cursor.update(gc, delta);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		y1 = gc.getHeight() / 2 + 64 * 2;
		y2 = y1 + 64;
		y3 = y2 + 64;
		x1 = gc.getWidth() / 2 - 64 * 1;
		x2 = x1;
		x3 = x1;
		cursor = new Cursor(x1 - 64*1, y1);
		bgm = true;
	}
	
//	public UnicodeFont setFont() {
//		UnicodeFont ufont = null;
//		try {
//			Font font = new Font("Serif", Font.BOLD, 48);
//			ufont = new UnicodeFont(font, font.getSize(), font.isBold(),
//					font.isItalic());
//			ufont.addAsciiGlyphs();
//			ufont.addGlyphs(400, 600);
//			ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
//			ufont.loadGlyphs();
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
//		return ufont;
//	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
//		g.setFont(setFont());
		if (!optionPane) {
			
			g.drawString("Play", x1, y1);
			g.drawString("Options", x2, y2);
			g.drawString("Exit", x3, y3);
		}

		else {
			g.drawString("Background Music ON/OFF", x1, y1);
			g.drawString("Fullscreen ON/OFF", x2, y2);
			g.drawString("Return", x3, y3);
		}
		cursor.render(g);
	}

	private class Cursor extends Entity {

		Image img;

		public Cursor(float x, float y) throws SlickException {
			super(x, y);
			img = new Image("data/cursor.png");
		}

		@Override
		public void update(GameContainer container, int delta)
				throws SlickException {
			Input input = container.getInput();
			// int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			if (mouseY >= y1 && mouseY <= y2) {
				y = y1;
			} else if (mouseY >= y2 && mouseY <= y3) {
				y = y2;
			} else if (mouseY >= y3 && mouseY <= y3 + 64) {
				y = y3;
			}
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) {
				if (optionPane) {
					if (y == y1) {
						if (bgm) bgm = false;
						else bgm = true;
					}
					else if (y == y2) {
						if (container.isFullscreen()) {
							container.setFullscreen(false);
						}
						else container.setFullscreen(true);
					}
					else if (y == y3) {
						optionPane = false;
					}
				}
				else {
					if (y == y1) play = true;
					if (y == y2) optionPane = true;
					if (y == y3) container.exit();
				}
			}
		}

		public void render(Graphics g) {
			g.drawImage(img, x, y);
		}

	}

}
