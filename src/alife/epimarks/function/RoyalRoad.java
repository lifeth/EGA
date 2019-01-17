package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.*;
import unalcol.types.collection.bitarray.BitArray;

/**
 * 
 * The OptimizationFunction of a binary array is the RoyalRoad function as
 * proposed by Mickalewicks
 * 
 * @author lifeth
 *
 */
public class RoyalRoad{
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
	
	public class Classic extends OptimizationFunction<BitArray>{
     
		@Override
		public Double apply(BitArray x) {
			double f = 0.0;
			int n = x.size() / pathLength;
			for (int i = 0; i < n; i++) {
				int start = pathLength * i;
				int end = start + pathLength;
				while (start < end && x.get(start)) {
					start++;
				}
				if (start == end) {
					f += pathLength;
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
}
