package model.algorithm;

import java.util.HashMap;
/**
 * <h1>MiniMaxAlphaBeta<h1> <p> 
 * Implementation of MiniMaxAlphaBeta pruning cut algorithm
 */
public class MiniMaxAlphaBeta extends CommonAI {
	
	//generate first childer's layer from received state - we want to take the biggest score from one of the children
	@Override
	public int firstLayer(State state, int maxDepth) {
		//generate all next moves from the state that received
		HashMap<String, State> layer = state.getAllPossibleMoves();
		State bestState = null;
		State nextState;
		int depth = 0;
		//using alpha and beta for cutting
		int alpha = -100;
		int beta = 100;
		int lastPlayed;
		//check who was the last player -> we want that the last player will win
		if(state.getTurn() == 2)
			lastPlayed = 2;
		else lastPlayed = 1;
		for(String a : layer.keySet()) {
			nextState =layer.get(a);
			//use minimax algorithm
			nextState.setScore(bestMove(nextState,depth+1,lastPlayed, alpha, beta, maxDepth));
			if(bestState==null)
				bestState = nextState;
			else
				if ((bestState.getScore()< nextState.getScore()))
					bestState = nextState;
		}
		return bestState.getLastMove();
	}
	
	public int bestMove(State state, int depth, int agent, int alpha, int beta, int maxDepth) {
			int winner = state.gameOver();
			int score;
			//if it is a final state or depth = 0 -> return h function
			if((winner != -1 ) ||(depth == maxDepth ))
				return state.Hfunc(agent);
			//get a current state and return all possible moves
			HashMap<String, State> nighbers = state.getAllPossibleMoves();
			//added
			if(nighbers == null)
				return state.Hfunc(agent);
			//end
			
			//check who is the player - always save next player turn
			if(state.getTurn() == agent) {
				//Maximizing player
				//for each children, find the Max score
				for(String a : nighbers.keySet())
				{
					State temp = nighbers.get(a);
					score = bestMove(temp, depth+1, agent, alpha, beta,maxDepth);
					if(score > alpha)
						alpha = score;
					if(beta < alpha)
						break; // beta cut off
				}
				return alpha;
			}
			//minimizing player
				else
				{
					//for each children, find the minimum score
					for(String a : nighbers.keySet())
					{
						State temp = nighbers.get(a);
						score = bestMove(temp, depth+1, agent, alpha, beta,maxDepth);
						if(score < beta)
							beta = score;
						if ((beta <= alpha) )
							break; //alpha cut off
					}
					return beta;
				}
		}
}
