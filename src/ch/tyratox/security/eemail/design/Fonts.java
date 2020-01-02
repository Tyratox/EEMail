package ch.tyratox.security.eemail.design;

import java.awt.Font;
import java.io.InputStream;

public class Fonts {
	
	public static Font lato(@SuppressWarnings("rawtypes") Class c){
		try{
			InputStream is = c.getResourceAsStream("/res/fonts/Lato.ttf");
			Font lato = Font.createFont(Font.TRUETYPE_FONT,is);
			Font f = lato.deriveFont(18f);
			return f;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
