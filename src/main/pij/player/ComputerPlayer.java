package pij.player;

import pij.board.Board;
import pij.board.Move;
import pij.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String playerName) {
        super(playerName);
    }
    @Override
    public Move makeMove(Board board) {
            return new Move(board, null, null,true);
        }
    }
