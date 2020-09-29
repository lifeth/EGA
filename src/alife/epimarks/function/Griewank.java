/**
 * 
 */
package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * @author lifeth
 *
 */
public class Griewank extends OptimizationFunction<MarkedBitArray>{
	
	private Reader reader = new Reader();
	public static double MIN = -600;
	public static double MAX = 599;

	/**
	 * Creates a Griewank function
     * [-600, 600] interval
	 */
	public Griewank() {}
	
		  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x ){
		  
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);

		double [] genome =  Utils.decode(xx.toString(), MIN, MAX);
		  
	    int n = genome.length;
	    double sum =  0;
	    double prod = 1;
	    
	    for( int i=0; i<n; i++ ){
	    	sum += Math.pow(genome[i], 2)/4000;
	    	prod *= Math.cos(genome[i]/Math.sqrt(i+1));
	    }
	    		    
	    return sum - prod + 1; 
	  }
  }