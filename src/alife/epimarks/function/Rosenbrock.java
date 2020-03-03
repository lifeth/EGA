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
public class Rosenbrock extends OptimizationFunction<MarkedBitArray>{

	 private Reader reader = new Reader();
	 
	/**
	 * Creates a Rosenbrock function
     * [-2.048, 2.048] interval
	 */
	public Rosenbrock() {}
	
	/**
	   * Evaluates the RosenbrockSaddle function over two real values
	   * @param x1 the first real value argument of the RosenbrockSaddle function
	   * @param x2 the second real value argument of the RosenbrockSaddle function
	   * @return the RosenbrockSaddle value for the given values
	   */
	  public double apply( double x1, double x2 ){
	    double y = x1*x1 - x2;
	    return (100.0*y*y + (1.0-x1)*(1.0-x1));
	  }

	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x  ){
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);
		
		double [] genome =  Utils.decode(xx.toString(), -2.048, 2.047);
			  
		int n = genome.length-1;
		    
	    double f = 0.0;
	    for( int i=0; i<n; i++ ){
	      f += apply( genome[i], genome[i+1] );
	    }
	    
	    return f;
	  }
}
