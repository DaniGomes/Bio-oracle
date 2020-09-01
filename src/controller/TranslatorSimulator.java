package controller;

/*** Class used to manage the translation of the status of the simulation. ***/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
*
* @authors: Gabriel Franco e Daniela Gomes
*/

public class TranslatorSimulator {
	
	private JFrame frame;
	int language;
	
	/*** This is the main method. It receives a language code as a parameter and initialize the window in that language ***/
	public TranslatorSimulator (int lang) {
		language = lang;
		initialize();
		JPanel panel = mainPanel();
		frame.getContentPane().add(panel);
	}

	/*** This method creates the graphic interface ***/
	private void initialize() {
		frame = new JFrame("BIO-ORACLE");
		frame.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
		frame.setBounds(100, 100, 400, 320);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/*** This method controls the translation of the simulator status to the especific language, received as a parameter ***/
	private JPanel mainPanel() {
        JPanel panel = new JPanel(new MigLayout(new LC().fillX().noGrid()));
        panel.setBackground(new Color(188,210,238));
        
        /*** Translation to Portuguese ***/
        if (language == 1)
        {
        	JLabel title = new JLabel("Tradu��o do Status da Simula��o");
            title.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            panel.add(title, new CC().grow().wrap());
            
            JTextArea statsArea = new JTextArea();
            statsArea.setPreferredSize(new Dimension(500, 600));
            statsArea.setEditable(false);
            statsArea.setBackground(new Color(188,210,238));
            panel.add(statsArea, "span");
            
            statsArea.setText("Inst�ncias corretamente classificadas\n" + "Inst�ncias incorretamente classificadas\n" + "Estat�stica Kappa\n" + "Erro m�dio absoluto\n" + "Erro quadr�tico\n" + "Erro relativo absoluto\n" + "Raiz do erro quadrado relativa\n" + "Cobertura dos casos (n�vel 0.95)\n" + "Tamanho da regi�o m�dia rel. (n�vel 0.95)\n" + "N�mero total de inst�ncias\n" + "===Matriz de confus�o===\n\n");
        }
        /*** Translation to Spanish ***/
        else if (language == 2)
        {
        	JLabel title = new JLabel("Traducci�n de Estado Simulador");
            title.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            panel.add(title, new CC().grow().wrap());
            
            JTextArea statsArea = new JTextArea();
            statsArea.setPreferredSize(new Dimension(500, 600));
            statsArea.setEditable(false);
            statsArea.setBackground(new Color(188,210,238));
            panel.add(statsArea, "span");
            
            statsArea.setText("Casos correctamente clasificados\n" + "Los casos clasificados incorrectamente\n" + "Estad�stica Kappa\n" + "Error absoluto promedio\n" + "Error cuadr�tico\n" + "Error relativo absoluto\n" + "Ra�z del error cuadr�tico relativo\n" + "Cobertura de los casos (nivel 0.95)\n" + "Tama�o de la regi�n media rel. (nivel 0.95)\n" + "N�mero total de casos\n" + "===Matriz de confusi�n===\n\n");
            
            
        }
        return panel;
	}
}
