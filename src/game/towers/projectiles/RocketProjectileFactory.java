package game.towers.projectiles;

import game.base.Room;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.geom.Point2D;

public class RocketProjectileFactory implements ProjectileFactory {
	
	private final static double SIZE = 5;
	private final static double SPEED = 7;

	public Projectile makeProjectile(Room parent, Point2D pos, Zombie target, int damage) {
		return new Projectile(parent, pos, target, damage, SIZE, Color.RED, SPEED);
	}

}
