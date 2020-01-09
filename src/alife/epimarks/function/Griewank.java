/**
 * 
 */
package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author lifeth
 *
 */
public class Griewank {
	
	/**
	 * Creates a Griewank function
     * [-600, 600] interval
	 */
	public Griewank() {}
	
	public class Classic extends OptimizationFunction<BitArray> {
		  /**
		   * Evaluate the OptimizationFunction function over the real vector given
		   * @param x Real vector to be evaluated
		   * @return the OptimizationFunction function over the real vector
		   */
		  public Double apply( BitArray x ){
			
			double [] genome =  Utils.decode(x.toString(), -600, 599);
			  
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
	  
	  public class Extended extends OptimizationFunction<MarkedBitArray> {
		  
		  private Reader reader = new Reader();
		  
			/**
			 * 
			 */
		   public Extended() {}
		  
		  /**
		   * Evaluate the OptimizationFunction function over the real vector given
		   * @param x Real vector to be evaluated
		   * @return the OptimizationFunction function over the real vector
		   */
		  public Double apply( MarkedBitArray x ){
			  
			MarkedBitArray xx = reader.readMarks(x);
			double [] genome =  Utils.decode(xx.toString(), -600, 599);
			  
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

}
