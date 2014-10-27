package game.towers;

import game.base.GameItem;
import game.base.Room;
import game.towers.strategy.TowerStrategy;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

public class Tower implements BaseTower {
	private Point2D pos;
	private Point2D center;
	private double fireRate; // in shots/second (second = 60 frames)
	private int range;
	private int damage;
	private int width;
	private int height;
	private int moneyValue;
	private Color color;
	private Room parentRoom;
	private TowerStrategy strategy;
	private BufferedImage image;
	
	public Tower(Room parent, Point2D position, TowerStrategy strategy, double fireRate, int range, int width, int height, int damage, int moneyVal, Color color) {
		center = position;
		this.strategy = strategy;
		this.pos = new Point2D.Double(position.getX() - width, position.getY() - height);
		this.fireRate = fireRate;
		this.range = range;
		this.width = width;
		this.height = height;
		this.damage = damage;
		moneyValue = moneyVal;
		this.color = color;
		this.parentRoom = parent;
		try {
			image = ImageIO.read(new File("Charizard.png"));
		} catch (IOException e) {
		}
	}
	
	public Tag getTag() {
		return Tag.TOWER;
	}
	
	public Point2D getPos() {
		return pos;
	}

	public void update() {
		strategy.update(this);
	}

	public Zombie acquireClosestZombie() {
		Collection<GameItem> zombies = parentRoom.getAllUnitsWithTag(Tag.ZOMBIE);
		GameItem closestZombie = null;
		
		for (GameItem zombie : zombies) {
			boolean isZombieInRange = zombie.getPos().distance(center) <= range;
			if (closestZombie == null) {
				if (isZombieInRange) {
					closestZombie = zombie;
				}
			} else {
				boolean isZombieClosest = zombie.getPos().distance(center) < closestZombie.getPos().distance(center);
				if (isZombieClosest && isZombieInRange) {
					closestZombie = zombie;
				}
			}
		}
		
		return (Zombie)closestZombie; // Looks type-unsafe, but unless our tags are lying to us, we should be fine.
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawOval((int)pos.getX() - (range) + (width / 2), (int)pos.getY() - (range) + (height / 2), range * 2, range * 2);
		g.drawImage(image, (int)pos.getX(), (int)pos.getY(), width*2, height*2, null);
	}
	
	//getters and setters
	public double getFireRate() {
		return fireRate;
	}

	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setPos(Point2D pos) {
		this.pos = pos;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getMoneyValue() {
		return moneyValue;
	}

	public void setMoneyValue(int value) {
		moneyValue = value;
	}
	
	public Room getParentRoom() {
		return parentRoom;
	}

	public Point2D getCenter() {
		return center;
	}
	
	public void setStrategy(TowerStrategy strategy) {
		this.strategy = strategy;
	}

}
