package ch.tyratox.security.eemail.design;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class EEMail_TextField extends JTextField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2951139662936567301L;

	public EEMail_TextField(String text){
		setText(text);
		designTextField(this);
	}
	public EEMail_TextField(){
		designTextField(this);
	}
	
	private void designTextField(final EEMail_TextField tfield){
		tfield.setOpaque(true);
		
		tfield.setBorder(Borders.niceBorder());
		
		tfield.setFont(Fonts.lato(getClass()));
		
		tfield.setBackground(Colors.desginBackground);
		tfield.setForeground(Colors.designForeground);
		
		tfield.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				tfield.setBorder(Borders.niceBorder());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				tfield.setBorder(Borders.niceBorder());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				tfield.setBorder(Borders.niceBorder());
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(tfield.hasFocus() != true){
					tfield.setBorder(Borders.hoverBorder());
				}
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				tfield.setBorder(Borders.niceBorder());
				
			}
		});
	}

}
