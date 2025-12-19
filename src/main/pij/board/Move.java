package pij.board;

import pij.tile.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Move {
    private Board board;
    private String position;
    private List<Tile> tiles;

    public Move(Board board, String position, List<Tile> tiles) {
        this.board = board;
        this.position = position;
        this.tiles = tiles;
    }
    //if numbers first, then horizontal
    //if letter first, then downward
    //Need to also check for letters already in the squares to ensure it skips occupied squares
    public void placeTile(){

        int currentColIndex = board.getColumnIndex(position);
        int currentRowIndex = board.getRowIndex(position);

        char firstPositionCharacter = this.position.charAt(0);

        if(Character.isDigit(firstPositionCharacter)){
            for(Tile tile : tiles){

                if(!board.getSquare(currentRowIndex,currentColIndex).isSquareOccupied()){
                    board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                    currentColIndex++;
                } else {
                    currentColIndex++;
                    board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                }
            }
        }else{
            for(Tile tile : tiles){

                if(!board.getSquare(currentRowIndex,currentColIndex).isSquareOccupied()){
                    board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                    currentRowIndex++;
                } else {
                    currentRowIndex++;
                    board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                }
            }

        }}
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
        move.placeTile();
        board.displayBoard();

        List<Tile> tiles2 = List.of(
                new Tile('T', 1),
                new Tile('N', 1),
                new Tile('Z', 9),
                new Tile('O', 1),
                new Tile('N', 1)
        );

        Move move2 = new Move(board, "7c", tiles2);
        move2.placeTile();
        board.displayBoard();

        List<Tile> tiles3 = List.of(
                new Tile('O', 1),
                new Tile('V', 4),
                new Tile('E', 2)
        );

        Move move3 = new Move(board, "4e", tiles3);
        move3.placeTile();
        board.displayBoard();

    }

    }



