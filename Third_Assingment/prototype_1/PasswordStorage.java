import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.sql.PreparedStatement;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;



public class PasswordStorage {

	Database db;
	Statement stm;
    ResultSet res;

    private Connection connect() {
    	final String userDB = "dataUser";
    	final String passDB = "password";
    	final String conn = "jdbc:mysql://localhost:3306/data_security?useSSL=false";
        Connection c = null;
        try {
        	c = DriverManager.getConnection(conn, userDB, passDB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    // Get a encrypted password using PBKDF2 hash algorithm
    public String getSecuredPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000;
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }
}
