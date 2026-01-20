package pij.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.tile.Tile;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    private Board board;

    @BeforeEach
    void setUp() throws IOException {
        this.board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
    }

    @Test
    public void testVerticalMove() throws IOException {
        List<Tile> tiles = List.of(
                new Tile('D', 2),
                new Tile('I', 1),
                new Tile('N', 1),
                new Tile('E', 2),
                new Tile('D', 2)
        );

        Move move = new Move(board, "d4", tiles);
        move.placeTile();

        assertTrue(board.getSquareByPosition("d4").isSquareOccupied());
        assertTrue(board.getSquareByPosition("d5").isSquareOccupied());
        assertTrue(board.getSquareByPosition("d6").isSquareOccupied());
        assertTrue(board.getSquareByPosition("d7").isSquareOccupied());
        assertTrue(board.getSquareByPosition("d8").isSquareOccupied());

        assertEquals('D', board.getSquareByPosition("d4").getTile().getLetter());
        assertEquals('I', board.getSquareByPosition("d5").getTile().getLetter());
        assertEquals('N', board.getSquareByPosition("d6").getTile().getLetter());
        assertEquals('E', board.getSquareByPosition("d7").getTile().getLetter());
        assertEquals('D', board.getSquareByPosition("d8").getTile().getLetter());

    }

    @Test
    public void testHorizontalMove() throws IOException {

        List<Tile> tiles = List.of(
                new Tile('T', 1),
                new Tile('E', 2),
                new Tile('N', 1)
        );

        Move move = new Move(board, "7c", tiles);
        move.placeTile();

        assertTrue(board.getSquareByPosition("7c").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7d").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7e").isSquareOccupied());

        assertEquals('T', board.getSquareByPosition("7c").getTile().getLetter());
        assertEquals('E', board.getSquareByPosition("7d").getTile().getLetter());
        assertEquals('N', board.getSquareByPosition("7e").getTile().getLetter());

    }

    @Test
    public void testSkippingOccupiedSquare() throws IOException {

        List<Tile> tiles = List.of(
                new Tile('D', 2),
                new Tile('I', 1),
                new Tile('N', 1),
                new Tile('E', 2),
                new Tile('D', 2)
        );

        Move move = new Move(board, "d4", tiles);
        move.placeTile();

        List<Tile> tiles2 = List.of(
                new Tile('T', 1),
                new Tile('N', 1),
                new Tile('Z', 9),
                new Tile('O', 1),
                new Tile('N', 1)
        );

        Move move2 = new Move(board, "7c", tiles2);

        assertFalse(board.getSquareByPosition("7c").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7d").isSquareOccupied());
        assertFalse(board.getSquareByPosition("7e").isSquareOccupied());
        assertFalse(board.getSquareByPosition("7f").isSquareOccupied());
        assertFalse(board.getSquareByPosition("7g").isSquareOccupied());
        assertFalse(board.getSquareByPosition("7h").isSquareOccupied());

        move2.placeTile();
        
        assertTrue(board.getSquareByPosition("7c").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7d").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7e").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7f").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7g").isSquareOccupied());
        assertTrue(board.getSquareByPosition("7h").isSquareOccupied());

        assertEquals('T', board.getSquareByPosition("7c").getTile().getLetter());
        assertEquals('E', board.getSquareByPosition("7d").getTile().getLetter());
        assertEquals('N', board.getSquareByPosition("7e").getTile().getLetter());
        assertEquals('Z', board.getSquareByPosition("7f").getTile().getLetter());
        assertEquals('O', board.getSquareByPosition("7g").getTile().getLetter());
        assertEquals('N', board.getSquareByPosition("7h").getTile().getLetter());


    }

    @Test
    public void testPreviewWordAddingToFront() {

        board.getSquareByPosition("8g").setTile(new Tile('A', 1));
        board.getSquareByPosition("8h").setTile(new Tile('T', 1));
        List<Tile> tilesToPlace = List.of(new Tile('C', 1),new Tile('H', 1));
        Move move = new Move(board, "8e", tilesToPlace);
        assertEquals("CHAT", move.previewWord());
    }

    @Test
    public void testPreviewWordAddingToFrontAndBack() {
        board.getSquareByPosition("8g").setTile(new Tile('N', 1));
        board.getSquareByPosition("8h").setTile(new Tile('O', 1));
        List<Tile> tilesToPlace = List.of(new Tile('S', 1),new Tile('W', 4));
        Move move = new Move(board, "8f", tilesToPlace);
        assertEquals("SNOW", move.previewWord());
    }

    @Test
    public void testPreviewWordAddingToBack() {
        board.getSquareByPosition("8g").setTile(new Tile('H', 4));
        board.getSquareByPosition("8h").setTile(new Tile('E', 1));
        board.getSquareByPosition("8i").setTile(new Tile('L', 1));
        board.getSquareByPosition("8j").setTile(new Tile('L', 1));
        List<Tile> tilesToPlace = List.of(new Tile('O', 1));
        Move move = new Move(board, "8k", tilesToPlace);
        assertEquals("HELLO", move.previewWord());
    }

}