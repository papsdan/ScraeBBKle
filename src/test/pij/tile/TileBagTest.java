package pij.tile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class TileBagTest {
        @Test
        public void testTileBagConstructor() {
            TileBag tileBag = new TileBag();
            assertEquals(100,tileBag.getRemainingTileCount());
            assertFalse(tileBag.isEmpty());
        }
        @Test
        public void testTileBagDraw() {
            TileBag tileBag = new TileBag();
            assertEquals(100,tileBag.getRemainingTileCount());
            tileBag.drawTile();
            assertEquals(99,tileBag.getRemainingTileCount());
            tileBag.drawTiles(9);
            assertEquals(90,tileBag.getRemainingTileCount());
            tileBag.drawTiles(10);
            assertEquals(80,tileBag.getRemainingTileCount());
        }
        @Test
        public void testTileBagEmpty() {
            TileBag tileBag = new TileBag();
            assertFalse(tileBag.isEmpty());
            tileBag.drawTiles(100);
            assertTrue(tileBag.isEmpty());
            tileBag.drawTiles(2);
            assertTrue(tileBag.isEmpty());
        }
}
