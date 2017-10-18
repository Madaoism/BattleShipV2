package game;

// Need to include slick.jar and lwjgl.jar in library build path for the program to run

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Window {

	private static GameContent game = new GameContent("Maze Game");

	
	public static void main(String[] args) {

		AppGameContainer app;
		try {
			app = new AppGameContainer(game);
			app.setDisplayMode(game.getWidth(), game.getHeight(), game.isFullScreen());
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




}
