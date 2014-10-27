package game.base;

import game.zombies.Zombie;
import game.zombies.ZombieObserver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class User implements ZombieObserver {
	private static final Font FONT = new Font("Courrier", Font.BOLD, 18);
	private static final Color LIFE_COLOR = new Color(255, 128, 0);
	private static final String LIFE_PREFIX = "\u2764 ";
	private static final Color CASH_COLOR = new Color(30, 128, 18);
	private static final String CASH_PREFIX = " $ ";
	private final int CASH_X = 8;
	private final int CASH_Y = 65;
	private final int HEALTH_X = CASH_X;
	private final int HEALTH_Y = CASH_Y - 20;
	private int money;
	private int health;
	
	public User() {
		money = 1000000;
		health = 50;
	}

	public void onZombieDie(Zombie zombie) {
		money += zombie.getValue();
	}

	public void onZombieCompletePath(Zombie zombie) {
		health -= 1;
	}

	public void draw(Graphics g) {
		g.setColor(LIFE_COLOR);
		g.setFont(FONT);
		g.drawString(LIFE_PREFIX + health, HEALTH_X, HEALTH_Y);
		g.setColor(CASH_COLOR);
		g.drawString(CASH_PREFIX + money, CASH_X, CASH_Y);
	}
	
	public int getMoney() {
		return money;
	}
	
	public void spendMoney(int moneySpent) {
		money -= moneySpent;
	}
	
	public void giveMoney(int moneyGained) {
		money += moneyGained;
	}
	
	public int getHealth() {
		return health;
	}
}
