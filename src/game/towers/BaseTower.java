package game.towers;

import game.base.GameItem;
import game.zombies.Zombie;

public interface BaseTower extends GameItem {
	double getFireRate();
	void setFireRate(double fireRate);
	int getRange();
	void setRange(int range);
	int getDamage();
	void setDamage(int damage);
	Zombie acquireClosestZombie();
}
