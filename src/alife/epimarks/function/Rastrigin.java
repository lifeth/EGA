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
public class Rastrigin extends OptimizationFunction<MarkedBitArray>{
	
	private Reader reader = new Reader();
	public static double MIN = -5.12;
	public static double MAX = 5.11;
	
	/**
	 * Constructor: Creates a Rastrigin function Variables should be in the
	 * [-5.12, 5.12] interval
	 */
	public Rastrigin(){
	}

	/**
	 * Evaluates the Rastrigin function over a real value
	 * 
	 * @param x
	 *            the real value argument of the Rastrigin function
	 * @return the Rastrigin value for the given value
	 */
	public double apply(double x) {
		return (x * x - 10.0 * Math.cos(2.0 * Math.PI * x));
	}	
		
	/**
	 * Evaluate the OptimizationFunction function over the real vector given
	 * 
	 * @param x
	 *            Real vector to be evaluated
	 * @return the OptimizationFunction function over the real vector
	 */
	public Double apply(MarkedBitArray x) {
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);
		
		double [] genome =  Utils.decode(xx.toString(), MIN, MAX);
		
		int n = genome.length;
		double f = 0.0;
		for (int i = 0; i < n; i++) {
			f += apply(genome[i]);
		}
		return (10.0 * n + f);
	}
}