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
import game.towers.BaseTower;
import game.towers.TowerFactory;
import game.towers.upgrades.DamageUpgrade;
import game.towers.upgrades.FireRateUpgrade;
import game.zombies.Zombie;
import game.zombies.ZombieFactory;

public class TowerTests {
	private BaseTower machineGun;
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
		assertEquals(TowerFactory.MACHINEGUNFIRERATE, machineGun.getFireRate(), 0);
	}
	
	@Test
	public void testMachineGunRange() {
		assertEquals(TowerFactory.MACHINEGUNRANGE, machineGun.getRange());
	}
	
	@Test
	public void testClosestZombie() {
		zombie1.setPos(new Point2D.Double(200, 200));
		zombie2.setPos(new Point2D.Double(0, 0));
		assertEquals(zombie2, machineGun.acquireClosestZombie());
	}
	@Test
	public void testFireRateUpgrade() {
		double prevFireRate = machineGun.getFireRate();
		
		machineGun = new FireRateUpgrade(machineGun);
		
		assertEquals(prevFireRate * 1.5, machineGun.getFireRate(), 0.0001);
		
		prevFireRate = machineGun.getFireRate();
		
		machineGun = new FireRateUpgrade(machineGun);
		
		assertEquals(prevFireRate * 1.5, machineGun.getFireRate(), 0.0001);
	}
	@Test
	public void testDamageUpgrade() {
		int prevDamage = machineGun.getDamage();
		
		machineGun = new DamageUpgrade(machineGun);
		
		assertEquals((int)(prevDamage * 1.25), machineGun.getDamage(), 0.001);
		
		prevDamage = machineGun.getDamage();
		
		machineGun = new DamageUpgrade(machineGun);
		
		assertEquals((int)(prevDamage * 1.25), machineGun.getDamage(), 0.001);
	}
}
