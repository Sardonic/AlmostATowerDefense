package game.zombies;

import game.base.Room;
import game.grid.Path;
import game.grid.Tile;
import game.strategy.FlyMovement;
import game.strategy.FollowPathMovement;

import java.awt.Color;
import java.awt.geom.Point2D;

public class ZombieFactory {
	
	private final static int RUNNERSIZE = 5;
	private final static int RUNNERHEALTH = 50;
	private final static double RUNNERSPEED = 3;
	private final static int RUNNERMUNNEY = 10;
	
	private final static int WALKERSIZE = 7;
	private final static int WALKERHEALTH = 100;
	private final static double WALKERSPEED = 2;
	private final static int WALKERMUNNEY = 5;
	
	private final static int FATTYSIZE = 10;
	private final static int FATTYHEALTH = 200;
	private final static double FATTYSPEED = 1.5;
	private final static int FATTYMUNNEY = 20;
	
	private ZombieFactory() {
		
	}
	public static Zombie makeRunner(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), RUNNERSIZE, RUNNERSIZE, RUNNERHEALTH, RUNNERSPEED, Color.GREEN, RUNNERMUNNEY, new FollowPathMovement(path));
	}
	
	public static Zombie makeWalker(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), WALKERSIZE, WALKERSIZE, WALKERHEALTH, WALKERSPEED, Color.BLUE, WALKERMUNNEY, new FollowPathMovement(path));
	}
	
	public static Zombie makeFatty(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), FATTYSIZE, FATTYSIZE, FATTYHEALTH, FATTYSPEED, Color.YELLOW, FATTYMUNNEY, new FollowPathMovement(path));
	}
	
	public static Zombie makeImp(Room parent, Point2D pos) {
		return new Zombie(parent, pos, 7, 7, 100, 2, Color.ORANGE, 7, new FlyMovement());
	}
	
	private static Point2D getStartLocation(Path path) {
		Tile firstTile = path.getFirstTile();
		if (firstTile != null) {
			Point2D firstTileCenter = path.getFirstTile().getCenter();
			return new Point2D.Double(firstTileCenter.getX() - Tile.WIDTH, firstTileCenter.getY());
		}
		else {
			return new Point2D.Double();
		}
	}
}
