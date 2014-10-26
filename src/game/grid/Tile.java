package game.grid;

import game.towers.BaseTower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class Tile {
	static public final int HEIGHT = 64;
	static public final int WIDTH = 64;
	private Point2D centerPoint;
	private Point2D ulCorner;
	private Color color;
	private boolean isEmpty;
	private BaseTower towerReferance;
	
	public Tile(Point2D point) {
		centerPoint = new Point2D.Double();
		ulCorner = point;
		centerPoint.setLocation(point.getX() + WIDTH/2, point.getY() + HEIGHT/2);
		color = Color.WHITE;
		isEmpty = true;
		towerReferance = null;
	}
	
	public Point2D getCenter() {
		return centerPoint;
	}
	
	public Point2D getULCorner() {
		return ulCorner;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setEmpty(boolean empty) {
		isEmpty = empty;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setTower(BaseTower tower) {
		towerReferance = tower;
	}
	
	public BaseTower getTower() {
		return towerReferance;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)centerPoint.getX() - WIDTH/2, (int)centerPoint.getY() - HEIGHT/2, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.drawRect((int)centerPoint.getX() - WIDTH/2, (int)centerPoint.getY() - HEIGHT/2, WIDTH, HEIGHT);
		g.fillOval((int)centerPoint.getX() - 1, (int)centerPoint.getY() - 1, 2, 2);
	}
}
