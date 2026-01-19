package pij.board;

import pij.square.PremiumLetterSquare;
import pij.square.PremiumWordSquare;
import pij.square.RegularSquare;
import pij.square.Square;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class BoardLoader {
    public static Board loadFromFile(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        return parseContent(content);
    }

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