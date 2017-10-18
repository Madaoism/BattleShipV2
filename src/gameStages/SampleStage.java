package gameStages;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class SampleStage extends Stage{

	public SampleStage(GameContainer gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap("data/sewer-1.tmx");
		tiledMap = new PropertyBasedMap(map, "blocked");
		setStartingPosition(4, 24);
		setGoalPosition(6, 11);
		maxPathLength = 200;
		findPath();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render(cameraX, cameraY);
	}
	

}
