package game.zombies;

import game.base.Room;
import game.grid.Path;
import game.grid.Tile;
import game.zombies.zombietypes.Fatty;
import game.zombies.zombietypes.Imp;
import game.zombies.zombietypes.Runner;
import game.zombies.zombietypes.Walker;

import java.awt.geom.Point2D;

public class ZombieFactory {
	
	private ZombieFactory() {
		
	}
	public static Zombie makeRunner(Room parent, Path path) {
		return new Runner(parent, getStartLocation(path), path);
	}
	
	public static Zombie makeWalker(Room parent, Path path) {
		return new Walker(parent, getStartLocation(path), path);
	}
	
	public static Zombie makeFatty(Room parent, Path path) {
		return new Fatty(parent, getStartLocation(path), path);
	}
	
	public static Zombie makeImp(Room parent, Point2D pos) {
		return new Imp(parent, pos);
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
