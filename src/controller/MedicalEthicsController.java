package controller;

/*** Class used to manage the files of Medical Ethics. ***/

import java.awt.Color;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

/**
*
* @authors: Gabriel Franco e Daniela Gomes
*/

public class MedicalEthicsController {
	
	private JFrame frame;
	
	/*** This is the main method, it initializes the window. ***/
	public MedicalEthicsController (ClassificationController classificationController) {
		initialize();
		JPanel panel = mainPanel();
		frame.getContentPane().add(panel);
	}

	/*** This method manage some components of the graphical interface of the window like title and icon. ***/
	private void initialize() {
		frame = new JFrame("BIO-ORACLE");
		frame.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
		frame.setBounds(100, 100, 450, 210);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/*** This method helps in the creation of the graphical interface and it give access to the PDF files by managing the buttons. ***/
	private JPanel mainPanel() {
        JPanel panel = new JPanel(new MigLayout(new LC().fillX().noGrid()));
        panel.setBackground(new Color(188,210,238));
        
        JButton medical = new JButton("Código de Ética Médica");
        medical.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        JButton nursing = new JButton("Código de Ética na Enfermagem");
        nursing.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        JButton physio = new JButton("Código de Ética Fisioterapeutica");
        physio.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        JButton nutrition = new JButton("Código de Ética na Nutrição");
        nutrition.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        
        medical.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PDFMedical jt = new PDFMedical();
				
			}
		});
        
		nursing.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						PDFNursing jt = new PDFNursing();
						
					}
				});
		
		physio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PDFPhysio jt = new PDFPhysio();
				
			}
		});
		
		nutrition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PDFNutrition jt = new PDFNutrition();
				
			}
		});
		
        panel.add(medical, new CC().alignX("center").spanX().wrap());
        panel.add(nursing, new CC().alignX("center").spanX().wrap());
        panel.add(physio, new CC().alignX("center").spanX().wrap());
        panel.add(nutrition, new CC().alignX("center").spanX().wrap());
     
        return panel;
	}
}
