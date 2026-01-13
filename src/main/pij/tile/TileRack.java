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
        int tilesRequired = MAXTILES - getRackCount();
        if (tilesRequired > 0) {
            tiles.addAll(tileBag.drawTiles(tilesRequired));
        }

    }

    //accoutn for lowercase letterts and check if wildcard '_' in tile rack
    public boolean hasTiles(String word) {
        List<Tile> tilesAvailable = new ArrayList<>(this.tiles);
        for (int i = 0; i < word.length(); i++) {
            char letterToCheck = word.charAt(i);
            boolean letterFound = false;

            for(Tile tile : tilesAvailable) {
                if (tile.getLetter() == letterToCheck) {
                    tilesAvailable.remove(tile);
                    letterFound = true;
                    break;
                }
            }
            if (!letterFound) {
                return false;
            }
    }
        return true;
    }

    public void removeTiles(List<Tile> tilesToRemove) {
        for (Tile tile : tilesToRemove) {
            this.tiles.remove(tile);
        }
    }

    public static void main(String[] args) {
        TileRack rack = new TileRack();

        // Manually add some tiles (since we're not using TileBag for testing)
        rack.tiles.add(new Tile('D', 2));
        rack.tiles.add(new Tile('I', 1));
        rack.tiles.add(new Tile('N', 1));
        rack.tiles.add(new Tile('E', 2));
        rack.tiles.add(new Tile('D', 2));
        rack.tiles.add(new Tile('L', 1));
        rack.tiles.add(new Tile('E', 2));

        // Test
        System.out.println("Rack: " + rack.tiles);
        System.out.println("Has DINED? " + rack.hasTiles("DINE4D"));
        System.out.println("Has DINNER? " + rack.hasTiles("DINEL"));
        System.out.println("Has LED? " + rack.hasTiles("LED"));
        System.out.println(new Tile('D', 2).getLetter());
    }

}
