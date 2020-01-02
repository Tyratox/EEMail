package ch.tyratox.security.eemail.design;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class EEMail_Btns extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6574535310201608029L;

	public EEMail_Btns(String text){
		setText(text);
		designButton(this);
	}
	public EEMail_Btns(){
		designButton(this);
	}
	
	private void designButton(final EEMail_Btns btn){
		btn.setBorder(Borders.niceBorder());
		
		btn.setFont(Fonts.lato(getClass()));
		
		btn.setOpaque(true);
		btn.setForeground(Colors.designForeground);
		btn.setBackground(Colors.desginBackground);
		
		btn.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(Colors.desginBackground);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				btn.setBackground(Colors.button_hover);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btn.setBackground(Colors.button_click);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				btn.setBackground(Colors.desginBackground);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(Colors.button_hover);
			}
		});
	}

}
