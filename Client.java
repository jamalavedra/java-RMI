import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Client
{
    public static void main( String[] args ) throws Exception
    {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println(" ---" + service.echo("Server responding correctly"));
        System.out.println(" ---" + service.print("filname", "printer"));
        System.out.println(" ---" + service.queue());
        System.out.println(" ---" + service.topQueue(2));
        System.out.println(" ---" + service.start());
        System.out.println(" ---" + service.stop());
        System.out.println(" ---" + service.restart());
        System.out.println(" ---" + service.status());
        System.out.println(" ---" + service.readConfig("param"));
        System.out.println(" ---" + service.setConfig("param", "Value"));


        PasswordStorage passManager = new PasswordStorage();
        String userName = "Mike";
        String password = "ThisTheFirstPassword";

        // passManager.signUp(userName, password);
        checkDatabase(userName,password);

    }

public static boolean checkDatabase(String user,String pwd)throws RemoteException, SQLException{

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


            // Checks if the given username and password is correct.
            // That means the user exists in the Database and gave the correct credentials.

            System.out.println("Successfuly connected to the dB");
            userAuthenticated = db.authenticateUser(user, pwd);

            if(userAuthenticated){
                System.out.println("Welcome " + user);
            }
            else{
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
