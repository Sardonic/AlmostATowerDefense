package game.wave;

import game.base.GameItem;
import game.base.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WaveManager {
	private final int MAX_ZOMBIES_PER_WAVE = 20;
	private final int FRAMES_BETWEEN_WAVES = 60;
	private int FRAMES_BETWEEN_ZOMBIES = 2;
	private int framesUntilNextWave;
	private int framesUntilNextZombie;
	private Iterator<GameItem> zombieIter;
	private int zombiesToSpawn;
	private int waveNum;
	private Collection<GameItem> zombiesThisWave;
	private Room parentRoom;
	private Wave nextWave;

	public WaveManager(Room parentRoom) {
		framesUntilNextWave = FRAMES_BETWEEN_WAVES;
		framesUntilNextZombie = FRAMES_BETWEEN_ZOMBIES;
		waveNum = 1;
		zombiesToSpawn = waveNum;
		zombieIter = null;
		nextWave = new Wave(waveNum, parentRoom);
		zombiesThisWave = new ArrayList<GameItem>();
		this.parentRoom = parentRoom;
	}
	
	public void update() {
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
