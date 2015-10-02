package properties;

import java.io.Serializable;
import java.util.Scanner;

/**
 * <h1>MyProperties<h1><p>
 * including properties to connect server
 */
public class MyProperties implements Serializable {


	private static final long serialVersionUID = 2227557974022601505L;
	int port;
	String ip;
	Scanner scan = new Scanner(System.in);
	
	/**
	 * <h1> Constructor <h1> <p> 
	 *  default constructor for serializable 
	 */
	public MyProperties() {
		port = 0;
		ip = new String();
	}

	/**
	 * <h1> getPort <h1> <p> 
	 *  @return Server local port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * <h1> setPort <h1> <p> 
	 *  @param port set port for connection
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * <h1> getIp <h1> <p> 
	 *  @return ip of the server
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * <h1> getIp <h1> <p> 
	 *  @param ip of the server and set in properties
	 */
	public void setIp(String ip) {
		this.ip = new String(ip);
	}
	

	public void newProperties() {
		
		String newIp;
		int newPort;
		System.out.println("enter new IP ( by default: 127.0.0.1 )");
		newIp = scan.nextLine();
		setIp(newIp);
		System.out.println("enter new port ( by default 2525 )");
		newPort = scan.nextInt();
		setPort(newPort);
	}
	
	
	
}
