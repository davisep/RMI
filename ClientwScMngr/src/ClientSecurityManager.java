import java.rmi.RMISecurityManager;
import java.security.Permission;

//Does not do anything
public class ClientSecurityManager extends RMISecurityManager {
	public void checkPermission(Permission p) {
	// Can be use later to change permissions.
	}
}