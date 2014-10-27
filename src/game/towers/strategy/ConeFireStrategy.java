package game.towers.strategy;

import java.awt.geom.Point2D;
import java.util.Collection;

import game.base.Game;
import game.base.GameItem;
import game.base.Room;
import game.base.GameItem.Tag;
import game.towers.Tower;
import game.towers.particles.FireParticle;
import game.zombies.Zombie;

public class ConeFireStrategy implements TowerStrategy {
	private Zombie target;
	public final static double CONE_ANGLE = Math.PI / 2;
	public final static double CONE_HALFANGLE = CONE_ANGLE / 2;
	private final static int PARTICLES_PER_FRAME = 3;
	private int framesUntilDamage;
	
	public ConeFireStrategy() {
		target = null;
		framesUntilDamage = 0;
	}

	public void update(Tower tower) {
		Room parentRoom = tower.getParentRoom();
		
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
		
		if (framesUntilDamage > 0) {
			framesUntilDamage--;
		}
		
		if (target != null) {
			// produce particles
			double targetX = target.getPos().getX();
			double targetY = target.getPos().getY();
			double towerX = tower.getPos().getX();
			double towerY = tower.getPos().getY();
			double theta = Math.atan2(targetY - towerY, targetX - towerX);
			createParticles(parentRoom, tower.getCenter(), theta, CONE_HALFANGLE, tower.getRange(), PARTICLES_PER_FRAME);
			
			if (framesUntilDamage <= 0) {
				Collection<GameItem> zombies = tower.getParentRoom().getAllUnitsWithTag(Tag.ZOMBIE);
				// deal damage
				framesUntilDamage += Game.IDEAL_FPS / tower.getFireRate();
				
				//loop through all the zombies
				for(GameItem item : zombies) {
					Zombie zombie = (Zombie)item;
					//check if they are within range
					if(zombie.getPos().distance(tower.getCenter()) < tower.getRange())
					{
						//check if they within CONE_ANGLE/2
						if (isZombieInAngle(theta, zombie.getCenter(), tower.getCenter(), CONE_HALFANGLE)) {
							// damage them
							zombie.reduceHealth(tower.getDamage());
						}
					}
				}
			}
		}
	}
	
	private void createParticles(Room room, Point2D center, double orientation, double angleVariance, double distance, int num) {
		for (int i = 0; i < num; i++) {
			FireParticle p = new FireParticle(room, center, orientation, angleVariance, distance);
			room.addUnit(p);
		}
	}
	
	public static boolean isZombieInAngle(double angleCenter, Point2D zombie, Point2D towerCenter, double halfArc) {
		double zombieX = zombie.getX();
		double zombieY = zombie.getY();
		double towerX = towerCenter.getX();
		double towerY = towerCenter.getY();
		double theta = Math.atan2(zombieY - towerY, zombieX - towerX);
		return Math.abs(angleCenter - theta) <= halfArc;
	}
	
	public boolean isZombieInCone(Point2D zombie, Point2D centerTarget, Point2D tower) {
		double zombieX = zombie.getX();
		double zombieY = zombie.getY();
		double targetX = centerTarget.getX();
		double targetY = centerTarget.getY();
		double towerX = tower.getX();
		double towerY = tower.getY();
		double x = Math.atan2(targetY - towerY, targetX - towerX);
		double y = Math.atan2(zombieY - towerY, zombieX - towerX);
		
		return Math.abs(y - x) <= CONE_HALFANGLE;
	}

}
