package controller;

/*** Class used to manage the help files. ***/

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
*
* @authors: Gabriel Franco e Daniela Gomes
*/

public class HelpController {
	
	private JFrame frame;
	
	/*** This is the main method, it initializes the help window. ***/
	public HelpController () {
		initialize();
		JPanel panel = mainPanel();
		frame.getContentPane().add(panel);
	}

	/*** This method manage some components of the graphical interface of the window like title and icon. ***/
	private void initialize() {
		frame = new JFrame("BIO-ORACLE");
		frame.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
		frame.setBounds(100, 100, 450, 210);
		frame.setSize(350,250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/*** This method helps in the creation of the graphical interface and it give access to the tutorial files by managing the buttons. ***/
	private JPanel mainPanel() {
        JPanel panel = new JPanel(new MigLayout(new LC().fillX().noGrid()));
        panel.setBackground(new Color(188,210,238));
        
        JLabel contactLab1 = new JLabel("Contato L-MECS:");
        contactLab1.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 20));
        
        JLabel contactLab2 = new JLabel("E-mail: lmecs@ufv.br");
        contactLab1.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        
        JLabel contactLab3 = new JLabel("Telefone: (31) 3899-3978");
        contactLab1.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        
        JButton tutWin = new JButton("Tutorial Bio-Oracle para Windows");
        tutWin.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        JButton tutLin = new JButton("Tutorial Bio-Oracle para Linux");
        tutLin.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));		//NAO APAGAR! COMENTADO APENAS PORQUE AINDA NÃO TEMOS OS TUTORIAIS DE MAC
        /*JButton tutMac = new JButton("Tutorial Bio-Oracle para Mac");
        tutMac.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));*/
        
        tutWin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PDFWindowsTutorial jt = new PDFWindowsTutorial();	
				
			}
		});
        
        tutLin.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {					
						PDFLinuxTutorial jt = new PDFLinuxTutorial();
						
					}
				});
		
        /*tutMac.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PDFMacOsTutorial jt = new PDFMacOsTutorial();
				
			}
		});*/
        
        panel.add(contactLab1, new CC().alignX("center").spanX().wrap());
        panel.add(contactLab2, new CC().alignX("center").spanX().wrap());
        panel.add(contactLab3, new CC().alignX("center").spanX().wrap());
        panel.add(tutWin, new CC().alignX("center").spanX().wrap());
        panel.add(tutLin, new CC().alignX("center").spanX().wrap());
        //panel.add(tutMac, new CC().alignX("center").spanX().wrap());				//NAO APAGAR! COMENTADO APENAS PORQUE AINDA NÃO TEMOS OS TUTORIAIS DE MAC
     
        return panel;
	}

}
