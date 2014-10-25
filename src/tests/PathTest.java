package tests;

import java.awt.geom.Point2D;

import game.grid.Path;
import game.grid.Tile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {
	Path testPath;
	Tile tile1;
	Tile tile2;
	Tile tile3;
	@Before
	public void init() {
		testPath = new Path();
		tile1 = new Tile(new Point2D.Double(1, 1));
		tile2 = new Tile(new Point2D.Double(2, 2));
		tile3 = new Tile(new Point2D.Double(3, 3));
	}
	@Test
	public void findNextPointTest() {
		testPath.addTile(tile1);
		testPath.addTile(tile2);
		testPath.addTile(tile3);
		
		assertEquals(tile2, testPath.findNextTile(tile1));
		assertEquals(tile3, testPath.findNextTile(tile2));
		assertEquals(null, testPath.findNextTile(tile3));
	}
	@Test
	public void findIfItExists() {
		testPath.addTile(tile1);
		testPath.addTile(tile2);
		
		assertTrue(testPath.doesTileExist(tile1));
		assertTrue(!testPath.doesTileExist(tile3));
	}
}
