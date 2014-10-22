package game.wave;

import game.base.GameItem;
import game.base.Room;
import game.grid.Path;
import game.zombies.ZombieFactory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Wave {
	ArrayList<GameItem> units;
	private int maxY;
	private Room parentRoom;
	private final int xVariance = 33;
	private final int maxZombiesInAWave = 14;

	public Wave(int numZombies, Room parentRoom) {
		maxY = parentRoom.getHeight();
		this.parentRoom = parentRoom;
		
		units = new ArrayList<GameItem>();
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		
		if(numZombies > maxZombiesInAWave) {
			numZombies = maxZombiesInAWave;
		}
		
		makeZombies(numZombies, random);
	}

	private void makeZombies(int numZombies, Random random) {
		for (int i = 0; i < numZombies; i++) {
			Path path = parentRoom.getPath();
			double highestTileCenterY = path.getHighestTile().getCenter().getY();
			double lowestTileCenterY = path.getLowestTile().getCenter().getY();
			double randomY = random.nextInt((int) (lowestTileCenterY - highestTileCenterY)) + highestTileCenterY;
			int randomX = random.nextInt(xVariance);
			GameItem newZombie;
			
			switch (random.nextInt(4)) {
				case 0:
					newZombie = ZombieFactory.makeWalker(parentRoom, path);
					break;
				case 1:
					newZombie = ZombieFactory.makeRunner(parentRoom, path);
					break;
				case 2:
					newZombie = ZombieFactory.makeFatty(parentRoom, path);
					break;
				case 3:
					Point2D randomSpawnPoint = new Point2D.Double(randomX - xVariance, randomY);
					newZombie = ZombieFactory.makeImp(parentRoom, randomSpawnPoint);
					break;
				default:
					newZombie = ZombieFactory.makeWalker(parentRoom, path);
					break;
			}
			
			units.add(newZombie);
		}
	}
	
	public int getMaxY() {
		return maxY;
	}
	
	public void setMaxY(int y) {
		maxY = y;
	}
	
	public ArrayList<GameItem> getZombies() {
		return units;
	}
}
