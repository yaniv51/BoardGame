package view;


import java.io.IOException;

import tasks.Task;
import model.algorithm.State;

/**
 * <h1> View<h1> <p>
 * Interface for user view.
 */
public interface View extends Task {

	public void setUserPoints();
	/**
	 * <h1> startView <h1> <p>
	 * start conversation
	 */
	public void startView() throws IOException;
	
	/**
	 * <h1> displayCurrentState<h1> <p>
	 * redraw canvas and show the new state
	 * 
	 */
	public void displayCurrentState(State game);
	
	/**
	 * return user action
	 */
	public String getUserAction();
	
	/**
	 * <h1>gameOver<h1><p>
	 * print if the game is over
	 */
	public void gameOver(int gameOver);
	
	public void printHint(String hint);
	
	/**
	 * <h1> setButtonSelection <h1>
	 * get message from canvas which button selected
	 * 
	 */
	public void setButtonSelection(String action);
	
	/**
	 * <h1> changeLableText <h1><p>
	 * get score of Reversi game add show score on window 
	 */
	public void changeLableText(int playerScore,int computerScore);
	
	public void getExeptionMessage(String message);
	
	/**
	 * insert properties
	 */
	public void insertProperties();
}
