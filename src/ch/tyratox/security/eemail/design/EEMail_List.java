package ch.tyratox.security.eemail.design;

import javax.swing.JList;

public class EEMail_List{

	
	public static void designJList(JList<String> list, @SuppressWarnings("rawtypes")Class c){
		list.setOpaque(true);
		
		list.setFont(Fonts.lato(c));
		
		list.setBorder(Borders.niceBorder());
		list.setBackground(Colors.desginBackground);
		list.setForeground(Colors.designForeground);
	}

}
