/**
 * 
 */
package alife.epimarks.tests;

import unalcol.search.solution.Solution;
import unalcol.search.variation.Variation_1_1;

/**
 * @author lifeth
 * @param <T>
 *
 */
public class Variation_One_One<T> extends Variation_1_1<T> {

	public T applyMutationOnTags(T x) {
		return applyMutationOnTags(new Solution<T>(x)).object();
	}

	public Solution<T> applyMutationOnTags(Solution<T> x) {
		return new Solution<T>(applyMutationOnTags(x.object()), x.tags(), false);
	}

	@SuppressWarnings("unchecked")
	public Solution<T>[] applyMutationOnTags(Solution<T>... pop) {
		Solution<T>[] v = new Solution[pop.length];
		for (int i = 0; i < pop.length; i++)
			v[i] = applyMutationOnTags(pop[i]);
		return v;
	}
}
