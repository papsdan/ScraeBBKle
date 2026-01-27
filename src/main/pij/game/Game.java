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
    private int numberOfConsecutivePasses;
    private boolean isFirstMove;


    /**
     * Constructs a new Game and initialises through user prompts including board selection, player types and game type (open/closed).
     *
     * @throws IOException if board file cannot be read
     */
    public Game() throws IOException {
        displayGameHeader();
        Scanner sc = new Scanner(System.in);
        setupBoard(sc);
        setupPlayers(sc);
        setupGameType(sc);

        this.tileBag = new TileBag();
        this.moveValidator = new MoveValidator();
        this.currentPlayerTurn = this.player1;
        this.numberOfConsecutivePasses = 0;
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
                || this.numberOfConsecutivePasses == 4;
    }

    /**
     * Runs the main game loop.
     * Alternates turns between players until the game ends.
     * Then calculates final scores and announces the winner.
     */
    public void play() throws IOException {

        while (!isGameOver()) {
            Player opponentPlayer = getOpponentPlayer();
            displayGameState(opponentPlayer);
            Move input = getValidMove();
            processMove(input);
            this.currentPlayerTurn = opponentPlayer;
        }
        calculateFinalScores();
        announceWinner();
    }

    /**
     * Processes the validated move.
     * If the move is not passed, it updates the scoring and refills the players rack with new tiles from the tile bag (up to 7 max)
     * @param input the move to process
     */
    private void processMove(Move input) {
        if (input.getIsPass()) {
            this.numberOfConsecutivePasses += 1;
            System.out.println("The move is: Pass Move!");
        } else {
            Scoring score = new Scoring();
            this.currentPlayerTurn.addScore(score.totalScore(input,this.board));
            input.placeTile();
            currentPlayerTurn.getTileRack().removeTiles(input.getTiles());
            if(isFirstMove) {
                isFirstMove = false;
            }
            currentPlayerTurn.getTileRack().fillRack(this.tileBag);
            this.numberOfConsecutivePasses = 0;
            System.out.println("The move is: Letters: " + input.getWord() + " at position " +  input.getPosition());
        }
        System.out.println("Player 1 score: " + this.player1.getScore());
        System.out.println("Player 2 score: " + this.player2.getScore());
    }

    /**
     * Displays the current game state including the board, start position, opponents tiles (if open game), and current players tiles.
     * @param opponentPlayer the opponent of the current player
     */
    private void displayGameState(Player opponentPlayer) {
        board.displayBoard();
        System.out.println();
        System.out.println("Start position: " + board.getStartingPosition());
        if(this.gameType.equals("open")) {
            System.out.println("OPEN GAME: " + opponentPlayer.getPlayerName() + "'s tiles:");
            List<Tile> opponentTiles = opponentPlayer.getTileRack().getTiles();
            System.out.println("OPEN GAME: " + String.join(",", opponentTiles.stream().map(Tile::toString).toList()));
        }
        String currentPlayerClass = this.currentPlayerTurn.getClass().getSimpleName();
        if(currentPlayerClass.equals("HumanPlayer")) {
            System.out.println("It's your turn, " + this.currentPlayerTurn.getPlayerName() + "! Your tiles:");
            List<Tile> currentPlayerTiles = currentPlayerTurn.getTileRack().getTiles();
            System.out.println(String.join(",", currentPlayerTiles.stream().map(Tile::toString).toList()));

        } else {
            System.out.println("It's your turn, " + this.currentPlayerTurn.getPlayerName() + "!");
        }
    }
    /**
     * Returns the opponent of the current player.
     *
     * @return the opponent player
     */
    private Player getOpponentPlayer() {
        if (this.currentPlayerTurn == this.player1) {
            return this.player2;
        } else {
            return this.player1;
        }
    }
    /**
     * Calculates and displays the final scores for both players.
     * It subtracts the value of remaining tiles values from each players score.
     */
    private void calculateFinalScores() {
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
        System.out.println();
        System.out.println("Game Over!");
        System.out.println(this.player1.getPlayerName() + " scored " + this.player1.getScore() + " points.");
        System.out.println(this.player2.getPlayerName() + " scored " + this.player2.getScore() + " points.");
    }
    /**
     * Announces the winner based on final scores or will declare a draw if the scores are equal.
     */
    private void announceWinner() {
        if(this.player1.getScore() > this.player2.getScore()) {
            System.out.println(this.player1.getPlayerName() + " wins!");
        } else if(this.player2.getScore() > this.player1.getScore()) {
            System.out.println(this.player2.getPlayerName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
    /**
     * Asks the current player for a move and validates it using the MoveValidator class.
     * Will continue prompting the player until a valid move or a pass is entered.
     * @return a valid move
     * @throws IOException if there is an error reading input
     */
    private Move getValidMove() throws IOException {
        Move input = this.currentPlayerTurn.makeMove(this.board,this.isFirstMove);

        while(!input.getIsPass() && !moveValidator.validateMove(input, this.board, this.isFirstMove,this.currentPlayerTurn,true)) {
            for (Tile tile : input.getTiles()) {
                if(tile.isWildcard()){
                    tile.resetWildcardLetter();
                }
            }
            input = this.currentPlayerTurn.makeMove(this.board,this.isFirstMove);
        }
        return input;
    }
    /**
     * Prompts the user to select a board (default or load custom file).
     * @param sc the scanner for user input
     * @throws IOException if the board file cannot be read
     */
    private void setupBoard(Scanner sc) throws IOException {
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?\n" +
                "Please enter your choice (l/d):");
        while (this.board == null) {
            String boardType = sc.nextLine();
            if (boardType.equals("d")) {
                this.board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
            } else if (boardType.equals("l")) {
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
    }
    /**
     * Prompts the user to select player types for player 1 and player 2 (human or computer).
     * @param sc the scanner for user input
     */
    private void setupPlayers(Scanner sc) {
        System.out.println("Is Player 1 a _h_uman player or a _c_omputer player?\n" +
                "Please enter your choice (h/c):");
        while (this.player1 == null || this.player2 == null) {
            String playerType = sc.nextLine();
            if (playerType.equals("h")) {
                if (this.player1 == null) {
                    this.player1 = new HumanPlayer("Player 1");
                    System.out.println("Is Player 2 a _h_uman player or a _c_omputer player?\n" +
                            "Please enter your choice (h/c):");
                } else {
                    this.player2 = new HumanPlayer("Player 2");
                }
            } else if (playerType.equals("c")) {
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
    }
    /**
     * Prompts the user to select the game type (open or closed).
     * @param sc the scanner for user input
     */
    private void setupGameType(Scanner sc) {
        System.out.println("Would you like to play an _o_pen or a _c_losed game?\n" +
                "Please enter your choice (o/c):");
        while (this.gameType == null) {
            String open_or_closed = sc.nextLine();
            if (open_or_closed.equals("o")) {
                this.gameType = "open";
            } else if (open_or_closed.equals("c")) {
                this.gameType = "closed";
            } else {
                System.out.println("Invalid choice. Please enter o or c:");
            }
        }
    }

    private void displayGameHeader(){
        System.out.println("============                     ============");
        System.out.println("============ S c r a e B B K l e ============");
        System.out.println("============                     ============");
        System.out.println();
    }

}
