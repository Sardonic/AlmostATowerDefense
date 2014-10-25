package game.towers.projectiles;

import game.base.Room;
import game.zombies.Zombie;

import java.awt.geom.Point2D;

public interface ProjectileFactory {
	Projectile makeProjectile(Room parent, Point2D pos, Zombie target, int damage);
}
