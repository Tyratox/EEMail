package ch.tyratox.security.eemail.design;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class EEMail_BtnsClose extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6574535310201608029L;

	public EEMail_BtnsClose(String text){
		setText(text);
		designButton(this);
	}
	public EEMail_BtnsClose(){
		designButton(this);
	}
	
	private void designButton(final EEMail_BtnsClose btn){
		setBorder(null);
		setOpaque(true);
		setForeground(Colors.designForeground);
		setBackground(new Color(230, 80, 65));
		setFont(Fonts.lato(getClass()));
		
		btn.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(230, 80, 65));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				btn.setBackground(new Color(190,60,50));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btn.setBackground(new Color(190,60,50));
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(new Color(230, 80, 65));
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(new Color(190,60,50));
			}
		});
	}

}
