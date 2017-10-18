package game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import entities.BattleShip;
import entities.FleetBoard;
import entities.Player;
import entities.Ship1;
import entities.Ship2;
import entities.Ship3;
import entities.Ship4;
import entities.Ship5;
import entities.ShotBoard;
import gameStages.Firing;
import gameStages.Menu;
import gameStages.ShipPlacing;
import gameStages.Stage;

public class GameContent extends BasicGame {

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

		public void update(Input input) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				msgOkayed = true;
			}
		}

	}

	private int width = 1280, height = 1280 * 9 / 16;
	Player player;
	Stage currentStage, shipPlacing;
	Menu menu;
	ArrayList<Firing> firing = new ArrayList<Firing>();
	private boolean fullScreen;
	int currentFiring;
	boolean play;
	boolean stageInit = false;
	float bgx, bgy;
	boolean transitionLeft = false;
	Image bgImage;
	Music bgm;
	FleetBoard playerBoard;
	FleetBoard enemyBoard;
	private boolean bgmLooped;
	private boolean showGameOver;

	// ShotBoard playerShots;
	// ShotBoard enemyShots;

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fs) {
		fullScreen = fs;
	}

	public GameContent(String title) {
		super(title);

	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// gc.setFullscreen(true);
		bgImage = new Image("data/wallpaper.png");
		bgm = new Music("data/bgm2.wav");
		bgm.loop();
		menu = new Menu(gc);
		currentStage = menu;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		bgImage.draw(bgx, bgy);
		if (play) {

			if (stageInit) {
				currentStage.render(gc, g);
			}
		} else {
			currentStage.render(gc, g);
		}

		if (msg != null) {
			if (!msg.msgOkayed)
				msg.render(g);
		}

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// background image transition logics
		boolean test = true;
		if (msg != null) {
			test = msg.msgOkayed;
		}

		if (test) {
			if (bgx >= -(1920 - 1280) && !transitionLeft) {

				bgx -= 0.02f * delta;
			} else if (bgx < -(1920 - 1280) && !transitionLeft) {
				transitionLeft = true;
			}

			else if (bgx <= 0 && transitionLeft) {
				bgx += 0.02f * delta;

			} else if (bgx > 0 && transitionLeft) {
				transitionLeft = false;
			}
			if (play) {

				if (!stageInit) {
					shipPlacing = new ShipPlacing(gc);
					shipPlacing.setDone(false);
					firing = new ArrayList<Firing>();
					currentFiring = 0;
					currentStage = shipPlacing;
					enemyBoard = new FleetBoard(new BattleShip[] {
							new Ship1(0, 0), new Ship2(0, 0), new Ship3(0, 0),
							new Ship4(0, 0), new Ship5(0, 0) }, true);
					for (int y = 0; y < 10; y++) {
						for (int x = 0; x < 10; x++) {
							System.out.print(enemyBoard.board[x][y] + "\t");
						}
						System.out.println();
					}
					// playerShots = new ShotBoard(enemyBoard.getShips());

					stageInit = true;
				} else {
					currentStage.update(gc, delta);
				}

				if (stageInit) {
					if (currentStage == shipPlacing) {
						if (shipPlacing.isDone()) {

							firing.add(new Firing(gc, this));
							currentStage = firing.get(currentFiring);
							// shipPlacing.setDone(false);
							playerBoard = shipPlacing.getBoard();
							// enemyShots = new
							// ShotBoard(playerBoard.getShips());
						}

					}

					else if (firing.size() != 0) {
						if (currentStage == firing.get(currentFiring)) {
							if (firing.get(currentFiring).isDone()) {
								// currentStage = shipPlacing;
								firing.add(new Firing(gc, this));
								currentFiring++;
								currentStage = firing.get(currentFiring);
								if (enemyBoard.gameOver) {
									if (!showGameOver) {
										msg = new MessageBox("You won");
										showGameOver = true;
									}// currentStage = menu;

									menu.setDone(false);
									menu.setPlay(false);
									stageInit = false;
									play = false;
									showGameOver = false;

								} else if (playerBoard.gameOver) {
									if (!showGameOver) {
										msg = new MessageBox("You lose");
										showGameOver = true;
										// currentStage = menu;
									}
									menu.setDone(false);
									menu.setPlay(false);
									stageInit = false;
									play = false;
									showGameOver = false;

								}
							}
						}
					}

				}

				// game logics

			}

			else {
				currentStage = menu;
				currentStage.update(gc, delta);
				play = menu.isDone();
				if (menu.isBgm()) {
					if (!bgmLooped) {
						bgm.loop();
						bgmLooped = true;
					}

				} else {
					bgm.stop();
					bgmLooped = false;
				}
			}
		} else {
			Input input = gc.getInput();
			msg.update(input);
		}

	}

	// public ShotBoard getPlayerShots() {
	// return playerShots;
	// }
	//
	// public void setPlayerShots(ShotBoard playerShots) {
	// this.playerShots = playerShots;
	// }
	//
	// public ShotBoard getEnemyShots() {
	// return enemyShots;
	// }
	//
	// public void setEnemyShots(ShotBoard enemyShots) {
	// this.enemyShots = enemyShots;
	// }

	public FleetBoard getEnemyBoard() {
		return enemyBoard;
	}

	public void setEnemyBoard(FleetBoard enemyBoard) {
		this.enemyBoard = enemyBoard;
	}

	public FleetBoard getPlayerBoard() {
		return playerBoard;
	}

	public void setPlayerBoard(FleetBoard playerBoard) {
		this.playerBoard = playerBoard;
	}

	public int getWidth() {
		return width;
	}

	public void setwidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}

}
