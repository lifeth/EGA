package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * <p>Title:  Ackley</p>
 * <p>Description: The Ackley function</p>
 * @author lifeth
 */
public class Ackley extends OptimizationFunction<MarkedBitArray>{

	private Reader reader = new Reader();

	/**
	 * Constructor: Creates a Ackley function
	 * [-32.768, 32.768] interval
	 */
	  public Ackley(){
	  }
		
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply(MarkedBitArray x ){
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);

		double [] genome =  Utils.decode(xx.toString(), -32.768, 32.767);
			
	    int n = genome.length;
	    double sum1 = 0.0;
	    double sum2 = 0.0;
	    
	    for( int i=0; i<n; i++ ){
	      sum1 += genome[i]*genome[i];
	      sum2 += Math.cos(2.0*Math.PI*genome[i]);
	    }
	    
	    sum1 /= n;
	    sum2 /= n;

	    //return (20.0 + Math.exp(1.0) - 20.0*Math.exp(-0.2*Math.sqrt(sum1)) - Math.exp(sum2));
	    return  (- 20.0*Math.exp(-0.2*Math.sqrt(sum1))) - Math.exp(sum2) + 20.0 + Math.exp(1.0);
	  }
}
