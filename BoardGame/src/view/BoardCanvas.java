package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import model.algorithm.State;

/**
 * <h1> BoardCanvas <h1> <p>
 *Abstract class. All classes that will extend this class will have to implement PaintSolution method.<p>
 *Have all same things between every board game canvas
 */
public abstract class BoardCanvas extends Canvas{
	/**
	 * <h1> BoardCanvas <h1> <p>
	 * @param parent - the shell of the display
	 * @param style - None
	 */
	public BoardCanvas(Composite parent, int style) {
		super(parent, style);
	}
	/**
	 * <h1> PaintSolution <h1>
	 * the method will paint the solution
	 */
	public abstract void PaintSolution(State game);

}
