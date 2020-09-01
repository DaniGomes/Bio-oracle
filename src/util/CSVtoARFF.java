/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/*** Class used to convert a CSV file to a PDF file. ***/

/**
 *
 * @author Tiago
 */
public class CSVtoARFF {
        
    public static void main(String args[]){
        String csv  = "D:/Mestrado/db comma.csv";
        String arff = "D:/Mestrado/newdbWeka.arff";
        AppUtil.convertCSVtoARFF(csv, arff);
    }
}
