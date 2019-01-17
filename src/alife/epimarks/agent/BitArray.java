/**
 * 
 */
package alife.epimarks.agent;

import unalcol.random.util.RandBool;
import unalcol.types.integer.IntUtil;

/**
 * @author lifeth
 *
 */
public class BitArray extends unalcol.types.collection.bitarray.BitArray {

	protected GeneInfo[] tags;// for each gene there is a gene info object with the tags
	protected int geneLength;
	protected boolean randomStates = true;

	/**
	 * @param n
	 *            The size of the bit array
	 * @param randomly
	 *            If the array will be initialized randomly or not
	 * @param genSize
	 *            number of alleles per gene
	 */
	public BitArray(boolean randomly, int n, int genSize) {
		super(n, randomly);
		this.geneLength = genSize;
		this.setGeneInfo();
	}

	/**
	 * @param randomly
	 *            If the array will be initialized randomly or not
	 * @param n
	 *            The size of the bit array
	 * @param genSize
	 *            number of alleles per gene
	 * @param randomStates
	 *            whether the states are going to be random (active and
	 *            inactive) or only active by default.
	 */
	public BitArray(boolean randomly, int n, int genSize, boolean randomStates) {
		super(n, randomly);
		this.geneLength = genSize;
		this.randomStates = randomStates;
		this.setGeneInfo();
	}

	public BitArray(BitArray source) {
		super(source);
		if (source.tags != null) {
			this.geneLength = source.geneLength;
			this.tags = new GeneInfo[source.tags.length];
			for (int i = 0; i < source.tags.length; i++) {
				char[] t = source.tags[i].getTags();
				this.tags[i] = new GeneInfo(source.tags[i].isActive(), t != null ? t.clone() : null);
			}
		}
	}

	public BitArray(String source) {
		super(source);
	}

	/**
	 * Gene Info (state and tags)
	 */
	public void setGeneInfo() {

		int m = size() / this.geneLength;
		this.tags = new GeneInfo[m];

		for (int i = 0; i < m; i++) {
			this.tags[i] = new GeneInfo(1);
		}

		if (randomStates) {

			GeneInfo.showstate = true;

			RandBool rb = new RandBool(0.3);

			for (int i = 0; i < m; i++) {

				if (rb.next()) {
					tags[i].setState(false);
				}
			}

			/* int count = 0;
			 * while (count < 10) {// 10 states only?
			 * 
			 * int index = (int) (Math.random() * m);
			 * 
			 * if (tags[index].isActive()) { tags[index].setState(false);
			 * count++; } }
			 */
		}
	}

	@Override
	public BitArray subBitArray(int start, int end) {
		return new BitArray(super.subBitArray(start, end).toString());
	}

	@Override
	public BitArray clone() {
		return new BitArray(this);
	}

	public BitArray cloneBits() {
		BitArray b = new BitArray(this.toString());
		b.geneLength = this.geneLength;
		b.setGeneInfo();
		return b;
	}

	public int[] getActiveGenes() {
		int[] genes = new int[this.tags.length];

		for (int i = 0; i < tags.length; i++) {
			GeneInfo gene = this.tags[i];
			if (gene.isActive())
				genes[i] = i;
		}

		return genes;
	}

	/**
	 * @return the tags
	 */
	public GeneInfo[] getGenesInfo() {
		return tags;
	}

	/**
	 * @return the geneLength
	 */
	public int getGeneLength() {
		return geneLength;
	}

	public String toStringTags() {

		String text = "";
		int length = 0;
		int start;
		int n = size() / this.geneLength;

		for (int i = 0; i < n; i++) {

			start = this.geneLength * i;
			length = start + this.geneLength;

			for (int j = start; j < length; j++) {
				if (get(j)) {
					text += "1";
				} else {
					text += "0";
				}
			}

			text += tags[i].toString();

			text += " ";
		}

		return text;
	}

	/**
	 * Returns the buffer position (the integer that contains the bit) of an
	 * specific bit
	 * <p>
	 * m DIV INTEGER_SIZE
	 * </P>
	 * 
	 * @param m
	 *            The bit index
	 * @return The buffer position of an specific bit
	 */
	protected int getIndex(int m) {
		return (m >>> IntUtil.DIV_MASK);
	}

	/**
	 * Returns the position of a specific bit in the integer that contains it.
	 * <p>
	 * m MOD INTEGER_SIZE
	 * </p>
	 * 
	 * @param m
	 *            The bit index
	 * @return The position of a specific bit in the integer that contains it
	 */
	protected int getBit(int m) {
		return (m & IntUtil.MOD_MASK);
	}

	/**
	 * Set the first bits to zero.
	 * <p>
	 * A = 1000111011
	 * </p>
	 * <p>
	 * A.leftSetToZero( 6 ) = 0000001011
	 * </p>
	 * 
	 * @param end
	 *            The last point to be set to zeroes
	 */
	public void leftSetToZeroes(int end) {

		int n = end * this.geneLength;
		int m = getIndex(n);

		if (0 <= m && m < getData().length) {
			int r = getBit(n);
			int mask = IntUtil.ONE_BITS >>> r;
			getData()[m] &= mask;

			for (int i = 0; i < m; i++) {
				getData()[i] = 0;
			}

			/*
			 * for (int i = 0; i < end; i++) {
			 * 
			 * StringBuilder sb = getTags()[i].getTags();
			 * 
			 * if (sb != null) { char[] tags = sb.toString().toCharArray();
			 * 
			 * for (int j = 0; j < tags.length; j++) { tags[j] = '0'; }
			 * 
			 * sb.setLength(0); sb.append(tags); } }
			 */

			// System.out.println("left: " + toStringTags());
		}
	}

	/**
	 * Set the last bits to zero starting to the given position.
	 * <p>
	 * A = 1000111011
	 * </p>
	 * <p>
	 * A.rightSetToZero( 6 ) = 1000110000
	 * </p>
	 * 
	 * @param start
	 *            The start point to be set to zeroes
	 */
	public void rightSetToZeroes(int start) {

		int n = start * this.geneLength;
		int m = getIndex(n);

		if (getData() != null && 0 <= m && m < getData().length) {

			int r = getBit(n);
			int mask;

			if (r > 0) {
				mask = IntUtil.ONE_BITS << (IntUtil.INTEGER_SIZE - r);
			} else {
				mask = 0;
			}

			getData()[m] &= mask;

			for (int i = m + 1; i < getData().length; i++) {
				getData()[i] = 0;
			}

			/*
			 * for (int i = start; i < size() / this.geneLength; i++) {
			 * 
			 * StringBuilder sb = getTags()[i].getTags();
			 * 
			 * if (sb != null) { char[] tags = sb.toString().toCharArray();
			 * 
			 * for (int j = 0; j < tags.length; j++) { tags[j] = '0'; }
			 * 
			 * sb.setLength(0); sb.append(tags); } }
			 */

			// System.out.println("right: " + toStringTags());
		}
	}

	/**
	 * Performs the or operator between the bit array and the given bit array.
	 * <p>
	 * A = 10011001011
	 * </p>
	 * <p>
	 * B = 0101010101
	 * </p>
	 * <p>
	 * A.or( B ) = 11011101011
	 * </p>
	 * <p>
	 * B.or( A ) = 1101110101
	 * </p>
	 * 
	 * @param arg2
	 *            The array used to perform the or operator
	 */
	public void oro(BitArray arg2) {

		String oldBits = this.toString();

		int n = arg2.size() / arg2.getGeneLength();
		int m = arg2.getData().length;

		if (getData().length < m) {
			m = getData().length;
		}

		for (int i = 0; i < m; i++) {
			getData()[i] |= arg2.getData()[i];
		}

		String newBits = this.toString();
		int start;
		int length;

		for (int i = 0; i < n; i++) {// gene

			start = this.geneLength * i;
			length = start + this.geneLength;

			if (!newBits.substring(start, length).equals(oldBits.substring(start, length))) {
				char[] t2 = arg2.getGenesInfo()[i].getTags();
				this.getGenesInfo()[i].setTag(t2 == null ? null : t2.clone());
			}
		}

		/*
		 * for (int i = 0; i < n; i++) {//gene
		 * 
		 * StringBuilder t1 = getTags()[i].getTags(); StringBuilder t2 =
		 * arg2.getTags()[i].getTags();
		 * 
		 * char[] tags1 = { Character.MIN_VALUE }; char[] tags2 = {
		 * Character.MIN_VALUE };
		 * 
		 * tags2 = t2 == null ? tags2 : t2.toString().toCharArray(); tags1 = t1
		 * == null ? tags1 : t1.toString().toCharArray();
		 * 
		 * m = tags1.length < tags2.length ? tags1.length : tags2.length;
		 * 
		 * //
		 * Arrays.stream(t2.toString().split("\\D")).mapToInt(Integer::parseInt)
		 * .toArray();
		 * 
		 * for (int k = 0; k < m; k++) { tags1[k] |= tags2[k]; }
		 * 
		 * if(Character.isDigit(tags1[0])){// && t1 != null if(t1 == null) t1 =
		 * new StringBuilder(String.valueOf(tags1)); else t1 = t1.replace(0,
		 * t1.length(), String.valueOf(tags1));
		 * 
		 * getTags()[i].setTag(t1); } }
		 */
	}
}
