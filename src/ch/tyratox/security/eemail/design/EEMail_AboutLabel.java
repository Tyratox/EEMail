package ch.tyratox.security.eemail.design;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ch.tyratox.security.eemail.EEMail_About;

public class EEMail_AboutLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7499150070631081033L;
	
	public EEMail_AboutLabel(){
		setOpaque(true);
		setBackground(Colors.menuBarAboutBackgroundColor);
		setBorder(null);
		setForeground(Colors.designForeground);
		
		setFont(Fonts.lato(getClass()));
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setText("About");
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Colors.menuBarAboutBackgroundColor);
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Colors.menuBarAboutBackgroundColor_hover);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Colors.menuBarAboutBackgroundColor);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Colors.menuBarAboutBackgroundColor_hover);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setBackground(Colors.menuBarAboutBackgroundColor_hover);
				EEMail_About es = new EEMail_About();
				es.setVisible(true);
			}
		});
	}

}
