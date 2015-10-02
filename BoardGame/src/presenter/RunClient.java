package presenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import properties.MyProperties;
import properties.PropertiesHandler;
import tasks.TaskRunnable;
import view.MyConsolView;


@SuppressWarnings("unused")
public class RunClient {
	
/*	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		boolean error = false;
		
		//read properties from XML file if exist
		MyProperties properties = new MyProperties();
		PropertiesHandler ph = new PropertiesHandler();
		try {
			properties = ph.readProperties();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			System.out.println("properties fine not found\n");
			//if there is no XML file, give user option to insert new values
			properties.newProperties();
			error = true;
		}
		System.out.println("client side");
		//connect new server
		Socket myServer=new Socket(properties.getIp(), properties.getPort());
		
		System.out.println("connected to server");
		PrintWriter outToServer=new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
		//send server what we want
		outToServer.println("BoardGame");
		outToServer.flush();
		MyModel model = new MyModel(myServer.getInputStream(), myServer.getOutputStream());
		MyConsolView view = new MyConsolView();
		//BoardGameWindow view = new BoardGameWindow();
		Presenter presenter = new Presenter(model, view);

		model.addObserver(presenter);
		view.addObserver(presenter);
		TaskRunnable r = new TaskRunnable(view);
		Thread t = new Thread(r);
		
		//state client
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		//check if failed to open XML file
		if(error == true)
			ph.writeProperties(properties);
		
		//view stopped -> end connection
		outToServer.close();
		myServer.close();
		scan.close();
		System.out.println("bye bye");
	}*/
}

