package game.menus;

import game.base.*;
import game.grid.Tile;
import game.towers.towertypes.*;

import java.awt.Graphics;
import java.awt.Image;
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
	private static final int HEIGHT = Tile.HEIGHT;
	private static final int WIDTH = Tile.WIDTH;
	
	private static final String MACHINEGUN_IMAGE_LOCATION = "machinegun.jpg";
	private static final String ROCKET_IMAGE_LOCATION = "Rocket.png";
	private static final String FIRE_IMAGE_LOCATION = "Fire.jpg";
	private static final int ITEM_WIDTH = WIDTH / 2;
	private static final int ITEM_HEIGHT = HEIGHT / 2;
	
	private static enum Item {
		MACHINEGUN,
		ROCKET,
		FIRE
	}
	
	private EnumMap<Item, Rectangle> items;
	private Point2D position;
	private Tile tile;
	private Room parent;
	private BufferedImage machinegunImage;
	private BufferedImage rocketImage;
	private BufferedImage flameImage;
	
	public PurchaseMenu(Room parent, Tile tile) {
		this.tile = tile;
		position = tile.getULCorner();
		this.parent = parent;
		items = new EnumMap<Item, Rectangle>(Item.class);
		
		try {
			machinegunImage = ImageIO.read(new File(MACHINEGUN_IMAGE_LOCATION));
			rocketImage = ImageIO.read(new File(ROCKET_IMAGE_LOCATION));
			flameImage = ImageIO.read(new File(FIRE_IMAGE_LOCATION));
		} catch (IOException e) {
		}
		
		
		Rectangle machineGunRect = new Rectangle(0, 0, ITEM_WIDTH, ITEM_HEIGHT);
		Rectangle rocketRect = new Rectangle(WIDTH / 2, 0, ITEM_WIDTH, ITEM_HEIGHT);
		Rectangle fireRect = new Rectangle(0, HEIGHT / 2, ITEM_WIDTH, ITEM_HEIGHT);
		
		items.put(Item.MACHINEGUN, machineGunRect);
		items.put(Item.ROCKET, rocketRect);
		items.put(Item.FIRE, fireRect);
		
		parent.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		Point2D roomPoint = e.getPoint();
		Point2D localPoint = parent.getPointRelativeToUL(position, roomPoint);
		if (localPoint.getX() < 0 || localPoint.getY() < 0 || localPoint.getX() > WIDTH || localPoint.getY() > HEIGHT) {
			parent.removeMouseListener(this);
			parent.removeUnit(this);
		}
		for (Map.Entry<Item, Rectangle> item : items.entrySet()) {
			if (item.getValue().contains(localPoint)) {
				User user = parent.getUser();
				
				switch (item.getKey()) {
				case MACHINEGUN:
					if (user.getMoney() > MachineGunTower.VALUE) {
						parent.placeMachineGunTower(tile);
						user.spendMoney(MachineGunTower.VALUE);
					} else {
						System.out.println("Insufficient funds");
					}
					break;
				case FIRE:
					if (user.getMoney() > FireTower.VALUE) {
						parent.placeFireTower(tile);
						user.spendMoney(FireTower.VALUE);
					} else {
						System.out.println("Insufficient funds");
					}
					break;
				case ROCKET:
					if (user.getMoney() > RocketTower.VALUE) {
						parent.placeRocketTower(tile);
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
		for (Map.Entry<Item, Rectangle> item : items.entrySet()) {
			Image image = null;
			switch (item.getKey()) {
			case MACHINEGUN:
				image = machinegunImage;
				break;
			case FIRE:
				image = flameImage;
				break;
			case ROCKET:
				image = rocketImage;
				break;
			default:
				System.err.println("Can't draw shop menu image");
				return;
			}
			
			int x = (int)(position.getX() + item.getValue().x);
			int y = (int)(position.getY() + item.getValue().y);
			
			g.drawImage(image, x, y, ITEM_WIDTH, ITEM_HEIGHT, null);
		}
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
		// Empty
	}

	public Tag getTag() {
		return Tag.MENU;
	}
}
