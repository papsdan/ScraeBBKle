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

    private List<Tile> tileList;

    /**
     * Constructs a new TileBag with all tiles initialised
     * according to the game specification and shuffled.
     */
    public TileBag() {
        this.tileList = new ArrayList<>();
        initialiseOriginalTiles();
        shuffle();
    }
    /**
     * Initialises the tile bag according to the game specification.
     */
    private void initialiseOriginalTiles() {
        // 8 x A1
        for (int i = 0; i < 8; i++) {
            tileList.add(new Tile('A', 1));
        }

        // 2 x B3
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('B', 3));
        }

        // 2 x C4
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('C', 4));
        }

        // 4 x D2
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile('D', 2));
        }

        // 9 x E2
        for (int i = 0; i < 9; i++) {
            tileList.add(new Tile('E', 2));
        }

        // 3 x F4
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile('F', 4));
        }

        // 4 x G3
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile('G', 3));
        }

        // 3 x H4
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile('H', 4));
        }

        // 9 x I1
        for (int i = 0; i < 9; i++) {
            tileList.add(new Tile('I', 1));
        }

        // 1 x J11
        tileList.add(new Tile('J', 11));

        // 2 x K6
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('K', 6));
        }

        // 4 x L1
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile('L', 1));
        }

        // 2 x M3
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('M', 3));
        }

        // 7 x N1
        for (int i = 0; i < 7; i++) {
            tileList.add(new Tile('N', 1));
        }

        // 7 x O1
        for (int i = 0; i < 7; i++) {
            tileList.add(new Tile('O', 1));
        }

        // 2 x P3
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('P', 3));
        }

        // 1 x Q12
        tileList.add(new Tile('Q', 12));

        // 6 x R1
        for (int i = 0; i < 6; i++) {
            tileList.add(new Tile('R', 1));
        }

        // 4 x S1
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile('S', 1));
        }

        // 5 x T1
        for (int i = 0; i < 5; i++) {
            tileList.add(new Tile('T', 1));
        }

        // 5 x U1
        for (int i = 0; i < 5; i++) {
            tileList.add(new Tile('U', 1));
        }

        // 2 x V4
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('V', 4));
        }

        // 2 x W4
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('W', 4));
        }

        // 1 x X9
        tileList.add(new Tile('X', 9));

        // 2 x Y5
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('Y', 5));
        }

        // 1 x Z9
        tileList.add(new Tile('Z', 9));

        // 2 x _8
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile('_', 8));
        }
    }
    /**
     * Shuffles all tiles in the bag to ensure randomised order when drawing tiles.
     */
    public void shuffle() {
        Collections.shuffle(tileList);
    }
    /**
     * Returns a string representation of the current tiles in the bag.
     *
     * @return a string showing all tiles currently in the bag
     */
    public String currentTilebag(){
        return tileList.toString();
    }
    /**
     * Checks if the tile bag is empty.
     *
     * @return true if there are no tiles left, false otherwise
     */
    public boolean isEmpty() {
        return tileList.isEmpty();
    }

    /**
     * Returns the number of tiles remaining in the bag.
     *
     * @return the count of tiles currently in the bag
     */
    public int getRemainingTileCount() {
        return tileList.size();
    }

    /**
     * Draws a specified number of tiles from the bag.
     *
     * @param count the number of tiles to draw
     * @return a list of drawn tiles
     */
    public List<Tile> drawTiles(int count){
        List<Tile> drawnTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drawnTiles.add(tileList.remove(tileList.size()-1));
        }
       return drawnTiles;
    }

}
