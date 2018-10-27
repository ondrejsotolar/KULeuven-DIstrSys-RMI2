package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface RemoteRentalSession extends Remote {

	Quote createQuote(ReservationConstraints constraints, String client)
			throws ReservationException, RemoteException, Exception;
	
    Collection<Quote> getCurrentQuotes() throws RemoteException;
    
    List<Reservation> confirmQuotes() throws ReservationException, RemoteException;
}
