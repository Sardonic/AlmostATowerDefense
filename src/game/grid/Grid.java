package game.grid;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Grid {
	ArrayList<ArrayList<Tile>> grid;
	public static final int Y_OFFSET = 25;
	public static final int X_OFFSET = 2;
	int width;
	int height;
	
	public Grid(int width, int height) {
		grid = new ArrayList<ArrayList<Tile>>();
		this.width = width;
		this.height = height;
		for(int i = 0; i < width; i++)
		{
			grid.add(new ArrayList<Tile>());
			for(int j = 0; j < height; j++)
			{
				grid.get(i).add(new Tile(new Point2D.Double(i * Tile.WIDTH + X_OFFSET, j * Tile.HEIGHT + Y_OFFSET)));
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		return grid.get(x).get(y);
	}
	
	public void draw(Graphics g) {
		for (ArrayList<Tile> row : grid) {
			for (Tile tile : row) {
				tile.draw(g);
			}
		}
	}
}
