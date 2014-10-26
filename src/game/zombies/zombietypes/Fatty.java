package game.zombies.zombietypes;

import game.base.Room;
import game.grid.Path;
import game.strategy.FollowPathMovement;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Fatty extends Zombie {

	private final static int SIZE = 10;
	private final static int HEALTH = 200;
	private final static double SPEED = 1.5;
	private final static int MUNNEY = 10;
	private final static Color COLOR = Color.YELLOW;
	
	public Fatty(Room parent, Path path, Point2D pos) {
		super(parent, pos, SIZE, SIZE, HEALTH, SPEED, COLOR, MUNNEY, new FollowPathMovement(path));
	}
	
	public Fatty(Room parent, Path path) {
		this(parent, path, path.getDefaultStartLocation());
	}
}
