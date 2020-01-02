package ch.tyratox.security.eemail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SubjectTerm;

import com.sun.mail.imap.IMAPFolder;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class EEMail_IMAP {
	
	private String ls = System.getProperty("line.separator");
	
	private EEMail main;
	
	public EEMail_IMAP(){
		
	}
	
	public String[][] getMailsIMAP(EEMail main_, String username, String password, String imapHost, String imapPort){
		this.main = main_;
		String[][] msgs = null;
		Properties props = System.getProperties();
		props.setProperty("mail.imap.port", imapPort);
		props.setProperty("mail.imap.socketFactory.port", imapPort);
		
		try {
			  Session session = Session.getDefaultInstance(props, null);
			  Store store = session.getStore("imaps");
			  try{
				  store.connect(imapHost, username, password);
			  }catch(Exception e){
				  String[][] s = new String[1][1];
				  if(e.getCause() instanceof UnknownHostException || e instanceof UnknownHostException){
					  s[0][0] = "serverInfo";
				  }else if(e.getCause() instanceof AuthenticationFailedException || e instanceof AuthenticationFailedException){
					  s[0][0] = "loginData";
				  }else{
					  s[0][0] = "error";
					  e.printStackTrace();
				  }
				  return s;
			  }
			  
			  IMAPFolder folder = (IMAPFolder) store.getFolder("Inbox");
			  if(!folder.isOpen()){
		          folder.open(Folder.READ_WRITE);
		          Message messages[] = folder.search(new SubjectTerm(main.uniqueID));
		          
		          msgs = new String[messages.length][4];
		          
		          for (int i=0; i < messages.length;i++) {
		        	  
		        	  Message msg =  messages[i];
			          if(msg != null && msg.getSubject() != null && msg.getFrom()[0].toString() != null){
			            msgs[i][0] = msg.getSubject().replaceAll(main.uniqueID+" ", "").replaceAll(main.uniqueID, "");
			            msgs[i][1] = msg.getFrom()[0].toString();
			            msgs[i][2] = msg.getReceivedDate().toString();
			            if(msg.getSubject().contains(main.uniqueID) && msg.isMimeType("text/plain")){
			            	if(msg.getSubject().contains(main.uniqueIDNotEnc)){
			            		msgs[i][3] = (String) msg.getContent();
			            	}else{
			            		
			            		String encodedC = (String) msg.getContent();
			            		String[] c = encodedC.split(main.split);
			            		
			            		byte[] key = Base64.decode(c[0]);
			            		String encData = c[1];
			            		
				            	EEMail_RSA rsa = new EEMail_RSA();
				            	
				            	try{
				            		byte[] aesKey = rsa.decrypt(key, main.appKeyPath + "private.pem");
				            		
				            		String decoded = Crypter.decrypt(encData, aesKey);
				            		
				            		msgs[i][3] = decoded;
				            	}catch(BadPaddingException e){
				            		e.printStackTrace();
				            		for(int j = 0;j<4;j++){
				            			msgs[i][j] = null;
				            		}
				            	}
			            	}
			            }else{
			            	msgs[i][3] = getContentinText(msg);
			            }
			            
			            
			          }else{
			        	  msgs[i][0] = null;
			          }
//			          msg.setFlag(Flags.Flag.SEEN, false);

		          }
		          
		          if (folder != null && folder.isOpen()) { folder.close(true); }
		          if (store != null) { store.close(); }
			  }
			  
			} catch (Exception e) {
			  e.printStackTrace();
			}
		return msgs;
    }
	
	public String getContentinText(Message m) throws Exception{
		String body = "We are very sorry, but this Mail Type isn't compatible with this software :( We are working on that :)" + ls + "For mor informations please contact me: me@tyratox.ch";
		String b = body;
		if( m.isMimeType("multipart/*") ){
            Multipart mp = (Multipart) m.getContent();
            
            for( int j=0;j<mp.getCount();j++ ){
               Part part = mp.getBodyPart(j);
               String disposition = part.getDisposition();
               
               if( disposition == null ){
                  MimeBodyPart mimePart = (MimeBodyPart) part;
                  
                  if( mimePart.isMimeType("text/plain") ){
                     BufferedReader in = new BufferedReader( new InputStreamReader(mimePart.getInputStream()) );
                     
                     for( String line; (line=in.readLine()) != null; ){
                        if(body == b){
                        	body = line;
                        }else{
                        	body = body + ls + line;
                        }
                     }
                  }
               }
            }
            return body;
         }else if(m.isMimeType("text/plain")){
        	 return (String) m.getContent();
         }
		return body;
	}

}
