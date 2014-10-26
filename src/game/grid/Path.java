package game.grid;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Path {
	private ArrayList<Tile> tiles;
	private Tile highestTile;
	private Tile lowestTile;
	
	public Path(){
		tiles = new ArrayList<Tile>();
		highestTile = null;
		lowestTile = null;
	}
	
	public void addTile(Tile tile) {
		tile.setColor(Color.GRAY);
		tiles.add(tile);
		
		if (highestTile == null || highestTile.getCenter().getY() > tile.getCenter().getY()) {
			highestTile = tile;
		}
		
		if (lowestTile == null || lowestTile.getCenter().getY() < tile.getCenter().getY()) {
			lowestTile = tile;
		}
	}
	
	public Tile getFirstTile() {
		if (tiles.size() > 0) {
			return tiles.get(0);
		}
		else {
			return null;
		}
	}
	
	public Tile findNextTile(Tile prev) {
		int indexOfPrev = tiles.indexOf(prev);
		int indexOfNext = -1;
		
		if(prev == null) {
			return getFirstTile();
		}
		
		if (indexOfPrev == -1) {
			return null;
		}
		
		indexOfNext = indexOfPrev + 1;
		
		if (indexOfNext < tiles.size())
		{
			return tiles.get(indexOfNext);
		}
		else
		{
			return null;
		}
	}
	
	public boolean doesTileExist(Tile lookingFor) {
		for(Tile tile : tiles) {
			if(tile == lookingFor) {
				return true;
			}
		}
		
		return false;
	}
	
	public Point2D getDefaultStartLocation() {
		Tile firstTile = getFirstTile();
		if (firstTile != null) {
			Point2D firstTileCenter = firstTile.getCenter();
			return new Point2D.Double(firstTileCenter.getX() - Tile.WIDTH, firstTileCenter.getY());
		}
		else {
			return new Point2D.Double();
		}
	}
	
	public Tile getHighestTile() {
		return highestTile;
	}
	
	public Tile getLowestTile() {
		return lowestTile;
	}
}
