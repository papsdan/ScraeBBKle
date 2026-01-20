package pij.player;

import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.board.MoveValidator;
import pij.square.Square;
import pij.tile.Tile;
import pij.tile.TileBag;
import pij.tile.TileRack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String playerName) {
        super(playerName);
    }
//    @Override
//    public Move makeMove(Board board) {
//            return new Move();
//    }

    @Override
    public Move makeMove(Board board) throws IOException {
        MoveValidator moveValidator = new MoveValidator();
        for(int row = 0; row < board.getRows(); row++){
            for(int col = 0; col < board.getCols() ; col++) {

                String verticlePosition = getVerticleCoordinateString(row, col);
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
                            Move verticalInput = new Move(board, verticlePosition, tilesToPlace);
                            if (moveValidator.validateMove(verticalInput, board, false, this)) {
                                System.out.println("trying " + tile.getLetter() + " at position "+ verticlePosition);
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
                        Move verticalInput = new Move(board, verticlePosition, tilesToPlace);
                        if (moveValidator.validateMove(verticalInput, board, false, this)) {
                            System.out.println("trying " + tile.getLetter() + " at position "+ verticlePosition);
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


    static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        ComputerPlayer computerPlayer = new ComputerPlayer("Compu)terPlayer");
        System.out.println(computerPlayer.getHorizontalCoordinateString(0, 0));
        System.out.println(board.getSquareByPosition("a1").getDisplayString());
        System.out.println(board.getSquare(0,0).getDisplayString());
        System.out.println(board.getSquareByPosition("1a"));

    }
}
