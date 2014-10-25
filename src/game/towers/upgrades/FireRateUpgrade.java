package game.towers.upgrades;

import game.towers.BaseTower;
import game.towers.TowerModifier;
import game.zombies.Zombie;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public class FireRateUpgrade implements TowerModifier {

	private BaseTower baseTower;
	private final double modifier = 1.5;
	
	public FireRateUpgrade(BaseTower tower) {
		baseTower = tower;
		baseTower.setFireRate(getFireRate() * modifier);
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

}
