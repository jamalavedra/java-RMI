import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.spec.SecretKeySpec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class HelloServant extends UnicastRemoteObject implements HelloService {
    private static final  String keyString="1122334455667788";
	public HelloServant() throws RemoteException {
        super();
    }

	Database db;
	Statement stm;
    ResultSet res;
    
    @Override
	public String echo(String input) throws RemoteException {
    // initial test
        return "From server: " + input;
    }

    @Override
	public String print(String token, String filename, String printer) throws Exception, RemoteException{
    // prints file filename on the specified printer
    	String funcName ="print";    	
        String user = CheckToken(token, funcName);
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- print");           
            return "print: " + filename + "in " + printer; 
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String queue(String token, String printer) throws Exception, RemoteException{
    // lists the print queue on the user's display in lines of the form <job number>   <file name>
    	String funcName ="queue";
    	String user = CheckToken(token, funcName); 
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- queue");
            return "queue: " + printer;
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String topQueue(String token, int job) throws Exception, RemoteException{
    	String funcName ="topQueue";
    	String user = CheckToken(token,funcName); 
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- topQueue");
            return "topQueue: " + job;
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String start(String token) throws Exception, RemoteException{
    // starts the print server
    	String funcName ="start";
        String user = CheckToken(token, funcName); 
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- start");
            return "start";
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String stop(String token) throws Exception, RemoteException{
    // stops the print server
    	 String funcName ="stop";
        String user = CheckToken(token, funcName); 
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- stop");
            return "stop";
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String restart(String token) throws Exception, RemoteException{
    // stops the print server, clears the print queue and starts the print server again
    	String funcName ="restart";
    	String user = CheckToken(token, funcName);
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- restart");
            return "restart";
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String status(String token, String printer) throws Exception, RemoteException{
    // prints status of printer on the user's display
    	String funcName ="status";
    	String user = CheckToken(token,funcName);
        if(user != "false" && checkRole(token,funcName) == true){
            System.out.println(user + "--- status");
            return "status: " + printer;
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String readConfig(String token, String parameter) throws Exception, RemoteException{
    // prints the value of the parameter on the user's display
    	String funcName ="readConfig";
    	String user = CheckToken(token, funcName); 
        if(user != "false"  && checkRole(token,funcName) == true){
            System.out.println(user + "--- readConfig");
            return "readConfig: " + parameter;
        }
        else{
            return "User not authenticated or authorised to run this operation";
        }
    }
    @Override
	public String setConfig(String token, String parameter, String value) throws Exception, RemoteException{
    // sets the parameter to value
        String funcName ="setConfig";
        String user = CheckToken(token, funcName);
        if(user != "false"  && checkRole(token,funcName) == true){
            System.out.println(user + "--- setConfig");
            return "setConfig: " + parameter + " to " + value;
        }
        else{
            return "User not authenticated or authorised to run this operation";
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
    public String CheckToken(String encToken, String method) throws Exception, RuntimeException{
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
    public boolean checkRole(String encToken,String methodName)throws Exception, RuntimeException{
      	 CryptoHelper crypto = new CryptoHelper();
      	 SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
      	String token = crypto.decrypt(encToken.toString(), key);
          String [] parts = token.split( ":" );
          String user = parts[0];         
          String query ="SELECT EXISTS(SELECT * FROM data INNER JOIN Permissions "
        	        + "ON data.Role = Permissions.Permission "
        	        + "WHERE Username = ? AND Function = ?)";
          System.out.println(query);
        	  try (Connection conn = this.connect();PreparedStatement stm = conn.prepareStatement(query)) {
        	      stm.setString(1, user);
        	      stm.setString(2, methodName);
        	      try (ResultSet res = stm.executeQuery()) {
        	          if (res.next()) {           
        	              boolean exists = res.getBoolean(1);
        	              return exists;
        	          }       	          	
       }
     }catch(Exception e){
         System.out.println(e);
        }
     return false;
   }
    
    
    private Connection connect() {
    	final String userDB = "root";
    	final String passDB = "hello";
    	final String connection = "jdbc:mysql://192.168.99.100:3306/DB?useSSL=false";
        Connection c = null;
        try {
        	c = DriverManager.getConnection(connection, userDB, passDB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
  }

