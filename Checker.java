/*

	Created by Pramodh Aryasomayajula

*/

public class Checker extends Point implements Movement{

	private int row;
	private int col;
	private char symbol;

	public Checker(char symbol, int row, int col) {
		super(row, col);
		this.symbol = symbol;
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