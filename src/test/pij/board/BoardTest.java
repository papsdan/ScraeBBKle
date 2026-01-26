package pij.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() throws IOException {
        board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
    }

    @Test
    public void testgetColumnIndex() throws IOException {
        assertEquals(3, board.getColumnIndex("d7"));
        assertEquals(3, board.getColumnIndex("7d"));
        assertEquals(3, board.getColumnIndex("d14"));
        assertEquals(3, board.getColumnIndex("d14"));

    }

    @Test
    public void testgetRowIndex() throws IOException {
        assertEquals(6, board.getRowIndex("d7"));
        assertEquals(6, board.getRowIndex("7d"));
        assertEquals(13, board.getRowIndex("d14"));
        assertEquals(13, board.getRowIndex("d14"));
    }

    @Test
    public void testgetSquareByPosition() throws IOException {
        assertEquals("-4.", board.getSquareByPosition("m1").getDisplayString());
        assertEquals("-4.", board.getSquareByPosition("1m").getDisplayString());
    }

    @Test
    public void testInvalidBoardLoading() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> BoardLoader.loadFromFile("resources/invalidBoard.txt"));
    }

    @Test
    public void testValidBoardLoading() throws IOException {
        assertDoesNotThrow(() -> BoardLoader.loadFromFile("resources/defaultBoard.txt"));
    }

    @Test
    public void testBoardNotFound() throws IOException {
        assertThrows(IOException.class, () -> BoardLoader.loadFromFile("resources/nonExistentBoard.txt"));
    }


}

