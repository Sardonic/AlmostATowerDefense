package game.towers.towertypes;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.base.Room;
import game.towers.Tower;
import game.towers.projectiles.MachineGunProjectileFactory;

public class MachineGunTower extends Tower {
	
	public final static double FIRERATE = 10;
	public final static int RANGE = 200;
	public final static int SIZE = 20;
	public final static int DAMAGE = 20;
	public final static int VALUE = 500;
	public final static Color COLOR = Color.RED;

	public MachineGunTower(Room parent, Point2D position) {
		super(parent, position, new MachineGunProjectileFactory(), FIRERATE, RANGE, SIZE, SIZE,
				DAMAGE, VALUE, COLOR);
	}

	
}
