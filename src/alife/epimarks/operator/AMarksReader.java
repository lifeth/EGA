/**
 * 
 */
package alife.epimarks.operator;

/**
 * @author lifeth
 * @param <T>
 *
 */
public abstract class AMarksReader<T> {
	
	/**
	 * Returns a copy of the original chromosome without tags. The copy represents
	 * the interpretation of the genome and tags. The returned object is used to 
	 * calculate the fitness.
	 * @param x
	 * @return Phenotype to be evaluated.
	 */
	public abstract T readMarks(T x);

}
