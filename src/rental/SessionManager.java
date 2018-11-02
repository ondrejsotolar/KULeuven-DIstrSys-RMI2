package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class SessionManager implements RemoteSessionManager {
	public String createSession(String sessionName) throws RemoteException, AlreadyBoundException {

		RentalSession obj = new RentalSession(sessionName);
		RemoteRentalSession stub = (RemoteRentalSession) UnicastRemoteObject.exportObject(obj, 0);

		Registry registry = LocateRegistry.getRegistry();
        registry.bind(sessionName, stub);

		return sessionName;
	}

	public String createManagerSession(String userName) throws RemoteException, AlreadyBoundException {

		Random rand = new Random();
		int  salt = rand.nextInt(50) + 1;
		userName += salt;

		ManagerSession obj = new ManagerSession(userName);
		RemoteManagerSession stub = (RemoteManagerSession) UnicastRemoteObject.exportObject(obj, 0);

		Registry registry = LocateRegistry.getRegistry();
		registry.bind(userName, stub);

		return userName;
	}

}
