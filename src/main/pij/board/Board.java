package pij.board;
import pij.square.PremiumLetterSquare;
import pij.square.PremiumWordSquare;
import pij.square.RegularSquare;
import pij.square.Square;

public class Board {
    private int cols;
    private int rows;
    private String startingPosition;
    private Square[][] squares;

    public Board(int cols, int rows, String startingPosition) {
        if(cols < 7 || cols > 26) {
            throw new IllegalArgumentException("Columns must be between 7 and 26");
        }
        if(rows < 10 || rows > 99) {
            throw new IllegalArgumentException("Rows must be between 10 and 99");
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

    public void setSquare(int row, int column, Square square) {
        this.squares[row][column] = square;
    }

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    public Square getSquareByPosition(String startingPosition){
        char colChar = startingPosition.charAt(0);
        int rowNum =  Integer.parseInt(startingPosition.substring(1));
        int colNum = 0;
        if (colChar >= 'a' && colChar <= 'z') {
            colNum = (int) colChar - (int) 'a';
        }
        return getSquare(rowNum-1, colNum);
    }


    public void prettyPrint() {
        for (int i=0;i<this.squares.length;i++) {
            System.out.println();
            for (int j=0;j<this.squares[i].length;j++) {
                System.out.print(getSquare(i,j).getDisplayString());
            }
        }
    }


    static void main(String[] args) {
        Board board = new Board(14,16,"f7");
        System.out.println(board);
        

        board.setSquare(2,0,new PremiumWordSquare(10));
        board.setSquare(1,1,new PremiumLetterSquare(24));
        board.setSquare(5,2,new PremiumWordSquare(13));
        board.setSquare(0,0,new PremiumWordSquare(16));
        board.getSquare(0,0);
        board.prettyPrint();
        System.out.println();
        System.out.println(board.getSquareByPosition("a1").getDisplayString());

}
    }

