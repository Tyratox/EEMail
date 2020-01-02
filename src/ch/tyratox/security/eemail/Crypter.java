package ch.tyratox.security.eemail;

import java.security.*;
import java.util.Random;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypter {
	
	private static final String ALGO = "AES";
	
	public static String encrypt(String Data, byte[] pw) throws Exception {
        Key key = generateKey(pw);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decrypt(String encryptedData, byte[] pw) throws Exception {
        Key key = generateKey(pw);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private static Key generateKey(byte[] pw) throws Exception {
        Key key = new SecretKeySpec(pw , ALGO);
        return key;
    }
    
    public static byte[] generateRandomString() {
    	byte[] pw = new byte[16];
    	Random random = new Random();
    	random.nextBytes(pw);
    	return pw;
      }
}
