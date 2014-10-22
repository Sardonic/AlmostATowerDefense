package tests;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.junit.*;

import static org.junit.Assert.*;
import game.base.Room;
import game.grid.Path;
import game.zombies.Zombie;
import game.zombies.ZombieFactory;

public class ZombieTests {
	private Zombie runner;
	private Zombie walker;
	private Zombie fatty;
	private Zombie imp;
	
	@Before
	public void init() {
		runner = ZombieFactory.makeRunner(new Room(512, 512), new Path());
		walker = ZombieFactory.makeWalker(new Room(512, 512), new Path());
		fatty = ZombieFactory.makeFatty(new Room(512, 512), new Path());
		imp = ZombieFactory.makeImp(new Room(512, 512), new Point2D.Double(0, 0));
	}

	@Test
	public void testRunnerHealth() {
		assertEquals(50, runner.getHealth());
	}
	
	@Test
	public void testWalkerHealth() {
		assertEquals(100, walker.getHealth());
	}
	
	@Test
	public void testFattyHealth() {
		assertEquals(200, fatty.getHealth());
	}
	
	@Test
	public void testRunnerSpeed() {
		assertEquals((double)3, runner.getSpeed(), 0);
	}
	
	@Test
	public void testWalkerSpeed() {
		assertEquals(2.0, walker.getSpeed(), 0);
	}
	
	@Test
	public void testFattySpeed() {
		assertEquals(1.5, fatty.getSpeed(), 0);
	}
	
	@Test
	public void testRunnerColor() {
		assertEquals(Color.GREEN, runner.getColor());
	}
	
	@Test
	public void testWalkerColor() {
		assertEquals(Color.BLUE, walker.getColor());
	}
	
	@Test
	public void testFattyColor() {
		assertEquals(Color.YELLOW, fatty.getColor());
	}
	
	@Test
	public void testImpHealth() {
		assertEquals(100, imp.getHealth());
	}
	
	@Test
	public void testImpSpeed() {
		assertEquals(2, imp.getSpeed(), 0);
	}
	
	@Test
	public void testImpColor() {
		assertEquals(Color.ORANGE, imp.getColor());
	}
}
