package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.base.Room;
import game.wave.Wave;

public class WaveTest {
	private Wave wave;
	private Room room;

	@Before
	public void init() {
		room = new Room(512, 512);
		wave = new Wave(5, room);
	}
	
	@Test
	public void testNumZombies() {
		assertEquals(wave.getZombies().size(), 5);
	}
	
	@Test
	public void testMaxY() {
		assertTrue(wave.getMaxY() <= room.getHeight());
	}
}
