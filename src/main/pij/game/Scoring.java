package pij.game;

import pij.board.Board;
import pij.board.Move;

import pij.square.Square;
import pij.square.SquareType;
import pij.tile.Tile;

import java.util.List;
/**
 * Calculates scores for moves in the game.
 * Handles premium letter squares, premium word squares and the 7 tile extra bonus scoring.
 */
public class Scoring {

    /**
     * Calculates the total score for a move.
     *
     * @param move the move to score
     * @param board the game board
     * @return the total score for the move
     */
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
            while (wordStartColIndex >0 && board.isSquareOccupiedLeft(currentRowIndex, wordStartColIndex)) {
                wordStartColIndex--;
            }
        } else {
            while (wordStartRowIndex >0 && board.isSquareOccupiedAbove(wordStartRowIndex, currentColIndex)) {
                wordStartRowIndex--;
            }
        }

        while(board.isSquareOccupied(wordStartRowIndex, wordStartColIndex) || i < tiles.size()) {
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
                } else if (squareType.equals(SquareType.PREMIUM_LETTER)) {
                    score += premiumLetterScore(square,tileValue);
                } else {
                    score += tileValue;
                }
                i++;
            }

            if (move.isHorizontal()) {
                wordStartColIndex++;
            } else {
                wordStartRowIndex++;
            }

        }

        score *= wordMultiplier;
        if (tiles.size() == 7) {
            score += 60;
        }
        return score;
    }

    /**
     * Calculates the score for a tile on a premium letter square.
     *
     * @param square the premium letter square
     * @param tileValue the base value of the tile
     * @return the multiplied tile value
     */
    private int premiumLetterScore(Square square, int tileValue) {
        return square.getMultiplier() * tileValue;
    }



}
