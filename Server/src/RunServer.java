import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RunServer {
	JFrame frame;
	JPanel panel;
	JButton connect;
	JTextField ipInput;

	public static void main(String[] args) {
		RunServer serverGUI = new RunServer();
		serverGUI.runServerWindow();
	}
		
	public void runServerWindow(){
		//Creates the GUI
    	frame=new JFrame("Enter the IP of the host computer");
	    panel =new JPanel();
	    ipInput=new JTextField();
	    ipInput.setText("161.23.166.76");
	    connect=new JButton("Connect");
	    
	    panel.setLayout(new GridLayout(1,0,5,5)); 
	    panel.add(new JLabel("Host IP: "));
	    panel.add(ipInput);    
	    panel.add(connect);
		
	    //When connect button is pushed the server tries to bind to the RMIR.
	    connect.addActionListener(new ActionListener(){
  	      public void actionPerformed(ActionEvent e){
  	    	  try{
	  	    	  if (connect.getText().equals("Connect")){
	  	    		  connect(ipInput.getText());
	  	    	  }
				} catch (Exception e4) {
					JOptionPane.showMessageDialog(frame, "ERROR, Can not connect");
					System.out.println("Error 18. Class RunServer: "+e4);
				}
  	    	  
  	      }});
	    //If the enter key is hit the window will try to connect to the server.
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                	try{
      	  	    	  if (connect.getText().equals("Connect")){
      	  	    		  connect(ipInput.getText());
      	  	    	  }
      				} catch (Exception e4) {
      					JOptionPane.showMessageDialog(frame, "ERROR, Can not connect");
      					System.out.println("Error 19. Class RunServer: "+e4);
      				}
                }
            }
        });
        //Needed for key listener.
        frame.setFocusable(true);
	    frame.setContentPane(panel);
	    frame.setSize(300,50);
	    frame.setVisible(true); 
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
	}
	

	public void connect(String hostIP){
		try {
				
				//Creates new security manager. Security policy set to Grant all permissions
				//Currently commented out because not needed.
				//System.setSecurityManager(new RMISecurityManager());
			
				//Creates a registry and listens to port 1099
			 	java.rmi.registry.LocateRegistry.createRegistry(1099);
				ServerInterface server =new Server();
				//IP needs to be set the the host computer's IP. Binds server object to RMIR.
				Naming.rebind("rmi://"+hostIP+"/myPaintChat", server);
				System.out.println("Server is working.");
			}catch (Exception e) {
					System.out.println("Error 1. Class RunServer: " + e);
			}
	}
		
		

}

