package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

public class TicTacToeGameCharcter {
	
	final Image xImage = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/tttX.png"));
	final Image oImage = new Image(null, this.getClass().getClassLoader().getResourceAsStream("view/res/tttO.png"));
	
	//final Image xImage = new Image(null, "res/tttX.png");
	//final Image oImage = new Image(null, "res/tttO.png");

	/**
	 * the method will print the X Image and the O Image
	 * @param e - PaintEvent
	 * @param player - turn 
	 * @param w - Width
	 * @param h - Height
	 * @param xPos - X position
	 * @param yPos - Y position
	 */
	public void paint(PaintEvent e, int player, int w, int h, int xPos, int yPos) {
		if (player == 1)
			e.gc.drawImage(xImage, 0, 0, 193, 193,xPos, yPos, w, h);
		else //player==2
			e.gc.drawImage(oImage, 0, 0, 193, 193,xPos, yPos, w, h);
	}
}
