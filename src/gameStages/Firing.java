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
import entities.FireButton;
import entities.FiringTarget;
import entities.FleetBoard;
import entities.ShotBoard;
import entities.ShotImage;
import entities.TransitionButton;
import game.GameContent;

public class Firing extends Stage {

	private ArrayList<ShotImage> imgs;
	private int targetCount, shipsLeft;
	private boolean newTarget;
	private FiringTarget[] targets, enemyTargets;
	private int targetSelected;
	private boolean allTargetDropped;
	private FireButton fireButton;
	// private boolean buttonPressed;
	private TiledMap shipsMap;
	private GameContent game;
	private boolean transitionToRight;
	private int transitionX, transitionY;
	private TransitionButton transition;
	private FleetBoard myBoard, enemyBoard;
	// private ShotBoard myShots, enemyShots;
	private BattleShip[] ships;
	private boolean getShips;
	private float[] tempX;
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

		public void update( Input input) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				msgOkayed = true;
			}
		}

	}

	public Firing(GameContainer gc) {
		super(gc);
	}

	public Firing(GameContainer gc, GameContent game) {
		super(gc);
		this.game = game;

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		boolean test3 = true;
		if (msg != null) {
			test3 = msg.msgOkayed;
		}
		if (test3) {
		if (!getShips) {
			imgs = new ArrayList<ShotImage>();
			// myShots = game.getPlayerShots();
			myBoard = game.getPlayerBoard();
			// enemyShots = game.getEnemyShots();
			enemyBoard = game.getEnemyBoard();
			shipsLeft = game.getPlayerBoard().shipsLeft;
			targets = new FiringTarget[shipsLeft];

			ships = game.getPlayerBoard().getShips();
			tempX = new float[ships.length];

			for (int i = 0; i < ships.length; i++) {
				tempX[i] = ships[i].getX();

			}
			getShips = true;

			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					if (enemyBoard.board[x][y] == "miss") {
						imgs.add(new ShotImage("data/miss.png", cameraX + 64
								+ y * 64, cameraY + 64 + x * 64));
					} else if (enemyBoard.board[x][y].substring(0, 3)
							.equalsIgnoreCase("hit")) {
						imgs.add(new ShotImage("data/hit.png", cameraX + 64 + y
								* 64, cameraY + 64 + x * 64));
					}
				}
			}

			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					if (myBoard.board[x][y] == "miss") {
						imgs.add(new ShotImage("data/miss.png", cameraX + 64
								+ 16 * 64 + y * 64, cameraY + 64 + x * 64));
					} else if (myBoard.board[x][y].substring(0, 3)
							.equalsIgnoreCase("hit")) {
						imgs.add(new ShotImage("data/hit.png", cameraX + 64
								+ 16 * 64 + y * 64, cameraY + 64 + x * 64));
					}
				}
			}
		}

		else {

			Input input = gc.getInput();
			float mouseX = input.getMouseX(), mouseY = input.getMouseY();
			fireButton.update(mouseX, mouseY, input);
			transition.update(mouseX, mouseY, input);

			if (fireButton.isSelected()) {
				boolean allDropped = true;
				for (int i = 0; i < targets.length; i++) {
					if (!targets[i].isDropped()) {
						allDropped = false;
						break;
					}
				}

				if (allDropped) {

					// Testing
					for (int i = 0; i < targets.length; i++) {
						targets[i]
								.setyCo((int) (targets[i].getX() - cameraX - 64) / 64);
						targets[i]
								.setxCo((int) (targets[i].getY() - cameraY - 64) / 64);
					}

					boolean test = true;
					for (int i = 0; i < targets.length; i++) {

						if (enemyBoard.testShotCollision(targets[i])) {
							test = false;

							msg = new MessageBox("Target " + i
									+ " collided");
							break;
						}

						if (i != targets.length - 1) {
							for (int j = i + 1; j < targets.length; j++) {

								if (targets[i].getxCo() == targets[j].getxCo()
										&& targets[i].getyCo() == targets[j]
												.getyCo()) {
									test = false;

								}
							}
						}
					}

					allTargetDropped = test;

					if (allTargetDropped) {
						for (int i = 0; i < targets.length; i++) {
							System.out
									.println(enemyBoard.shotFired(targets[i]));
						}

						for (int i = 0; i < ships.length; i++) {
							ships[i].setX(tempX[i]);
						}
						if (enemyBoard.shipsLeft >= 0)
							enemyTargets = new FiringTarget[enemyBoard.shipsLeft];
						else
							gc.exit();
						boolean test2 = false;
						while (!test2) {
							if (enemyTargets != null && enemyTargets.length !=0) {
								enemyTargets[0] = generateTargetSmart(myBoard);
								System.out.println("Enemy Shot: ("
										+ enemyTargets[0].getxCo() + ", "
										+ enemyTargets[0].getyCo() + ")");
								for (int i = 1; i < enemyTargets.length; i++) {
									
									enemyTargets[i] = generateTarget(myBoard);

									System.out.println("Enemy Shot: ("
											+ enemyTargets[i].getxCo() + ", "
											+ enemyTargets[i].getyCo() + ")");
								}
								test2 = true;
								for (int i = 0; i < enemyTargets.length; i++) {
									if (i != enemyTargets.length - 1) {
										for (int j = i + 1; j < enemyTargets.length; j++) {
											if (enemyTargets[i].getxCo() == enemyTargets[j]
													.getxCo()
													&& enemyTargets[i].getyCo() == enemyTargets[j]
															.getyCo()) {
												test2 = false;
												break;
											}
										}
									}
								}
								if (test2) {
									for (int i = 0; i < enemyTargets.length; i++) {
										myBoard.shotFired(enemyTargets[i]);
									}
								}

							}
						}


					} else {
						msg = new MessageBox("Collision Detected!");
					}

					fireButton.setSelected(false);
				} else {
					fireButton.setSelected(false);
					msg = new MessageBox("Not all targets are fired yet!");
				}
			}

			// Only transition after all targets dropped
			if (transition.isSelected()) {
				if (transitionToRight) {
					if (cameraX > -9 * 64) {
						cameraX -= 2;
						transition.setX(transition.getX() - 2);
						fireButton.setX(fireButton.getX() - 2);
						for (int i = 0; i < targets.length; i++) {
							if (targets[i] != null) {
								targets[i].setX(targets[i].getX() - 2);
							}
						}
						for (int i = 0; i < ships.length; i++) {
							if (ships[i] != null) {
								ships[i].setX(ships[i].getX() - 2);
							}
						}
						if (imgs.size() != 0) {
							for (int i = 0; i < imgs.size(); i++) {
								imgs.get(i).setX(imgs.get(i).getX() - 2);
							}
						}

					} else {
						transitionToRight = false;
						transition.setSelected(false);
					}
				} else {
					if (cameraX < 1 * 64) {
						cameraX += 2;
						transition.setX(transition.getX() + 2);
						fireButton.setX(fireButton.getX() + 2);
						for (int i = 0; i < targets.length; i++) {
							if (targets[i] != null) {
								targets[i].setX(targets[i].getX() + 2);
							}
						}
						for (int i = 0; i < ships.length; i++) {
							if (ships[i] != null) {
								ships[i].setX(ships[i].getX() + 2);
							}
						}
						if (imgs.size() != 0) {
							for (int i = 0; i < imgs.size(); i++) {
								imgs.get(i).setX(imgs.get(i).getX() + 2);
							}
						}

					} else {
						transitionToRight = true;
						transition.setSelected(false);
					}
				}
			}

			if (targetCount < shipsLeft) {
				if (newTarget) {
					targets[targetCount] = new FiringTarget(cameraX + 64,
							cameraY + 64);
					targets[targetCount].setSelected(true);
					newTarget = false;
				}

				else {
					if (mouseX >= cameraX + 64
							&& mouseX <= cameraX + 64 + (10 * 64)
							&& mouseY >= cameraY + 64
							&& mouseY <= cameraY + 64 + (10 * 64)) {
						targets[targetCount].setTarget(mouseX, mouseY);
					}

					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						targets[targetCount].setSelected(false);
						targets[targetCount].setDropped(true);
						newTarget = true;
						targetCount++;

					}
				}
			}

			else {
				if (targetSelected == -1) {
					for (int i = 0; i < targets.length; i++) {
						if (mouseX >= targets[i].getX()
								&& mouseX <= targets[i].getX() + 64
								&& mouseY >= targets[i].getY()
								&& mouseY <= targets[i].getY() + 64) {
							if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
								targetSelected = i;
								targets[i].setSelected(true);
								targets[i].setDropped(false);
								break;
							}
						}
					}
				}

				else {
					if (mouseX >= cameraX + 64
							&& mouseX <= cameraX + 64 + (10 * 64)
							&& mouseY >= cameraY + 64
							&& mouseY <= cameraY + 64 + (10 * 64)) {
						targets[targetSelected].setTarget(mouseX, mouseY);

					}

					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						targets[targetSelected].setSelected(false);
						targets[targetSelected].setDropped(true);
						targetSelected = -1;

					}
				}

			}
		}
		}
		else {
			Input input = gc.getInput();
			msg.update(input);
		}

	}


	@Override
	public void init(GameContainer gc) throws SlickException {

		map = new TiledMap("data/board.tmx");
		shipsMap = new TiledMap("data/board.tmx");
		cameraX = 1 * 64;
		cameraY = 0 * 64;
		newTarget = true;
		targetCount = 0;

		targetSelected = -1;
		fireButton = new FireButton(cameraX + 11 * 64 + 3 * 64, cameraY + 11
				* 64 - 3 * 64);
		transitionX = cameraX + 11 * 64 + 2 * 64;
		transitionY = cameraY + 2 * 64;
		transition = new TransitionButton(transitionX, transitionY);
		transitionToRight = true;

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render(cameraX, cameraY);
		shipsMap.render(cameraX + 64 * 16, cameraY);
		// drawBorder(g);
		if (targets != null) {
			for (int i = 0; i < targets.length; i++) {
				if (targets[i] != null) {
					targets[i].render(g);
				}
			}
		}
		fireButton.render(g);

		transition.render(g);
		if (ships != null) {
			for (int i = 0; i < ships.length; i++) {
				ships[i].render();
			}
		}

		if (imgs != null) {
			if (imgs.size() != 0) {
				for (int i = 0; i < imgs.size(); i++) {
					g.drawImage(imgs.get(i), imgs.get(i).getX(), imgs.get(i)
							.getY());
				}
			}
		}
		
		if (msg != null) {
			if (!msg.msgOkayed) msg.render(g);
		}
	}

	// public void drawBorder(Graphics g) {
	// Color blue = new Color(0, 125, 223);
	// g.setColor(blue);
	// g.drawLine(5 * 64, 11 * 64, (1 + 11) * 64, 11 * 64);
	// g.drawLine((1 + 11) * 64, 0 * 64, (1 + 11) * 64, 11 * 64);
	// g.setColor(Color.black);
	// }

	@Override
	public boolean isDone() {
		return allTargetDropped;
	}

	@Override
	public void setDone(boolean done) {
		allTargetDropped = false;

	}

	// public String generateRandomShot(FleetBoard fb){
	// int[] intCoords = findEmpty(fb);
	// String coords = "";
	// coords+= intCoords[0]+","+intCoords[1];
	// return coords;
	// }
	public FiringTarget generateTargetSmart(FleetBoard fb) throws SlickException {
		int[] coord = myBoard.enemyShots.findSmartShot();
		int x = coord[0];
		int y = coord[1];
		FiringTarget target = new FiringTarget(x * 64 + cameraX + 64 + 16 * 64,
				y * 64 + cameraY + 64);
		target.setDropped(true);
		target.setxCo(x);
		target.setyCo(y);
		target.setSelected(false);
		return target;
	}
	
	public FiringTarget generateTarget(FleetBoard fb) throws SlickException {
		int[] coord = generateCoordinate(fb);
		int x = coord[0];
		int y = coord[1];
		FiringTarget target = new FiringTarget(x * 64 + cameraX + 64 + 16 * 64,
				y * 64 + cameraY + 64);
		target.setDropped(true);
		target.setxCo(x);
		target.setyCo(y);
		target.setSelected(false);
		return target;
	}

	public int[] generateCoordinate(FleetBoard fb) {
		boolean successful = false;
		int[] coords = new int[2];
		while (!successful) {
			coords[0] = randomNum();
			coords[1] = randomNum();
			successful = collisionTest(coords, fb);
		}
		return coords;
	}

	public boolean collisionTest(int[] coords, FleetBoard fleetboard) {
		String[][] board = fleetboard.board;
		int x = coords[0];
		int y = coords[1];
		boolean noCollisionTemp = true;
		if (board[y][x] == "miss" || board[y][x].substring(0, 3).equals("hit"))
			noCollisionTemp = false;
		return noCollisionTemp;
	}

	public int randomNum() {
		float floatCo = Math.round(Math.random() * 10 - .5);
		int intCo = Math.round(floatCo);
		return intCo;
	}

}
