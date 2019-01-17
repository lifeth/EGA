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
	 *            string representation of a set o tags
	 * @return
	 * @throws Exception
	 */
	public IReadingRule<MarkedBitArray> getRule(String binary) throws Exception {
		return valueOf(getValue(binary));
	}

	/**
	 * Return the integer value codified by the binary
	 * 
	 * @param binary
	 *            string representation of a set o tags
	 * 
	 * @return The integer value codified by the binary
	 */
	public int getValue(String binary) {

		return Integer.parseInt(binary, 2);
	}

	/**
	 * @param rule
	 *            The rule number related to the reading rule that will be
	 *            applied on the genome.
	 * @return ReadingRule for the chromosome
	 * @throws Exception
	 */
	public IReadingRule<MarkedBitArray> valueOf(int rule) throws Exception {

		switch (rule) {

		case 0:// 000
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.leftRotate(i[0]);
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

		// marca que crece, prioridad a algunas marcas, (automata finito
		// determinista) Complemento a dos
		case 2:// 010
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.leftShift();
					return x;
				}
			};

		case 3:// 011
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightShift();
					return x;
				}
			};

		case 4:// 100
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.rightSetTo(i[0], i[1]);
					return x;
				}
			};

		case 5:// 101
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.leftSetTo(i[0]);
					return x;
				}
			};

		case 6:// 110
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.transpose(i[0]);
					return x;
				}
			};

		case 7:// 111
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.replicate(i[0], i[1]);
					return x;
				}
			};

		case 8:// conflict resolution -1
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.not(i[0], i[1]);
					return x;
				}
			};

		case 9:// conflict resolution -2
			return new IReadingRule<MarkedBitArray>() {

				@Override
				public MarkedBitArray apply(MarkedBitArray x, int... i) {
					x.interleave();
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
