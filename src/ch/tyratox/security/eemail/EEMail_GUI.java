package ch.tyratox.security.eemail;

import helper.ComponentMover;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.tyratox.security.eemail.design.EEMail_AboutLabel;
import ch.tyratox.security.eemail.design.EEMail_Btns;
import ch.tyratox.security.eemail.design.EEMail_BtnsClose;
import ch.tyratox.security.eemail.design.EEMail_Frames;
import ch.tyratox.security.eemail.design.EEMail_KeysLabel;
import ch.tyratox.security.eemail.design.EEMail_Label;
import ch.tyratox.security.eemail.design.EEMail_List;
import ch.tyratox.security.eemail.design.EEMail_Menu;
import ch.tyratox.security.eemail.design.EEMail_ProgressBar;
import ch.tyratox.security.eemail.design.EEMail_TextArea;

public class EEMail_GUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3238395860560742226L;
	private JPanel contentPane;
	
	public DefaultListModel<String> list = new DefaultListModel<String>();
	public JList<String> jlist = new JList<String>(list);
	
	public EEMail_Label loading;
	public EEMail_TextArea message_body;
	public EEMail_ProgressBar pb;
	
	private EEMail main;
	private final EEMail main_;
	private EEMail_Btns refresh;

	/**
	 * Create the frame.
	 */
	public EEMail_GUI(EEMail main, String host_IMAP) {
		EEMail_List.designJList(jlist, getClass());
		
		this.main = main;
		this.main_ = main;
		
		saveInfosToConfig();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		if(OSDetect.isWindows()){
			setBounds(100, 100, 900, 725);
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
		
		EEMail_KeysLabel keys = new EEMail_KeysLabel(main);
		keys.setBounds(200, 0, 100, 25);
		contentPane.add(keys);
		
		EEMail_Menu mb = new EEMail_Menu();
		contentPane.add(mb);
		
		@SuppressWarnings("unused")
		ComponentMover cm = new ComponentMover(JFrame.class, mb);
		
		EEMail_Frames.setNiceFrameDesign(this);
		
		jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist.setBounds(6, 48+main.topSpacer, 888, 224);
		
		JScrollPane jscrollpane = new JScrollPane(jlist);
		jscrollpane.setBounds(6, 48+main.topSpacer, 888, 224);
		jscrollpane.setVisible(true);
		contentPane.add(jscrollpane);
		
		jlist.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = jlist.getSelectedIndex();
				message_body.setText(main_.messages[index][3]);
				
			}
		});
		
		loading = new EEMail_Label("Getting unread Mails from " + host_IMAP + " .....");
		loading.setBounds(6, 6+main.topSpacer, 651, 16);
		contentPane.add(loading);
		
		message_body = new EEMail_TextArea();
		message_body.setBounds(6, 284+main.topSpacer, 888, 388);
		message_body.setEditable(false);
		message_body.setLineWrap(true);
		
		JScrollPane jscrollpane2 = new JScrollPane(message_body);
		jscrollpane2.setBounds(6, 284+main.topSpacer, 888, 388);
		jscrollpane2.setVisible(true);
		
		contentPane.add(jscrollpane2);
		
		EEMail_Btns btnSendMail = new EEMail_Btns("Send Mail");
		btnSendMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Send a mail
				EEMail_MailForm form = new EEMail_MailForm(main_);
				main_.form = form;
				
			}
		});
		btnSendMail.setBounds(785, 18+main.topSpacer, 109, 29);
		contentPane.add(btnSendMail);
		
		refresh = new EEMail_Btns("Refresh");
		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main_.refreshMails();
			}
		});
		refresh.setBounds(665, 18+main.topSpacer, 109, 29);
		contentPane.add(refresh);
		
		pb = new EEMail_ProgressBar(contentPane, 6, 27+main.topSpacer, 651, 20);
		
//		contentPane.add(jlist);
	}

	private void saveInfosToConfig() {
		String content = "mail:" + main.email + EEMail.ls + "imap:" + main.host_IMAP + EEMail.ls + "smtp:" + main.host_SMTP;
		try {
			FileOutputStream fos = new FileOutputStream(new File(main.appConfigPath + "data.save"));
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
