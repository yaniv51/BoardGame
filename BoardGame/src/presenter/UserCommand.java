package presenter;

import java.io.IOException;
import java.util.HashMap;

import model.gameModel.*;


/**
 * <h1> UserCommand <h1> <p> 
 * Class to handle with user command.<p>
 */
public class UserCommand {
	
	private HashMap<String, Command> userCommands;
	
	//c'tor
	public UserCommand() {
		userCommands = new HashMap<String, UserCommand.Command>();
		userCommands.put("SelectGame", new SelectDomainCommand());
		userCommands.put("SelectAlgoritm", new SelectAlgorithmCommand());
		userCommands.put("insert", new UserSetActionCommand());
		userCommands.put("hint", new getHintCommand());
		userCommands.put("exit", new DisconnectCommand());
		userCommands.put("open", new OpenPropertiesCommand());
	}

	/**
	 * <h1> getCommands <h1> <p> 
	 * 
	 * the method will get the command of the user by String.
	 * @param gameModel 
	 * @param select - the kind of command the user wish to use
	 * @param selection - the detail about the game we need them to run the game.
	 * @throws Exception
	 */
	
	public void getCommands(Model gameModel, String select, String selection) throws Exception {
		
		Command command = userCommands.get(select);
		
		if(command == null)
			throw new Exception("not a legal command");
		if (selection == null) // impossible if enter play/exit
			command.doCommand(gameModel, select);
		else
			command.doCommand(gameModel, selection);
	}

	/**
	 * command interface
	 * 
	 *
	 */
	private interface Command {

		void doCommand(Model gameModel, String select) throws Exception;

	}

	/**
	 * <h1> SelectDomainCommand <h1> <p> 
	 * 
	 * the class will select the game domain command and the hard level of the game.
	 *
	 */
	private class SelectDomainCommand implements Command {

		public void doCommand(Model gameModel, String select) {
			String[] arr = select.split(":");

			int depth = Integer.parseInt(arr[1]);
			gameModel.selectDomain(arr[0]);
			gameModel.setHardLevel(depth);

		}

	}
	/**
	 * <h1> SelectDomainCommand <h1> <p> 
	 * 
	 * the class will select the algorithm command.
	 *
	 */
	
	private class SelectAlgorithmCommand implements Command {
		public void doCommand(Model gameModel, String select) {
			gameModel.selectAlgorithm(select);
		}
	}

	/**
	 *<h1> UserSetActionCommand <h1> <p> 
	 * 
	 * when the user wish to choose a point in the game, the class will receive the request and active the the game manager method
	 * 
	 *
	 */
	private class UserSetActionCommand implements Command {
		@Override
		public void doCommand(Model gameModel, String select) throws Exception {
			String[] points = select.split(",");
			int row = Integer.parseInt(points[0]);
			int column = Integer.parseInt(points[1]);
			gameModel.gameManager(row, column);
		}
	}
	
	/**
	 * <h1> DisconnectCommand <h1> <p> 
	 * the class will close the game when the user push the exit button
	 *
	 */
	private class DisconnectCommand implements Command {

		@Override
		public void doCommand(Model gameModel, String select) throws IOException {
				gameModel.saveGame();
		}
	}

	/**
	 * <h1> getHintCommand <h1> <p> 
	 * the class will generate a hint by the user request
	 *
	 */
	private class getHintCommand implements Command {

		@Override
		public void doCommand(Model gameModel, String select) throws Exception {
			gameModel.getHint();
		}
	}
	/**
	 * <h1> OpenPropertiesCommand <h1> <p> 
	 * the class will load the Properties of the sever.
	 *
	 */
	private class OpenPropertiesCommand implements Command {

		@Override
		public void doCommand(Model gameModel, String select) throws Exception {
			gameModel.loadPropertiesFromFile(select);
		}
	}


}
