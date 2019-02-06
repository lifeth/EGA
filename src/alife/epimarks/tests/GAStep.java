package alife.epimarks.tests;

import unalcol.search.population.Generational;
import unalcol.search.population.PopulationReplacement;
import unalcol.search.population.RealQualifyPopulationSearch;
import unalcol.search.population.VariationReplacePopulationSearch;
import unalcol.search.variation.Variation_1_1;
import unalcol.search.variation.Variation_2_2;
import unalcol.search.selection.Selection;

public class GAStep<T> extends VariationReplacePopulationSearch<T,Double> implements RealQualifyPopulationSearch<T>{
	
    public GAStep( int mu, Selection<T> selection,
    		Variation_1_1<T> mutation, Variation_2_2<T> xover,
            double probability, Variation_One_One<T> marking, PopulationReplacement<T> replace ) {
    	
    	super( mu, new GAVariation<T>(selection, mutation, xover, probability, marking), replace);
    } 
    
    public GAStep( int mu, Selection<T> selection,
    		Variation_1_1<T> mutation, Variation_2_2<T> xover,
            double probability, Variation_One_One<T> marking, boolean generational ) {
    	
    	super( 	mu, new GAVariation<T>(selection, mutation, xover, probability, marking), 
    			generational?new Generational<T>():new TotalSelectionReplacement<T>());
    } 
}