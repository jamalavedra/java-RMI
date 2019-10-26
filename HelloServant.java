import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;


public class HelloServant extends UnicastRemoteObject implements HelloService {
	public HelloServant() throws RemoteException {
        super();
    }

    @Override
	public String echo(String input) throws RemoteException {
    // initial test
        return "From server: " + input;
    }
    @Override
	public String print(String filename, String printer) throws RemoteException {
    // prints file filename on the specified printer
        return "print: " + filename + "in " + printer;
    }
    @Override
	public String queue() throws RemoteException{
    // lists the print queue on the user's display in lines of the form <job number>   <file name>
        return "queue";
    }
    @Override
	public String topQueue(int job) throws RemoteException{
    // moves job to the top of the queue
        return "TopQueue: " + job;
    }
    @Override
	public String start() throws RemoteException {
    // starts the print server
        return "start";
    }
    @Override
	public String stop() throws RemoteException {
    // stops the print server
        return "stop";
    }
    @Override
	public String restart() throws RemoteException {
    // stops the print server, clears the print queue and starts the print server again
        return "restart";
    }
    @Override
	public String status() throws RemoteException{
    // prints status of printer on the user's display
        return "status";
    }
    @Override
	public String readConfig(String parameter) throws RemoteException{
    // prints the value of the parameter on the user's display
        return "readConfig: " + parameter;
    }
    @Override
	public String setConfig(String parameter, String value) throws RemoteException{
    // sets the parameter to value
    return "setConfig: " + parameter + " to " + value;
    }
    @Override
	public String Login(String username, String password) throws RemoteException{
	    if (IsValidUser(username,password) == true) {
	    	return IssueToken(username);
	    } 	
    	else {
    		echo("Invalid username or password");
    		return null;
    	}
	} 
    @Override
    public boolean RequestService(String token) throws RemoteException{
	   	 if (IsValidUser(DecodeToken(token))) {
	   		 return true;
	   	 }
	   	 else {
	   		 return false;
	   	 }
    }
    public boolean IsValidUser(String username, String password) throws RuntimeException{    	 
    	 if (username == "testUser" && password == "pass")
    		 return true;
    	 else
    		 return false; 	 
     }
     public boolean IsValidUser(String username) throws RuntimeException{    	 
    	 if (username == "testUser")
    		 return true;
    	 else
    		 return false;
     } 
     public String IssueToken(String username) throws RuntimeException{
	    try {   	  
	    	 return Base64.getEncoder().encodeToString(username.getBytes());
	    } 
	    catch (Exception ex) {
	    	 ex.printStackTrace();
	    } 
     return null;
     }         
     public String DecodeToken(String token) throws RuntimeException{
    	 byte[] decodedBytes = Base64.getDecoder().decode(token);
    	 return new String(decodedBytes);
     }  
}

