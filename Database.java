import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {

    Statement stm;
    ResultSet res;
    
    // Constructor
    public Database(Statement stm, ResultSet res){
        this.stm = stm;
        this.res = res;
    }
    public void printDatabase() throws SQLException{
        
        
        try{
        	res = stm.executeQuery("SELECT * FROM data");
            
            while(res.next()){
               
                String usernameDB = res.getString("Username");
                String passwordDB = res.getString("Password");
                
                System.out.println(usernameDB + " " + passwordDB + " ");
            }
            
        }
        catch(Exception e){
            
        }
            
        
    }
       
    public boolean verifyUser( String username, String password) throws SQLException{
        
        try{
            res = stm.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password ='" + password + "'");
            
            while(res.next()){
               
                String usernameDB = res.getString("Username");
                String passwordDB = res.getString("Password");

                
                if(passwordDB.equals(password)){
                    
                    return true;
                }
                
            }  
        }
        catch(Exception e){
        	System.out.println(e);
            
        }
        
        
        return false;
        
    }

}
