package hexapion;
import java.util.ArrayList;


public class Agent extends Player{
	

	public ArrayList<Integer> states; 
	public ArrayList<int[]> actions; 
	public ArrayList<float[]> Qtable; 
	
	public int stateIndex = -1; 
	public int actionIndex=-1; 
	
	public ArrayList<Integer> actionSequence; 
	public ArrayList<Integer> stateSequence; 
	public ArrayList<Integer> rewardSequence; 
	
	public float learnRate=0.01f; 
	public float gamma=0.9f; 
	public float epsilon = 1; //on aura besoin plus tard
	
	public int nbStep=0; 

	private AgentFrame display;
	
	public Agent(String name, int id, Main m){
		super(name, id,m);
		states=new ArrayList<Integer>(); 
		actions=new ArrayList<int[]>(); 
		Qtable=new ArrayList<float[]>(); 
		
		
		display=new AgentFrame(this);
		
		actionSequence = new ArrayList<Integer>(); 
		stateSequence = new ArrayList<Integer>(); 
		rewardSequence = new ArrayList<Integer>(); 
	}
	
	public void initialize(){
		stateIndex = -1; 
		actionIndex=-1; 
		
		actionSequence.clear(); 
		stateSequence.clear(); 
		rewardSequence.clear(); 
	}
	
	public int update(int state, int reward, ArrayList<Integer> possible_actions){
		
		//if unkown state, add it to the list
		if (states.indexOf(state)==-1) {
			states.add(state); 
			
			//conversion ArrayList -> tableau
			int[] actList= new int[possible_actions.size()]; 
			for (int i=0; i<possible_actions.size(); i++) {
				actList[i]=possible_actions.get(i); 
			}
			
			// vecteur de float à 0
			float[] table = new float[possible_actions.size()]; 
			
			actions.add(actList); 
			Qtable.add(table); 
			
			//exemple
			//etat  state  actions  Qtable
			//      1000   [2,4,8]  [0.1;0.15;0.2]
			//      1500   [3,5,9]  [0;0.23;0.4]
			//      17000  [10,11]  [2;4]
			//       42    [1;3;6]  [0;0;0]
		}
		
		//get new state index 
		stateIndex=states.indexOf(state); 
		
		display.repaint(); 
		
		//selection of action : epsilon-greedy
		double rand = Math.random(); 
		if (rand<epsilon) {
			actionIndex=(int) (Math.random()*possible_actions.size()); 
			//System.out.println("random"); 
		}
		else {
			double[] softmax = new double[Qtable.get(stateIndex).length]; 
			double sum=0; 
			
			for (int i=0; i<Qtable.get(stateIndex).length; i++) {
				softmax[i]=Math.exp(Qtable.get(stateIndex)[i]); 
				sum+=softmax[i]; 
			}
			for (int i=0; i<Qtable.get(stateIndex).length; i++) {
				softmax[i]=softmax[i]/sum; 
			}
			
			//get a random value 
			double randvalue = Math.random(); 
			//get the element correspondig to this random value
			int argmax=0; 
			sum=softmax[0]; 
			while (sum<randvalue) {
				argmax++; 
				sum+=softmax[argmax]; 
			}
			actionIndex=argmax; 
			
			
			/*
			float maxval = -1000; 
			for (int i=0; i<Qtable.get(stateIndex).length; i++) {
				if (Qtable.get(stateIndex)[i]>maxval) {
					maxval=Qtable.get(stateIndex)[i]; 
					actionIndex=i; 
				}
			}
			*/
			//actionIndex=(int) (Math.random()*possible_actions.size()); 
			//System.out.println("max"); 
			
			
		}
		
		
		//int actionIndex = (int)(Math.random()*possible_actions.size());
		
		
		//update epsilon
		if (nbStep>5000 && nbStep%100==00) {
			epsilon=0.99f*epsilon; 
		}
		nbStep++; 
		
		//update sequences
		actionSequence.add(actionIndex); 
		stateSequence.add(stateIndex); 
		rewardSequence.add(reward); 
		
		return possible_actions.get( actionIndex );
	}
	
	
	public void learn(int state, int reward){
		//last step 
		float maxQ=0; 
		float reward_next=reward; 
		
		for(int i=actionSequence.size()-1; i>=0; i--) {
			int stateId=stateSequence.get(i); 
			int actionId=actionSequence.get(i); 
			
			Qtable.get(stateId)[actionId]= (1-learnRate) * Qtable.get(stateId)[actionId] + learnRate * (reward_next + gamma*maxQ); 
			
			// maxQ for previous step 
			maxQ=-1000; 
			for (int j=0; j<Qtable.get(stateId).length; j++) {
				if (Qtable.get(stateId)[j]>maxQ) maxQ=Qtable.get(stateId)[j]; 
			}
			
			//read reward for previous step 
			reward_next=rewardSequence.get(i); 
		}
		
		display.repaint(); 
	}

	
}
