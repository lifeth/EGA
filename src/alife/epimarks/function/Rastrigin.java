/**
 * 
 */
package alife.epimarks.function;

import java.util.Arrays;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author lifeth
 *
 */
public class Rastrigin {
	
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
	public static double apply(double x) {
		return (x * x - 10.0 * Math.cos(2.0 * Math.PI * x));
	}

	public class Classic extends OptimizationFunction<BitArray> {

		/**
		 * Evaluate the OptimizationFunction function over the real vector given
		 * 
		 * @param x
		 *            Real vector to be evaluated
		 * @return the OptimizationFunction function over the real vector
		 */
		public Double apply(BitArray x) {
			
			double [] genome =  Utils.decode(x.toString(), -5.12, 5.11);
		    //System.out.println(Arrays.toString(genome));
			int n = genome.length;
			double f = 0.0;
			for (int i = 0; i < n; i++) {
				f += Rastrigin.apply(genome[i]);
			}
			return (10.0 * n + f);
		}
	}

	public class Extended extends OptimizationFunction<MarkedBitArray> {

		private Reader reader = new Reader();
		
		/**
		 * Evaluate the OptimizationFunction function over the real vector given
		 * 
		 * @param x
		 *            Real vector to be evaluated
		 * @return the OptimizationFunction function over the real vector
		 */
		public Double apply(MarkedBitArray x) {
			
			MarkedBitArray xx = reader.readMarks(x);
			double [] genome =  Utils.decode(xx.toString(), -5.12, 5.11);
			
			//System.out.println(Arrays.toString(genome));
			int n = genome.length;
			double f = 0.0;
			for (int i = 0; i < n; i++) {
				f += Rastrigin.apply(genome[i]);
			}
			return (10.0 * n + f);
		}
	}
}
