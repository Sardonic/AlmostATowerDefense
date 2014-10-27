package game.towers.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Random;

import game.base.GameItem;
import game.base.Room;

public class FireParticle implements GameItem {
	private Point2D center;
	private Rectangle boundingBox;
	private Color color;
	private double vx;
	private double vy;
	private double distanceToTravel;
	private double distPerStep;
	private Room parentRoom;
	
	public static final double RADIUS = 5;
	public static final Color START_COLOR = Color.YELLOW;
	public static final Color END_COLOR = Color.RED;
	public static final double VELOCITY = 4;
	
	public FireParticle(Room parentRoom, Point2D center, double angle, double angleVariance, double distance) {
		this.parentRoom = parentRoom;
		this.center = new Point2D.Double(center.getX(), center.getY());
		this.distanceToTravel = distance;
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
		distPerStep = Math.sqrt(vx * vx + vy * vy);
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
		distanceToTravel -= distPerStep;
		if (distanceToTravel <= 0) {
			parentRoom.removeUnit(this);
		} else {
			interpolateColors();
		}
	}

	private void interpolateColors() {
		double T = distPerStep / distanceToTravel;
		int newR = (int)lerp(color.getRed(), END_COLOR.getRed(), T);
		int newG = (int)lerp(color.getGreen(), END_COLOR.getGreen(), T);
		int newB = (int)lerp(color.getBlue(), END_COLOR.getBlue(), T);
		newR = Math.min(newR, 255);
		newR = Math.max(newR, 0);
		newG = Math.min(newG, 255);
		newG = Math.max(newG, 0);
		newB = Math.min(newB, 255);
		newB = Math.max(newB, 0);
		color = new Color(newR, newG, newB);
	}
	
	private double lerp(double x, double y, double T) {
		return (1 - T) * x + T * y;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
	}

	public Tag getTag() {
		return Tag.PARTICLE;
	}

}
