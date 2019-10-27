import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
	public static void connectDb() {
		final String user = "root";
	    final String pass = "Password123!";
	    final String connection = "jdbc:mysql://localhost:3306/DB";

	    try
	    {
	      Class.forName("com.mysql.jdbc.Driver");
	      DriverManager.getConnection(connection,user,pass); 
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }	
	}
}
