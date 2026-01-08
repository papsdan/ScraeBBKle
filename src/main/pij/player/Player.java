package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.tile.TileRack;

public abstract class Player {
    private int score;
    private TileRack tileRack;
    private final String playerName;

    public Player(String playerName) {
        this.score = 0;
        this.tileRack =  new TileRack();
        this.playerName = playerName;
    }

    public int getScore() {
        return this.score;
    }
    public TileRack getTileRack() {
        return this.tileRack;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public abstract Move makeMove(Board board);

}
