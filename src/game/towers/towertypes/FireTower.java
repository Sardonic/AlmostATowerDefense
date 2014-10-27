package game.towers.towertypes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.base.Room;
import game.towers.Tower;
import game.towers.strategy.ConeFireStrategy;

public class FireTower extends Tower {
	
	public final static double FIRERATE = 45;
	public final static int RANGE = 100;
	public final static int SIZE = 15;
	public final static int DAMAGE = 1;
	public final static int VALUE = 2000;
	public final static Color COLOR = Color.ORANGE;
	private ConeFireStrategy strategy;

	public FireTower(Room parent, Point2D position) {
		super(parent, position, new ConeFireStrategy(), FIRERATE, RANGE, SIZE, SIZE, DAMAGE, VALUE, COLOR);
	}
}
