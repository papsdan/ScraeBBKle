package pij.board;

import pij.player.Player;
import pij.tile.Tile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Validates moves based on game rules:
 */
public class MoveValidator {
    private static HashSet<String> validWords;


    /**
     * Constructs a new MoveValidator and loads the word dictionary. Only done once to avoid word dictionary creation each time
     *
     * @throws IOException if the wordlist file cannot be read
     */
    public MoveValidator() throws IOException {
        if(validWords == null) {
            String content = Files.readString(Path.of("resources/wordlist.txt"));
            String[] words = content.split("\n");
            validWords = new HashSet<>();
            for (String word : words) {
                validWords.add(word.trim().toLowerCase());
            }
        }
    }

    /**
     * Validates a move based on all game rules.
     *
     * @param move the move to validate
     * @param board the game board
     * @param isFirstMove true if this is the first move of the game
     * @param currentPlayer the player making the move
     * @param showValidationErrorMessage true to print error messages, false for no error messages
     * @return true if the move is valid, false if not
     */
    public boolean validateMove(Move move, Board board, boolean isFirstMove, Player currentPlayer, boolean showValidationErrorMessage) {

        if (move.getIsPass()) {
            return true;
        }
        if (!isSingleWord(board, move)) {
            if(showValidationErrorMessage){
                System.out.println("Move must only contain single word");
            }
            return false;
        }

        if (!tilesPlayedInRack(move, currentPlayer)) {
            if(showValidationErrorMessage){
                System.out.println("Move must only include tile from the rack");
            }
            return false;
        }
        if (!isWithinBoard(move, board)) {
            if(showValidationErrorMessage){
                System.out.println("The move goes starts or will go off the board");
            }
            return false;
        }

        if (isFirstMove) {
            if (!atLeastTwoTilesPlayed(move)) {
                if(showValidationErrorMessage){
                    System.out.println("First move must use at least two tiles");
                }
                return false;
            }
            if (!usesStartingSquare(board, move)) {
                if(showValidationErrorMessage){
                    System.out.println("First move must use the starting square");
                }
                return false;
            }
        }

        if (!isValidWord(move.previewWord())) {
            if(showValidationErrorMessage){
                System.out.println("The board does not permit word " + move.previewWord() + " at position " + move.getPosition() + ". Please try again.");
            }
            return false;
        }

        if (!isFirstMove && !connectToExistingTiles(board, move)) {
            if(showValidationErrorMessage){
                System.out.println("Move must connect to another existing tile.");
            }
            return false;
        }

        return true;
    }

    /**
     * Checks if the word in the move exists in the dictionary.
     *
     * @param word the word to check
     * @return true if the word is valid, false if not
     */
    public boolean isValidWord(String word) {
        return validWords.contains(word.toLowerCase());
    }

    /**
     * Checks if the move stays within board boundaries.
     *
     * @param move the move to check
     * @param board the game board
     * @return true if the move is within bounds, false if not
     */
    private boolean isWithinBoard(Move move, Board board) {

        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());

        if (currentColIndex < 0 || currentColIndex >= board.getCols() || currentRowIndex < 0 || currentRowIndex >= board.getRows()) {
            return false;
        }

        int tilesToBePlaced = move.getTiles().size();

        while (tilesToBePlaced > 0) {
            if (currentColIndex >= board.getCols() || currentRowIndex >= board.getRows()) {
                return false;
            }

            if (!board.isSquareOccupied(currentRowIndex, currentColIndex)) {
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


    /**
     * Checks if the move creates only a single word only.
     *
     * @param board the game board
     * @param move the move to check
     * @return true if only one word is formed, false if not
     */
    private boolean isSingleWord(Board board, Move move) {
        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());

        for (int i = 0; i < move.getTiles().size(); i++) {
            while (board.isSquareOccupied(currentRowIndex, currentColIndex)) {
                if (move.isHorizontal()) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
            }
            if (move.isHorizontal()) {
                if (board.isSquareOccupiedAbove(currentRowIndex, currentColIndex) ||
                        board.isSquareOccupiedBelow(currentRowIndex, currentColIndex)) {
                    return false;
                }
                currentColIndex++;
            } else {
                if (board.isSquareOccupiedRight(currentRowIndex, currentColIndex) ||
                        board.isSquareOccupiedLeft(currentRowIndex, currentColIndex)) {
                    return false;
                }
                currentRowIndex++;

            }

        }
        return true;
    }

    /**
     * Checks if at least two tiles are played - used for first move logic in validateMove method.
     *
     * @param move the move to check
     * @return true if two or more tiles are played, false if not
     */
    private boolean atLeastTwoTilesPlayed(Move move) {
        return move.getTiles().size() >= 2;
    }

    /**
     * Checks if the move uses the starting square - used for first move logic in validateMove method.
     *
     * @param board the game board
     * @param move the move to check
     * @return true if the starting square is used, false if not
     */
    private boolean usesStartingSquare(Board board, Move move) {
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

    /**
     * Checks if the move connects to existing tiles on the board.
     *
     * @param board the game board
     * @param move the move to check
     * @return true if the move connects to existing tiles, false if not
     */
    private boolean connectToExistingTiles(Board board, Move move) {
        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());
        int tilesToPlace = move.getTiles().size();
        int tilesPlacedCounter = 0;

        // Check if tile directly before the move position. Horizontal (left) and Vertical (above)
        if (move.isHorizontal()) {
            if (board.isSquareOccupiedLeft(currentRowIndex, currentColIndex)) {
                return true;
            }
        } else {
            if (board.isSquareOccupiedAbove(currentRowIndex, currentColIndex)) {
                return true;
            }
        }

        while (tilesPlacedCounter < tilesToPlace) {
            if (move.isHorizontal()) {
                if(board.isSquareOccupiedAbove(currentRowIndex, currentColIndex) ||
                        board.isSquareOccupiedBelow( currentRowIndex, currentColIndex)) {
                    return true;
                }
            } else {
                if (board.isSquareOccupiedLeft( currentRowIndex, currentColIndex) ||
                        board.isSquareOccupiedRight( currentRowIndex, currentColIndex)) {
                    return true;
                }
            }
            tilesPlacedCounter++;

            if (move.isHorizontal()) {
                currentColIndex++;
            } else {
                currentRowIndex++;
            }
        }
        // Check if tile directly after the move position. Horizontal (right) and Vertical (below)

        return board.isSquareOccupied(currentRowIndex, currentColIndex);

    }

    /**
     * Checks if all tiles in the move are available to play from the players tile rack.
     *
     * @param move the move to check
     * @param player the player making the move
     * @return true if all tiles are in the rack, false if not
     */
    private boolean tilesPlayedInRack(Move move, Player player) {

        List<Tile> tileRackCopy = new ArrayList<>(player.getTileRack().getTiles());
        List<Tile> moveTiles = move.getTiles();

        for (Tile moveTile : moveTiles) {
            char mTile = moveTile.getLetter();
            boolean isLetterFound = false;
            for (int j = 0; j < tileRackCopy.size(); j++) {
                char trTiles = tileRackCopy.get(j).getLetter();
                if (mTile == trTiles) {
                    tileRackCopy.remove(tileRackCopy.get(j));
                    isLetterFound = true;
                    break;
                }
            }
            if (!isLetterFound) {
                return false;
            }
        }
        return true;

    }
}