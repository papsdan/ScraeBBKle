package pij.board;
import pij.square.Square;

import java.io.IOException;


public class Board {
    private final int cols;
    private final int rows;
    private final String startingPosition;
    private final Square[][] squares;

    public Board(int cols, int rows, String startingPosition) {
        if (cols < 7 || cols > 26) {
            throw new IllegalArgumentException("Columns must be between 7 and 26");
        }
        if (rows < 10 || rows > 99) {
            throw new IllegalArgumentException("Rows must be between 10 and 99");
        }
        if (cols * rows < 192) {
            throw new IllegalArgumentException("Board must have at least 192 squares");
        }
        // Check if the starting position is in the correct format - it needs to be lowercase letter, then row number.
        // Also must be values within the cols and rows given e.g. if 10 rows it can't be d11 and if 7 columns,it can't be z10
        // so need to convert numbers and letters - helper method would be useful
        if (!(Character.isLowerCase(startingPosition.charAt(0)) && Character.isDigit(startingPosition.charAt(1)))) {
            throw new IllegalArgumentException("Starting position must be in format [columnLetter][rowNumber] e.g. d7");
        }

        this.cols = cols;
        this.rows = rows;
        this.startingPosition = startingPosition;
        this.squares = new Square[rows][cols];
    }

    public int getRows() {
        return this.rows;
    }
    public int getCols() {
        return this.cols;
    }
    public String getStartingPosition() {
        return this.startingPosition;
    }

    public void setSquare(int row, int column, Square square) {
        this.squares[row][column] = square;
    }

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    public String getSquarePositionCoordinates(String position){
        int colIndex = getColumnIndex(position);
        int rowIndex = getRowIndex(position);
        return (colIndex) + "," + (rowIndex);
    }

    public Square getSquareByPosition(String position) {
        int colIndex = getColumnIndex(position);
        int rowIndex = getRowIndex(position);
        return getSquare(rowIndex, colIndex);
    }

    public int getColumnIndex(String position){
        return position.charAt(0) - 'a';
    }

    public int getRowIndex(String position){
        return Integer.parseInt(position.substring(1)) - 1;
    }

    public void displayBoard() {
        displayColumnIndex();
        for (int row = 0; row < this.squares.length; row++) {
            System.out.println();
            displayRowIndex(row);
            System.out.print("  ");
            for (int col = 0; col < this.squares[row].length; col++) {
                System.out.print(getSquare(row, col).getDisplayString());
            }
            System.out.print("  ");
            displayRowIndex(row);
        }
        System.out.println();
        displayColumnIndex();

    }

    public void displayColumnIndex() {
        System.out.println();
        System.out.print("     ");
        for (int i = 0; i < this.cols; i++) {
            System.out.print(" " + (char) ('a' + i) + " ");
        }
        System.out.println();
    }

    public void displayRowIndex(int row) {
        if (row < 9) {
            System.out.print(" " + (row + 1) + " ");
        } else {
            System.out.print((row + 1) + " ");
        }
    }

}

