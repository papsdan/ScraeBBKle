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

        int wordStartColIndex = currentColIndex;
        int wordStartRowIndex = currentRowIndex;

        List<Tile> tiles = move.getTiles();
        int i = 0;
        int wordMultiplier = 1;

        if(move.isHorizontal()){
            while (board.getSquare(currentRowIndex, wordStartColIndex - 1).isSquareOccupied()) {
                wordStartColIndex--;
            }
        } else {
            while (board.getSquare(wordStartRowIndex - 1, currentColIndex).isSquareOccupied()) {
                wordStartRowIndex--;
            }
        }

        while(board.getSquare(wordStartRowIndex, wordStartColIndex).isSquareOccupied() || i < tiles.size()) {
            Square square = board.getSquare(wordStartRowIndex, wordStartColIndex);
            if (square.isSquareOccupied()) {
                int tileValue = square.getTile().getValue();
                score += tileValue;
            } else {
                int tileValue = tiles.get(i).getValue();
                SquareType squareType = square.getSquareType();
                if(squareType.equals(SquareType.PREMIUM_WORD)) {
                    score += tileValue;
                    wordMultiplier *= board.getSquare(wordStartRowIndex,wordStartColIndex).getMultiplier();
                    System.out.println(tiles.get(i).getLetter() + "is on a word multiplier = " + board.getSquare(wordStartRowIndex,wordStartColIndex).getMultiplier());
                } else if (squareType.equals(SquareType.PREMIUM_LETTER)) {
                    score += premiumLetterScore(square,tileValue);
                    System.out.println(tiles.get(i).getLetter() + "is on a letter multiplier = " + board.getSquare(wordStartRowIndex,wordStartColIndex).getMultiplier());
                } else {
                    score += tileValue;
                    System.out.println(tiles.get(i).getLetter() + "is on NOT on a multiplier = " + tileValue);

                }
                i++;
            }

            if (move.isHorizontal()) {
                wordStartColIndex++;
            } else {
                wordStartRowIndex++;
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
