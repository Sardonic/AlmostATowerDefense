package game.towers;

import game.base.Room;
import game.towers.projectiles.MachineGunProjectileFactory;
import game.towers.projectiles.RocketProjectileFactory;

import java.awt.Color;
import java.awt.geom.Point2D;

public class TowerFactory {
	
	public final static double MACHINEGUNFIRERATE = 10;
	public final static int MACHINEGUNRANGE = 200;
	public final static int MACHINEGUNSIZE = 20;
	public final static int MACHINEGUNDAMAGE = 20;
	
	public final static double ROCKETFIRERATE = 1.25;
	public final static int ROCKETRANGE = 100;
	public final static int ROCKETSIZE = 15;
	public final static int ROCKETDAMAGE = 3000;
	
	private TowerFactory() {
		
	}
	
	public static BaseTower makeMachineGunTower(Room parent, Point2D pos) {
		return new Tower(parent, pos, new MachineGunProjectileFactory(), MACHINEGUNFIRERATE, MACHINEGUNRANGE, MACHINEGUNSIZE, MACHINEGUNSIZE, ROCKETDAMAGE, Color.RED);
	}
	public static BaseTower makeRocketTower(Room parent, Point2D pos) {
		return new Tower(parent, pos, new RocketProjectileFactory(), ROCKETFIRERATE, ROCKETRANGE, ROCKETSIZE, ROCKETSIZE, ROCKETDAMAGE, Color.ORANGE);
	}
}
