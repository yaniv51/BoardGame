package view;

import java.util.Observable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 *  * <h1> BasicWindow <h1> <p>
 *  abstract class, implement Runnable - the main loop for the shell in this class.
 */
public abstract class BasicWindow extends Observable implements Runnable {

	Display display;
	Shell shell;
	
	/**
	 * the C'tor of the basic window of the GUI 
	 */
	public BasicWindow() {
		display = new Display();
		shell = new Shell(display);
		
		/*shell.setSize(width , height);*/
		shell.setText("Game");
	}
	
	/**
	 * <h1> initWidgets <h1> <p>
	 *  abstract class that run the Widgets.
	 */
	abstract void initWidgets();
	
	@Override
	public void run() {
		initWidgets();
		
		//open new shell
		shell.open();
		
		//wait for user action
		while(! shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}
	
}
