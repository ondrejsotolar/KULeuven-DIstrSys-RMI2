package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                //System.out.println("Reservation success for " + guest);
                return q;
            }
            catch (ReservationException e) {
                //System.out.println("ReservationExcepion for " + guest);
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
}
