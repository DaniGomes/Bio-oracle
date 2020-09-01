package controller;

/*** Class used starts the application. ***/

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

/**
*
* @authors: Gabriel Franco e Daniela Gomes
*/

public class Main extends JFrame {
	
	/*** Creating buttons to represent the languages Bio-Oracle supports. ***/
	JButton language1 = new JButton();
	JButton language2 = new JButton();
	JButton language3 = new JButton();
	JPanel mainPanel = new JPanel(new MigLayout());
    AppController appController;
    
    /*** This is the main method. It creates the theme for the graphic interface. ***/
    public static void main(String args[]) {

    	try {
    		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
		}

    	Main App = new Main();
    }

    /*** This method controls the graphic interface and give access to the application in a specific language by managing the buttons. ***/   
    public Main() {
    	setTitle("BIO-ORACLE");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
		setSize(580,330);
		setLocation(500, 300);
	
		mainPanel.setBackground(new Color(188,210,238)); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
		
		JLabel title = new JLabel("Bio-Oracle");
        title.setFont(new Font("High Tower Text", Font.CENTER_BASELINE, 80));
       
        
        language1.setToolTipText("Português");
        language2.setToolTipText("Español");
        language3.setToolTipText("English");
		
		language1.setIcon(new ImageIcon("arquivos" + File.separator + "br.png"));
		language2.setIcon(new ImageIcon("arquivos" + File.separator + "es.png"));
		language3.setIcon(new ImageIcon("arquivos" + File.separator + "us.png"));
		
		mainPanel.add(title, new CC().wrap().spanX().alignX("center"));
		mainPanel.add(language1);
        mainPanel.add(language2);
        mainPanel.add(language3);
	
		add(mainPanel);
		validate();
		
		language1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppController app = new AppController(1);
				close();
				
			}
			
		});
		
		language2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppController app = new AppController(2);
				close();
				
			}
		});
		
		language3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppController app = new AppController(3);
				close();
				
			}
		});
		
    }
    
    /*** This method controls the shutdown of the window. ***/ 
    public void close()
    {
    	this.dispose();
    }

}