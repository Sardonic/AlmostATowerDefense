package game.menus;

import game.base.*;
import game.grid.Tile;
import game.towers.towertypes.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class PurchaseMenu implements MouseListener, GameItem {
	private static final int height = Tile.HEIGHT;
	private static final int width = Tile.WIDTH;
	
	private static final String MACHINEGUN_IMAGE_LOCATION = "machinegun.jpg";
	private static final String ROCKET_IMAGE_LOCATION = "Rocket.png";
	private static final String FIRE_IMAGE_LOCATION = "Fire.jpg";
	
	private static enum Item {
		MACHINEGUN,
		ROCKET,
		FIRE
	}
	
	private EnumMap<Item, Rectangle> items;
	private Point2D position;
	private Room parent;
	private BufferedImage machinegunImage;
	private BufferedImage rocketImage;
	private BufferedImage flameImage;
	
	public PurchaseMenu(Room parent, Point2D ULcorner) {
		position = ULcorner;
		this.parent = parent;
		items = new EnumMap<Item, Rectangle>(Item.class);
		
		try {
			machinegunImage = ImageIO.read(new File(MACHINEGUN_IMAGE_LOCATION));
			rocketImage = ImageIO.read(new File(ROCKET_IMAGE_LOCATION));
			flameImage = ImageIO.read(new File(FIRE_IMAGE_LOCATION));
		} catch (IOException e) {
		}
		
		final int ITEM_WIDTH = width / 2;
		final int ITEM_HEIGHT = height / 2;
		
		Rectangle machineGunRect = new Rectangle(0, 0, ITEM_WIDTH, ITEM_HEIGHT);
		Rectangle rocketRect = new Rectangle(width / 2, 0, ITEM_WIDTH, ITEM_HEIGHT);
		Rectangle fireRect = new Rectangle(0, height / 2, ITEM_WIDTH, ITEM_HEIGHT);
		
		items.put(Item.MACHINEGUN, machineGunRect);
		items.put(Item.ROCKET, rocketRect);
		items.put(Item.FIRE, fireRect);
		
		parent.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		Point2D roomPoint = e.getPoint();
		Point2D localPoint = parent.getPointRelativeToUL(position, roomPoint);
		for (Map.Entry<Item, Rectangle> item : items.entrySet()) {
			if (item.getValue().contains(localPoint)) {
				int tileX = (int)(position.getX() / Tile.WIDTH);
				int tileY = (int)(position.getY() / Tile.HEIGHT);
				User user = parent.getUser();
				
				switch (item.getKey()) {
				case MACHINEGUN:
					if (user.getMoney() > MachineGunTower.VALUE) {
						parent.placeMachineGunTower(tileX, tileY);
						user.spendMoney(MachineGunTower.VALUE);
					} else {
						System.out.println("Insufficient funds");
					}
					break;
				case FIRE:
					if (user.getMoney() > FireTower.VALUE) {
						parent.placeFireTower(tileX, tileY);
						user.spendMoney(FireTower.VALUE);
					} else {
						System.out.println("Insufficient funds");
					}
					break;
				case ROCKET:
					if (user.getMoney() > RocketTower.VALUE) {
						parent.placeRocketTower(tileX, tileY);
						user.spendMoney(RocketTower.VALUE);
					} else {
						System.out.println("Insufficient funds");
					}
					break;
				default:
					System.err.println("Tried to place a non-existant tower type");
					break;
				}
				
				parent.removeMouseListener(this);
				parent.removeUnit(this);
			}
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(machinegunImage, (int)position.getX(), (int)position.getY(), width/2, height/2, null);
		g.drawImage(rocketImage, (int)position.getX() + width/2, (int)position.getY(), width/2, height/2, null);
		g.drawImage(flameImage, (int)position.getX(), (int)position.getY() + height/2, width/2, height/2, null);
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

	public Point2D getPos() {
		return position;
	}

	public void update() {
		
	}

	public Tag getTag() {
		return Tag.MENU;
	}
}
