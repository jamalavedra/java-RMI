import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {

    Statement stm;
    ResultSet res;
    PasswordStorage manageEncription = new PasswordStorage();
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
                String saltDB = res.getString("Salt");

                System.out.println(usernameDB + " " + passwordDB + " " + saltDB);
            }

        }
        catch(Exception e){

        }


    }

    public boolean authenticateUser( String username, String password) throws SQLException{

        try{
            res = stm.executeQuery("SELECT * FROM data WHERE username = '" + username + "'");
            while(res.next()){
                String usernameDB = res.getString("Username");
                String passwordDB = res.getString("Password");
                String saltDB = res.getString("Salt");
                // System.out.println(usernameDB + " " + passwordDB + " " + saltDB);
                String calculatedHash = manageEncription.getEncryptedPassword(password, saltDB);
                if (calculatedHash.equals(passwordDB)) {
                	System.out.println("User authenticated");
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
