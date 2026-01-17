package pij.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.tile.Tile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringTest {
    private Board board;
    private List<Tile> tiles;
    private Scoring score = new Scoring();



    @BeforeEach
    public void setUp() throws IOException {
        board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        tiles = List.of(
                new Tile('A', 1),
                new Tile('B', 2),
                new Tile('C', 3),
                new Tile('D', 4)
                );
    }

    @Test
    public void testRegularSquareOnlyScore(){
        Move move = new Move(board, "a4", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(10, moveScore);
    }

    @Test
    public void testPremiumLetterSquareScore(){
        Move move = new Move(board, "e1", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(13, moveScore);
    }

    @Test
    public void testOnePremiumWordSquareScore(){
        Move move = new Move(board, "1i", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(20, moveScore);
    }

    @Test
    public void testMultiplePremiumWordSquareScore(){
        Move move = new Move(board, "i1", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(60, moveScore);
    }

    @Test
    public void testCombinationPremiumSquaresScore(){
        Move move = new Move(board, "i2", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(42, moveScore);
    }

    @Test
    public void testExtraScoreTileBonus(){
        tiles = List.of(
                new Tile('A', 1),
                new Tile('B', 2),
                new Tile('C', 3),
                new Tile('D', 4),
                new Tile('E', 5),
                new Tile('F', 6),
                new Tile('G',7)
        );
        Move move = new Move(board, "a4", tiles);
        int moveScore = this.score.totalScore(move, board);
        assertEquals(88, moveScore);

    }
}
