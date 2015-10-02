package view;

import java.io.IOException;
import model.algorithm.State;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

/**
 * <h1> BoardGameWindow<h1> <p>
 * Class for GUI. Extends BasicWindow and implements View.
 */
public class BoardGameWindow extends BasicWindow implements View {

	String actions[];
	String action;
	BoardCanvas board =null;
	boolean gameAlive;
	boolean reversiGame;
	Text playerScore;
	Text opponentScore;
	Label opponentScoreLable;
	Label playerScoreLable;
	
	public BoardGameWindow() {
		super();
		gameAlive = false;
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(6, false));
		//set view for get update from canvas
		View view = this;
		// create bar menu
		Menu menuBar = new Menu(shell, SWT.BAR);
		// create the File&Help item's drop menu
		Menu fileMenu = new Menu(menuBar);
		Menu helpMenu = new Menu(menuBar);
		// Create all the items in the bar menu
		//File
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);
		//Help
		MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		helpItem.setText("Help");
		helpItem.setMenu(helpMenu);
		// Create all the items in the File dropdown menu
		MenuItem newGameItem = new MenuItem(fileMenu, SWT.NONE);
		newGameItem.setText("New Game");
		MenuItem openPropItem = new MenuItem(fileMenu, SWT.NONE);
		openPropItem.setText("Open Properties");
		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");
		// Create all the items in the help dropdown menu
		MenuItem aboutItem = new MenuItem(helpMenu, SWT.NONE);
		aboutItem.setText("About");
		shell.setMenuBar(menuBar);	
		
		// create domain buttons
		Label domainLabel = new Label(shell, SWT.NONE);
		domainLabel.setText("Choose Game:");
		String domainName[] = { "TicTacToe", "Reversi" };
		Combo domain = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		domain.setText("Choose Game");
		for (int i = 0; i < domainName.length; i++)
			domain.add(domainName[i]);
		domain.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		// create algorithm buttons
		Label algorithmLabel = new Label(shell, SWT.NONE);
		algorithmLabel.setText("Choose Algorithm:");
		String alorithmName[] = { "MiniMax", "MiniMaxAlphaBeta" };
		Combo algorithm = new Combo(shell, SWT.DROP_DOWN| SWT.READ_ONLY);
		algorithm.setText("Choose Algorithm");
		for (int i = 0; i < alorithmName.length; i++)
			algorithm.add(alorithmName[i]);
		algorithm.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		// create hard level buttons
		Label hardLevelLabel = new Label(shell, SWT.NONE);
		hardLevelLabel.setText("Choose Hard Level:");
		Combo hardLevel = new Combo(shell, SWT.DROP_DOWN| SWT.READ_ONLY);
		hardLevel.setText("Choose Hard Level");
		for (int i = 0; i < 3; i++)
			hardLevel.add(Integer.toString(i+1));
		hardLevel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		// play button
		Button play = new Button(shell, SWT.PUSH);
		play.setText("Play");
		play.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	
		//hint button
		Button hint = new Button(shell, SWT.PUSH);
		hint.setText("Get Hint");
		hint.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		//set and hide score label+text ( if it is a ticTacToe game we don't need score
		playerScoreLable = new Label(shell, SWT.NONE);
		playerScoreLable.setText("Your Score: ");
		playerScoreLable.setVisible(false);
		playerScore = new Text(shell, SWT.READ_ONLY);
		playerScore.setVisible(false);
		opponentScoreLable = new Label(shell, SWT.NONE);
		opponentScoreLable.setText("Computer score: ");
		opponentScoreLable.setVisible(false);
		opponentScore = new Text(shell, SWT.READ_ONLY);
		opponentScore.setVisible(false);
		
		
		//      Listeners     ///
		aboutItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox messageBox = new MessageBox(shell, SWT.OK);
				messageBox.setText("About");
				messageBox.setMessage("Written by: Yaniv Israel & Yehonatan Rauf\nFor more information:\nYaniv.israel@gmail.com\njonathanry@gmail.com");
				messageBox.open();

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		openPropItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog properFile = new FileDialog(shell);
				properFile.setText("Open properties");
				String filter[] ={"*.xml"};
				properFile.setFilterExtensions(filter);
				String selected = properFile.open();
				actions = new String[1];
				actions[0] = new String("open "+selected);
				System.out.println(actions[0]);
				try {
					startView();
				} catch (IOException e) {
					MessageBox messageBox = new MessageBox(shell, SWT.OK);
					messageBox.setMessage(e.getMessage());
					messageBox.open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//hint listener
		hint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				actions = new String[1];
				actions[0] = new String("hint");
				try {
					startView();
				} catch (IOException e) {
					getExeptionMessage(e.getMessage());
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//add dispose listener for shell
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				stop();	
			}
		});	
		
		//add listener for newGame selected
		newGameItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				newGamePreference();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stop();
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		play.addSelectionListener(new SelectionListener() {
		  
		  @Override public void widgetSelected(SelectionEvent arg0) {
			  //check if is legal to push play -> if all combos are not empty + game is not alive
			  if((gameAlive == false)&&(! (domain.getText().isEmpty()))&&(! algorithm.getText().isEmpty())&&(! hardLevel.getText().isEmpty())) {
				  //get selected combos and start conversation with the model
				  String s = "SelectGame "+domain.getText()+":"+hardLevel.getText()+",SelectAlgoritm "+algorithm.getText()+",play";
				  actions =  s.split(",");
				  gameAlive = true;
				  if(domain.getText().equals("TicTacToe")) {
					  board = new TicTacToeCanvas(shell, SWT.BORDER, view);
					  shell.setSize(shell.getSize().x+1, shell.getSize().y+1);
					//  board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 6));
				  }
				  else if(domain.getText().equals("Reversi")) {
					  board = new ReversiCanvas(shell, SWT.BORDER, view);
					  shell.setSize(shell.getSize().x+1, shell.getSize().y+1);
				  }
				  try {
					  startView();
				  } catch (IOException e) {
					  getExeptionMessage(e.getMessage());
				  }
			  }
		  }
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub 
			} });
		  
	}

	@Override
	public void startView() throws IOException {
		for(int i = 0; i< actions.length; i++) {
			action = new String(actions[i]);
			this.setChanged();
			this.notifyObservers();
		}
	}

	@Override
	public void displayCurrentState(State game) {
		board.PaintSolution(game);
		board.redraw();
	}


	@Override
	public String getUserAction() {
		return action;
	}


	@Override
	public void gameOver(int gameOver) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK);
		if(gameOver == 1)
			messageBox.setMessage("you are the Winner!");
		else if(gameOver == 2)
			messageBox.setMessage("Computer Win");
		else if (gameOver == 0)
			messageBox.setMessage("It is a Draw!");
		messageBox.setText("GameOver");
		messageBox.open();
		newGamePreference();
	}

	public void stop(){
		action = new String("exit");
		this.setChanged();
		this.notifyObservers();
		shell.dispose();
	}
	

	@Override
	public void setButtonSelection(String action) {
		this.action = new String("insert "+action);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void getExeptionMessage(String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	/**
	 * <h1> newGamePreference <h1><p>
	 * set all necessary preference on GUI for starting new game 
	 */
	public void newGamePreference() {
		board.dispose();
		gameAlive = false;
		opponentScoreLable.setVisible(false);
		playerScoreLable.setVisible(false);
		playerScore.setVisible(false);
		opponentScore.setVisible(false);
	}

	@Override
	public void changeLableText(int playerScores, int computerScores) {
		opponentScoreLable.setVisible(true);
		playerScoreLable.setVisible(true);
		playerScore.setText(Integer.toString(playerScores));
		playerScore.setVisible(true);
		opponentScore.setText(Integer.toString(computerScores));
		opponentScore.setVisible(true);		
	}
	
	@Override
	public void printHint(String hint) {
		MessageBox messageBox = new MessageBox(shell);
		messageBox.setText("Hint");
		messageBox.setMessage(hint);
		messageBox.open();
	}
	
	@Override
	public void doTask() {
		// TODO Auto-generated method stub
	}

	//get user points
	@Override
	public void setUserPoints() {
	}


	@Override
	public void insertProperties() {
		MessageBox messageBox = new MessageBox(shell, SWT.OK);
		messageBox.setMessage("Did not foud properties file. Try relocate or create new");
		messageBox.open();
	}
}
