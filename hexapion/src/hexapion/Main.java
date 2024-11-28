package hexapion;

public class Main {

	
	public Board board;		// pointer to game board
	
	public boolean gameover=false;
	
	public int player=1;	// current player
	
	public Player player1;	// the two opponents
	public Player player2;
	
	public int reward1=0;	// collect reward during a move
	public int reward2=0;
	
	// main display
	public DisplayFrame display;
	public boolean pause=true;
	public boolean tempo=true;
	
	// delay for temporization (in ms)
	private int delay=100;
	
	
	public Main(){
		
		board=new Board();	// initialize game board
		
		////////////////////////////////////////////////
		// select two players

		player1=new Agent("AgentA", 1, this);
		//player1=new RandomPlayer("AgentA", 1, this);
		//player1=new Joueur("AgentA", 1, this);
		
		//player2=new Agent("AgentB", 2, this);
		player2=new RandomPlayer("AgentB", 2, this);
		//player2=new Joueur("AgentB", 2, this);
		
		////////////////////////////////////////////////
		
		display=new DisplayFrame(this);
		
		try {Thread.sleep(500);} 
		catch (InterruptedException e) {e.printStackTrace();}
		
		
		// main loop
		while (true){
		
			// while game is not over
			while (!gameover){
				
				if (player==1){
					// player 1 turn: get list of possible actions
					board.getActionsPlayer1();
					
					// get player selected action
					int action=player1.update(board.getStatePlayer1(), reward1, board.actions);

					display.repaint();
					pause();
					
					reward1=0; //reset reward 
					
					if (board.event==1) {
						reward1+=10; //add reward for player1
						reward2+=-10;
					}
					
					// perform action
					board.movePlayer1(action);
					
					display.repaint();
					pause();
					

					
					// detect end of game by victory
					if (board.player1Wins()){
						System.out.println("Player 1 wins");
						gameover=true;
						
						player1.learn(board.getStatePlayer1(),100);
						player2.learn(board.getStatePlayer2(), -100); //on inverse pour le joueur 2
					}
					else{
						// detect end of game by draw
						board.getActionsPlayer2();
						if (board.actions.size()==0){
							System.out.println("draw");
							gameover=true;
							
							player1.learn(board.getStatePlayer1(), 10);
							player2.learn(board.getStatePlayer2(), 10);
						}
					}
				}
				else{
					// player 2 turn: get list of possible actions
					board.getActionsPlayer2();
					
					// get player selected action
					int action=player2.update(board.getStatePlayer2(), reward2,board.actions);
					
					display.repaint();
					pause();
					
					// perform action
					board.movePlayer2(action);
					
					display.repaint();
					pause();
					
					reward2=0; 
					
					//get reward from move (eat events)
					if (board.event==1) {
						reward1+=-10; 
						reward2+=10; 
					}
					
					// detect end of game by victory
					if (board.player2Wins()){
						System.out.println("Player 2 wins");
						gameover=true;
						
						player1.learn(board.getStatePlayer1(), -100);
						player2.learn(board.getStatePlayer2(), 100);
					}
					else{
						// detect end of game by draw
						board.getActionsPlayer1();
						if (board.actions.size()==0){
							System.out.println("draw");
							gameover=true;
							
							player1.learn(board.getStatePlayer1(), 10);
							player2.learn(board.getStatePlayer2(), 10);
						}
					}
				}
				
				// change current player
				if (!gameover){
					player=(player+1)%2;
				}
			}
			
			// end of current play: re-initialize board
			
			display.repaint();
			pause();
			pause();
			
			board.initialize();
			player=1;
			
			reward1=0; 
			reward2=0; 

			player1.initialize();
			player2.initialize();
			gameover=false;
			

			display.repaint();
			pause();
			pause();
		}

	}
	

	// set delay
	private void pause(){
		
		while (pause){
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		if (tempo){
			try {Thread.sleep(delay);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}

	// save agents' parameters
	public void saveAgents(){
		player1.save();
		player2.save();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		new Main();
	}

}
