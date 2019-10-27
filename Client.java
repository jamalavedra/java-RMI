import java.rmi.Naming;
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
        Database.connectDb();
        passManager.signUp(userName, password);
 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter username:");
        String inputUser = scanner.nextLine();
 
        System.out.println("Please enter password:");
        String inputPass = scanner.nextLine();
 
        boolean status = passManager.authenticateUser(inputUser, inputPass);
        if (status) {
            System.out.println("Logged in!");
        } else {
            System.out.println("Sorry, wrong username/password");
        }
        scanner.close();
    }
}