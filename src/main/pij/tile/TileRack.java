package pij.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Represents a player's tile rack in the game.
 * A rack can hold up to 7 tiles at a time and provides
 * functionality to manage tiles during gameplay.
 */
public class TileRack {
    private List<Tile> tiles;
    private final int MAXTILES = 7;

    /**
     * Constructs a new empty TileRack.
     */
    public TileRack() {
        this.tiles = new ArrayList<Tile>();
    }

    /**
     * Returns the number of tiles currently on the rack.
     *
     * @return count of tiles on the rack
     */
    public int getRackCount() {
        return this.tiles.size();
    }

    /**
     * Returns the list of tiles on the rack.
     *
     * @return list of tiles
     */
    public List<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * Fills the rack with tiles from the tile bag. Can only fill the rack up to maximum of 7.
     *
     * @param tileBag the tile bag to draw tiles from
     */
    public void fillRack(TileBag tileBag) {
        int tilesRequired = MAXTILES - getRackCount();
        if (tilesRequired > 0) {
            tiles.addAll(tileBag.drawTiles(tilesRequired));
        }

    }
    /**
     * Removes the specified tiles from the rack.
     *
     * @param tilesToRemove the tiles to remove
     */
    public void removeTiles(List<Tile> tilesToRemove) {
        for (Tile tile : tilesToRemove) {
            this.tiles.remove(tile);
        }
    }
    
}
