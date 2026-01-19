package pij.board;

import pij.square.PremiumLetterSquare;
import pij.square.PremiumWordSquare;
import pij.square.RegularSquare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for loading boards from files.
 * Parses board configuration files and creates Board objects.
 */
public class BoardLoader {
    /**
     * Loads a board from a file.
     *
     * @param filePath the path to the board file
     * @return the loaded Board object
     * @throws IOException if the file cannot be read/found
     * @throws IllegalArgumentException if the file format is invalid
     */
    public static Board loadFromFile(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        return parseContent(content);
    }

    /**
     * Parses the content of a board file and creates a Board object.
     * File format:
     * line 1 = number of columns
     * line 2 = number of rows
     * line 3 = starting position
     * remaining lines = board rows with squares.
     *
     * @param fileContent the content of the board file
     * @return the parsed Board object
     * @throws IllegalArgumentException if the content is invalid
     */
    private static Board parseContent(String fileContent) {
        String[] lines = fileContent.split("\n");
        int expectedColumns = Integer.parseInt(lines[0].trim());
        int expectedRows = Integer.parseInt(lines[1].trim());
        String startingPosition = lines[2].trim();

        if (lines.length - 3 != expectedRows) {
            throw new IllegalArgumentException();
        }

        Board board = new Board(expectedColumns, expectedRows, startingPosition);

        for (int currentRow = 0; currentRow < expectedRows; currentRow++) {
            parseBoardRow(lines[currentRow + 3], board, currentRow, expectedColumns);
        }

        return board;
    }
    /**
     * Parses a single row of the board.
     *
     * @param line the line of text to parse
     * @param board the board to add squares to
     * @param currentRow the row index being parsed
     * @param expectedColumns the expected number of columns / expected number of squares in the row
     * @throws IllegalArgumentException if the row has incorrect number of columns
     */
    private static void parseBoardRow(String line, Board board, int currentRow, int expectedColumns) {

        int currentColumn = 0;
        for (int character = 0; character < line.length(); character++) {
            char c = line.charAt(character);

            if (c == '.') {
                board.setSquare(currentRow, currentColumn, new RegularSquare());
                currentColumn++;
            }
            if (c == '[') {
                int endIndex = line.indexOf(']', character + 1);
                String strMultiplier = line.substring(character + 1, endIndex);
                int multiplier = Integer.parseInt(strMultiplier);
                board.setSquare(currentRow, currentColumn, new PremiumLetterSquare(multiplier));
                currentColumn++;
                character = endIndex;
            }
            if (c == '<') {
                int endIndex = line.indexOf('>', character + 1);
                String strMultiplier = line.substring(character + 1, endIndex);
                int multiplier = Integer.parseInt(strMultiplier);
                board.setSquare(currentRow, currentColumn, new PremiumWordSquare(multiplier));
                currentColumn++;
                character = endIndex;
            }
        }
        if (currentColumn != expectedColumns) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.displayBoard();
    }
}