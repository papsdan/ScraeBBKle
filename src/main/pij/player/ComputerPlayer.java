package pij.player;

import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
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
    @Override
    public Move makeMove(Board board) {
            return new Move();
    }

    public Move makeMoveEnhanced(Board board) {
        int numberOfTiles = this.getTileRack().getRackCount();

        for(int row = 0; row < board.getRows(); row++){
            for(int col = 0; col < board.getCols() ; col++){
                if(!board.getSquare(row,col).isSquareOccupied()){
                    System.out.println("Square not occupied");
                }
            }
        }
        return new Move();
    }

    static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        ComputerPlayer computerPlayer = new ComputerPlayer("ComputerPlayer");
        TileBag bag = new TileBag();
        computerPlayer.getTileRack().fillRack(bag);
        computerPlayer.makeMoveEnhanced(board);
    }
}
