/**
 * 
 */
package alife.epimarks.function;

import java.util.Arrays;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.real.BinaryToRealVector;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.real.array.DoubleArray;

/**
 * @author lifeth
 *
 */
public class Rastrigin {

	int DIM = 10;
	int BITS_PER_DOUBLE = 16;
	double[] min = DoubleArray.create(DIM, -5.12);
	double[] max = DoubleArray.create(DIM, 5.12);
	BinaryToRealVector p = new BinaryToRealVector(BITS_PER_DOUBLE, min, max);

	/**
	 * Constructor: Creates a Rastrigin function Variables should be in the
	 * [-5.12, 5.12] interval
	 */
	public Rastrigin() {
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
			
			double[] genome = p.decode(x);
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
			double[] genome = p.decode(new BitArray(xx.toString()));
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
