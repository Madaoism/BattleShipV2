package gameStages;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import entities.BattleShip;
import entities.DoneButton;
import entities.FleetBoard;
import entities.Ship1;
import entities.Ship2;
import entities.Ship3;
import entities.Ship4;
import entities.Ship5;

public class ShipPlacing extends Stage {

	public ShipPlacing(GameContainer gc) {
		super(gc);
	}

	private FleetBoard board;
	private int ID = 1283971;
	private TiledMap dragBox;
	private float dragBoxX, dragBoxY;
	BattleShip[] ships;
	private boolean setFont;
	private float boardX, boardY;
	private float mouseX, mouseY;
	private int cameraX, cameraY;
	private TiledMap map;
	private int index;
	private boolean selected, done;
	private DoneButton doneButton;
	private int[] tempX;
	// private boolean msg;
	private MessageBox msg;

	private class MessageBox {

		public boolean msgOkayed;
		String msg;

		public MessageBox(String msg) {
			this.msg = msg;
		}

		public void render(Graphics g) {
			g.setColor(Color.red);
			g.fillRect(1280 / 2 - 200, 720 / 2 - 40, 400, 80);
			g.setColor(Color.black);
			g.drawString(msg, 1280 / 2 - 190, 720 / 2 - 30);
		}

		public void update(int mouseX, int mouseY, Input input) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				msgOkayed = true;
			}
		}

	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// msgs = new ArrayList<MessageBox>();
		map = new TiledMap("data/board.tmx");

		dragBox = new TiledMap("data/DragDrop.tmx");
		cameraX = 1 * 64;
		cameraY = 0 * 64;
		dragBoxX = (1 + 11 + 1) * 64;
		dragBoxY = 1 * 64;

		ships = new BattleShip[5];
		ships[0] = new Ship1(dragBoxX, dragBoxY + 64 * 2);
		ships[1] = new Ship2(dragBoxX, dragBoxY + 64 * 3);
		ships[2] = new Ship3(dragBoxX, dragBoxY + 64 * 4);
		ships[3] = new Ship4(dragBoxX, dragBoxY + 64 * 5);
		ships[4] = new Ship5(dragBoxX, dragBoxY + 64 * 6);

		doneButton = new DoneButton(dragBoxX + 4 * 64, dragBoxY + 64 * 9);

		boardX = 64;
		boardY = 0;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render(cameraX, cameraY);
		dragBox.render((int) dragBoxX, (int) dragBoxY);
		drawBorder(g);

		// g.setFont(setFont());
		g.setColor(Color.green);
		setFont = true;

		g.drawString("Click&Drop Box", dragBoxX + 20, dragBoxY + 20);

		for (int i = 0; i < ships.length; i++) {
			ships[i].render();
		}

		doneButton.render(g);
		if (msg != null) {
			if (!msg.msgOkayed) {
				msg.render(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		doneButton.update(mouseX, mouseY, input);
		boolean test = true;
		if (msg != null) {
			test = msg.msgOkayed;
		}

		if (test) {
			if (doneButton.isSelected()) {
				boolean allDropped = true;
				for (int i = 0; i < ships.length; i++) {
					if (!ships[i].isDropped()) {
						allDropped = false;
						break;
					}
				}

				if (allDropped) {

					for (int i = 0; i < ships.length; i++) {
						ships[i].setxCo((int) (ships[i].getX() - boardX) / 64 - 1);
						ships[i].setyCo((int) (ships[i].getY() - boardY) / 64 - 1);
					}

					board = new FleetBoard(ships);
					if (board.isNoCollision()) {
						for (int i = 0; i < ships.length; i++) {
							ships[i].setPlaced(true);
						}
						done = true;


						for (int i = 0; i < ships.length; i++) {

							ships[i].setX(ships[i].getX() + 16 * 64);

						}

					} else {
						msg = new MessageBox(
								"Collision Detected: Ships Overlapping");
					}
					doneButton.setSelected(false);

				}

				else {
					doneButton.setSelected(false);
					msg = new MessageBox("Please place all ships");
				}
			}

			if (!selected) {
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					for (int i = 0; i < ships.length; i++) {
						if (!ships[i].isFlipped()) {
							if (mouseX >= ships[i].getX()
									&& mouseX <= ships[i].getX() + 64
											* ships[i].getShipLength()
									&& mouseY >= ships[i].getY()
									&& mouseY <= ships[i].getY() + 64) {
								selected = true;
								ships[i].setSelected(true);
								index = i;
								break;
							}
						} else {
							if (mouseX >= ships[i].getX()
									&& mouseX <= ships[i].getX() + 64
									&& mouseY >= ships[i].getY()
									&& mouseY <= ships[i].getY() + 64
											* ships[i].getShipLength()) {
								selected = true;
								ships[i].setSelected(true);
								index = i;
								break;
							}
						}

						if (input.getMouseX() >= ships[i].getINIT_X()
								&& input.getMouseX() <= ships[i].getINIT_X()
										+ 64 * ships[i].getShipLength()
								&& input.getMouseY() >= ships[i].getINIT_Y()
								&& input.getMouseY() <= ships[i].getINIT_Y() + 64) {
							selected = true;
							ships[i].setSelected(true);
							index = i;
							break;
						}
					}
				}
			}

			else {
				if (!ships[index].isFlipped()) {
					if (mouseY >= boardY + 1 * 64
							&& mouseY <= boardY + (11) * 64) {

						if (mouseX >= boardX + 1 * 64
								&& mouseX <= boardX
										+ (11 - ships[index].getShipLength() + 1)
										* 64) {
							ships[index].setX(((int) (mouseX / 64)) * 64);
							ships[index].setY(((int) (mouseY / 64)) * 64);

						} else if (mouseX >= boardX
								+ (11 - ships[index].getShipLength() + 1) * 64
								&& mouseX <= boardX + (11) * 64) {
							ships[index].setX(((int) (11 - ships[index]
									.getShipLength() + 1)) * 64);

							ships[index].setY(((int) (mouseY / 64)) * 64);
						}
					}

				} else {

					if (mouseX >= boardX + 1 * 64
							&& mouseX <= boardX + (11) * 64) {

						if (mouseY >= boardY + 1 * 64
								&& mouseY < boardY
										+ (11 - ships[index].getShipLength())
										* 64) {
							ships[index].setX(((int) (mouseX / 64)) * 64);
							ships[index].setY(((int) (mouseY / 64)) * 64);

						} else if (mouseY >= boardY
								+ (11 - ships[index].getShipLength()) * 64
								&& mouseY <= boardY + (11) * 64) {
							ships[index].setY(((int) (11 - ships[index]
									.getShipLength())) * 64);
							ships[index].setX(((int) (mouseX / 64)) * 64);
						}
					}
				}

				if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)) {
					if (ships[index].isFlipped()) {
						ships[index].setFlipped(false);
					} else {
						ships[index].setFlipped(true);
					}
				}

				if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {

					selected = false;

					ships[index].setSelected(false);
					ships[index].setDropped(true);
					index = -1;
				}
			}
		} else {

			msg.update((int) mouseX, (int) mouseY, input);

		}

	}

	public FleetBoard getBoard() {
		return board;
	}

	public void setBoard(FleetBoard board) {
		this.board = board;
	}

	public void drawBorder(Graphics g) {
		Color blue = new Color(0, 125, 223);
		// g.setFont(setFont());
		g.setColor(blue);
		g.drawLine(5 * 64, 11 * 64, (1 + 11) * 64, 11 * 64);
		g.drawLine((1 + 11) * 64, 0 * 64, (1 + 11) * 64, 11 * 64);

	}

	// public UnicodeFont setFont() {
	// UnicodeFont ufont = null;
	// try {
	// Font font = new Font("Serif", Font.ITALIC, 48);
	// ufont = new UnicodeFont(font, font.getSize(), font.isBold(),
	// font.isItalic());
	// ufont.addAsciiGlyphs();
	// ufont.addGlyphs(400, 600);
	// ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
	// ufont.loadGlyphs();
	// } catch (SlickException e) {
	// e.printStackTrace();
	// }
	// return ufont;
	// }

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void setDone(boolean done) {
		this.done = done;
	}

}
