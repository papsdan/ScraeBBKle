package pij.board;

import pij.game.Game;
import pij.player.HumanPlayer;
import pij.player.Player;
import pij.tile.Tile;
import pij.tile.TileRack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

public class MoveValidator {
    private final HashSet<String> validWords;

    public MoveValidator() throws IOException {
        String content = Files.readString(Path.of("resources/wordlist.txt"));
        String[] words = content.split("\n");
        this.validWords = new HashSet<>();
        for (String word : words) {
            this.validWords.add(word.trim().toLowerCase());
        }
    }

    public boolean validateMove(Move move, Board board, boolean isFirstMove) {

        if (move.getIsPass()) {
            return true;
        }

        if (!isWithinBoard(move, board)) {
            System.out.println("The move goes starts or will go off the board");
            return false;
        }

        if (isFirstMove) {
            if (!atLeastTwoTilesPlayed(move)) {
                System.out.println("First move must use at least two tiles");
                return false;
            }
            if (!usesStartingSquare(board, move)) {
                System.out.println("First move must use the starting square");
                return false;
            }
        }

        if (!isValidWord(move.previewWord())) {
            System.out.println("The board does not permit word " + move.previewWord() + " at position " + move.getPosition() + ". Please try again.");
            return false;
        }

        return true;
    }

    public boolean isValidWord(String word) {
        return this.validWords.contains(word.toLowerCase());
    }

    public boolean isWithinBoard(Move move, Board board) {

        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());

        if(currentColIndex < 0 || currentColIndex >= board.getCols() || currentRowIndex < 0 || currentRowIndex >= board.getRows()) {
            return false;
        }

        int tilesToBePlaced = move.getTiles().size();

        while(tilesToBePlaced > 0) {
            if(currentColIndex >= board.getCols() || currentRowIndex >= board.getRows()) {
                return false;
            }

            if(!board.getSquare(currentRowIndex, currentColIndex ).isSquareOccupied()){
                tilesToBePlaced--;
            }


            if (move.isHorizontal()) {
                currentColIndex++;
            } else {
                currentRowIndex++;
            }
        }

    return true;
    }

    public boolean atLeastTwoTilesPlayed(Move move) {
        return move.getTiles().size() >= 2;
    }
    public boolean usesStartingSquare(Board board, Move move) {
        int moveColIndex = board.getColumnIndex(move.getPosition());
        int moveRowIndex = board.getRowIndex(move.getPosition());
        int startColIndex = board.getColumnIndex(board.getStartingPosition());
        int startRowIndex = board.getRowIndex(board.getStartingPosition());

        int tilesToBePlaced = move.getTiles().size();

        for (int i = 0; i < tilesToBePlaced; i++) {
            if (moveColIndex == startColIndex && moveRowIndex == startRowIndex) {
                return true;
            }

            if (move.isHorizontal()) {
                moveColIndex++;
            } else {
                moveRowIndex++;
            }
        }
        return false;
    }

    static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        List<Tile> tiles = List.of(
                new Tile('D', 2),
                new Tile('I', 1),
                new Tile('N', 1),
                new Tile('E', 2),
                new Tile('D', 2)
        );

        Move move = new Move(board, "d4", tiles);
        MoveValidator moveValidator = new MoveValidator();
        System.out.println(moveValidator.usesStartingSquare(board, move));
    }

}

