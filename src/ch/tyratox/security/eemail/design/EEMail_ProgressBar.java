package ch.tyratox.security.eemail.design;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EEMail_ProgressBar extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5245823529006599717L;
	
	double width;
	JLabel cover;

	public EEMail_ProgressBar(JPanel contentPane, int x, int y, int width, int height){
		this.width = width;
		
		setOpaque(true);
		setBorder(Borders.niceBorder());
		setBackground(Colors.desginBackground);
		setBounds(x, y, width, height);
		
		cover = new JLabel();
		cover.setOpaque(true);
//		cover.setBorder(Borders.niceBorder());
		cover.setBackground(Colors.designForeground);
		cover.setForeground(Colors.desginBackground);
		cover.setBounds(x, y, width, height);
		cover.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane.add(cover);
		contentPane.add(this);
		
	}
	
	public void setValue(int value){
		double d = (width/100)*value;
		cover.setText(value + "%");
		cover.setBounds(cover.getBounds().x, cover.getBounds().y, (int)d, cover.getBounds().height);
	}

}
