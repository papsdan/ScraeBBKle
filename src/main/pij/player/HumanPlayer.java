package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * Human player is a player that has a name, score, a tile rack and can make moves by manual input.
 */
public class HumanPlayer extends Player {
    /**
     * Constructs a new HumanPlayer with specified name.
     * @param playerName the name of the player
     */
    public HumanPlayer(String playerName) {
        super(playerName);
    }

    /**
     * Prompts the user for a move and parses the input.
     *
     * @param board the game board
     * @param isFirstMove true if this is the first move of the game
     * @return the move entered by the user
     */
    @Override
    public Move makeMove(Board board, boolean isFirstMove) {
        Scanner sc = new Scanner(System.in);
        String input = getValidInput(sc);

        if (!input.equals(",")) {

            String[] moveInput = input.split(",");
            String word = moveInput[0];
            String position = moveInput[1];

            List<Tile> placedTiles = getTiles(word);
            return new Move(board, position, placedTiles);
        } else {
            return new Move();
        }
    }
    /**
     * Gets tiles from the players rack that match the letters being played in the move.
     * Matches characters in the word to tiles in the rack:
     * Lowercase letters match wildcard tiles and assigned that letter.
     * Uppercase letters match styandard tiles with that letter.
     *
     * @param word the word to find tiles for
     * @return list of tiles from the tile rack matching the word
     */
    private List<Tile> getTiles(String word) {
        List<Tile> tiles = new ArrayList<>(this.getTileRack().getTiles());
        List<Tile> placedTiles = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char letter =  word.charAt(i);

            if(Character.isLowerCase(letter)) {
                for (int j = 0; j < tiles.size(); j++) {
                    if (tiles.get(j).isWildcard()){
                        tiles.get(j).setWildcardLetter(letter);
                        placedTiles.add(tiles.get(j));
                        tiles.remove(j);
                        break;
                    }
                }
            } else {
                for (int j = 0; j < tiles.size(); j++) {
                    if (tiles.get(j).getLetter() == letter) {
                        placedTiles.add(tiles.get(j));
                        tiles.remove(j);
                        break;
                    }
            }
            }
        }
        return placedTiles;
    }
    /**
     * Prompts the user for input and validates the format.
     * Will continue to prompt until a valid format is entered.
     *
     * @param sc the scanner for user input
     * @return a valid move input string
     */
    private String getValidInput(Scanner sc) {
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)\n" +
                "For example, for suitable tile rack and board configuration, a downward move\n" +
                "could be \"HI,f4\" and a rightward move could be \"HI,4f\".\n" +
                "In the word, upper-case letters are standard tiles and lower-case letters\n" +
                "are wildcards.\n" +
                "Entering \",\" passes the turn.");

        String input = sc.nextLine();
        while(!isValidMoveFormat(input)) {
            System.out.println("Illegal move format");
            System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)\n" +
                    "For example, for suitable tile rack and board configuration, a downward move\n" +
                    "could be \"HI,f4\" and a rightward move could be \"HI,4f\".\n" +
                    "In the word, upper-case letters are standard tiles and lower-case letters\n" +
                    "are wildcards.\n" +
                    "Entering \",\" passes the turn.");
            input =  sc.nextLine();
        }
        return input;
    }
    /**
     * Validates the format of a move input string:
     * "," for pass
     * "WORD,position" where position is either lowercase letter + number (e.g. "d7") or number + lowercase letter (e.g. "7d").
     * @param input the input string to validate
     * @return true if the format is valid, false otherwise
     */
    public boolean isValidMoveFormat(String input){
        if(input.equals(",")){
            return true;
        }

        String[] moveInput = input.split(",");
        if(moveInput.length != 2){
            return false;
        }

        String word = moveInput[0];
        String position = moveInput[1];
        Pattern wordPattern = Pattern.compile("[a-zA-Z]+");
        if (!wordPattern.matcher(word).matches()) {
            return false;
        }
        Pattern positionPattern1 = Pattern.compile("[a-z]\\d+");
        Pattern positionPattern2 = Pattern.compile("\\d+[a-z]");
        if(!positionPattern1.matcher(position).matches() && !positionPattern2.matcher(position).matches()){
            return false;
        }

        return true;
    }
    
    }
