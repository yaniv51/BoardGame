package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

public class ReversiCharacter {
	final Image reversiRed = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/redCircle2.png"));
	final Image reversiBlue = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/blueCircle.png"));
	
	//final Image reversiRed = new Image(null, "res/redCircle2.png");
	//final Image reversiBlue = new Image(null, "res/blueCircle.png");
	/**
	 * <h1> ReversiCharacter<h1> <p>
	 *  the C'tor of the ReversiCharacter Class
	 */
	public ReversiCharacter() {
	}
	

/**
 * <h1> paint<h1> <p>
 * Method for paint reversi game character. <p>
 * @param e paint event
 * @param player player to paint - 1 player, 2 computer
 * @param w new width size
 * @param h new height size
 * @param xPos new x position
 * @param yPos new y position
 */
	public void paint(PaintEvent e, int player, int w, int h, int xPos, int yPos) {
		if(player == 1) {
			e.gc.drawImage(reversiBlue, 0, 0, 142, 142,xPos, yPos, w, h);
		}
		else
		{
			e.gc.drawImage(reversiRed, 0, 0, 142, 142,xPos, yPos, w, h);
		}
	}
}
	
