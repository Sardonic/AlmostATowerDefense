package game.towers.strategy;

import java.awt.geom.Point2D;

import game.base.Game;
import game.base.Room;
import game.towers.Tower;
import game.towers.projectiles.ProjectileFactory;
import game.zombies.Zombie;

public class SingleShotStrategy implements TowerStrategy {
	private ProjectileFactory projectileFactory;
	private double framesUntilFire;
	private Zombie target;
	
	public SingleShotStrategy(ProjectileFactory projectileFactory) {
		this.projectileFactory = projectileFactory;
		target = null;
	}

	public void update(Tower tower) {
		Room parentRoom = tower.getParentRoom();
		Point2D center = tower.getCenter();
		double fireRate = tower.getFireRate();
		int damage = tower.getDamage();
		
		if (target != null) {
			if (target.getCenter().distance(tower.getCenter()) > tower.getRange()) {
				target = null;
			} else if (target.getHealth() <= 0 || !parentRoom.doesUnitExist(target)) {
				target = null;
			}
		}
		
		if (target == null) {
			target = tower.acquireClosestZombie();
		}
		
		if (framesUntilFire > 0) {
			framesUntilFire--;
		}
		
		if (target != null) {
			if (framesUntilFire <= 0) {
				parentRoom.addUnit(projectileFactory.makeProjectile(parentRoom, new Point2D.Double(center.getX(), center.getY()), target, damage));
				framesUntilFire += Game.IDEAL_FPS / fireRate;
			}
		}
	}

}
