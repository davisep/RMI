import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PenTool extends JPanel implements MouseListener, MouseMotionListener {
	
		JFrame frame; 
		int prevX, prevY; 
		boolean dragging;  
		Graphics g2d, clientG2d, eraserG;
		Graphics2D eraserG2d; //Needed for stroke command
		ServerInterface server;
		static PenTool pen;
		Color penColor; 
		Color clientColor;
		boolean eraser;
		
		//Creates the canvas and sets he starting pen color to black.
		PenTool() {
	          setBackground(Color.WHITE);
	          addMouseListener(this);
	          addMouseMotionListener(this);
	          penColor = new Color(0,0,0);
	    }
		
		//Allows the canvas to be drawn on.
	  	public void paintComponent (Graphics g) {
	  		super.paintComponent(g); 
		}
	  	
	  	//Code from http://stackoverflow.com/questions/37813525/multiuser-whiteboard-getting-the-server-client-to-pass-x-y-values-to-draw-a-lin
	  	//Checks if mouse is pressed to create a line on the canvas. Sets dragging to true while mouse is pressed.
	  	public void mousePressed(MouseEvent evt) {
	  		int x = evt.getX(); 
	  		int y = evt.getY(); 
	  		if (dragging == true){
	  			return;  
	  		}else{
	            prevX = x;
	            prevY = y;
	            dragging = true;
	            g2d = getGraphics();
	  		}
	  	}
	  	
	  	//Code from http://stackoverflow.com/questions/37813525/multiuser-whiteboard-getting-the-server-client-to-pass-x-y-values-to-draw-a-lin
	  	//Sets dragging to false when mouse is released.
	     public void mouseReleased(MouseEvent evt) {
	          if (dragging == false)
	             return;  
	          dragging = false;
	     }
	     
		 //Code from http://stackoverflow.com/questions/37813525/multiuser-whiteboard-getting-the-server-client-to-pass-x-y-values-to-draw-a-lin
		 //Records new x and y coordinates as the mouse is being dragged and sends them to the server to distribute to all the clients.
	     //If eraser is on then it tells the server to erase, if not it tells the server to draw a line.
	     public void mouseDragged(MouseEvent evt) {
	    	  if (dragging == false)
	              return; 
	          int x = evt.getX();  
	          int y = evt.getY(); 
	         try {
	        	  if (eraser == true){
	        		 server.sendEraserToServer(prevX, prevY, x, y); 
	        	  }else{
	        		  server.sendLineToServer(penColor, prevX, prevY, x, y);
				}
			} catch (Exception e) {
				System.out.println("Error 11 Class PenTool: "+e);
			} 
	          prevX = x; 
	          prevY = y;
	      }
	      
	    //Java requires using all the mouse listener commands.
	    public void mouseEntered(MouseEvent evt) { }  
	    public void mouseExited(MouseEvent evt) { }   
	    public void mouseClicked(MouseEvent evt) { }  
	    public void mouseMoved(MouseEvent evt) { }
		
	    //Receives line information form the server and draws it on the canvas
		public void getLine(Color clientColor, int drawPrevX, int drawPrevY, int drawX, int drawY) {
			clientG2d=getGraphics();
			clientG2d.setColor(clientColor);  
			clientG2d.drawLine(drawPrevX, drawPrevY, drawX, drawY); 
		} 
		
		//Receives eraser information from the server and draws it on the canvas.
		public void clientEraser(int eraserPrevX, int eraserPrevY, int eraserX, int eraserY) {
			eraserG = getGraphics();
			//Graphics object needs to be cast as graphics2D in order to use setStroke.
			eraserG2d = (Graphics2D) eraserG;
			eraserG2d.setColor(Color.WHITE);  
			//Sets size of eraser.
			eraserG2d.setStroke(new BasicStroke(9));
			eraserG2d.drawLine(eraserPrevX, eraserPrevY, eraserX, eraserY); 
			
		} 
		
		//When the clear all command from the server is received it fills the canvas with a white rectangle. 
	    public void clearAll(){
	  		int width = getWidth(); 
	  		int height = getHeight();
	  		g2d.setColor(Color.WHITE);
	  		g2d.fillRect(0, 0, getWidth(), getHeight());
	    }
}