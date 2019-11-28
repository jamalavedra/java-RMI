import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoHelper {

    public String encrypt( String plaintext, SecretKeySpec key) throws Exception {
        return encrypt( generateIV(), plaintext, key);
    }

    public String encrypt( byte [] iv, String plaintext, SecretKeySpec key) throws Exception {

        byte [] decrypted = plaintext.getBytes();
        byte [] encrypted = encrypt( iv, decrypted, key);

        StringBuilder ciphertext = new StringBuilder();

        ciphertext.append( Base64.encodeBase64String( iv ) );
        ciphertext.append( ":" );
        ciphertext.append( Base64.encodeBase64String( encrypted ) );

        return ciphertext.toString();

    }

    public String decrypt( String ciphertext, SecretKeySpec key) throws Exception {
        String [] parts = ciphertext.split( ":" );
        byte [] iv = Base64.decodeBase64( parts[0] );
        byte [] encrypted = Base64.decodeBase64( parts[1] );
        // System.out.println(Arrays.toString(encrypted));
        byte [] decrypted = decrypt( iv, encrypted, key);
        return new String( decrypted );
    }

    public static byte [] generateIV() {
        SecureRandom random = new SecureRandom();
        byte [] iv = new byte [16];
        random.nextBytes( iv );
        return iv;
    }


    public byte [] encrypt( byte [] iv, byte [] plaintext, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init( Cipher.ENCRYPT_MODE, key, new IvParameterSpec( iv ) );
        return cipher.doFinal(plaintext);
    }

    public byte [] decrypt( byte [] iv, byte [] ciphertext , SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init( Cipher.DECRYPT_MODE, key, new IvParameterSpec( iv ) );
        return cipher.doFinal( ciphertext );
    }

}
