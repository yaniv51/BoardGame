package model.gameModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import general.PresenterOrder;
import properties.MyProperties;
import properties.PropertiesHandler;
import model.algorithm.AI;
import model.algorithm.GameDomain;
import model.algorithm.State;
import model.domain.ReversiState;

public class BoardGameModel extends Observable implements Model {

	private AI ai;
	private GameDomain game;
	private algorithmFactory algorithmFactory;
	private GameDomainFactory gameFactory;
	private Solution solution;
	private int hardLevel;
	private int gameOver;
	private int hintGame;
	private Problem prob;
	private InputStream input;
	private OutputStream output;
	private MyProperties properties;
	private PropertiesHandler properHendler;
	private Socket myServer;
	private boolean firstConnection;
	private boolean haveConnection;
	private boolean propertiesLoaded;
	
	private final boolean connectToServer = false;
	
	/**
	 * <h1> BoardGameModel <h1> <p>
	 *  the C'tor will initialization all the parameters of the class
	 * 
	 */
	public BoardGameModel() {
		algorithmFactory = new algorithmFactory();
		gameFactory = new GameDomainFactory();
		this.gameOver = -1;
		solution = new Solution();
		hintGame = -1;
		prob = new Problem();
		properties = new MyProperties();
		properHendler = new PropertiesHandler();
		firstConnection = true;
		haveConnection = false;
		propertiesLoaded = false;
	}


	/**
	 *  <h1> SelectDomain <h1> <p>
	 * the method will generate Domain by the String that it receive<p>
	 * if getStatus() function equal to 1 - it is mean that is the first turn of of the player. 
	 * otherwise, the method will update the Problem method with the changes that occurred in the game. 
	 * @param String domainName - contain a string with the name of the Domain that the user wish to active. <p>  
	 * 
	 */
	@Override
	public void selectDomain(String domainName) {
		game = this.gameFactory.createGame(domainName);
		if(prob.getStatus() != 1)
			prob = new Problem();
		prob.setGameDomain(domainName);
		firstConnection = true;
	}

	/**
	 * <h1> SelectAlgorithm <h1> <p>
	 * the method use algorithm factory for getting the right algoritam.
	 * @param String algorithmName -  will contain the algoritam the user wish to active<p>
	 *
	 */
	@Override
	public void selectAlgorithm(String algorithmName) {
		ai = this.algorithmFactory.createAlgorithm(algorithmName);
		prob.setAi(algorithmName);
	}

	/**
	 *  <h1> GameManager <h1> <p>
	 *   the method will set the player turn and notify about the changes to the presenter.
	 * @param int row & int column - is the point that the user choose to play
	 * 
	 */
	public void gameManager(int row, int column) throws Exception {
		//TODO: implement player turn in new thread
		gameOver = game.playerTurn(row, column);
		
		//if computer has no more moves -> skip turn
		this.setChanged();
		this.notifyObservers(PresenterOrder.ComputerTurn);
	}

	/**
	 *  <h1> solveDomain <h1> <p>
	 * the method will solve the game  
	 * 
	 */	
	@Override
	public void solveDomain() throws IOException, ClassNotFoundException {
		//TODO: implement solve domain in new thread
		if(connectToServer)
		{
			if((firstConnection == true) && (haveConnection == false)) {
				//load properties and connect to server
				System.out.println("connect to server and load properties");
				connectToServer();
			}
		}
		
		if(game instanceof ReversiState)
		{
			ReversiState tempGame = (ReversiState) game.getState();
			//if computer has no more moves -> skip turn
			if(tempGame.getWhiteMoves().isEmpty() == true){
				System.out.println("no white moves");
				this.setChanged();
				this.notifyObservers();
			}
		}
		if(connectToServer)
		{
			//set problem state to be the current state
			prob.setGame(game.getState());
			if(firstConnection == false) 
				prob.setStatus(2);
			// send server a problem
			new ObjectOutputStream(output).writeObject(prob);
			output.flush();
			// wait for a solution
			solution = null;
			solution = (Solution) new ObjectInputStream(input).readObject();
			gameOver = game.setSolution(solution.getCurrentState());
			//disconnect from server
			firstConnection = false;
			// have a solution - notify
		}
		else
		{
			game.computerTurn(ai);
		}
		
		if(game instanceof ReversiState)
		{
			ReversiState tempGame = (ReversiState) game.getState();
			//if player has no more moves -> start automatically computer turn
			if(tempGame.getBlackMoves().isEmpty() == true){
				//TODO: instead of println, should notify to user this message.
				System.out.println("no black moves");
				this.setChanged();
				this.notifyObservers(PresenterOrder.ComputerTurn);
			}
		}
		else
		{
			this.setChanged();
			this.notifyObservers();
		}
	}

	//TODO: check if need to use this method
	/**
	 * <h1> saveGame <h1> <p>
	 * 
	 * the method will be active when user enter "exit". <p>
	 * the method will finish connection with the server.
	 * 
	 */
	// when user enter "exit" -> send to server finish connection
	public void saveGame() throws IOException  {
		//make new problem to be the smallest that possible
		prob = new Problem();
		prob.setStatus(4);
		new ObjectOutputStream(output).writeObject(prob);
		disconnect();
		properHendler.writeProperties(properties);
	}

	/**
	 * 
	 * <h1> getSolution <h1> <p>
	 * 
	 * @return the solution of the game.
	 */
	public Solution getSolution() {
		return solution;
	}

	/**
	 * <h1> setHardLevel<h1> <p>
	 * get hard level and set at game domain
	 * 
	 * @param int depth - Hard level of the game.   
	 */
	public void setHardLevel(int depth) {
		this.hardLevel = depth;
		game.setDepth(this.hardLevel);
		prob.setHardLevel(depth);
	}

	/**
	 * <h1> getGameOver <h1> <p>
	 *  @return the result of the game(winner/draw/more moves)
	 * 
	 */
	public int getGameOver() {
		return this.gameOver;
	}

	/**
	 * <h1> getState <h1> <p>
	 *  @return current game state
	 */
	public State getState() {
		return game.getState();
	}

	/**
	 * <h1> getHint <h1> <p>
	 * the method will get hint(next move) for the user turn.
	 * 
	 */
	@Override
	public void getHint() throws Exception {
		//TODO: implement getHint in new thread
		if(connectToServer)
		{
			// send server client want an hint
			if(haveConnection == false)
				throw new Exception("You are not connected to the server");
			if(firstConnection == true)
				throw new Exception("You are not connected to the server");
			prob.setStatus(3);
			prob.setGame(game.getState());
			
			new ObjectOutputStream(output).writeObject(prob);
			output.flush();
			
			//wait for solution
			hintGame = (int)new ObjectInputStream(input).readObject();
		}
		else
			hintGame = game.getHint(ai,  hardLevel);
		
		//notify presenter
		this.setChanged();
		this.notifyObservers();
	}
	/**
	 * 
	 * return hint and before return change original data member to empty string
	 * 
	 */
	public String getHintString() {
		String hint = new String();
		if (hintGame != -1) {
			hint = new String("Row: "+hintGame/10+", Column: "+hintGame%10);
			hintGame = -1;
		}
		return hint;
	}
	/**
	 *  the method will connect to the server and send to it the "OutOfServer" class.
	 * 
	 */
	
	
	@Override
	public void connectToServer() {
		//connect new server
		try {
			System.out.println(properties.getIp());
			System.out.println(properties.getPort());
			myServer = new Socket(properties.getIp(), properties.getPort());
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		try {
			input = myServer.getInputStream();
			output = myServer.getOutputStream();
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		System.out.println("connected to server");
		PrintWriter outToServer;
		outToServer = new PrintWriter(new OutputStreamWriter(output));
		outToServer.println("BoardGame");
		outToServer.flush();
		haveConnection = true;
	}

	
	/**
	 * 
	 * the method will disconnect from the server
	 * 
	 */
	@Override
	public void disconnect() throws IOException {
		//disconnect after finished
		input.close();
		output.close();
		myServer.close();
	}

	/**
	 *the method will get file destination from view and load XML file
	 */
	
	@Override
	public void loadPropertiesFromFile(String dest) throws FileNotFoundException {
		if(propertiesLoaded == false){
			properties = properHendler.readPropertiesFromFile(dest);
			System.out.println("load sucsesfull");
			propertiesLoaded = true;
			haveConnection = false;
			firstConnection = true;
		}
	}
	
	/**
	 * <h1> LoadProperties <h1> <p>
	 * the method will load the properties from the XML file
	 * 
	 */
	public void loadProperties() throws FileNotFoundException {
		if(propertiesLoaded == false) {
			properties = properHendler.readProperties();
			propertiesLoaded = true;
		}
	}
		
}
