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

    public TileRack() {
        this.tiles = new ArrayList<Tile>();
    }

    public int getRackCount() {
        return this.tiles.size();
    }
    public List<Tile> getTiles() {
        return this.tiles;
    }
    public void fillRack(TileBag tileBag) {
        int tilesRequired = 7 - getRackCount();
        if (tilesRequired > 0) {
            tiles.addAll(tileBag.drawTiles(tilesRequired));
        }

    }




}
