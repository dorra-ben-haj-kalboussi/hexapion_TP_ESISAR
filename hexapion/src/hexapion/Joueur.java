package hexapion;

import java.util.ArrayList;

// user interface
public class Joueur extends Player{

	// constructor
	public Joueur(String name, int id, Main m) {
		super(name,id,m);
	}
	
	// get selected action
	public int update(int state, int reward, ArrayList<Integer> actions){
		System.out.println("Player "+name+" turn");
		
		// wait for an action from user
		while (main.display.panel.played==-1){
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		// get action from interface
		int ret=main.display.panel.played;
		main.display.panel.played=-1;
		
		System.out.println(ret);
		
		return ret;
	}
	
}
