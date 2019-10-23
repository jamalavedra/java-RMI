import java.rmi.Remote;
import java.rmi.RemoteException;


//this is an interface

public interface HelloService extends Remote {

    public String echo(String input) throws RemoteException;
}
