package pij.board;

import pij.game.Game;
import pij.player.HumanPlayer;
import pij.player.Player;
import pij.tile.Tile;
import pij.tile.TileBag;
import pij.tile.TileRack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoveValidator {
    private final HashSet<String> validWords;

    public MoveValidator() throws IOException {
        String content = Files.readString(Path.of("resources/wordlist.txt"));
        String[] words = content.split("\n");
        this.validWords = new HashSet<>();
        for (String word : words) {
            this.validWords.add(word.trim().toLowerCase());
        }
    }

    public boolean validateMove(Move move, Board board, boolean isFirstMove, Player currentPlayer) {

        if (move.getIsPass()) {
            return true;
        }
        if (!isSingleWord(board, move)) {
            System.out.println("Move must only contain single word");
            return false;
        }

        if (!tilesPlayedInRack(move, currentPlayer)) {
            System.out.println("Move must only include tile from the rack");
            return false;
        }
        if (!isWithinBoard(move, board)) {
            System.out.println("The move goes starts or will go off the board");
            return false;
        }

        if (isFirstMove) {
            if (!atLeastTwoTilesPlayed(move)) {
                System.out.println("First move must use at least two tiles");
                return false;
            }
            if (!usesStartingSquare(board, move)) {
                System.out.println("First move must use the starting square");
                return false;
            }
        }

        if (!isValidWord(move.previewWord())) {
            System.out.println("The board does not permit word " + move.previewWord() + " at position " + move.getPosition() + ". Please try again.");
            return false;
        }

        if (!isFirstMove && !connectToExistingTiles(board, move)) {
            System.out.println("Move must connect to another existing tile.");
            return false;
        }

        return true;
    }

    public boolean isValidWord(String word) {
        return this.validWords.contains(word.toLowerCase());
    }

    public boolean isWithinBoard(Move move, Board board) {

        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());

        if (currentColIndex < 0 || currentColIndex >= board.getCols() || currentRowIndex < 0 || currentRowIndex >= board.getRows()) {
            return false;
        }

        int tilesToBePlaced = move.getTiles().size();

        while (tilesToBePlaced > 0) {
            if (currentColIndex >= board.getCols() || currentRowIndex >= board.getRows()) {
                return false;
            }

            if (!isSquareOccupied(board, currentRowIndex, currentColIndex)) {
                tilesToBePlaced--;
            }


            if (move.isHorizontal()) {
                currentColIndex++;
            } else {
                currentRowIndex++;
            }
        }

        return true;
    }

    public boolean isSingleWord(Board board, Move move) {
        int currentColIndex = board.getColumnIndex(move.getPosition());
        int currentRowIndex = board.getRowIndex(move.getPosition());

        for (int i = 0; i <= move.getTiles().size(); i++) {
            while (isSquareOccupied(board, currentRowIndex, currentColIndex)) {
                if (move.isHorizontal()) {
                    currentColIndex++;
                } else {
                    currentRowIndex++;
                }
            }
            if (move.isHorizontal()) {
                if (isSquareOccupiedAbove(board, currentRowIndex, currentColIndex) ||
                        isSquareOccupiedBelow(board, currentRowIndex, currentColIndex)) {
                    return false;
                }
                currentColIndex++;
            } else {
                if (isSquareOccupiedRight(board, currentRowIndex, currentColIndex) ||
                        isSquareOccupiedLeft(board, currentRowIndex, currentColIndex)) {
                    return false;
                }
                currentRowIndex++;

            }

        }
        return true;
    }

    public boolean isSquareOccupiedAbove(Board board, int row, int col) {
        return isSquareOccupied(board, row - 1, col);
    }

    public boolean isSquareOccupiedBelow(Board board, int row, int col) {
        return isSquareOccupied(board, row + 1, col);
    }


    public boolean isSquareOccupiedLeft(Board board, int row, int col) {
        return isSquareOccupied(board, row, col - 1);

    }

    public boolean isSquareOccupiedRight(Board board, int row, int col) {
        return isSquareOccupied(board, row, col + 1);
    }

    public boolean isSquareOccupied(Board board, int row, int col) {
        if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols()) {
            return false;
        }
        return board.getSquare(row, col).isSquareOccupied();
    }

    public boolean atLeastTwoTilesPlayed(Move move) {
        return move.getTiles().size() >= 2;
    }

    public boolean usesStartingSquare(Board board, Move move) {
        int moveColIndex = board.getColumnIndex(move.getPosition());
        int moveRowIndex = board.getRowIndex(move.getPosition());
        int startColIndex = board.getColumnIndex(board.getStartingPosition());
        int startRowIndex = board.getRowIndex(board.getStartingPosition());

        int tilesToBePlaced = move.getTiles().size();

        for (int i = 0; i < tilesToBePlaced; i++) {
            if (moveColIndex == startColIndex && moveRowIndex == startRowIndex) {
                return true;
            }

            if (move.isHorizontal()) {
                moveColIndex++;
            } else {
                moveRowIndex++;
            }
        }
        return false;
    }

    public boolean connectToExistingTiles(Board board, Move move) {
        int moveColIndex = board.getColumnIndex(move.getPosition());
        int moveRowIndex = board.getRowIndex(move.getPosition());
        int tilesToPlace = move.getTiles().size();
        int tilesPlacedCounter = 0;

        // Check if tile directly before the move position. Horizontal (left) and Vertical (above)
        if (move.isHorizontal()) {
            if (isSquareOccupied(board, moveRowIndex, moveColIndex - 1)) {
                return true;
            }
        } else {
            if (isSquareOccupied(board, moveRowIndex - 1, moveColIndex)) {
                return true;
            }
        }

        while (tilesPlacedCounter < tilesToPlace) {
            if (move.isHorizontal() && (isSquareOccupied(board, moveRowIndex - 1, moveColIndex)
                    || isSquareOccupied(board, moveRowIndex + 1, moveColIndex))) {
                return true;
            } else if (!move.isHorizontal() && (isSquareOccupied(board, moveRowIndex, moveColIndex - 1)
                    || isSquareOccupied(board, moveRowIndex, moveColIndex + 1))) {
                return true;
            }
            tilesPlacedCounter++;

            if (move.isHorizontal()) {
                moveColIndex++;
            } else {
                moveRowIndex++;
            }
        }
        // Check if tile directly after the move position. Horizontal (right) and Vertical (below)

        return isSquareOccupied(board, moveRowIndex, moveColIndex);

    }

    public boolean tilesPlayedInRack(Move move, Player player) {

        List<Tile> tileRackCopy = new ArrayList<>(player.getTileRack().getTiles());
        List<Tile> moveTiles = move.getTiles();

        for (int i = 0; i < moveTiles.size(); i++) {
            char mTile = moveTiles.get(i).getLetter();
            boolean isLetterFound = false;
            for (int j = 0; j < tileRackCopy.size(); j++) {
                char trTiles = tileRackCopy.get(j).getLetter();
                if (mTile == trTiles) {
                    tileRackCopy.remove(tileRackCopy.get(j));
                    isLetterFound = true;
                    break;
                }
            }
            if (!isLetterFound) {
                return false;
            }
        }
        return true;

    }

    static void main(String[] args) throws IOException {}
//        Board board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
//        List<Tile> tiles = List.of(
//                new Tile('D', 2),
//                new Tile('I', 1),
//                new Tile('N', 1),
//                new Tile('E', 2),
//                new Tile('D', 2)
//        );
//
//        Move move = new Move(board, "d4", tiles);
//        move.placeTile();
//        MoveValidator moveValidator = new MoveValidator();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move));
//
//        List<Tile> tiles2 = List.of(
//                new Tile('T', 2),
//                new Tile('N', 1),
//                new Tile('Z', 1),
//                new Tile('O', 2),
//                new Tile('N', 2)
//        );
//
//        Move move2 = new Move(board, "7c", tiles2);
//        move2.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move2));
//
//        List<Tile> tiles3 = List.of(
//                new Tile('O', 2)
//        );
//
//        Move move3 = new Move(board, "e6", tiles3);
//        move3.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move3));
//
//
//        List<Tile> tiles4 = List.of(
//                new Tile('O', 2),
//                new Tile('R', 1)
//        );
//
//        Move move4 = new Move(board, "8h", tiles4);
//        move4.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move4));
//
//        List<Tile> tiles5 = List.of(
//                new Tile('O', 2),
//                new Tile('V', 1),
//                new Tile('E', 1)
//
//        );
//
//        Move move5 = new Move(board, "4e", tiles);
//        move5.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move5));
//
//        Player p = new HumanPlayer("Dan");
//        TileBag tb = new TileBag();
//        List<Tile> tiles6 = List.of(
//                new Tile('Z', 2)
//        );
//        Move move6 = new Move(board, "d4", tiles6);
//        p.getTileRack().fillRack(tb);
//        System.out.println(p.getTileRack().getTiles());
//        System.out.println(move6.getTiles());
//        System.out.println(moveValidator.tilesPlayedInRack(move6, p));
//
//        Move move = new Move(board, "4d", tiles);
//        move.placeTile();
//        MoveValidator moveValidator = new MoveValidator();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move));
//
//        Move move2 = new Move(board, "8d", tiles);
//        move2.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move2));
//
//        List<Tile> tiles2 = List.of(
//                new Tile('D', 2),
//                new Tile('I', 1),
//                new Tile('I', 1)
//
//
//        );
//        Move move3 = new Move(board, "e5", tiles2);
//        move3.placeTile();
//        board.displayBoard();
//        System.out.println(moveValidator.connectToExistingTiles(board, move3));
//    }
//}


    }