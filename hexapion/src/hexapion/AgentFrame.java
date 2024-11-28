package hexapion;

import javax.swing.JFrame;


public class AgentFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private AgentPanel panel;

	// main drawing function
	public AgentFrame(Agent a){
		this.setTitle("Agent "+a.name);
    	this.setSize(650, 500);
    	this.setLocationRelativeTo(null);               
    	this.setVisible(true);
    	panel=new AgentPanel(a);
    	this.setContentPane(panel);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}