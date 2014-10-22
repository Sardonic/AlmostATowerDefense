package game.strategy;

import game.grid.Path;
import game.grid.Tile;
import game.zombies.Zombie;

import java.awt.geom.Point2D;

public class FollowPathMovement implements EnemyMovementStrategy {
	Path path;
	Tile prevTile;
	
	public FollowPathMovement(Path toFollow) {
		path = toFollow;
		prevTile = null;
	}

	public void update(Zombie zombie) {
		Tile nextTile = path.findNextTile(prevTile);
		Point2D nextTileCenter;
		
		if (nextTile == null) {
			// At the end of the path. Exit, stage right.
			nextTileCenter = new Point2D.Double(prevTile.getCenter().getX() + Tile.WIDTH, prevTile.getCenter().getY());
		} else {
			nextTileCenter = nextTile.getCenter();
		}
			
		double distance = zombie.getPos().distance(nextTileCenter.getX(), nextTileCenter.getY());
		
		if (distance == 0) {
			prevTile = nextTile;
		} else {
			double x;
			double y;
			double T = zombie.getSpeed() / distance;
			
			double a = zombie.getPos().getX();
			double b = zombie.getPos().getY();
			
			double c = nextTileCenter.getX();
			double d = nextTileCenter.getY();
			
			x = (1 - T) * a + T * c;
			y = (1 - T) * b + T * d;
			Point2D destination = new Point2D.Double(x, y);
			
			
			if (destination.distance(zombie.getPos()) > nextTileCenter.distance(zombie.getPos())) {
				//if we got there start looking for the next point
				//by setting prev to where we just got
				prevTile = nextTile;
				zombie.setPos(nextTileCenter);
			} else {
				zombie.setPos(new Point2D.Double(x, y));
			}
		}
	}
}
