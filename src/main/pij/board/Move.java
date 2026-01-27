package pij.board;

import pij.square.Square;
import pij.tile.Tile;
import java.util.List;

/**
 * Represents a move in the game.
 * A move consists of the tiles to be placed on the board and the position, which can be either
 * horizontally or vertically. It can also represent a pass move.
 */
public class Move {
    private Board board;
    private String position;
    private List<Tile> tiles;
    private String word;
    private boolean isHorizontal;
    private boolean isPass;

    /**
     * Constructs a new Move with the board, position and tiles.
     * The direction is determined by the position format:
     * - Horizontal = Number first (e.g "7d")
     * - Vertical = Letter first (e.g "d7")
     *
     * @param board the game board
     * @param position the position for the move
     * @param tiles the tiles to place
     */
    public Move(Board board, String position, List<Tile> tiles) {
        this.board = board;
        this.position = position;
        this.tiles = tiles;
        this.word = "";
        this.isHorizontal = Character.isDigit(this.position.charAt(0));
    }

    /**
     * Constructs a pass move, by setting isPass to true if no arguments are
     */
    public Move() {
        this.isPass = true;
    }

    /**
     * Places the tiles on the board at the specified position.
     * Skips over occupied squares and builds the word string to get the letters used in the move.
     */
    public void placeTile() {

        int currentColIndex = board.getColumnIndex(position);
        int currentRowIndex = board.getRowIndex(position);
        int tilesPlaced = 0;

        while (tilesPlaced < this.tiles.size()) {
            Square square = board.getSquare(currentRowIndex, currentColIndex);
            if (!square.isSquareOccupied()) {
                square.setTile(tiles.get(tilesPlaced));
                this.word = this.word + tiles.get(tilesPlaced).getLetter();
                if (this.isHorizontal) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }

                tilesPlaced++;

            } else {
                this.word = this.word + square.getTile().getLetter();

                if (this.isHorizontal) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
            }
        }
    }

    /**
     * Previews the complete word that would be formed by this move.
     * Includes existing tiles on the board that would connect to the placed tiles.
     * However, this does not modify the board and is used for validation checks before placing the tile(s).
     *
     * @return the complete word that would be formed
     */
    public String previewWord() {

        int currentColIndex = board.getColumnIndex(position);
        int currentRowIndex = board.getRowIndex(position);

        int wordStartColIndex = currentColIndex;
        int wordStartRowIndex = currentRowIndex;

        StringBuilder completePreviewWord = new StringBuilder();

        //FINDING THE START INDEX TO WORK OUT WHAT THE START OF THE WORD IS
        if (isHorizontal) {
            //check if any tiles to left to work out what the starting column index is of word
            while (wordStartColIndex > 0 && board.isSquareOccupiedLeft(currentRowIndex, wordStartColIndex)) {
                wordStartColIndex--;
            }
        } else {
            //check if any tiles above to work out what the starting row index is of word
            while (wordStartRowIndex > 0 && board.isSquareOccupiedAbove(wordStartRowIndex, currentColIndex)) {
                wordStartRowIndex--;
            }
        }

        int tilesToBePlaced = 0;

        while (tilesToBePlaced < this.tiles.size()) {
            Square square = board.getSquare(wordStartRowIndex, wordStartColIndex);
            if(square.isSquareOccupied()) {
                completePreviewWord.append(square.getTile().getLetter());
            } else {
                completePreviewWord.append(tiles.get(tilesToBePlaced).getLetter());
                tilesToBePlaced++;
            }
            if(isHorizontal){
                wordStartColIndex++;
            } else {
                wordStartRowIndex++;
            }
        }
        while(board.isSquareOccupied(wordStartRowIndex, wordStartColIndex)) {
            completePreviewWord.append(board.getSquare(wordStartRowIndex, wordStartColIndex).getTile().getLetter());

            if(isHorizontal){
                wordStartColIndex++;
            } else {
                wordStartRowIndex++;
            }
        }

        return completePreviewWord.toString();
    }

    /**
     * Checks if this move is a pass.
     *
     * @return true if this is a pass move, false if it is not.
     */
    public boolean getIsPass() {
        return this.isPass;
    }

    /**
     * Returns the position for this move.
     *
     * @return the position string
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * Returns the tiles to be placed in this move.
     *
     * @return the list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Checks if this move is horizontal.
     *
     * @return true if horizontal, false if vertical
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Returns the letters used in the move.
     *
     * @return the tile letters used in the move
     */
    public String getWord() {
        return word;
    }
}



