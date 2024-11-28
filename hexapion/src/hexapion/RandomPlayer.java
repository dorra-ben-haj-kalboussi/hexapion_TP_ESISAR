package hexapion;

import java.util.ArrayList;

// random player
public class RandomPlayer extends Player{

	// constructor
	public RandomPlayer(String name, int id, Main m){
		super(name,id,m);
	}
	
	// get the selected action
	public int update(int state, int reward, ArrayList<Integer> possible_actions){

		// random action
		if (possible_actions.size()>0) return possible_actions.get( (int)(Math.random()*possible_actions.size()) );
		else return 0;
	}
	
}
