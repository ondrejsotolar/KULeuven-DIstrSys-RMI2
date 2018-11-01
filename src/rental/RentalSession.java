package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RentalSession implements RemoteRentalSession {

	private String userName;
    private List<Quote> quoteStore;
    
    public RentalSession(String userName) throws RemoteException {
        super();

        this.userName = userName;
    	this.quoteStore = new ArrayList<>();
        
    }

    public Quote createQuote(ReservationConstraints constraints, String guest) throws Exception {
   	   	for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            try {
            	System.out.println("Checking at company " + carRentalCompany.getName() + " for " + constraints.getCarType() );
            	Quote q = carRentalCompany.createQuote(constraints, guest);
                
                this.quoteStore.add(q);
                return q;
            }
            catch (ReservationException e) {
                System.out.println("SERVER LOG: RentalSession: ReservationExcepion for " + guest);
            }
        }
        throw new ReservationException("ReservationException: no car matches constraints." + guest);
    }

    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> successfulReservations = new ArrayList<>();
        
        for (Quote q : quoteStore) {
            try {
                CarRentalCompany carRentalCompany = RentalServer.rentalCompanies.get(q.getRentalCompany());
                Reservation r = carRentalCompany.confirmQuote(q);
                successfulReservations.add(r);
            }
            catch (ReservationException e) {
                for (Reservation r : successfulReservations) {
                    CarRentalCompany carRentalCompany = RentalServer.rentalCompanies.get(r.getRentalCompany());
                    carRentalCompany.cancelReservation(r);
                }
                throw new ReservationException("ReservationException: can't confirm quote.");
            }
        }
        return successfulReservations;
    }

    public void checkForAvailableCarTypes(Date start, Date end) throws Exception {
        boolean isAvailable = false;

        for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            Set<CarType> availableCarTypes = carRentalCompany.getAvailableCarTypes(start, end);

            if (availableCarTypes != null && availableCarTypes.size() >= 1) {
                isAvailable = true;
            }

            if (!isAvailable) {
                throw new Exception("No car is available.");
            }
        }
    }

    public String getCheapestCarType(Date start, Date end, String region) {
        double cheapestCarPrice = Double.MAX_VALUE;
        String cheapestCarType = "";

        for (CarRentalCompany carRentalCompany : RentalServer.rentalCompanies.values()) {
            Set<CarType> availableCarTypes = carRentalCompany.getAvailableCarTypes(start, end);

            if (availableCarTypes == null || availableCarTypes.size() < 1) {
                continue;
            }

            for (CarType type : availableCarTypes) {
                if (type.getRentalPricePerDay() < cheapestCarPrice) {
                    cheapestCarPrice = type.getRentalPricePerDay();
                    cheapestCarType = type.getName();
                }
            }
        }
        return cheapestCarType;
    }
}
