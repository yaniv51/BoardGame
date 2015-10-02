package view;

import model.algorithm.State;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * <h1> TicTacToeCanvas<h1> <p>
 * This class extends BoardCanvas class.
 */
public class TicTacToeCanvas extends BoardCanvas {
	//final Image tttBack = new Image(null, "res/tttBackRound.png");
	final Image tttBack = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/tttBackRound.png"));
	
	TicTacToeGameCharcter c;
	Button[][] button;
	String action;
	
	/**
	 * <h1> TicTacToeCanvas<h1> <p>
	 *  the C'tor of the TicTacToeCanvas Class
	 */
	
	public TicTacToeCanvas(Composite parent, int style, View v) {
		super(parent, style);
		//set view for update when button pushed
		final View view = v;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,6 , 6));
		c = new TicTacToeGameCharcter();
		
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
		 			int w = getSize().x;
						int h = getSize().y;
						int x = e.x;
						int y = e.y;
						int Xbox = w/3;
						int Ybox = h/3;
						int chosenX = x/Xbox;
						int chosenY = y/Ybox;
						view.setButtonSelection(chosenY+","+chosenX);
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
		int board[][] = game.getBoardGame();
		//add paint listener for each movement of window
		  addPaintListener(new PaintListener() {
			  
		  @Override public void paintControl(PaintEvent e) {
		  
		  int currentWidth = getSize().x; 
		  int currentHeight = getSize().y; 
		  //set for position
		  int newWidthPos = currentWidth /( board[0].length*3);
		  int newHieghtPos = currentHeight /  (board.length*3);
		  
		  int newWidthSize = currentWidth /( board[0].length*2);
		  int newHieghtSize = currentHeight /  (board.length*2);
		  
		  //draw background 
		  e.gc.drawImage(tttBack, 0, 0, 900, 900, 0, 0, currentWidth, currentHeight);
		  int plusWidth,plusHiegh;
		  //paint characters
		  for (int i = 0; i < board[0].length; i++)
			  for (int j = 0; j < board.length; j++) { 
				  	
				  if(i == 0)
					  plusHiegh = 20;
				  else if(i== 2)
					  plusHiegh = -30;
					  else
						  plusHiegh = 20;
				  if(j == 0)
					  plusWidth = 30;
				  else if(j== 2)
					  plusWidth = -30;
					  else
						  plusWidth = 0;
					  if (board[i][j] == 1) 
						  c.paint(e, 1, newWidthSize, newHieghtSize,(int)(j*newWidthPos*3.7)+plusWidth,(int)(i*newHieghtPos*3.5)+plusHiegh); 
					  else if (board[i][j]==2) 
						  c.paint(e, 2, newWidthSize, newHieghtSize, (int)(j*newWidthPos*3.7)+plusWidth, (int)(i*newHieghtPos*3.5)+plusHiegh);
					 
				  }
			  } 
		  
		  });
		  redraw();
	}
}
