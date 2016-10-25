## About

This project has created an application where the users can collaboratively draw together and it is visible in real time. It has developed a GUI that includes chat and canvas functionality, implemented drawing methods in RMI, created a server log file and has the ability to let users change their colors while drawing.
	 
## Purpose

There is only one open source collaborative drawing application available and it is in C++. The contribution of this project is to offer an open source program in Java so others may build on the frame work and use it a learning tool. The motivation for this project was wanting to see more advanced features in collaborative drawing applications, but the time constraints of this project did not allow for anything but the most basic features to be implemented.

##Summary of code

Server side - Creates a log in GUI, users input the IP of the host computer then it creates a server object and binds it to RMIR. It receives methods sent by a client and distributes them to all the clients and records changes on the canvas in a log file.

Client side – Creates a log in GUI for name and IP of host, connects to server, creates a drawing window that includes a canvas, chat box and buttons. The canvas allows drawing by sensing the mouse being pressed and dragged. In the chat box text can be typed in a text field and will be populated for all the connected clients in a text area when the client hits their ‘chat’ button. The mute button mutes or unmutes the noise hat is played when the chat box receives a new message. The other buttons on the right set the eraser or pen colors for drawing and the clear all button sends a clear all message to the server.

## Requirements

Windows OS
Clients are all on same LAN network
Java installed

Running the program:

	 To run the program, start by double clicking on the server .JAR file. A window will pop up asking you to input the IP of the computer hosting the server. Type this, including periods and click connect. This can also be done by running the class RunServer in the command line. If you used the command line instead of the .JAR it will tell you I the server is working and if the previous log file was deleted to create a new one. 

	Next for the client double click on the client .JAR or run StartClient from the command line. Type in the name to be used in the chat and the IP address of the host computer and click connect. The drawing window will appear: 

	 The program will default to the color black for the pen tool. To draw click and drag the mouse on the canvas area and release when the line is completed. A one-pixel line will appear on all the clients. The ‘Clear’ button will clear all the clients’ canvases. The ‘Eraser’ button will draw a nine-pixel wide white line. To change the colors, the color buttons or the custom button can be used. The box above the ‘Custom’ button will show the current color. Clicking the ‘Custom’ will show the JColorPIcker. Select the desired color and then click ‘OK’ to return to the drawing window. The ‘Mute’ button will stop the noise that plays when next text appears in the chat field.

Additions to be made:

-	A color picker so the user can select any color.
-	Being able to change the size of the pen tool and have different types of brushes (Ex: watercolor, paint, pen) .
-	Modifications to the canvas being stored in an array so that when a new user connects they can see the drawing as the other users see it. This would also fix the problem with the drawing being lost when the window is resized and moved. It would also make the program more robust if the array is stored on the server computer because the drawing could be recovered if something stops working. 
-	Creating the above functionality would also make it easy to add an undo and redo button. 
-	Saving functionally.
-	Drawing layers. 
-	Embedding the application in a browser for users to easily access. 
-	The ability to support different sessions for different groups of users.
-	A user definable palette to store previously used colors.
	
Overveiw:
	
		 The classes of the Server Program are RunServer, Server, ClientInterface and ServerInterface. The classes for the Client program are StartClient, Client, ClientInterface, SeverInterface, PaintWindow and PenTool.		 
RunSever class:
	This class sets the IP and port the server will listen to; it creates a GUI for the user to enter the IP of the computer hosting the server. It then creates the server object and binds it to the RMIR. If this project was using an RMI security manager it would be run first in this class.
Server class: 
The Server class contains the code for the methods the Client objects can perform on the Server object and the code for creating a log file of all incoming commands from the Client objects to the Server. It also has a vector which holds an array of ClientInterface objects. These are how the Server finds the Client. Vector is used instead of ArrayList because in Java vector is synchronized. The codes’ speed is worsened by using vector but multiple edits at once cannot be done to an ArrayList.
The Server contains four methods it can perform on a Client object: ‘login’, ‘sendLineToServer’,’ sendTextToServer’, ‘sendClearAllToServer’, ‘exitClient’ and ‘sendEraserToServer’. The login method receives a ClientInterface object from the connecting client and adds it to the vector. The ‘sendLineToServer’ method receives five attributes from the Client, marker, previous x, previous y, x and y. The number of the value for marker corresponds to a color and the other four are coordinates for drawing. When a Client does a method on the Server, the Server will loop through all the ClientInterfaces in the vector and calls the method on them. The ‘sendTextToServer’ method receives a string from the Client and calls the method ‘sendTextToClient’ to send the message to all the Clients.
This class also creates and writes a log file so errors can be monitored. When a Server object is created a method is called to create a new log file, ‘sendTextToServer’ and ‘sendLineToServer’ both write all the values they receive and the time it was received to the log file. 
ServerInterface and ClientInterface:
	These are in both the Server program and the Client program. On the server side, the Server interface class contains a list of the methods that the server can do to the client object. In this class the method bodies are empty, the code for what the method does is in the server class. This is the same on the client side for the client object and interface.
StartClient:
	This class creates the log in window and creates the ClientInterfance object (not to be confused with the class of the same name) and connects it to the Server object. A method called ‘runLoginWindow’ creates the GUI for the log in, the user must type a user name that will be used in the chat box and the IP address where the server is being run.
 
	The button text will change from “Connect” to “Disconnect” after a successful connection is made. When the button is pressed the mothed ‘doConnect’ creates new canvas and pen tool objects. It finds the server object and calls the method ‘login’ onto it then sends the serverInterface to the paint window and pen tool to establish a link. It then calls the method ‘runPaintWindow’ in the ‘paintWindow’ class to load the GUI.
Client:
	This class contains the code for the methods listed in the ClientInterface class. These are the methods the Server can perform on the Client object. The ‘sendTextToClient’ method receives a string of chat text from the server and sends it to be visible on the GUI, the method ‘sendLineToClient’ gets coordinates from the server and sends it to the drawing box object and the method ‘getName’ returns the name that the user has logged in with.
PaintWindow:
	This creates the GUI for the drawing box and chat feature. When the user is drawing it updates the color marker to the button the user has clicked on. It has a ‘postLineToGUI’ method for populating chat text received from the server. When text is typed in the bottom text field and the “Enter” button is pushed the method ‘sendText’ will concatenate the text with the user’s name and send it to the server to be distributed. 
PenTool:
	The PenTool class creates the canvas for drawing on and the ability to draw on it. In the same way as the first attempt that this program it creates Mouse Listeners and uses ‘mousePressed’, ‘mouseReleased’ and ‘mouseDragged’ to tell when the user is drawing. While the mouse is being dragged it collects the x and y coordinates and calls the method ‘sendLineToServer’ on the server object, sending the variable marker, previous x, previous y, x and y. It has the method ‘getLine’ that the ‘sendLineToClient’ method calls when the server is distributing information. It receives the marker, previous x, previous y, x and y from the other clients, uses the marker to set the color of the line and the coordinates to draw the line, only drawing one-pixel every time. 
	To follow the path of a line being drawn first, on the client side the user will click the left mouse button and drag their mouse in the canvas space. The x and y coordinates of the mouse are taken from the pen object and the ‘sendLineToServer’ method is called sending the pen color, x, y, previous x and previous y coordinates to the server. On the server side the ServerInterface class will check that ‘sendLineToServer’ is a method the client can do on the remote server object, since it can, it will run it. The method has a loop to run for every clientInterface object kept in the user array on the server, it will then run the method ‘sendLineToClient’ on each client interface. Back on the client side the ClientInterface class will check ‘sendLineToClient’ is an acceptable method the server can run on the client object, since it is, the method will be executed. It receives all the values from the server and calls the method ‘drawLine’ on the canvas object. In the canvas object this method will set the color and draw the line of one pixel long onto the canvas.
