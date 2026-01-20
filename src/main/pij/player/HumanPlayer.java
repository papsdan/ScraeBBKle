package pij.player;

import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.tile.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        if (!input.equals(",")) {

            String[] moveInput = input.split(",");
            String word = moveInput[0];
            String position = moveInput[1];

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
            return new Move(board, position, placedTiles);
        } else {
            return new Move();
        }
    }
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
