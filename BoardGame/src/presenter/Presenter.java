package presenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import general.PresenterOrder;
import model.gameModel.Model;
import view.View;

/**
 * <h1>Presenter<h1> <p>
 * Connect between the model and the view.
 */
public class Presenter implements Observer {

	private Model model;
	private View ui;
	private UserCommand commands;

	/**
	 * <h1> Presenter <h1> <p>
	 * the C'tor of the Presenter 
	 * 
	 * @param model - object of the class BoardGameModel
	 * @param view - object of the class BoardGameWindow  
	 */
	public Presenter(Model model, View view) {
		this.model = model;
		this.ui = view;
		commands = new UserCommand();
	}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 == model) {
			//check if player ask for a hint
			String hint = model.getHintString();
			if (hint.isEmpty() == false)
				ui.printHint(hint);
			else { // if not asked for hint
				int gameOver = model.getGameOver();
				// print state
				ui.displayCurrentState(model.getState());
				// if game is over -> print game over
				if (gameOver != -1) {
					ui.gameOver(gameOver);
				} 
				else 
					if(arg1 != null)
						if(arg1 == PresenterOrder.ComputerTurn)
							try {
								model.solveDomain();
							} catch (ClassNotFoundException | IOException e) {
								ui.getExeptionMessage(e.getMessage());
							}
						else 
							ui.getExeptionMessage((String)arg1);
					else // if not get user action
					ui.setUserPoints(); 
				}
			}

		 else if (arg0 == ui) {
			String action = ui.getUserAction();
			String arr[] = action.split(" ");
			// start play - player first -> get player action
			if (arr[0].equals("play")) {
				try {
					model.loadProperties();
				} catch (FileNotFoundException e) {
					ui.insertProperties();
				}
				ui.displayCurrentState(model.getState());
				ui.setUserPoints();
			} else {
				// when we want start the game the command is play -> only 1
				// String will create
				try {
				if (arr.length > 1)
					commands.getCommands(model, arr[0], arr[1]);
				else
					commands.getCommands(model, arr[0], null);
				} catch (Exception e) {
					ui.getExeptionMessage(e.getMessage());

				}
				
			}
		}
	}
}
