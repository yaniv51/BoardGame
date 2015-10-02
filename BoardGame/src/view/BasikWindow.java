package view;

import java.util.Observable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasikWindow extends Observable implements Runnable {

	Display display;
	Shell shell;
	
	//c'tor
	public BasikWindow() {
		display = new Display();
		shell = new Shell(display);
		
		/*shell.setSize(width , height);*/
		shell.setText("Game");
	}
	
	//for all bottoms
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
