package pij.tile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileTest {
    @Test
    public void testCreateTile() {
        Tile tile = new Tile('A',2);
        assertEquals('A',tile.getLetter());
        assertEquals(2,tile.getValue());
        assertEquals("[A2]",tile.toString());
    }


}
