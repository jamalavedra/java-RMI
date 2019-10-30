import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class Client
{
    public static void main( String[] args ) throws Exception
    {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println(" ---" + service.echo("hey server"));
        System.out.println(" ---" + service.print("mandril", "inutil"));
        System.out.println(" ---" + service.queue());
        System.out.println(" ---" + service.topQueue(2));
        System.out.println(" ---" + service.start());
        System.out.println(" ---" + service.stop());
        System.out.println(" ---" + service.restart());
        System.out.println(" ---" + service.status());
        System.out.println(" ---" + service.readConfig("molt"));
        System.out.println(" ---" + service.setConfig("molt", "important"));

        
        PasswordStorage passManager = new PasswordStorage();
        String userName = "admin";
        String password = "password";
        checkDatabase(userName,password); 
        passManager.signUp(userName, password);
 

       /* boolean status = passManager.authenticateUser(userName, password);
        if (status) {
            System.out.println("Logged in!");
        } else {
            System.out.println("Sorry, wrong username/password");
        }*/
        
    }
    
public static boolean checkDatabase(String user,String pwd)throws RemoteException, SQLException{
        
    	final String userDB = "root";
    	final String passDB = "hello";
    	final String connection = "jdbc:mysql://192.168.99.100:3306/DB?useSSL=false";
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        
        Database db;
        boolean isValidUser = false;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(connection, userDB, passDB);
            s = c.createStatement();
            
            db = new Database(s, rs);
            

            // Checks if the given username and password is correct.
            // That means the user exists in the Database and gave the correct credentials.

            System.out.println("I am connected to the databse !!!");
            isValidUser = db.verifyUser(user, pwd);
            
            if(isValidUser){
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
        
       
     
     return isValidUser; 
 }
}