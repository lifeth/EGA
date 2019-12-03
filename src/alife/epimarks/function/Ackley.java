package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.search.space.Space;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  Ackley</p>
 * <p>Description: The Ackley function</p>
 * @author lifeth
 */
public class Ackley{

/**
 * Constructor: Creates a Ackley function
 * [-32.768, 32.768] interval
 */
  public Ackley(){
  }
  
  public class Classic extends OptimizationFunction<BitArray> {
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply(BitArray x ){
		  
		double [] genome =  Utils.binaryToDouble(x.toString());
		
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
	  protected Space<double[]> space;
	  
		/**
		 * 
		 */
		public Extended(Space<double[]> space) {
			this.space = space;
		}
		
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply(MarkedBitArray x ){
		
		MarkedBitArray xx = reader.readMarks(x);
		double [] genome =  Utils.binaryToDouble(xx.toString());
		genome = this.space.repair(genome);
			
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
	    //20 + exp(1) - (20 * exp(-0.2 * sqrt( ninverse * sum1))) - exp( ninverse * sum2);
	  }
  }

}
