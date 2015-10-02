package presenter;


import model.gameModel.*;
import presenter.Presenter;
import view.BoardGameWindow;


public class RunClientWithGUI {

	public static void main(String[] args) {
		
		BoardGameModel model = new BoardGameModel();
		BoardGameWindow view = new BoardGameWindow();
		Presenter presenter = new Presenter(model, view);

		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.run();

		System.out.println("bye bye");
	}
}
