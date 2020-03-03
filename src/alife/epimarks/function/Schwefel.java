package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * <p>Title:  Schwefel [-500,500]^n </p>
 * <p>Description: The Schwefel function</p>
 * @author lifeth
 */
public class Schwefel extends OptimizationFunction<MarkedBitArray>{

	  private Reader reader = new Reader();
	  
	/**
	 * Constructor: Creates a Schwefel function
	 * [-500, 500] interval
	 */
	  public Schwefel(){}

	  /**
	   * Evaluates the Schwefel function over a real value
	   * @param x the real value argument of the Schwefel function
	   * @return the Schwefel value for the given value
	   */
	  public double apply( double x ){
	    return ( -x * Math.sin(Math.sqrt(Math.abs(x))) );
	    //418.9829 * n - (sum(x .* sin(sqrt(abs(x))), 2));
	  }
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x ){
		  
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);
		  
		double [] genome =  Utils.decode(xx.toString(), -500, 499);
		  
	    int n = genome.length;
	    double f = 0.0;
	    
	    for( int i=0; i<n; i++ ){
	      f += apply(genome[i]);
	    }
			 
	    return (418.9829101*n + f);
	  }
}
