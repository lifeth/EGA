/**
 * 
 */
package alife.epimarks.tests;

import unalcol.types.real.StatisticsWithMedian;
import unalcol.types.real.array.DoubleArray;

/**
 * @author lifeth
 *
 */
public class Stats extends StatisticsWithMedian {

	public static Stats statistics_with_median(double[] x) {
		return new Stats(x);
	}

	/**
	 * Computes the statistical information of the given array of doubles
	 * 
	 * @param x
	 *            Array to be statistically analized
	 */
	public Stats(double[] x) {

		compute_median(x.clone());
		
	    int n = x.length;
        min = max = avg = x[0];
        for (int i = 1; i < n; i++) {
            if (x[i] < min) {
                min = x[i];
                minIndex = i;
            } else {
                if (x[i] > max) {
                    max = x[i];
                    maxIndex = i;
                }
            }
            avg += x[i];
        }
        
        avg /= n;
        
        for (int i = 0; i < n; i++) {
            variance += (x[i] - median) * (x[i] - median);
        }                
        variance /= (n>1)?(n - 1):1.0;
        deviation = Math.sqrt(variance);
	}

	public void compute_median(double[] x) {
		DoubleArray.merge(x);
		int n = x.length;
		median = ((n % 2) == 0) ? (x[n / 2] + x[n / 2 - 1]) / 2.0 : x[n / 2];
	}
}
