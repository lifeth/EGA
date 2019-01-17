package alife.epimarks.tests;

import unalcol.search.population.Generational;
import unalcol.search.population.PopulationReplacement;
import unalcol.search.population.RealQualifyPopulationSearch;
import unalcol.search.population.TotalSelectionReplacement;
import unalcol.search.population.VariationReplacePopulationSearch;
import unalcol.search.variation.Variation_1_1;
import unalcol.search.variation.Variation_2_2;
import unalcol.search.selection.Selection;

public class GAStep<MarkedBitArray> extends VariationReplacePopulationSearch<MarkedBitArray,Double> implements RealQualifyPopulationSearch<MarkedBitArray>{
	
    public GAStep( int mu, Selection<MarkedBitArray> selection,
    		Variation_1_1<MarkedBitArray> mutation, Variation_2_2<MarkedBitArray> xover,
            double probability, Variation_1_1<MarkedBitArray> marking, PopulationReplacement<MarkedBitArray> replace ) {
    	super( mu, new GAVariation<MarkedBitArray>(selection, mutation, xover, probability, marking), replace);
    } 
    
    public GAStep( int mu, Selection<MarkedBitArray> selection,
    		Variation_1_1<MarkedBitArray> mutation, Variation_2_2<MarkedBitArray> xover,
            double probability, Variation_1_1<MarkedBitArray> marking, boolean generational ) {
    	super( 	mu, new GAVariation<MarkedBitArray>(selection, mutation, xover, probability, marking), 
    			generational?new Generational<MarkedBitArray>():new TotalSelectionReplacement<MarkedBitArray>());
    } 
}