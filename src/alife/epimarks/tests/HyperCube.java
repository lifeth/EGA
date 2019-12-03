/**
 * 
 */
package alife.epimarks.tests;

import unalcol.random.Random;
import unalcol.random.integer.IntUniform;

/**
 * @author lifeth
 *
 */
public class HyperCube extends unalcol.optimization.real.HyperCube{

	/**
	 * @param min
	 * @param max
	 */
	public HyperCube(double[] min, double[] max) {
		super(min, max);
	}
	
	 public double[] repair(double[] x) {
	        x = x.clone();
	        for( int i=0; i<x.length; i++ ){
	        	
	        	/*this never happens
	        	if( x[i] == Double.NEGATIVE_INFINITY){
	        		x[i] = min[i];
	        	}else if( x[i] == Double.POSITIVE_INFINITY){
	        		 x[i] = max[i];
	        	}*/
	        		
	            if( x[i] < min[i]){
	                x[i] = min[i];
	            }else if( x[i] > max[i]){
	                x[i] = max[i];
                }else if(Double.isNaN(x[i])){
                	x[i] = //Random.nextBool() ? max[i] : min[i]; UniformGenerator
                	this.pick()[(int) (Math.random() * x.length)];
	        	}
	            
	        }
	        return x;        
	    }
}
