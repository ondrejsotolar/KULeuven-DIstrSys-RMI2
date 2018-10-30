package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerSession implements RemoteManagerSession {

    private String userName;

    public ManagerSession(String userName) throws RemoteException {
        super();

        this.userName = userName;
    }
    
    public int getNumberOfReservationsBy(String clientName) throws RemoteException {
    	int counter = 0;
    	
    	for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            counter += carRentalCompany.getReservationsByRenter(clientName).size();
        }
       
    	return counter;
    }
    
    public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException {
    	CarRentalCompany carRentalCompany = RentalServer.rentalCompanies.get(carRentalName);
    	return carRentalCompany.getNbReservationsByCarType(carType);
    }
    
    
}
