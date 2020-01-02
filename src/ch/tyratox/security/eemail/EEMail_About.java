package ch.tyratox.security.eemail;

import helper.ComponentMover;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.tyratox.security.eemail.design.EEMail_BtnsClose;
import ch.tyratox.security.eemail.design.EEMail_Frames;
import ch.tyratox.security.eemail.design.EEMail_Menu;
import ch.tyratox.security.eemail.design.EEMail_TextArea;

public class EEMail_About extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9088404584218522421L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public EEMail_About() {
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
				dispose();
			}
		});
		contentPane.add(close);
		
		
		
		EEMail_Menu mb = new EEMail_Menu();
		contentPane.add(mb);
		
		@SuppressWarnings("unused")
		ComponentMover cm = new ComponentMover(JFrame.class, mb);
		
		EEMail_Frames.setNiceFrameDesign(this);
		
		EEMail_TextArea txtrEemailIsA = new EEMail_TextArea();
		txtrEemailIsA.setBounds(6, 31, 438, 266);
		txtrEemailIsA.setBackground(new Color(0,0,0,0));
		txtrEemailIsA.setOpaque(false);
		txtrEemailIsA.setEditable(false);
		txtrEemailIsA.setLineWrap(true);
		
		txtrEemailIsA.setText("This is an old project, please do *NOT* use it. "
				+ "\n"
				+ "\n"
				+ "Thanks to Lionel Fend, Dario Breitenstein,"
				+ "\n"
				+ "David Schmid"
				+ "\n"
				+ "\n"
				+ "Librarys used:"
				+ "\n"
				+ "\n"
				+ "-Bouncycastle : http://www.bouncycastle.org"
				+ "\n"
				+ "-simplersalibrary : https://code.google.com/p/simplersalibrary/");
		contentPane.add(txtrEemailIsA);
	}
}
