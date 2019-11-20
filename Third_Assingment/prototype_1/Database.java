import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.spec.SecretKeySpec;


public class Database {

    Statement stm;
    ResultSet res;
    PasswordStorage manageEncription = new PasswordStorage();
    // Constructor
    private static final  String keyString="1234567890123456";
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

    public boolean authenticateUser( String encriptedUser, String encriptedPass) throws SQLException, Exception{
        SecretKeySpec key= new SecretKeySpec(keyString.getBytes("UTF-8"),"AES");
        CryptoHelper crypto = new CryptoHelper();
        String username = crypto.decrypt(encriptedUser, key);
        String password = crypto.decrypt(encriptedPass, key);
        try{
            res = stm.executeQuery("SELECT * FROM data WHERE username = '" + username + "'");
            while(res.next()){
                String usernameDB = res.getString("Username");
                String passwordDB = res.getString("Password");
                String saltDB = res.getString("Salt");
                String calculatedHash = manageEncription.getSecuredPassword(password, saltDB);
                if (calculatedHash.equals(passwordDB)) {
                	return true;
                }
            }

        }
        catch(Exception e){
        	System.out.println(e);

        }


        return false;

    }
    public boolean authoriseUser( String user, String method) throws SQLException, Exception{
        try{
            res = stm.executeQuery("SELECT * FROM users WHERE username = '" + user + "'");
            while(res.next()){
                Boolean authorization = res.getBoolean(method);
                if (authorization) {
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
