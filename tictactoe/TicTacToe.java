package tictactoe;

/*
 * Author: Nathan Griffiths
 * Version: 20110220
 *
 * TicTacToe: The main class implementing minimax and alpha-beta
 * pruning.
 *
 * Notes:
 *     CS239 KBS Coursework: minimax and alpha-beta pruning. This code
 *     is deliberately minimally commented, written for simplicity,
 *     and does not always follow good coding practise. However, it
 *     should be very easy to follow if you understand minimax
 *     and alpha-beta pruning. Loosely based on code by Ravi Mohan.
 */

import java.util.*;

public class TicTacToe {

    GameState gameState;      // Current game state
    int totalNodesExpanded;   // Total number of nodes expanded in
			      // search so far
    int levelNodesExpanded;   // Number of nodes expanded to explore this
			      // level of the tree (i.e. all
			      // non-pruned successors of the current
			      // state) 
    int alpha;                // Value of best choice found so far for
			      // Max
    int beta;                 // Value of best choice found so far for
			      // Min 

    /*
     * Simple constructor that initializes current player and the
     * moves set to be all possible moves.
     */
    public TicTacToe() {
	gameState = new GameState();
	ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		moves.add(new Coordinate(i,j));
	    }
	}
	gameState.player = "X";
	gameState.moves = moves;
	totalNodesExpanded = 0;
	levelNodesExpanded = 0;
    }

    /*
     * Return a list of the states reachable in one move from the
     * current state.
     */
    public ArrayList getSuccessorStates(GameState state) {
	ArrayList<GameState> result = new ArrayList<GameState>();

	for (int i = 0; i < state.moves.size(); i++) {
	    Coordinate c = (Coordinate) state.moves.get(i);
	    GameState s = makeMove(state, c);
	    s.moveMade = c;
	    result.add(s);
	}
	return result;
    }

    /*
     * Perform a move to "c" from the given "state" (checking that the
     * move is valid in the current state) 
     */
    public GameState makeMove(GameState state, Coordinate c) {
	GameState result = null;
	ArrayList newMoves = (ArrayList) state.moves.clone();
	if (newMoves.contains(c)) {
	    newMoves.remove(c);
	    result = new GameState();
	    result.moves = newMoves;
	    TicTacToeBoard newBoard = (TicTacToeBoard) state.board.clone();
	    if (state.player == "X") {
		newBoard.markX(c.x, c.y);
		result.player = "O";
	    } else {
		newBoard.markO(c.x, c.y);
		result.player = "X";
	    }
	    result.board = newBoard;
	} else {
	    System.out.println("ERROR: attempt to make invalid move");
	}
	return result;
    }
    
    public int utilityFunction (GameState g) {//returns utility of current state
      if (g.board.boardFull() && !g.board.lineExists()) {//board full - draw
	return 0;
      }
      else if (g.player.equals(gameState.player)) {//if current player is to make next move, means previous, winning, move was made by opponent
	return -1;
      }
      else {//means current player made winning move to get to state. 
	return 1;
      }
    }
    /*
     * Make the next move, determined using minimax.
     * Print out information about the search progression.
     */
    public int expandMove (GameState g) {
	  ArrayList<GameState> moves;
	  GameState nextMove;
	  int util;//hold util value of explored move
	  int mmval = 0;//holds final return value
          int maxmval = -2; // holds best value for max
          int minmval = 2; //holds minimum value for min
	  if (g.board.lineExists()|| g.board.boardFull()) {//checks if one of the players has won or board full
	    return utilityFunction (g);
	  }
	  else {//board still has moves to make
	  moves = getSuccessorStates (g); //gets successor states for current move considered
	  for (int i = 0; i < moves.size(); i++) {//step through moves
	      nextMove = (GameState) moves.get(i);//retrieves next successor node
	      levelNodesExpanded++;//increases level nodes expanded
	      util = expandMove (nextMove);
	      if (g.player.equals(gameState.player)) {//means we're looking at the max utility
		if (util > maxmval) {//checks if new utility is better then best so far
		  maxmval = util;
                  mmval = maxmval;
		}
	      }
	      else {//looking at min utility 
		if (util < minmval) {//checks if new utility is worse then worst so far
		  minmval = util;
                  mmval = minmval;
		}
	      }
            }
	  return mmval;
          }
    }
    
    public void makeMiniMaxMove() {
	totalNodesExpanded += levelNodesExpanded;
	levelNodesExpanded = 0;
	GameState nextMove;//stores next game state to be explored
	GameState choosenMove = null;//stores current best move
	int util;//hold util value of explored move
	int mmval = -2;//hold best value 
	ArrayList<GameState> moves = getSuccessorStates(gameState); //get initial list of possible moves
	
	for (int i = 0; i < moves.size(); i++) {
	  nextMove = (GameState) moves.get(i);
	  levelNodesExpanded++;
	  util = expandMove (nextMove);
          //System.out.println("Move considering:" + nextMove.moveMade);
          System.out.println (util);
	  if (util > mmval) {//checks if new utility is better then best so far
		  mmval = util;
                  //System.out.println("ENTERING LOOP");
		  choosenMove= nextMove;//sets chosen move to current move being expanded
	  }
	}  
	// Print how many nodes were expanded to find this move
	System.out.println("Nodes considered: " + levelNodesExpanded);

	// Print the move itself - YOU SHOULD FILL THIS IN TOO
	System.out.println("Move chosen: " + choosenMove.moveMade);
	// E.g.:
	// System.out.println("Move chosen: " + nextState.moveMade);
	gameState = makeMove (gameState, choosenMove.moveMade);//sets game state to new move
    }

    /*
     * Make the next move, determined using minimax with alpha-beta
     * pruning.
     * Print out information about the search progression.
     */
    public int expandAlphaBetaMove (GameState g, int alpha, int beta) {
		ArrayList<GameState> moves;
		GameState nextMove;
		int util;//hold util value of explored move
		if (g.board.lineExists()|| g.board.boardFull()) {//checks if one of the players has won or board full
		  return utilityFunction (g);
		}
		else {//board still has moves to make
			moves = getSuccessorStates (g); //gets successor states for current move considered
			if (g.player.equals(gameState.player)) {
				for (int i = 0; i < moves.size(); i++) {//step through moves
					nextMove = (GameState) moves.get(i);//retrieves next successor node
					levelNodesExpanded++;//increases level nodes expanded
					util = expandAlphaBetaMove (nextMove, alpha, beta);
					if (util > alpha) {//checks if new utility is better then best so far
						alpha = util;
					}
					if (beta <= alpha){
						//System.out.println ("BREAK IT DOWN");
						break;
					}
				}
				return alpha;
			}
			else {
				for (int i = 0; i < moves.size(); i++) {//step through moves
					nextMove = (GameState) moves.get(i);//retrieves next successor node
					levelNodesExpanded++;//increases level nodes expanded
					util = expandAlphaBetaMove (nextMove, alpha, beta);
					if (util < beta) {//checks if new utility is worse then worst so far
						beta = util;
					}
					if (beta <= alpha){
						break;
					}
				}              
				return beta;
			}
		}
    }
    public void makeAlphaBetaMove() {
        totalNodesExpanded += levelNodesExpanded;
		levelNodesExpanded = 0;
		GameState nextMove;//stores next game state to be explored
		GameState choosenMove = null;//stores current best move
		int util;//hold util value of explored move
        int alpha = -2; //holds max value
        int beta = 2; // holds min value
		ArrayList<GameState> moves = getSuccessorStates(gameState); //get initial list of possible moves
	
		for (int i = 0; i < moves.size(); i++) {
		  nextMove = (GameState) moves.get(i);
		  levelNodesExpanded++;
		  util = expandAlphaBetaMove (nextMove, alpha, beta);
			  //System.out.println("Move considering:" + nextMove.moveMade);
			  System.out.println (util);
		  if (util > alpha) {//checks if new utility is better then best so far
			  alpha = util;
			  choosenMove= nextMove;//sets chosen move to current move being expanded
		  }
		}  
		// Print how many nodes were expanded to find this move
		System.out.println("Nodes considered: " + levelNodesExpanded);

		// Print the move itself - YOU SHOULD FILL THIS IN TOO
		System.out.println("Move chosen: " + choosenMove.moveMade);
		// E.g.:
		// System.out.println("Move chosen: " + nextState.moveMade);
		gameState = makeMove (gameState, choosenMove.moveMade);//sets game state to new move
    }

    public static void main(String[] args) {

	/********************************************************
	 * The following code is just to illustrate how to use the
	 * board. Uncomment it to play around. However, IT SHOULD BE
	 * COMMENTED OUT IN YOUR SUBMISSION.
	 ********************************************************/
	/*
	// Create a new board, print it, print the possiblemoves, and
	// show what states could be acheived
	TicTacToe board1 = new TicTacToe();
	System.out.println("board:\n" + board1.gameState.board);
	System.out.println("moves: " + board1.gameState.moves);

	ArrayList possMoves = board1.getSuccessorStates(board1.gameState);
	for (int i = 0; i < possMoves.size(); i++) {
	    System.out.println(((GameState) possMoves.get(i)).moveMade);
	    System.out.println(((GameState) possMoves.get(i)).board);
	}
	*/

	/********************************************************
	 * The following code is to illustrate how your submission
	 * will be used (although other tests will also be
	 * performed). Until you have implemented makeMiniMaxMove and
	 * makeAlphaBetaMoves, it clearly will not work. However,
	 * once you've written these methods it should produce output
	 * of the form shown at the end of this file.
	 ********************************************************/

	// Use standard minimax on an empty board
	System.out.println("--- Standard minimax ---");
	TicTacToe board1 = new TicTacToe();
	System.out.println("board:\n" + board1.gameState.board + "\n");
	while (!(board1.gameState.board.lineExists() ||
		 board1.gameState.board.boardFull())) {
	    System.out.println("Current player: " + board1.gameState.player);
	    System.out.println("Possible moves: " + board1.gameState.moves);
	    board1.makeMiniMaxMove();
	    System.out.println("board:\n" + board1.gameState.board + "\n");
	}
	System.out.println("Total nodes expanded: " 
			   + board1.totalNodesExpanded);

	System.out.println("\n\n\n");
        
	// Use minimax with alpha-beta pruning on an empty board
	System.out.println("--- alpha-beta minimax ---");
	TicTacToe board2 = new TicTacToe();
	System.out.println("board:\n" + board2.gameState.board);
	while (!(board2.gameState.board.lineExists() ||
		 board2.gameState.board.boardFull())) {
	    System.out.println("Current player: " + board2.gameState.player);
	    System.out.println("Possible moves: " + board2.gameState.moves);
	    board2.makeAlphaBetaMove();
	    System.out.println("board:\n" + board2.gameState.board +"\n");
	}
	System.out.println("Total nodes expanded: " 
			   + board2.totalNodesExpanded);
    }
}

/******************************************************************
 * Your output for both standad minimax and alpha-beta minimax should
 * be of the following form: 
 ******************************************************************/
/*
$ java TicTacToe
--- Standard minimax ---
board:
- - -
- - -
- - -

Current player: X
Possible moves: [(0,0), (0,1), (0,2), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]
Nodes considered: ???
Move chosen: (1,1)
board:
- - -
- X -
- - -

Current player: O
Possible moves: [(0,0), (0,1), (0,2), (1,0), (1,2), (2,0), (2,1), (2,2)]
Nodes considered: ???
Move chosen: (0,0)
board:
0 - -
- X -
- - -

etc...

Total nodes expanded: 613647
 */
