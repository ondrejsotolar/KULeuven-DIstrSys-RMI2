package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface RemoteManagerSession extends Remote {
	
	int getNumberOfReservationsBy(String clientName) throws RemoteException;
	
	int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException;

	Set<String> getBestClients() throws RemoteException;

	CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year);

}
