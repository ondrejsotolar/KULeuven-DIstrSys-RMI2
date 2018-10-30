package client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.*;

public class Client extends AbstractTestManagement<RemoteRentalSession, RemoteManagerSession>{
	
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

            String sessionName = stub.createSession("rentalSession_"+name);
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
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            stub = (RemoteSessionManager) registry.lookup("sessionManager");

            String sessionName = stub.createManagerSession("managerSession_"+name);
            RemoteManagerSession session = (RemoteManagerSession) registry.lookup(sessionName);

            System.out.println("CLIENT LOG: getNewManagerSession returns:'" + sessionName + "' SUCCESS");
            return session;
        } catch (RemoteException e ) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        throw new Exception("CLIENT LOG: something went wrong in method: getNewManagerSession");
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
    protected String getCheapestCarType(RemoteRentalSession remoteRentalSession, Date start, Date end, String region) throws Exception {
        return remoteRentalSession.getCheapestCarType(start, end, region);
    }


    @Override
    protected int getNumberOfReservationsBy(RemoteManagerSession ms, String clientName) throws Exception {
        return ms.getNumberOfReservationsBy(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(RemoteManagerSession ms, String carRentalName, String carType) throws Exception {
        return ms.getNumberOfReservationsForCarType(carRentalName, carType);
    }

    @Override
    protected Set<String> getBestClients(RemoteManagerSession ms) throws Exception {
        return null;
    }


    @Override
    protected CarType getMostPopularCarTypeIn(RemoteManagerSession ms, String carRentalCompanyName, int year) throws Exception {
        return null;
    }
}