package pij.board;


import org.junit.jupiter.api.Test;
import pij.player.HumanPlayer;
import pij.player.Player;
import pij.tile.Tile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MoveValidatorTest {


    @Test
    public void validMoveLowerCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("dined"));
    }
    @Test
    public void validMoveUpperCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("DINED"));
    }
    @Test
    public void validMoveMixedCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("diNed"));
    }
    @Test
    public void validMoveInvalidWordTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertFalse(move.isValidWord("dined12"));
    }

    @Test
    public void testUsesStartingSquare() throws IOException {
        MoveValidator validator = new MoveValidator();
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        Player player = new HumanPlayer("Player1");
        player.getTileRack().getTiles().add(new Tile('H', 4));
        player.getTileRack().getTiles().add(new Tile('I', 1));
        List<Tile> moveTiles = List.of(new Tile('H', 4), new Tile('I', 1));

        Move move = new Move(board, "a1", moveTiles);
        boolean isMoveValid =  validator.validateMove(move, board, true, player, false);
        assertFalse(isMoveValid);

        Move move2 = new Move(board, "d7", moveTiles);
        boolean isMove2Valid =  validator.validateMove(move2, board, true, player, false);
        assertTrue(isMove2Valid);
    }

    @Test
    public void testConnectToExistingTiles() throws IOException {
        MoveValidator validator = new MoveValidator();
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.getSquareByPosition("d7").setTile(new Tile('H', 4));
        board.getSquareByPosition("d8").setTile(new Tile('I', 1));
        Player player = new HumanPlayer("Player1");
        player.getTileRack().getTiles().add(new Tile('A', 1));
        List<Tile> moveTiles = List.of(new Tile('A', 1));

        Move move = new Move(board, "a1", moveTiles);
        boolean isMoveValid =  validator.validateMove(move, board, false, player, false);
        assertFalse(isMoveValid);

        Move move2 = new Move(board, "7e", moveTiles);
        boolean isMove2Valid =  validator.validateMove(move2, board, false, player, false);
        assertTrue(isMove2Valid);
    }

    @Test
    public void testTilesPlayedInRack() throws IOException {
        MoveValidator validator = new MoveValidator();
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        Player player = new HumanPlayer("Player1");
        player.getTileRack().getTiles().clear();
        List<Tile> moveTiles = List.of(new Tile('A', 1));

        Move move = new Move(board, "d7", moveTiles);
        boolean isMoveValid =  validator.validateMove(move, board, false, player, false);
        assertFalse(isMoveValid);

    }
}
