package game.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class Tile {
	static public final int HEIGHT = 64;
	static public final int WIDTH = 64;
	private Point2D centerPoint;
	private Color color;
	
	public Tile(Point2D point) {
		centerPoint = new Point2D.Double();
		centerPoint.setLocation(point.getX() + WIDTH/2, point.getY() + HEIGHT/2);
		color = Color.WHITE;
	}
	
	public Point2D getCenter() {
		return centerPoint;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)centerPoint.getX() - WIDTH/2, (int)centerPoint.getY() - HEIGHT/2, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.drawRect((int)centerPoint.getX() - WIDTH/2, (int)centerPoint.getY() - HEIGHT/2, WIDTH, HEIGHT);
		g.fillOval((int)centerPoint.getX() - 1, (int)centerPoint.getY() - 1, 2, 2);
	}
}
