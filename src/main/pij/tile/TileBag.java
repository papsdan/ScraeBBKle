package pij.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bag of tiles for the game.
 * The tile bag contains all tiles according to the game specification
 * and provides functionality to draw tiles randomly.
 */
public class TileBag {

    private List<Tile> tiles;

    /**
     * Constructs a new TileBag with all tiles initialised
     * according to the game specification and shuffled.
     */
    public TileBag() {
        this.tiles = new ArrayList<>();
        initialiseOriginalTiles();
        shuffle();
    }

    /**
     * Initialises the tile bag according to the game specification.
     */
    private void initialiseOriginalTiles() {
        Object[][] tileTypes =
                {
                        {'A', 1, 8},
                        {'B', 3, 2},
                        {'C', 4, 2},
                        {'D', 2, 4},
                        {'E', 2, 9},
                        {'F', 4, 3},
                        {'G', 3, 4},
                        {'H', 4, 3},
                        {'I', 1, 9},
                        {'J', 11, 1},
                        {'K', 6, 2},
                        {'L', 1, 4},
                        {'M', 3, 2},
                        {'N', 1, 7},
                        {'O', 1, 7},
                        {'P', 3, 2},
                        {'Q', 12, 1},
                        {'R', 1, 6},
                        {'S', 1, 4},
                        {'T', 1, 5},
                        {'U', 1, 5},
                        {'V', 4, 2},
                        {'W', 4, 2},
                        {'X', 9, 1},
                        {'Y', 5, 2},
                        {'Z', 9, 1},
                        {'_', 8, 2}
                };

        for (Object[] tile : tileTypes) {
            char letter = (char) tile[0];
            int value = (int) tile[1];
            int count = (int) tile[2];
            for (int j = 0; j < count; j++) {
                tiles.add(new Tile(letter, value));
            }
        }
    }

    /**
     * Shuffles all tiles in the bag to ensure randomised order when drawing tiles.
     */
    public void shuffle() {
        Collections.shuffle(tiles);
    }

    /**
     * Returns a string representation of the current tiles in the bag.
     *
     * @return a string showing all tiles currently in the bag
     */
    public String currentTilebag() {
        return tiles.toString();
    }

    /**
     * Checks if the tile bag is empty.
     *
     * @return true if there are no tiles left, false otherwise
     */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Returns the number of tiles remaining in the bag.
     *
     * @return the count of tiles currently in the bag
     */
    public int getRemainingTileCount() {
        return tiles.size();
    }
    /**
     * Draws a single tile from the bag.
     * The tile is removed from the bag and returned to the caller.
     *
     * @return the tile that was drawn from the bag
     */
    public Tile drawTile(){
            return tiles.removeLast();
    }
    /**
     * Draws a specified number of tiles from the bag.
     *
     * @param count the number of tiles to draw
     * @return a list of drawn tiles
     */
    public List<Tile> drawTiles(int count) {
        List<Tile> drawnTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drawnTiles.add(drawTile());
        }
        return drawnTiles;
    }

}
