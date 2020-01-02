package ch.tyratox.security.eemail;

import helper.ComponentMover;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.tyratox.security.eemail.design.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EEMail_getData extends JFrame implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -438030538401251145L;
	private JPanel contentPane;
	private EEMail_TextField mail;
	private EEMail_PasswordField pw;
	private EEMail_TextField imap;
	private EEMail_TextField smtp;
	private EEMail_TextField imapPort;
	
	public EEMail_Label pw_info;
	public EEMail_Label smtp_info;
	public EEMail_Label imap_info;
	
	private EEMail main;
	private EEMail_Label mail_info;

	/**
	 * Create the frame.
	 * @param main 
	 */
	public EEMail_getData(EEMail eemail) {
		main = eemail;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		if(OSDetect.isWindows()){
			setBounds(100, 100, 450, 325);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		EEMail_BtnsClose close = new EEMail_BtnsClose("Close");
		close.setBounds(0, 0, 100, 25);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(close);
		
		EEMail_AboutLabel l = new EEMail_AboutLabel();
		l.setBounds(100, 0, 100, 25);
		contentPane.add(l);
		
//		EEMail_MenuComponents.addMenuComponents(contentPane);
		
		EEMail_Menu mb = new EEMail_Menu();
		contentPane.add(mb);
		
		@SuppressWarnings("unused")
		ComponentMover cm = new ComponentMover(JFrame.class, mb);
		
		EEMail_Frames.setNiceFrameDesign(this);
		
		
		EEMail_Label lblEmailAdress = new EEMail_Label("E-Mail Address:");
		lblEmailAdress.setBounds(6, 12+main.topSpacer, 136, 16);
		contentPane.add(lblEmailAdress);
		
		mail = new EEMail_TextField();
		mail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				showInfo();
			}
		});
		mail.setBounds(154, 5+main.topSpacer, 290, 28);
		contentPane.add(mail);
		
		EEMail_Label lblPassword = new EEMail_Label("Password:");
		lblPassword.setBounds(6, 65+main.topSpacer, 136, 16);
		contentPane.add(lblPassword);
		
		pw = new EEMail_PasswordField();
		pw.setBounds(154, 65+main.topSpacer, 290, 28);
		contentPane.add(pw);
		
		pw_info = new EEMail_Label("");
		pw_info.setBounds(154, 95+main.topSpacer, 290, 28);
		contentPane.add(pw_info);
		
		EEMail_Label lblImapServer = new EEMail_Label("IMAP Server:");
		lblImapServer.setBounds(6, 125+main.topSpacer, 136, 16);
		contentPane.add(lblImapServer);
		
		imap = new EEMail_TextField();
		imap.setBounds(154, 125+main.topSpacer, 290, 28);
		contentPane.add(imap);
		
		imap_info = new EEMail_Label("");
		imap_info.setBounds(154, 155+main.topSpacer, 290, 28);
		contentPane.add(imap_info);
		
		EEMail_Label lblSmtpServer = new EEMail_Label("SMTP Server:");
		lblSmtpServer.setBounds(6, 185+main.topSpacer, 136, 16);
		contentPane.add(lblSmtpServer);
		
		smtp = new EEMail_TextField();
		smtp.setBounds(154, 185+main.topSpacer, 290, 28);
		contentPane.add(smtp);
		
		smtp_info = new EEMail_Label("");
		smtp_info.setBounds(154, 215+main.topSpacer, 290, 28);
		contentPane.add(smtp_info);
		
		addKeyListener(this);
		mail.addKeyListener(this);
		pw.addKeyListener(this);
		imap.addKeyListener(this);
		smtp.addKeyListener(this);
		
		if(main.email != null && main.email != ""){
			mail.setText(main.email);
		}
		if(main.password != null && main.password != ""){
			pw.setText(main.password);
		}
		if(main.host_IMAP != null && main.host_IMAP != ""){
			imap.setText(main.host_IMAP);
		}
		if(main.host_SMTP != null && main.host_SMTP != ""){
			smtp.setText(main.host_SMTP);
		}
		
		EEMail_Btns btnDone = new EEMail_Btns("Done");
		btnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				done();
			}
		});
		btnDone.setBounds(327, 243+main.topSpacer, 117, 29);
		contentPane.add(btnDone);
		
		mail_info = new EEMail_Label("");
		mail_info.setBounds(154, 35+main.topSpacer, 290, 28);
		contentPane.add(mail_info);
		
		EEMail_Label lblImapPort = new EEMail_Label("IMAP Port:");
		lblImapPort.setBounds(6, 243+main.topSpacer, 117, 16);
		contentPane.add(lblImapPort);
		
		imapPort = new EEMail_TextField("993");
		imapPort.setBounds(154, 243+main.topSpacer, 117, 28);
		contentPane.add(imapPort);
		
		if(new File(main.appConfigPath + "data.save").exists()){
			try {
				InputStream is = new FileInputStream(new File(main.appConfigPath + "data.save"));
				BufferedReader bf = new BufferedReader(new InputStreamReader(is));
				String line;
				while((line = bf.readLine())!=null){
					if(line.split(":")[0].equalsIgnoreCase("mail")){
						mail.setText(line.split(":")[1]);
					}else if(line.split(":")[0].equalsIgnoreCase("imap")){
						imap.setText(line.split(":")[1]);
					}else if(line.split(":")[0].equalsIgnoreCase("smtp")){
						smtp.setText(line.split(":")[1]);
					}
				}
				bf.close();
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(mail.getText().length() != 0){
			pw.requestFocus();
		}
		setVisible(true);
		if(mail.getText().length() != 0){
			pw.requestFocus();
			showInfo();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == 10){
			done();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	
	public void done(){
		if(mail.getText()!="" && pw.getPassword().length != 0 && imap.getText() != "" && smtp.getText() != ""){
			
			//Save Data
			
			main.email = mail.getText();
			main.password = new String(pw.getPassword());
			main.host_IMAP = imap.getText();
			main.host_SMTP = smtp.getText();
			main.imapPort = imapPort.getText();
			
			//Init GUI
			if(main.gui == null){
				main.gui = new EEMail_GUI(main, imap.getText());
			}
			
			String dataOK = main.refreshMails();
			
			if(dataOK.equalsIgnoreCase("loginData")){
				pw_info.setForeground(Colors.wrong);
				pw_info.setText("Your login details aren't correct!");
				
				System.err.println("Your login details aren't correct!");
			}else if(dataOK.equalsIgnoreCase("serverInfo")){
				imap_info.setForeground(Colors.wrong);
				imap_info.setText("The server doesn't exist or is down!");
				smtp_info.setForeground(Colors.wrong);
				smtp_info.setText("The server doesn't exist or is down!");
				
				System.err.println("The server doesn't exist or is down!");
			}else if(dataOK.equalsIgnoreCase("error")){
				pw_info.setForeground(Colors.wrong);
				pw_info.setText("Your login details aren't correct!");
				
				System.err.println("Error while login in in Mail Server!");
			}else{
				if(!main.hasKeys()){
					try {
						main.generateNewKeys();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		
				dispose();
				main.gui.setVisible(true);
			}
		}else{
			mail_info.setForeground(Colors.wrong);
			mail_info.setText("Please fill in all fields!");
		}
	}
	
	public void showInfo(){
		boolean error = false;
		try{
			if(mail.getText().contains("@gmail.com") || mail.getText().contains("@googlemail.com")){
				imap.setText("imap.googlemail.com");
				imap_info.setForeground(Colors.right);
				imap_info.setText("This should be correct :D");
				smtp.setText("smtp.googlemail.com");
				smtp_info.setForeground(Colors.right);
				smtp_info.setText("This should be correct :D");
			}else if(mail.getText().contains("@yahoo.com") || mail.getText().contains("@ymail.com")){
				imap.setText("imap.mail.yahoo.com");
				smtp.setText("smtp.mail.yahoo.com");
				imap_info.setText("This should be correct :D");
				smtp.setText("smtp.googlemail.com");
				smtp_info.setForeground(Colors.right);
				smtp_info.setText("This should be correct :D");
			}else{
				String domain = mail.getText().split("@")[1];
				
				if(new File(main.appConfigPath + "data.save").exists()){
					BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(main.appConfigPath + "data.save"))));
					bf.readLine();
					String i = bf.readLine();
					String s = bf.readLine();
					bf.close();
					if(imap.getText().equalsIgnoreCase(i.split(":")[1])){
						imap_info.setForeground(Colors.right);
						imap_info.setText("Valid Host :D");
					}else{
						imap.setText("imap."+domain);
						imap_info.setForeground(Colors.info);
						imap_info.setText("Please correct this if not correct!");
					}
					if(smtp.getText().equalsIgnoreCase(s.split(":")[1])){
						smtp_info.setForeground(Colors.right);
						smtp_info.setText("Valid Host :D");
					}else{
						smtp.setText("smtp."+domain);
						smtp_info.setForeground(Colors.info);
						smtp_info.setText("Please correct this if not correct!");
					}
				}else{
					imap.setText("imap."+domain);
					imap_info.setForeground(Colors.info);
					imap_info.setText("Please correct this if not correct!");

					smtp.setText("smtp."+domain);
					smtp_info.setForeground(Colors.info);
					smtp_info.setText("Please correct this if not correct!");
				}
			}
		}catch(ArrayIndexOutOfBoundsException es){
			invalid();
			error = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(error != true){
			mail_info.setForeground(Colors.right);
			mail_info.setText("Looks like a valid E-Mail Address");
		}
	}
	public void invalid(){
		clearInfo();
		mail_info.setForeground(Colors.wrong);
		mail_info.setText("Invalid E-Mail Address");
	}
	public void clearInfo(){
		pw_info.setText("");
		smtp.setText("");
		smtp_info.setText("");
		imap.setText("");
		imap_info.setText("");
	}
}