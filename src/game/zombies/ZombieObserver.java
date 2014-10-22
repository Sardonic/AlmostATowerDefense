package game.zombies;


public interface ZombieObserver {
	void onZombieDie(Zombie zombie);
	void onZombieCompletePath(Zombie zombie);
}
