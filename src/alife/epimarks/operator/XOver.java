package alife.epimarks.operator;

import alife.epimarks.types.MarkedBitArray;
import unalcol.clone.*;
import unalcol.random.raw.RawGenerator;
import unalcol.search.variation.Variation_2_2;

public class XOver extends Variation_2_2<MarkedBitArray> {

	/**
	 * The crossover point of the last xover execution
	 */
	protected int crossOverPoint;

	public XOver() {
	}

	/**
	 * Apply the simple point crossover operation over the given genomes and epigenomes at the
	 * given cross point
	 * 
	 * @param child1
	 *            The first parent
	 * @param child2
	 *            The second parent
	 * @param xoverPoint
	 *            crossover point
	 * @return The crossover point
	 */
	protected MarkedBitArray[] apply(MarkedBitArray child1, MarkedBitArray child2, int xoverPoint) {

		try {

			MarkedBitArray child1_1 = (MarkedBitArray) Clone.create(child1);
			MarkedBitArray child2_1 = (MarkedBitArray) Clone.create(child2);
			MarkedBitArray child1_2 = (MarkedBitArray) Clone.create(child1);
			MarkedBitArray child2_2 = (MarkedBitArray) Clone.create(child2);

			crossOverPoint = xoverPoint;

			child1_2.leftSetToZero(crossOverPoint);
			child2_2.leftSetToZero(crossOverPoint);
			child1_1.rightSetToZero(crossOverPoint);
			child2_1.rightSetToZero(crossOverPoint);

			child1_2.or(child2_1);
			child2_2.or(child1_1);

			char[][] tags_child1 = new char[child1.getEpiTagsLength()][];
			char[][] tags_child2 = new char[child1.getEpiTagsLength()][];
			
			for (int i = 0; i < child1.getEpiTagsLength(); i++) {
				tags_child1[i] = child2_2.getEpiTags()[i].clone();
				tags_child2[i] = child1_2.getEpiTags()[i].clone();
			}

			for (int i = 0; i < child1.getEpiTagsLength(); i++) {

				System.arraycopy(child1_2.getEpiTags()[i], crossOverPoint, tags_child1[i], crossOverPoint,
						child1_2.getEpiTags()[i].length - crossOverPoint);

				System.arraycopy(child2_2.getEpiTags()[i], crossOverPoint, tags_child2[i], crossOverPoint,
						child2_2.getEpiTags()[i].length - crossOverPoint);
			}

			child1_2.setEpiTags(tags_child1);
			child2_2.setEpiTags(tags_child2);

			return new MarkedBitArray[] { child1_2, child2_2 };

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Apply the simple point crossover operation over the given genomes
	 * 
	 * @param child1
	 *            The first parent
	 * @param child2
	 *            The second parent
	 * @return The crossover point
	 */
	@Override
	public MarkedBitArray[] apply(MarkedBitArray child1, MarkedBitArray child2) {
		RawGenerator g = RawGenerator.get(this);
		int pos = g.integer(Math.min(child1.size(), child2.size()));
		return apply(child1, child2, pos);
	}

	public static void main(String[] args) {

		XOver xo = new XOver();
		MarkedBitArray x = new MarkedBitArray(10, 0, true);

		/*x.setEpiTags(new char[][] { 
				{ '1', '1', '0', '1', '1', '1', '1', '0', '1', '1' },
				{ '0', '0', '1', '0', '1', '1', '1', '0', '1', '1' },
				{ '1', '1', '1', '1', '0', '1', '1', '1', '1', '0' },
				{ '1', '1', '0', '1', '1', '1', '1', '0', '1', '1' },
				{ '1', '1', '0', '1', '1', '1', '1', '0', '1', '1' } });*/

		MarkedBitArray y = new MarkedBitArray(10, 0, true);

		/*y.setEpiTags(new char[][] { 
				{ '1', '0', '0', '1', '1', '0', '1', '0', '1', '1' },
				{ '0', '0', '0', '1', '1', '1', '1', '0', '1', '1' },
				{ '0', '0', '1', '1', '1', '1', '1', '0', '0', '1' },
				{ '0', '0', '1', '1', '1', '1', '1', '0', '0', '1' },
				{ '0', '0', '1', '1', '1', '1', '1', '0', '0', '1' }});*/
		
		MarkedBitArray children[] = xo.apply(x, y);
		
		System.out.println(x.toStringTags());
		System.out.println(y.toStringTags());
		
		System.out.println(children[0].toStringTags());
		System.out.println(children[1].toStringTags());
	}
}