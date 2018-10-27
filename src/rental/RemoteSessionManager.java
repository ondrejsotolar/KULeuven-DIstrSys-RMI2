package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSessionManager extends Remote {
	String createSession(String userName) throws RemoteException, AlreadyBoundException;
}
