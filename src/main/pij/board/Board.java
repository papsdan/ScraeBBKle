package pij.board;
import pij.square.Square;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Board represents the game board grid for ScraeBBKle.
 * This class manages the grid of Squares where tiles can be placed.
 */
public class Board {
    private final int cols;
    private final int rows;
    private final String startingPosition;
    private final Square[][] squares;

    /**
     * Constructs new Board with specified validated dimensions and starting position.
     * @param cols number of columns (between 7 and 26)
     * @param rows number of rows (between 10 and 99
     * @param startingPosition the starting square position in format [columnLetter][rowNumber] e.g. d7
     */

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
        if (!startingPosition.matches("[a-z]\\d{1,2}")) {
            throw new IllegalArgumentException("Starting position must be in format [columnLetter][rowNumber] e.g. d7");
        }

        this.cols = cols;
        this.rows = rows;
        this.startingPosition = startingPosition;
        this.squares = new Square[rows][cols];
    }

    /**
     * Getter for the number of rows in the board
     * @return the number of rows in the board
     */
    public int getRows() {
        return this.rows;
    }
    /**
     * Getter for the number of columns in the board
     * @return the number of columns in the board
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Getter for the starting position to be used on the board
     * @return the starting position on the board
     */
    public String getStartingPosition() {
        return this.startingPosition;
    }

    /**
     * Sets a square at the specified position on the board using indexes.
     * @param row the row index
     * @param column the column index
     * @param square the square type to place
     */
    public void setSquare(int row, int column, Square square) {
        this.squares[row][column] = square;
    }

    /**
     * Returns the square at the specified position on the board using indexes.
     *
     * @param row the row index
     * @param column the column index
     * @return the square at the specified position
     */
    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    /**
     * Returns the square at the specified board position using the coordinates string.
     *
     * @param position the position string (e.g. d7 or 7d)
     * @return the square at the specified position
     */
    public Square getSquareByPosition(String position) {
        int colIndex = getColumnIndex(position);
        int rowIndex = getRowIndex(position);
        return getSquare(rowIndex, colIndex);
    }

    /**
     * Gets the column index from a position string.
     *
     * @param position the position string e.g. d7 or 7d
     * @return the column index starting from 0
     * @throws IllegalArgumentException if no column letter is found in specified position string
     */
    public int getColumnIndex(String position){
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(position);
        if (matcher.find()) {
            return matcher.group().charAt(0) - 'a';
        }
        throw new IllegalArgumentException("No column letter found in position: " + position);

    }

    /**
     * Gets the row index from a position string.
     *
     * @param position the position string e.g. d7 or 7d
     * @return the row index starting from 0
     * @throws IllegalArgumentException if no row number is found in specified position string
     */
    public int getRowIndex(String position){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(position);
        if (matcher.find()) {
            String numberStr = matcher.group();
            return Integer.parseInt(numberStr)-1;
        }
        throw new IllegalArgumentException("No row number found in position: " + position);
    }

    /**
     * Displays the board with row (1-99) and column labels (a-z).
     */
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

    /**
     * Displays the column index (a-z) surrounding the board
     */
    public void displayColumnIndex() {
        System.out.println();
        System.out.print("     ");
        for (int i = 0; i < this.cols; i++) {
            System.out.print(" " + (char) ('a' + i) + " ");
        }
        System.out.println();
    }

    /**
     * Displays the row index (1-99) surrounding the board
     */
    public void displayRowIndex(int row) {
        if (row < 9) {
            System.out.print(" " + (row + 1) + " ");
        } else {
            System.out.print((row + 1) + " ");
        }
    }

    /**
     * Checks if a square at the specified position is occupied by a tile.
     * Returns false if the position is out of bounds.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square is occupied, false if empty or out of bounds
     */
    public boolean isSquareOccupied(int row, int col) {
        if (row < 0 || row >= this.getRows() || col < 0 || col >= this.getCols()) {
            return false;
        }
        return this.getSquare(row, col).isSquareOccupied();
    }

    /**
     * Checks if the square above the specified position is occupied.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square above is occupied, false if empty or out of bounds
     */
    public boolean isSquareOccupiedAbove(int row, int col) {
        return isSquareOccupied(row - 1, col);
    }

    /**
     * Checks if the square below the specified position is occupied.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square below is occupied, false if empty or out of bounds
     */
    public boolean isSquareOccupiedBelow(int row, int col) {
        return isSquareOccupied(row + 1, col);
    }

    /**
     * Checks if the square to the left of the specified position is occupied.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square to the left is occupied, false if empty or out of bounds
     */
    public boolean isSquareOccupiedLeft(int row, int col) {
        return isSquareOccupied(row, col - 1);

    }

    /**
     * Checks if the square to the right the specified position is occupied.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the square to the right is occupied, false if empty or out of bounds
     */
    public boolean isSquareOccupiedRight(int row, int col) {
        return isSquareOccupied(row, col + 1);
    }

}

