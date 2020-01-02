package ch.tyratox.security.eemail.design;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Borders {
	
	public static Border niceBorder(){
		Border border = BorderFactory.createLineBorder(Colors.designForeground);
		return border;
	}
	
	public static Border hoverBorder(){
		Border border = BorderFactory.createLineBorder(Colors.textField_hover);
		return border;
	}

}
