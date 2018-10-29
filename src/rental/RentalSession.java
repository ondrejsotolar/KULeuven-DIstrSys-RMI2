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
                Quote q = carRentalCompany.createQuote(constraints, guest);
                this.quoteStore.add(q);
                System.out.println("SERVER LOG: RentalSession: Reservation success for " + guest);
                return q;
            }
            catch (ReservationException e) {
                System.out.println("SERVER LOG: RentalSession: ReservationExcepion for " + guest);
                //e.printStackTrace();
            }
        }
        throw new ReservationException("ReservationException: no car matches constraints." + guest);
    }
    
    public Collection<Quote> getCurrentQuotes() {
        return this.quoteStore;
    }
    
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> successfulReservations = new ArrayList();
        
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

            if (availableCarTypes == null || availableCarTypes.size() < 1) {

            }
            else {
                isAvailable = true;
            }

            if (!isAvailable) {
                throw new Exception("No car is available.");
            }
        }
    }
}
