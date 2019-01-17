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
public class Deceptive {

	public class Classic extends OptimizationFunction<BitArray> {

		/**
		 * Return the integer value codified by the bits in a section of the
		 * array
		 * 
		 * @param genes
		 *            Bitarray source
		 * @param start
		 *            Index of the first bit in the section to extract the index
		 * @param length
		 *            Size of the section from which the integer is extracted
		 * @return The integer value codified by the bits in a section of the
		 *         array
		 */
		public int getValue(BitArray genes, int start, int length) {
			int s = 0;
			int b = 1;
			length += start;
			for (int i = start; i < length; i++) {
				if (genes.get(i)) {
					s += b;
				}
				b *= 2;
			}
			return s;
		}

		@Override
		public Double apply(BitArray x) {

			int geneSize = 3;
			double f = 0.0;
			int n = x.size() / geneSize;

			for (int i = 0; i < n; i++) {

				int k = getValue(x, geneSize * i, geneSize);

				switch (k) {
					case 0:f += 28;break;
					case 1:f += 26;break;
					case 2:f += 22;break;
					case 3:f += 0;break;
					case 4:f += 14;break;
					case 5:f += 0;break;
					case 6:f += 0;break;
					case 7:f += 30;break;
				}
			}

			return f;
		}

	}

	public class Extended extends OptimizationFunction<MarkedBitArray> {

		private Reader reader = new Reader();

		/**
		 * Return the integer value codified by the bits in a section of the
		 * array
		 * 
		 * @param genes
		 *            Bitarray source
		 * @param start
		 *            Index of the first bit in the section to extract the index
		 * @param length
		 *            Size of the section from which the integer is extracted
		 * @return The integer value codified by the bits in a section of the
		 *         array
		 */
		public int getValue(MarkedBitArray genes, int start, int length) {
			int s = 0;
			int b = 1;
			length += start;
			for (int i = start; i < length; i++) {
				if (genes.get(i)) {
					s += b;
				}
				b *= 2;
			}
			return s;
		}

		@Override
		public Double apply(MarkedBitArray x) {

			MarkedBitArray xx = reader.readMarks(x);

			int geneSize = 3;
			double f = 0.0;
			int n = xx.size() / geneSize;

			for (int i = 0; i < n; i++) {

				int k = getValue(xx, geneSize * i, geneSize);

				switch (k) {
					case 0:f += 28;break;
					case 1:f += 26;break;
					case 2:f += 22;break;
					case 3:f += 0;break;
					case 4:f += 14;break;
					case 5:f += 0;break;
					case 6:f += 0;break;
					case 7:f += 30;break;
				}
			}

			return f;
		}
	}
}
