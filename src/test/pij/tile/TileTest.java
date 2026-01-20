package pij.tile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    @Test
    public void testTileConstructor() {
        Tile tile = new Tile('A',1);
        assertEquals('A',tile.getLetter());
        assertEquals(1,tile.getValue());
        assertFalse(tile.isWildcard());
    }
    @Test
    public void testTileConstructorWildcard() {
        Tile tile = new Tile('_',8);
        assertEquals('_',tile.getLetter());
        assertEquals(8,tile.getValue());
        assertTrue(tile.isWildcard());
    }
    @Test
    public void testSetWildcardLetter() {
        Tile wildcardTile = new Tile('_',8);
        assertEquals('_',wildcardTile.getLetter());
        assertTrue(wildcardTile.isWildcard());
        wildcardTile.setWildcardLetter('a');
        assertEquals('a',wildcardTile.getLetter());
        assertTrue(wildcardTile.isWildcard());


    }
    @Test
    public void testToString() {
        Tile tile  = new Tile('R',4);
        assertEquals("[R4]", tile.toString());

    }
    @Test
    public void testToStringWildcard() {
        Tile wildcardTile = new Tile('_',8);
        assertEquals("[_8]", wildcardTile.toString());
        wildcardTile.setWildcardLetter('a');
        assertEquals("[a8]", wildcardTile.toString());


    }

    @Test
    public void testWildcardReset() {
        Tile wildcardTile = new Tile('_',8);
        wildcardTile.setWildcardLetter('a');
        assertEquals('a',wildcardTile.getLetter());
        wildcardTile.resetWildcardLetter();
        wildcardTile.setWildcardLetter('b');
        assertEquals('b',wildcardTile.getLetter());
    }
}
