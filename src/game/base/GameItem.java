package game.base;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public interface GameItem {
	Point2D getPos();
	void update();
	void draw(Graphics g);
	Tag getTag();
	
	enum Tag {
		ZOMBIE,
		TOWER,
		PROJECTILE
	}
}
