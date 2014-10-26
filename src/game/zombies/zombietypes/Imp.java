package game.zombies.zombietypes;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.base.Room;
import game.strategy.FlyMovement;
import game.zombies.Zombie;

public class Imp extends Zombie {
	
	private final static int SIZE = 7;
	private final static int HEALTH = 100;
	private final static double SPEED = 2;
	private final static int MUNNEY = 7;
	private final static Color COLOR = Color.ORANGE;

	public Imp(Room parent, Point2D pos) {
		super(parent, pos, SIZE, SIZE, HEALTH, SPEED, COLOR, MUNNEY, new FlyMovement());
	}
}
