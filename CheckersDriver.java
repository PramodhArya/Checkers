/*

	Created by Pramodh Aryasomayajula

*/

import java.util.*;

public class CheckersDriver {

	public static Checker[][] board = new Checker[8][8];
	public static Checker picked;
	public static Checker target;
	public static String[] temp = new String[2];
	public static Scanner in = new Scanner(System.in);
	public static String input;
	public static String letterPos = "ABCDEFGH";
	public static char symbol = 'X';
	public static char s = 'X';
	// public static Checker right;
	// public static Checker left;
	public static ArrayList<Character> xScore = new ArrayList<Character>();
	public static ArrayList<Character> oScore = new ArrayList<Character>();
	public static boolean end = false;
	public static boolean again = false;
	public static boolean turn = true;

	public static void main(String[] args) {
		populateBoard();
		System.out.println("\nSelect a peice to move: [letter][number]\nWhich way do you want to move? right:[right]/[r] left: [left]/[l]");
		while(!end) {
			again = false;
			System.out.println("\n-------- " + symbol + "'s turn! ------");
			printBoard();
			getInput();
			if (again) moveAgain();
			checkBoard();
			turn = !turn;
			if(turn) {
				symbol = 'X';
			} else {
				symbol = 'O';
			}
		}	
	}

	public static void populateBoard() {
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				board[row][col] = new Checker('-',row,col);
				if((row == 0 || row == 2) && (col+1) % 2 == 0) board[row][col] = new Checker('X',row,col);
				else if (row == 1 && (col+1) % 2 == 1) board[row][col] = new Checker('X',row,col);
				else if ((row == 5 || row == 7) && (col+1) % 2 == 1) board[row][col] = new Checker('O',row,col);
				else if (row == 6 && (col+1) % 2 == 0) board[row][col] = new Checker('O',row,col);
			}
		}
	}

	public static void printBoard() {
		int count = 1;
		System.out.println();
		for(Checker[] row: board) {
			System.out.print(count++ + " ");
			for(Checker col: row) {
				System.out.print(col);
			}
			if (count == 2) {
				System.out.print("	X: ");
				for(Character x: xScore) System.out.print(x);
				for(int i = 12; i>xScore.size();i--) System.out.print(" ");
				System.out.print("|");
			} else if (count == 4){
				System.out.print("	O: ");
				for(Character o: oScore) System.out.print(o);
				for(int k = 12; k>oScore.size();k--) System.out.print(" ");
				System.out.print("|");
			}
			System.out.println();
		}
		System.out.println("   A  B  C  D  E  F  G  H\n");
	}

	public static void checkBoard() {
		int peiceCount = 0; 
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if (board[row][col].symbol() == symbol) peiceCount++;
				if (row == 0 && board[row][col].symbol() == 'O')  board[row][col].setSymbol('0');
				if (row == 7 && board[row][col].symbol() == 'X')  board[row][col].setSymbol('%');
			}
		}
		if(peiceCount == 0) {
			gameOver();
		}
	}

	public static void getInput() {
		try {
			int a = 0;
			System.out.println("Select a piece to move: ");
			input = in.nextLine();
			try {
				temp = input.split("");
				picked = board[Integer.parseInt(temp[1])-1][letterPos.indexOf(temp[0].toUpperCase())];
			} catch (NumberFormatException e) {
				error();
				return;
			}
			s = picked.symbol();
			if(picked.symbol() == symbol || (symbol == 'X' && picked.symbol() == '%') || (symbol == 'O' && picked.symbol() == '0')) {
				processInput();
			} else {
				error();
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			error();		
		}
	}

	public static void processInput() {
		System.out.println("Which way do you want to move? ");
				input = in.nextLine().toLowerCase();
				if(input.equals("right") || input.equals("r")) {
					target = right(picked, symbol);
					if(target == null) {
						error();
					} else {
						picked.move(target, s, board);
					}
				} else if (input.equals("left") || input.equals("l")) {
					target = left(picked, symbol);
					if(target == null) {
						error();
					} else {
						picked.move(target, s, board);
					} 
				} else if (input.equals("back right") || input.equals("br")){
					target = bRight(picked, symbol);
					if(target == null) {
						error();
					} else {
						picked.move(target, s, board);
					}
				} else if (input.equals("back left") || input.equals("bl")){
					target = bLeft(picked, symbol);
					if(target == null) {
						error();
					} else {
						picked.move(target, s, board);
					}
				} else {
					error();
				}
	}

	public static Checker right(Checker p, char s) {
		try {
			int row = p.row();
			int col = p.col()+1;
			if(s == 'X') {
				if((board[row+1][col].symbol() == 'O' || board[row+1][col].symbol() == '0')&& board[row+2][col+1].symbol() == '-') {
					xScore.add(board[row+1][col].symbol());
					again = true;
					board[row+1][col].setSymbol('-');
					return board[row+2][col+1];
				} else if(board[row+1][col].symbol() == '-') {
					return board[row+1][col];
				} else {
					return null;
				}
			} else {
				if((board[row-1][col].symbol() == 'X' || board[row-1][col].symbol() == '%')&& board[row-2][col+1].symbol() == '-') {
					oScore.add(board[row-1][col].symbol());
					again = true;
					board[row-1][col].setSymbol('-');
					return board[row-2][col+1];
				} else if(board[row-1][col].symbol() == '-') {
					return board[row-1][col];
				} else {
					return null;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		} 
	}

	public static Checker left(Checker p, char s) {
		try{
			int row = p.row();
			int col = p.col()-1;
			if(s == 'X') {
				if((board[row+1][col].symbol() == 'O' || board[row+1][col].symbol() == '0') && board[row+2][col-1].symbol() == '-') {
					xScore.add(board[row+1][col].symbol());
					again = true;
					board[row+1][col].setSymbol('-');
					return board[row+2][col-1];
				} else if (board[row+1][col].symbol() == '-') {
					return board[row+1][col];
				} else {
					return null;
				}
			} else {
				if((board[row-1][col].symbol() == 'X' || board[row-1][col].symbol() == '%')&& board[row-2][col-1].symbol() == '-') {
					oScore.add(board[row-1][col].symbol());
					again = true;
					board[row-1][col].setSymbol('-');
					return board[row-2][col-1];
				} else if (board[row-1][col].symbol() == '-'){
					return board[row-1][col];
				} else {
					return null;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static Checker bRight(Checker p, char s) {
		try {
			int row = p.row();
			int col = p.col()+1;
			if(s == 'X') {
				if((board[row-1][col].symbol() == 'O' || board[row-1][col].symbol() == '0')&& board[row-2][col+1].symbol() == '-') {
					xScore.add(board[row-1][col].symbol());
					again = true;
					board[row-1][col].setSymbol('-');
					return board[row-2][col+1];
				} else if(board[row-1][col].symbol() == '-') {
					return board[row-1][col];
				} else {
					return null;
				}
			} else {
				if((board[row+1][col].symbol() == 'X' || board[row-1][col].symbol() == '%')&& board[row+2][col+1].symbol() == '-') {
					oScore.add(board[row+1][col].symbol());
					again = true;
					board[row+1][col].setSymbol('-');
					return board[row+2][col+1];
				} else if(board[row+1][col].symbol() == '-') {
					return board[row+1][col];
				} else {
					return null;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		} 
	}

	public static Checker bLeft(Checker p, char s) {
		try{
			int row = p.row();
			int col = p.col()-1;
			if(s == 'X') {
				if((board[row-1][col].symbol() == 'O' || board[row-1][col].symbol() == '0') && board[row-2][col-1].symbol() == '-') {
					xScore.add(board[row-1][col].symbol());
					again = true;
					board[row-1][col].setSymbol('-');
					return board[row-2][col-1];
				} else if (board[row-1][col].symbol() == '-') {
					return board[row-1][col];
				} else {
					return null;
				}
			} else {
				if((board[row+1][col].symbol() == 'X' || board[row+1][col].symbol() == '%')&& board[row+2][col-1].symbol() == '-') {
					oScore.add(board[row+1][col].symbol());
					again = true;
					board[row+1][col].setSymbol('-');
					return board[row+2][col-1];
				} else if (board[row+1][col].symbol() == '-'){
					return board[row+1][col];
				} else {
					return null;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static void moveAgain() {
			int row = target.row();
			int col = target.col();
			printBoard();
			if (symbol == 'X') {
				try{
					if(((board[row+1][col+1].symbol() == 'O' ||  board[row+1][col+1].symbol() == '0') && board[row+2][col+2].symbol() == '-')
						|| ((board[row+1][col-1].symbol() == 'O' || board[row+1][col-1].symbol() == '0') && board[row+2][col-2].symbol() == '-')) {
						System.out.println("Want to move again?");
						input = in.nextLine();
					}
				} catch (IndexOutOfBoundsException e) {
			
				}
			} else {
				try{
					if(((board[row-1][col+1].symbol() == 'X' || board[row-1][col+1].symbol() == '%') && board[row-2][col+2].symbol() == '-') 
						|| ((board[row-1][col-1].symbol() == 'X' || board[row-1][col-1].symbol() == '%') && board[row-2][col-2].symbol() == '-')) {
						System.out.println("Want to move again?");
						input = in.nextLine();
					}
				} catch (IndexOutOfBoundsException e) {

				}
			}
			if(input.toLowerCase().equals("yes") || input.toLowerCase().equals("y")) {
				picked.position(target.row(), target.col());
				processInput();
			}
		
	}

	public static void error() {
		System.out.println("Invalid Input, try again.");
		getInput();
	}

	public static void gameOver() {
		System.out.println("Game Over " + symbol + " wins!\nWant to play again?");
		input = in.nextLine();
		if (input.toLowerCase().equals("yes") || input.toLowerCase().equals("y")) {
			populateBoard();
			symbol = 'X';
			end = false;
			again = false;
			turn = true;
			xScore.clear();
			oScore.clear();

		} else {
			System.out.println("Goodbye!");
			end = true;
		}
		
	}
}