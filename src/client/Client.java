package client;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

import rental.Quote;
import rental.RemoteCarRentalCompany;
import rental.RemoteRentalServer;
import rental.RemoteRentalSession;
import rental.RemoteSessionManager;
import rental.Reservation;
import rental.CarType;
import rental.ReservationConstraints;

public class Client {
	
	/********
	 * MAIN *
	 ********/
	
	public static void main(String[] args) throws Exception {
		
		String carRentalCompanyName = "Hertz";
		Client c1 = new Client("peter");
		
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		//Client client = new Client("simpleTrips", carRentalCompanyName);
		//client.run();
	}
	
	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	RemoteSessionManager stub;
	
	public Client(String userName) throws AlreadyBoundException {
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry();
			stub = (RemoteSessionManager) registry.lookup("sessionManager");
			String sessionName = stub.createSession(userName);
			RemoteRentalSession session = (RemoteRentalSession) registry.lookup(sessionName);
			
			
		} catch (RemoteException e ) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
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