/*

	Created by Pramodh Aryasomayajula

*/

public class Checker {

	private int row;
	private int col;
	private char symbol;

	public Checker(char symbol, int row, int col) {
		this.row = row;
		this.col = col;
		this.symbol = symbol;
	}

	public void position(int x, int y) {
		this.row = x;
		this.col = y;
	}

	public int row() {
		return row;
	}

	public int col() {
		return col;
	}

	public char symbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public void move(Checker target, char s, Checker[][] board) {
		board[target.row()][target.col()].setSymbol(s);
		board[this.row()][this.col()].setSymbol('-');
	}

	public String toString() {
		return " " + symbol + " ";
	}

}