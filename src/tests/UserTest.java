package tests;

import game.base.User;
import game.zombies.Zombie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class UserTest {
	private static final int ZOMBIE_VALUE = 5;
	private User user;
	@Mock private Zombie zombie;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		when(zombie.getValue()).thenReturn(ZOMBIE_VALUE);
	}

	@Test 
	public void testOnZombieDie() {
		int oldMoney = user.getMoney();
		user.onZombieDie(zombie);
		assertEquals(oldMoney + ZOMBIE_VALUE, user.getMoney());
	}
	
	@Test
	public void testOnZombieCompletePath() {
		int oldHealth = user.getHealth();
		user.onZombieCompletePath(zombie);
		assertTrue(user.getHealth() < oldHealth);
	}
}
