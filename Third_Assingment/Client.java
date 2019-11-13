import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.spec.SecretKeySpec;



public class Client
{
    private static final  String keyString="1234567890123456";
    public static void main( String[] args ) throws Exception
    {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println(" ---" + service.echo("Server responding correctly"));

        String userName = "Mike";
        String password = "ThisTheFirstPassword";
        // PasswordStorage passManager = new PasswordStorage();
        // passManager.signUp(userName, password);
        boolean login = checkDatabase(userName, password);
        if(login){
            System.out.println("Welcome " + userName);
            String sessionToken = service.issueToken(userName, password);
            System.out.println(" ---" + service.print(sessionToken, "filname ", "printer"));
            System.out.println(" ---" + service.queue(sessionToken));
            System.out.println(" ---" + service.topQueue(sessionToken, 2));
            System.out.println(" ---" + service.start(sessionToken));
            System.out.println(" ---" + service.stop(sessionToken));
            System.out.println(" ---" + service.restart(sessionToken));
            System.out.println(" ---" + service.status(sessionToken));
            System.out.println(" ---" + service.readConfig(sessionToken, "param"));
            System.out.println(" ---" + service.setConfig(sessionToken, "param", "Value"));
        }

    }

    public static boolean checkDatabase(String user,String pwd)throws Exception, RemoteException, SQLException{

        CryptoHelper crypto = new CryptoHelper();
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        String encriptedUser = crypto.encrypt(user, key);
        String encriptedPass = crypto.encrypt(pwd, key);

    	final String userDB = "dataUser";
    	final String passDB = "password";
    	final String connection = "jdbc:mysql://localhost:3306/data_security?useSSL=false";
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        boolean userAuthenticated = false;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(connection, userDB, passDB);
            s = c.createStatement();

            Database db = new Database(s, rs);

            // System.out.println("Successfully connected to the dB");
            userAuthenticated = db.authenticateUser(encriptedUser, encriptedPass);

            if(!userAuthenticated){
                System.out.println("Wrong username and/or password!");
            }

        }
        catch(Exception e){
            System.out.print(e);
        }
        finally {
            if(c != null){
                c.close();
            }
        }



     return userAuthenticated;
 }
}
