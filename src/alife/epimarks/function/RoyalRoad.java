package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * 
 * The OptimizationFunction of a binary array is the RoyalRoad function as
 * proposed by Mickalewicks
 * 
 * @author lifeth
 *
 */
public class RoyalRoad extends OptimizationFunction<MarkedBitArray>{
	
	private Reader reader = new Reader();
	
	/**
	 * The royal road path length
	 */
	protected int pathLength = 8;
	
	/**
	 * 
	 */
	public RoyalRoad() {
	}

	/**
	 * Constructor: Create a royal road OptimizationFunction function with the
	 * path given
	 * 
	 * @param pathLength
	 *            The royal road path length
	 */
	public RoyalRoad(int pathLength) {
		this.pathLength = pathLength;
	}	

	@Override
	public Double apply(MarkedBitArray x) {
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);

		double f = 0.0;
		int n = xx.size() / pathLength;
		
		for (int i = 0; i < n; i++) {
			int start = pathLength * i;
			int end = start + pathLength;
			while (start < end && xx.get(start)) {
				start++;
			}
			if (start == end) {
				f += pathLength;
			}
		}
		return f;
	}
}
