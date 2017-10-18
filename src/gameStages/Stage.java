package gameStages;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import entities.FleetBoard;

public abstract class Stage {
	
	protected TiledMap map;
	protected TileBasedMap tiledMap;
	protected Path path;
	protected int width, height;
	protected int cameraX, cameraY;
	protected AStarPathFinder pathFinder;
	protected int maxPathLength;
	protected Image bgImage;
	FleetBoard board;
	
	public FleetBoard getBoard() {
		return board;
	}

	public void setBoard(FleetBoard board) {
		this.board = board;
	}

	public int getCameraX() {
		return cameraX;
	}

	public void setCameraX(int cameraX) {
		this.cameraX = cameraX;
	}

	public int getCameraY() {
		return cameraY;
	}

	public void setCameraY(int cameraY) {
		this.cameraY = cameraY;
	}

	public int getMaxPathLength() {
		return maxPathLength;
	}

	public void setMaxPathLength(int maxPathLength) {
		this.maxPathLength = maxPathLength;
	}

	public int getSTART_X() {
		return START_X;
	}

	public void setSTART_X(int sTART_X) {
		START_X = sTART_X;
	}

	public int getSTART_Y() {
		return START_Y;
	}

	public void setSTART_Y(int sTART_Y) {
		START_Y = sTART_Y;
	}

	public int getGOAL_X() {
		return GOAL_X;
	}

	public void setGOAL_X(int gOAL_X) {
		GOAL_X = gOAL_X;
	}

	public int getGOAL_Y() {
		return GOAL_Y;
	}

	public void setGOAL_Y(int gOAL_Y) {
		GOAL_Y = gOAL_Y;
	}

	public Path getPath() {
		return path;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setDone(boolean done) {
		return;
	}
	
	public boolean isDone() {
		return false;
	}

	protected int START_X, START_Y, GOAL_X, GOAL_Y;
	
	public Stage(GameContainer gc) {
		try{
			init(gc);
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void update(GameContainer gc, int delta) throws SlickException;
	public abstract void init(GameContainer gc) throws SlickException;
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
		
	protected void findPath() {
		pathFinder = new AStarPathFinder(tiledMap, maxPathLength, false);
		path = pathFinder.findPath(null, START_X, START_Y, GOAL_X, GOAL_Y);
	}
	
	protected void setStartingPosition(int x, int y) {
		START_X = x;
		START_Y = y;
	}
	
	protected void setGoalPosition(int x, int y) {
		GOAL_X = x;
		GOAL_Y = y;
	}
}
