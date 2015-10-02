package model.algorithm;

import java.util.HashMap;

/**
 * <h1>MiniMax<h1> <p> 
 * Implementation of MiniMax algorithm
 */
public class MiniMax extends CommonAI {

	//get next states of  first layer - from this layer we'll choose 1 best state
	@Override
	public int firstLayer(State state, int maxDepth) {
		//generate all next moves from the state that received
		HashMap<String, State> layer = state.getAllPossibleMoves();
		State bestState = null;
		State nextState;
		int lastPlayed;
		int depth =0;
		//check who was the last player -> we want that the last player will win
		if(state.getTurn() == 2)
			lastPlayed = 2;
		else lastPlayed = 1;
		for(String a : layer.keySet()) {
			nextState =layer.get(a);
			//use minimax algorithm
			nextState.setScore(bestMove(nextState, depth+1, lastPlayed, maxDepth ));
			if(bestState==null)
				bestState = nextState;
			else
				//check if the returned score bigger that the current biggest score
				if((bestState.getScore()< nextState.getScore())||(bestState.getScore() == nextState.getScore()))
					bestState = nextState;
		}
		return bestState.getLastMove();
	}

	

	
	
	public int bestMove(State state, int depth, int agent, int maxDepth) {
		int winner = state.gameOver();
		int curMax = -2;
		int curMin = 2;
		//if it is a final state or depth = 0 -> return h function
		if((winner != -1 ) ||(depth == maxDepth))
			return state.Hfunc(agent);
		
		//get a current state and return all possible moves
		HashMap<String, State> nighbers = state.getAllPossibleMoves();
		//added
		if(nighbers == null)
			return state.Hfunc(agent);
		//end
		//check who is the player - always save next player turn
		//maximaizing player
		if(state.getTurn() == agent) {			
			//for each children, find the Max score
			for(String a : nighbers.keySet())
			{
				State temp = nighbers.get(a);
				//state.print();
				int score = bestMove(temp, depth+1, agent, maxDepth);
					if(score > curMax)
					curMax = score;
			}
			return curMax;
		}
		//minimizing player
			else
			{
				//for each children, find the min score
				for(String a : nighbers.keySet())
				{
					State temp = nighbers.get(a);
					int score = bestMove(temp, depth+1, agent, maxDepth);
					if(score < curMin)
						curMin = score;
				}
				return curMin;
			}
	}

}


