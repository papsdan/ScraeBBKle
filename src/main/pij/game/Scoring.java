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
     * Get the column index for the start of the word.
     * If the move is horizontal, it checks for occupied tiles to the left of
     * the current position. If so, it backtracks (decrements the column index) until it finds
     * the start of the word.
     * @param currentColIndex the column index of the moves starting position
     * @param currentRowIndex the row index of the moves starting position
     * @param board game board to check for occupied squares
     * @param isHorizontal true if the move is horizontal, false if vertical
     * @return the column index where the word begins
     */
    private int getWordStartColIndex(int currentColIndex,int currentRowIndex,Board board,boolean isHorizontal){
        if(isHorizontal){
            while (currentColIndex >0 && board.isSquareOccupiedLeft(currentRowIndex, currentColIndex)) {
                currentColIndex--;
            }
        }
        return currentColIndex;
    }

    /**
     * Get the row index for the start of the word.
     * If the move is vertical (not horizontal), it checks for occupied tiles above
     * the current position. If so, it backtracks (decrements the row index) until it finds
     * the start of the word.
     * @param currentColIndex the column index of the moves starting position
     * @param currentRowIndex the row index of the moves starting position
     * @param board game board to check for occupied squares
     * @param isHorizontal true if the move is horizontal, false if vertical
     * @return the row index where the word begins
     */
    private int getWordStartRowIndex(int currentColIndex,int currentRowIndex,Board board,boolean isHorizontal){
        if(!isHorizontal){
            while (currentRowIndex >0 && board.isSquareOccupiedAbove(currentRowIndex, currentColIndex)) {
                currentRowIndex--;
            }
        }
        return currentRowIndex;
    }

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

        int wordStartColIndex = getWordStartColIndex(currentColIndex,currentRowIndex,board,move.isHorizontal());
        int wordStartRowIndex = getWordStartRowIndex(currentColIndex,currentRowIndex,board,move.isHorizontal());

        List<Tile> tiles = move.getTiles();
        int tilesPlacedCount = 0;
        int wordMultiplier = 1;

        while(board.isSquareOccupied(wordStartRowIndex, wordStartColIndex) || tilesPlacedCount < tiles.size()) {
            Square square = board.getSquare(wordStartRowIndex, wordStartColIndex);
            if (square.isSquareOccupied()) {
                int tileValue = square.getTile().getValue();
                score += tileValue;
            } else {
                int tileValue = tiles.get(tilesPlacedCount).getValue();
                SquareType squareType = square.getSquareType();
                if(squareType.equals(SquareType.PREMIUM_WORD)) {
                    score += tileValue;
                    wordMultiplier *= board.getSquare(wordStartRowIndex,wordStartColIndex).getMultiplier();
                } else if (squareType.equals(SquareType.PREMIUM_LETTER)) {
                    score += premiumLetterScore(square,tileValue);
                } else {
                    score += tileValue;
                }
                tilesPlacedCount++;
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
