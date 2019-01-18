/**
 * 
 */
package alife.epimarks.logic;

import alife.epimarks.operator.Marking;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.search.Goal;
import unalcol.search.solution.Solution;

/**
 * This class is intended to perform bit operations over the chromosome (or
 * solution) associated to a specific cell.
 * 
 * @author lifeth
 *
 */
public class PerformeBitOperations {

	public void markingProcess(Solution<MarkedBitArray> solution) {
		Marking marking = new Marking();
		// TODO vamos marcando en la mitad si no se recibe eUNITS durante 1000
		// iteraciones
		// se debe definir que marcas son buenas y cuales malas para castigar
		// cuando no se recibe eUNITS
		int i = 1000;
		while (i > 0) {
			solution.set(marking.apply(solution.object()));
			System.out.println(solution.object().toStringTags() + ": " + solution.info(Goal.class.getName()));
			i--;
		}
	}

	public String[] readingProcess(Solution<MarkedBitArray> solution) {
		Reader reader = new Reader(); // Applies rules during lecture.

		// Interpreter of the chromosome with/without tags added, active genes
		// only are read and returned with no tags.
		MarkedBitArray x = reader.readMarks(solution.object());

		// TODO x contains the final bits to build the molecules (transporters
		// and receptors). the idea is to cut the string.
		return buildCodes(x);
	}

	public String[] buildCodes(MarkedBitArray x) {

		// TODO pending, not ready
		int length = 4;
		int n = x.size() / length;

		String sequence = "";

		for (int i = 0; i < n; i++) {
			int count = getValue(x, length * i, length);
			sequence += String.format("%04d", Integer.parseInt(Integer.toBinaryString(count)));
		}

		return new String[] { "1111", "1110", sequence };

	}

	public int getValue(MarkedBitArray genes, int start, int length) {
		int count = 0;
		length += start;
		for (int i = start; i < length; i++) {
			if (genes.get(i)) {
				count++;
			}
		}
		return count;
	}

}
