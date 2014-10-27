package game.towers.towertypes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.base.Room;
import game.towers.Tower;
import game.towers.strategy.ConeFireStrategy;
import game.towers.strategy.TowerStrategy;

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
		strategy = new ConeFireStrategy();
		setStrategy(strategy);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (strategy.getTarget() != null) {
			
			double targetX = strategy.getTarget().getPos().getX();
			double targetY = strategy.getTarget().getPos().getY();
			/*
			double theta = Math.atan2(targetY - getCenter().getY(), targetX - getCenter().getX());
			theta = Math.toDegrees(theta);
			theta -= Math.toDegrees(ConeFireStrategy.CONE_HALFANGLE);
			theta += 90;
			double ulX = getCenter().getX() - RANGE;
			double ulY = getCenter().getY() - RANGE;
			g.setColor(Color.RED);
			g.fillArc((int)ulX, (int)ulY, RANGE * 2, RANGE * 2, (int)theta, (int)Math.toDegrees(ConeFireStrategy.CONE_ANGLE));
			*/
			g.setColor(Color.BLACK);
			g.drawLine((int)getCenter().getX(), (int)getCenter().getY(), (int)targetX, (int)targetY);
		}
	}
}
