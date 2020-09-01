package controller;

import java.awt.Color;

import javax.swing.BorderFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import weka.classifiers.Evaluation;

/**
 *
 * @author Tiago
 * 
 * Changed by Daniela Gomes e Gabriel Franco
 */
public class AppController extends JFrame implements ActionListener, ChangeListener, FocusListener {
	public int language=0; 
    private File trainingFile = null;
    private Map<Component, JLabel> labelsMap = new HashMap<>();
    JButton help = new JButton();
    AppController appController;
    JSlider percSlider;
    JTextField r1c1 = new JTextField(3), r1c2 = new JTextField(3), r2c1 = new JTextField(3), r2c2 = new JTextField(3);
    ClassificationController classificationController = null;
    Main main;
    JTextArea statsArea;
    ButtonGroup algorithmGroup;
    
    /*** Obs.: language = 1 (Portuguese)
     * 		   language = 2 (Spanish)
     * 		   language = 3 (English)
     */
    
    
    /*** This is the main method, it initializes the AppController window. ***/
    public AppController(int lang) {
    	language = lang;
    	this.initWindow(lang);
    	this.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
        this.setSize(695,725);
    }
    
    /*** This method initialize the initial window ***/
    private void initWindow(int lang) {
        JPanel mainPanel = new JPanel(new MigLayout());
        mainPanel.setBackground(new Color(188,210,238)); 
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
         
        if(language==1){
        	JButton openTraining = new JButton("Abrir dados de treinamento");
        	openTraining.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        	openTraining.setName("train");
            openTraining.addActionListener(this);
            mainPanel.add(openTraining, new CC().alignX("left"));
        }
        else if(language==2){
        	JButton openTraining = new JButton("Abrir datos de entrenamiento");
        	openTraining.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        	openTraining.setName("train");
            openTraining.addActionListener(this);
            mainPanel.add(openTraining, new CC().alignX("left"));
        }
        else if(language==3){
        	JButton openTraining = new JButton("Open Training Data");
        	openTraining.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        	openTraining.setName("train");
            openTraining.addActionListener(this);
            mainPanel.add(openTraining, new CC().alignX("left"));
        }
        
        
        if(language==1){
        	JButton simulate = new JButton("Clique aqui para consultar o Bio-Oracle!");
            simulate.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            simulate.setName("simulate");
            simulate.addActionListener(this);
            mainPanel.add(simulate, new CC().wrap().alignX("right"));
        }
        else if(language==2){
        	JButton simulate = new JButton("Haga un clic aquí para consultar el Bio-Oracle!");
            simulate.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            simulate.setName("simulate");
            simulate.addActionListener(this);
            mainPanel.add(simulate, new CC().wrap().alignX("right"));
        }
        else if(language==3){
        	JButton simulate = new JButton("Click here to consult Bio-Oracle!");
            simulate.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            simulate.setName("simulate");
            simulate.addActionListener(this);
            mainPanel.add(simulate, new CC().wrap().alignX("right"));
        }
        

        JPanel learningOption = new JPanel(new MigLayout());
        learningOption.setBackground(new Color(188,210,238));
        JRadioButton mpButton = new JRadioButton("Multilayer-Perceptron");
        mpButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        mpButton.setBackground(new Color(188,210,238));
        mpButton.setName("mp");
        mpButton.addActionListener(this);
        JRadioButton smoButton = new JRadioButton("Support Vector Machine");
        smoButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        smoButton.setBackground(new Color(188,210,238));
        smoButton.setName("smo");
        smoButton.addActionListener(this);
        JRadioButton nbButton = new JRadioButton("Naive-Bayes");
        nbButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        nbButton.setBackground(new Color(188,210,238));
        nbButton.setSelected(true);
        nbButton.setName("nb");
        nbButton.addActionListener(this);

        algorithmGroup = new ButtonGroup();
        algorithmGroup.add(mpButton);
        algorithmGroup.add(smoButton);
        algorithmGroup.add(nbButton);
        algorithmGroup.setSelected(nbButton.getModel(), true);

        learningOption.add(nbButton);
        learningOption.add(smoButton);
        learningOption.add(mpButton);
        TitledBorder learningBoder = new TitledBorder(new LineBorder(Color.black, 2));
        learningBoder.setTitleFont(new Font("Times New Roman", Font.ITALIC, 15));
        learningBoder.setTitle("Learning Algorithm");
        learningBoder.setTitleJustification(TitledBorder.CENTER);
        
               
        if (language == 1)
        {
        	  JLabel percLabel = new JLabel("Percentual de dados que vão ser utilizados no treinamento : 100");
              percLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
              JSlider percSlider = new JSlider(1, 100, 100);
              percSlider.setBackground(new Color(188,210,238));
              labelsMap.put(percSlider, percLabel);
              percSlider.addChangeListener(this);
              this.percSlider = percSlider;
              mainPanel.add(percLabel, new CC().wrap().alignX("center").spanX());
        }
        else if (language == 2)
        {
        	  JLabel percLabel = new JLabel("Porcentaje de datos a utilizar en la formación: 100");
              percLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
              JSlider percSlider = new JSlider(1, 100, 100);
              percSlider.setBackground(new Color(188,210,238));
              labelsMap.put(percSlider, percLabel);
              percSlider.addChangeListener(this);
              this.percSlider = percSlider;
              mainPanel.add(percLabel, new CC().wrap().alignX("center").spanX());
        }
        else
        {
        	  JLabel percLabel = new JLabel("Percentage of data to be used in training: 100");
              percLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
              JSlider percSlider = new JSlider(1, 100, 100);
              percSlider.setBackground(new Color(188,210,238));
              labelsMap.put(percSlider, percLabel);
              percSlider.addChangeListener(this);
              this.percSlider = percSlider;
              mainPanel.add(percLabel, new CC().wrap().alignX("center").spanX());
        }
        
        
        JPanel statsPanel = new JPanel(new MigLayout());
        statsPanel.setBackground(new Color(188,210,238));
        statsPanel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        statsPanel.add(initCostMatrix());
        statsPanel.add(initStatsPanel(lang));
          
        mainPanel.add(percSlider, new CC().wrap().alignX("center").spanX());
        mainPanel.add(learningOption, new CC().wrap().spanX().alignX("center"));
        mainPanel.add(statsPanel, "span");
        
        if (language == 1)
        {
        	JButton traducao = new JButton("Tradução do Status da Simulação");
        	traducao.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        	mainPanel.add(traducao, new CC().wrap().spanX().alignX("center"));
        	traducao.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent e) {
					TranslatorSimulator me = new TranslatorSimulator(language);
        				
        			}
        		});
        }
        else if (language == 2)
        {
        	JButton traducao = new JButton("Traducción de Estado Simulador");
        	traducao.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
        	mainPanel.add(traducao, new CC().wrap().spanX().alignX("center"));
        	
        	traducao.addActionListener(new ActionListener() {
     			
                @Override
                public void actionPerformed(ActionEvent e) {
               	 TranslatorSimulator me = new TranslatorSimulator(language);
        				
        			}
        		});
        }
 
        if(language == 1){
        	help.setToolTipText("Ajuda");
        	help.setIcon(new ImageIcon("arquivos" + File.separator + "help.png"));
        	mainPanel.add(help,  new CC().wrap().spanX().alignX("right"));
        	help.addActionListener(new ActionListener() {
     			
                @Override
                public void actionPerformed(ActionEvent e) {
               	 HelpController H = new HelpController();
        				
        			}
        		});
        	
        	
        }
       
        
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("BIO-ORACLE");
        this.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
    }
    
    /*** This method iniciate the Stats Panel ***/
    JPanel initStatsPanel(int lang) {
        JPanel statsPanel = new JPanel(new MigLayout());
        statsPanel.setBackground(new Color(188,210,238));
        if(lang==1){
        	JLabel titlePanel = new JLabel("Status da Simulação");
        	statsPanel.add(titlePanel, "span" );
        }
        else if(lang==2){
        	JLabel titlePanel = new JLabel("Estado Simulador");
        	statsPanel.add(titlePanel, "span" );
        }
        else if(lang==3){
        	JLabel titlePanel = new JLabel("Simulator Status");
        	statsPanel.add(titlePanel, "span" );
        }

        statsArea = new JTextArea();
        statsArea.setPreferredSize(new Dimension(500, 600));
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(188,210,238));
        statsPanel.add(statsArea, "span");
        
        
        return statsPanel;
    }
    
    /*** This method choose the classification code ***/
    int getClassifierCode() {
        Enumeration<AbstractButton> buttons = algorithmGroup.getElements();
        for (; buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                if (button.getName().equalsIgnoreCase("mp")) {
                    return 0;
                } else if (button.getName().equalsIgnoreCase("smo")) {
                    return 1;
                }
            	else if (button.getName().equalsIgnoreCase("nb")) {
            		return 2;
            	}
            }
        }
        return 0;
    }
    
    /*** This method open a chosen file ***/
    public static ClassificationController openFromFile(String filename) {
        ObjectInputStream ois = null;
        ClassificationController controller = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
        	controller = (ClassificationController) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return controller;

    }
    
    /*** This method open a training file ***/
    void openTrainingFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("ARFF file", "arff"));
        int status = fileChooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getSelectedFile());
            trainingFile = fileChooser.getSelectedFile();
            File modelFile = new File("model.temp");

            try {
                classificationController = new ClassificationController(trainingFile.toString(), this.percSlider.getValue(), false, 0, language);
                this.setCostMatrix(classificationController.calculateCostMatrix());
                classificationController.train(this.getCostMatrix(), getClassifierCode());
                classificationController.save(modelFile.toString());
                updateStats();
            } catch (Exception ex) {
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Cancelled");
        }
    }
    
    /*** This method it makes Simulation ***/
    private void simulate() {
        if (classificationController == null) {
        	if(language==1)	JOptionPane.showMessageDialog(this, "Selecione um arquivo de treinamento antes de iniciar a simulação.", "Aviso!", JOptionPane.WARNING_MESSAGE);
        	else if(language==2) JOptionPane.showMessageDialog(this, "Seleccionar un archivo de formación antes de comenzar la simulación.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        	else if(language==3) JOptionPane.showMessageDialog(this, "Select a training file before starting the simulation.", "Warning!", JOptionPane.WARNING_MESSAGE);
        } else {
            classificationController.train(this.getCostMatrix(), getClassifierCode());
            SimulatorController simulatorController = new SimulatorController(classificationController, language);

        }
    }
   

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getName().equalsIgnoreCase("train")) {				
                this.openTrainingFile();
            }else if (button.getName().equalsIgnoreCase("simulate")) {
                this.simulate();
            }
        } else if (e.getSource() instanceof JRadioButton) {
            if (classificationController != null) {
                classificationController.train(this.getCostMatrix(), getClassifierCode());
                this.updateStats();
            }
        }
    }
    
    /*** This method initiates the cost matrix  ***/
    public JPanel initCostMatrix() {
        JPanel costMatrixPanel = new JPanel(new MigLayout());
        costMatrixPanel.setBackground(new Color(188,210,238));
        r1c1.setEnabled(false);
        r1c2.setEnabled(false);
        r2c1.setEnabled(false);
        r2c2.setEnabled(false);
        r1c1.addFocusListener(this);
        r1c2.addFocusListener(this);
       	r2c1.addFocusListener(this);
        r2c2.addFocusListener(this);
        
        if(language==1){
        	JLabel costLabel = new JLabel("MATRIZ DE CUSTOS");
            costLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
            costMatrixPanel.add(costLabel, "span");
        }
        else if(language==2){
        	JLabel costLabel = new JLabel("MATRIZ DE COSTOS");
            costLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
            costMatrixPanel.add(costLabel, "span");
        }
        else if(language==3){
        	JLabel costLabel = new JLabel("COST MATRIX");
            costLabel.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
            costMatrixPanel.add(costLabel, "span");
        }
       
        costMatrixPanel.add(r1c1);
        costMatrixPanel.add(r1c2, "wrap");
        costMatrixPanel.add(r2c1);
        costMatrixPanel.add(r2c2);
        return costMatrixPanel;
    }
    
    /*** This method set the values of the cost matrix  ***/
    public void setCostMatrix(double[][] values) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols separator = new DecimalFormatSymbols();
        separator.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(separator);
        r1c1.setEnabled(true);
        r1c2.setEnabled(true);
        r2c1.setEnabled(true);
        r2c2.setEnabled(true);
        r1c1.setText(df.format(values[0][0]));
        r1c2.setText(df.format(values[0][1]));
        r2c1.setText(df.format(values[1][0]));
        r2c2.setText(df.format(values[1][1]));
        
    }
    
    /*** This method returns the cost matrix  ***/
    public double[][] getCostMatrix() {
        double[][] values = {
            {Double.valueOf(r1c1.getText()), Double.valueOf(r1c2.getText())},
            {Double.valueOf(r2c1.getText()), Double.valueOf(r2c2.getText())}
        };

        return values;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            JLabel label = labelsMap.get(slider);

            label.setText(label.getText().substring(0, label.getText().indexOf(":") + 1));
            label.setText(label.getText() + " " + slider.getValue());
        }
    }
    
    /*** This method updates the values of the cost matrix  ***/
    void updateStats() {
        try {
            Evaluation eval = classificationController.getAccuracy();
            statsArea.setText(eval.toSummaryString() + eval.toMatrixString());
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*** This method returns the selected language  ***/
    public int langReturn ()
    {
    	return language;
    }
    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        classificationController.train(this.getCostMatrix(), getClassifierCode());
        updateStats();

    }
}
