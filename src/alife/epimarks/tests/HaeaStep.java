/**
 * 
 */
package alife.epimarks.tests;

import unalcol.evolution.haea.HaeaOperators;
import unalcol.evolution.haea.HaeaReplacement;
import unalcol.search.population.RealQualifyPopulationSearch;
import unalcol.search.population.VariationReplacePopulationSearch;
import unalcol.search.selection.Selection;

/**
 * @author lifeth
 * @param <T>
 *
 */
public class HaeaStep<T> extends VariationReplacePopulationSearch<T,Double> implements RealQualifyPopulationSearch<T>{

	/**
     * Constructor: Creates a Haea offspring generation strategy
     * @param operators Genetic operators used to evolve the solution
     * @param grow Growing function
     * @param selection Extra parent selection mechanism
     */
    public HaeaStep(int mu, Selection<T> parent_selection, HaeaOperators<T> operators, 
    		MarkingVariation<T> marking ) {
    	super(mu, new HaeaVariation<T>(parent_selection, operators, marking),
    			 new HaeaReplacement<T>( operators ) );
    }
    
    /**
     * Constructor: Creates a Haea offspring generation strategy
     * @param operators Genetic operators used to evolve the solution
     * @param grow Growing function
     * @param selection Extra parent selection mechanism
     */
    public HaeaStep(int mu, Selection<T> parent_selection, HaeaOperators<T> operators, 
    		MarkingVariation<T> marking, boolean steady) {
    	super(mu, new HaeaVariation<T>(parent_selection, operators, marking ),
    			 new HaeaReplacement<T>( operators, steady ) );
    }

}
