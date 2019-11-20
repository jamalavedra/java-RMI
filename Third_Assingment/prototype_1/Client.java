import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;



public class Client
{
    private static final  String keyString="1234567890123456";
    public static void main( String[] args ) throws Exception
    {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/hello");
        System.out.println(" ---" + service.echo("Server responding correctly"));

        Scanner scan = new Scanner(System.in);
        System.out.println(" Input your user name:");
        String userName = scan.next();
        System.out.println(" Input your password:");
        String password=scan.next();

        // PasswordStorage passManager = new PasswordStorage();
        // passManager.signUp(userName, password);
        boolean login = checkDatabase(userName, password);
        if(login){
            System.out.println("Welcome " + userName);
            String sessionToken = service.issueToken(userName, password);
            while(true) {
                System.out.println("Input what function you want to call:");
                String method = scan.next();
                switch (method){
                    case "print":
                        System.out.println("Input filename:");
                        String filename = scan.next();
                        System.out.println("Input printer:");
                        String printer_print = scan.next();
                        service.print(sessionToken, filename, printer_print);
                        System.out.println("--print: " + filename + " " + printer_print);
                        break;
                    case "queue":
                        System.out.println("Input a printer name:");
                        String printer_queue = scan.next();
                        service.queue(sessionToken, printer_queue);
                        System.out.println("--queue: ");
                        break;
                    case "topQueue":
                        System.out.println("Input the job number:");
                        int job = scan.nextInt();
                        service.topQueue(sessionToken, job);
                        System.out.println("--topQueue: " + job);
                        break;
                    case "start":
                        service.start(sessionToken);
                        break;
                    case "stop":
                        service.stop(sessionToken);
                        break;
                    case "restart":
                        service.restart(sessionToken);
                        break;
                    case "status":
                        System.out.println("Input printer name:");
                        String printer_status = scan.next();
                        service.status(sessionToken, printer_status);
                        System.out.println("--status: ");
                        break;
                    case "readConfig":
                        System.out.println("Input parameter:");
                        String parameter_readConfig = scan.next();
                        service.readConfig(sessionToken, parameter_readConfig);
                        System.out.println("--readConfig: " + parameter_readConfig);
                        break;
                    case "setConfig":
                        System.out.println("Input parameter:");
                        String parameter_setConfig = scan.next();
                        System.out.println("Input value:");
                        String value = scan.next();
                        service.setConfig(sessionToken, parameter_setConfig, value);
                        System.out.println("--setConfig: " + parameter_setConfig  + " " + value);
                        break;
                    default:
                        System.out.println("function name not recognized, please try again");
                }
            }
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
