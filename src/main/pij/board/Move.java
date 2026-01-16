package pij.board;

import pij.square.Square;
import pij.tile.Tile;

import java.io.IOException;
import java.util.List;

public class Move {
    private Board board;
    private String position;
    private List<Tile> tiles;
    private String word;
    private boolean isHorizontal;
    private boolean isPass;

    public Move(Board board, String position, List<Tile> tiles) {
        this.board = board;
        this.position = position;
        this.tiles = tiles;
        this.word = "";
        this.isHorizontal = Character.isDigit(this.position.charAt(0));
    }

    public Move() {
        this.isPass = true;
    }

    //if numbers first, then horizontal
    //if letter first, then downward
    //Need to also check for letters already in the squares to ensure it skips occupied squares
    //add validation to check if postion exists, if word exists and if the word can be made with player tiles

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
        System.out.println(word);
    }

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
            while (wordStartRowIndex > 0 && board.isSquareOccupiedRight(wordStartRowIndex, currentColIndex)) {
                wordStartRowIndex--;
            }
        }

        int tilesToBePlaced = 0;

        while (tilesToBePlaced < this.tiles.size()) {
            Square square = board.getSquare(wordStartRowIndex, wordStartColIndex);
            if(square.isSquareOccupied()) {
                //System.out.println("index " + wordStartRowIndex + ", " + wordStartColIndex + " is occupied by "  + square.getTile().getLetter());
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

        return completePreviewWord.toString();
    }

    public boolean getIsPass() {
        return this.isPass;
    }
    public String getPosition() {
        return this.position;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
//        List<Tile> tiles = List.of(
//                new Tile('D', 2),
//                new Tile('I', 1),
//                new Tile('N', 1),
//                new Tile('E', 2),
//                new Tile('D', 2)
//        );
//
//        Move move = new Move(board, "d4", tiles);
//        //move.placeTile();
//        // board.displayBoard();
//
//        List<Tile> tiles2 = List.of(
//                new Tile('T', 1),
//                new Tile('N', 1),
//                new Tile('Z', 9),
//                new Tile('O', 1),
//                new Tile('N', 1)
//        );
//
//        Move move2 = new Move(board, "7c", tiles2);
//        //move2.placeTile();
//        //board.displayBoard();

        List<Tile> tiles3 = List.of(
                new Tile('O', 1),
                new Tile('V', 4),
                new Tile('E', 2)
        );

        Move move3 = new Move(board, "3c", tiles3);
        //move3.placeTile();
        //board.displayBoard();

        board.getSquareByPosition("d3").setTile(new Tile('X', 1));
//        board.getSquareByPosition("c4").setTile(new Tile('C', 1));
//        board.getSquareByPosition("b4").setTile(new Tile('B', 1));

        board.displayBoard();

        System.out.println(move3.previewWord());



    }

}



