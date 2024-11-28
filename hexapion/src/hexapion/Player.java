package hexapion;

import java.util.ArrayList;

// base player class
public class Player {

	public String name;		// player name
	public int id;			// player number
	
	protected Main main;	// pointer to main board
	
	// constructor
	public Player(String name, int id, Main m){
		this.name=name;
		this.id=id;
		this.main=m;
	}
	
	// agent initialization
	public void initialize(){
	}
	
	// get selected action
	public int update(int state, int reward, ArrayList<Integer> actions){
		return -1;
	}
	
	// update agent's values
	public void learn(int state, int reward){
	}
	
	// save agent's parameters
	public void save(){
	}
}
