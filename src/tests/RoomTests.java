package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.base.GameItem;
import game.base.Room;
import game.zombies.Zombie;

public class RoomTests {
	private Room room;
	@Mock Zombie zombie1;
	@Mock Zombie zombie2;
	@Mock Zombie zombie3;
	
	@Before
	public void init() {
		room = new Room(512, 512);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddUnit() {
		// Size should start at 0, because we fixed
		// the side effect problem.
		room.addUnit(zombie1);
		room.addAndRemoveUnits();
		assertEquals(1, room.getUnits().size());
		
		room.addUnit(zombie2);
		room.addAndRemoveUnits();
		assertEquals(2, room.getUnits().size());
	}
	
	@Test
	public void testRemoveUnit() {
		room.addUnit(zombie1);
		room.addUnit(zombie2);
		room.removeUnit(zombie1);
		room.addAndRemoveUnits();
		assertEquals(1, room.getUnits().size());
	}
	
	@Test
	public void testRemoveUnits() {
		room.addUnit(zombie1);
		room.addUnit(zombie2);
		room.addUnit(zombie3);
		Collection<GameItem> zombies = new ArrayList<GameItem>();
		zombies.add(zombie1);
		zombies.add(zombie2);
		
		room.removeUnits(zombies);
		room.addAndRemoveUnits();
		
		assertEquals(1, room.getUnits().size());
	}
	
	@Test
	public void testDoestUnitExist() {
		room.addUnit(zombie1);
		room.addUnit(zombie2);
		
		room.addAndRemoveUnits();
		
		assertTrue(room.doesUnitExist(zombie1));
		assertFalse(room.doesUnitExist(zombie3));
	}
}
