/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.types.MarkedBitArray;
import unalcol.random.Random;
import unalcol.random.util.RandBool;
import unalcol.search.variation.Variation_1_1;

/**
 * @author lifeth
 *
 */
public class Marking extends Variation_1_1<MarkedBitArray> {

	/**
	 * Probability of marking one single gene
	 */
	protected double markingRate = 0.0;

	/**
	 * @param markingRate
	 */
	public Marking(double markingRate) {
		this.markingRate = markingRate;
	}
	
	/**
	 * 
	 */
	public Marking() {
	}

	@Override
	public MarkedBitArray apply(MarkedBitArray x) {

		int length = x.getTagsLength(); // max tags length in bits

		RandBool g = new RandBool((markingRate == 0.0)? 1.0 - (1.0/x.size()):markingRate);

		for (int i = 0; i < x.size(); i++) {
			
			if (g.next()) {

				int index = (int) (length * Math.random());

				if (Random.nextBool()) {
					// add a new tag
					x.getTags()[index][i] = Random.nextBool() ? '1' : '0';
                    //x.getIsMarked()[i] = true;
				} else {
					// delete an existing tag
					x.getTags()[index][i]  = Character.MIN_VALUE;
					//x.getIsMarked()[i] = x.isMarked(i);
				}
			 }
		 }

		return x;
	}
	
	public static void main(String[] args) throws Exception {
		Marking m = new Marking(0);
		String e = "0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<< 0 >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<1  >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<< 0 >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 1<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >>";

		MarkedBitArray x = new MarkedBitArray(e, 3);

		System.out.println(x.toStringTags());
		System.out.println(x);
		x = m.apply(x);
		System.out.println(x.toStringTags());
	}
}