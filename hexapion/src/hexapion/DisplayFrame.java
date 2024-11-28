package hexapion;

import javax.swing.JFrame;

// main board frame
public class DisplayFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public DisplayPanel panel;

	public DisplayFrame(Main m){
		this.setTitle("Board");
    	this.setSize(330, 440);
    	this.setLocationRelativeTo(null);               
    	this.setVisible(true);
    	panel=new DisplayPanel(m);
    	this.setContentPane(panel);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}