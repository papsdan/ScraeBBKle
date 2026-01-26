package pij.player;

import org.junit.jupiter.api.Test;
import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.tile.Tile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ComputerPlayerTest {

    @Test
    public void testComputerMakesValidMove() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.getSquareByPosition("d7").setTile(new Tile('A', 1));
        Player computerPlayer = new ComputerPlayer("Player1");
        computerPlayer.getTileRack().getTiles().add(new Tile('A', 1));
        Move move = computerPlayer.makeMove(board, false);
        assertFalse(move.getIsPass());
        assertTrue(move.previewWord().equals("AA"));
    }

    @Test
    public void testComputerMakesValidWilcardMove() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.getSquareByPosition("d7").setTile(new Tile('A', 1));
        Player computerPlayer = new ComputerPlayer("Player1");
        computerPlayer.getTileRack().getTiles().add(new Tile('_', 8));
        Move move = computerPlayer.makeMove(board, false);
        assertFalse(move.getIsPass());
        assertNotNull(move);
    }

    @Test
    public void testComputerMakesPassMove() throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.getSquareByPosition("d7").setTile(new Tile('A', 1));
        Player computerPlayer = new ComputerPlayer("Player1");
        computerPlayer.getTileRack().getTiles().add(new Tile('C', 1));
        Move move = computerPlayer.makeMove(board, false);
        assertTrue(move.getIsPass());
    }
}
