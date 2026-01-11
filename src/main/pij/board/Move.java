package pij.board;

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

    public Move(Board board, String position, List<Tile> tiles, boolean isPass) {
        this.board = board;
        this.position = position;
        this.tiles = tiles;
        this.word = "";
        this.isPass = isPass;

        if (!isPass) {
            this.isHorizontal = Character.isDigit(this.position.charAt(0));
        }
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
            if (!board.getSquare(currentRowIndex, currentColIndex).isSquareOccupied()) {
                board.getSquare(currentRowIndex, currentColIndex).setTile(tiles.get(tilesPlaced));
                this.word = this.word + tiles.get(tilesPlaced).getLetter();
                if (this.isHorizontal) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }

                tilesPlaced++;

            } else {
                this.word = this.word + board.getSquare(currentRowIndex, currentColIndex).getTile().getLetter();

                if (this.isHorizontal) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
            }
        }
        System.out.println(word);
    }

    public String formWord() {

        int currentColIndex = board.getColumnIndex(position);
        int currentRowIndex = board.getRowIndex(position);

        int wordStartColIndex = currentColIndex;
        int wordStartRowIndex = currentRowIndex;

        int wordEndColIndex = currentColIndex;
        int wordEndRowIndex = currentRowIndex;

        StringBuilder completeWord = new StringBuilder();

        if (isHorizontal) {
            //check if any tiles to left to work out what the starting column index is of word
            while (wordStartColIndex > 0 && board.getSquare(currentRowIndex, wordStartColIndex - 1).isSquareOccupied()) {
                wordStartColIndex--;
            }

            while (wordEndColIndex < board.getCols()-1 && board.getSquare(currentRowIndex, wordEndColIndex + 1).isSquareOccupied()) {
                wordEndColIndex++;
            }
            for (int col = wordStartColIndex; col <= wordEndColIndex; col++) {
                completeWord.append(board.getSquare(currentRowIndex, col).getTile().getLetter());
            }
        } else {
            //check if any tiles above to work out what the starting row index is of word
            while (wordStartRowIndex > 0 && board.getSquare(wordStartRowIndex - 1, currentColIndex).isSquareOccupied()) {
                wordStartRowIndex--;
            }

            while (wordEndRowIndex < board.getRows() -1 && board.getSquare(wordEndRowIndex + 1, currentColIndex).isSquareOccupied()) {
                wordEndRowIndex++;
            }
            for (int row = wordStartRowIndex; row <= wordEndRowIndex; row++) {
                completeWord.append(board.getSquare(row, currentColIndex).getTile().getLetter());
            }
        }
        return completeWord.toString();
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
        List<Tile> tiles = List.of(
                new Tile('D', 2),
                new Tile('I', 1),
                new Tile('N', 1),
                new Tile('E', 2),
                new Tile('D', 2)
        );

        Move move = new Move(board, "d4", tiles,false);
        move.placeTile();
        // board.displayBoard();

        List<Tile> tiles2 = List.of(
                new Tile('T', 1),
                new Tile('N', 1),
                new Tile('Z', 9),
                new Tile('O', 1),
                new Tile('N', 1)
        );

        Move move2 = new Move(board, "7c", tiles2,false);
        move2.placeTile();
        //board.displayBoard();

        List<Tile> tiles3 = List.of(
                new Tile('O', 1),
                new Tile('V', 4),
                new Tile('E', 2)
        );

        Move move3 = new Move(board, "4e", tiles3,false);
        move3.placeTile();
        //board.displayBoard();

        board.getSquareByPosition("d3").setTile(new Tile('X', 1));
        board.getSquareByPosition("c4").setTile(new Tile('C', 1));
        board.getSquareByPosition("b4").setTile(new Tile('B', 1));

        board.displayBoard();

        System.out.println(move3.formWord());
        System.out.println(move.formWord());
        System.out.println(move2.formWord());


    }

}



