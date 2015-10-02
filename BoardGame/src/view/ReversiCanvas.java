package view;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import model.algorithm.State;
import model.domain.ReversiState;

/**
 * 
 * <h1> ReversiCanvas<h1> <p>
 * This class extends BoardCanvas class.
 */
public class ReversiCanvas extends BoardCanvas {

	final Image reversiBack = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/RiversiGrid.gif"));
	final Image reversiButton = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/ReversiButton.png"));
//	final Image reversiBack = new Image(null, "res/RiversiGrid.gif");
//	final Image reversiButton = new Image(null, "res/ReversiButton.png");
	ReversiCharacter c;
	HashMap<String, Button> ReversiButton;
	Button[] button;
	String action;
	final View view;
	ReversiCanvas tempCanvas;
	
	
	/**
	 * 
	 * <h1> ReversiCanvas<h1> <p>
	 * 
	 * the C'tor of the ReversiCanvas class
	 * @param parent - the shell of the display
	 * @param style - None
	 * @param v - the view that the C'tor work with.
	 */
	public ReversiCanvas(Composite parent, int style, View v) {
		super(parent, style);
		//set view for update
		 view = v;
		//set character
		c = new ReversiCharacter();
		//set temp canvas data member for buttons
		tempCanvas = this;
		//set layout data
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,6 , 6));
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
	 			int w = getSize().x;
					int h = getSize().y;
					int x = e.x;
					int y = e.y;
					int Xbox = w/8;
					int Ybox = h/8;
					int chosenX = x/Xbox;
					int chosenY = y/Ybox;
					view.setButtonSelection((chosenY+","+chosenX));
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	
	}
	
	@Override
	public void PaintSolution(State game) {
		final boolean playerTurn;
		final ArrayList<Integer> moves;
		ReversiState tempGame = null;
		if( game instanceof ReversiState )
			tempGame = (ReversiState)game;
		int[][] board = tempGame.getBoardGame();
		if( tempGame.getBlackMoves().isEmpty() == false) {
			moves = tempGame.getBlackMoves();
			playerTurn = true;
		}
		else {
			playerTurn = false;
			moves = new ArrayList<Integer>();
		}
		view.changeLableText(tempGame.getBlackScore(),tempGame.getWhiteScore());

		addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				int count = 0;
				e.gc.drawImage(reversiBack, 0, 0, 722, 722, 0, 0, getSize().x,getSize().y);
				int width = getSize().x;
				int height = getSize().y;
				// set for position
				int newWidth = width / (board[0].length);
				int newHeight = height / (board.length);
				// set new width and height for button & image size
				int newWidthSize = width / (board[0].length * 2);
				int newHeightSize = height / (board.length * 2);
				//draw characters and possible moves
				for (int i = 0; i < board[0].length; i++)
					for (int j = 0; j < board.length; j++) {
						if ((playerTurn == true) && (moves.contains(i*10+j)==true) ){
							int num = moves.get(count);
							int x = num / 10;
							int y = num % 10;
							count++;
							e.gc.drawImage(reversiButton, 0, 0, 60, 60, y* newWidth, x * newHeight,newWidthSize * 2, newHeightSize * 2);
							while(count<moves.size()-2) {
								//check if next number is same
								int nextNumber = moves.get(count);
								if(nextNumber == i*10+j)
									count++;
								else
									break;
							}
						}

						if (board[i][j] != 0) {
							if (board[i][j] == 1)
								c.paint(e, 1, newWidthSize * 2,newHeightSize * 2, j * newWidth, i* newHeight);
							else
								// board[i][j] == 2
								c.paint(e, 2, newWidthSize * 2,newHeightSize * 2, j * newWidth, i* newHeight);
						}
						
					}
			}
		});
	}

}
