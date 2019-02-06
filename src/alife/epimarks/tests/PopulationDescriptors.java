/**
 * 
 */
package alife.epimarks.tests;

import unalcol.search.Goal;
import unalcol.search.population.Population;

/**
 * @author lifeth
 * @param <T>
 *
 */
public class PopulationDescriptors<T> extends unalcol.search.population.PopulationDescriptors<T>{
	
	@Override
	public double[] descriptors(Population<T> pop) {
		String gName = Goal.class.getName();
		double[] quality = new double[pop.size()];
		for(int i=0; i<quality.length; i++ ){
			quality[i] = (Double)pop.get(i).info(gName);
		} 
		return Stats.statistics_with_median(quality).get();		
	}

}
