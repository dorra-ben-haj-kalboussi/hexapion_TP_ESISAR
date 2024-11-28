package hexapion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

// main board display
public class DisplayPanel extends JPanel  implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	
	private Main main;	// pointer to main class
	
	
	// variables used for manual moves
	private boolean clicked=false;
	private int px=0;
	private int py=0;
	private int px2=0;
	private int py2=0;
	public int played=-1;
	
	
	// constructor
	public DisplayPanel(Main m){
		main=m;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	// main drawing function
	public void paintComponent(Graphics g){
		
		// white background
		g.setColor(Color.white);
		g.fillRect(0,0, this.getWidth(), this.getHeight());
		
		
		// draw board
		g.setColor(Color.black);
		g.drawRect(10, 10, 300, 300);
		
		g.drawLine(10, 110, 310, 110);
		g.drawLine(10, 210, 310, 210);
		g.drawLine(110, 10, 110, 310);
		g.drawLine(210, 10, 210, 310);
		
		// draw pawns
		for (int i=0;i<3;i++){
			for (int j=0;j<3;j++){
				
				if (main.board.board[j][i]==1){
					g.setColor(Color.lightGray);
					g.fillOval(20+100*i, 20+100*j, 80, 80);
					g.setColor(Color.black);
					g.drawOval(20+100*i, 20+100*j, 80, 80);
				}
				else if (main.board.board[j][i]==-1){
					g.setColor(Color.darkGray);
					g.fillOval(20+100*i, 20+100*j, 80, 80);
					g.setColor(Color.black);
					g.drawOval(20+100*i, 20+100*j, 80, 80);
				}
				
			}
		}
		
		// draw buttons
		if (main.tempo) g.setColor(Color.gray);
		else g.setColor(Color.lightGray);
		g.fillRect(20, 320, 80, 50);
		
		if (main.pause) g.setColor(Color.gray);
		else g.setColor(Color.lightGray);
		g.fillRect(120, 320, 80, 50);
		
		g.setColor(Color.lightGray);
		g.fillRect(220, 320, 80, 50);
		
		g.setColor(Color.black);
		g.drawString("tempo", 42, 347);
		g.drawString("pause", 142, 347);
		g.drawString("save", 245, 347);
		
		
		// draw movement line
		if (clicked){
			g.setColor(Color.red);
			g.drawLine((py*100)+60, (px*100)+60, py2, px2);
			g.drawOval(py2-40, px2-40, 80, 80);
		}
	}

	
	public void mouseClicked(MouseEvent e) {
		// button events
		if (e.getX()>20 && e.getX()<100 && e.getY()>320 && e.getY()<370){
			main.tempo=!main.tempo;
		}
		else if (e.getX()>120 && e.getX()<200 && e.getY()>320 && e.getY()<370){
			main.pause=!main.pause;
		}
		else if (e.getX()>220 && e.getX()<300 && e.getY()>320 && e.getY()<370){
			main.saveAgents();
		}
		this.repaint();
	}

	public void mouseEntered(MouseEvent arg0) {}

	// interruption of user movement
	public void mouseExited(MouseEvent arg0) {
		clicked=false;
	}

	// start measuring user movement
	public void mousePressed(MouseEvent e) {
		if (e.getX()>10 && e.getX()<310 && e.getY()>10 && e.getY()<310){
			py=(e.getX()-10)/100;
			px=(e.getY()-10)/100;
			
			if (main.player==1){
				if (main.board.board[px][py]==1) clicked=true;
			}
			else{
				if (main.board.board[px][py]==-1) clicked=true;
			}
		}
	}

	// stop measuring user movement and get action
	public void mouseReleased(MouseEvent e) {
		clicked=false;
		
		if (e.getX()>10 && e.getX()<310 && e.getY()>10 && e.getY()<310){
			px2=(px2-10)/100;
			py2=(py2-10)/100;
		
			played=main.board.getMove(main.player, px, py, px2, py2);
		
			this.repaint();
		}
	}

	// track user's movements
	public void mouseDragged(MouseEvent e) {
		if (e.getX()>10 && e.getX()<310 && e.getY()>10 && e.getY()<310){
			py2=e.getX();
			px2=e.getY();
			this.repaint();
		}
		else{
			clicked=false;
		}
	}

	public void mouseMoved(MouseEvent arg0) {}
	
}