package game.zombies;

import game.base.GameItem;
import game.base.Room;
import game.strategy.EnemyMovementStrategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

public class Zombie implements GameItem, ZombieObservable {
	private Room parentRoom;
	private Point2D pos;
	private int health;
	private double speed;
	private int width;
	private int height;
	private Color color;
	private EnemyMovementStrategy movement;
	private int value;
	private ArrayList<ZombieObserver> zombieObservers;

	public Zombie(Room parent, Point2D pos, int width, int height, int health, double speed, Color color, int value, EnemyMovementStrategy movement) {
		this.parentRoom = parent;
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.health = health;
		this.speed = speed;
		this.color = color;
		this.value = value;
		this.movement = movement;
		
		zombieObservers = new ArrayList<ZombieObserver>();
	}
	
	public void setMovement(EnemyMovementStrategy movement) {
		this.movement = movement;
	}

	public Tag getTag() {
		return Tag.ZOMBIE;
	}
	
	public Point2D getPos() {
		return pos;
	}
	
	public Point2D getCenter() {
		double centerX = pos.getX() + width / 2;
		double centerY = pos.getY() + height / 2;
		return new Point2D.Double(centerX, centerY);
	}

	public void update() {
		movement.update(this);
		if (pos.getX() > parentRoom.getWidth()) {
			notifyObserversCompletePath();
			parentRoom.removeUnit(this);
		}
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)pos.getX(), (int)pos.getY(), width, height);
	}
	
	
	//getters and setters
	public int getHealth() {
		return health;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getWidth() {
		return width;
	}
	
	public Room getParentRoom() {
		return parentRoom;
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

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void reduceHealth(int reduceBy) {
		this.health -= reduceBy;
		if (health <= 0) {
			notifyObserversDie();
			parentRoom.removeUnit(this);
		}
	}

	public void addObserver(ZombieObserver observer) {
		zombieObservers.add(observer);
	}

	public void removeObserver(ZombieObserver observer) {
		zombieObservers.remove(observer);
	}

	public void notifyObserversDie() {
		for (ZombieObserver observer : zombieObservers) {
			observer.onZombieDie(this);
		}
	}

	public void notifyObserversCompletePath() {
		for (ZombieObserver observer : zombieObservers) {
			observer.onZombieCompletePath(this);
		}
	}
}
