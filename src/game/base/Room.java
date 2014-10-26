package game.base;

import game.base.GameItem.Tag;
import game.grid.Grid;
import game.grid.Path;
import game.grid.Tile;
import game.towers.BaseTower;
import game.towers.TowerFactory;
import game.towers.upgrades.RangeUpgrade;
import game.wave.WaveManager;
import game.zombies.Zombie;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Room extends JPanel implements MouseListener {
	private final int height;
	private final int width;
	
	private Collection<GameItem> units;
	private Collection<GameItem> unitsToAddAtEndOfFrame;
	private Collection<GameItem> unitsToRemoveAtEndOfFrame;
	private WaveManager waveManager;
	private Grid grid;
	private Path path;
	private User user;
	
	//constructor
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
		
		// This smells of side-effects and is
		// making the tests nightmarish to maintian. Delorted.
		//addDefaultTowers();
	}
	
	//called in the constructor
	//this makes the path
	//totes hardcoded but we ain't no designers so...meh
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
	
	//main game logic
	//ie initilize and main loop
	public void addDefaultTowers() {
		placeMachineGunTower(5, 5);
		placeRocketTower(1, 4);
	}

	public boolean mainLoop() {
		waveManager.update();
		updateAllUnits();
		
		return false;
	}
	
	//room's functions
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
	
	private void openBuyMenu(int x, int y) {
		//TODO buy menu
		placeMachineGunTower(x, y);
	}
	
	private void openUpgradeMenu(int x, int y) {
		//TODO upgrade menu
		upgradeTower(x, y);
	}
	
	private void placeMachineGunTower(int x, int y) {
		Tile tile = grid.getTile(x, y);
		BaseTower tower = TowerFactory.makeMachineGunTower(this, tile.getCenter());
		
		unitsToAddAtEndOfFrame.add(tower);
		tile.setEmpty(false);
		tile.setTower(tower);
	}
	
	private void placeRocketTower(int x, int y) {
		Tile tile = grid.getTile(x, y);
		BaseTower tower = TowerFactory.makeRocketTower(this, tile.getCenter());
		
		unitsToAddAtEndOfFrame.add(tower);
		tile.setEmpty(false);
		tile.setTower(tower);
	}
	
	private void upgradeTower(int x, int y) {
		//TODO differentiate between upgrades
		Tile tile = grid.getTile(x, y);
		BaseTower tower = tile.getTower();
		
		if(tile.getTower() != null) {//should never fail but just in case
			units.remove(tower);
			tower = new RangeUpgrade(tower);
			tile.setTower(tower);
			units.add(tower);
		} else {
			//throw error
			System.out.println("Error There was no tower there.");
		}
	}
	
	private void sellTower(int x, int y) {
		Tile tile = grid.getTile(x, y);
		
		if(tile.getTower() != null) {//should never fail but just in case
			unitsToRemoveAtEndOfFrame.add(tile.getTower());
			tile.setTower(null);
			tile.setEmpty(true);
		} else {
			//throw error
			System.out.println("Error There was no tower there.");
		}
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
	
//getter stuffs
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
	
	//draw stuff, overridden from Jpanel
	@Override
	public void paintComponent(Graphics g) {
		grid.draw(g);
		for (GameItem unit : units) {
			unit.draw(g);
		}
		user.draw(g);
	}
	
	//mouse listeners functions
	public void mouseClicked(MouseEvent e) {
		int mouseX;
		int mouseY;
		
		mouseX = e.getX() / Tile.WIDTH;
		//mouseY need to be offset
		mouseY = (e.getY() - (Tile.HEIGHT/2)) / Tile.HEIGHT;
		
		Tile tile = grid.getTile(mouseX, mouseY);
		
		if(tile.getEmpty() && !path.doesTileExist(tile)){
			openBuyMenu(mouseX, mouseY);
		} else if(!tile.getEmpty() && !path.doesTileExist(tile)) {
			openUpgradeMenu(mouseX, mouseY);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {

	}
}
