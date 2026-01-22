package pij.player;

import pij.board.Board;
import pij.board.BoardLoader;
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
//    @Override
//    public Move makeMove(Board board) {
//            return new Move();
//    }

    @Override
    public Move makeMove(Board board,  boolean isFirstMove) throws IOException {
        MoveValidator moveValidator = new MoveValidator();

        if(isFirstMove) {
            int startCol = board.getColumnIndex(board.getStartingPosition());
            int startRow = board.getRowIndex(board.getStartingPosition());

            String verticalPosition = getVerticleCoordinateString(startRow, startCol);
            String horizontalPosition = getHorizontalCoordinateString(startRow, startCol);

            List<Tile> rackTiles = this.getTileRack().getTiles();

            for (int i = 0; i < rackTiles.size(); i++) {
                for (int j = 0; j < rackTiles.size(); j++) {
                    List<Tile> tilesToPlace = new ArrayList<>();
                    tilesToPlace.add(rackTiles.get(i));
                    tilesToPlace.add(rackTiles.get(j));

                    Move horizontalInput = new Move(board, horizontalPosition, tilesToPlace);

                    if (moveValidator.validateMove(horizontalInput, board, true, this)) {
                        return horizontalInput;
                    }

                    Move verticalInput = new Move(board, verticalPosition, tilesToPlace);
                    if (moveValidator.validateMove(verticalInput, board, true, this)) {
                        return verticalInput;
                    }
                }
            }
            return new Move();
        }

        for(int row = 0; row < board.getRows(); row++){
            for(int col = 0; col < board.getCols() ; col++) {

                String verticalPosition = getVerticleCoordinateString(row, col);
                String horizontalPosition = getHorizontalCoordinateString(row, col);
                if (board.isSquareOccupied(row, col)) {
                    continue;
                }
                if(!(board.isSquareOccupiedAbove(row, col) || board.isSquareOccupiedBelow(row, col)
                || board.isSquareOccupiedRight(row, col) || board.isSquareOccupiedLeft(row, col))) {
                    continue;
                }
                List<Tile> rackTiles = this.getTileRack().getTiles();

                for (Tile tile : rackTiles) {
                    List<Tile> tilesToPlace = new ArrayList<>();
                    if (tile.isWildcard()) {
                        tilesToPlace.add(tile);
                        for (int i = 0; i < 26; i++) {
                            System.out.println((char) ('a' + i));
                            tile.setWildcardLetter((char) ('a' + i));
                            Move horizontalInput = new Move(board, horizontalPosition, tilesToPlace);
                            if (moveValidator.validateMove(horizontalInput, board, false, this)) {
                                System.out.println("trying " + tile.getLetter() + " at position "+ horizontalPosition);
                                return horizontalInput;
                            }
                            Move verticalInput = new Move(board, verticalPosition, tilesToPlace);
                            if (moveValidator.validateMove(verticalInput, board, false, this)) {
                                System.out.println("trying " + tile.getLetter() + " at position "+ verticalPosition);
                                return verticalInput;
                            }
                            tile.resetWildcardLetter();

                        }
                    } else {
                        tilesToPlace.add(tile);
                        Move horizontalInput = new Move(board, horizontalPosition, tilesToPlace);
                        if (moveValidator.validateMove(horizontalInput, board, false, this)) {
                            System.out.println("trying " + tile.getLetter() + " at position "+ horizontalPosition);
                            return horizontalInput;
                        }
                        Move verticalInput = new Move(board, verticalPosition, tilesToPlace);
                        if (moveValidator.validateMove(verticalInput, board, false, this)) {
                            System.out.println("trying " + tile.getLetter() + " at position "+ verticalPosition);
                            return verticalInput;
                        }
                    }

                }
            }
        }
        return new Move();
    }

    public String getVerticleCoordinateString(int row, int col) {
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
