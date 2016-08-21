import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

//The server extends UnicastRemoteObject so it can be added to the RMIR. The Server Interface is the methods that can be done on the server object.
public class Server extends UnicastRemoteObject implements ServerInterface{
    	//Vector is used because it is synchronized. It has a cap of 20 connections.
    	Vector userVector= new Vector(20);
    	//A log is used to store clients' actions.
    	Writer serverLog = null;
    	
    	//Creates a new log file and gets port when server object is created.
    	public Server() throws RemoteException{
    		//Gets the port from the RMIR for the object
    		super(Registry.REGISTRY_PORT);
    		newLogFile();
    	}
    	
    	//Creates a new log file.
		public void newLogFile(){
			//This sets the path for the LogFile.
	        Path path = FileSystems.getDefault().getPath("C:/Users/Beth/RMI/Server", "ServerLogFile.txt");

	        //Deletes the log file if an old one already exists.
	        try {
	            boolean delete = Files.deleteIfExists(path);
	            System.out.println("Previous log deleted: " + delete);
	        } catch (IOException | SecurityException e) {
	            System.out.println("Error 2 Class Server: "+e);
	        }  
			try {
			    serverLog = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream("ServerLogFile.txt"), "utf-8"));
			} catch (IOException ex) {
				System.out.println("Error 3 Class Server: "+ex);
			} catch (Exception e) {
				System.out.println("Error 4 Class Server: "+e);
			}
		}
		
		//Writes a string to the log file.
		public void writeLog(String line){
			//Appends date and time to lines written to log file.
			DateFormat dateFormat = new SimpleDateFormat("\n dd/MM/yy/ HH:mm:ss");
		    Date date = new Date();
			try {
				serverLog.write(dateFormat.format(date)+" "+line+"\r\n");
				serverLog.flush();
			} catch (IOException e) {
				System.out.println("Error 5 Class Server: "+e);
			}
		} 
  
		//Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
		//Notifies other user of a new client and add it to the vector. 
    	public boolean login(ClientInterface tempCI) throws RemoteException{
    		String line = tempCI.getName()+ "  is connected.";
    		writeLog(line);	
    		tempCI.sendTextToClient("You have Connected successfully.");
    		sendTextToServer(tempCI.getName()+ " has just connected.");
    		userVector.add(tempCI);
    		return true;		
    	}
    	
    	//Code from http://www.ejbtutorial.com/java-rmi/group-chat-example-using-java-rmi-with-a-graphical-user-interface
    	//Sends the received chat line from login to all the connected clients. (Is only used by login)
    	public void sendTextToServer(String tempS) throws RemoteException{
    		String line = tempS+"\r\n";
    		writeLog(line);
    		for(int i=0;i<userVector.size();i++){
    			if (userVector.get(i) != null){
	    		    try{
	    		    	ClientInterface tempCI=(ClientInterface)userVector.get(i);
	    		    	tempCI.sendTextToClient(tempS);
	    		    }catch(Exception e){
	    		    	System.out.println("Error 6 Class Server: "+e);
	    		    }
	    		}
    		}
    	}
    	
    	//Receives a chat line from one of the clients
    	public void sendLineToServer(Color penColor, int prevX, int prevY, int x, int y) throws RemoteException{
    		String line = String.format("Drawline: prevX: %d, prevY: %d, x: %d, y: %d"+ '\n',  prevX, prevY, x, y);
    		writeLog(line);
    		for(int i=0;i<userVector.size();i++){
    			if (userVector.get(i) != null){
	    		    try{
	    		    	ClientInterface tempCI=(ClientInterface)userVector.get(i);
	    		    	tempCI.sendLineToClient(penColor, prevX, prevY, x, y);
	    		    }catch(Exception e){
	    		    	System.out.println("Error 7 Class Server: "+e);
	    		    }
	    		}
    		}
    	}

		//Is called when a client closes the window to delete their ClientInterface out of the vector. This prevent errors.
    	public void exitClient(ClientInterface tempCI, String name){
    		String tempS= name+" has disconnected from the server.";
    		try {
    			sendTextToServer(tempS);
			} catch (RemoteException e) {
				System.out.println("Error 8 Class Server: "+e);
			}
    		userVector.remove(tempCI);
    	}

		//Sends the Clear All command to all clients
    	public void sendClearAllToServer() throws RemoteException{
    		writeLog("Clear all");
    		for(int i=0;i<userVector.size();i++){
    			if (userVector.get(i) != null){
	    		    try{
	    		    	ClientInterface tmp=(ClientInterface)userVector.get(i);
	    		    	tmp.sendClearAllToClient();
	    		    }catch(Exception e){
	    		    	System.out.println("Error 9 Class Server: "+e);
	    		    }
	    		}
    		}
    	}

		//Get an eraser command from a client using it ad sends it to the other clients
		public void sendEraserToServer(int prevX, int prevY, int x, int y) {
			for(int i=0;i<userVector.size();i++){
    			if (userVector.get(i) != null){
	    		    try{
	    		    	ClientInterface tempCI=(ClientInterface)userVector.get(i);
	    		    	tempCI.sendEraserToClient(prevX, prevY, x, y);
	    		    }catch(Exception e){
	    		    	System.out.println("Error 10 Class Server: "+e);
	    		    }
	    		}
    		}
			
		}
    }