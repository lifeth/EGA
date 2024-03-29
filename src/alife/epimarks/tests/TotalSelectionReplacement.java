/**
 * 
 */
package alife.epimarks.tests;

import unalcol.search.Goal;
import unalcol.search.population.Population;
import unalcol.search.selection.Elitism;
import unalcol.search.selection.Selection;
import unalcol.search.solution.Solution;

/**
 * @author lifeth
 * @param <T>
 *
 */
public class TotalSelectionReplacement<T> implements unalcol.search.population.PopulationReplacement<T>{
	
protected Selection<T> selection;
	
	public TotalSelectionReplacement() {
		//this( new Tournament<T>(4) ); 
		 this( new Elitism<T>(1.0, 0.0));
	}
	
	public TotalSelectionReplacement( Selection<T> selection ){
		this.selection = selection;
	}
	
	@Override
	public Population<T> apply(Population<T> current,
			Population<T> next) {
		int n = current.object().length;
		int m = next.object().length;
		Solution<T>[] parent = (Solution<T>[])tagged_array(n+m);
		System.arraycopy(current.object(), 0, parent, 0, n);
		System.arraycopy(next.object(), 0, parent, n, m);
		
		Population<T> newPop = new Population<T>(selection.pick(n, parent));
    	String gName = Goal.class.getName();
    	newPop.set( gName, current.data(gName) );

		return newPop;
	}	

}
