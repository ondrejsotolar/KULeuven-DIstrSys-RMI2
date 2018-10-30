package client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

import rental.*;

public class Client extends AbstractTestAgency<RemoteRentalSession, RemoteManagerSession>{
	
	/********
	 * MAIN *
	 ********/
	public static void main(String[] args) throws Exception {
		
//		String carRentalCompanyName = "Hertz";
//		Client c1 = new Client("peter");
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		Client client = new Client("trips");
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR *
	 ***************/
	RemoteSessionManager stub;

    public Client(String scriptFile) throws AlreadyBoundException {
		super(scriptFile);
	}

    @Override
    protected RemoteRentalSession getNewReservationSession(String name) throws Exception {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            stub = (RemoteSessionManager) registry.lookup("sessionManager");

            String sessionName = stub.createSession(name);
            RemoteRentalSession session = (RemoteRentalSession) registry.lookup(sessionName);

            System.out.println("CLIENT LOG: getNewReservationSession returns:'" + sessionName + "' SUCCESS");
            return session;
        } catch (RemoteException e ) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        throw new Exception("CLIENT LOG: something went wrong in method: getNewReservationSession");
    }

    @Override
    protected RemoteManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
        return null;
    }

    @Override
    protected void checkForAvailableCarTypes(RemoteRentalSession remoteRentalSession, Date start, Date end) throws Exception {
        remoteRentalSession.checkForAvailableCarTypes(start, end);
        System.out.println("CLIENT LOG: checkForAvailableCarTypes SUCCESS");
    }

    @Override
    protected void addQuoteToSession(RemoteRentalSession remoteRentalSession, String name, Date start, Date end, String carType, String region) throws Exception {
        ReservationConstraints rc = new ReservationConstraints(start, end, carType, region);
        if (carType.equals("Premium")) {
            System.out.println(carType + " " + region + " AAAAAAAAAAAAAAAAAAAAAAa");
        }
        remoteRentalSession.createQuote(rc, name);
    }

    @Override
    protected List<Reservation> confirmQuotes(RemoteRentalSession remoteRentalSession, String name) throws Exception {
        return remoteRentalSession.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsBy(RemoteManagerSession ms, String clientName) throws Exception {
        return 0;
    }

    @Override
    protected int getNumberOfReservationsForCarType(RemoteManagerSession ms, String carRentalName, String carType) throws Exception {
        return 0;
    }

//	public Client(String scriptFile, String carRentalCompanyName) {
//		//super(scriptFile);
//
//		Registry registry;
//		try {
//			registry = LocateRegistry.getRegistry();
//			stub = (RemoteRentalServer) registry.lookup("RemoteCarRentalCompany");
//			String response = stub.getName();
//			System.out.println("response " + response);
//		} catch (RemoteException e ) {
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			e.printStackTrace();
//		}
//
//	}
	
//	/**
//	 * Check which car types are available in the given period
//	 * and print this list of car types.
//	 *
//	 * @param 	start
//	 * 			start time of the period
//	 * @param 	end
//	 * 			end time of the period
//	 * @throws 	Exception
//	 * 			if things go wrong, throw exception
//	 */
//	@Override
//	protected void checkForAvailableCarTypes(Date start, Date end) throws Exception {
//		System.out.println(stub.getAvailableCarTypes(start, end));
//	}
//
//	/**
//	 * Retrieve a quote for a given car type (tentative reservation).
//	 * 
//	 * @param	clientName 
//	 * 			name of the client 
//	 * @param 	start 
//	 * 			start time for the quote
//	 * @param 	end 
//	 * 			end time for the quote
//	 * @param 	carType 
//	 * 			type of car to be reserved
//	 * @param 	region
//	 * 			region in which car must be available
//	 * @return	the newly created quote
//	 *  
//	 * @throws 	Exception
//	 * 			if things go wrong, throw exception
//	 */
//	@Override
//	protected Quote createQuote(String clientName, Date start, Date end,
//			String carType, String region) throws Exception {
//		ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
//		return stub.createQuote(constraints, clientName);
//	}
//
//	/**
//	 * Confirm the given quote to receive a final reservation of a car.
//	 * 
//	 * @param 	quote 
//	 * 			the quote to be confirmed
//	 * @return	the final reservation of a car
//	 * 
//	 * @throws 	Exception
//	 * 			if things go wrong, throw exception
//	 */
//	@Override
//	protected Reservation confirmQuote(Quote quote) throws Exception {
//		return stub.confirmQuote(quote);
//	}
//	
//	/**
//	 * Get all reservations made by the given client.
//	 *
//	 * @param 	clientName
//	 * 			name of the client
//	 * @return	the list of reservations of the given client
//	 * 
//	 * @throws 	Exception
//	 * 			if things go wrong, throw exception
//	 */
//	@Override
//	protected List<Reservation> getReservationsByRenter(String clientName) throws Exception {
//		return stub.getReservationsByRenter(clientName);
//	}
//
//	/**
//	 * Get the number of reservations for a particular car type.
//	 * 
//	 * @param 	carType 
//	 * 			name of the car type
//	 * @return 	number of reservations for the given car type
//	 * 
//	 * @throws 	Exception
//	 * 			if things go wrong, throw exception
//	 */
//	@Override
//	protected int getNumberOfReservationsForCarType(String carType) throws Exception {
//		return stub.getNbReservationsByCarType(carType);
//	}
}