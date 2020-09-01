/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/*** Class used to define important constants and manage them. ***/

/**
 *
 * @author Tiago
 */
public enum AppConstants {
    POSITIVE_INDEX(0), NEGATIVE_INDEX(1), MAX_ATT_VALUE(1000), NEW_POSITIVE_SAMPLE("Get new POSITIVE sample instance"), NEW_NEGATIVE_SAMPLE("Get new NEGATIVE sample instance");
    private final int value;
    private final String sValue;
    
    AppConstants(int value){
        this.value = value;
        this.sValue = String.valueOf(value);
    }
    
    AppConstants(String value){
        this.sValue = value;
        this.value = 0;
    }

    public String getStringValue(){
        return sValue;
    }
    public int getValue(){
        return value;
    }
}
