package ch.tyratox.security.eemail.design;

import javax.swing.JLabel;

public class EEMail_Label extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 96829610934689911L;
	
	public EEMail_Label(){
		designLabel(this);
	}
	public EEMail_Label(String text){
		setText(text);
		designLabel(this);
	}
	
	private void designLabel(EEMail_Label label){
		label.setOpaque(true);
		
		label.setFont(Fonts.lato(getClass()));
		
		label.setBorder(null);
		label.setBackground(Colors.desginBackground);
		label.setForeground(Colors.designForeground);
	}

}
