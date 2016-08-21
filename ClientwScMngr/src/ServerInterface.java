import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;

//A list of methods the client can do to the server.
public interface ServerInterface extends Remote{
	public boolean login (ClientInterface a)throws RemoteException ;
	public void sendTextToServer(String s)throws RemoteException ;
	public void sendLineToServer(Color penColor, int prevX,int prevY,int x,int y)throws RemoteException;
	public void sendClearAllToServer()throws RemoteException;
	public void exitClient(ClientInterface tmp, String name) throws RemoteException;
	public void sendEraserToServer(int prevX, int prevY, int x, int y)throws RemoteException;
}
