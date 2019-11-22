/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.types.MarkedBitArray;

/**
 * @author lifeth
 *
 */
public class Rules {

	/**
	 * Return the Rule associated to the binary parameter
	 * 
	 * @param binary
	 *            string representation of an operation.
	 * @return
	 * @throws Exception
	 */
	public IReadingRule<MarkedBitArray> getRule(String binary) throws Exception {
		return operation(Integer.parseInt(binary, 2));
	}

	/**
	 * @param rule
	 *            The rule number related to the reading operation that will be
	 *            applied on the genome.
	 * @return ReadingRule for the chromosome
	 * @throws Exception
	 */
	public IReadingRule<MarkedBitArray> operation(int rule) throws Exception {

		switch (rule) {
		
		//OPERATIONS
		//0 01 transpose
		//0 00 circular shift
		//0 10 set to
		//0 11 do nothing
		//1 01 add 1 or
		//1 00 divide by 2
		//1 10 multiply by 0 and
		//1 11 subtract 1 xor

		case 0:// 000
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightShift(i[0], i[1]);
					return x;
				}
			};

		case 1:// 001
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightRotate(i[0], i[1]);
					return x;
				}
			};

		case 2:// 010
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightSetTo(i[0], i[1]);
					return x;
				}
			};

		case 3:// 011
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					return x;
				}
			};
			
		case 4:// 100
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.divideByTwo(i[0], i[1]);
					return x;
				}
			};
			
		case 5:// 101
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.addOne(i[0], i[1]);
					return x;
				}
			};
			
		case 6:// 110
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.multiplyByZero(i[0], i[1]);
					return x;
				}
			};
			
		case 7:// 111
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.subtractOne(i[0], i[1]);
					return x;
				}
			};

		default:
			throw new Exception("The rule number " + rule + " does not exist.");
		}
	}

	public interface IReadingRule<T> {

		/**
		 * @param x The genome
		 * @param i The positions to be taken into account to apply the operations
		 * @return
		 */
		public T apply(T x, int... i);
	}

}
