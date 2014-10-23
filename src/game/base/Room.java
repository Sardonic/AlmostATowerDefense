package game.base;

import game.base.GameItem.Tag;
import game.grid.Grid;
import game.grid.Path;
import game.grid.Tile;
import game.towers.TowerFactory;
import game.wave.WaveManager;
import game.zombies.Zombie;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Room extends JPanel {
	private final int height;
	private final int width;
	
	private Collection<GameItem> units;
	private Collection<GameItem> unitsToAddAtEndOfFrame;
	private Collection<GameItem> unitsToRemoveAtEndOfFrame;
	private WaveManager waveManager;
	private Grid grid;
	private Path path;
	private User user;

	public Room(int height, int width) {
		this.height = height;
		this.width = width;
		setPreferredSize(new Dimension(width, height));

		grid = new Grid(width / Tile.WIDTH, height / Tile.HEIGHT);
		
		makePath();
		
		user = new User();
		units = new ArrayList<GameItem>();
		unitsToAddAtEndOfFrame = new ArrayList<GameItem>();
		unitsToRemoveAtEndOfFrame = new ArrayList<GameItem>();
		waveManager = new WaveManager(this);
		
		init();
	}

	private void makePath() {
		path = new Path();
		
		path.addTile(grid.getTile(0, 5));
		path.addTile(grid.getTile(1,5));
		path.addTile(grid.getTile(2, 5));
		path.addTile(grid.getTile(2, 4));
		path.addTile(grid.getTile(2, 3));
		path.addTile(grid.getTile(3, 3));
		path.addTile(grid.getTile(4, 3));
		path.addTile(grid.getTile(4, 4));
		path.addTile(grid.getTile(5, 4));
		path.addTile(grid.getTile(6, 4));
		path.addTile(grid.getTile(7, 4));
	}
	
	public Collection<GameItem> getAllUnitsWithTag(GameItem.Tag tag) {
		ArrayList<GameItem> unitsWithTag = new ArrayList<GameItem>();
		
		// This is O(n) complexity. For O(1), we'd need a map
		// object (or other associative container) to handle the
		// caching of tagged objects. If we end up having a lot of
		// units, this might become necessary, but it is much more complex to maintain.
		for (GameItem unit : units) {
			if (unit.getTag() == tag) {
				unitsWithTag.add(unit);
			}
		}
		
		return unitsWithTag;
	}
	
	private void init() {
		units.add(TowerFactory.makeMachineGunTower(this, grid.getTile(5, 5).getCenter()));
		units.add(TowerFactory.makeRocketTower(this, grid.getTile(1, 4).getCenter()));
	}

	public boolean mainLoop() {
		waveManager.update();
		updateAllUnits();
		
		return false;
	}

	public void addUnit(GameItem unit) {
		unitsToAddAtEndOfFrame.add(unit);
		if (unit.getTag() == GameItem.Tag.ZOMBIE) {
			Zombie zombie = (Zombie) unit;
			zombie.addObserver(user);
		}
	}
	
	public void removeUnit(GameItem unit) {
		unitsToRemoveAtEndOfFrame.add(unit);
		if (Tag.ZOMBIE == unit.getTag()) {//"zombie".equals(unit.getTag()) tags an enum yo, that ain't gonna work
			Zombie zombie = (Zombie) unit;
			zombie.removeObserver(user);
		}
	}
	
	public void removeUnits(Collection<GameItem> toRemove) {
		for (GameItem unit : toRemove) {
			unitsToRemoveAtEndOfFrame.add(unit);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		grid.draw(g);
		for (GameItem unit : units) {
			unit.draw(g);
		}
		user.draw(g);
	}
	
	private void updateAllUnits() {
		for (GameItem unit : units) {
			unit.update();
		}
		addAndRemoveUnits();
	}

	public void addAndRemoveUnits() {
		for (GameItem unit : unitsToAddAtEndOfFrame) {
			units.add(unit);
		}
		
		for (GameItem unit : unitsToRemoveAtEndOfFrame) {
			units.remove(unit);
		}
		
		unitsToAddAtEndOfFrame.clear();
		unitsToRemoveAtEndOfFrame.clear();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public Collection<GameItem> getUnits() {
		return units;
	}
	
	public boolean doesUnitExit(GameItem unit) {
		return units.contains(unit);
	}
	
	public Path getPath() {
		return path;
	}
}
