/**
 * 
 */
package alife.epimarks.tests;

import unalcol.search.variation.Variation_1_1;

/**
 * @author lifeth
 * @param <T>
 *
 */
public abstract class MarkingVariation<T> extends Variation_1_1<T> {
	
	/**
	 * Computes marking periods.
	 * @param pop
	 */
	public abstract boolean isMarkingPeriodOn();
	
	public abstract void increment();

}
