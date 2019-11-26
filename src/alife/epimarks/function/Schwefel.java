package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.real.BinaryToRealVector;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  Schwefel [-500,500]^n </p>
 * <p>Description: The Schwefel function</p>
 * @author lifeth
 */
public class Schwefel {
	
   private BinaryToRealVector p;

/**
 * Constructor: Creates a Schwefel function
 * [-500, 500] interval
 */
  public Schwefel(int BITS_PER_DOUBLE, double min[], double max[]){
	this.p = new BinaryToRealVector(BITS_PER_DOUBLE, min, max);
  }

  /**
   * Evaluates the Schwefel function over a real value
   * @param x the real value argument of the Schwefel function
   * @return the Schwefel value for the given value
   */
  public static double apply( double x ){
    return ( -x * Math.sin(Math.sqrt(Math.abs(x))) );
  }
  
  
  public class Classic extends OptimizationFunction<BitArray> {
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( BitArray x ){
		
		double[] genome = p.decode(x);
		  
	    int n = genome.length;
	    double f = 0.0;
	    for( int i=0; i<n; i++ ){
	      f += Schwefel.apply(genome[i]);
	    }
	    return (418.9829101*n + f);
	  }  
  }
  
  public class Extended extends OptimizationFunction<MarkedBitArray> {
	  
	  private Reader reader = new Reader();
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x ){
		  
		MarkedBitArray xx = reader.readMarks(x);
		double[] genome = p.decode(new BitArray(xx.toString()));
		  
	    int n = genome.length;
	    double f = 0.0;
	    for( int i=0; i<n; i++ ){
	      f += Schwefel.apply(genome[i]);
	    }
	    return (418.9829101*n + f);
	  }
  }
}
