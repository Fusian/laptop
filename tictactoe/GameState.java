package tictactoe;

/*
 * Author: Nathan Griffiths
 * Version: 20110220
 *
 * GameState: Represents a node (game state) for the search.
 *
 * Notes:
 *     CS239 KBS Coursework: minimax and alpha-beta pruning. This code
 *     is deliberately minimally commented, written for simplicity,
 *     and does not always follow good coding practise. However, it
 *     should be very easy to follow if you understand minimax
 *     and alpha-beta pruning. Loosely based on code by Ravi Mohan.
 */

import java.util.*;

public class GameState {

    String player;          // Who is next to move
    TicTacToeBoard board;   // Current board state
    ArrayList moves;        // The set of moves possible
    Coordinate moveMade;    // The move that lead to the current state
    GameState next;         // The next state

    public GameState() {
	board = new TicTacToeBoard();
    }
    
}