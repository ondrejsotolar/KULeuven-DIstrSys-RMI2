package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SessionManager implements RemoteSessionManager {
	public String createSession(String userName) throws RemoteException, AlreadyBoundException {

		RentalSession obj = new RentalSession(userName);
		RemoteRentalSession stub = (RemoteRentalSession) UnicastRemoteObject.exportObject(obj, 0);

		//TODO maybe check for duplicate users
		Registry registry = LocateRegistry.getRegistry();
        registry.bind(userName, stub);

		System.out.println("SERVER LOG: createSession returns: '"+userName+"' SUCCESS");
		return userName;
	}

	public String createManagerSession(String userName) throws RemoteException, AlreadyBoundException {

		ManagerSession obj = new ManagerSession(userName);
		RemoteManagerSession stub = (RemoteManagerSession) UnicastRemoteObject.exportObject(obj, 0);

		//TODO maybe check for duplicate users
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(userName, stub);

		System.out.println("SERVER LOG: createManagerSession returns: '"+userName+"' SUCCESS");
		return userName;
	}

}
