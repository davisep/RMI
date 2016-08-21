import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartClient {
    Client client;
    ServerInterface server;
    PaintWindow paintWindow;
    static StartClient sc;
	JTextField textFeild,ip, name;
	JButton connect;
	JFrame frame;
	PenTool drawBox;
    String stringName;
    
    //Starts the application by creating a start client object,
    public static void main(String [] args){
  	 sc = new StartClient();
  	 sc.runLoginWindow();
    }  

    //Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    //Creates the GUI for the login window.
    public void runLoginWindow(){
    	frame=new JFrame("Input the IP of the Host");
	    JPanel main =new JPanel();
	    name=new JTextField();
	    //Used for quick testing
	    //name.setText("Name");
	    ip=new JTextField();
	    //ip.setText("161.23.166.76");
	    connect=new JButton("Connect");
	    
	    main.setLayout(new GridLayout(1,0,5,5)); 
	    main.add(new JLabel("Your name: "));
	    main.add(name);    
	    main.add(new JLabel("Server Address: "));
	    main.add(ip);
	    main.add(connect);
	    
	    //When connect button is pushed the client tries to connect to the server.
	    connect.addActionListener(new ActionListener(){
  	      public void actionPerformed(ActionEvent e){
  	    	  if (connect.getText().equals("Connect")){
  	    		  doConnect();
  	    	  }else{
  	    		  try {
  	    			stringName = name.getText();
					server.exitClient(client, stringName);
				} catch (Exception e3) {
					System.out.println("Error 15. Class StartClient: "+e3);
				}
  	    	  }
  	      }});
	    //If the enter key is hit the window will try to connect to the server.
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                	doConnect(); 
                }
            }
        });
        //Needed for key listener.
        frame.setFocusable(true);
	    frame.setContentPane(main);
	    frame.setSize(600,50);
	    frame.setVisible(true); 
	    frame.setResizable(false);
	    //Will remove client interface from user array if the window is closed.
	    frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	frame.dispose();
	  	    		try {
	  	    			stringName = name.getText();
						server.exitClient(client, stringName);
					} catch (Exception e4) {
						System.out.println("Error 16. Class StartClient: "+e4);
					}	
	  	    		try {
	  	    			paintWindow.frame.dispose();
					} catch (Exception e5) {
						System.out.println("Error 20. Class StartClient: "+e5);
					}	
                
            }
        });
    }

    //Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    //Tries to connect to the server.
    public void doConnect(){
  	    if (connect.getText().equals("Connect")){
  	    	if (name.getText().length()<=1){JOptionPane.showMessageDialog(frame, "You need to type a name."); return;}
  	    	if (ip.getText().length()<2){JOptionPane.showMessageDialog(frame, "You need to type an IP."); return;}	    	
  	    	try{
  				client=new Client(name.getText());
  	    		client.sc = sc;
  	    		//Creates the canvas and paint window.
  	    		drawBox = new PenTool();
  	  	  		paintWindow = new PaintWindow();
  	  	  		//Sets the paint windows values.
  	  	  		paintWindow.drawBox = drawBox;
  	  	  		paintWindow.name = name.getText();
  	  	  		paintWindow.client = client;
  	  	  		//Sets the clients values.
  	  	  		client.drawBox = drawBox;
  	  	  		client.pw = paintWindow;
  	  	  		
  	  	  		//Looks up server object in RMIR
  			    server=(ServerInterface)Naming.lookup("rmi://"+ip.getText()+"/myPaintChat");
  			    //Creates the security manager. (Currently does nothing)
  			    if (System.getSecurityManager( ) == null) {
  			    	System.setSecurityManager(new ClientSecurityManager());
  				}
  			  	//Sets the server value in drawbox and paintwindow object.
  	    		drawBox.server = server;
  			    paintWindow.server = server;
  			    paintWindow.runPaintWindow();
  			    //Sends server client to log in.
  				server.login(client);
  				//Changes button text to disconnect.
  			    connect.setText("Disconnect");

  	    	}catch(Exception e){
  	    		JOptionPane.showMessageDialog(frame, "ERROR, Can not connect");
  	    		System.out.println("Error 17. Class StartClient: "+e);
  	    	}		  
  	      }else{
  	    	  //Sets button text to connect if it can't connect.
  	    	   connect.setText("Connect");
  		}
  	  } 
}