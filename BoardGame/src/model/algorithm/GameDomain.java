package model.algorithm;

/**
 * <h1>GameDomain<h1> <p> 
 * Interface that include all necessary methods for boardGame domain
 */
public interface GameDomain {
	
	/**
	 * <h1> getState <h1> <p>
	 * 
	 * @return the method will return the State from the class is active on
	 */
	
	public State getState();
	
	/**
	 * <h1> getSolution <h1> <p>
	 * the method will get the Solution of the game from the current state 
	 * @param state - the method will get the state of the game 
	 * @param ai - the method will get the algorithm that it can get the solution
	 */
	
	public void getSolution(State state, AI ai);
	
	
	/**
	 * <h1> playerTurn <h1> <p>
	 * 
	 * the method will receive the point that the player wish to play. 
	 * @param row
	 * @param column
	 * @return if the game is over
	 * @throws Exception
	 */
	public int playerTurn(int row, int column) throws Exception; 
	
	/**
	 * <h1> computerTurn <h1> <p>
	 * 
	 * the method will receive the algorithm the computer choose his next move by. 
	 * 
	 * @param ai - algorithm
	 * @return if the game is over
	 * @throws Exception
	 */
	
	public int computerTurn(AI ai); 
	
	
	/**
	 * <h1> setSolution <h1> <p>
	 * set state from the state we've get, possible if we already have a solution
	 * @param state
	 * @return
	 */
	public int setSolution(State state); // set state from the state we've get, possible if we already have a solution
	
	/**
	 *  <h1> getHint <h1> <p>
	 *  the method will get a hint from the computer about the next move.
	 * @param ai
	 * @param depth - Hard Level
	 * @return integer that help the player choose his next move. 
	 */
	
	public int getHint(AI ai, int depth); 
	
	
	/**
	 *  <h1> setDepth <h1> <p>
	 *  the method will set the hard level of the algorithm
	 * @param hardlevel
	 */
	public void setDepth(int hardlevel);
	
	public int getDepth(); // return depth

}
