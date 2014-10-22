package game.towers;

import game.base.Room;
import game.towers.projectiles.MachineGunProjectileFactory;
import game.towers.projectiles.RocketProjectileFactory;

import java.awt.Color;
import java.awt.geom.Point2D;

public class TowerFactory {
	private TowerFactory() {
		
	}
	
	public static Tower makeMachineGunTower(Room parent, Point2D pos) {
		return new Tower(parent, pos, new MachineGunProjectileFactory(), 60, 200, 20, 20, Color.RED);
	}
	public static Tower makeRocketTower(Room parent, Point2D pos) {
		return new Tower(parent, pos, new RocketProjectileFactory(), 1.25, 100, 15, 15, Color.ORANGE);
	}
}
