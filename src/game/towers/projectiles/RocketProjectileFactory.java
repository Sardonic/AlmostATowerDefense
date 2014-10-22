package game.towers.projectiles;

import game.base.Room;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.geom.Point2D;

public class RocketProjectileFactory implements ProjectileFactory {

	public Projectile makeProjectile(Room parent, Point2D pos, Zombie target) {
		return new Projectile(parent, pos, target, 3000, 5, Color.RED, 7);
	}

}
