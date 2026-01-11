package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.tile.Tile;
import pij.tile.TileBag;
import pij.tile.TileRack;

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

    static void main(String[] args) {
        ComputerPlayer computerPlayer = new ComputerPlayer("ComputerPlayer");
        String name = computerPlayer.getPlayerName();
        System.out.println("Player name: " + name);
        computerPlayer.addScore(2);
        int score = computerPlayer.getScore();
        System.out.println("Score: " + score);
        TileRack tr = computerPlayer.getTileRack();
        int countTiles = tr.getRackCount();

        System.out.println("Tile Rack: " + tr.getTiles());
        System.out.println("Tile Rack Count: " + countTiles);

        tr.fillRack(new TileBag());
        System.out.println("Tile Bag: " + tr.getTiles());
        tr.fillRack(new TileBag());
        countTiles = tr.getRackCount();
        System.out.println("Tile Rack Count: " + countTiles);
    }
}
