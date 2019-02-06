/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.tests.Variation_One_One;
import alife.epimarks.types.MarkedBitArray;
import unalcol.random.Random;
import unalcol.random.util.RandBool;

/**
 * @author lifeth
 *
 */
public class Marking extends Variation_One_One<MarkedBitArray> {

	/**
	 * Probability of marking one single gene
	 */
	protected double markingRate = 0.0;

	/**
	 * @param markingRate
	 *            Probability of marking one single bit.
	 */
	public Marking(double markingRate) {
		this.markingRate = markingRate;
	}

	/**
	 * No Marking 0.98 Add a tag (5-bits) 0.007 0.35 Remove a tag (5-bits) 0.007
	 * 0.35 Modify a bit of a tag (0 or 1) 0.006 0.3
	 */
	public Marking() {
	}

	@Override
	public MarkedBitArray apply(MarkedBitArray x) {

		int length = x.getTagsLength(); // max tags length in bits

		RandBool g = new RandBool(1.0 - ((markingRate == 0.0) ? 1.0 / x.size() : markingRate));
		RandBool arm = new RandBool(0.3);// add and remove or modify
		RandBool ar = new RandBool();// add or remove
		RandBool bitMutattion = new RandBool(1.0 - (1.0 / length));// when modifying tags

		for (int col = 0; col < x.size(); col++) {

			if (g.next()) {

				if (arm.next()) {

					if (ar.next()) {
						// Add a new tag (all 5-bits)
						if (!x.isMarked(col)) {
							for (int i = 0; i < length; i++) {
								x.getTags()[i][col] = Random.nextBool() ? '1' : '0';
							}
						}
					} else {
						// Remove an existing tag (all 5-bits)
						if (x.isMarked(col)) {
							for (int i = 0; i < length; i++) {
								x.getTags()[i][col] = Character.MIN_VALUE;
							}
						}
					}

				} else {

					// Modify an existing tag (a single bit)
					if (x.isMarked(col)) {

						for (int i = 0; i < length; i++) {
							if (bitMutattion.next()) {
								x.getTags()[i][col] ^= 1;
							}
						}
					}
				}
			}
		}

		return x;
	}

	/**
	 * Flips a bit in the given epigenome
	 * 
	 * @param x
	 *            genome to be modified
	 * @return Number of mutated bits
	 */
	@Override
	public MarkedBitArray applyMutationOnTags(MarkedBitArray x) {
		try {
			RandBool g = new RandBool(1.0 - (1.0 / x.getTagsLength()));

			for (int j = 0; j < x.size(); j++) {
				if (x.isMarked(0)) {
					//int index = (int) (x.getTagsLength() * Math.random());
					//x.getTags()[index][j] ^= 1;
					for (int i = 0; i < x.getTagsLength(); i++) {
						if (g.next()) {
							x.getTags()[i][j] ^= 1;
						}
					}
				}
			}
			return x;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[Mutation on Tags]" + e.getMessage());
		}
		return null;
	}

	public MarkedBitArray apply2(MarkedBitArray x) {

		int length = x.getTagsLength(); // max tags length in bits

		RandBool g = new RandBool(1.0 - ((markingRate == 0.0) ? 1.0 / x.size() : markingRate));

		for (int col = 0; col < x.size(); col++) {

			if (g.next()) {

				if (Random.nextBool()) {
					// Add a new tag (all 5-bits)
					if (!x.isMarked(col)) {
						for (int i = 0; i < length; i++) {
							x.getTags()[i][col] = Random.nextBool() ? '1' : '0';
						}
					}

				} else {
					if (Random.nextBool(0.4)) {
						// Modify an existing tag (a single bit)
						if (x.isMarked(col)) {
							// int index = (int) (length * Math.random());

							for (int i = 0; i < length; i++) {
								if (Random.nextBool()) {
									x.getTags()[i][col] ^= 1;
								}
							}
						}

					} else {
						// Delete an existing tag (all 5-bits)
						if (x.isMarked(col)) {
							for (int i = 0; i < length; i++) {
								x.getTags()[i][col] = Character.MIN_VALUE;
							}
						}
					}
				}
			}
		}

		return x;
	}

	public static void main(String[] args) throws Exception {
		Marking m = new Marking(0.03);
		String e = "0<<   00000>> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   00000>> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<00000   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >>";
		MarkedBitArray x = new MarkedBitArray(e, 5);

		System.out.println(x.toStringTags());
		System.out.println(x);
		x = m.apply(x);
		System.out.println(x.toStringTags());
		
		x = m.applyMutationOnTags(x);
		System.out.println(x.toStringTags());
	}
}