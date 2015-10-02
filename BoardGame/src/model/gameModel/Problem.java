package model.gameModel;

import java.io.Serializable;

import model.algorithm.State;

/**
 * <h1> Problem<h1>
 * Is serializable class<p> Including state and description for solve the game state
 */
public class Problem implements Serializable{
	
	
	private static final long serialVersionUID = -2707782663110394514L;
	State gameState;
	String gameDomain;
	String ai;
	int status; // 1. new game, 2.computer turn, 3. hint, 4. exit
	int hardLevel;
	
	public String getGameDomain() {
		return gameDomain;
	}

	/**
	 *  the C'tor of the Problem class 
	 */
	public Problem() {
		status = 1;
		hardLevel = 0;
		gameDomain = new String();
		ai = new String();
		
	}
	
	/**
	 * 
	 * set game domain name
	 * @param gameDomain
	 */
	public void setGameDomain(String gameDomain) {
		this.gameDomain = new String(gameDomain);
	}

	/**
	 * 
	 * get and set algorithm name name
	 * @return algorithm
	 */
	public String getAi() {
		return ai;
	}
	public void setAi(String ai) {
		this.ai = new String (ai);
	}
	
	/**
	 * get and set hard level
	 * @return hardLevel
	 */
	public int getHardLevel() {
		return hardLevel;
	}
	public void setHardLevel(int hardLevel) {
		this.hardLevel = hardLevel;
	}
	
	/**
	 * get and set game state
	 * @return gameState
	 */
	public State getGame() {
		return gameState;
	}
	public void setGame(State game) {
		this.gameState = game;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
