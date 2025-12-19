package pij.board;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testgetColumnIndex() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        assertEquals(3, board.getColumnIndex("d7"));
        assertEquals(3, board.getColumnIndex("7d"));
        assertEquals(3, board.getColumnIndex("d14"));
        assertEquals(3, board.getColumnIndex("d14"));

    }

    @Test
    public void testgetRowIndex() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        assertEquals(6, board.getRowIndex("d7"));
        assertEquals(6, board.getRowIndex("7d"));
        assertEquals(13, board.getRowIndex("d14"));
        assertEquals(13, board.getRowIndex("d14"));
    }

    @Test
    public void testgetSquareByPosition() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        assertEquals("-4.", board.getSquareByPosition("m1").getDisplayString());
        assertEquals("-4.", board.getSquareByPosition("1m").getDisplayString());
    }

}

