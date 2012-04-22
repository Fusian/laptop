package tictactoe;

/*
 * Author: Nathan Griffiths
 * Version: 20110220
 *
 * Coordinate: Represents a simple 2-dimensional coordinate.
 *
 * Notes:
 *     CS239 KBS Coursework: minimax and alpha-beta pruning. This code
 *     is deliberately minimally commented, written for simplicity,
 *     and does not always follow good coding practise. However, it
 *     should be very easy to follow if you understand minimax
 *     and alpha-beta pruning. Loosely based on code by Ravi Mohan.
 */

public class Coordinate {
    int x, y;

    public Coordinate(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public boolean equals(Object o) {
	Coordinate c = (Coordinate) o;
	return ((this.x == c.x) && (this.y == c.y));
    }

    public String toString() {
	return "(" + x + "," + y + ")";
    }
}