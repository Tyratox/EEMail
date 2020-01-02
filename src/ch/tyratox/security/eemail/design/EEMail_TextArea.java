package ch.tyratox.security.eemail.design;

import javax.swing.JTextArea;

public class EEMail_TextArea extends JTextArea{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7750584782175813609L;

	public EEMail_TextArea(){
		designTextArea(this);
	}
	public EEMail_TextArea(String text){
		setText(text);
		designTextArea(this);
	}
	
	private void designTextArea(final EEMail_TextArea textArea){
		textArea.setOpaque(true);
		
		textArea.setFont(Fonts.lato(getClass()));
		
		textArea.setBackground(Colors.desginBackground);
		textArea.setForeground(Colors.designForeground);
		textArea.setBorder(Borders.niceBorder());
	}

}
