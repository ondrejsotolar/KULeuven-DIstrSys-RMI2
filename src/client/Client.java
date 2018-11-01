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

    RemoteSessionManager stub;

	/********
	 * MAIN *
	 ********/
	public static void main(String[] args) throws Exception {
		Client client = new Client("trips");
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR *
	 ***************/
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
            return (RemoteManagerSession) registry.lookup(sessionName);
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
    }

    @Override
    protected void addQuoteToSession(RemoteRentalSession remoteRentalSession, String name, Date start, Date end, String carType, String region) throws Exception {
        ReservationConstraints rc = new ReservationConstraints(start, end, carType, region);
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
        return ms.getBestClients();
    }


    @Override
    protected CarType getMostPopularCarTypeIn(RemoteManagerSession ms, String carRentalCompanyName, int year) throws Exception {
        return ms.getMostPopularCarTypeIn(carRentalCompanyName, year);
    }
}