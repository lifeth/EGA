/**
 * 
 */
package alife.epimarks.operator;

import alife.epimarks.types.MarkedBitArray;
import unalcol.clone.Clone;

/**
 * @author lifeth
 *
 */
public class Reader extends AMarksReader<MarkedBitArray> {

	private Rules rule = new Rules();

	@Override
	public MarkedBitArray readMarks(MarkedBitArray x) {

		MarkedBitArray genome = (MarkedBitArray) Clone.create(x);

		try {
			
			String binary;

			for (int i = 0; i < x.size(); i++) {
				
				int k = 0;
				binary = null;

				if (x.isMarked(i)) {//x.getIsMarked()[i]
					
					k = 1;
					
					for (int j = i + 1; j < x.size(); j++) {

						if (x.isMarked(j))
							k++;
						else
							break;
					}

					if (k > 3) {
						binary = "1001";// -2
						return rule.getRule(binary).apply(genome, i, k);
					}

					if (k == 2 || k == 3) {
						binary = "1000";// -1
						genome = rule.getRule(binary).apply(genome, i, k);
						i += k - 1;
						continue;
					}
					

					binary = String.valueOf(x.getTags(i)).replaceAll(Character.toString(Character.MIN_VALUE), "");
					
					k = 0;

					// limit by finding an allele with tags
					int d = rule.getValue(binary);
					if (d == 1 || d == 4 || d == 7) {

						for (int j = i + 1; j < x.size(); j++) {
							if (!x.isMarked(j))
								k++;
							else
								break;
						}
					}

					genome = rule.getRule(binary).apply(genome, i, k);

					i += k;
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return genome;
	}

	public static void main(String[] args) throws Exception {
		Reader r = new Reader();

		/*
		 * MarkedBitArray x = new MarkedBitArray(10, 3, true); x.setTags(new
		 * char[][]{
		 * {Character.MIN_VALUE,Character.MIN_VALUE,'1',Character.MIN_VALUE,
		 * Character.MIN_VALUE,Character.MIN_VALUE,Character.MIN_VALUE,Character
		 * .MIN_VALUE,Character.MIN_VALUE,Character.MIN_VALUE},
		 * {Character.MIN_VALUE,Character.MIN_VALUE,'0',Character.MIN_VALUE,
		 * Character.MIN_VALUE,Character.MIN_VALUE,Character.MIN_VALUE,Character
		 * .MIN_VALUE,Character.MIN_VALUE,Character.MIN_VALUE},
		 * {Character.MIN_VALUE,Character.MIN_VALUE,'0',Character.MIN_VALUE,
		 * Character.MIN_VALUE,Character.MIN_VALUE,Character.MIN_VALUE,'0',
		 * Character.MIN_VALUE,Character.MIN_VALUE}});
		 * 
		 * for (int i = 0; i < x.size(); i++) { x.getIsMarked()[i] =
		 * x.isMarked(i); }
		 */
	    String e = "1<<   >> 1<<11 >> 1<<   >> 1<<1 0>> 0<<1  >> 1<<000>> 1<<0 0>> 1<<11 >> 1<< 1 >> 1<< 10>> 0<< 1 >> 1<< 0 >> 0<< 1 >> 1<<11 >> 0<<0  >> 0<< 00>> 0<<  1>> 0<<  1>> 0<<  0>> 1<<00 >> 0<<   >> 1<< 1 >> 1<<110>> 0<<10 >> 0<<001>> 1<< 00>> 1<<  0>> 0<<0 0>> 0<<   >> 1<<   >> 1<<   >> 1<<11 >> 1<<   >> 1<< 10>> 1<<1 1>> 0<<0 0>> 1<<1  >> 1<<   >> 1<< 0 >> 0<<   >> 0<<1 1>> 1<< 01>> 0<<   >> 0<< 1 >> 0<<   >> 0<<10 >> 1<<0 1>> 0<<0 1>> 0<<0  >> 1<<  1>> 0<<0  >> 0<< 1 >> 1<<01 >> 0<<1  >> 1<<  1>> 1<< 0 >> 1<<10 >> 0<<11 >> 1<< 01>> 0<<  0>> 1<<   >> 1<<   >> 0<<   >> 0<< 01>> 0<<10 >> 0<< 11>> 0<<100>> 0<<  1>> 1<< 10>> 0<<010>> 0<<  0>> 0<< 11>> 0<< 10>> 0<<000>> 0<<0 1>> 1<<  1>> 1<<0  >> 1<< 1 >> 1<<  1>> 1<< 00>> 0<< 10>> 1<< 1 >> 1<<111>> 0<< 10>> 1<<10 >> 1<< 10>> 1<<1 1>> 0<<001>> 0<<00 >> 1<<010>> 1<<0  >> 0<<1 0>> 1<<  1>> 1<<010>> 1<<1 1>> 1<<  0>> 1<<110>> 1<<1  >> 1<<0  >> 0<<  1>> 0<<001>> 0<<  0>> 0<<10 >> 0<<  0>> 1<<110>> 1<<101>> 1<<   >> 1<<111>> 0<< 01>> 1<< 0 >> 1<<0  >> 0<<101>> 0<<110>> 0<< 00>> 0<<111>> 0<<  1>> 1<<100>> 1<< 1 >> 1<<  0>> 0<<110>> 0<<10 >> 0<<01 >> 1<<  0>> 0<<11 >> 0<<0  >> 0<<10 >> 0<<   >> 0<< 00>> 0<<   >> 0<<   >> 0<<0 0>> 0<<  1>> 0<< 10>> 1<< 0 >> 0<< 01>> 0<< 01>> 0<< 10>> 0<<1  >> 0<<  1>> 1<<   >> 0<<   >> 0<<1 1>> 0<<100>> 1<<1  >> 1<<1  >> 1<<011>> 0<<00 >> 0<<1 0>> 1<<  1>> 1<< 1 >> 1<<   >> 0<< 0 >> 1<< 01>> 0<<01 >> 1<<00 >> 0<<1  >> 1<<   >> 0<<  1>> 0<<   >> 0<<   >> 0<<  0>> 0<<0  >> 1<<  0>> 1<<   >> 0<<  0>> 1<<1 0>> 0<<01 >> 1<<  1>> 1<< 1 >> 0<<   >> 1<<0 0>> 1<< 0 >> 1<<10 >> 1<<   >> 1<<   >> 0<<0  >> 1<<1 1>> 0<<  1>> 1<<0 1>> 1<< 1 >> 1<<1  >> 1<<00 >> 1<<   >> 1<<  0>> 1<<   >> 1<< 1 >> 0<<000>> 0<<0 0>> 0<<1  >> 1<<  0>> 0<<10 >> 0<<010>> 1<<1  >> 1<<   >> 1<<  0>> 1<<  1>> 1<<  0>> 0<<0 0>> 1<<0  >> 1<<  0>> 0<<1 1>> 1<<   >> 0<< 01>> 1<<   >> 1<<  1>> 1<<  1>> 1<<   >> 0<< 1 >> 0<<0  >> 1<<   >> 0<<00 >> 0<<11 >> 1<<10 >> 0<<0  >> 1<<100>> 1<<1 0>> 0<<10 >> 1<< 00>> 1<<   >> 0<< 0 >> 1<< 00>> 1<< 0 >> 1<<11 >> 0<<0 1>> 1<<010>> 1<<  0>> 1<< 01>> 0<<1 1>> 0<<0 1>> 1<<000>> 1<<0  >> 1<<  0>> 1<< 1 >> 1<<  1>> 0<<11 >> 1<<000>> 0<< 1 >> 1<< 1 >> 0<<   >> 1<<   >> 0<<  1>> 1<<1 0>> 0<< 00>> 1<<1  >> 0<<101>> 1<<   >> 1<< 00>> 0<<111>> 1<<   >> 0<<0  >> 1<<  1>> 1<<0 0>> 1<< 11>> 0<<   >> 1<<0  >> 1<<10 >> 1<<   >> 1<<110>> 1<< 0 >> 1<<110>> 1<<001>> 1<<1  >> 1<< 00>> 0<< 0 >> 0<<1  >> 1<<   >> 0<<001>> 1<<000>> 0<<1 1>> 1<< 0 >> 1<<1  >> 0<<   >> 0<<000>> 0<<0  >> 0<< 0 >> 1<< 01>> 1<<  0>> 0<<001>> 0<<  1>> 1<<  1>> 0<< 1 >> 1<<100>> 1<<10 >> 0<<   >> 0<<111>> 0<<11 >> 0<<1  >> 1<<0  >> 0<< 01>> 0<<1  >> 0<<0 0>> 1<<111>> 1<<011>> 0<<01 >> 1<<  1>> 1<< 01>> 0<<0 0>> 0<<1  >> 1<<1 0>> 1<<   >> 0<<  0>> 0<<   >> 0<<1 0>> 1<<101>> 1<<1  >> 1<<1 0>> 0<<11 >> 0<< 11>> 0<<  0>> 1<< 0 >> 0<<111>> 1<<   >> 1<<100>> 1<<1 0>> 1<<  1>> 1<<010>> 0<<0 1>> 0<<   >> 1<<   >> 1<<11 >>";     
	    MarkedBitArray x = new MarkedBitArray(e, 3);

		System.out.println(x.toStringTags());
		System.out.println(x);
		System.out.println(r.readMarks(x));
	}
}
