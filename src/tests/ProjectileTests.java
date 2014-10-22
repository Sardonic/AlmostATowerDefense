package tests;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.MockitoAnnotations;

import game.base.Room;
import game.strategy.FlyMovement;
import game.towers.projectiles.Projectile;
import game.zombies.Zombie;

public class ProjectileTests {
	private Projectile projectile;
	private Zombie zombie1;
	private Zombie zombie2;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		zombie1 = new Zombie(new Room(512, 512), new Point2D.Double(5, 5), 20, 20, 20, 5, Color.BLUE, 5, new FlyMovement());
		zombie2 = new Zombie(new Room(512, 512), new Point2D.Double(5, 5), 50, 50, 20, 5, Color.BLUE, 5, new FlyMovement());
		projectile = new Projectile(new Room(512, 512), new Point2D.Double(10, 10), zombie1, 5, 5, Color.BLACK, 15);
	}
	
	@Test
	public void testDamage() {
		projectile.hurtZombie(zombie1);
		assertTrue(zombie1.getHealth() < zombie2.getHealth());
	}
	
	@Test
	public void testCollisionDetection() {
		zombie1.setPos(new Point2D.Double(0, 0));
		assertTrue(projectile.testCollision(zombie1));
		zombie1.setPos(new Point2D.Double(1000, 1000));
		assertFalse(projectile.testCollision(zombie1));
	}
}
