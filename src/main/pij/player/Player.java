package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.game.Game;
import pij.tile.TileRack;

import java.io.IOException;

/**
 * Abstract class representing a player.
 * Players have a name, score, a tile rack, and can make moves.
 */
public abstract class Player {
    private int score;
    private TileRack tileRack;
    private final String playerName;
    /**
     * Constructs a new Player with the specified name - for the game it will be Player 1 and Player 2.
     * Initialises score to 0 and creates an empty tile rack for the player.
     *
     * @param playerName the name of the player
     */
    public Player(String playerName) {
        this.score = 0;
        this.tileRack =  new TileRack();
        this.playerName = playerName;
    }
    /**
     * Returns the player's current score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }
    /**
     * Returns the player's tile rack.
     *
     * @return the tile rack
     */
    public TileRack getTileRack() {
        return this.tileRack;
    }
    /**
     * Returns the player's name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return this.playerName;
    }
    /**
     * Adds points to the player's score.
     *
     * @param points the points to add (this can be negative)
     */
    public void addScore(int points) {
        this.score += points;
    }
    /**
     * Abstract method to make a move on the given board.
     * Implementation differs for human and computer players.
     *
     * @param board the game board
     * @return the move to make
     */
    public abstract Move makeMove(Board board, boolean isFirstMove) throws IOException;

}
