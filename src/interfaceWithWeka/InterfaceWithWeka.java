package interfaceWithWeka;

import java.io.*;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.AttributeSelection;
import weka.filters.supervised.attribute.Discretize;
import weka.filters.Filter;
import weka.core.*;
import weka.filters.supervised.instance.Resample;
import java.util.Enumeration;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.filters.unsupervised.instance.Randomize;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class InterfaceWithWeka {

  /**
   * loads the given ARFF file and sets the class attribute as the last
   * attribute.
   *
   * @param filename    the file to load
   */
  public Instances load(String filename) {
    Instances result = null;
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(filename));
      result = new Instances(reader);
      result.setClassIndex(result.numAttributes() - 1);
      reader.close();
    } catch(IOException e) {
      System.out.println( e.getMessage() );
    }
    return result;
  } // load

  /**
   * saves the data to the specified file
   *
   * @param data        the data to save to a file
   * @param filename    the file to save the data to
   */
  public void save(Instances data, String filename) {
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(filename));
      writer.write(data.toString());
      writer.newLine();
      writer.flush();
      writer.close();
    } catch(IOException e) {
      System.out.println( e.getMessage() );
    }
  } // save

  public Instances getInstancesOfGivenClass( int classValue, Instances inputInstances ) {
    RemoveWithValues filter;
    Instances filteredInstances = null;

    try {
      // setup filter
      filter = new RemoveWithValues();
      filter.setAttributeIndex( "last" );
      filter.setInvertSelection( true );
      filter.setMatchMissingValues( false );
      filter.setModifyHeader( false );
      filter.setNominalIndices("" + (classValue+1));
      filter.setSplitPoint( 0.0 );
      filter.setInputFormat(inputInstances);
      // apply filter to get instances of given class
      filteredInstances = Filter.useFilter( inputInstances, filter );
    } catch(Exception e) {
      e.printStackTrace();
    }

    return filteredInstances;
  } // getInstancesOfGivenClass

  public Instances resizeSet( Instances setToResize, double percentOfSet ) {
    Instances resampledInstances = null;
    Resample filter;
    try {
      // setup filter
      filter = new Resample();
      filter.setBiasToUniformClass( 0.0 );
      filter.setSampleSizePercent( percentOfSet );
      filter.setInputFormat(setToResize);
      // apply filter to get subset of instances
      resampledInstances = Filter.useFilter( setToResize, filter );
    } catch(Exception e) {
      e.printStackTrace();
    }

    return resampledInstances;
  } // resizeSet

  public void appendSecondToFirst(Instances set1, Instances set2) {
    Enumeration e = set2.enumerateInstances();
    Instance i;

    while ( e.hasMoreElements() ) {
      i = (Instance) e.nextElement();
      set1.add( i );
    } // while
  } // appendSecondToFirst

  public Instances discretize( Instances inputInstances ) {
    Discretize filter;
    Instances filteredInstances = null;

    try {
      // setup filter
      filter = new Discretize();
      filter.setInputFormat(inputInstances);

      // apply filter
      filteredInstances= Filter.useFilter(inputInstances, filter);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return filteredInstances;
  } // discretize

  public Instances selectFeatures( Instances inputInstances ) {
    AttributeSelection attribSelec;
    CfsSubsetEval attribEval;
    GreedyStepwise searchMet;
    Instances outputInstances = null;

    try {
      attribSelec = new AttributeSelection();
      attribEval = new CfsSubsetEval();
      searchMet = new GreedyStepwise();

      attribSelec.setEvaluator( attribEval );
      attribSelec.setSearch( searchMet );
      attribSelec.SelectAttributes( inputInstances );
      outputInstances = attribSelec.reduceDimensionality( inputInstances );
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return outputInstances;
  } // selectFeatures

  public Instances shakeInstances( Instances instances ) {
    Randomize filter;
    Instances filteredInstances = null;

    try {
      // setup filter
      filter = new Randomize();
      filter.setRandomSeed( 42 );
      filter.setInputFormat( instances );

      // apply filter to get a new set with random order.
      filteredInstances = Filter.useFilter( instances, filter );
    } catch(Exception e) {
      e.printStackTrace();
    }

    return filteredInstances;
  } // shakeInstances

} // class InterfaceWithWeka
