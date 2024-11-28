package hexapion;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

// agent display
public class AgentPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Agent agent;	// pointer to the agent
	
	// constructor
	public AgentPanel(Agent a){
		agent=a;
	}
	
public void paintComponent(Graphics g){
		
		g.setColor(Color.white);
		g.fillRect(0,0, this.getWidth(), this.getHeight());
		
		if (agent.stateIndex!=-1){
			g.setColor(Color.red);
			g.fillRect(5, 15+15*agent.stateIndex, 620, 12); // current state
		}
		
		g.setColor(Color.black);
		for (int i=0;i<14;i++){		// labels of action index
			g.drawString(""+i, 80+40*i, 10);
		}
		for (int i=0;i<agent.states.size();i++){ // lines of the Q-table
			g.drawString(""+agent.states.get(i), 10, 25+15*i);
			
			for (int j=0;j<agent.actions.get(i).length;j++){
				g.drawString(""+(float)Math.round(agent.Qtable.get(i)[j]*100)/100, 
						     80+40*agent.actions.get(i)[j], 25+15*i);
			}
		}
	}
}