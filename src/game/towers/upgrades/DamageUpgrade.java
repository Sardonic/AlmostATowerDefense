package game.towers.upgrades;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.towers.BaseTower;
import game.towers.TowerModifier;
import game.zombies.Zombie;

public class DamageUpgrade implements TowerModifier {

	private BaseTower baseTower;
	private final double modifier = 1.25;
	
	public DamageUpgrade(BaseTower tower) {
		baseTower = tower;
		setDamage((int) (getDamage() * modifier));
		setMoneyValue((int) (getMoneyValue() * modifier));
	}
	
	public double getFireRate() {
		return baseTower.getFireRate();
	}

	public int getRange() {
		return baseTower.getRange();
	}

	public Point2D getPos() {
		return baseTower.getPos();
	}

	public void update() {
		baseTower.update();
	}

	public void draw(Graphics g) {
		baseTower.draw(g);
	}

	public Tag getTag() {
		return baseTower.getTag();
	}

	public Zombie acquireClosestZombie() {
		return baseTower.acquireClosestZombie();
	}

	public void setFireRate(double fireRate) {
		baseTower.setFireRate(fireRate);
	}

	public void setRange(int range) {
		baseTower.setRange(range);
	}

	public int getDamage() {
		return baseTower.getDamage();
	}

	public void setDamage(int damage) {
		baseTower.setDamage(damage);
	}

	public int getMoneyValue() {
		return baseTower.getMoneyValue();
	}

	public void setMoneyValue(int value) {
		baseTower.setMoneyValue(value);
	}
}
