/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/*** Class used to calculate the importance of the instances in the model. ***/

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Tiago
 */
public class TestAttributeImportance {

    public static void main(String args[]) throws Exception {
        Instances inst = AppUtil.loadInstances("D:/Mestrado/newdbWeka - Atts finais.arff");
        
        List<Integer> attList = new ArrayList();
        for (int i = 1; i <= inst.numAttributes() - 1; i++) {
            attList.add(i);
        }
        ICombinatoricsVector<Integer> initialSet = Factory.createVector(attList);

        // Create an instance of the subset generator
        Generator<Integer> gen = Factory.createSubSetGenerator(initialSet);
        CostMatrix costMatrix = new CostMatrix(2);
        costMatrix.setCell(0, 0, 0.0);
        costMatrix.setCell(0, 1, 0.87);
        costMatrix.setCell(1, 0, 0.13);
        costMatrix.setCell(1, 1, 0.0);
        List<TestResult> testResults = new ArrayList();        
        int testStop = 0;
        System.out.println("Num atts: " + attList.size());
        for (ICombinatoricsVector<Integer> subSet : gen) {
            if (subSet.getSize() == 0) {
                continue;
            }
            testStop++;

            CostSensitiveClassifier csClassifier = new CostSensitiveClassifier();
            csClassifier.setCostMatrix(costMatrix);
            csClassifier.setClassifier(new SMO());

            //System.out.println(subSet);
            String range = "";
            for (int i = 0; i < subSet.getSize(); i++) {
                range += "," + subSet.getValue(i);
            }
            Instances testInst = AppUtil.keepAttributes(inst, range);
            //System.out.println(testInst.numAttributes());
            csClassifier.buildClassifier(testInst);
            Evaluation eval = new Evaluation(testInst);
            Random rand = new Random(0);
            eval.crossValidateModel(csClassifier, testInst, 10, rand);
            //System.out.println(eval.toSummaryString());
            //System.out.println(eval.toMatrixString());
            testResults.add(new TestResult(getSensitivity(eval, AppConstants.POSITIVE_INDEX.getValue()), getSpecificity(eval, AppConstants.POSITIVE_INDEX.getValue()), getSensitivity(eval, AppConstants.NEGATIVE_INDEX.getValue()), getSpecificity(eval, AppConstants.NEGATIVE_INDEX.getValue()), subSet.getVector()));
            //System.out.println(testResults.get(testResults.size() - 1));
        }
        System.out.println("Number of iterations: " + testStop);
        saveCsvFile(testResults, "testResults.csv");
        AppUtil.saveObject(testResults, "testResults.list");
    }

    private static void saveCsvFile(List<TestResult> resultsList, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (TestResult testResult : resultsList) {
                writer.append(testResult.toCsv() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TestAttributeImportance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    private static double getSensitivity(Evaluation eval, int classIndex) {
        return eval.truePositiveRate(classIndex) / (eval.truePositiveRate(classIndex) + eval.falseNegativeRate(classIndex));
    }

    private static double getSpecificity(Evaluation eval, int classIndex) {
        return eval.trueNegativeRate(classIndex) / (eval.trueNegativeRate(classIndex) + eval.falsePositiveRate(classIndex));
    }
}

class TestResult implements Serializable {

    private double positive_class_sensitivity;
    private double positive_class_specificity;
    private double negative_class_sensitivity;
    private double negative_class_specificity;
    List<Integer> attributes;
    Evaluation resultEval;

    public TestResult(double positive_class_sensitivit, double positive_class_specificity, double negative_class_sensitivity, double negative_class_specificity, List<Integer> attributes) {
        this.positive_class_sensitivity = positive_class_sensitivit;
        this.positive_class_specificity = positive_class_specificity;
        this.negative_class_sensitivity = negative_class_sensitivity;
        this.negative_class_specificity = negative_class_specificity;
        this.attributes = attributes;
    }

    public String toString() {
        return toCsv();
    }

    public String toCsv() {
        String returnString = positive_class_sensitivity + "," + positive_class_specificity + "," + negative_class_sensitivity + "," + negative_class_specificity + ",";
        for (Integer value : attributes) {
            returnString += value + " ";
        }
        return returnString;
    }

    public double getPositive_class_sensitivit() {
        return positive_class_sensitivity;
    }

    public double getPositive_class_specificity() {
        return positive_class_specificity;
    }

    public double getNegative_class_sensitivity() {
        return negative_class_sensitivity;
    }

    public double getNegative_class_specificity() {
        return negative_class_specificity;
    }

    public List<Integer> getAttributes() {
        return attributes;
    }

    public Evaluation getResultEval() {
        return resultEval;
    }
}