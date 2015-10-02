package model.gameModel;

import java.util.HashMap;

import model.algorithm.AI;
import model.algorithm.MiniMax;
import model.algorithm.MiniMaxAlphaBeta;

/**
 * <h1> algorithmFactory <h1> <p>
 *  Use factory pattern to find in O(1) time what to do.
 */
public class algorithmFactory {
	
	private HashMap<String, algorithmCreator> algorithms;
	
	/**
	 * <h1> AlgorithmFactory <h1> <p>
	 *  C'tor that create algorithms in O(n) memory
	 */
	public algorithmFactory() {
		
		algorithms = new HashMap<String, algorithmFactory.algorithmCreator>();
		algorithms.put("MiniMax", new miniMaxCreator());
		algorithms.put("MiniMaxAlphaBeta", new miniMaxAlphaBetaCreator());
	}
	/**
	 * <h1> CreateAlgorithm <h1> <p>
	 *  the method will get a String that contain the name of the algorithm the user wish to create in O(1) time
	 *  @param String algorithmName - contain the Algorithm Name
	 *  @return the method will return the algorithm the user requested 
	 */
	public AI createAlgorithm(String algorithmName) {
		//get algorithm in O(1) time
		algorithmCreator creator = algorithms.get(algorithmName);
		AI ai = null;
		if (creator != null )
			ai = creator.create();
		return ai;
	}

	private interface algorithmCreator {
		public AI create();
	}
	
	/**
	 * <h1> miniMaxCreator <h1> <p>
	 * 
	 * the class will implement the create() method  
	 * MinMax()method create the MinMax Algorithm 
	 * @return MinMax Algorithm
	 */
	
	//creator for minimax algorithm
	private class miniMaxCreator implements algorithmCreator {
		@Override
		public AI create() {
			return new MiniMax();
		}
	}
	/**
	 * <h1> miniMaxAlphaBetaCreator <h1> <p>
	 *  the class will implement the create() method  
	 * miniMaxAlphaBeta() method create the miniMaxAlphaBeta Algorithm 
	 * @return miniMaxAlphaBeta Algorithm
	 */
	//creator for minimaxAlphaBeta algorithm
	private class miniMaxAlphaBetaCreator implements algorithmCreator {
		@Override
		public AI create() {
			return new MiniMaxAlphaBeta();
		}
	}

}
