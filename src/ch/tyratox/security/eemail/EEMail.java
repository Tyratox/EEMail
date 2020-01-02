package ch.tyratox.security.eemail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class EEMail {
	
	public EEMail_GUI gui;
	public EEMail_IMAP imap = new EEMail_IMAP();
	
	public static String ls = System.getProperty("line.separator");
	
	public String[][] messages;
	
	public String email;
	public String password;
	public String host_IMAP;
	public String host_SMTP;
	public String imapPort;
	
	public String appMainPath;
	public String appConfigPath;
	public String appKeyPath;
	public String appKeyOldPath;
	
	public EEMail master;
	
	public String uniqueID = "EEMAILENC";
	public String uniqueIDNotEnc = "EEMAILENC NOT ENC";
	
	public EEMail_MailForm form;
	
	public String split = "/s/";
	
	public int topSpacer = 25;

	public static void main(String[] args) {
		new EEMail();
	}
	public EEMail(){
		URL url;
		try {
			url = new URL("http://server.tyratox.ch/test.html");
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			String line = bf.readLine();
			if(line.equalsIgnoreCase("works")){
				bf.close();
			}else{
				bf.close();
				JOptionPane.showMessageDialog(null, "Your firewall blocks this software! We will quit!");
				System.exit(0);
			}
		} catch (Exception e) {
			try{
				url = new URL("http://google.ch");
				URLConnection con = url.openConnection();
				con.getInputStream();
			}catch(Exception lol){
				JOptionPane.showMessageDialog(null, "You aren't connected to the internet :(");
				System.exit(0);	
			}
			JOptionPane.showMessageDialog(null, "Our server is currently down, we well be back tomorrow morning :( , if you think this is an error contact me@tyratox.ch");
			System.exit(0);
		}
		getAppPath();
		master = this;
		getData();
		
	}
	public static String changeKey(String url_, String username, String newKeyLink, String pathToPrivateKey)throws Exception{
		EEMail_RSA rsa = new EEMail_RSA();
		 // open a connection to the site
	    URL url = new URL(url_);
	    URLConnection con = url.openConnection();
	    // activate the output
	    con.setDoOutput(true);
	    PrintStream ps = new PrintStream(con.getOutputStream());
	    // send your parameters to your site
	    ps.print("username=" + username);
	    ps.print("&rsa_key=" + newKeyLink);
	 
	    // we have to get the input stream in order to actually send the request
	    InputStream is = con.getInputStream();
	    BufferedReader bf = new BufferedReader(new InputStreamReader(is));
	    String code = "";
	    String line;
	    while((line = bf.readLine()) != null){
	    	code = code+line;
	    }
	    if(code.equalsIgnoreCase("Added user and key!")){
	    	return "added";
	    }else{
		    byte[] encoded = Base64.decode(code);
		    String decoded = new String(rsa.decrypt(encoded, pathToPrivateKey));
		 
		    // close the print stream
		    ps.close();
		    
		    url = new URL(url_);
		    con = url.openConnection();
		    con.setDoOutput(true);
		    ps = new PrintStream(con.getOutputStream());
		    // send your parameters to your site
		    ps.print("username="+username);
		    ps.print("&rsa_key="+newKeyLink);
		    ps.print("&pw=" + decoded);
		 
		    // we have to get the input stream in order to actually send the request
		    is = con.getInputStream();
		    is.close();
			ps.close();
			return decoded;
	    }
	}
	public static String getPublicKeyFromUser(String username, String url_) throws Exception{
		try{
			URL url = new URL(url_ + "?username="+username);
		    URLConnection con = url.openConnection();
		    // activate the output
		    con.setDoOutput(true);
		    
		    InputStream is = con.getInputStream();
		    BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		    String line = "";
		    String key = "";
		    while((line = bf.readLine())!=null){
		    	if(key == ""){
		    		key = line;
		    	}else{
		    		key = key+ls+line;
		    	}
		    }
		    if(key.contains("404")){
		    	return "404";
		    }else{
		    	return key;
		    }
		}catch(UnknownHostException es){
			JOptionPane.showMessageDialog(null, "You aren't connected to the internet!");
			return "nonet";
		}
	}
	
	private void getData(){
		new EEMail_getData(this);
	}
	
	public String refreshMails(){
		
		gui.list.clear();
		
		gui.loading.setText("Getting unread Mails from " + host_IMAP + " .....");
		gui.pb.setValue(10);
		messages = imap.getMailsIMAP(master, email, password, host_IMAP, imapPort);
		gui.pb.setValue(90);
		if(messages != null && messages.length != 0){
			if(messages[0][0] == "loginData"){
				return "loginData";
			}else if(messages[0][0] == "serverInfo"){
				return "serverInfo";
			}else if(messages[0][0] == "error"){
				return "error";
			}else{
				loadMails();
			}
		
		}else{
			loadMails();
		}
		return "good";
	}
	
	private void loadMails() {
		
		for(int i = 0;i<messages.length;i++){
			if(messages[i][0] != null){
			gui.list.addElement(messages[i][0] + " from: " + messages[i][1]);
			}
		}
		gui.pb.setValue(100);
		gui.loading.setText("Loaded all Mails :)");
		
	}
	@SuppressWarnings("static-access")
	public boolean sendMail(String to, String subject, String msg, String port){
		gui.pb.setValue(5);
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host_SMTP);
		properties.put("mail.smtp.starttls.enable","true");
		properties.setProperty("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		gui.pb.setValue(20);
		Session session = Session.getInstance(properties,
	            new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(email, password);
	                }
	            });
		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(email));
	         gui.pb.setValue(40);
	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(uniqueID + " " + subject);
	         gui.pb.setValue(70);
	         gui.loading.setText("Encrypting Mail.......");
	         EEMail_RSA rsa = new EEMail_RSA();
	         String s = EEMail.getPublicKeyFromUser(to, "http://server.tyratox.ch/api/");
	         String enc = "";
	         if(s.equalsIgnoreCase("404")){
	        	 if(JOptionPane.showConfirmDialog(form, "Not able to send Mail encrypted, the other user doesn't use this software! Do you want to send the mail unencrypted?") == JOptionPane.OK_OPTION){
	        		 enc = msg;
	        		 message.setSubject(uniqueIDNotEnc + " " + subject);
	        	 }else{
	        		 gui.pb.setValue(100);
	        		 return false;
	        	 }
	         }else{
	        	 
	        	 byte[] key = Crypter.generateRandomString();
	        	 byte[] encKey = rsa.encryptWithString(key, EEMail.getPublicKeyFromUser(to, "http://server.tyratox.ch/api/"));
	        	 String encKeyB64 = Base64.encode(encKey);
	        	 
	        	 String encData64 = Crypter.encrypt(msg, key);
	        	 
		         enc =  encKeyB64 + split + encData64;
	         }
	         gui.pb.setValue(90);
	         message.setText(enc);

	         // Send message
	         Transport transport = session.getTransport("smtps");
	         transport.send(message);
	         gui.pb.setValue(100);
	         gui.loading.setText("Sent Mail");
	      }catch (Exception es) {
	         if(es.getCause() instanceof SMTPAddressFailedException){
	        	 JOptionPane.showMessageDialog(form, es.getCause().toString());
	         }else{
	        	 es.printStackTrace();
	         }
	      }
		return true;
	}
	
	public void getAppPath(){
		String path = "";
		String user = System.getProperty("user.name");
		
		if(OSDetect.isMac()){
			path = "/Users/" + user +"/Library/Application Support/Eemail/";
		}else if(OSDetect.isUnix()){
			path = "/home/" + user +"/.Eemail/";
		}else if(OSDetect.isWindows()){
			path = "C://Users//"+user+"//AppData//Roaming//Eemail//";
		}else{
			System.exit(0);
		}
		
		if(new File(path).exists() && new File(path).isDirectory()){
			
		}else if(new File(path).exists() != true){
			new File(path).mkdirs();
			setupDirs(path);
		}else{
			new File(path).delete();
			new File(path).mkdirs();
			setupDirs(path);
		}
		this.appMainPath = path;
		this.appConfigPath = appMainPath + "config/";
		this.appKeyPath = appMainPath + "keys/";
		this.appKeyOldPath = appMainPath + "keys_old/";
	}
	public void setupDirs(String path) {
		new File(path + "config/").mkdirs();
		new File(path + "keys/").mkdirs();
		new File(path + "keys_old/").mkdirs();
		try {
			new File(path + "keys_old/DO_NOT_EDIT_ANYTHING_IN_HERE!").createNewFile();
			new File(path + "keys/DO_NOT_EDIT_ANYTHING_IN_HERE!").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateNewKeys() throws Exception{
		EEMail_RSA rsa = new EEMail_RSA();
		if(new File(appKeyPath + "private.pem").exists()){
			new File(appKeyPath + "private.pem").renameTo(new File(appKeyOldPath + "private.pem"));
		}
		
		rsa.generateKeyPair(appKeyPath + "mypublic.pem", appKeyPath + "private.pem");
		String code = changeKey("http://server.tyratox.ch/api/", email, "http://server.tyratox.ch/api/keys/users/" + email, appKeyOldPath + "private.pem");
		if(code.equalsIgnoreCase("added")){
			EEMail_FTP.uploadFileoverFTP("server.tyratox.ch", "eemail", "eemail", appKeyPath + "mypublic.pem", email, "/users/");
		}else{
			EEMail_FTP.uploadFileoverFTP("server.tyratox.ch", "eemail", "eemail", appKeyPath + "mypublic.pem", email+"_replace_"+code, "/users/");
		}
//		JOptionPane.showMessageDialog(gui, "Generated Keys!");
	}

}
