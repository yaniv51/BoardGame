package model.domain;

import java.util.Scanner;

import model.algorithm.AI;
import model.algorithm.GameDomain;
import model.algorithm.State;

/**
 * <h1>TicTacToeGameDomain<h1> <p> 
 * TicTacToe game domain - including method for TicTacToe game
 */
public class TicTacToeGameDomain implements GameDomain {

	TicTacToeState game;
	final int size = 3;
	Scanner in = new Scanner(System.in);
	int depth;

	//c'tor
	public TicTacToeGameDomain() {
		game = new TicTacToeState(size);
	}
	
	//set depth from hard level
	public void setDepth(int hardLevel) {
		if(hardLevel == 1)
			depth = 2;
		else if(hardLevel == 2)
			depth = 5;
		else if(hardLevel == 3)
			depth = 7;
		else // default
			depth = 3;
	
	}
	
	//getState from Domain
 	public State getState() {
		return game;
	}
 	
 	//computer turn - return if the game is over
	public int computerTurn(AI ai) {
		int turn = 2;
		int move;
		
		game.setTurn(turn);
		move = ai.firstLayer(game, this.depth);
		game.setCell(move/10, move%10,turn);
		game.setLastMove(move);
		
		return game.gameOver();
	}
	
	//player turn - return if the game is over
	public int playerTurn(int row, int column) throws Exception {
		int turn;
		boolean validMove = game.validMove(row, column); ;
		if(validMove == false)
			throw new Exception("Not a legal move");
		
		turn = 1;
		game.setTurn(turn);
		game.setCell(row, column,turn);
		game.setLastMove(row*10+column);
		
		return game.gameOver();
	}
	
	//get depth
	@Override
	public int getDepth() {
		return this.depth;
	}

	//get solution and set state
	@Override
	public int setSolution(State state) {	
		game.cloneState((TicTacToeState)state);
		int gameOver = game.gameOver();
		
		return gameOver;
	}

	//return hint for player
	@Override
	public int getHint(AI ai, int depth) {
		int move = ai.firstLayer(game, depth);
		// String hint = new String ("your hint: row "+ Integer.toString(move/10) +" column "+ Integer.toString(move%10));
		return move;
	}

	@Override
	public void getSolution(State state, AI ai) {
		int turn = 2;
		state = (TicTacToeState)state;
		game = new TicTacToeState(state.getTurn(), state.getBoardGame(), game.getLastMove(), state.getSize(), game.getScore());
		game.setTurn(turn);
		int move = ai.firstLayer(game, this.depth);
		game.setCell(move/10, move%10,turn);
		game.setLastMove(move);
	
	}

	
}

