package game.towers.projectiles;

import game.base.GameItem;
import game.base.Room;
import game.zombies.Zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class Projectile implements GameItem {
	
	private Point2D pos;
	private int damage;
	private double diameter;
	private Color color;
	private Zombie target;
	private Room parentRoom;
	private double speed;
	
	public Projectile(Room parent, Point2D pos, Zombie target, int damage, double diameter, Color color, double speed) {
		this.damage = damage;
		this.diameter = diameter;
		this.color = color;
		this.speed = speed;
		this.pos = new Point2D.Double(pos.getX(), pos.getY());
		this.target = target;
		this.parentRoom = parent;
	}
	
	public Tag getTag() {
		return Tag.PROJECTILE;
	}

	public Point2D getPos() {
		return pos;
	}

	public void update() {
		// This function doesn't seem to calculate distance to target twice, but maybe it's just very subtle. Could you explain further?
		double meToThemX = target.getPos().getX() - pos.getX();
		double meToThemY = target.getPos().getY() - pos.getY();
		double dist = Point2D.distance(target.getPos().getX(), target.getPos().getY(), pos.getX(), pos.getY());
		double vx = (meToThemX / dist) * speed;
		double vy = (meToThemY / dist) * speed;
		
		if (dist < speed) {
			hurtZombie(target);
			parentRoom.removeUnit(this);
		} else {
			pos.setLocation(pos.getX() + vx, pos.getY() + vy);
			
			if (testCollision(target)) {
				hurtZombie(target);
				parentRoom.removeUnit(this);
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)pos.getX(), (int)pos.getY(), (int)diameter, (int)diameter);
	}
	
	public void hurtZombie(Zombie zombie) {
		zombie.reduceHealth(damage);
	}
	
	public boolean testCollision(Zombie zombie) {
		double radius = diameter / 2;
		Point2D center = new Point2D.Double(pos.getX() + radius, pos.getY() + radius);
		double zombieRadius = ((zombie.getWidth() / 2) + (zombie.getHeight() / 2)) / 2; // approximate radius if oval, exact if circle.
		Point2D zombieCenter = new Point2D.Double(zombie.getPos().getX() + (zombie.getWidth() / 2), zombie.getPos().getY() + (zombie.getHeight() / 2));
		double distance = center.distance(zombieCenter);
		
		return distance < radius + zombieRadius;
	}
}
