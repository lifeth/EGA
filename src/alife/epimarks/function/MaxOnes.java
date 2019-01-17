/**
 * 
 */
package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author lifeth
 *
 */
public class MaxOnes{
	
	public class Classic extends OptimizationFunction<BitArray>{
		
		@Override
		public Double apply(BitArray x) {
			
			double f = 0.0;
			for (int i = 0; i < x.size(); i++) {
				if (x.get(i)) {
					f++;
				}
			}
			return f;
		}
	}
	
	public class Extended extends OptimizationFunction<MarkedBitArray>{
		
		private Reader reader = new Reader();

		@Override
		public Double apply(MarkedBitArray x) {
			
			MarkedBitArray xx = reader.readMarks(x);
			
			double f = 0.0;
			for (int i = 0; i < xx.size(); i++) {
				if (xx.get(i)) {
					f++;
				}
			}
			
			return f;
		}
	}
}
