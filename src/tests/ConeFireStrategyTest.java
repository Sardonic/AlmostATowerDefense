package tests;

import java.awt.geom.Point2D;

import game.towers.strategy.ConeFireStrategy;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConeFireStrategyTest {
	
	@Test
	public void testAngle() {
		Point2D towerCenter = new Point2D.Double(0, 0);
		Point2D targetZombie = new Point2D.Double(0, 1);
		Point2D testZombie = new Point2D.Double(0, 0.5);
		double angleCenter = Math.atan2(targetZombie.getY() - towerCenter.getY(), targetZombie.getX() - towerCenter.getX());
		assertTrue(ConeFireStrategy.isZombieInAngle(angleCenter, testZombie, towerCenter, ConeFireStrategy.CONE_HALFANGLE));
		
		towerCenter = new Point2D.Double(0, 0);
		targetZombie = new Point2D.Double(-1, 0);
		testZombie = new Point2D.Double(0, 1);
		angleCenter = Math.atan2(targetZombie.getY() - towerCenter.getY(), targetZombie.getX() - towerCenter.getX());
		assertFalse(ConeFireStrategy.isZombieInAngle(angleCenter, testZombie, towerCenter, ConeFireStrategy.CONE_HALFANGLE));
		
		towerCenter = new Point2D.Double(0, 0);
		targetZombie = new Point2D.Double(-1, 0);
		testZombie = new Point2D.Double(-0.5, 0.2);
		angleCenter = Math.atan2(targetZombie.getY() - towerCenter.getY(), targetZombie.getX() - towerCenter.getX());
		assertTrue(ConeFireStrategy.isZombieInAngle(angleCenter, testZombie, towerCenter, ConeFireStrategy.CONE_HALFANGLE));
	}
}
