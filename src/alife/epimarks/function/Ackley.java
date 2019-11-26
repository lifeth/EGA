package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.real.BinaryToRealVector;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  Ackley</p>
 * <p>Description: The Ackley function</p>
 * @author lifeth
 */
public class Ackley{
	
  private BinaryToRealVector p;

/**
 * Constructor: Creates a Ackley function
 * [-32.768, 32.768] interval
 */
  public Ackley(int BITS_PER_DOUBLE, double min[], double max[]){
	 this.p = new BinaryToRealVector(BITS_PER_DOUBLE, min, max);
  }
  
  public class Classic extends OptimizationFunction<BitArray> {
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply(BitArray x ){
		  
		double[] genome = p.decode(x);
		
	    int n = genome.length;
	    double sum1 = 0.0;
	    double sum2 = 0.0;
	    
	    for( int i=0; i<n; i++ ){
	      sum1 += genome[i]*genome[i];
	      sum2 += Math.cos(2.0*Math.PI*genome[i]);
	    }
	    sum1 /= n;
	    sum2 /= n;

	    return (20.0 + Math.exp(1.0) - 20.0*Math.exp(-0.2*Math.sqrt(sum1)) - Math.exp(sum2));
	  }
	  
  }
  
  public class Extended extends OptimizationFunction<MarkedBitArray> {
	  
	  private Reader reader = new Reader();
		
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply(MarkedBitArray x ){
		
		MarkedBitArray xx = reader.readMarks(x);
		double[] genome = p.decode(new BitArray(xx.toString()));
			
	    int n = genome.length;
	    double sum1 = 0.0;
	    double sum2 = 0.0;
	    
	    for( int i=0; i<n; i++ ){
	      sum1 += genome[i]*genome[i];
	      sum2 += Math.cos(2.0*Math.PI*genome[i]);
	    }
	    sum1 /= n;
	    sum2 /= n;

	    return (20.0 + Math.exp(1.0) - 20.0*Math.exp(-0.2*Math.sqrt(sum1)) - Math.exp(sum2));
	  }
  }

}
