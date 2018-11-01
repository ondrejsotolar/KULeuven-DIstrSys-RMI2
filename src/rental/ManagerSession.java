package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.*;

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

    public Set<String> getBestClients() throws RemoteException {
        Map<String,Integer> rentersWithTotNbReservations = new HashMap<>();

        for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            int max = 0;
            String name = "";
            for (Map.Entry<String, Integer> entry : carRentalCompany.getRentersWithNbReservations().entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (value > max) {
                    max = value;
                    name = key;
                }
                System.out.println("SRVER LOG: getBestClients: " + key + " " + value.toString());
            }
            rentersWithTotNbReservations.put(name,max);
        }
        return new HashSet<>(rentersWithTotNbReservations.keySet());
    }
}
