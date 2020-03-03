package alife.epimarks.tests;

import alife.epimarks.operator.Marking;
import unalcol.algorithm.iterative.ForLoopCondition;
import unalcol.evolution.haea.HaeaOperators;
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
public class EAFactory<T> extends unalcol.evolution.EAFactory<T>{

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PopulationSearch<T,Double>	generational_ga(
				int mu, Selection<T> parentSelection, 
				Variation_1_1<T> mutation, 
				Variation_2_2<T> xover, double xoverProbability, 
				Predicate<Population<T>> tC ){
		
		return new IterativePopulationSearch<T,Double>(
						new GAStep( mu, parentSelection, mutation, xover, xoverProbability, new Marking(0.02), true),
						tC );
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PopulationSearch<T,Double>	steady_ga(
			int mu, Selection<T> parent_selection, 
			Variation_1_1<T> mutation, 
			Variation_2_2<T> xover, double xover_probability, 
			Predicate<Population<T>> tC ){
		return new IterativePopulationSearch<T,Double>(
						new GAStep( mu, parent_selection, mutation, xover, xover_probability, new Marking(0.02), false),
						tC );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PopulationSearch<T,Double> HAEA_Generational( int mu, HaeaOperators<T> operators, Selection<T> selection, int MAXITERS ){
		return new IterativePopulationSearch<T,Double>( new HaeaStep(mu,selection,operators, new Marking(0.02), false) , 
				new ForLoopCondition<Population<T>>(MAXITERS));
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PopulationSearch<T,Double> HAEA( int mu, HaeaOperators<T> operators, Selection<T> selection, Predicate<Population<T>> tC ){
		return  new IterativePopulationSearch<T,Double>( new HaeaStep(mu,selection,operators, new Marking(0.02)), tC );
	}
}