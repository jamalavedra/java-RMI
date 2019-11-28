import java.rmi.Remote;
import java.rmi.RemoteException;


//this is an interface

public interface HelloService extends Remote {

    public String echo(String input) throws RemoteException;
// initial test
    public String print(String token, String filename, String printer) throws Exception, RemoteException;
// prints file filename on the specified printer
    public String queue(String token, String printer) throws Exception, RemoteException;
// lists the print queue on the user's display in lines of the form <job number>   <file name>
    public String topQueue(String token, int job) throws Exception, RemoteException;
// moves job to the top of the queue
    public String start(String token) throws Exception, RemoteException;
// starts the print server
    public String stop(String token) throws Exception, RemoteException;
// stops the print server
    public String restart(String token) throws Exception, RemoteException;
// stops the print server, clears the print queue and starts the print server again
    public String status(String token, String printer) throws Exception, RemoteException;
// prints status of printer on the user's display
    public String readConfig(String token, String parameter) throws Exception, RemoteException;
// prints the value of the parameter on the user's display
    public String setConfig(String token, String parameter, String value) throws Exception, RemoteException;
// sets the parameter to value
    public String issueToken(String username, String password) throws Exception, RemoteException;
// requests a new session
}