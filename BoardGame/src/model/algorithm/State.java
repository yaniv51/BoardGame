 package model.algorithm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("serial")
/**
 * <h1>State<h1> <p> 
 * Abstract class for board game state.<p>
 * Include all relevant methods and data members for basic board game.
 */
public abstract class State implements Serializable {
	
	protected int[][] boardGame;
	int score;
	protected int size;
	int turn;
	int lastMove;
	
	/**
	 * the C'tor of the State Class
	 * @param int size - means if the C'tor receive 3 so the boradGame will be 3X3. 
	 */
	public State(int size) {
		this.size = size;
		boardGame = new int[this.size][this.size];
		for(int row=0 ; row<this.size; row++)
			for(int column=0; column<this.size; column++)
				boardGame[row][column] = 0;
		score = 0;
		turn = 0;
		lastMove = 0;
	}
	
	/**
	 * default C'tor for serializable implement
	 * 
	 */
	public State() {
		score = 0;
		turn = 0;
		lastMove = 0;
		this.size = 0;
	}
	
	/**
	 * <h1> State<h1> <p>
	 * 
	 * 
	 * @param turn - turn = 1 -> Player turn | turn = 1 -> Computer turn.<p>
	 *  board the boardGame Matrix <p>
	 *  lastMove - the lastmove means the point that choose in the last turn.<p>
	 *  size - the size of matrix <p>
	 *  score - the score of the game <p>
	 */
	public State(int turn, int[][] board,int lastMove, int size, int score) {
		setBoardGame(board);
		this.lastMove = lastMove;
		this.turn = turn;
		this.size = size;
		this.score = score;
	}
	
	/**
	 * 
	 * @return boardGame matrix
	 */
	public int[][] getBoardGame() {
		return boardGame;
	}

	/**
	 * 
	 *  the method will copy the matrix of the gameBoard
	 * @param boardGame
	 */
	public void setBoardGame(int[][] boardGame) {
		int size = boardGame.length;
		this.boardGame = new int[size][size];
		for(int row=0 ; row<size; row++)
			for(int column=0; column<size; column++)
				this.boardGame[row][column] = boardGame[row][column];
	}
	
	/**
	 * Generate new string for hash code
	 * @return hashCode in String  
	 */
	public int hashcode() {
		return toString().hashCode();
	}
	
	/**
	 * heuristic function for a state - check player turn and current state and make a function
	 * @param agent - the player that you want to win.  
	 * 
	 */
	public abstract int Hfunc(int agent);
	
	/**
	 * 
	 * the method will get a current state 
	 * @return all possible moves
	 */
	public abstract HashMap<String, State> getAllPossibleMoves();
	
	/**
	 * the method will convert boardGame to String for putting at hash map
	 * @return boardGame in String
	 */
	public String toString() {
		String a = " ";
		for(int[] is : this.boardGame)
			a+=Arrays.toString(is);
		return a;
	}
	
	/**
	 * 
	 * the method will check if the game is over
	 *  @return gameOver <p>
	 *   if return -1 : the game is not over <p>
	 *   if return 0  : it is a draw<p>
	 *   if return 1  : the player win<p>
	 *   if return 2  : the computer win<p>
	 */
	public abstract int gameOver(); 

	/**
	 * the method will print the boardGame
	 * 
	 */
	public abstract void print ();

	
	//getters & setters
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getLastMove() {
		return lastMove;
	}

	public int getCell(int row,int column)
	{
		return boardGame[row][column];
	}
	/**
	 * the method will set the cell of the matrix board game
	 * 
	 */
	public abstract void setCell(int row, int column, int val);

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setLastMove(int lastMove) {
		this.lastMove = lastMove;
	}

}
