package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String playerName) {
        super(playerName);
    }
    @Override
    public Move makeMove(Board board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)\n" +
                "For example, for suitable tile rack and board configuration, a downward move\n" +
                "could be \"HI,f4\" and a rightward move could be \"HI,4f\".\n" +
                "In the word, upper-case letters are standard tiles and lower-case letters\n" +
                "are wildcards.\n" +
                "Entering \",\" passes the turn.");

        String input = sc.nextLine();

        if (!input.equals(",")) {

            String[] moveInput = input.split(",");
            List<Tile> tiles = this.getTileRack().getTiles();
            List<Tile> placedTiles = new ArrayList<>();

            for (int i = 0; i < moveInput[0].length(); i++) {
                for (int j = 0; j < getTileRack().getRackCount(); j++) {
                    if (moveInput[0].charAt(i) == tiles.get(j).getLetter()) {
                        placedTiles.add(tiles.get(j));
                        tiles.remove(tiles.get(j));
                        break;
                    }
                }
            }
            return new Move(board, moveInput[1], placedTiles,false);
        } else {
            return  new Move(board, null, null,true);
        }
    }
    }
