import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;

//A list of methods the server can do to the client
public interface ClientInterface extends Remote{
    	public void sendTextToClient(String name)throws RemoteException ;
    	public String getName()throws RemoteException ;
    	public void sendClearAllToClient()throws RemoteException;
    	public void sendLineToClient(Color clientColor, int prevX, int prevY, int x, int y) throws RemoteException;
    	public void sendEraserToClient(int prevX, int prevY, int x, int y) throws RemoteException;

    }