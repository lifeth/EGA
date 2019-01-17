package alife.epimarks.tests;

import alife.epimarks.operator.Marking;
import alife.epimarks.types.MarkedBitArray;
import unalcol.math.logic.Predicate;
import unalcol.search.population.IterativePopulationSearch;
import unalcol.search.population.Population;
import unalcol.search.population.PopulationSearch;
import unalcol.search.selection.Selection;
import unalcol.search.variation.Variation_1_1;
import unalcol.search.variation.Variation_2_2;

/**
 * Generational Genetic Algorithm factory (Only uses offsprings in replacement)
 * 
 * @author lifeth
 *
 * @param <T>
 */
public class EAFactory<T> extends unalcol.evolution.EAFactory<MarkedBitArray>{
	
	@Override
	public PopulationSearch<MarkedBitArray,Double>	generational_ga(
				int mu, Selection<MarkedBitArray> parentSelection, 
				Variation_1_1<MarkedBitArray> mutation, 
				Variation_2_2<MarkedBitArray> xover, double xoverProbability, 
				Predicate<Population<MarkedBitArray>> tC ){
		//0.98
		return new IterativePopulationSearch<MarkedBitArray,Double>(
						new GAStep<MarkedBitArray>( mu, parentSelection, mutation, xover, xoverProbability, new Marking(), true),
						tC );
	}	
	
}