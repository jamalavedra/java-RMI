import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;

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
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- print");
            return "print: " + filename + "in " + printer;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String queue(String token) throws Exception, RemoteException{
    // lists the print queue on the user's display in lines of the form <job number>   <file name>
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- queue");
            return "queue" ;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String topQueue(String token, int job) throws Exception, RemoteException{
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- topQueue");
            return "topQueue: " + job;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String start(String token) throws Exception, RemoteException{
    // starts the print server
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- start");
            return "start";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String stop(String token) throws Exception, RemoteException{
    // stops the print server
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- stop");
            return "stop";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String restart(String token) throws Exception, RemoteException{
    // stops the print server, clears the print queue and starts the print server again
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- restart");
            return "restart";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String status(String token) throws Exception, RemoteException{
    // prints status of printer on the user's display
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- status");
            return "status";
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String readConfig(String token, String parameter) throws Exception, RemoteException{
    // prints the value of the parameter on the user's display
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- readConfig");
            return "readConfig: " + parameter;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    @Override
	public String setConfig(String token, String parameter, String value) throws Exception, RemoteException{
    // sets the parameter to value
        String user = CheckToken(token);
        if(user != "false"){
            System.out.println(user + "--- setConfig");
            return "setConfig: " + parameter + " to " + value;
        }
        else{
            return "User not authenticated. Corrupted Token";
        }
    }
    public String issueToken(String username, String password) throws Exception, RemoteException{
        CryptoHelper crypto = new CryptoHelper();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        try {
            StringBuilder token = new StringBuilder();
            token.append(username);
            token.append(":");
            token.append( password);
            token.append(":");
            long current_time = timestamp.getTime();
            token.append( String.valueOf(current_time));
            return crypto.encrypt(token.toString(), key);
        }
        catch (Exception ex) {
             ex.printStackTrace();
        }
    return null;
    }
    private long ConvertIntoNumeric(String xVal)
    {
        try
        {
            return Long.parseLong(xVal);
        }
        catch(NumberFormatException ex)
        {
            System.out.println(ex);
            return 0;
        }
    }
    public String CheckToken(String encToken) throws Exception, RuntimeException{
        CryptoHelper crypto = new CryptoHelper();
        Client isValid = new Client();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        try {
            String token = crypto.decrypt(encToken.toString(), key);
            String [] parts = token.split( ":" );
            String user = parts[0];
            String password = parts[1];
            String time = parts[2];
            long time_int = ConvertIntoNumeric(time);
            long diff = timestamp.getTime() - time_int; // 5 min of valid token
            if(isValid.checkDatabase(user, password)&&(diff<300000)){
                return user;
            }
            else{
                return "false";
            }

        }
        catch (Exception ex) {
             ex.printStackTrace();
        }
    return "false";
    }
}
