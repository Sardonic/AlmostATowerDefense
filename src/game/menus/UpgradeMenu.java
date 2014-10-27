package game.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import game.base.*;
import game.grid.Tile;
import game.towers.BaseTower;

public class UpgradeMenu implements MouseListener, GameItem {
	private Point2D position;
	private Tile tile;
	private Room parent;
	private Rectangle sellRect;
	private Rectangle upgradeRect;
	
	private final static Color BOX_COLOR = Color.BLACK;
	private final static Color UPGRADE_COLOR = Color.ORANGE;
	private final static Color SELL_COLOR = Color.GREEN;
	private final static Color INSUFFICIENT_FUNDS_COLOR = Color.GRAY;
	private final static Font FONT = new Font("Courrier", Font.BOLD, 18);
	private final static String UPGRADE_TEXT = "U";
	private final static String SELL_TEXT = "$";
	private final static int X_OFFSET = 10;
	private final static int Y_OFFSET = 20;
	private final static int ITEM_WIDTH = Tile.WIDTH / 2;
	private final static int ITEM_HEIGHT = Tile.HEIGHT / 2;
	
	private final static int WIDTH = Tile.WIDTH;
	private final static int HEIGHT = Tile.HEIGHT;
	
	public UpgradeMenu(Room parent, Tile tile) {
		this.tile = tile;
		position = tile.getULCorner();
		this.parent = parent;
		sellRect = new Rectangle(0, 0, ITEM_WIDTH, ITEM_HEIGHT);
		upgradeRect = new Rectangle(ITEM_WIDTH, 0, ITEM_WIDTH, ITEM_HEIGHT);
		parent.addMouseListener(this);
	}

	public Point2D getPos() {
		return position;
	}

	public void update() {
		// Empty
	}

	public void draw(Graphics g) {
		g.setColor(BOX_COLOR);
		drawRect(g, upgradeRect);
		drawRect(g, sellRect);

		g.setFont(FONT);
		
		if (tile.getTower() != null && parent.getUser().getMoney() >= tile.getTower().getMoneyValue()) {
			g.setColor(UPGRADE_COLOR);
		} else {
			g.setColor(INSUFFICIENT_FUNDS_COLOR);
		}
		drawOffsetString(g, UPGRADE_TEXT, upgradeRect.getX(), upgradeRect.getY());
		
		g.setColor(SELL_COLOR);
		drawOffsetString(g, SELL_TEXT, sellRect.getX(), sellRect.getY());
	}

	private void drawOffsetString(Graphics g, String text, double localX, double localY) {
		int x = (int)(position.getX() + localX + X_OFFSET);
		int y = (int)(position.getY() + localY + Y_OFFSET);
				
		g.drawString(text, x, y);
	}

	private void drawRect(Graphics g, Rectangle rect) {
		int x = (int)(position.getX() + rect.getX());
		int y = (int)(position.getY() + rect.getY());
		
		g.drawRect(x, y, rect.width, rect.height);
	}

	public Tag getTag() {
		return Tag.MENU;
	}

	public void mouseClicked(MouseEvent e) {
		Point2D roomPoint = e.getPoint();
		Point2D localPoint = parent.getPointRelativeToUL(position, roomPoint);
		
		if (checkClickedOutside(localPoint)) {
			parent.removeMouseListener(this);
			parent.removeUnit(this);
		} else if (sellRect.contains(localPoint)) {
			parent.sellTower(tile);
			parent.removeMouseListener(this);
			parent.removeUnit(this);
		} else if (upgradeRect.contains(localPoint)) {
			User user = parent.getUser();
			BaseTower tower = tile.getTower();
			if (user.getMoney() >= tower.getMoneyValue()) {
				user.spendMoney(tower.getMoneyValue());
				parent.upgradeTower(tile);
				parent.removeMouseListener(this);
				parent.removeUnit(this);
			} else {
				System.out.println("Insufficient funds to upgrade");
				System.out.println("\tCost: " + tower.getMoneyValue());
			}
		}
	}
	
	public boolean checkClickedOutside(Point2D localPoint) {
		boolean isXNegative = localPoint.getX() < 0;
		boolean isXTooBig = localPoint.getX() > WIDTH;
		boolean isYNegative = localPoint.getY() < 0;
		boolean isYTooBig = localPoint.getY() > HEIGHT;
		return isXNegative || isXTooBig || isYNegative || isYTooBig;
	}

	public void mouseEntered(MouseEvent arg0) {
		// Empty
	}

	public void mouseExited(MouseEvent arg0) {
		// Empty
	}

	public void mousePressed(MouseEvent arg0) {
		// Empty
	}

	public void mouseReleased(MouseEvent arg0) {
		// Empty
	}

}
