package pij.game;

import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.player.HumanPlayer;
import pij.player.Player;
import pij.square.PremiumLetterSquare;
import pij.square.PremiumWordSquare;
import pij.square.Square;
import pij.square.SquareType;
import pij.tile.Tile;

import java.io.IOException;
import java.util.List;

public class Scoring {
    //board to get multiples for squares
    //move letters
    //move position
    //maybe utilise similar code to placing tiles to get the square multiples and letter, but no skipping. Will need to check left and right to check where the word starts#
    //methods:
    // premium letter score (multiplier * letter point),
    // premium word score (multiplier * sum(all letter points in the word))
    //--check if multiple letter included in this or origianl letter point--
    //total score (loops through each square position, checks if regular,premium letter, premium word (if occupied then just assumes its regular as multiplier used), runs the other methods to give number. Then adds to a total score count. Then once finishes returns sum of score)

    public int totalScore(Move move, Board board) {
        int score = 0;

        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());
        List<Tile> tiles = move.getTiles();
        int i = 0;
        int wordMultiplier = 1;

        while(i < tiles.size()) {
            if (!board.getSquare(currentRowIndex, currentColIndex).isSquareOccupied()) {
                System.out.println(tiles.get(i).getLetter() +": square is not occupied");
                int tileValue = tiles.get(i).getValue();
                System.out.println("tile value is " + tileValue);
                Square square = board.getSquare(currentRowIndex, currentColIndex);
                SquareType squareType = square.getSquareType();
                if(squareType.equals(SquareType.PREMIUM_WORD)) {
                    score += tileValue;
                    wordMultiplier *= board.getSquare(currentRowIndex,currentColIndex).getMultiplier();
                    System.out.println(tiles.get(i).getLetter() + "is on a word multiplier = " + board.getSquare(currentRowIndex,currentColIndex).getMultiplier());
                } else if (squareType.equals(SquareType.PREMIUM_LETTER)) {
                    score += premiumLetterScore(square,tileValue);
                    System.out.println(tiles.get(i).getLetter() + "is on a letter multiplier = " + board.getSquare(currentRowIndex,currentColIndex).getMultiplier());
                } else {
                    score += tileValue;
                    System.out.println(tiles.get(i).getLetter() + "is on NOT on a multiplier = " + tileValue);

                }

                if (move.isHorizontal()) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
                i++;

            } else {
                int tileValue = board.getSquare(currentRowIndex, currentColIndex).getTile().getValue();
                score += tileValue;
                if (move.isHorizontal()) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
            }
        }
        System.out.println("Letter score only: " + score);
        System.out.println("Word multiplier: " + wordMultiplier);
        System.out.println("Score = Letter score * wordMultiplier  = " + score);

        score *= wordMultiplier;
        if (tiles.size() == 7) {
            score += 60;
        }
        return score;
    }
    

    public int premiumLetterScore(Square square, int tileValue) {
        return square.getMultiplier() * tileValue;
    }



}
