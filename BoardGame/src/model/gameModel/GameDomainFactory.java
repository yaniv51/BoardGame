package model.gameModel;

import java.util.HashMap;

import model.algorithm.GameDomain;
import model.domain.ReversiGameDomain;
import model.domain.TicTacToeGameDomain;

/**
 * <h1> GameDomainFactory <h1> <p>
 *  Use factory pattern to find in O(1) time what to do.
 */
public class GameDomainFactory {
	private HashMap<String, gameDomainCreator> CreateGameDomain;
	
	/**
	 * <h1> AlgorithmFactory <h1> <p>
	 *  C'tor that create GameDomain in O(n) memory
	 */
	public GameDomainFactory() {
		
		CreateGameDomain = new HashMap<String, GameDomainFactory.gameDomainCreator>();
		CreateGameDomain.put("TicTacToe" , new ticTacToeCreator());
		CreateGameDomain.put("Reversi", new CreatorReversi());
	}
	
	/**
	  * <h1> createGame <h1> <p>
	 *  the method will get a String that contain the name of the Game that the user wish to create in O(1) time
	 *  @param String gameDomainName - contain the Game Name
	 *  @return the method will return the GameDomain the user requested 
	 */
	
	public GameDomain createGame(String gameDomainName) {
		//get gameDomain in O(1) time
		gameDomainCreator creator = CreateGameDomain.get(gameDomainName);
		GameDomain game = null;
		if(creator != null )
			game = creator.create();
		return game;
	}
	
	
	private interface gameDomainCreator {
		public GameDomain create(); 
	}
	
	/**
	 * <h1> miniMaxCreator <h1> <p>
	 * 
	 * the class will implement the create() method  
	 * TicTacToeGameDomain() method create the TicTacToe Game  
	 * @return TicTacToe Game
	 */
	private class ticTacToeCreator implements gameDomainCreator {
		@Override
		public GameDomain create() {
			return new TicTacToeGameDomain();
		}
	}
	/**
	 * <h1> CreatorReversi <h1> <p>
	 * 
	 * the class will implement the create() method  
	 * ReversiGameDomain() method create the Reversi Game  
	 * @return Reversi Game
	 */
	private class CreatorReversi implements gameDomainCreator {
		@Override
		public GameDomain create() {
			return new ReversiGameDomain();
		}
	}
	
	
	
}


