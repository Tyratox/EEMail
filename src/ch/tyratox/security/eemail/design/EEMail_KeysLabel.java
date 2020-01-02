package ch.tyratox.security.eemail.design;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import ch.tyratox.security.eemail.EEMail;

public class EEMail_KeysLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7499150070631081033L;
	
	private EEMail main;
	
	public EEMail_KeysLabel(EEMail m){
		
		main = m;
		
		setOpaque(true);
		setBackground(Colors.menuBarKeysBackgroundColor);
		setBorder(null);
		setForeground(Colors.designForeground);
		
		setFont(Fonts.lato(getClass()));
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setText("Keys");
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Colors.menuBarKeysBackgroundColor);
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Colors.menuBarKeysBackgroundColor_hover);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Colors.menuBarKeysBackgroundColor);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Colors.menuBarKeysBackgroundColor_hover);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setBackground(Colors.menuBarKeysBackgroundColor_hover);
				
				JPopupMenu pop = new JPopupMenu();
				pop.setOpaque(true);
				pop.setForeground(Colors.designForeground);
				pop.setBackground(Colors.menuBarKeysBackgroundColor);
				
				JMenuItem gNK = new JMenuItem("Generate new keys");
				gNK.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							main.generateNewKeys();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				gNK.setOpaque(true);
				gNK.setBackground(Colors.menuBarKeysBackgroundColor);
				gNK.setForeground(Colors.designForeground);
				gNK.setFont(Fonts.lato(getClass()));
				
				pop.add(gNK);
				
				JMenuItem getPrivateKey = new JMenuItem("Generate Installer");
				getPrivateKey.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							JFileChooser fc = new JFileChooser();
							fc.setDialogTitle("Where do you want to save the installer?");
							fc.setApproveButtonText("Save");
							fc.setMultiSelectionEnabled(false);
							fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							if(fc.showDialog(main.gui, "Save") == JFileChooser.APPROVE_OPTION){
								if(fc.getSelectedFile().isDirectory()){
									String path = fc.getSelectedFile().getAbsolutePath();
									byte[] jar = IOUtils.toByteArray(getClass().getResourceAsStream("/res/installer/Installer.jar"));
									FileOutputStream fos = new FileOutputStream(new File(path + "/Installer.jar"));
									fos.write(jar);
									fos.close();
									
									ZipFile z = new ZipFile(path + "/Installer.jar");
									
									ZipParameters parameters = new ZipParameters();

									parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
									parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
									ArrayList<File> fls = new ArrayList<File>();
									if(new File(main.appKeyOldPath + "privateOld.pem").exists()){
										new File(main.appKeyOldPath + "privateOld.pem").delete();
									}
									FileUtils.copyFile(new File(main.appKeyPath + "private.pem"), new File(main.appKeyOldPath + "privateOld.pem"));
									fls.add(new File(main.appConfigPath + "data.save"));
									fls.add(new File(main.appKeyPath + "mypublic.pem"));
									fls.add(new File(main.appKeyPath + "private.pem"));
									fls.add(new File(main.appKeyOldPath + "privateOld.pem"));
									z.addFiles(fls, parameters);
									  
								}
							}else{
								
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				getPrivateKey.setOpaque(true);
				getPrivateKey.setBackground(Colors.menuBarKeysBackgroundColor);
				getPrivateKey.setForeground(Colors.designForeground);
				getPrivateKey.setFont(Fonts.lato(getClass()));
				
				pop.add(getPrivateKey);
				pop.show(main.gui, getBounds().x, getBounds().y);
			}
		});
	}

}
