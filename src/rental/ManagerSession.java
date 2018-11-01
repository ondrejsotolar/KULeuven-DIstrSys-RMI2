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
        Map<String,Integer> counter = new HashMap<>();

        for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            for (Map.Entry<String, Integer> entry : carRentalCompany.getRentersWithNbReservations().entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                counter.putIfAbsent(key, 0);
                counter.put(key, counter.get(key) + value);
            }
        }
        int max = 0;
        Set<String> names = new HashSet<>();
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            String name = entry.getKey();
            Integer value = entry.getValue();

            if (value > max) {
                max = value;
                names.clear();
                names.add(name);
            } else if (value == max) {
                names.add(name);
            }
        }
        return names;
    }
}
