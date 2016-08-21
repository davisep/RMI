import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientInterface{
    	
    	private String name;
    	StartClient sc;	
    	PaintWindow pw;
    	PenTool drawBox;
    	
    	//Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    	//Sets a username when the client object is created.
    	public Client (String clientName) throws RemoteException {
    		name=clientName;
    	}
    	
    	//Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    	//Lets the user know when a new client is connected.
    	public void sendTextToClient(String chatMessage) throws RemoteException{
    		pw.postLineToGUI(chatMessage);
    	}
    	
    	//Receives coordinates from the server and sends them to the canvas to be drawn.
    	public void sendLineToClient(Color clientColor, int prevX, int prevY, int x, int y) throws RemoteException{
    		drawBox.getLine(clientColor,prevX, prevY,x,y);
    	}
    	
    	//Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    	//Returns the username.
    	public String getName() throws RemoteException{
    		return name;
    	}
    	
    	//Receives the Clear All command from the server and relays it to the canvas to do.
    	public void sendClearAllToClient() throws RemoteException{
    		drawBox.clearAll();
    	}
    	
    	//Receives an eraser line from the server and sends it to the canvas to be done.
    	public void sendEraserToClient(int prevX, int prevY, int x, int y){
    		drawBox.clientEraser(prevX, prevY,x,y);
    	}
    }
