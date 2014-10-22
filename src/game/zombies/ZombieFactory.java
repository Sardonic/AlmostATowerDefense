package game.zombies;

import game.base.Room;
import game.grid.Path;
import game.grid.Tile;
import game.strategy.FlyMovement;
import game.strategy.FollowPathMovement;

import java.awt.Color;
import java.awt.geom.Point2D;

public class ZombieFactory {
	private ZombieFactory() {
		
	}
	public static Zombie makeRunner(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), 5, 5, 50, 3, Color.GREEN, 10, new FollowPathMovement(path));
	}
	
	public static Zombie makeWalker(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), 7, 7, 100, 2, Color.BLUE, 5, new FollowPathMovement(path));
	}
	
	public static Zombie makeFatty(Room parent, Path path) {
		return new Zombie(parent, getStartLocation(path), 10, 10, 200, 1.5, Color.YELLOW, 20, new FollowPathMovement(path));
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
