/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/*** Class used to manage the simulation of the answer for the model. ***/

import interfaceWithWeka.InterfaceWithWeka;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import util.AppConstants;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Tiago
 * 
 * Changed by Daniela Gomes e Gabriel Franco
 */
public class SimulatorController extends JFrame implements ChangeListener, ActionListener {

    private static InterfaceWithWeka intWithWeka = new InterfaceWithWeka();
    ClassificationController classificationController;
    Map<String, JSlider> instanceSliders = new HashMap<>();
    Map<JSlider, JLabel> slidersLabels = new HashMap<>();
    Map<String, JComboBox> instanceCombos = new HashMap<>();
    Map<JComboBox, JLabel> combosLabels = new HashMap<>();
    JLabel classValue;
    JLabel realClassValue;
    JLabel recomendation;
    JLabel Approbation;
    JButton medical_ethics;
    Instance currentInstance;
    int language;
    
	/*** This is the main method, it initializes the window and catch parameters. ***/
    public SimulatorController(ClassificationController classificationController, int lang) {
        language = lang;
    	this.classificationController = classificationController;
        currentInstance = classificationController.getRandomInstance(AppConstants.POSITIVE_INDEX.getValue());
        initWindow();
        
        this.pack();
        this.setVisible(true);

    }
    
    /*** This method manage some components of the graphical interface of the window like title and icon. ***/
    private void initWindow() {
    	this.setLayout(new MigLayout(new LC().fillX().noGrid()));
    	this.setIconImage(new ImageIcon(getClass().getResource("Oraculo.png")).getImage());
    	this.getContentPane().setBackground(new Color(188,210,238));
        this.setSize(800, 600);
        this.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 15));
        this.add(initSliders(), new CC().grow().newline());
        this.add(initClassPanel(), new CC().grow().newline());
        this.setTitle("BIO-ORACLE");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /*** This method helps in the creation of the graphical interface and it manage buttons and areas. ***/
    private JPanel initClassPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout(new LC().fillX().noGrid()));
        panel.setSize(500, 400);
        panel.setBackground(new Color(188,210,238));
        
        recomendation = new JLabel(classificationController.BO_Recomendation());
        classValue = new JLabel(classificationController.classificateAsString(currentInstance));
        Approbation = new JLabel(classificationController.classificateApprobation(classificationController.numReturn()));
        
        classValue.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 22));
        Approbation.setFont(new Font("Times New Roman", Font.BOLD, 30));
        

        panel.add(classValue, "wrap");
        panel.add(Approbation, "wrap");
        
        if (language == 1)
        {
        	 medical_ethics = new JButton();
             medical_ethics.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
             medical_ethics.addActionListener(this);
             medical_ethics.setText("Código de ética profissional!");
             medical_ethics.setName("Código de ética profissional!");
             medical_ethics.addActionListener(new ActionListener() {
     			
             @Override
             public void actionPerformed(ActionEvent e) {
            	 MedicalEthicsController me = new MedicalEthicsController(classificationController);
     				
     			}
     		});
             
             JPanel buttonPanel_medical = new JPanel();
             buttonPanel_medical.setBackground(new Color(188,210,238));
             buttonPanel_medical.add(medical_ethics, new CC().alignX("center").spanX());
             panel.add(buttonPanel_medical);
        }
        
        if (language == 1)
        {
        	JButton rankButton = new JButton();
            rankButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            rankButton.addActionListener(this);
            rankButton.setText("Ranking de importância dos atributos");
            rankButton.setName("Ranking de importância dos atributos");
            rankButton.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				GainRankController jt = new GainRankController(classificationController, currentInstance, language);
    				
    			}
    		});

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(188,210,238));
            buttonPanel.add(rankButton, new CC().alignX("center").spanX());
            panel.add(buttonPanel);
        }
        else if (language == 2)
        {
        	JButton rankButton = new JButton();
            rankButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            rankButton.addActionListener(this);
            rankButton.setText("Clasificación de importancia de los atributos");
            rankButton.setName("Clasificación de importancia de los atributos");
            rankButton.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				GainRankController jt = new GainRankController(classificationController, currentInstance, language);
    				
    			}
    		});

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(188,210,238));
            buttonPanel.add(rankButton, new CC().alignX("center").spanX());
            panel.add(buttonPanel);
        }
        else
        {
        	JButton rankButton = new JButton();
            rankButton.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 18));
            rankButton.addActionListener(this);
            rankButton.setText("Information Gain Rank");
            rankButton.setName("Information Gain Rank");
            rankButton.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				GainRankController jt = new GainRankController(classificationController, currentInstance, language);
    				
    			}
    		});

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(188,210,238));
            buttonPanel.add(rankButton, new CC().alignX("center").spanX());
            panel.add(buttonPanel);
        }
        
        return panel;
    }
    
  
    /*** This method shows the instances and manage the input type of the answer for each of them. ***/ 
    private JPanel initSliders() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(188,210,238));
        panel.setLayout(new MigLayout());
        if (language == 1)
        {
        	JLabel instanceTitle = new JLabel("Instância atual");
            instanceTitle.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 22));

            panel.add(instanceTitle, "wrap");
        }
        else if (language == 2)
        {
        	JLabel instanceTitle = new JLabel("Instancia actual");
            instanceTitle.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 22));

            panel.add(instanceTitle, "wrap");
        }
        else
        {
        	JLabel instanceTitle = new JLabel("Current Instance");
            instanceTitle.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 22));

            panel.add(instanceTitle, "wrap");
        }
        for (int i = 0; i < currentInstance.numAttributes() - 1; i++) {
            if (currentInstance.attribute(i).type() == Attribute.NUMERIC) {
                JSlider jSlider;
                jSlider = new JSlider(0, AppConstants.MAX_ATT_VALUE.getValue(), (int) currentInstance.value(i));
                jSlider.setName(String.valueOf(i));
                jSlider.setToolTipText(currentInstance.attribute(i).name());
                jSlider.addChangeListener(this);
                instanceSliders.put(currentInstance.attribute(i).name(), jSlider);
                slidersLabels.put(jSlider, new JLabel(currentInstance.attribute(i).name() + ": " + classificationController.denormalize(currentInstance.attribute(i).name(), currentInstance.value(i))));
            } else if (currentInstance.attribute(i).type() == Attribute.NOMINAL) {
                JComboBox comboBox;
                comboBox = new JComboBox<>(Collections.list(currentInstance.attribute(i).enumerateValues()).toArray());
                comboBox.setName(String.valueOf(i));
                comboBox.setSelectedIndex((int) currentInstance.value(Integer.parseInt(comboBox.getName())));
                comboBox.addActionListener(this);
                instanceCombos.put(currentInstance.attribute(i).name(), comboBox);
                combosLabels.put(comboBox, new JLabel(currentInstance.attribute(i).name()));
            }
        }


        for (JSlider js : instanceSliders.values()) {
            panel.add(slidersLabels.get(js), new CC().alignX("left"));
            panel.add(js, new CC().wrap().alignX("right"));
        }
        for (JComboBox combo : instanceCombos.values()) {
            panel.add(combosLabels.get(combo), new CC().alignX("left"));
            panel.add(combo, new CC().wrap().alignX("right"));

        }
        return panel;
    }
  
    
    /*** This method updates the values of the objects in the Slider. ***/
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            JLabel label = slidersLabels.get(slider);
            label.setText(slider.getToolTipText() + ": " + classificationController.denormalize(slider.getToolTipText(), slider.getValue()));
            currentInstance.setValue(Integer.parseInt(slider.getName()), slider.getValue());
            recomendation.setText(String.valueOf(classificationController.BO_Recomendation()));
            classValue.setText(String.valueOf(classificationController.classificateAsString(currentInstance)));
            Approbation.setText(String.valueOf(classificationController.classificateApprobation(classificationController.numReturn())));
            realClassValue.setText("[UNKNOW]");
        }
    }

    /*** This method updates the values of the instances. ***/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) e.getSource();
            currentInstance.setValue(Integer.parseInt(comboBox.getName()), comboBox.getSelectedItem().toString());
            recomendation.setText(String.valueOf(classificationController.BO_Recomendation()));
            classValue.setText(String.valueOf(classificationController.classificateAsString(currentInstance)));
            Approbation.setText(String.valueOf(classificationController.classificateApprobation(classificationController.numReturn())));
        }
    }
    
    /*** This method catch the value of the instance in a moment and calculate and update the data. ***/
    /*private void updateInstance() {
        for (JSlider slider : instanceSliders.values()) {
            slider.setValue((int) currentInstance.value(Integer.parseInt(slider.getName())));
        }
        for (JComboBox combo : instanceCombos.values()) {
            combo.setSelectedIndex((int) currentInstance.value(Integer.parseInt(combo.getName())));
        }
        realClassValue.setText(classificationController.classValueFromIndex((int) currentInstance.value(currentInstance.numAttributes() - 1)));
    }*/
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /*private void displayInstance() {
    }
    public static void main(String args[]) {
        Instances insts = intWithWeka.load("D:/Mestrado/dbweka- Atributos finais.arff");
        System.out.println(insts.numInstances());

    }
    public void preprocess(Instances insts) {
    }*/
}
