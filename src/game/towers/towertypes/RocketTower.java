package game.towers.towertypes;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.base.Room;
import game.towers.Tower;
import game.towers.projectiles.RocketProjectileFactory;

public class RocketTower extends Tower {
	
	public final static double FIRERATE = 1.25;
	public final static int RANGE = 100;
	public final static int SIZE = 15;
	public final static int DAMAGE = 3000;
	public final static int VALUE = 2000;
	public final static Color COLOR = Color.ORANGE;
	
	public RocketTower(Room parent, Point2D position) {
		super(parent, position, new RocketProjectileFactory(), FIRERATE, RANGE, SIZE, SIZE, DAMAGE, VALUE, COLOR);
	}
}
