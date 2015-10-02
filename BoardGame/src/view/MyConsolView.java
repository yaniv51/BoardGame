package view;



import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

import model.algorithm.State;



public class MyConsolView extends Observable implements View {

	private String input;
	Scanner in = new Scanner(System.in);
	boolean stop = false;
	
	
	
	@Override
	public void startView() throws IOException {
		
		System.out.println("enter command:\n1)SelectGame gameDomain:hardLevel\n2)SelectAlgoritm Algorithm\n3)play\n"
				+ "GameDomains: Reversi, TicTacToe\nAlgorithms: MiniMax, MiniMaxAlphaBeta\nHard Level: 1, 2, 3");
		input = "";
		do
		{
			
			System.out.println("please enter command");
			input = in.nextLine();
			if(!(input.equals("exit")))
			{
				//get date -> notify
				this.setChanged();
				this.notifyObservers();
			}
			else {
				System.out.println("exit");
				this.setChanged();
				this.notifyObservers();
				
			}
				
		}while((!(input.equals("exit")))&&(stop == false));
	}
	
	//display current state
	@Override
	public void displayCurrentState(State state) {
		state.print();
	}

	//get user point
	public void setUserPoints() {
		System.out.println("your turn: enter row and column for next turn\nfor example: if you want insert to row 2 column 2 type: insert 2,2\nfor hint insert: hint depth");
		input = in.nextLine();
		this.setChanged();
		this.notifyObservers();
	}

	//return user action
	@Override
	public String getUserAction() {
		return input;
	}
	
	//print if the game is over
	public void gameOver(int gameOver) {
		if(gameOver == 1)
			System.out.println("you are the Winner!");
		else if(gameOver == 2)
			System.out.println("Computer Win");
		else if (gameOver == 0)
			System.out.println("It is a Draw!");
		
	}

	@Override
	public void doTask() {
		try {
			startView();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//print an hint
	@Override
	public void printHint(String hint) {
		System.out.println(hint);
		//this.setUserPoints();
	}

	@Override
	public void setButtonSelection(String action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeLableText(int playerScore, int computerScore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getExeptionMessage(String e) {
		System.out.println(e);
		
	}

	@Override
	public void insertProperties() {
		// TODO Auto-generated method stub
		
	}
}
