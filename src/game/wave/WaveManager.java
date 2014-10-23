package game.wave;

import game.base.GameItem;
import game.base.Room;

import java.util.Collection;
import java.util.Iterator;

public class WaveManager {
	private final int MAX_ZOMBIES_PER_WAVE = 20;
	private final int FRAMES_BETWEEN_WAVES = 90;
	private int FRAMES_BETWEEN_ZOMBIES = 2;
	private int framesUntilNextWave;
	private int framesUntilNextZombie;
	private Iterator<GameItem> zombieIter;
	private int waveNum;
	private Room parentRoom;
	

	public WaveManager(Room parentRoom) {
		framesUntilNextWave = 0;//immediatly star sending out zombles no mercy
		framesUntilNextZombie = FRAMES_BETWEEN_ZOMBIES;
		waveNum = 1;
		zombieIter = null;
		this.parentRoom = parentRoom;
	}
	
	public void update() {
		Wave nextWave = new Wave(waveNum, parentRoom);
		Collection<GameItem> zombiesThisWave;
		int zombiesToSpawn;
		
		framesUntilNextWave--;
		
		if (framesUntilNextWave <= 0) {
			zombiesThisWave = nextWave.getZombies();
			zombieIter = zombiesThisWave.iterator();
			waveNum++;
			zombiesToSpawn = Math.min(waveNum, MAX_ZOMBIES_PER_WAVE);
			nextWave = new Wave(zombiesToSpawn, parentRoom);
			framesUntilNextWave = FRAMES_BETWEEN_WAVES;
		}
		
		framesUntilNextZombie--;
		
		if (framesUntilNextZombie <= 0) {
			if (zombieIter != null && zombieIter.hasNext()) {
				parentRoom.addUnit(zombieIter.next());
			}
			
			framesUntilNextZombie = FRAMES_BETWEEN_ZOMBIES;
		}
	}
}
