package pij.board;
import pij.square.RegularSquare;
import pij.square.Square;

public class Board {
     int cols;
     int rows;
     String startingPosition;
     Square[][] squares;

    public Board(int rows,int cols,  String startingPosition) {
        if(rows < 10 || rows > 99) {
            throw new IllegalArgumentException("Rows must be between 10 and 99");
        }
        if(cols < 7 || cols > 26) {
            throw new IllegalArgumentException("Columns must be between 7 and 26");
        }
        if(cols*rows < 192) {
            throw new IllegalArgumentException("Board must have at least 192 squares");
        }
        // Check if the starting position is in the correct format - it needs to be lowercase letter, then row number.
        // Also must be values within the cols and rows given e.g. if 10 rows it can't be d11 and if 7 columns,it can't be z10
        // so need to convert numbers and letters - helper method would be useful
        if(!(Character.isLowerCase(startingPosition.charAt(0)) && Character.isDigit(startingPosition.charAt(1)))) {
            throw new IllegalArgumentException("Starting position must be in format [columnLetter][rowNumber] e.g. d7");
        }

        this.cols = cols;
        this.rows = rows;
        this.startingPosition = startingPosition;
        this.squares = new Square[rows][cols];
        for (int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                this.squares[i][j] = new RegularSquare();
            }
        }
    }

    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public String getStartingPosition() {
        return startingPosition;
    }


    static void main(String[] args) {
        Board board = new Board(20,10,"d7");
        System.out.println(board);
    }
}
