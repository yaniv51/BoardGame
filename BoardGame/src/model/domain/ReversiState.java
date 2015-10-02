package model.domain;

import java.util.ArrayList;
import java.util.HashMap;

import model.algorithm.State;

/**
 * <h1>ReversiState<h1> <p> 
 * Reversi game state, extends state and include all relevant methods and data members for Reversi game
 */
public class ReversiState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1083322379980813294L;
	int blackScore;
	int whiteScore;
	ArrayList<Integer> blackMoves;
	ArrayList<Integer> whiteMoves;
	
	/**
	 * <h1> ReversiState <h1> <p>
	 * 
	 * default c'tor for serializable
	 * 
	 */
	public ReversiState() {
		super();
	}
	
	/**
	 *  <h1> ReversiState <h1> <p>
	 * the C'tor of the class
	 * 
	 * @param size - the size of the board game
	 */
	public ReversiState(int size) {
		super(size);
		//set black
		boardGame[this.getSize()/2][this.getSize()/2-1] = 1;
		boardGame[this.getSize()/2-1][this.getSize()/2] = 1;
		
		//set white
		boardGame[this.getSize()/2-1][this.getSize()/2-1] = 2;
		boardGame[this.getSize()/2][this.getSize()/2] = 2;
		
		//set started score
		blackScore = 2;
		whiteScore = 2;
		
		//set new array list for possible moves
		blackMoves = new ArrayList<Integer>();
		whiteMoves = new ArrayList<Integer>();
		
		this.setTurn(1);
		this.possibleMoves(this.getTurn());
	}
	
	/**
	 *  <h1> ReversiState <h1> <p>
	 * the C'tor of the ReversiState 
	 * 
	 * @param turn 
	 * @param board - the board game 
	 * @param lastMove - the last turn  in the game (witch point played in the last turn) 
	 * @param size - the size of the game board
	 * @param score - the game score 
	 * @param white - white score 
	 * @param black - Black score
	 * @param whiteMoves - white possible moves
	 * @param blackMoves - black possible moves
	 */
	public ReversiState(int turn, int[][] board,int lastMove, int size, int score, int white, int black, ArrayList<Integer> whiteMoves, ArrayList<Integer> blackMoves) {
		super(turn, board, lastMove, size, score);
		whiteScore = white;
		blackScore = black;
		this.whiteMoves = new ArrayList<Integer>();
		this.blackMoves = new ArrayList<Integer>();
		for(Integer num: whiteMoves)
			this.whiteMoves.add(num);
		for(Integer num: blackMoves)
			this.blackMoves.add(num);
	}

	
	@Override
	public int Hfunc(int agent) {
		int myTiles = 0, oppTiles = 0, i, j;

		double tilesScore = 0, cornerScore = 0, cornerMobilityScore = 0;
		double MatrixWeightScore = 0;
		//board weight matrix
		int V[][] = new int[][] {{100, -10, 11, 6, 6, 11, -10, 100}, {-10, -20, 1, 2, 2, 1, -20, -10}, {10, 1, 5, 4, 4, 5, 1, 10}, {6, 2, 4, 2, 2, 1, 2, 6},
				                 {6, 2, 4, 2, 2, 1, 2, 6}, {10, 1, 5, 4, 4, 5, 1, 10} ,{-10, -20, 1, 2, 2, 1, -20, -10}, {100, -10, 11, 6, 6, 11, -10, 100} };
		int opponent;
		if(agent == 1)
			opponent = 2;
		else 
			opponent = 1;
		
		//1) use score to set how many cell you and your opponent have - mobility
		if(agent == 1) {
			opponent = 2;
			myTiles = this.blackScore;
			oppTiles = this.whiteScore;

		}
		else {
			opponent = 1;
			myTiles = this.whiteScore;
			oppTiles = this.blackScore;

		}
		
		
		
		// 2) check and set score with board weight matrix
		for(i=0; i < this.getSize(); i++)
			for(j=0; j < this.getSize(); j++) {
				if(boardGame[i][j] == agent) 
					MatrixWeightScore += V[i][j];
				else if(boardGame[i][j] == opponent) 
					MatrixWeightScore -= V[i][j];	
			}
		
		
		//set tiles score
		if(myTiles > oppTiles)
			tilesScore = (100.0 * myTiles)/(myTiles + oppTiles);
		else if(myTiles < oppTiles)
			tilesScore = -(100.0 * oppTiles)/(myTiles + oppTiles);
			else
				tilesScore = 0;
		

		
		// 3) check corners
		myTiles = 0; 
		oppTiles = 0;
		if(boardGame[0][0] == agent)
			myTiles++;
		else if(boardGame[0][0] == opponent)
			oppTiles++;
		if(boardGame[0][this.getSize()-1] == agent)
			myTiles++;
		else if(boardGame[0][this.getSize()-1] == opponent)
			oppTiles++;
		if(boardGame[this.getSize()-1][0] == agent)
			myTiles++;
		else if(boardGame[this.getSize()-1][0] == opponent)
			oppTiles++;
		if(boardGame[this.getSize()-1][this.getSize()-1] == agent) 
			myTiles++;
		else if(boardGame[this.getSize()-1][this.getSize()-1] == opponent)
			oppTiles++;
		cornerScore = 25 * (myTiles - oppTiles);
		
		// 4)corners mobility
		myTiles = 0; 
		oppTiles = 0;
		if(boardGame[0][0] == 0) {
			if(boardGame[0][1] == agent)
				myTiles++;
			else if(boardGame[0][1] == opponent)
				oppTiles++;
			if(boardGame[1][1] == agent) 
				myTiles++;
			else if(boardGame[1][1] == opponent) 
				oppTiles++;
			if(boardGame[1][0] == agent)
				myTiles++;
			else if(boardGame[1][0] == opponent)
				oppTiles++;
		}
		
		if(boardGame[0][this.getSize()-1] == 0) {
			if(boardGame[0][this.getSize()-2] == agent)
				myTiles++;
			else if(boardGame[0][this.getSize()-2] == opponent)
				oppTiles++;
			if(boardGame[1][this.getSize()-2] == agent)
				myTiles++;
			else if(boardGame[1][this.getSize()-2] == opponent)
				oppTiles++;
			if(boardGame[1][this.getSize()-1] == agent) 
				myTiles++;
			else if(boardGame[1][this.getSize()-1] == opponent)
				oppTiles++;
		}
		
		if(boardGame[this.getSize()-1][0] == 0) {
			if(boardGame[this.getSize()-1][1] == agent)
				myTiles++;
			else if(boardGame[this.getSize()-1][1] == opponent)
				oppTiles++;
			if(boardGame[this.getSize()-2][1] == agent)
				myTiles++;
			else if(boardGame[this.getSize()-2][1] == opponent)
				oppTiles++;
			if(boardGame[this.getSize()-2][0] == agent)
				myTiles++;
			else if(boardGame[this.getSize()-2][0] == opponent) 
				oppTiles++;
		}
		
		if(boardGame[this.getSize()-1][this.getSize()-1] == 0) {
			if(boardGame[this.getSize()-2][this.getSize()-1] == agent) 
				myTiles++;
			else if(boardGame[this.getSize()-2][this.getSize()-1] == opponent)
				oppTiles++;
			if(boardGame[this.getSize()-2][this.getSize()-2] == agent)
				myTiles++;
			else if(boardGame[this.getSize()-2][this.getSize()-2] == opponent) 
				oppTiles++;
			if(boardGame[this.getSize()-1][this.getSize()-2] == agent)
				myTiles++;
			else if(boardGame[this.getSize()-1][this.getSize()-2] == opponent) 
				oppTiles++;
		}
		
		cornerMobilityScore = -12.5 * (myTiles - oppTiles);
		// final weighted score
		double score = (2 * tilesScore) + (160.34 * cornerScore) + (76.4 * cornerMobilityScore)  + (2 * MatrixWeightScore);
		return (int)score;
	}
	
	/**
	 * 
	 * find all the possible moves for the current turn
	 * @param val - the turn in the game 
	 * @return the moves the player can do
	 */
	public int possibleMoves(int val) {
		blackMoves.clear();
		whiteMoves.clear();
		int opponent;
		int x, y;
		if(val == 1)
			opponent = 2;
		else
			opponent = 1;

		int countMoves = 0;
		
		//check every cell
		for(int row = 0; row< this.getSize(); row++)
			for(int column = 0; column < this.getSize(); column ++)
			{
				//found an empty square - we have to check the adjacent 
				if(boardGame[row][column] == 0) {
					//move in each direction  ( from 8 )and check if next square belongs to opponent
					for(int newRow = -1; newRow<=1; newRow++)
						for(int newColumn = -1; newColumn<=1; newColumn++)
						{
							//check if we exceed from array
							if((row == 0 )&&(newRow == -1))
								continue;
							if((column == 0 )&& (newColumn == -1))
								continue;
							if((column == this.getSize() - 1) && (newColumn ==1 ))
								continue;
							if((row == this.getSize() - 1) && (newRow ==1 ))
								continue;
							//if we at the current cell - continue
							if((newRow == 0) && (newColumn == 0))
								continue;
							
							//check every direction of the board - if the next cell at the same direction is opponent cell we have to continue checking

							for(x = row+newRow, y=column+newColumn;(boardGame[x][y] == opponent)&&(x+newRow>-1)&& (x+newRow<this.getSize())&&(y+newColumn>-1)&&(y+newColumn<this.getSize()); x+=newRow, y+=newColumn){
								if(boardGame[x+newRow][y+newColumn] == val)
									//if the next cell at the same direction is our cell - possible move
									if(val == 1) {
										blackMoves.add(row*10+column);
										countMoves++;
									}
									else {
											whiteMoves.add(row*10+column);
											countMoves++;
									}
							}	
						}
				}
			}
		return countMoves;	
	}
	
	
	//generate all possible moves
	@Override
	public HashMap<String, State> getAllPossibleMoves() {
		HashMap<String, State> moves = new HashMap<>();
		int turn, lastMove;
		//check if there is possible moves
		if(this.getTurn() == 1) {
			if(blackMoves.isEmpty() == true)
				return null;
		}
		else //this.getTurn() = 2
			if(whiteMoves.isEmpty() == true)
				return null;
		
		ReversiState tempState = null;
		if(this.getTurn() == 1) {
			turn = 2;
			for( Integer num : blackMoves)
			{
				lastMove = num;
				int row = num/10;
				int column = num%10;
				tempState = new ReversiState(this.getTurn(), this.getBoardGame(), lastMove, this.getSize(), this.getScore(), this.whiteScore, this.blackScore, this.whiteMoves, this.blackMoves);
				tempState.setCell(row, column, this.getTurn());
				tempState.setTurn(turn);
				moves.put(tempState.toString(), tempState);
			}
		}
		else {
			turn = 1;
			for( Integer num : whiteMoves)
			{
				lastMove = num;
				int row = num/10;
				int column = num%10;
				tempState = new ReversiState(this.getTurn(), this.getBoardGame(), lastMove, this.getSize(), this.getScore(), this.whiteScore, this.blackScore, this.whiteMoves, this.blackMoves);
				tempState.setCell(row, column, this.getTurn());
				tempState.setTurn(turn);
				moves.put(tempState.toString(), tempState);
			}
			
		}
		
		
		return moves;
	}

	/**
	 * 
	 * the method get the point and check if move is valid
	 * 
	 * @param row - the row in the game board 
	 * @param column - the column in the game board 
	 * @param turn 
	 * @return if the move is valid
	 */
	public boolean isValid(int row, int column, int turn) {
		//check if cell is empty
		if(this.boardGame[row][column] != 0)
			return false;
		else { //empty cell
			int cell = row*10+column;
			if(turn  == 1)
			{
				for(Integer num: blackMoves )
				{
					if(num == cell)
						return true;
				}
			}
			else //turn =2
			{
				for(Integer num: whiteMoves)
					if(num == cell)
						return true;
			}
		}
		return false;
		
	}
	
	//check if the game is over - return 1,2 for winner, 0 drew and -1 if there are more moves
	@Override
	public int gameOver() {
		//check if the board is full
		if(blackScore + whiteScore == this.getSize()*this.getSize()) {
			if(blackScore > whiteScore)
				return 1;
			else if(blackScore < whiteScore)
				return 2;
				else // draw
					return 0;
			
		}
		//there are more moves
		return -1;
	}
	
	//print board
	@Override
	public void print() {
		int size = this.getSize();
		for( int i=0; i< size ; i++)
		{
			for(int j=0; j<size; j++)
			{
				
				if((i == 0 ) && (j == 0) )
					System.out.println("  0 1 2 3 4 5 6 7 ");
				if(j == 0)
					System.out.print(i+" ");
				if(boardGame[i][j] == 1 )
					System.out.print("B ");
				else if(boardGame[i][j] == 2 )
					System.out.print("W ");
				else System.out.print("- ");
			}
			System.out.println();
		}
		System.out.println("Black Score: " +blackScore +"\nWhite score: " +whiteScore );
		
		//print moves
		if(this.getTurn() == 1) {
			System.out.print("your moves: ");
			for(Integer i:blackMoves )
				System.out.print("("+i/10+"," + i%10+") ");
		}
		else
			for(Integer i:whiteMoves )
			System.out.print(i+",");
		System.out.println();

	}
	
	/**
	 * 
	 * check vertical after input point and change score and board if necessary
	 * 
	 * @param row
	 * @param column
	 * @param val - turn
	 */
	public void checkNchangeVertical(int row, int column, int val) {
		
		int countDown = 0;
		int countUp = 0;
		int indexUp = 0;
		int indexDown = 0;
		int rowDown, rowUp;
		boolean foundDown, foundUp;
		boolean zeroDown,zeroUp;

		//checking vertical
		rowDown = row +1;
		rowUp = row - 1;
		foundDown  =false;
		foundUp = false;
		zeroUp = false;
		zeroDown = false;
		while (((rowDown < this.getSize()) || (rowUp > -1)) && ((zeroUp == false)|| (zeroDown==false))    ) {
			//down
			if(rowDown < this.getSize())
				if((zeroDown == false)&&(foundDown == false))
					if(boardGame[rowDown][column] != 0) {
						if( boardGame[rowDown][column] == val) {
							foundDown = true;
							indexDown = rowDown;
						}
						if(foundDown == false)
							if(boardGame[rowDown][column] != val)
								countDown ++;
					}
					else zeroDown = true;
				//up
			if(rowUp > -1)
				if((zeroUp == false)&&(foundUp == false))
					if(boardGame[rowUp][column] != 0) {
						if( boardGame[rowUp][column] == val) {
							foundUp = true;
							indexUp = rowUp;
						}
						if(foundUp == false)
							if(boardGame[rowUp][column] != val)
								countUp ++;
					}
					else zeroUp = true;
			rowUp--;
			rowDown++;
		}

		
		//change value if necessary - down
		rowDown= row+1;
		if((countDown > 0 ) && (foundDown == true))
			while( rowDown < indexDown ) {
				boardGame[rowDown][column] = val;
				rowDown++;
			}
		
		//update score if necessary - down
		if(foundDown == true)
			if(val == 1) {
				blackScore+=countDown;
				whiteScore-=countDown;
			}
			else
			{
				blackScore-=countDown;
				whiteScore+=countDown;
			}

		//change value if necessary - up
		rowUp= row-1;
		if((countUp > 0 ) && (foundUp == true))
			while( rowUp > indexUp ) {
				boardGame[rowUp][column] = val;
				rowUp--;	
			}
		
		//update score if necessary - up
		if(foundUp== true)	
			if(val == 1) {
				blackScore+=countUp;
				whiteScore-=countUp;
			}
			else
			{
				blackScore-=countUp;
				whiteScore+=countUp;
			}
			
	}
	
	/**
	 * 
	 * check horizontal after input point and change score and board if necessary
	 * 
	 * @param row
	 * @param column
	 * @param val
	 */
	public void checkNchangeHorizontal(int row, int column, int val) {
		int countLeft = 0;
		int countRight = 0;
		int columnLeft, columnRight;
		boolean foundLeft,foundRight;
		int leftIndex = 0, rightIndex = 0;
		boolean zeroLeft, zeroRight;
		
		//checking horizontal
		foundRight = false;
		columnLeft = column -1;
		columnRight = column +1;
		foundLeft = false;
		zeroLeft = false;
		zeroRight = false;
		while ( ( ( columnRight < this.getSize()) || ( columnLeft > 0) )  && (( zeroLeft == false) || (zeroRight == false)) ) {
			//right side
			if(columnRight < this.getSize())
				if((zeroRight == false)&&(foundRight == false))
					if(boardGame[row][columnRight] != 0) {
						if( boardGame[row][columnRight] == val) {
							foundRight = true;
							rightIndex = columnRight;
			
						}
						if(foundRight == false)
							if(boardGame[row][columnRight] != val)
								countRight ++;
					}
					else zeroRight = true;
			//left side
			if(columnLeft > 0)
				if((zeroLeft == false)&&(foundLeft == false))
					if(boardGame[row][columnLeft] != 0) {
						if( boardGame[row][columnLeft] == val) {
							foundLeft = true;
							leftIndex = columnLeft;
						}
						if(foundLeft == false)
							if(boardGame[row][columnLeft] != val)
								countLeft ++;
					}
					else zeroLeft = true;
			columnLeft--;
			columnRight++;
		}
		
		
		//change value if necessary - right
		columnRight = column + 1;
		if((countRight > 0 ) && (foundRight == true))
		while( columnRight < rightIndex ) {
			boardGame[row][columnRight] = val;
			columnRight++;
		}	
		
		//update score if necessary - right
		if(foundRight == true)
			if(val == 1) {
				blackScore+=countRight;
				whiteScore-=countRight;
			}
			else
			{
				blackScore-=countRight;
				whiteScore+=countRight;
			}
		
		//change value if necessary - left
		columnLeft= column-1;
		if((countLeft > 0 ) && (foundLeft == true))
			while( columnLeft > leftIndex ) {
				boardGame[row][columnLeft] = val;
				columnLeft--;		
			}
		//update score if necessary			
		if(foundLeft == true)
			if(val == 1) {
				blackScore+=countLeft;
				whiteScore-=countLeft;
			}
			else
			{
				blackScore-=countLeft;
				whiteScore+=countLeft;
			}	
	}
	
	/**
	 * 
	 * check oblique after input point and change score and board if necessary
	 * 
	 * @param row
	 * @param column
	 * @param val
	 */
	public void checkNchangeOblique(int row, int column, int val) {
		int row1, columnLeft,columnRight;
		boolean foundLeft, foundRight;
		int indexLeft = 0,indexRight = 0;
		int countLeft,countRight;
		boolean zeroRight,zeroLeft;
		
		//checking oblique up
		foundRight = false;
		foundLeft = false;
		zeroRight = false;
		zeroLeft = false;
		row1 = row -1;
		columnRight = column+1;
		columnLeft = column -1;
		countLeft = 0;
		countRight = 0;
		while((( row1 > -1) && (( columnRight< this.getSize()) || (columnLeft > -1) )) && ((foundRight == false) || (foundLeft == false) )&& ((zeroLeft == false) || (zeroRight == false) ))
		{
			//right side - up
			if(columnRight < this.getSize()) 
				if((foundRight == false)&&(zeroRight == false))
					if(boardGame[row1][columnRight] != 0) {
						if(boardGame[row1][columnRight] == val) {
							foundRight = true;
							indexRight = columnRight;
						}
						if(foundRight == false)
							if(boardGame[row1][columnRight] != val)
								countRight ++;
					}
					else zeroRight = true;
			//left side - up
			if(columnLeft > -1) 
				if((foundLeft == false)&&(zeroLeft == false))
					if(boardGame[row1][columnLeft] != 0) {
						if(boardGame[row1][columnLeft] == val) {
							foundLeft = true;
							indexLeft = columnLeft;
						}
						if(foundLeft == false)
							if(boardGame[row1][columnLeft] != val)
								countLeft++;
					
					}
					else zeroLeft = true;
			columnLeft--;
			columnRight++;
			row1--;
		}	
		
		//change value - right side
		row1 = row -1;
		columnRight=column +1;
		while((row1>-1) && (columnRight < indexRight)&&(foundRight == true)) {
			boardGame[row1][columnRight] = val;
			row1--;
			columnRight++;
		}
		
		//change value = left side
		row1 = row -1;
		columnLeft=column -1;
		while((row1>-1) && (columnLeft > indexLeft)&&(foundLeft == true)) {
			boardGame[row1][columnLeft] = val;
			row1--;
			columnLeft--;
		}
		
		if(foundRight == true)
			if(val == 1) {
				blackScore+= countRight;
				whiteScore-=countRight;
			}
			else
			{
				blackScore-=countRight;
				whiteScore+=countRight;
			}
		
		if(foundLeft == true)
			if(val == 1) {
				blackScore+= countLeft;
				whiteScore-=countLeft;
			}
			else
			{
				blackScore-=countLeft;
				whiteScore+=countLeft;
			}
		
		
		//checking oblique down
		countRight = 0;
		countLeft = 0;
		foundRight = false;
		foundLeft = false;
		columnRight = column +1;
		columnLeft = column -1;
		row1 = row +1 ;
		zeroRight = false;
		zeroLeft = false;
		while((( row1 < this.getSize() ) && (( columnRight< this.getSize()) || (columnLeft > -1) )) && ((foundRight == false) || (foundLeft == false) ) && ((zeroRight == false) || (zeroLeft == false) ))
		{	
			//right side
			if(columnRight < this.getSize()) 
				if((foundRight == false)&&(zeroRight == false))
					if(boardGame[row1][columnRight] != 0) {
						if(boardGame[row1][columnRight] == val) {
							foundRight = true;
							indexRight = columnRight;
						}
						if(foundRight == false)
							if(boardGame[row1][columnRight] != val)
								countRight ++;
					}
					else zeroRight = true;
			
			//left side
			if(columnLeft > -1) 
				if((foundLeft == false)&&(zeroLeft == false))
					if(boardGame[row1][columnLeft] != 0){
						if(boardGame[row1][columnLeft] == val) {
							foundLeft = true;
							indexLeft = columnLeft;
						}
						if(foundLeft == false)
							if(boardGame[row1][columnLeft] != val)
								countLeft++;
				
					}
					else zeroLeft = true;
			columnLeft--;
			columnRight++;
			row1++;
		}	
		
		//change value -right
		row1 = row +1;
		columnRight=column +1;
		while((row1>-1) && (columnRight < indexRight)&&(foundRight == true) && (zeroRight == false)) {
			boardGame[row1][columnRight] = val;
			row1++;
			columnRight++;
		}
		
		//change value - left
		row1 = row +1;
		columnLeft=column -1;
		while((row1>-1) && (columnLeft > indexLeft)&&(foundLeft == true) && (zeroLeft == false)) {
			boardGame[row1][columnLeft] = val;
			row1++;
			columnLeft--;
		}
		
		//change score
		
		if(foundRight == true)
			if(val == 1) {
				blackScore+= countRight;
				whiteScore-=countRight;
			}
			else
			{
				blackScore-=countRight;
				whiteScore+=countRight;
			}
		
		if(foundLeft == true)
			if(val == 1) {
				blackScore+= countLeft;
				whiteScore-=countLeft;
			}
			else
			{
				blackScore-=countLeft;
				whiteScore+=countLeft;
			}
	}
	
	//get point and set all new changes
	@Override
	public void setCell(int row, int column, int val) {
		int nextTurn;
		boardGame[row][column] = val;
		if(val == 1) {
			blackScore ++;
			nextTurn = 2;
		}
		else {
			whiteScore ++;
			nextTurn = 1;
		}
		//check and change adj. values if necessary
		checkNchangeVertical(row, column, val);
		checkNchangeHorizontal(row, column, val);
		checkNchangeOblique(row, column, val);
		this.possibleMoves(nextTurn);
		

	}

	
	//getters & setters
	public int getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
	}

	public ArrayList<Integer> getBlackMoves() {
		return blackMoves;
	}

	public void setBlackMoves(ArrayList<Integer> blackMoves) {
		this.blackMoves = new ArrayList<Integer>( blackMoves);
	}

	public ArrayList<Integer> getWhiteMoves() {
		return whiteMoves;
	}

	public void setWhiteMoves(ArrayList<Integer> whiteMoves) {
		this.whiteMoves = new ArrayList<Integer>(whiteMoves);
	}

	
	/**
	 * set clone state
	 * 
	 * @param state
	 */
	public void cloneState(ReversiState state) {
		this.setBoardGame(state.getBoardGame());
		this.setTurn(state.getTurn());
		this.setLastMove(state.getLastMove());
		this.setScore(state.getScore());
		this.setBlackMoves(state.getBlackMoves());
		this.setBlackScore(state.getBlackScore());
		this.setWhiteMoves(state.getWhiteMoves());
		this.setWhiteScore(state.getWhiteScore());
		
	}

	
}


