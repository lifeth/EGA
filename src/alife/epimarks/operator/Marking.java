/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.tests.MarkingVariation_1_1;
import alife.epimarks.types.MarkedBitArray;
import unalcol.random.Random;
import unalcol.random.util.RandBool;
import unalcol.search.Goal;
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
	 * Add a tag (5-bits) 				0.007	0.35 
	 * Remove a tag (5-bits) 			0.007	0.35 
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
		//RandBool bitMutattion = new RandBool(1.0 - (1.0 / length));// when modifying tags

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
							//if (!this.isStable(x, col)) {
								for (int i = 0; i < length; i++) {
									x.getEpiTags()[i][col] = Character.MIN_VALUE;
								}
							//}
						}
					}

				} else {

					// Modify an existing tag (before: a single bit, now??: all bits??)
					if (x.isMarked(col)) {
						//if (!this.isStable(x, col)) {
							for (int i = 0; i < length; i++) {
								//if (Random.nextBool()) {
									x.getEpiTags()[i][col] ^= 1;
									// x.getEpiTags()[i][col] = Random.nextBool() ? '1' : '0';
							//	}
							}
						//}
					}
				}
			}
		}

		return x;
	}

	@Override
	public void computeEpiTagsStability(Solution<MarkedBitArray>[] pop) {

		// # tags... schema method
		for (int i = 0; i < this.tagsCombination; i++) {
			this.withEpiTagsStability[0][i] = 0;
			this.withEpiTagsStability[1][i] = 0;

			this.withoutEpiTagsStability[0][i] = 0;
			this.withoutEpiTagsStability[1][i] = 0;
		}

		for (int i = 0; i < pop.length; i++) {

			MarkedBitArray x = pop[i].object();

			boolean[] present = new boolean[this.tagsCombination];

			for (int j = 0; j < x.size(); j++) {

				if (x.isMarked(j)) {
					int pos = Integer.parseInt(String.valueOf(x.getTagsPerAllele(j)), 2);
					if (!present[pos]) {
						present[pos] = true;
					}
				}
			}

			for (int k = 0; k < present.length; k++) {
				if (present[k]) {
					this.withEpiTagsStability[0][k] += 1;
					this.withEpiTagsStability[1][k] += (double) pop[i].info(Goal.class.getName());
				} else {
					this.withoutEpiTagsStability[0][k] += 1;
					this.withoutEpiTagsStability[1][k] += (double) pop[i].info(Goal.class.getName());
				}
			}
		}
	}

	/**
	 * Returns a boolean value that defines if the tag in a specified position
	 * is stable. This is defined by taking the AVG of individuals from the
	 * previous population that do not have the tag versus the AVG of
	 * individuals that have it in any position. Then, if the AVG without the
	 * tag is greater than the AVG with the tag, the survival probability of the
	 * tag is returned. (Schema Theorem)
	 * 
	 * @param x
	 *            individual
	 * @param col
	 *            position of the l-bits tag.
	 * @return true if the tag in the specified position is stable, false
	 *         otherwise.
	 */
	public boolean isStable(MarkedBitArray x, int col) {

		int pos = Integer.parseInt(String.valueOf(x.getTagsPerAllele(col)), 2);
		double countWith = this.withEpiTagsStability[0][pos];
		double sumWith = this.withEpiTagsStability[1][pos];

		double countWithout = this.withoutEpiTagsStability[0][pos];
		double sumWithout = this.withoutEpiTagsStability[1][pos];

		double avgWith = this.getAvgTag(countWith, sumWith);
		double avgWithout = this.getAvgTag(countWithout, sumWithout);
		//double difference = avgWith-avgWithout;
		//double falseP = 0;

	/*	if(difference<=0){
			falseP = 1;
		}else if(difference > 0 && difference <=10){
			falseP = 0.5;
		}else if(difference > 10 && difference <=20){
			falseP = 0.4;
		}else if(difference > 20 && difference <=30){
			falseP = 0.3;
		}else if(difference > 30 && difference <=40){
			falseP = 0.2;
		}else if(difference > 40 && difference <=50){
			falseP = 0.1;
		}else if(difference > 50){
			falseP = 0;
		}*/

		// false probability
		return Random.nextBool(avgWithout >= avgWith ? 0.9 : 0.1);
	}

	public double getAvgTag(double count, double sum) {
		return count == 0 ? 0 : (sum /= count);
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