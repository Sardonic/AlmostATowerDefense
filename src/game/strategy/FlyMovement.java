package game.strategy;

import game.zombies.Zombie;

import java.awt.geom.Point2D;

public class FlyMovement implements EnemyMovementStrategy {
	
	public void update(Zombie zombie) {
		zombie.setPos(new Point2D.Double(zombie.getPos().getX() + zombie.getSpeed(), zombie.getPos().getY()));
	}
}
