/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/*** Class used to manage files. ***/

import interfaceWithWeka.InterfaceWithWeka;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveType;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

/**
 *
 * @author Tiago
 */

public class AppUtil {
    private static InterfaceWithWeka intWithWeka = new InterfaceWithWeka();
    private static DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
    static {
        DecimalFormatSymbols separator = new DecimalFormatSymbols();
        separator.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(separator);
        df.setMaximumFractionDigits(2);
    }
    
    /*** Method used to load a file. ***/
    public static Instances loadInstances(String filename){
        return intWithWeka.load(filename);
    }
    
    /*** Method used to save a file. ***/
    public static void saveInstances(Instances instances, String filename){
        intWithWeka.save(instances, filename);
    }
    
    /*** Method used to create a new empty file. ***/
    public static void createEmptyFile(File file) throws IOException{
        if(file.exists()){
            file.delete();
        }else{
            if(file.getParentFile() != null && !file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            
            file.createNewFile();
        }
    }
    
    /*** Method used to convert a Double variable to a String. ***/
    public static String formatDouble(double value){
        return df.format(value);
    }
    
    /*** Method used to format the file. ***/
    public static Instances wekaReplaceMissing(Instances inst) throws Exception {
        ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(inst);
        return Filter.useFilter(inst, replaceMissingValues);
    }

    /*** Method used to delete a type of the file. ***/
    public static Instances wekaRemoveType(Instances inst, String type) throws Exception {
        RemoveType removeType = new RemoveType();
        removeType.setAttributeType(new SelectedTag(3, RemoveType.TAGS_ATTRIBUTETYPE));
        removeType.setInputFormat(inst);
        return Filter.useFilter(inst, removeType);
    }
    
    /*** Method used to get the class of the file. ***/
    public static Instances getInstancesOfGivenClass(int classValue, Instances inputInstances) {
        return intWithWeka.getInstancesOfGivenClass(classValue, inputInstances);
    }
    
    /*** Method used to convert CSV file to ARFF file. ***/
    public static Instances convertCSVtoARFF(String csvFilename, String arffFilename) {
        try {
            File origin = new File(csvFilename);
            CSVLoader csvLoader = new CSVLoader();
            csvLoader.setSource(origin);
            Instances inst = csvLoader.getDataSet();
            System.out.println(inst.numAttributes());
            System.out.println(inst.numInstances());

            csvLoader.setSource(origin);
            csvLoader.setDateAttributes("2,108");
            csvLoader.setNominalAttributes("112-115");
            csvLoader.setDateFormat("dd/MM/yy");
            System.out.println(csvLoader.getDateFormat());
            inst = csvLoader.getDataSet();
            inst.setClassIndex(61);
            inst = AppUtil.wekaRemoveType(inst, "date");
            AppUtil.saveInstances(inst, arffFilename);
            return inst;
        } catch (Exception ex) {
            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    /*** Method used to save the modified file. ***/
    public static void saveObject(Object obj, String filename){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(obj);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*** Method used to format the file. ***/
    public static Instances keepAttributes(Instances inst, String range) throws Exception {
        Remove remove = new Remove();
        remove.setAttributeIndices(range + "," + inst.numAttributes());
        remove.setInvertSelection(true);
        remove.setInputFormat(inst);
        return Filter.useFilter(inst, remove);
    }
    
    /*** Method used to change the size of the Set in the file. ***/
    public static Instances resizeSet(Instances inst, double percentage){
        return intWithWeka.resizeSet(inst, percentage);
    }
}
