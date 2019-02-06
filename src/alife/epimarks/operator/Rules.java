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

		case 0:// 00
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightShift(i[0], i[1]);
					return x;
				}
			};

		case 1:// 01
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightRotate(i[0], i[1]);
					return x;
				}
			};

		case 2:// 10
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightSetTo(i[0], i[1]);
					return x;
				}
			};

		case 3:// 11
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					return x;
				}
			};

		default:
			throw new Exception("The rule number " + rule + " does not exist.");
		}
	}

	public interface IReadingRule<T> {

		public T apply(T x, int... i);
	}

}
