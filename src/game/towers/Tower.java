package game.towers;

import game.base.Game;
import game.base.GameItem;
import game.base.Room;
import game.towers.projectiles.ProjectileFactory;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Collection;

public class Tower implements BaseTower {
	private Point2D pos;
	private Point2D center;
	private double fireRate; // in shots/second (second = 60 frames)
	private double framesUntilFire;
	private int range;
	private int damage;
	private int width;
	private int height;
	private Color color;
	private Room parentRoom;
	private Zombie target;
	private ProjectileFactory projectileFactory;
	
	public Tower(Room parent, Point2D position, ProjectileFactory projectileFactory, double fireRate, int range, int width, int height, int damage, Color color) {
		center = position;
		this.projectileFactory = projectileFactory;
		this.pos = new Point2D.Double(position.getX() - width/2, position.getY() - height/2);
		this.fireRate = fireRate;
		this.framesUntilFire = 0;
		this.range = range;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.color = color;
		this.parentRoom = parent;
		target = null;
	}
	
	public Tag getTag() {
		return Tag.TOWER;
	}
	
	public Point2D getPos() {
		return pos;
	}

	public void update() {
		if (target != null) {
			if (target.getPos().distance(pos) > range) {
				target = null;
			} else if (target.getHealth() <= 0 || parentRoom.doesUnitExit(target)) {
				target = null;
			}
		}
		
		if (target == null) {
			target = acquireClosestZombie();
		}
		
		if (framesUntilFire > 0) {
			framesUntilFire--;
		}
		
		if (target != null) {
			if (framesUntilFire <= 0) {
				parentRoom.addUnit(projectileFactory.makeProjectile(parentRoom, new Point2D.Double(center.getX(), center.getY()), target, damage));
				framesUntilFire += Game.IDEAL_FPS / fireRate;
			}
		}
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
		g.setColor(color);
		g.fillOval((int)pos.getX(), (int)pos.getY(), width, height);
		g.setColor(Color.black);
		g.drawOval((int)pos.getX() - (range) + (width / 2), (int)pos.getY() - (range) + (height / 2), range * 2, range * 2);
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


}
