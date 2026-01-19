package pij.main;

import pij.game.Game;

import java.io.IOException;
/**
 * Starts the ScraeBBKle game.
 */
public class Main {
    static void main(String[] args) throws IOException {
        Game game = new Game();
        game.play();
    }
}
