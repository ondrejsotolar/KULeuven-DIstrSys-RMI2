package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ManagerSession implements RemoteManagerSession {

    private String userName;

    public ManagerSession(String userName) throws RemoteException {
        super();

        this.userName = userName;
    }
}
