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
        int columns = Integer.parseInt(lines[0].trim());
        int rows = Integer.parseInt(lines[1].trim());
        String startingPosition = lines[2].trim();

        Board board = new Board(columns, rows, startingPosition);

        for (int row = 0; row < rows; row++) {
            parseBoardRow(lines[row + 3], board, row);
        }

        return board;
    }

    private static void parseBoardRow(String line, Board board, int currentRow) {

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
    }

    public static void main(String[] args) throws IOException {
        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
        board.displayBoard();
    }
}