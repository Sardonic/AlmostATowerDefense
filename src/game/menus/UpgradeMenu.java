package game.menus;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.base.GameItem;

public class UpgradeMenu implements GameItem {
	private Point2D position;

	public Point2D getPos() {
		return position;
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	public Tag getTag() {
		return Tag.MENU;
	}

}
