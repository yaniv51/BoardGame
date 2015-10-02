package model.domain;

import java.util.HashMap;
import java.util.Random;

import model.algorithm.State;

/**
 * <h1>TicTacToeState<h1> <p> 
 * TicTacTOe game state, extends state and include all relevant methods and data members for TicTacTOe game
 */
public class TicTacToeState extends State {

	private static final long serialVersionUID = -7647010730076072159L;

	/**
	 * <h1> TicTacToeState<h1> <p>
	 * C'tor of the TicTacToeState class
	 * 
	 */
	public TicTacToeState() {
		super();
	}
	/**
	 * <h1> TicTacToeState<h1> <p>
	 * C'tor of the TicTacToeState class
	 * @param int size - the size of the game board 
	 */
	public TicTacToeState(int size) {
		super(size);
	}
	
	/**
	 * <h1> TicTacToeState<h1> <p>
	 * 
	 * 
	 * @param turn - turn = 1 -> Player turn | turn = 1 -> Computer turn.<p>
	 *  board the boardGame Matrix <p>
	 *  lastMove - the lastmove means the point that choose in the last turn.<p>
	 *  size - the size of matrix <p>
	 *  score - the score of the game <p>
	 */
	public TicTacToeState(int turn, int[][] board,int lastMove, int size, int score) {
		super(turn, board, lastMove, size, score);
	}
	
	//heuristic function
	public int Hfunc(int agent) {
		//have to separate - if we want agent 1 or agent 2 to win
		int win = gameOver();
		int opponent;
		if(agent == 1)
			opponent = 2;
		else
			opponent = 1;
		if( win == agent)
			return (200);

		else if(win == opponent )
			return (-200);
		
		int score = 0;
		int c1=0,c2=0,c3=0,c4=0;
		
		
		
		for(int i=0; i<this.getSize();i++) {

			for(int j=0; j<this.getSize(); j++){
				//count how many x\o in row
				if(boardGame[i][j] == agent)
					c1++;
				if(boardGame[i][j] == opponent )
					c2++;
				//count columns
				if(boardGame[j][i] == agent)
					c3++;
				if (boardGame[j][i] == opponent)
					c4++;
			}
			
			switch (c1) {
			case 1: score+=1;
					break;
			case 2: score+=10;
					break;
			case 3: score+=100;
					break;
			}
			
			switch (c3) {
			case 1: score+=1;
					break;
			case 2: score+=10;
					break;
			case 3: score+=100;
					break;
			}
			
			switch (c2) {
			case 1: score-=1;
					break;
			case 2: score-=10;
					break;
			case 3: score-=100;
					break;
			}
			switch (c4) {
			case 1: score-=1;
					break;
			case 2: score-=10;
					break;
			case 3: score-=100;
					break;
			}
			c1 = 0;
			c2 = 0;
			c3 = 0;
			c4 = 0;
		}
		
	
		if(boardGame[0][0] == agent)
			c1++;
		if(boardGame[0][0] == opponent)
			c2++;
		if(boardGame[1][1] == agent)
			c1++;
		if(boardGame[1][1] == opponent)
			c2++;
		if(boardGame[2][2] == agent)
			c1++;
		if(boardGame[2][2] == opponent)
			c2++;
		
		if(boardGame[0][2] == agent)
			c3++;
		if(boardGame[0][2] == opponent)
			c4++;
		if(boardGame[1][1] == agent)
			c3++;
		if(boardGame[1][1] == opponent)
			c4++;
		if(boardGame[2][0] == agent)
			c3++;
		if(boardGame[2][0] == opponent)
			c4++;
		
		switch (c1) {
		case 1: score+=1;
				break;
		case 2: score+=10;
				break;
		case 3: score+=100;
				break;
		}
		
		switch (c2) {
		case 1: score-=1;
				break;
		case 2: score-=10;
				break;
		case 3: score-=100;
				break;
		}
		switch (c4) {
		case 1: score-=1;
				break;
		case 2: score-=10;
				break;
		case 3: score-=100;
				break;
		}
		switch (c3) {
		case 1: score+=1;
				break;
		case 2: score+=10;
				break;
		case 3: score+=100;
				break;
		}
		
		return score;
		}

	//get all possible moves from current state
	@Override
	public HashMap<String, State> getAllPossibleMoves() {
		HashMap<String, State> moves = new HashMap<>();
		int turn;
		int lastMove;
		TicTacToeState tempState = null;
			//prepare data member for next player turn
			if ( this.getTurn() == 1)
				turn = 2;
			else 
				turn=1;
			for(int i=0 ; i<this.size ; i++)
				for(int j=0; j<this.size; j++)
				{
					if(getCell(i, j) == 0 ) {
						lastMove = (i*10) +j;
						tempState = new TicTacToeState( this.getTurn() , this.getBoardGame(), lastMove, this.getSize(), this.getScore());
						tempState.setCell(i, j, tempState.getTurn());
						//set who is the next player that should play
						tempState.setTurn(turn);
						moves.put(tempState.toString(), tempState);
					}
				}
			return moves;
	}

	@Override
	//check if there is a winner - return 1,2 for win, 0 for a drawn, -1 if there are more moves
	public int gameOver() {
		int win;
		win = this.rowWinner();
		if(win != 0)
			return win;
		win = this.columnWinner();
		if(win!=0)
			return win;
		win = this.obliqueWinner();
		if(win!=0)
			return win;
		
		int fullCell = 0;
		for(int i =0; i<this.getSize(); i++) {
			if(fullCell>0)
				break;
			for(int j=0; j<this.getSize(); j++)
				if(boardGame[i][j] == 0)
				{
					fullCell++;
					return -1;
				}
		}
		return 0;
		
	}
	
	/**
	 * <h1> rowWinner <h1> <p>
	 *  the method will check if there is a winner from rows
	 * 
	 * @return
	 */
	public int rowWinner() {
		int counter1 = 0;
		int counter2 = 0;
		int row = 0;
		int column = 0;
		//checking rows
		for(row = 0; row <this.getSize(); row++) {
			for(column =0; column<this.getSize()-1; column++)
			{
				if( (boardGame[row][column] == boardGame[row][column+1]) && (boardGame[row][column]!=0) )
					if(boardGame[row][column] == 1)
						counter1++;
					else
						counter2++;
				if(counter1 == (this.getSize() - 1 ))
					return 1;
				if(counter2 == 2)
					return 2;
			}
			counter1 = 0;
			counter2 = 0;
		}
		return 0;
	}
	
	/**
	 * <h1> rowColumn <h1> <p>
	 *  the method will check if there is a winner from columns
	 * 
	 * @return
	 */
	public int columnWinner() {
		int row = 0;
		int column = 0;
		int counter1 = 0;
		int counter2 = 0;
		//checking columns
		for(column = 0; column <this.getSize(); column++) {
			for(row =0; row<this.getSize()-1; row++)
			{
				if( (boardGame[row][column] == boardGame[row+1][column]) && (boardGame[row][column]!=0) )
					if(boardGame[row][column] == 1)
						counter1++;
					else
						counter2++;
				if(counter1 == (this.getSize() - 1 ))
					return 1;
				if(counter2 == 2)
					return 2;
			}
			counter1 = 0;
			counter2 = 0;
		}
		return 0;
	}
	
	/**
	 * <h1> obliqueWinner <h1> <p>
	 *  the method will check if there is a winner from oblique
	 * 
	 * @return
	 */
	public int obliqueWinner() {
		if   ((this.boardGame[0][0] == this.boardGame[1][1]) && (this.boardGame[1][1] == this.boardGame[2][2])&& (this.boardGame[0][0]!= 0)) {
			if ( this.boardGame[0][0] == 1 )
				return 1;
			else return 2;
		
		}
		if   ((this.boardGame[0][2] == this.boardGame[1][1]) && (this.boardGame[1][1] == this.boardGame[2][0])&& (this.boardGame[0][2]!= 0)) {
			if ( this.boardGame[0][2] == 1 )
				return 1;
			else return 2;
		}
		
		
		return 0;
	}
	
	//get row and column and set cell
	@Override
	public void setCell(int row, int column, int val)
	{
		if (val == 1)
			boardGame[row][column] = 1;
		else boardGame[row][column] = 2;
	}

	//print current state of board
	@Override
	public void print() {
		int size = this.getSize();
		for( int i=0; i< size ; i++)
		{
			for(int j=0; j<size; j++)
			{
				if(boardGame[i][j] == 1 )
					System.out.print("X");
				else if(boardGame[i][j] == 2 )
					System.out.print("O");
				else System.out.print("-");
			}
			System.out.println();
		}

	}
	/**
	 * <h1> generateRandomCell <h1> <p> 
	 * the method will generate Random Cell from the first turn 
	 * 
	 */
	
	
	public void generateRandomCell()
	{
		Random rand = new Random();
		int x = rand.nextInt(this.getSize());
		int y = rand.nextInt(this.getSize());
		this.setCell(x, y, this.getTurn());
	}

	
	/**
	 * 
	 * the method get the point and check if move is valid
	 * 
	 * @param row - the row in the game board 
	 * @param column - the column in the game board 
	 * @return if the move is valid - true 
	 * else false 
	 */
	public boolean validMove(int row, int column)
	{
		if(( row > 2) || (column > 2) )
			return false;
		if(boardGame[row][column] != 0)
			return false;
		return true;
	}

	/**
	 * set clone state
	 * 
	 * @param state
	 */
	public void cloneState(TicTacToeState state)
	{
		this.setBoardGame(state.getBoardGame());
		this.setTurn(state.getTurn());
		this.setLastMove(state.getLastMove());
		this.setScore(state.getScore());
	}

}