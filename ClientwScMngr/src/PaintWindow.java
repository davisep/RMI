import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.JScrollPane;

public class PaintWindow {
	JFrame frame;
	JPanel drawPanel, chatPanel, buttonPanel;
	JButton blackButton, redButton, whiteButton, blueButton, yellowButton, greenButton, chatButton, clearButton, eraserButton, customButton;
	JTextField typedText;
	JTextArea chatText;
	PenTool drawBox;
	Client client;
	String name;
	ServerInterface server;
	JScrollPane textArea;
	Panel colorPanel;
	Checkbox mute;
	boolean playNoise = true;

	//Creates the Paint Window GUI.
    public void runPaintWindow(){
    	
    	drawPanel = new JPanel();
    	chatPanel = new JPanel();
    	buttonPanel = new JPanel();
    	colorPanel = new Panel();
    	colorPanel.setBackground(new Color(0, 0, 0));
    	
    	blackButton = new JButton();
    	redButton = new JButton();
    	whiteButton = new JButton();
    	blueButton = new JButton();
    	yellowButton = new JButton();
    	greenButton = new JButton();
    	chatButton  = new JButton();
    	clearButton= new JButton();
    	eraserButton= new JButton();
    	customButton= new JButton();
    	
    	typedText = new JTextField();
    	chatText = new JTextArea();
    	textArea = new JScrollPane(chatText);
    	mute = new Checkbox();
    	frame = new JFrame ();

		//Adds the canvas to the drawing panel.
    	drawPanel.add(drawBox);
    	
    	//Layout code created with help of NetBeans software.
    	//Creates three panels and inputs the objects.
    	//There is a draw panel for drawing, a button panel on the right for the buttons and a chat panel at the bottom for the chat function.
    	GroupLayout drawPanelLayout = new GroupLayout(drawPanel);
    	drawPanel.setLayout(drawPanelLayout);
    	drawPanelLayout.setHorizontalGroup(
    			drawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(drawBox, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addGap(0, 396, Short.MAX_VALUE)
        );
    	drawPanelLayout.setVerticalGroup(
    			drawPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
    			.addGroup(drawPanelLayout.createSequentialGroup()
    	                .addComponent(drawBox, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
    	                .addGap(0, 0, Short.MAX_VALUE))
        );
    	//Below code sets the buttont texts and actions the buttons do.
    	//The color buttons set eraser to false and change the color.
    	blackButton.setText("Black");
    	blackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.BLACK;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	redButton.setText("Red");
    	redButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.RED;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	whiteButton.setText("White"); 
    	whiteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.WHITE;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	blueButton.setText("Blue");
    	blueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.BLUE;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	yellowButton.setText("Yellow");
    	yellowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.YELLOW;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	greenButton.setText("Green");
    	greenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
            	drawBox.penColor = Color.GREEN;
            	colorPanel.setBackground(drawBox.penColor);
            }
        });
    	//The clear button sends the Clear All command to the server to distribute to all the clients. 
    	clearButton.setText("Clear");
    	clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
					server.sendClearAllToServer();
				} catch (RemoteException e) {
					System.out.println("Error 12 Class PaintWindow: "+e);
				}
            }
        });
    	//The eraser button sets the eraser to true so when the mouse is clicked and dragged the canvas will knwo to send an eraser command to the server instead of a draw line command.
    	eraserButton.setText("Eraser");
    	eraserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = true;
            	colorPanel.setBackground(Color.WHITE);
            }
        });
    	//This button opens JColorChooser to let the user select a custom color.
    	customButton.setText("Custom");
    	customButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawBox.eraser = false;
                Color colorPicked = JColorChooser.showDialog(null, "Pick a color", drawBox.penColor);
                if (colorPicked != null) {
                	drawBox.penColor = colorPicked;
                	colorPanel.setBackground(drawBox.penColor);
                }
            }
        });
    	//Sets the text for the mute check box and will turn the chat noise on or off.
    	mute.setLabel("Mute");
        mute.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               if (playNoise == true){
            	   playNoise = false;
            	}else{
            	   playNoise = true;
               } 	
            }
        });

    	//Creates the color box that displays the current color of the pen.
        GroupLayout colorPanelLayout = new GroupLayout(colorPanel);
        colorPanel.setLayout(colorPanelLayout);
        colorPanelLayout.setHorizontalGroup(
        	colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        colorPanelLayout.setVerticalGroup(
        	colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
    	
        //Adds the objects to the button panel.
    	GroupLayout buttonLayout = new GroupLayout(buttonPanel);
    	buttonPanel.setLayout(buttonLayout);
    	buttonLayout.setHorizontalGroup(
    			buttonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(buttonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(buttonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(clearButton)
                            .addComponent(eraserButton)
                        	.addComponent(blackButton)
                            .addComponent(redButton)
                            .addComponent(whiteButton)
                            .addComponent(blueButton)
                            .addComponent(yellowButton)
                            .addComponent(greenButton)
                            .addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(customButton)
                            .addComponent(mute))
                        .addContainerGap(17, Short.MAX_VALUE))
                );
    		buttonLayout.setVerticalGroup(
    			buttonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(buttonLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(clearButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(eraserButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(blackButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(redButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(whiteButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(blueButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(yellowButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(greenButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(colorPanel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(customButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(mute)
                    .addContainerGap(17, Short.MAX_VALUE))
            );
    
    	//Sets text and action of chat button.
    	chatButton.setText("Chat");
		chatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				//Sends the text typed in the typedText bx to the server to be distributed to all the clients.
				sendText();
			}
		});
		
		//Sets he objects in the chat panel
    	GroupLayout chatLayout = new GroupLayout(chatPanel);
    	chatPanel.setLayout(chatLayout);
        chatLayout.setHorizontalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(chatLayout.createSequentialGroup()
                .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(typedText, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        chatLayout.setVerticalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatLayout.createSequentialGroup()
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(chatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typedText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

       //Adds the three panels to the frame
       GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(chatPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(drawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        //Adds a key listener to the typedText box, if "Enter" is pressed the text will send to the server.
        typedText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                	sendText(); 
                }
            }
        });
        //Lets the key listener work
    	frame.setFocusable(true);
        frame.pack();
        //Loads cursor in typedtext box. Needs to be after the frame is packed.
        typedText.requestFocusInWindow();
    	frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    	frame.setResizable(false);
    	frame.setVisible(true);
    	//Went window is closed sends exitClient command to the server to remove self from the array of users.
	    frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
	  	    		  try {
						server.exitClient(client, name);
					} catch (Exception e2) {
						System.out.println("Error 13 Class PaintWindow: "+e2);
					}
                frame.dispose();
            }
        });
 	
    }

    //Populates the chat line received from the server in the GUI and plays a noise.
    public void postLineToGUI(String chatMessage){  
    	chatText.setText(chatText.getText()+"\n"+chatMessage);  
    	//Play noise if not muted
        if(playNoise == true){
            Toolkit.getDefaultToolkit().beep();
        }  
        //Makes scroll bar update to the bottom
        DefaultCaret caret = (DefaultCaret)chatText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
    //Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    //Sends the typed text to the server to be sent to all the clients
    public void sendText(){
          String chatMessage=typedText.getText();
          chatMessage="["+name+"] "+chatMessage;
          typedText.setText("");
          try{
        	  	server.sendTextToServer(chatMessage);
      	 }catch(Exception e){
      	  	System.out.println("Error 14 Class PaintWindow: "+e);
      	 }
    }		
}