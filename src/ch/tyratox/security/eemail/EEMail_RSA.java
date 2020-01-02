package ch.tyratox.security.eemail;

import java.io.*;
import java.security.*;

import javax.crypto.*;

import com.estontorise.simplersa.RSAToolFactory;
import com.estontorise.simplersa.interfaces.RSAKey;
import com.estontorise.simplersa.interfaces.RSATool;

public class EEMail_RSA {
	
	public EEMail_RSA() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException{
		
	}
	public void generateKeyPair(String pathToPublicKey, String pathToPrivateKey) throws Exception{
		RSATool tool = RSAToolFactory.getRSATool();
		tool.generateKeyPair(new File(pathToPublicKey), new File(pathToPrivateKey));
	}
	public byte[] encrypt(byte[] data, String pathToPublicKey) throws Exception{
		RSATool tool = RSAToolFactory.getRSATool();
		
		RSAKey publicKey = tool.loadPublicKey(new File(pathToPublicKey));
		byte[] encoded = tool.encryptWithKey(data, publicKey);
		return encoded;
	}
	public byte[] encryptWithString(byte[] data, String publicKey_) throws Exception{
		RSATool tool = RSAToolFactory.getRSATool();
		
		PrintWriter out = new PrintWriter("public.pem");
		out.write(publicKey_);
		out.close();
		
		RSAKey publicKey = tool.loadPublicKey(new File("public.pem"));
		byte[] encoded = tool.encryptWithKey(data, publicKey);
		new File("public.pem").delete();
		return encoded;
	}
	public byte[] decrypt(byte[] cipherText, String pathToPrivateKey) throws Exception{
		RSATool tool = RSAToolFactory.getRSATool();
		
		RSAKey privateKey = tool.loadPrivateKey(new File(pathToPrivateKey));
		byte[] decoded = tool.decryptWithKey(cipherText, privateKey);
		return decoded;
	}
}
