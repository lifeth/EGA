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
	
	protected Compute epiTagsStability[];

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

		int length = x.getEpiTagsLength(); // max tags length in bits

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
								x.getEpiTags()[i][col] = Random.nextBool() ? '1' : '0';
							}
						}
					} else {
						// Remove an existing tag (all 5-bits)
						if (x.isMarked(col)) {
							int pos = Integer.parseInt(String.valueOf(x.getTagsPerAllele(col)), 2);
							Compute c = this.epiTagsStability[pos];
							if(c.getAvgWithoutTag() > c.getAvgWithTag()){
								for (int i = 0; i < length; i++) {
									x.getEpiTags()[i][col] = Character.MIN_VALUE;
								}
							}
						}
					}

				} else {

					// Modify an existing tag (a single bit)
					if (x.isMarked(col)) {
						int pos = Integer.parseInt(String.valueOf(x.getTagsPerAllele(col)), 2);
						Compute c = this.epiTagsStability[pos];
						if(c.getAvgWithoutTag() > c.getAvgWithTag()){//debe definir la probabil
							for (int i = 0; i < length; i++) {
								if (Random.nextBool()) {
									x.getEpiTags()[i][col] ^= 1;
									//x.getEpiTags()[i][col] = Random.nextBool() ? '1' : '0';
								}
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
		
		//32 tags... schema method
		if(this.epiTagsStability == null){
			this.epiTagsStability = new Compute[32];
			
			for (int i = 0; i < epiTagsStability.length; i++) {
				this.epiTagsStability[i] = new Compute();
			}
		}else{
			for (int i = 0; i < epiTagsStability.length; i++) {
				this.epiTagsStability[i].sumWith = 0;
				this.epiTagsStability[i].withTag = 0;
				this.epiTagsStability[i].sumWithout = 0;
				this.epiTagsStability[i].withoutTag = 0;
			}
		}
		
		for (int i = 0; i < pop.length; i++){
			
			MarkedBitArray x = pop[i].object();
			
			boolean [] present = new boolean[epiTagsStability.length];
			
			for (int j = 0; j < x.size(); j++) {
				
				if(x.isMarked(j)){
					int pos = Integer.parseInt(String.valueOf(x.getTagsPerAllele(j)), 2);
					if(!present[pos]){
						present[pos]= true; 
					}
				}
			}
			
			for (int j = 0; j < present.length; j++) {
				if(present[j]){
					this.epiTagsStability[j].withTag+=1;
					this.epiTagsStability[j].sumWith+= (double) pop[i].info(Goal.class.getName());
				}else{
					this.epiTagsStability[j].withoutTag+=1;
					this.epiTagsStability[j].sumWithout+= (double) pop[i].info(Goal.class.getName());
				}		
			}
		}
		
//		for (Compute comp : epiTagsStability) {
//			System.out.println(comp);
//		}
	}
	
	public class Compute{
		
		int withTag;
		double sumWith;
		int withoutTag;
		double sumWithout;
		
		public double getAvgWithTag(){
			return withTag == 0 ? 0: (sumWith/=withTag);
		}
		
		public double getAvgWithoutTag(){
			return withoutTag == 0 ? 0: (sumWithout/=withoutTag);
		}
		
		public double getStability(){
			return this.getAvgWithTag() - this.getAvgWithoutTag();
		}
		
		@Override
		public String toString() {
			return "Compute [withTag=" + withTag + ", sumWith=" + sumWith + ", withoutTag=" + withoutTag
					+ ", sumWithout=" + sumWithout + "]" 
					+ " AVG Without: "+ this.getAvgWithoutTag() + " AVG With: "+ this.getAvgWithTag();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Marking m = new Marking(0.03);
		String e = "0<<   00000>> 0<<   >> 1<<   >> 1<<   >> 1<<   >> 0<<   >> 1<<   00000>> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 1<<00000   >> 1<<   >> 1<<   >> 0<<   >> 1<<   >> 0<<   >> 0<<   >> 0<<   >> 0<<   >>";
		MarkedBitArray x = new MarkedBitArray(e, 5);

		System.out.println(x.toStringTags());
		System.out.println(x);
		x = m.apply(x);
		System.out.println(x.toStringTags());
		
//		String op[] = { "00", "01", "10", "11"};
//		String size[] = { "000", "001", "010", "011", "100","101", "110", "111"};
//		
//		for (int i = 0; i < op.length; i++) {
//			for (int j = 0; j < size.length; j++) {
//				
//			}
//		}
	}
}