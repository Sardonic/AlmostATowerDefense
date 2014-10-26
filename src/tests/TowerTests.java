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
import game.towers.towertypes.MachineGunTower;
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
		assertEquals(MachineGunTower.FIRERATE, machineGun.getFireRate(), 0);
	}
	
	@Test
	public void testMachineGunRange() {
		assertEquals(MachineGunTower.RANGE, machineGun.getRange());
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
		int prevValue = machineGun.getMoneyValue();
		
		machineGun = new FireRateUpgrade(machineGun);
		
		assertEquals(prevFireRate * 1.5, machineGun.getFireRate(), 0.0001);
		assertEquals((int)(prevValue * 1.5), machineGun.getMoneyValue());
		
		prevFireRate = machineGun.getFireRate();
		prevValue = machineGun.getMoneyValue();
		
		machineGun = new FireRateUpgrade(machineGun);
		
		assertEquals(prevFireRate * 1.5, machineGun.getFireRate(), 0.0001);
		assertEquals((int)(prevValue * 1.5), machineGun.getMoneyValue());
	}
	@Test
	public void testDamageUpgrade() {
		int prevDamage = machineGun.getDamage();
		int prevValue = machineGun.getMoneyValue();
		
		machineGun = new DamageUpgrade(machineGun);
		
		assertEquals((int)(prevDamage * 1.25), machineGun.getDamage());
		assertEquals((int)(prevValue * 1.25), machineGun.getMoneyValue());
		
		prevDamage = machineGun.getDamage();
		prevValue = machineGun.getMoneyValue();
		
		machineGun = new DamageUpgrade(machineGun);
		
		assertEquals((int)(prevDamage * 1.25), machineGun.getDamage());
		assertEquals((int)(prevValue * 1.25), machineGun.getMoneyValue());
	}
}
