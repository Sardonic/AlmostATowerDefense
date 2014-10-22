package game.zombies;


public interface ZombieObservable {
	void addObserver(ZombieObserver observer);
	void removeObserver(ZombieObserver observer);
	void notifyObserversDie();
	void notifyObserversCompletePath();
}
