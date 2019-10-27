import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
	private boolean connectDb(String usr,String passw) {
	    final String user = "root";
	    final String pass = "Password123!";
	    final String connection = "jdbc:mysql://localhost:3306/DB";
	    System.out.println(user);
	    System.out.println(pass);

	    try
	    {
	      Class.forName("com.mysql.jdbc.Driver");
	      Connection con = DriverManager.getConnection(connection,user,pass);
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		return true;
	}

}
