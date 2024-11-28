package hexapion;

import java.util.ArrayList;

// this class contains the game board
// and functions to convert actions to
// moves and vice versa
public class Board {

	public int[][] board;		// game board
	
	public int event=0;			// rise events that occurred during the move (i.e. eat)
	
	public ArrayList<Integer> actions=new ArrayList<Integer>();	// list of possible actions
	
	public Board(){
		// main board : 1 for white pawn, -1 for black pawn and 0 for empty
		board=new int[][]{ {-1,-1,-1},
			   				{0,0,0},
			   				{1,1,1} };
	}
	
	// restart game
	public void initialize(){
		board=new int[][]{ {-1,-1,-1},
							{0,0,0},
							{1,1,1} };
	}
	
	// get current environment state number
	public int getStatePlayer1(){
		int state=0;
		
		for (int i=0;i<3;i++){
			for (int j=0;j<3;j++){
				state=state*3+(board[i][j]+1);
			}
		}
		return state;
	}
	
	// get current environment state number for player 2 (reversed board)
	public int getStatePlayer2(){
		int state=0;
		
		for (int i=2;i>=0;i--){
			for (int j=2;j>=0;j--){
				state=state*3+(-board[i][j]+1);
			}
		}
		return state;
	}
	
	
	// get possible actions (from player1 perspective)
	public void getActionsPlayer1(){
		
		actions.clear();
		
		// line 1
		if (board[2][0]==1 && board[1][0]==0) actions.add(0);
		if (board[2][0]==1 && board[1][1]==-1) actions.add(1);
		
		if (board[2][1]==1 && board[1][0]==-1) actions.add(2);
		if (board[2][1]==1 && board[1][1]==0) actions.add(3);
		if (board[2][1]==1 && board[1][2]==-1) actions.add(4);
		
		if (board[2][2]==1 && board[1][1]==-1) actions.add(5);
		if (board[2][2]==1 && board[1][2]==0) actions.add(6);
		
		// line 2
		if (board[1][0]==1 && board[0][0]==0) actions.add(7);
		if (board[1][0]==1 && board[0][1]==-1) actions.add(8);
		
		if (board[1][1]==1 && board[0][0]==-1) actions.add(9);
		if (board[1][1]==1 && board[0][1]==0) actions.add(10);
		if (board[1][1]==1 && board[0][2]==-1) actions.add(11);
		
		if (board[1][2]==1 && board[0][1]==-1) actions.add(12);
		if (board[1][2]==1 && board[0][2]==0) actions.add(13);
	}
	
	// get possible actions (from player1 perspective)
	public void getActionsPlayer2(){
		
		actions.clear();
		
		// line 1
		if (board[0][2]==-1 && board[1][2]==0) actions.add(0);
		if (board[0][2]==-1 && board[1][1]==1) actions.add(1);
		
		if (board[0][1]==-1 && board[1][2]==1) actions.add(2);
		if (board[0][1]==-1 && board[1][1]==0) actions.add(3);
		if (board[0][1]==-1 && board[1][0]==1) actions.add(4);
		
		if (board[0][0]==-1 && board[1][1]==1) actions.add(5);
		if (board[0][0]==-1 && board[1][0]==0) actions.add(6);
		
		// line 2
		if (board[1][2]==-1 && board[2][2]==0) actions.add(7);
		if (board[1][2]==-1 && board[2][1]==1) actions.add(8);
		
		if (board[1][1]==-1 && board[2][2]==1) actions.add(9);
		if (board[1][1]==-1 && board[2][1]==0) actions.add(10);
		if (board[1][1]==-1 && board[2][0]==1) actions.add(11);
		
		if (board[1][0]==-1 && board[2][1]==1) actions.add(12);
		if (board[1][0]==-1 && board[2][0]==0) actions.add(13);
	}
	
	
	public void movePlayer1(int act){
		
		event=0;
		if (actions.indexOf(act)==-1){
			System.out.println("wrong action");
		}
		else{
				
			// line 1
			if (act==0){     board[2][0]=0;board[1][0]=1;}
			else if (act==1){board[2][0]=0;board[1][1]=1;event=1;}
			
			else if (act==2){board[2][1]=0;board[1][0]=1;event=1;}
			else if (act==3){board[2][1]=0;board[1][1]=1;}
			else if (act==4){board[2][1]=0;board[1][2]=1;event=1;}
			
			else if (act==5){board[2][2]=0;board[1][1]=1;event=1;}
			else if (act==6){board[2][2]=0;board[1][2]=1;}
			
			// line 2
			else if (act==7){board[1][0]=0;board[0][0]=1;}
			else if (act==8){board[1][0]=0;board[0][1]=1;event=1;}
			
			else if (act==9){board[1][1]=0;board[0][0]=1;event=1;}
			else if (act==10){board[1][1]=0;board[0][1]=1;}
			else if (act==11){board[1][1]=0;board[0][2]=1;event=1;}
			
			else if (act==12){board[1][2]=0;board[0][1]=1;event=1;}
			else if (act==13){board[1][2]=0;board[0][2]=1;}
		}
	}
	
	// detect if player1 wins
	public boolean player1Wins(){
		boolean win=false;
		
		// detect end of game
		if (board[0][0]==1 || board[0][1]==1 || board[0][2]==1) win=true;
		else{ // count number of other side pawns
			int count=0;
			for (int i=0;i<3;i++){
				for (int j=0;j<3;j++){
					if (board[i][j]==-1) count++;
				}
			}
			if (count==0) win=true;
		}
		return win;
	}
	
	
	public void movePlayer2(int act){
		event=0;
		if (actions.indexOf(act)==-1){
			System.out.println("wrong action");
		}
		else{
			
			// line 1
			if (act==0){     board[0][2]=0;board[1][2]=-1;}
			else if (act==1){board[0][2]=0;board[1][1]=-1;event=-1;}
			
			else if (act==2){board[0][1]=0;board[1][2]=-1;event=-1;}
			else if (act==3){board[0][1]=0;board[1][1]=-1;}
			else if (act==4){board[0][1]=0;board[1][0]=-1;event=-1;}
			
			else if (act==5){board[0][0]=0;board[1][1]=-1;event=-1;}
			else if (act==6){board[0][0]=0;board[1][0]=-1;}
			
			// line 2
			else if (act==7){board[1][2]=0;board[2][2]=-1;}
			else if (act==8){board[1][2]=0;board[2][1]=-1;event=-1;}
			
			else if (act==9){board[1][1]=0;board[2][2]=-1;event=-1;}
			else if (act==10){board[1][1]=0;board[2][1]=-1;}
			else if (act==11){board[1][1]=0;board[2][0]=-1;event=-1;}
			
			else if (act==12){board[1][0]=0;board[2][1]=-1;event=-1;}
			else if (act==13){board[1][0]=0;board[2][0]=-1;}

		}	
	}
	
	// detect if player2 wins
	public boolean player2Wins(){
		boolean win=false;
		
		// detect end of game
		if (board[2][0]==-1 || board[2][1]==-1 || board[2][2]==-1) win=true;
		else{ // count number of other side pawns
			int count=0;
			for (int i=0;i<3;i++){
				for (int j=0;j<3;j++){
					if (board[i][j]== 1) count++;
				}
			}
			if (count==0) win=true;
		}
		return win;
	}
	
	// conversion user'move -> action id
	public int getMove(int id, int x1, int y1, int x2, int y2){
		
		if (id==1){
			if      (x1==2 && y1==0 && x2==1 && y2==0 && board[1][0]== 0) return 0;
			else if (x1==2 && y1==0 && x2==1 && y2==1 && board[1][1]==-1) return 1;
			
			else if (x1==2 && y1==1 && x2==1 && y2==0 && board[1][0]==-1) return 2;
			else if (x1==2 && y1==1 && x2==1 && y2==1 && board[1][1]== 0) return 3;
			else if (x1==2 && y1==1 && x2==1 && y2==2 && board[1][2]==-1) return 4;
			
			else if (x1==2 && y1==2 && x2==1 && y2==1 && board[1][1]==-1) return 5;
			else if (x1==2 && y1==2 && x2==1 && y2==2 && board[1][2]== 0) return 6;
			
			
			else if (x1==1 && y1==0 && x2==0 && y2==0 && board[0][0]== 0) return 7;
			else if (x1==1 && y1==0 && x2==0 && y2==1 && board[0][1]==-1) return 8;
			
			else if (x1==1 && y1==1 && x2==0 && y2==0 && board[0][0]==-1) return 9;
			else if (x1==1 && y1==1 && x2==0 && y2==1 && board[0][1]== 0) return 10;
			else if (x1==1 && y1==1 && x2==0 && y2==2 && board[0][2]==-1) return 11;
			
			else if (x1==1 && y1==2 && x2==0 && y2==1 && board[0][1]==-1) return 12;
			else if (x1==1 && y1==2 && x2==0 && y2==2 && board[0][2]== 0) return 13;
			
			else{
				System.out.println("wrong move");
				return -1;
			}
		}
		else{
			if      (x1==0 && y1==2 && x2==1 && y2==2 && board[1][2]== 0) return 0;
			else if (x1==0 && y1==2 && x2==1 && y2==1 && board[1][1]==1) return 1;
			
			else if (x1==0 && y1==1 && x2==1 && y2==2 && board[1][2]==1) return 2;
			else if (x1==0 && y1==1 && x2==1 && y2==1 && board[1][1]== 0) return 3;
			else if (x1==0 && y1==1 && x2==1 && y2==0 && board[1][0]==1) return 4;
			
			else if (x1==0 && y1==0 && x2==1 && y2==1 && board[1][1]==1) return 5;
			else if (x1==0 && y1==0 && x2==1 && y2==0 && board[1][0]== 0) return 6;
			
			
			else if (x1==1 && y1==2 && x2==2 && y2==2 && board[2][2]== 0) return 7;
			else if (x1==1 && y1==2 && x2==2 && y2==1 && board[2][1]==1) return 8;
			
			else if (x1==1 && y1==1 && x2==2 && y2==2 && board[2][2]==1) return 9;
			else if (x1==1 && y1==1 && x2==2 && y2==1 && board[2][1]== 0) return 10;
			else if (x1==1 && y1==1 && x2==2 && y2==0 && board[2][0]==1) return 11;
			
			else if (x1==1 && y1==0 && x2==2 && y2==1 && board[2][1]==1) return 12;
			else if (x1==1 && y1==0 && x2==2 && y2==0 && board[2][0]== 0) return 13;
			
			else{
				System.out.println("wrong move");
				return -1;
			}
		}
		
		
		 
	}
}
