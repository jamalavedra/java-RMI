import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.spec.SecretKeySpec;


public class HelloServant extends UnicastRemoteObject implements HelloService {
    private static final  String keyString="1122334455667788";
	public HelloServant() throws RemoteException {
        super();
    }

    @Override
	public String echo(String input) throws RemoteException {
    // initial test
        return "From server: " + input;
    }
    @Override
	public String print(String token, String filename, String printer) throws Exception, RemoteException{
    // prints file filename on the specified printer
        if(CheckToken(token)){
            return "print: " + filename + "in " + printer;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String queue(String token) throws Exception, RemoteException{
    // lists the print queue on the user's display in lines of the form <job number>   <file name>
        if(CheckToken(token)){
            return "queue";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String topQueue(String token, int job) throws Exception, RemoteException{
        if(CheckToken(token)){
            return "TopQueue: " + job;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String start(String token) throws Exception, RemoteException{
    // starts the print server
        if(CheckToken(token)){
            return "start";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String stop(String token) throws Exception, RemoteException{
    // stops the print server
        if(CheckToken(token)){
            return "stop";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String restart(String token) throws Exception, RemoteException{
    // stops the print server, clears the print queue and starts the print server again
        if(CheckToken(token)){
            return "restart";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String status(String token) throws Exception, RemoteException{
    // prints status of printer on the user's display
        if(CheckToken(token)){
            return "status";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String readConfig(String token, String parameter) throws Exception, RemoteException{
    // prints the value of the parameter on the user's display
        if(CheckToken(token)){
            return "readConfig: " + parameter;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String setConfig(String token, String parameter, String value) throws Exception, RemoteException{
    // sets the parameter to value
        if(CheckToken(token)){
            return "setConfig: " + parameter + " to " + value;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    public String issueToken(String username, String password) throws Exception, RemoteException{
        CryptoHelper crypto = new CryptoHelper();
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        try {
            StringBuilder token = new StringBuilder();
            token.append(username);
            token.append(":");
            token.append( password);
            return crypto.encrypt(token.toString(), key);
            // return token.toString();
        }
        catch (Exception ex) {
             ex.printStackTrace();
        }
    return null;
    }
    public boolean CheckToken(String encToken) throws Exception, RuntimeException{
        CryptoHelper crypto = new CryptoHelper();
        Client isValid = new Client();
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        try {
            String token = crypto.decrypt(encToken.toString(), key);
            String [] parts = token.split( ":" );
            String user = parts[0];
            String password = parts[1];
            if(isValid.checkDatabase(user, password)){
                return true;
            }
            else{
                return false;
            }

        }
        catch (Exception ex) {
             ex.printStackTrace();
        }
    return false;
    }
}
