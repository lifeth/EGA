/**
 * 
 */
package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * @author lifeth
 *
 */
public class MaxOnes extends OptimizationFunction<MarkedBitArray>{
	
	private Reader reader = new Reader();
	
	@Override
	public Double apply(MarkedBitArray x) {
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);
		
		double f = 0.0;
		for (int i = 0; i < xx.size(); i++) {
			if (xx.get(i)) {
				f++;
			}
		}
		
		return f;
	}
}
