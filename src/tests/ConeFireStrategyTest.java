package tests;

import java.awt.geom.Point2D;

import game.towers.strategy.ConeFireStrategy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConeFireStrategyTest {
	private ConeFireStrategy strategy;

	@Before
	public void init() {
		strategy = new ConeFireStrategy();
	}
	
	@Test
	public void testAngle() {
		Point2D p0 = new Point2D.Double(0, 0);
		Point2D p1 = new Point2D.Double(0, 1);
		Point2D p2 = new Point2D.Double(0, 0.5);
		assertTrue(strategy.isZombieInCone(p2, p1, p0));
		
		p0 = new Point2D.Double(0, 0);
		p1 = new Point2D.Double(-1, 0);
		p2 = new Point2D.Double(0, 1);
		assertFalse(strategy.isZombieInCone(p2, p1, p0));
		
		p0 = new Point2D.Double(0, 0);
		p1 = new Point2D.Double(-1, 0);
		p2 = new Point2D.Double(-0.5, 0.2);
		assertTrue(strategy.isZombieInCone(p2, p1, p0));
	}
}
