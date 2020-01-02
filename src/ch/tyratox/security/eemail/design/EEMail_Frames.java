package ch.tyratox.security.eemail.design;

import javax.swing.JFrame;

public class EEMail_Frames {
	
	public static void setNiceFrameDesign(JFrame frame){
		
		frame.setBackground(Colors.desginBackground);
		frame.setForeground(Colors.designForeground);
		frame.getContentPane().setBackground(Colors.desginBackground);
		frame.getContentPane().setForeground(Colors.designForeground);
		
		frame.setUndecorated(true);
		
		
	}

}
