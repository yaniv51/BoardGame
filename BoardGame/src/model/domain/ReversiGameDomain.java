package model.domain;

import model.algorithm.AI;
import model.algorithm.GameDomain;
import model.algorithm.State;

/**
 * <h1>ReversiGameDomain<h1> <p> 
 * Reversi game domain - including method for reversi game
 */
public class ReversiGameDomain implements GameDomain {

	ReversiState game;
	final int size = 8;
	int depth;
	
	//c'tor
	public ReversiGameDomain() {
		game = new ReversiState(size);
	}
	
	//get state
	@Override
	public State getState() {
		return game;
	}

	//set depth from hard level
	@Override
	//TODO: fix hard level(for both reversi and tictactoe
	public void setDepth(int hardlevel) {
		if(hardlevel == 1)
			depth = 2;
		else if(hardlevel == 2)
			depth = 5;
		else if(hardlevel == 3)
			depth = 7;
		else // default
			depth = 3;
	}
	
	//player turn - return if the game is over
	//the state is saved in domain
	@Override
	public int playerTurn(int row, int column) throws Exception {
		
		boolean valid = game.isValid(row, column, game.getTurn());
		if(valid == false)
			throw new Exception("Not a legal move");
		//if is empty - there are no moves
		if(game.getBlackMoves().isEmpty() == true)
			return -1;
		
		game.setCell(row, column, game.getTurn());
		game.setLastMove(row*10+column);
		game.setTurn(2);
		return game.gameOver();
	}

	
	//the state is saved in domain
	//computer turn - return if the game is over
	@Override
	public int computerTurn(AI ai) {
		int move;
		//if is empty - there are no moves
		if(game.getWhiteMoves().isEmpty() == true)
			return -1;
		
		move = ai.firstLayer(game, this.depth);
		game.setCell(move/10, move%10,game.getTurn());
		game.setLastMove(move);
		game.setTurn(1);
		return game.gameOver();
	}

	//return hint for player
	@Override
	public int getHint(AI ai, int depth) {
		int move = ai.firstLayer(game, depth);
		// String hint = new String ("your hint: row "+ Integer.toString(move/10) +" column "+ Integer.toString(move%10));
		return move;
	}
	
	//get depth
	@Override
	public int getDepth() {
		return this.depth;
	}

	//get solution and set state
	@Override
	public int setSolution(State state) {
		game.cloneState((ReversiState)state);
		return game.gameOver();
	}
	
	@Override
	public void getSolution(State state, AI ai) {
		int move;
		state =(ReversiState) state;
		game.cloneState((ReversiState)state);
		if(game.getWhiteMoves().isEmpty() == true)
			return;
		move = ai.firstLayer(game, depth);
		game.setCell(move/10, move%10,game.getTurn());
		game.setLastMove(move);
		game.setTurn(1);
	}
}
