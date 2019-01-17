package alife.epimarks.operator;

import unalcol.random.util.*;
import unalcol.search.variation.ParameterizedObject;
import unalcol.search.variation.Variation_1_1;
import alife.epimarks.types.MarkedBitArray;
import unalcol.clone.*;


public class BitMutation extends Variation_1_1<MarkedBitArray> implements ParameterizedObject<Double> {
  /**
   * Probability of mutating one single bit
   */
  protected double bitMutationRate = 0.0;

  /**
   * Constructor: Creates a mutation with a mutation probability depending on the size of the genome
   */
  public BitMutation() {}

  /**
   * Constructor: Creates a mutation with the given mutation rate
   * @param bitMutationRate Probability of mutating each single bit
   */
  public BitMutation(double bitMutationRate) {
    this.bitMutationRate = bitMutationRate;
  }

  /**
   * Flips a bit in the given genome
   * @param gen Genome to be modified
   * @return Number of mutated bits
   */
  @Override
  public MarkedBitArray apply(MarkedBitArray gen) {
    try{
	      MarkedBitArray genome = (MarkedBitArray) Clone.create(gen);
	      double rate = 1.0 - ((bitMutationRate == 0.0)?1.0/genome.size():bitMutationRate);
	      RandBool g = new RandBool(rate);
	     
	      for (int i = 0; i < genome.size(); i++) {
	        if (g.next()) {
	          genome.not(i);
	        }
	      }
	      return genome;
    }catch( Exception e ){ 
        e.printStackTrace();
        System.err.println("[Mutation]"+e.getMessage()); }
    return null;
  }

  @Override
  public void setParameters(Double parameters) {
	bitMutationRate = parameters;
  }

  @Override
  public Double getParameters() {
	return bitMutationRate;
  }
}

