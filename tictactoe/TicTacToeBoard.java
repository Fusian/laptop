package tictactoe;

/*
 * Author: Nathan Griffiths
 * Version: 20110220
 *
 * TicTacToeBoard: Simple representation of the board.
 *
 * Notes:
 *     CS239 KBS Coursework: minimax and alpha-beta pruning. This code
 *     is deliberately minimally commented, written for simplicity,
 *     and does not always follow good coding practise. However, it
 *     should be very easy to follow if you understand minimax
 *     and alpha-beta pruning. Loosely based on code by Ravi Mohan.
 */
public class TicTacToeBoard {

    String[][] board = {{"-", "-", "-"},
			{"-", "-", "-"},
			{"-", "-", "-"}};

    public void markO(int col, int row) {
	board[row][col] = "0";
    }

    public void markX(int col, int row) {
	board[row][col] = "X";
    }

    /*
     * Is there a row containing a line of Xs or Os?
     */
    public boolean rowComplete() {
	boolean result = false;
 	for (int i = 0; i < 3; i++) {
	    if (!board[i][0].equals("-")
		&& board[i][1].equals(board[i][0])
		&& board[i][2].equals(board[i][0])) {
		result = true;
		break;
	    }
	}
	return result;
    }

    /*
     * Is there a column containing a line of Xs or Os?
     */
    public boolean colComplete() {
	boolean result = false;
 	for (int i = 0; i < 3; i++) {
	    if (!board[0][i].equals("-")
		&& board[1][i].equals(board[0][i])
		&& board[2][i].equals(board[0][i])) {
		result = true;
		break;
	    }
	}
	return result;
    }

    /*
     * Is there a diagonal containing a line of Xs or Os?
     */
    public boolean diaComplete() {
	boolean result = false;
	if (!board[0][0].equals("-")
	    && board[1][1].equals(board[0][0])
	    && board[2][2].equals(board[0][0])) {
	    result = true;
	} else if (!board[0][2].equals("-")
		&& board[1][1].equals(board[0][2])
	    && board[2][0].equals(board[0][2])) {
	    result = true;
	}
	return result;	
    }

    /*
     * Does a winning line exist for either player?
     */
    public boolean lineExists() {
	return colComplete() || rowComplete() || diaComplete();
    }

    /*
     * Is every place in the board marked?
     */
    public boolean boardFull() {
	boolean result = true;
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		if (board[i][j].equals("-")) {
		    result = false;
		    break;
		}
	    }
	}
	return result;
    }

    public Object clone() {
	TicTacToeBoard ttt = new TicTacToeBoard();
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		ttt.board[i][j] = board[i][j];
	    }
	}
	return ttt;
    }

    public String toString() {
	StringBuffer strBuff = new StringBuffer();
	for (int i = 0; i < 3; i++) {
	    if (i != 0) strBuff.append("\n");
	    strBuff.append(board[i][0]);
	    for (int j = 1; j < 3; j++)  {
		strBuff.append(" " + board[i][j]);
	    }
	}
	return strBuff.toString();
    }

    public static void main(String[] args) {
	TicTacToeBoard ttt = new TicTacToeBoard();
	System.out.println(ttt);
	ttt.markO(0,0);
	System.out.println(ttt);
	ttt.markX(0,2);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	ttt.markO(1,0);
	ttt.markO(1,1);
	ttt.markO(1,2);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	ttt.markX(1,2);
	ttt.markX(2,2);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	ttt.markO(2,2);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	ttt.markX(1,1);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	System.out.println("full? " + ttt.boardFull());
	ttt.markX(2,0);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	System.out.println("full? " + ttt.boardFull());
	ttt.markX(0,1);
	ttt.markX(2,1);
	System.out.println(ttt);
	System.out.println("row? " + ttt.rowComplete());
	System.out.println("col? " + ttt.colComplete());
	System.out.println("dia? " + ttt.diaComplete());
	System.out.println("full? " + ttt.boardFull());
    }

}