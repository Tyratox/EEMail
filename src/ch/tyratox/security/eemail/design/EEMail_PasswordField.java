package ch.tyratox.security.eemail.design;

import javax.swing.JPasswordField;

public class EEMail_PasswordField extends JPasswordField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189716319965853961L;

	public EEMail_PasswordField(){
		setOpaque(true);		
		setBorder(Borders.niceBorder());
		setBackground(Colors.desginBackground);
		setForeground(Colors.designForeground);
	}

}
