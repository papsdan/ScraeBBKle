package pij.board;

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

    public boolean validateMove(Move move, Board board) {

        if (move.getIsPass()) {
            return true;
        }

        boolean isValidWord = isValidWord(move.previewWord());
        boolean isWithinBoard = isWithinBoard(move, board);

        return isValidWord && isWithinBoard;
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

}

