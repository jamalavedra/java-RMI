import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;



public class Client
{
    public static void main( String[] args ) throws NotBoundException, MalformedURLException, RemoteException
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

        String sessionToken = service.Login("testUser", "pass");
        
        
    }
}
