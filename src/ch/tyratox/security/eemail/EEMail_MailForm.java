package ch.tyratox.security.eemail;

import helper.ComponentMover;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ch.tyratox.security.eemail.design.EEMail_Btns;
import ch.tyratox.security.eemail.design.EEMail_BtnsClose;
import ch.tyratox.security.eemail.design.EEMail_Frames;
import ch.tyratox.security.eemail.design.EEMail_Label;
import ch.tyratox.security.eemail.design.EEMail_Menu;
import ch.tyratox.security.eemail.design.EEMail_TextArea;
import ch.tyratox.security.eemail.design.EEMail_TextField;

public class EEMail_MailForm extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8064377326007271549L;
	private JPanel contentPane;
	
	private EEMail main;
	private EEMail_TextField to;
	private EEMail_Btns btnNewButton;
	private EEMail_TextField subject;
	private EEMail_Label lblSubject;
	private EEMail_TextArea body;
	private EEMail_TextField port;
	private EEMail_Label label;
	
	private JFrame me = this;

	/**
	 * Create the frame.
	 */
	public EEMail_MailForm(EEMail eemail) {
		main = eemail;
		setBounds(100, 100, 700, 510);
		if(OSDetect.isWindows()){
			setBounds(100, 100, 700, 525);
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
				dispose();
			}
		});
		contentPane.add(close);
		
		EEMail_Menu mb = new EEMail_Menu();
		contentPane.add(mb);
		
		
		
		@SuppressWarnings("unused")
		ComponentMover cm = new ComponentMover(JFrame.class, mb);
		
		EEMail_Frames.setNiceFrameDesign(this);
		
		EEMail_Label lblTo = new EEMail_Label("To:");
		lblTo.setBounds(6, 11+main.topSpacer, 61, 16);
		contentPane.add(lblTo);
		
		to = new EEMail_TextField();
		to.setBounds(40, 5+main.topSpacer, 310, 28);
		contentPane.add(to);
		
		subject = new EEMail_TextField();
		subject.setBounds(428, 5+main.topSpacer, 266, 28);
		contentPane.add(subject);
		
		lblSubject = new EEMail_Label("Subject:");
		lblSubject.setBounds(362, 11+main.topSpacer, 332, 16);
		contentPane.add(lblSubject);
		
		body = new EEMail_TextArea();
		body.setBounds(6, 40+main.topSpacer, 688, 396);
		contentPane.add(body);
		body.setLineWrap(true);
		
		btnNewButton = new EEMail_Btns("Send Mail");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(body.getText().getBytes().length > 245){
					JOptionPane.showMessageDialog(me, "The message has to be smaller than 245 bytes!", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(main.sendMail(to.getText(), subject.getText(), body.getText(), port.getText())){
						dispose();	
					}
				}
			}
		});
		btnNewButton.setBounds(577, 448+main.topSpacer, 117, 29);
		contentPane.add(btnNewButton);
		
		port = new EEMail_TextField();
		port.setText("587");
		port.setColumns(4);
		port.setBounds(104, 448+main.topSpacer, 67, 28);
		contentPane.add(port);
		
		label = new EEMail_Label("SMTP Port:");
		label.setBounds(6, 455+main.topSpacer, 128, 16);
		contentPane.add(label);
		
		
		setVisible(true);
		
	}
}
