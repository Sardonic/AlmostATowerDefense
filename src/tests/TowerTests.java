package tests;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.*;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import game.base.GameItem;
import game.base.Room;
import game.grid.Path;
import game.towers.Tower;
import game.towers.TowerFactory;
import game.zombies.Zombie;
import game.zombies.ZombieFactory;

public class TowerTests {
	private Tower machineGun;
	private Collection<GameItem> zombieList;
	private Zombie zombie1;
	private Zombie zombie2;
	@Mock Room room;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		zombieList = new ArrayList<GameItem>();
		zombie1 = ZombieFactory.makeRunner(room, new Path());
		zombie2 = ZombieFactory.makeRunner(room, new Path());
		zombieList.add(zombie1);
		zombieList.add(zombie2);
		when(room.getAllUnitsWithTag(GameItem.Tag.ZOMBIE)).thenReturn(zombieList);
		machineGun = TowerFactory.makeMachineGunTower(room, new Point2D.Double(0, 0));
	}
	
	@Test
	public void testMachineGunFireRate() {
		assertEquals(60, machineGun.getFireRate(), 0);
	}
	
	@Test
	public void testMachineGunRange() {
		assertEquals(200, machineGun.getRange());
	}
	
	@Test
	public void testClosestZombie() {
		zombie1.setPos(new Point2D.Double(200, 200));
		zombie2.setPos(new Point2D.Double(0, 0));
		assertEquals(zombie2, machineGun.acquireClosestZombie());
	}
}
