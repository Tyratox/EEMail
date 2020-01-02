package ch.tyratox.security.eemail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class EEMail_FTP {
	
	static FTPClient ftp = null;
	
	public static void uploadFileoverFTP(String host, String user, String pwd, String localFileFullName, String fileName, String hostDir) throws Exception{
		ftp = new FTPClient();
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.deleteFile(hostDir + fileName);
        InputStream inputStream = ftp.retrieveFileStream(hostDir + fileName);
        int returnCode = ftp.getReplyCode();
        if (inputStream == null || returnCode == 550) {
        	if(new File(localFileFullName).exists()){
	        	try(InputStream input = new FileInputStream(new File(localFileFullName))){
	                ftp.storeFile(hostDir + fileName, input);
	                ftp.sendSiteCommand("CHMOD 777 " + hostDir + fileName);
	            }
        	}
        }else{
        	
        	try(InputStream input = new FileInputStream(new File(localFileFullName))){
                ftp.storeFile(hostDir + fileName, input);
            }
        }
        
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }    
	}
	public static void deleteFTPFile(String host, String user, String pwd, String localFileFullName, String fileName, String hostDir) throws Exception{
		ftp = new FTPClient();
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        
        InputStream inputStream = ftp.retrieveFileStream(hostDir + fileName);
        int returnCode = ftp.getReplyCode();
        if (inputStream == null || returnCode == 550) {
        	//File does not exist
        }else{
        	//File does exist
        	ftp.deleteFile(hostDir + fileName);
        }
        
        
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
	}
	public static boolean downloadFileoverFTP(String host, String user, String pwd, String localFileFullName, String fileName, String hostDir) throws Exception{
		boolean r = false;
		ftp = new FTPClient();
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        
        InputStream inputStream = ftp.retrieveFileStream(hostDir + fileName);
        int returnCode = ftp.getReplyCode();
        if (inputStream == null || returnCode == 550) {
        	//File does not exist
        	r = false;
        }else{
        	//File does exist
        	r = true;
        	OutputStream os = new FileOutputStream(localFileFullName);
        	byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while((bytesRead = inputStream.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
        }
        
        
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
        return r;
	}

}
