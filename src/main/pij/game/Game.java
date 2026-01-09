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
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private TileBag tileBag;
    private MoveValidator moveValidator;
    private Player currentPlayerTurn;
    private String gameType;
    private int numberOfConsectivePasses;

    public Game() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?\n" +
                "Please enter your choice (l/d):");
        while(this.board == null) {
            char boardType = sc.nextLine().charAt(0);
            if(boardType == 'd') {
                this.board = BoardLoader.loadFromFile("resources/defaultBoard.txt");
            } else if(boardType == 'l') {
                System.out.println("Please enter the file name of the board:");
                String fileName = sc.nextLine();
                this.board = BoardLoader.loadFromFile(fileName);
            } else {
                System.out.println("Invalid choice. Please enter l or d:");
            }
        }
        System.out.println("Is Player 1 a _h_uman player or a _c_omputer player?\n" +
                "Please enter your choice (h/c):");
        while(this.player1 == null || this.player2 == null) {
            char playerType = sc.nextLine().charAt(0);
            if(playerType == 'h') {
                if (this.player1 == null) {
                    this.player1 = new HumanPlayer("Player 1");
                    System.out.println("Is Player 2 a _h_uman player or a _c_omputer player?\n" +
                            "Please enter your choice (h/c):");
                } else {
                    this.player2 =  new HumanPlayer("Player 2");
                }
            } else if(playerType == 'c') {
                if (this.player1 == null) {
                    this.player1 = new ComputerPlayer("Player 1");
                    System.out.println("Is Player 2 a _h_uman player or a _c_omputer player?\n" +
                            "Please enter your choice (h/c):");
                } else {
                    this.player2 =  new ComputerPlayer("Player 2");
                }
            } else {
                System.out.println("Invalid choice. Please enter h or c:");
            }
        }
        System.out.println("Would you like to play an _o_pen or a _c_losed game?\n" +
                "Please enter your choice (o/c):");
        while(this.gameType == null) {
            char open_or_closed = sc.nextLine().charAt(0);
            if(open_or_closed == 'o') {
                this.gameType = "open";
            } else if(open_or_closed == 'c') {
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
    }

}
