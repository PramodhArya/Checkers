public class King extends Checker implements Movement {
    public King(char symbol, int row, int col) {
        super(symbol, row, col);
    }

    public void move(Checker target, char s, Checker[][] board) {
		board[this.row()][this.col()] = new Checker('-',this.row(),this.col());
		if (s == 'X') board[target.row()][target.col()].setSymbol('%');
		else board[target.row()][target.col()].setSymbol('0');
	}

}