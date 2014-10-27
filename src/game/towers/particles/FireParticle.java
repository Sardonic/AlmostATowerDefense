package game.towers.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Random;

import game.base.GameItem;

public class FireParticle implements GameItem {
	private Point2D center;
	private Rectangle boundingBox;
	private Color color;
	private double vx;
	private double vy;
	
	public static final double RADIUS = 5;
	public static final Color START_COLOR = Color.RED;
	public static final Color END_COLOR = Color.GRAY;
	public static final double VELOCITY = 10;
	
	public FireParticle(Point2D center, double angle, double angleVariance) {
		this.center = new Point2D.Double(center.getX(), center.getY());
		boundingBox = new Rectangle();
		boundingBox.x = (int)(center.getX() - RADIUS);
		boundingBox.y = (int)(center.getY() - RADIUS);
		boundingBox.width = (int)(RADIUS * 2);
		boundingBox.height = (int)(RADIUS * 2);
		Random random = new Random();
		boolean negative = random.nextBoolean();
		double realAngle;
		double sign;
		if (negative) {
			sign = -1;
		} else {
			sign = 1;
		}
		
		realAngle = sign * random.nextDouble() * angleVariance + angle;
		vx = Math.cos(realAngle) * VELOCITY;
		vy = Math.sin(realAngle) * VELOCITY;
		color = START_COLOR;
	}

	public Point2D getPos() {
		return center;
	}

	public void update() {
		double newX = vx + center.getX();
		double newY = vy + center.getY();
		center.setLocation(newX, newY);
		boundingBox.x = (int)(newX - RADIUS);
		boundingBox.y = (int)(newY - RADIUS);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
	}

	public Tag getTag() {
		return Tag.PARTICLE;
	}

}
