package pij.game;

import pij.board.Board;
import pij.board.BoardLoader;
import pij.board.Move;
import pij.board.MoveValidator;
import pij.player.ComputerPlayer;
import pij.player.HumanPlayer;
import pij.player.Player;
import pij.tile.Tile;
import pij.tile.TileBag;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 * Main game controller for ScraeBBKle.
 * Handles game setup, turn management, scoring and end game conditions.
 */
public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private TileBag tileBag;
    private MoveValidator moveValidator;
    private Player currentPlayerTurn;
    private String gameType;
    private int numberOfConsectivePasses;
    private boolean isFirstMove;


    /**
     * Constructs a new Game and initialises through user prompts including board selection, player types and game type (open/closed).
     *
     * @throws IOException if board file cannot be read
     */
    public Game() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?\n" +
                "Please enter your choice (l/d):");
        while (this.board == null) {
            char boardType = sc.nextLine().charAt(0);
            if (boardType == 'd') {
                this.board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
            } else if (boardType == 'l') {
                    System.out.println("Please enter the file name of the board:");
                    while (this.board == null) {
                        String fileName = sc.nextLine();
                        try {
                            this.board = BoardLoader.loadFromFile("resources/"+fileName);
                        } catch (IOException ex){
                            System.out.println("File " + fileName + " does not exist. Please enter the file name of the board:");
                        } catch (IllegalArgumentException ex) {
                            System.out.println("This is not a valid file. Please enter the file name of the board:");
                        }
                    }
            } else {
                System.out.println("Invalid choice. Please enter l or d:");
            }
        }
        System.out.println("Is Player 1 a _h_uman player or a _c_omputer player?\n" +
                "Please enter your choice (h/c):");
        while (this.player1 == null || this.player2 == null) {
            char playerType = sc.nextLine().charAt(0);
            if (playerType == 'h') {
                if (this.player1 == null) {
                    this.player1 = new HumanPlayer("Player 1");
                    System.out.println("Is Player 2 a _h_uman player or a _c_omputer player?\n" +
                            "Please enter your choice (h/c):");
                } else {
                    this.player2 = new HumanPlayer("Player 2");
                }
            } else if (playerType == 'c') {
                if (this.player1 == null) {
                    this.player1 = new ComputerPlayer("Player 1");
                    System.out.println("Is Player 2 a _h_uman player or a _c_omputer player?\n" +
                            "Please enter your choice (h/c):");
                } else {
                    this.player2 = new ComputerPlayer("Player 2");
                }
            } else {
                System.out.println("Invalid choice. Please enter h or c:");
            }
        }
        System.out.println("Would you like to play an _o_pen or a _c_losed game?\n" +
                "Please enter your choice (o/c):");
        while (this.gameType == null) {
            char open_or_closed = sc.nextLine().charAt(0);
            if (open_or_closed == 'o') {
                this.gameType = "open";
            } else if (open_or_closed == 'c') {
                this.gameType = "closed";
            } else {
                System.out.println("Invalid choice. Please enter o or c:");
            }
        }
        this.tileBag = new TileBag();
        this.moveValidator = new MoveValidator();
        this.currentPlayerTurn = this.player1;
        this.numberOfConsectivePasses = 0;
        this.player1.getTileRack().fillRack(this.tileBag);
        this.player2.getTileRack().fillRack(this.tileBag);
        this.isFirstMove = true;
    }

    /**
     * Checks if the game has ended.
     * This happens when the tile bag is empty and a player has no tiles,
     * or four consecutive passes have occurred.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return (this.tileBag.getRemainingTileCount() == 0 && (this.player1.getTileRack().getRackCount() == 0 || this.player2.getTileRack().getRackCount() == 0))
                || this.numberOfConsectivePasses == 4;
    }

    /**
     * Runs the main game loop.
     * Alternates turns between players until the game ends.
     * Then calculates final scores and announces the winner.
     */
    public void play() {
        while (!isGameOver()) {

            board.displayBoard();
            System.out.println();
            System.out.println("Start position: " + board.getStartingPosition());
            Player opponentPlayer;
            if (this.currentPlayerTurn == this.player1) {
                opponentPlayer = this.player2;
            } else {
                opponentPlayer = this.player1;
            }
            if(this.gameType.equals("open")) {
                System.out.println("OPEN GAME: " + opponentPlayer.getPlayerName() + " 's tiles:");
                System.out.println("OPEN GAME: " + opponentPlayer.getTileRack().getTiles());
            }
            System.out.println("It's your turn, " + this.currentPlayerTurn.getPlayerName() + "! Your tiles:");
            System.out.println(this.currentPlayerTurn.getTileRack().getTiles());

            Move input = this.currentPlayerTurn.makeMove(this.board);

            while(!input.getIsPass() && !moveValidator.validateMove(input, this.board, this.isFirstMove,this.currentPlayerTurn)) {
                input = this.currentPlayerTurn.makeMove(this.board);
            }


            if (input.getIsPass()) {
                this.numberOfConsectivePasses += 1;
            } else {
                Scoring score = new Scoring();
                this.currentPlayerTurn.addScore(score.totalScore(input,this.board));
                input.placeTile();
                currentPlayerTurn.getTileRack().removeTiles(input.getTiles());
                if(isFirstMove) {
                    isFirstMove = false;
                }
                currentPlayerTurn.getTileRack().fillRack(this.tileBag);
                this.numberOfConsectivePasses = 0;
            }
            System.out.println("Player 1 score: " + this.player1.getScore());
            System.out.println("Player 2 score: " + this.player2.getScore());
            this.currentPlayerTurn = opponentPlayer;

            System.out.println("Number of consecutive passes: " + this.numberOfConsectivePasses);
            System.out.println(this.gameType);

        }

        List<Tile> player1Tiles = player1.getTileRack().getTiles();
        List<Tile> player2Tiles = player2.getTileRack().getTiles();

        for(Tile tile : player1Tiles) {
            int value = tile.getValue();
            player1.addScore(-value);
        }
        for(Tile tile : player2Tiles) {
            int value = tile.getValue();
            player2.addScore(-value);
        }

        System.out.println("Game Over!");
        System.out.println(this.player1.getPlayerName() + " scored " + this.player1.getScore() + " points.");
        System.out.println(this.player2.getPlayerName() + " scored " + this.player2.getScore() + " points.");
        if(this.player1.getScore() > this.player2.getScore()) {
            System.out.println(this.player1.getPlayerName() + " wins!");
        } else if(this.player2.getScore() > this.player1.getScore()) {
            System.out.println(this.player2.getPlayerName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }


    }
    public static void main(String[] args) throws IOException {
        Game game = new Game();


        game.play();

    }
}
