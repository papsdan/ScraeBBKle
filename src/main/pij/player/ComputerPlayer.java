package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.board.MoveValidator;
import pij.tile.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a computer player.
 * The computer player automatically finds and makes valid moves without manual input.
 */
public class ComputerPlayer extends Player {
    /**
     * Constructs a new ComputerPlayer with specified name.
     * @param playerName the name of the player
     */
    public ComputerPlayer(String playerName) {
        super(playerName);
    }

    /**
     * Makes a move by searching for valid single tile moves on the board.
     * If it is the first move, it tries all 2 tile combinations to meet move validation rules.
     * For non first moves, it tries single tiles at positions adjacent to any existing tiles on the board.
     *
     * @param board the game board
     * @param isFirstMove true if it is the first move of the game
     * @return a valid move or a pass move if no valid move is found
     */
    @Override
    public Move makeMove(Board board,  boolean isFirstMove) throws IOException {
        MoveValidator moveValidator = new MoveValidator();

        if(isFirstMove) {
            return makeFirstMove(board, moveValidator);
        }
        
        List<Tile> rackTiles = this.getTileRack().getTiles();
        for(int row = 0; row < board.getRows(); row++){
            for(int col = 0; col < board.getCols() ; col++) {

                String verticalPosition = getVerticalCoordinateString(row, col);
                String horizontalPosition = getHorizontalCoordinateString(row, col);
                if (board.isSquareOccupied(row, col)) {
                    continue;
                }
                if(!(board.isSquareOccupiedAbove(row, col) || board.isSquareOccupiedBelow(row, col)
                || board.isSquareOccupiedRight(row, col) || board.isSquareOccupiedLeft(row, col))) {
                    continue;
                }

                for (Tile tile : rackTiles) {
                    List<Tile> tilesToPlace = new ArrayList<>();
                    if (tile.isWildcard()) {
                        tilesToPlace.add(tile);
                        for (int i = 0; i < 26; i++) {
                            tile.setWildcardLetter((char) ('a' + i));

                            Move move = tryBothDirectionMoves(board, horizontalPosition, verticalPosition, moveValidator, tilesToPlace,false);
                            if (move != null) {
                                return move;
                            }

                            tile.resetWildcardLetter();

                        }
                    } else {
                        tilesToPlace.add(tile);

                        Move move = tryBothDirectionMoves(board, horizontalPosition, verticalPosition, moveValidator, tilesToPlace,false);
                        if (move != null) {
                            return move;
                        }

                    }

                }
            }
        }
        return new Move();
    }

    /**
     * Tries a move in both horizontal and vertical directions.
     * @param board the game board
     * @param horizontalPosition the horizontal position string
     * @param verticalPosition the vertical position string
     * @param moveValidator the move validator
     * @param tilesToPlace the tiles to place
     * @param isFirstMove true if it is the first move
     * @return a valid move if found, null otherwise
     */
    private Move tryBothDirectionMoves(Board board,String horizontalPosition, String verticalPosition, MoveValidator moveValidator,List<Tile> tilesToPlace, boolean isFirstMove) {
        Move horizontalInput = getMove(board, horizontalPosition, tilesToPlace, moveValidator,isFirstMove);
        if (horizontalInput != null) {
            return horizontalInput;
        }

        Move verticalInput = getMove(board, verticalPosition, tilesToPlace, moveValidator,isFirstMove);
        if (verticalInput != null) {
            return verticalInput;
        }
        return null;
    }
    /**
     * Creates and validates a move at the specified position.
     * @param board the game board
     * @param position the position
     * @param tilesToPlace the tiles to place
     * @param moveValidator the move validator
     * @param isFirstMove true if it is the first move
     * @return the move if valid, null otherwise
     */
    private Move getMove(Board board, String position, List<Tile> tilesToPlace, MoveValidator moveValidator, boolean isFirstMove) {
        Move input = new Move(board, position, tilesToPlace);
        if (moveValidator.validateMove(input, board, isFirstMove, this,false)) {
            return input;
        }
        return null;
    }
    /**
     * Makes the first move of the game by trying 2 tile combinations at the starting square.
     *
     * @param board the game board
     * @param moveValidator the move validator
     * @return a valid move or a pass move if no valid move is found
     */
    private Move makeFirstMove(Board board, MoveValidator moveValidator) {
        int startCol = board.getColumnIndex(board.getStartingPosition());
        int startRow = board.getRowIndex(board.getStartingPosition());

        String verticalPosition = getVerticalCoordinateString(startRow, startCol);
        String horizontalPosition = getHorizontalCoordinateString(startRow, startCol);

        List<Tile> rackTiles = this.getTileRack().getTiles();

        for (int i = 0; i < rackTiles.size(); i++) {
            for (int j = 0; j < rackTiles.size(); j++) {
                if(i == j) {
                    continue;
                }
                List<Tile> tilesToPlace = new ArrayList<>();
                tilesToPlace.add(rackTiles.get(i));
                tilesToPlace.add(rackTiles.get(j));

                Move move = tryBothDirectionMoves(board, horizontalPosition, verticalPosition, moveValidator, tilesToPlace, true);
                if (move != null) {
                    return move;
                }

            }
        }
        return new Move();
    }


    /**
     * Converts row and column indices to a vertical position string
     *
     * @param row the row index
     * @param col the column index
     * @return the vertical position string
     */
    public String getVerticalCoordinateString(int row, int col) {
        char colLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        return "" + colLetter + rowNumber;
    }
    /**
     * Converts row and column indices to a horizontal position string
     *
     * @param row the row index
     * @param col the column index
     * @return the horizontal position string
     */
    public String getHorizontalCoordinateString(int row, int col) {
        char colLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        return "" + rowNumber + colLetter;
    }

}
