import java.rmi.RMISecurityManager;
import java.security.Permission;

//Currently does not do anything but can be used later to change permissions.
public class ClientSecurityManager extends RMISecurityManager {
	public void checkPermission(Permission p) {
	}
}