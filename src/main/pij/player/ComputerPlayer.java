package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.board.MoveValidator;
import pij.tile.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String playerName) {
        super(playerName);
    }

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

    private Move getMove(Board board, String position, List<Tile> tilesToPlace, MoveValidator moveValidator, boolean isFirstMove) {
        Move input = new Move(board, position, tilesToPlace);
        if (moveValidator.validateMove(input, board, isFirstMove, this,false)) {
            return input;
        }
        return null;
    }

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


    public String getVerticalCoordinateString(int row, int col) {
        char colLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        return "" + colLetter + rowNumber;
    }

    public String getHorizontalCoordinateString(int row, int col) {
        char colLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        return "" + rowNumber + colLetter;
    }

}
