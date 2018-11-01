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

//        m2.forEach((k, v) -> m.merge(k, v, (v1, v2) -> v1 + v2));

        for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            carRentalCompany.getRentersWithNbReservations().forEach( (k,v) ->
                rentersWithTotNbReservations.merge(k, v, (v1,v2) -> v1 + v2));
        }

        String bestClient = "";
        int highestNbReservations = 0;
        for (Map.Entry<String, Integer> entry: rentersWithTotNbReservations.entrySet()) {
            if (entry.getValue() > highestNbReservations) {
                highestNbReservations = entry.getValue();
                bestClient = entry.getKey();
            }
        }
        Set<String> bestClients = Collections.emptySet();
        bestClients.add(bestClient);
        return bestClients;
    }

//    CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year) {
//
//    }
    
}
