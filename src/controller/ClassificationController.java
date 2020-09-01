/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/*** Class used to implement the neural network algorithms in the database and manage the results. ***/

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import util.AppConstants;
import util.AppUtil;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author Tiago
 * Changed by Daniela Gomes e Gabriel Franco
 */

public class ClassificationController implements Serializable{
    private Discretize discretizeFilter = null;
    private Normalize normalizeFilter = null;
    private Classifier classifier = null;
    private Instances originalInsts = null;
    private Instances discInsts = null;
    private Instances trainInstances;
    private Map<String, Double> minValue = new HashMap<>(), maxValue = new HashMap<>();
    private boolean discretized;
    private String subSet = null;
    private Double percTrain = null;
    private Random rand = new Random(0);
    
    double numDouble;
    
    
    int language;
    
    public Discretize getFilter() {
        return discretizeFilter;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public Instances getOriginalInsts() {
        return originalInsts;
    }

    public Instances getDiscInsts() {
        return discInsts;
    }

    public String getSubSet() {
        return subSet;
    }
    
    /*** This method checks if the database is discretized or not. ***/
    public boolean isDiscretized() {
        return discretized;
    }

    /*** This is the main method. It defines the database. ***/
    public static void main(String args[]) {
        try {
            String databaseName = "D:/Mestrado/newdbWeka - Atts finais.arff";
            boolean discretize = false;
            ClassificationController controller = new ClassificationController(databaseName, 100, discretize,0, 1);
            controller.save("D:/Mestrado/testes/trainer.wk");
            for(int i=0;i<10;i++){
                System.out.println(controller.getRandomInstance(AppConstants.NEGATIVE_INDEX.getValue()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /*** This method saves the training file. ***/
    public void save(String filename){
        try {
            File trainerFile = new File(filename);
            AppUtil.createEmptyFile(trainerFile);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trainerFile));
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*** This method formats the database. ***/
    public double[] classificateAsDouble(Instance inst){
        try {        
            return classifier.distributionForInstance(inst);
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
    /*** This method selects a random instance. ***/
    public Instance getRandomInstance(int classIndex){
        Instances insts = AppUtil.getInstancesOfGivenClass(classIndex, trainInstances);

        int instIndex = rand.nextInt(insts.numInstances());
        return insts.get(instIndex);
    }
    
    /*** This method makes impossible the usage of a negative average. ***/
    public Instance getAverageNegativeInstance(){
        return null;
    }
    
    /*** This method loads the instances and processes them. ***/
    public ClassificationController(String filename,double percTrain, boolean discretize, int classifierOption, int lang) throws Exception {
    	language = lang;
    	originalInsts = AppUtil.loadInstances(filename);
        this.discretized = discretize;
        if (discretize) {
            discretize();
        }
        this.percTrain = percTrain;
        this.preTrain();
    }

    /*** Pre-processing the instances. ***/
    private void preTrain() throws Exception {

        if (discInsts != null) {
            trainInstances = discInsts;
        } else if (originalInsts != null) {
            trainInstances = originalInsts;
        } else {
            throw new Exception("Training database could not be opened properly.");
        }
        
        initDenormalization();
        
        normalizeFilter = new Normalize();
        normalizeFilter.setScale(AppConstants.MAX_ATT_VALUE.getValue());
        normalizeFilter.setInputFormat(trainInstances);
        trainInstances = Filter.useFilter(trainInstances, normalizeFilter);
        

    }
    
    /*** Processing. This method starts the calculations for the cost matrix. ***/
    public void train(double [][] costMatrixValues, int classifierCode){
        
        initClassifier(initCostMatrix(costMatrixValues), classifierCode);
        try {
            classifier.buildClassifier(AppUtil.resizeSet(trainInstances, percTrain));
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*** This method gets the accuracy of the answer of the model. ***/
    public Evaluation getAccuracy(){
        try {
            Evaluation eval = new Evaluation(trainInstances);
            eval.crossValidateModel(classifier, trainInstances, 10, new Random(0));
            System.out.println(eval.toSummaryString());
            return eval;
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
        
    }
        
    /*** This method creates the rank of attributes. ***/
    public double[][] rankAttributes(){
        try {
            InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
            infoGain.buildEvaluator(trainInstances);
            Ranker ranker = new Ranker();
            ranker.search(infoGain, trainInstances);
            return ranker.rankedAttributes();
            
        } catch (Exception ex) {
            Logger.getLogger(ClassificationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    /*** This method do the calculations for the cost matrix. ***/
    public double[][] calculateCostMatrix(){
        
        double positive_cost = ((double)AppUtil.getInstancesOfGivenClass(AppConstants.POSITIVE_INDEX.getValue(), trainInstances).numInstances())/((double)trainInstances.numInstances());
        double negative_cost = ((double)AppUtil.getInstancesOfGivenClass(AppConstants.NEGATIVE_INDEX.getValue(), trainInstances).numInstances())/((double)trainInstances.numInstances());
        
        double[][] values = {
                                {0,negative_cost},
                                {positive_cost, 0}
                            };
        
        return values;
    }
    
    /*** This method creates the cost matrix. ***/
    private CostMatrix initCostMatrix(double[][] values){
        CostMatrix costMatrix = new CostMatrix(2);        
       
        costMatrix.setCell(0, 0, values[0][0]);
        costMatrix.setCell(0, 1, values[0][1]);
        costMatrix.setCell(1, 0, values[1][0]);
        costMatrix.setCell(1, 1, values[1][1]);
        return costMatrix;
    }
    
    /*** This method initialize the object. ***/
    private void initClassifier(CostMatrix costMatrix, int code){
        CostSensitiveClassifier csClassifier = new CostSensitiveClassifier();
        csClassifier.setCostMatrix(costMatrix);        
        csClassifier.setClassifier(getClassifier(code));
        
        classifier = csClassifier;
    }
    
    /*** This method creates an instance of the chosen algorithm of neural network. ***/
    private Classifier getClassifier(int code){
        if(code == 0){
            return new MultilayerPerceptron();
        }if(code==1){
            SMO smo = new SMO();
            smo.setBuildLogisticModels(true);
            return smo;
        }
        if (code==2)
        {
        	NaiveBayes nb = new NaiveBayes();
        	nb.setUseSupervisedDiscretization(true);
        	return nb;
        }
        else{
            return null;
        }
    }
    
    /*** This method searches the extremes values for the attributes. ***/
    private void initDenormalization(){
        
        for(Instance inst : trainInstances){
            for(int i=0; i< inst.numAttributes(); i++){
                String attName = inst.attribute(i).name();
                Double min = minValue.get(attName);
                Double max = maxValue.get(attName);
                if(min == null || min > inst.value(i)){
                    minValue.put(attName, inst.value(i));
                }
                if(max == null || max < inst.value(i)){
                    maxValue.put(attName, inst.value(i));
                }
                
            }
        }
    }
    
    /*** This method defines the interval of values for the attributes. ***/
    public double denormalize(String attName, double value){
        Double min = minValue.get(attName);
        Double max = maxValue.get(attName);
        Double interval = max-min;
        String returnValue = AppUtil.formatDouble(min+(interval * (value / AppConstants.MAX_ATT_VALUE.getValue())));
        return Double.parseDouble(returnValue);
    }
    
    /*** This method discretizes the values of the attributes. ***/
    private void discretize() {
        try {
            discretizeFilter = new Discretize();
            discretizeFilter.setInputFormat(originalInsts);
            discInsts = Filter.useFilter(originalInsts, discretizeFilter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
       
    /*** This method checks the answer of the algorithm of neural network. ***/
    public String BO_Recomendation(){
    	if (language == 1)
    		return "O Bio-Oracle recomenda a realização do procedimento.";
    	else if (language == 2)
    		return "Bio-Oracle recomienda el procedimiento.";
    	else
    		return "The Bio-Oracle recommends the realization of the procedure.";
    }
    
    /*** This method shows the accuracy of the answer. ***/
    public String classificateAsString(Instance inst){
        
        double[] results = this.classificateAsDouble(inst);        
        double prediction = (results[AppConstants.POSITIVE_INDEX.getValue()]*100);
        numDouble = Double.parseDouble(AppUtil.formatDouble(prediction));
        if (language == 1)
        	return  "O modelo indica que existe " + AppUtil.formatDouble(prediction) + " % de chance de que a realização do procedimento é a melhor decisão.";
        else if (language == 2)
        	return  "El modelo indica un " + AppUtil.formatDouble(prediction) + " % de posibilidades de que la realización del procedimiento es la mejor decisión.";
        else
        	return  "The model indicates a " + AppUtil.formatDouble(prediction) + " % chance that the realization of the procedure is the best decision.";
    }
    
    /*** This method decides what answer the Bio-Oracle will give due to the accuracy of the answer of the algorithm applied. ***/
    public String classificateApprobation (double numDouble)
    {
    	if (numDouble > 50.0)
        {
    		if (language == 1)
    			return "Bio-Oracle recomenda a realização do procedimento";
            else if (language == 2)
            	return "Bio-Oracle recomienda el procedimiento.";
            else
            	return "Bio-Oracle recommends the realization of the procedure.";
        }
        else
        {
        	if (language == 1)
    			return "Bio-Oracle não recomenda a realização do procedimento";
            else if (language == 2)
            	return "Bio-Oracle no recomienda el procedimiento.";
            else
            	return "Bio-Oracle doesn't recommend the realization of the procedure.";
        	
        }
    }
    
    /*** This method returns the prediction of the answer. ***/
    public double numReturn()
    {
    	return numDouble;
    }
    
    /*** This method prevents of the usage of negative numbers. ***/ 
    public String classValueFromIndex(int index){
        return (index == AppConstants.NEGATIVE_INDEX.getValue()) ? "false" : "true";
    }
}
