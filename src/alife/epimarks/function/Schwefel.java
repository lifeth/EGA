package alife.epimarks.function;

import java.util.Arrays;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.tests.HyperCube;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.search.space.Space;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  Schwefel [-500,500]^n </p>
 * <p>Description: The Schwefel function</p>
 * @author lifeth
 */
public class Schwefel {

/**
 * Constructor: Creates a Schwefel function
 * [-500, 500] interval
 */
  public Schwefel(){}

  /**
   * Evaluates the Schwefel function over a real value
   * @param x the real value argument of the Schwefel function
   * @return the Schwefel value for the given value
   */
  public static double apply( double x ){
    return ( -x * Math.sin(Math.sqrt(Math.abs(x))) );
    //418.9829 * n - (sum(x .* sin(sqrt(abs(x))), 2));
  }
  
  
  public class Classic extends OptimizationFunction<BitArray> {
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( BitArray x ){
		
		double [] genome =  Utils.binaryToDouble(x.toString());
		  
	    int n = genome.length;
	    double f = 0.0;
	    for( int i=0; i<n; i++ ){
	      f += Schwefel.apply(genome[i]);
	    }
	    
	    System.out.println(Arrays.toString(genome));
	    
	    return (418.9829101*n + f);
	  }  
  }
  
  public class Extended extends OptimizationFunction<MarkedBitArray> {
	  
	  private Reader reader = new Reader();
	  protected Space<double[]> space;
	  
		/**
		 * 
		 */
		public Extended(Space<double[]> space) {
			this.space = space;
		}
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x ){
		  
		MarkedBitArray xx = reader.readMarks(x);
		double [] genome =  Utils.binaryToDouble(xx.toString());
		genome = this.space.repair(genome);
		  
	    int n = genome.length;
	    double f = 0.0;
	    for( int i=0; i<n; i++ ){
	      f += Schwefel.apply(genome[i]);
	    }
		
	    System.out.println(Arrays.toString(genome));
	 
	    return (418.9829101*n + f);
	  }
  }
}
