/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.tests.MarkingVariation_1_1;
import alife.epimarks.types.MarkedBitArray;
import unalcol.random.Random;
import unalcol.random.util.RandBool;
import unalcol.search.solution.Solution;

/**
 * @author lifeth
 *
 */
public class Marking extends MarkingVariation_1_1<MarkedBitArray> {

	/**
	 * Probability of marking one single gene
	 */
	protected double markingRate = 0.0;
    protected int tagsCombination = 128;
	protected double withEpiTagsStability[][] = new double[2][this.tagsCombination];
	protected double withoutEpiTagsStability[][] = new double[2][this.tagsCombination];

	/**
	 *  Marking rate = 0.02 
	 * @param markingRate
	 *            Probability of marking one single bit.
	 */
	public Marking(double markingRate) {
		this.markingRate = markingRate;
	}

	/**
	 * Marking rate = 1.0 / chromosome.size() 
	 * Add a tag (l-bits) 				0.007	0.35 
	 * Remove a tag (l-bits) 			0.007	0.35 
	 * Modify a bit of a tag (0 or 1) 	0.006 	0.3
	 */
	public Marking() {
	}

	@Override
	public MarkedBitArray apply(MarkedBitArray x) {

		int length = x.getEpiTagsLength(); // max tags length in bits

		RandBool g = new RandBool(1.0 - ((markingRate == 0.0) ? 1.0 / x.size() : markingRate));
		RandBool arm = new RandBool(0.3);// add and remove or modify
		RandBool ar = new RandBool();// add or remove
		RandBool tagMutattion = new RandBool(1.0 - (1.0 / length));// when modifying tags

		for (int col = 0; col < x.size(); col++) {

			if (g.next()) {

				if (arm.next()) {

					if (ar.next()) {
						// Add a new tag (all l-bits)
						if (!x.isMarked(col)) {
							for (int i = 0; i < length; i++) {
								x.getEpiTags()[i][col] = Random.nextBool() ? '1' : '0';
							}
						}
					} else {
						// Remove an existing tag (all l-bits)
						if (x.isMarked(col)) {
							for (int i = 0; i < length; i++) {
								x.getEpiTags()[i][col] = Character.MIN_VALUE;
							}
						}
					}

				} else {

					// Modify an existing tag
					if (x.isMarked(col)) {
						for (int i = 0; i < length; i++) {
							if (tagMutattion.next()) {
								x.getEpiTags()[i][col] ^= 1;
									// x.getEpiTags()[i][col] = Random.nextBool() ? '1' : '0';
							}
						}
					}
				}
			}
		}

		return x;
	}

	@Override
	public void computeEpiTagsStability(Solution<MarkedBitArray>[] pop) {
	}



	public static void main(String[] args) throws Exception {
		Marking m = new Marking(0.02);
		String e = "0<<   0000000>> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   0000000>> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<0000000>> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >>";
		MarkedBitArray x = new MarkedBitArray(e, 7);

		System.out.println(x.toStringTags());
		System.out.println(x);
		x = m.apply(x);
		System.out.println(x.toStringTags());

		// String op[] = { "00", "01", "10", "11"};
		// String size[] = { "000", "001", "010", "011", "100","101", "110",
		// "111"};
		//
		// for (int i = 0; i < op.length; i++) {
		// for (int j = 0; j < size.length; j++) {
		//
		// }
		// }
		
		// for (int i = 0; i < 32; i++) {
		// System.out.println("WithTag: "+withEpiTagsStability[0][i] + "; AVG
		// withTag: "+this.getAvgTag(withEpiTagsStability[0][i],
		// withEpiTagsStability[1][i]) +
		// "; WithoutTag: "+withoutEpiTagsStability[0][i] + "; AVG withoutTag:
		// "+this.getAvgTag(withoutEpiTagsStability[0][i],
		// withoutEpiTagsStability[1][i]));
		// }
	}
}