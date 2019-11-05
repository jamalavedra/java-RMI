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
    	final String userDB = "root";
    	final String passDB = "hello";
    	final String conn = "jdbc:mysql://192.168.99.100:3306/DB?useSSL=false";
        Connection c = null;
        try {
        	c = DriverManager.getConnection(conn, userDB, passDB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
 
    //Method used for Login the user
    public void signUp(String userName, String password) throws Exception {
    	try {
    	CryptoHelper crypto = new CryptoHelper();
        String decryptedUser = crypto.decrypt(userName);
        System.out.println("mandril");
        String decryptedPass = crypto.decrypt(password);
        
        String salt = getNewSalt();
        String encryptedPassword = getEncryptedPassword(decryptedPass, salt);
        UserInfo user = new UserInfo();
        
        user.userEncryptedPassword = encryptedPassword;
        user.userName = decryptedUser;
        user.userSalt = salt;

        saveUser(user);
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
        
    }
 
    // Get a encrypted password using PBKDF2 hash al`gorithm
    public String getEncryptedPassword(String password, String salt) throws Exception {

    	String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000;
 
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
 
        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }
 
    // Returns base64 encoded salt
    public String getNewSalt() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    //save user in Database
    private void saveUser(UserInfo user) throws SQLException {
    	String sql = "INSERT INTO Users(Username, Password,Salt) VALUES(?,?,?)";
    	
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.userName);
            pstmt.setString(2, user.userEncryptedPassword);
            pstmt.setString(3, user.userSalt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
