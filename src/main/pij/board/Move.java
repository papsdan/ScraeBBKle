package pij.board;

import pij.tile.Tile;
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
                board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                currentColIndex++;
            }
        } else{
            for(Tile tile : tiles){
                board.getSquare(currentRowIndex,currentColIndex).setTile(tile);
                currentRowIndex++;
                }
            }

        }


    }



