package controller;

/*** Class used to manage the rank of attributes. ***/

import java.awt.Color;


import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicBorders;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import util.AppUtil;

import weka.core.Instance;

/**
*
* @authors: Gabriel Franco e Daniela Gomes
*/

public class GainRankController {

	private JFrame frame;
	private ClassificationController classificationController;
	private Instance currentInstance;
	
	int language;
	
	/*** This is the main method, it initializes the window and catch the parameters. ***/
	public GainRankController(ClassificationController classificationController, Instance currentInstance, int lang) {
		language = lang;
		this.classificationController = classificationController;
		this.currentInstance = currentInstance;
		initialize();
		JPanel panel = initAttRankPanel();
		frame.getContentPane().add(panel);
	}

	/*** This method manage some components of the graphical interface of the window like title and icon. ***/
	private void initialize() {
		frame = new JFrame("BIO-ORACLE");
		frame.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
		frame.setBounds(200, 200, 650, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/*** This method calculate the importance of the instances for the study and show them. ***/
	private JPanel initAttRankPanel() {
		int ATT_INDEX = 0, ATT_MERIT = 1;
        JPanel panel = new JPanel(new MigLayout(new LC().fillX().noGrid()));
        panel.setBackground(new Color(188,210,238));

        if (language == 1)
        {
        	JLabel title = new JLabel("Ranking de importância dos atributos");
            title.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            panel.add(title, new CC().grow().wrap());
        }
        else if (language == 2)
        {
        	JLabel title = new JLabel("Clasificación de importancia de los atributos");
            title.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            panel.add(title, new CC().grow().wrap());
        }
        else
        {
        	JLabel title = new JLabel("Information Gain Rank");
            title.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            panel.add(title, new CC().grow().wrap());
        }
        
        JTextArea rankArea1 = new JTextArea();
        rankArea1.setEditable(false);
        rankArea1.setBackground(new Color(188,210,238));
        panel.add(rankArea1);
        
        JTextArea rankArea2 = new JTextArea();
        rankArea2.setEditable(false);
        rankArea2.setBackground(new Color(188,210,238));
        panel.add(rankArea2);

        double attRank[][] = classificationController.rankAttributes();
        
        String text = "";
        String numbers = "";
        
       for (int i = 0; i < attRank.length; i++) {
    	   text += currentInstance.attribute((int)attRank[i][ATT_INDEX]).name() + "\n";
    	   numbers += AppUtil.formatDouble(attRank[i][ATT_MERIT]) + "\n";
            
        }
       
       	rankArea1.setText(text);
       	rankArea2.setText(numbers);

        panel.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.gray, Color.gray, Color.gray));
        return panel;

    }

}
