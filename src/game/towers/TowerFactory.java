package game.towers;

import game.base.Room;
import game.towers.towertypes.FireTower;
import game.towers.towertypes.MachineGunTower;
import game.towers.towertypes.RocketTower;

import java.awt.geom.Point2D;

public class TowerFactory {
	
	private TowerFactory() {
		
	}
	
	public static BaseTower makeMachineGunTower(Room parent, Point2D pos) {
		return new MachineGunTower(parent, pos);
	}
	public static BaseTower makeRocketTower(Room parent, Point2D pos) {
		return new RocketTower(parent, pos);
	}
	
	public static BaseTower makeFireTower(Room parent, Point2D pos) {
		return new FireTower(parent, pos);
	}
}
