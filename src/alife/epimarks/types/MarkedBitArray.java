package alife.epimarks.types;

import java.util.Arrays;

import alife.epimarks.Utils;
import unalcol.random.integer.IntUniform;
import unalcol.random.util.RandBool;
import unalcol.types.integer.IntUtil;

/**
 * <p>
 * Title: MarkedBitArray
 * </p>
 * <p>
 * Description: Stores the positions with a value different of the default
 * value,
 * 
 * @author Jonatan Gomez, modified by lifeth
 * @version 1.0
 *
 */

public class MarkedBitArray implements Cloneable {

	/**
	 * Matrix with tags per each bit in the data bit array. Each column
	 * corresponds to the epiTags of a bit. Each row contains a tag 1 or 0, each row
	 * of a column from top to the bottom forms part of the set of tags.
	 */
	protected char[][] epiTags = null;

	/**
	 * The tag length in binary string
	 */
	protected int l;

	//protected boolean ismarked[];

	/**
	 * Integer array used to store the bits
	 */
	protected int[] data = null;
	/**
	 * The number of bits in the bit array
	 */
	protected int n = 0;

	/**
	 * Constructor: Creates a bit array of n bits, in a random way or with all
	 * bit in false according to the randomly argument
	 * 
	 * @param n
	 *            The size of the bit array
	 * @param l
	 *            The tags-length in binary string
	 * @param randomly
	 *            If the array will be initialized randomly or not
	 */
	public MarkedBitArray(int n, int l, boolean randomly) {
		this(n, randomly);
		this.l = l;
		this.epiTags = new char[l][this.n];
	}

	/**
	 * Constructor: Creates a bit array of n bits, in a random way or with all
	 * bit in false according to the randomly argument
	 * 
	 * @param n
	 *            The size of the bit array
	 * @param randomly
	 *            If the array will be initialized randomly or not
	 */
	public MarkedBitArray(int n, boolean randomly) {
		this.n = n;
		int m = getIndex(n) + 1;
		data = new int[m];
		if (randomly) {
			IntUniform g = new IntUniform(IntUtil.HIGHEST_BIT >>> 1);
			RandBool rg = new RandBool();
			g.generate(data, 0, m);
			for (int i = 0; i < m; i++) {
				if (rg.next()) {
					data[i] = -data[i];
				}
			}
		} else {
			for (int i = 0; i < m; i++) {
				data[i] = 0;
			}
		}
	}

	/**
	 * Constructor: Creates a clone of the bit array given as argument
	 * 
	 * @param source
	 *            The bit array that will be cloned
	 */
	public MarkedBitArray(MarkedBitArray source) {
		if (source.data != null) {
			n = source.n;
			l = source.l;
			data = new int[source.data.length];
			epiTags = new char[l][n];
			//ismarked = new boolean[this.n];

			for (int i = 0; i < source.data.length; i++) {
				data[i] = source.data[i];
			}
			for (int i = 0; i < n; i++) {
				//ismarked[i] = source.ismarked[i];
				for (int j = 0; j < l; j++) {
					epiTags[j][i] = source.epiTags[j][i];
				}
			}
		}
	}

	/**
	 * Constructor: Creates a bit array using the boolean values given in the
	 * array
	 * 
	 * @param source
	 *            The bits that will conform the bit array
	 */
	public MarkedBitArray(boolean[] source) {
		n = source.length;
		int m = getIndex(n) + 1;
		data = new int[m];
		//ismarked = new boolean[this.n];
		for (int i = 0; i < n; i++) {
			set(i, source[i]);
		}
	}
	
	/**
	 * Constructor: Creates a bit array using the boolean values given in the
	 * array
	 * 
	 * @param source
	 *            The bits that will conform the bit array
	 */
	public MarkedBitArray(boolean[] source, int l) {
		n = source.length;
		this.l= l;
		int m = getIndex(n) + 1;
		data = new int[m];
		epiTags = new char[l][n];
		//ismarked = new boolean[this.n];
		for (int i = 0; i < n; i++) {
			set(i, source[i]);
		}
	}

	/**
	 * Constructor: Creates a bit array using the boolean values given in the
	 * string
	 * 
	 * @param source
	 *            The String with the bits that will conform the bit array
	 */
	public MarkedBitArray(String source, int l, int some) {
		n = source.length();
		this.l= l;
		int m = getIndex(n) + 1;
		data = new int[m];
		epiTags = new char[l][n];
		//ismarked = new boolean[this.n];
		for (int i = 0; i < n; i++) {
			set(i, (source.charAt(i) == '1'));
		}
	}
	
	/**
	 * @param source  The String with the bits that will conform the bit array
	 * @param l tag length
	 */
	public MarkedBitArray(String source, int l) {
		
		String [] alleles = source.split(" ");
		source = "";
		for (String string : alleles) {
			source += string.charAt(0);
		}
			
		n = source.length();
		int m = getIndex(n) + 1;
		data = new int[m];
		this.l = l;
		for (int i = 0; i < n; i++) {
			set(i, (source.charAt(i) == '1'));
		}
			
		epiTags = new char[l][n];

		for (int j = 0; j < n; j++) {
			String string = alleles[j];
			String ts = string.replaceAll("<|>", "").substring(1).trim();
			
			for (int i = 0; i < ts.length(); i++) {
				this.epiTags[i][j] = ts.charAt(i);
			}

		}
	}

	/**
	 * Utilizada para retornar un MarkedBitArray
	 * 
	 * @param limit
	 *            Utilizado para asignarle el tamano al MarkedBitArray con el
	 *            numero de bit requeridos para represnetar su valor
	 * @return MarkedBitArray de tamano igual al numero de bit para representar
	 *         limit y contiene 0 y 1 aleatorios
	 */
	public static MarkedBitArray random(int limit) {
		int size = IntUtil.getBitsNumber(limit);
		return new MarkedBitArray(size, true);
	}

	/**
	 * Utilizada para clonar un MarkedBitArray, sin copiar su referencia
	 * 
	 * @return The new MarkedBitArray
	 */
	@Override
	public MarkedBitArray clone() {
		return new MarkedBitArray(this);
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
	private int getIndex(int m) {
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
	private int getBit(int m) {
		return (m & IntUtil.MOD_MASK);
	}

	/**
	 * Sets a bit to a given value
	 * 
	 * @param i
	 *            The bit index
	 * @param v
	 *            The new value for the bit
	 */
	public void set(int i, boolean v) {
		int m = getIndex(i);
		int p = getBit(i);
		int vmask = (IntUtil.HIGHEST_BIT >>> p);
		int dmask = vmask & data[m];
		if (v) {
			if (dmask == 0) {
				data[m] |= vmask;
			}
			;
		} else {
			if (dmask != 0) {
				data[m] ^= vmask;
			}
		}
	}

	/**
	 * Returns the boolean value of a specific position
	 * 
	 * @param i
	 *            The bit index
	 * @return The boolean value of a specific position
	 */
	public boolean get(int i) {
		int m = getIndex(i);
		int p = getBit(i);
		return (((IntUtil.HIGHEST_BIT >>> p) & data[m]) != 0);
	}

	

	/**
	 * Returns a sub bit array of the bit array starting from the position start
	 * until the end of the bit array.
	 * <p>
	 * A = 1000111001
	 * </p>
	 * <p>
	 * A.subMarkedBitArray( 4 ) = 111001
	 * </p>
	 * <p>
	 * A.subArray( 0 ) = 1000111001
	 * </p>
	 * <p>
	 * A.subArray( 10 ) = empty bit array
	 * </p>
	 * 
	 * @param start
	 *            The start position
	 * @return A sub bit array of the bit array starting from the position start
	 *         until the end of the bit array.
	 */
	public MarkedBitArray subMarkedBitArray(int start) {
		MarkedBitArray subArray = new MarkedBitArray(this);
		subArray.leftShift(start);
		subArray.n -= start;
		if (subArray.n < 0) {
			subArray.n = 0;
		}
		return subArray;
	}

	/**
	 * Returns the sub bit array of the bit array starting at the position start
	 * and the previous to the position end-1. If the end position is greater
	 * than the last position of the array then the function will return only
	 * the last bits.
	 * <p>
	 * A = 1000111001
	 * </p>
	 * <p>
	 * A.subMarkedBitArray( 4, 7 ) = 111
	 * </p>
	 * <p>
	 * A.subArray( 0, 4 ) = 1000
	 * </p>
	 * <p>
	 * A.subArray( 7, 11 ) = 001
	 * </p>
	 * 
	 * @param start
	 *            The start position of the substring
	 * @param end
	 *            The end position + 1 of the subarray
	 * @return The sub bit array of the bit array starting at the position start
	 *         and the previous to the position end-1.
	 */
	public MarkedBitArray subMarkedBitArray(int start, int end) {
		int length = end - start;
		MarkedBitArray subArray = subMarkedBitArray(start);
		if (subArray.n > length) {
			subArray.n = length;
		}

		subArray.rightSetToZero(subArray.n);
		return subArray;
	}

	/**
	 * Shifts the bit array to the left in k bits. The remaining bits will be
	 * set to zero.
	 * <p>
	 * A = 1010011011
	 * </p>
	 * <p>
	 * A.left_shift( 3 ) = 0011011000
	 * </p>
	 * 
	 * @param k
	 *            The number of bits to be shifted
	 */
	public void leftShift(int k) {
		if (data != null) {
			rightSetToZero(n);
			int startindex = getIndex(k);
			k = getBit(k);
			if (k > 0) {
				int supk = IntUtil.INTEGER_SIZE - k;
				int m = data.length - 1;
				int j = 0;
				for (int i = startindex; i < m; i++) {
					data[j] = (data[i] << k) | (data[i + 1] >>> supk);
					j++;
				}
				data[j] = (data[m] << k);
				for (int i = j + 1; i < m; i++) {
					data[i] = 0;
				}
			} else {
				int j = 0;
				for (int i = startindex; i < data.length; i++) {
					data[j] = data[i];
					j++;
				}
				for (int i = j; i < data.length; i++) {
					data[i] = 0;
				}
			}
		}
	}

	/**
	 * Shifts the bit array to the right in k bits. The remaining bits will be
	 * set to zero
	 * <p>
	 * A = 1010011011
	 * </p>
	 * <p>
	 * A.right_shift( 3 ) = 0001010011
	 * </p>
	 * 
	 * @param k
	 *            The number of bits to be shifted
	 */
	public void rightShift(int k) {
		if (data != null) {
			rightSetToZero(n);
			int startindex = getIndex(k);
			k = getBit(k);
			if (k > 0) {
				int supk = IntUtil.INTEGER_SIZE - k;
				// int m = data.length - 1;
				int j = data.length - 1 - startindex;
				for (int i = data.length - 1; i > startindex; i--) {
					data[i] = (data[j] >>> k) | (data[j - 1] << supk);
					j--;
				}
				data[startindex] = (data[0] >>> k);
				for (int i = 0; i < startindex; i++) {
					data[i] = 0;
				}
			} else {
				int j = data.length - 1 - startindex;
				for (int i = data.length - 1; i >= startindex; i--) {
					data[i] = data[j];
					j--;
				}
				for (int i = 0; i < startindex; i++) {
					data[i] = 0;
				}
			}
		}
	}

	/**
	 * Add the given bit array to the end of the bit array.
	 * <p>
	 * A = 1001
	 * </p>
	 * <p>
	 * B = 11001
	 * </p>
	 * <p>
	 * A.add( B ) = 100111001
	 * </p>
	 * 
	 * @param source
	 *            The bit array to be added to the end
	 */
	public void add(MarkedBitArray source) {

		MarkedBitArray newMarkedBitArray = new MarkedBitArray(n + source.n, source.l, false);
		newMarkedBitArray.or(source);
		newMarkedBitArray.rightShift(n);
		newMarkedBitArray.or(this);
		data = newMarkedBitArray.data;
		n = newMarkedBitArray.n;
	}

	/**
	 * Add the given bit to the end of the bit array
	 * 
	 * @param v
	 *            The bit to be added
	 */
	public void add(boolean v) {
		if (data.length * IntUtil.INTEGER_SIZE == n) {
			int[] newdata = new int[data.length + 1];
			for (int i = 0; i < data.length; i++) {
				newdata[i] = data[i];
			}
			if (v) {
				newdata[data.length] = IntUtil.HIGHEST_BIT;
			} else {
				newdata[data.length] = 0;
			}
			data = newdata;
		} else {
			set(n, v);
		}
		n++;
	}

	/**
	 * Removes the last bit in the bit array
	 */
	public void del() {
		del(1);
	}

	/**
	 * Removes the last k bits from the bit array
	 * 
	 * @param k
	 *            the number of bits to be removed
	 */
	public void del(int k) {
		n -= k;
		if (n > 0) {
			int m = getIndex(n);
			if (m + 1 < data.length) {
				int[] newdata = new int[m + 1];
				for (int i = 0; i <= m; i++) {
					newdata[i] = data[i];
				}
				data = newdata;
			}
		} else {
			n = 0;
			data = new int[1];
		}
	}

	/**
	 * Returns the number of bits in the array
	 * 
	 * @return The number of bits in the array
	 */
	public int size() {
		return n;
	}

	/**
	 * Sets all the bits in the array to zero
	 */
	public void zero() {
		for (int i = 0; i < data.length; i++) {
			data[i] = 0;
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
	 *            The start position to be set to zero
	 */
	public void rightSetToZero(int start) {
		int m = getIndex(start);
		if (data != null && 0 <= m && m < data.length) {
			int r = getBit(start);
			int mask;
			if (r > 0) {
				mask = IntUtil.ONE_BITS << (IntUtil.INTEGER_SIZE - r);
			} else {
				mask = 0;
			}
			data[m] &= mask;
			for (int i = m + 1; i < data.length; i++) {
				data[i] = 0;
			}
		}
	}

	/**
	 * Set the last bits to one starting to the given position.
	 * <p>
	 * A = 1000110011
	 * </p>
	 * <p>
	 * A.rightSetToOne( 6 ) = 1000111111
	 * </p>
	 * 
	 * @param start
	 *            The start position to be set to one
	 */
	public void rightSetToOne(int start) {
		int m = getIndex(start);
		if (0 <= m && m < data.length) {
			int r = getBit(start);
			int mask = IntUtil.ONE_BITS >>> r;
			data[m] |= mask;
			for (int i = m + 1; i < data.length; i++) {
				data[i] = IntUtil.ONE_BITS;
			}
		}
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
	 *            The number of bits to be set to zero
	 */
	public void leftSetToZero(int end) {
		int m = getIndex(end);
		if (0 <= m && m < data.length) {
			int r = getBit(end);
			int mask = IntUtil.ONE_BITS >>> r;
			data[m] &= mask;
			for (int i = 0; i < m; i++) {
				data[i] = 0;
			}
		}
	}

	/**
	 * Set the first bits to one.
	 * <p>
	 * A = 1000111011
	 * </p>
	 * <p>
	 * A.leftSetToOne( 6 ) = 1111111011
	 * </p>
	 * 
	 * @param end
	 *            The number of bits to be set to one
	 */
	public void leftSetToOne(int end) {
		int m = getIndex(end);
		if (0 <= m && m < data.length) {
			int r = getBit(end);
			int mask = IntUtil.ONE_BITS << (IntUtil.INTEGER_SIZE - r);
			data[m] |= mask;
			for (int i = 0; i < m; i++) {
				data[i] = IntUtil.ONE_BITS;
			}
		}
	}

	/**
	 * Performs the and operator between the bit array and the given bit array.
	 * <p>
	 * A = 10011001011
	 * </p>
	 * <p>
	 * B = 0101010101
	 * </p>
	 * <p>
	 * A.and( B ) = 00010001011
	 * </p>
	 * <p>
	 * B.and( A ) = 0001000101
	 * </p>
	 * 
	 * @param arg2
	 *            The array used to perform the and operator
	 */
	public void and(MarkedBitArray arg2) {
		arg2.rightSetToOne(arg2.n);
		int m = arg2.data.length;
		if (data.length < m) {
			m = data.length;
		}
		for (int i = 0; i < m; i++) {
			data[i] &= arg2.data[i];
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
	public void or(MarkedBitArray arg2) {
		arg2.rightSetToZero(arg2.n);
		int m = arg2.data.length;
		if (data.length < m) {
			m = data.length;
		}
		for (int i = 0; i < m; i++) {
			data[i] |= arg2.data[i];
		}
	}

	/**
	 * Performs the xor operator between the bit array and the given bit array.
	 * <p>
	 * A = 10011001011
	 * </p>
	 * <p>
	 * B = 0101010101
	 * </p>
	 * <p>
	 * A.xor( B ) = 11001100001
	 * </p>
	 * <p>
	 * B.xor( A ) = 1100110000
	 * </p>
	 * 
	 * @param arg2
	 *            The array used to perform the xor operator
	 */
	public void xor(MarkedBitArray arg2) {
		int m = arg2.data.length;
		if (data.length < m) {
			m = data.length;
		}
		for (int i = 0; i < m; i++) {
			data[i] ^= arg2.data[i];
		}
	}

	/**
	 * Flips all the bits in the bit array
	 * <p>
	 * A = 10001001
	 * </p>
	 * <p>
	 * A.not() = 01110110
	 * </p>
	 */
	public void not() {
		for (int i = 0; i < data.length; i++) {
			data[i] ^= IntUtil.ONE_BITS;
		}
	}

	/**
	 * Flips the bit given
	 * <p>
	 * A = 10011011
	 * </p>
	 * <p>
	 * A.not( 3 ) = 10001011
	 * </p>
	 * <p>
	 * A.not( 5 ) = 10011111
	 * </p>
	 * 
	 * @param bit
	 *            apply not
	 */
	public void not(int bit) {
		int m = getIndex(bit);
		int p = getBit(bit);
		data[m] ^= (IntUtil.HIGHEST_BIT >>> p);
	}

	/**
	 * Converts the bit array to a string
	 * <p>
	 * A = 1000111
	 * </p>
	 * <p>
	 * A.toString() = "1000111"
	 * </p>
	 * 
	 * @return The String representation of the bit array
	 */
	@Override
	public String toString() {
		String text = "";
		for (int i = 0; i < n; i++) {
			if (get(i)) {
				text += "1";
			} else {
				text += "0";
			}
		}
		return text;
	}

	/**
	 * Compares a MarkedBitArray with other Object
	 * 
	 * @param obj
	 *            The MarkedBitArray to be compared with this MarkedBitArray
	 * @return <i>true</i> if the object is a MarkedBitArray with the same
	 *         information than the bit array <i>false</i> otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag = (obj instanceof MarkedBitArray);
		if (flag) {
			MarkedBitArray other = (MarkedBitArray) obj;
			int s = size();
			flag = (other.size() == s);
			if (flag && s > 0) {
				for (int i = 0; i < data.length - 1 && flag; i++) {
					flag = (data[i] == other.data[i]);
				}
				if (flag) {
					for (int i = (data.length - 1) * IntUtil.INTEGER_SIZE; i < s && flag; i++) {
						flag = (get(i) == other.get(i));
					}
				}
			}
		}
		return flag;
	}

	// Special methods and atributes
	/**
	 * If the MarkedBitArray is using Gray code for representing the integer
	 * numbers
	 */
	public static boolean useGrayCode = false;

	/**
	 * Returns the integer at the given buffer position.
	 * 
	 * @param i
	 *            The buffer position
	 * @return The integer at the given buffer position.
	 */
	public int getInt(int i) {
		int x = data[i];
		if (useGrayCode) {
			x = IntUtil.grayToBinary(x);
		}
		return x;
	}

	/**
	 * Sets the integer value at the given buffer position.
	 * 
	 * @param i
	 *            The buffer position
	 * @param value
	 *            The new integer for the given buffer position.
	 */
	public void setInt(int i, int value) {
		if (useGrayCode) {
			data[i] = IntUtil.binaryToGray(value);
		} else {
			data[i] = value;
		}
	}

	/**
	 * Gets the array which stores the bits
	 * 
	 * @return The data of the bits array
	 */
	public int[] getData() {
		return data;
	}

	/**
	 * Sets the data of the bits array
	 * 
	 * @param data
	 *            The data of the bits array
	 */
	public void setData(int[] data) {
		this.data = data;
	}

	/**
	 * Gets the dimension of the bits array
	 * 
	 * @return The dimension
	 */
	public int dimension() {
		return n;
	}

	/**
	 * @return the tags
	 */
	public char[][] getEpiTags() {
		return epiTags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setEpiTags(char[][] tags) {
		this.epiTags = tags;
	}

	/**
	 * @return the max tags length in bits
	 */
	public int getEpiTagsLength() {
		return l;
	}
	
	public String toStringTags() {

		String text = "";

		for (int i = 0; i < n; i++) {

			if (get(i)) {
				text += "1";
			} else {
				text += "0";
			}

			char[] tags = getTagsPerAllele(i);

			text += "<<" + (tags == null ? "" : String.valueOf(tags)) + ">>";
			text += " ";
		}

		return text;
	}
	
	/**
	 * Returns the epi tags value of a specific position
	 * 
	 * @param col
	 *            The bit index
	 * @return The char array with tags of a specific bit position
	 */
	public char[] getTagsPerAllele(int col) {

		char[] tags = new char[l];

		for (int i = 0; i < l; i++) {
			tags[i] = this.epiTags[i][col];
		}

		return tags;
	}
	
	/**
	 * Returns the tags that represent the Operation from a specific position<br>
	 * OPERATIONS<br>
	 * 001 transpose<br>
	 * 000 shift<br>
	 * 010 set to<br>
	 * 011 do nothing<br>
	 * 101 add 1 (OR bit per bit)<br>
	 * 100 divide by 2 (arithmetic right shift - signed shift)<br>
	 * 110 multiply by 0 (AND bit per bit)<br>
	 * 111 subtract 1 (bit per bit)<br>
	 * 
	 * @param col
	 *            The bit index
	 * @return The char array with tags of a specific bit position
	 */
	public char[] getTagOp(int col) {

		char[] tags = new char[3];
		int j = 0;
		  
		for (int i = 0; i < tags.length; i++) {
			tags[j++] = this.epiTags[i][col];
		}

		return tags;
	}
	
	/**
	 * Returns the tags that represent the Gene Size from a specific position<br>
	 * 
	 *  GENE SIZES<br>
	 *	0001=1 ---  1001=9<br>
	 *	0010=2 ---  1010=10<br>
	 *	0011=3 ---  1011=11<br>
	 *	0100=4 ---  1100=12<br>
	 *	0101=5 ---  1101=13<br>
	 *	0110=6 ---  1110=14<br>
	 *	0111=7 ---  1111=15<br>
	 *	1000=8 ---  0000=16<br>
	 * 
	 * @param col
	 *            The bit index
	 * @return The char array with tags of a specific bit position
	 */
	public char[] getTagGSize(int col) {

		char[] tags = new char[l-3];
        int j = 0;
        
		for (int i = 3; i < l ; i++) {
			tags[j++] = this.epiTags[i][col];
		}

		return tags;
	}

	/**
	 * Transpose
	 * 
	 * @param index starting position
	 * @param k bits
	 */
	public void rightRotate(int index, int k) {
		
		char[] clone = this.toString().toCharArray();
		int end = index + (k-1);//including the bit
		
		if (end >= this.size())
			end = this.size() - 1;
		
		int j = end;
		for (int i = index; i <= end ; i++) {
			set(i, clone[j] == '1');
			j--;
		}
	}

	/**
	 * Circular shift
	 * 
	 * @param index starting position
	 * @param k bits
	 */
	public void rightShift(int index, int k) {
		
		char[] clone = this.toString().toCharArray();
		int end = index + k;//including the bit
		
		if (end > this.size())
			end = this.size();
		
		//end is exclusive
		char[] sub = Arrays.copyOfRange(clone, index, end);
		char[] sub2 = new char[sub.length];
		
		for (int i = 0; i < sub.length; i++) {
			sub2[((i + 1) % sub.length)] = sub[i];
		}
		 
		for (int i = 0; i < sub2.length; i++) {
			set(index, sub2[i] == '1');
			index++;
		}
	}

	/**
	 * Set To
	 * 
	 * @param index starting position
	 * @param k bits
	 */
	public void rightSetTo(int index, int k) {
		
		int end = index + (k-1);//including the bit
		
		if (end >= this.size())
			end = this.size() - 1;
		
		if (get(index)) {
			for (int i = index+1; i <= end; i++) {
				set(i, true);
			}
		} else {
			for (int i = index+1; i <= end; i++) {
				set(i, false);
			}
		}
	}
	
	/**
	 * Add ONE
	 * @param index
	 * @param k
	 */
	public void addOne(int index, int k)
	{
		int end = index + (k-1);//including the bit
		
		if (end >= this.size())
			end = this.size() - 1;
		
		MarkedBitArray sub = this.subMarkedBitArray(index, end+1);
		long b1 = Long.parseUnsignedLong(sub.toString(), 2);
		long sum = b1 + 1;
		//returns binary with no leading zeroes
		String binary = Long.toBinaryString(sum);
		boolean[] bits = new boolean[binary.length() > sub.n ? binary.length() : sub.n];
	
		int j = bits.length-1;
	     for (int i = binary.length()-1; i >= 0 ; i--) {
		    bits[j] = binary.charAt(i) == '1';
		    j--;
		 }	
		
	     if(binary.length() > sub.n && end < this.size() - 1)
	    	 end++;
	     
		j = 0;
		for (int i = index; i <= end ; i++) {
			set(i, bits[j]);
			j++;
		}
	}
	
	/**
	 * Subtract ONE
	 * @param index
	 * @param k
	 */
	public void subtractOne(int index, int k)
	{
		int end = index + (k-1);//including the bit
		
		if (end >= this.size())
			end = this.size() - 1;
		
		MarkedBitArray sub = this.subMarkedBitArray(index, end+1);
		long b1 = Long.parseUnsignedLong(sub.toString(), 2);
		long subtract = b1 - 1;
		//returns binary with no leading zeroes
		String binary = Long.toBinaryString(subtract);
		
		if(subtract == -1)
			binary = binary.substring(0, sub.n);
		
		boolean[] bits = new boolean[sub.n];
	
		int j = bits.length-1;
	     for (int i = binary.length()-1; i >= 0 ; i--) {
		    bits[j] = binary.charAt(i) == '1';
		    j--;
		 }	
	     
		j = 0;
		for (int i = index; i <= end ; i++) {
			set(i, bits[j]);
			j++;
		}
	}
	
	/**
	 * Arithmetic left shift - signed shift.
	 * 
	 * @param index
	 * @param k
	 */
	public void multiplyByTwo(int index, int k)
	{
		int end = index + (k-1);//including the bit
		
		if (end >= this.size())
			end = this.size() - 1;
		
		MarkedBitArray sub = this.subMarkedBitArray(index, end+1);
		sub.leftShift(1);
		int j = 0;
		for (int i = index; i <= end ; i++) {
			set(i, sub.get(j));
			j++;
		}
	}
	
	/**
	 * Arithmetic right shift - signed shift.
	 * 
	 * @param index
	 * @param k
	 */
	public void divideByTwo(int index, int k)
	{
		int end = index + (k-1);//including the bit
			
		if (end >= this.size())
			end = this.size() - 1;
			
		//assuming index position contains the sign
		boolean sign = this.get(index);
		MarkedBitArray sub = this.subMarkedBitArray(index, end+1);
		sub.rightShift(1);
		int j = 0;
		for (int i = index; i <= end ; i++) {
			set(i, sub.get(j));
			j++;
		}
		
		set(index, sign);
	}

	public boolean isMarked(int col) {
		return this.epiTags[0][col] != Character.MIN_VALUE;
	}

	public static void main(String[] args) {

		MarkedBitArray x = new MarkedBitArray(20, true);
		//boolean [] array = new boolean[x.size()] ;
		//Arrays.fill(array, true);
		//Arrays.fill(array, false);
		
		 //BinaryToRealVector p = new BinaryToRealVector(32);
		// [100.0, -3.75540298150587E-309, -4.457370669181882E-308, 3.658445548993004E-309, -5.828287045482088E-299, -6.44749793167779E-309, -5.059897025072937E-309, -5.89749139226362E-309, -9.214348591037205E-309, -9.10639065539646E-310]
		/* x = new MarkedBitArray(Utils.doubleToBinary(new double[]{3.658445548993004E-309, 3.658445548993004E-309}), 7);
	     System.out.println(x);
	     System.out.println( "+++"+ Arrays.toString(Utils.binaryToDouble(x.toString())));
	     x.divideByTwo(0, 64);
	     System.out.println(x);
	     System.out.println( "+++"+ Arrays.toString(Utils.binaryToDouble(x.toString())));
	     x.multiplyByTwo(0, 64);
	     System.out.println(x);
	     System.out.println( "+++"+ Arrays.toString(Utils.binaryToDouble(x.toString())));
	     System.out.println(Arrays.toString(Utils.doubleToBinary(new double[]{-3.658445548993004E-309})));
	     System.out.println("Length: "+Utils.doubleToBinary(new double[]{3.658445548993004E-309}).length);
	    */ System.out.println(Double.longBitsToDouble( Long.parseUnsignedLong("1000000000000010101000010111010111100001101010101000111101001101",2)));
	    
	     x = new MarkedBitArray("1111111111111111111111111111111111111111111111111111111111110000", 7, 0);
	     System.out.println(x);
	     System.out.println(Long.parseUnsignedLong(x.toString(),2));
	     x.addOne(0, 4);
	     System.out.println(x);
	     System.out.println(Long.parseUnsignedLong(x.toString(),2));
	     x.subtractOne(60, 64);
	     System.out.println(x);
	     System.out.println(Long.parseUnsignedLong(x.toString(),2));
	     
	     //  if (y == 0)  return x; return subtract(x ^ y, (~x & y) << 1); 
	}

}
