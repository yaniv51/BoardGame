package model.gameModel;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.algorithm.State;

public interface Model{
	
	void selectDomain(String domainName);
	void selectAlgorithm(String algorithmName);
	void solveDomain() throws IOException, ClassNotFoundException;
	Solution getSolution();
	public void saveGame() throws IOException;
	public void setHardLevel(int depth);
	public void gameManager(int row, int colum) throws Exception;
	public int getGameOver();
	public String getHintString();
	State getState();
	public void loadProperties() throws FileNotFoundException;
	public void connectToServer();
	public void disconnect() throws IOException;
	public void getHint() throws Exception;
	public void loadPropertiesFromFile(String dest) throws FileNotFoundException;

	

}
