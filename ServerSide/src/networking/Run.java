package networking;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model_.SolutionManager;
import Properties.MyProperties;
import Properties.PropertiesHandler;
import tasks.RunnableTask;

public class Run {
	public static void main(String[] args) throws IOException {
		//create new solution manager
		SolutionManager sm = SolutionManager.getInstance();
		//read solutions
		try {
			sm.readSolutionFromFile();
		} catch (ClassNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
		
		Scanner scan = new Scanner(System.in);
		boolean error = false;
		MyServer server = null;
		
		//read properties from XML file if exist
		MyProperties properties = new MyProperties();
		PropertiesHandler ph = new PropertiesHandler();
		try {
			properties = ph.readProperties();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
			System.out.println("properties file not found\n");
			//if there is no XML file, give user option to insert new values
			properties.newProperties();
			error = true;
		}
		try{ 
			
		server = new MyServer(properties.getPort(), properties.getNumOfClients());
		
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.out.println("failed to create server, try again. insert new port and open progeam again.");
			int port = scan.nextInt();
			properties.setPort(port);
			ph.writeProperties(properties);
		}
		RunnableTask r = new RunnableTask(server);
		Thread t = new Thread(r);
		//start new server
		t.start();

		String s = new String();
		System.out.println("enter 'stop' to stop server");
		do {
			s = scan.nextLine();
			if(s.equals("stop")) 
				r.stop();	
		}while(!(s.equals("stop")));
		if(error == true)
			ph.writeProperties(properties);
		scan.close();
	
		sm.WriteSolutionToFile();
		
		System.out.println("server down");
		
	}

}